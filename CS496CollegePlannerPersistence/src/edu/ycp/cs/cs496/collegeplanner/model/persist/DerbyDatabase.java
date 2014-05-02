package edu.ycp.cs.cs496.collegeplanner.model.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import edu.ycp.cs.cs496.collegeplanner.models.Advisor;
import edu.ycp.cs.cs496.collegeplanner.models.Course;
import edu.ycp.cs.cs496.collegeplanner.models.CourseSequencePairs;
import edu.ycp.cs.cs496.collegeplanner.models.User;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;


public class DerbyDatabase implements IDatabase{
	private static final int MAX_ATTEMPTS = 10;

	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby JDBC driver");
		}
	}

	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:H:/collegeplanner.edu;create=true");

		// Set autocommit to false to allow multiple the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);

		return conn;
	}

	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}

	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();

		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;

			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}

			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}

			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	//TODO: GOOD LUCK

	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement users = null;
				PreparedStatement advisors = null;
				PreparedStatement courses = null;
				PreparedStatement courseSequences = null;
				PreparedStatement currentClasses = null;
				PreparedStatement userAdvisorLink = null;
				PreparedStatement departments = null;
				PreparedStatement classCategories = null;
				PreparedStatement majors = null;
				PreparedStatement courseUserLink = null;

				try {
					// Note that the 'id' column is an autoincrement primary key,
					// so SQLite will automatically assign an id when rows
					// are inserted.
					users = conn.prepareStatement(
							"create table users (" +
									" id integer primary key not null generated always as identity," +
									" username varchar(80) unique," +
									" password varchar(20)," +
									" name varchar(50)," +
									" emailAddress varchar(80)," +
									" major varchar(40)," +
									"maxCredits integer" +
									")"

							);

					advisors = conn.prepareStatement(
							"create table advisors (" +
									"id integer primary key not null generated always as identity," +
									"name varchar(80)," +
									"department varchar(80)," +
									"location varchar(10)," +
									"email varchar(80)," +
									"phone varchar(15)" +
							")");

					courses = conn.prepareStatement( 
							"create table courses(" +
									"id integer primary key not null generated always as identity," +
									"startTime varchar(20)," +
									"endTime varchar(20)," +
									"name varchar(50) unique," +
									"prereq_id integer," +
									"instructor varchar(50)," +
									"location varchar(10)," +
									"category varchar(40)," +
									"type varchar(10)," +
									"level integer," +
									"semester integer" +							
							")");

					courseSequences = conn.prepareStatement(
							"create table courseSequences (" +
									"sequenceName varchar(80)," +
									"courseName varchar(80)," +
									"yearNum integer," +
									"credits integer," +
									"prereq varchar(10)" +
									")"
							);
					currentClasses = conn.prepareStatement(
							"create table currentClasses (" +
									"userId integer," +
									"nameAndInfo varchar(100)," +
									"courseName varchar(10)" +
									")"
							);
					userAdvisorLink = conn.prepareStatement(
							"create table userAdvisorLink (" +
									"userId integer," +
									"advisorId integer" +
									")"
							);

					departments = conn.prepareStatement(
							"create table departments (" +
									"name varchar(80)" +
							")");
					classCategories = conn.prepareStatement(
							"create table classCategories (" +
									"category varchar(80)" +
									")"
							);
					majors = conn.prepareStatement(
							"create table majors (" +
									"major varchar(80)" +
									")"
							);
					courseUserLink = conn.prepareStatement(
							"create table courseUserLinks (" +
									"userId integer," +
									"courseId integer" +
							")");

					users.executeUpdate();
					advisors.executeUpdate();
					courses.executeUpdate();
					courseSequences.executeUpdate();
					currentClasses.executeUpdate();
					userAdvisorLink.executeUpdate();
					departments.executeUpdate();
					classCategories.executeUpdate();
					majors.executeUpdate();
					courseUserLink.executeUpdate();

					return true;
				} finally {
					DBUtil.closeQuietly(users);
					DBUtil.closeQuietly(advisors);
					DBUtil.closeQuietly(courses);
					DBUtil.closeQuietly(courseSequences);
					DBUtil.closeQuietly(currentClasses);
					DBUtil.closeQuietly(userAdvisorLink);
					DBUtil.closeQuietly(departments);
					DBUtil.closeQuietly(classCategories);
					DBUtil.closeQuietly(majors);
					DBUtil.closeQuietly(courseUserLink);
				}
			}
		});
	}

	@Override
	public boolean addUser(final User user) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet generatedKeys = null;

				try {
					stmt = conn.prepareStatement(
							"insert into users (username, password, name, emailAddress, major, maxCredits)" +
									" values (?, ?, ?, ?, ?, ?)",
									PreparedStatement.RETURN_GENERATED_KEYS
							);					

					int index = 1;
					stmt.setString(index++, user.getUsername());
					stmt.setString(index++, user.getPassword());
					stmt.setString(index++, user.getName());
					stmt.setString(index++, user.getEmailAddress());
					stmt.setString(index++, user.getMajor());
					stmt.setInt(index++,  user.getMaxCredits());

					try {
						stmt.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
						return false;
					}

					generatedKeys = stmt.getGeneratedKeys();
					if (!generatedKeys.next()) {
						throw new SQLException("Could not get auto-generated key for inserted User");
					}

					user.setId(generatedKeys.getInt(1));
					System.out.println("New User has id " + user.getId());
					return true;
				} finally {
					DBUtil.closeQuietly(conn);					
					DBUtil.closeQuietly(stmt);
				}
			}
		});		
	}

	@Override
	public boolean setNameOfUser(String username, String newUsername) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setPasswordOfUser(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getPasswordOfUser(final String username) {
		return executeTransaction(new Transaction<String>() {

			@Override
			public String execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement("select users.password from users where users.username= ?");
					stmt.setString(1, username);

					resultSet = stmt.executeQuery();

					if (!resultSet.next()) {
						// No such item
						return null;
					}

					String pw = resultSet.getString(1);

					return pw;
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}

		});		
	}


	@Override
	public boolean changeUsernameOfUser(String username, String newUsername) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUser(final String username) {
		return executeTransaction(new Transaction<User>() {

			@Override
			public User execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement("select users.* from users where users.username= ?");
					resultSet = stmt.executeQuery();				

					if (!resultSet.next()) {
						// No such item
						return null;
					}

					User user = new User();
					user.setId(resultSet.getInt(1));
					user.setUsername(resultSet.getString(2));
					user.setPassword(resultSet.getString(3));
					user.setName(resultSet.getString(4));
					user.setEmailAddress(resultSet.getString(5));
					user.setMajor(resultSet.getString(6));

					return user;
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}

			}
		});
	}

	@Override
	public ArrayList<User> getUsers() {
		return executeTransaction(new Transaction<ArrayList<User>>() {

			@Override
			public ArrayList<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {					
					stmt = conn.prepareStatement("select users.* from users");

					resultSet = stmt.executeQuery();

					ArrayList<User> result = new ArrayList<User>();

					while (resultSet.next()) {
						User user = new User();
						loadUser(user, resultSet, 1);
						result.add(user);
					}

					return result;
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}					
		});	
	}

	@Override
	public boolean addAdvisor(final Advisor advsr) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet generatedKeys = null;

				try {
					stmt = conn.prepareStatement(
							"insert into advisors(name, department, location, email, phone)" +
									" values (?, ?, ?, ?, ?)",
									PreparedStatement.RETURN_GENERATED_KEYS
							);					

					int index = 1;
					stmt.setString(index++, advsr.getName());
					stmt.setString(index++, advsr.getDepartment());
					stmt.setString(index++, advsr.getLocation());
					stmt.setString(index++, advsr.getEmail());
					stmt.setString(index++, advsr.getPhone());					

					try {
						stmt.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
						return false;
					}

					generatedKeys = stmt.getGeneratedKeys();
					if (!generatedKeys.next()) {
						throw new SQLException("Could not get auto-generated key for inserted User");
					}

					advsr.setId(generatedKeys.getInt(1));
					System.out.println("New Advisor has id " + advsr.getId());
					return true;
				} finally {
					DBUtil.closeQuietly(conn);					
					DBUtil.closeQuietly(stmt);
				}
			}
		});	
	}

	@Override
	public boolean setAdvisorForUser(String advisor, String user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Advisor getAdvisorForUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Advisor getAdvisor(String advisorName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Advisor> getAdvisors() {
		return executeTransaction(new Transaction<ArrayList<Advisor>>() {

			@Override
			public ArrayList<Advisor> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {					
					stmt = conn.prepareStatement("select advisors.* from advisors");

					resultSet = stmt.executeQuery();

					ArrayList<Advisor> result = new ArrayList<Advisor>();

					while (resultSet.next()) {
						Advisor advisor = new Advisor();
						loadAdvisor(advisor, resultSet, 1);
						result.add(advisor);
					}

					return result;
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}					
		});	
	}

	@Override
	public ArrayList<String> getAdvisorDepartments() {
		return executeTransaction(new Transaction<ArrayList<String>>() {

			@Override
			public ArrayList<String> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {					
					stmt = conn.prepareStatement("select departments.* from departments");

					resultSet = stmt.executeQuery();

					ArrayList<String> result = new ArrayList<String>();

					int index = 1;
					
					while (resultSet.next()) {
						String name = resultSet.getString(index++);
						result.add(name);
					}

					return result;
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}					
		});	
	}

	@Override
	public ArrayList<Advisor> getAdvisorsByDepartment(String department) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteUser(final User user) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("delete from users where users.username =?");
					stmt.setString(1, user.getUsername());
					
					stmt.executeUpdate();
				} finally {
					DBUtil.closeQuietly(conn);					
					DBUtil.closeQuietly(stmt);
				}
				return true;
			}
		});
	}

	@Override
	public String getNameOfUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getMajors() {
		return executeTransaction(new Transaction<ArrayList<String>>() {

			@Override
			public ArrayList<String> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {					
					stmt = conn.prepareStatement("select majors.* from majors");

					resultSet = stmt.executeQuery();

					ArrayList<String> result = new ArrayList<String>();

					int index = 1;					
					
					while (resultSet.next()) {
						String name = resultSet.getString(index++);
						result.add(name);
					}

					return result;
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}					
		});			
	}

	@Override
	public boolean setMajor(String username, String major) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addCourse(Course course) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Course> getCourses() {
		return executeTransaction(new Transaction<ArrayList<Course>>() {

			@Override
			public ArrayList<Course> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {					
					stmt = conn.prepareStatement("select courses.* from courses");

					resultSet = stmt.executeQuery();

					ArrayList<Course> result = new ArrayList<Course>();					
					
					while (resultSet.next()) {
						Course course = new Course();
						loadCourses(course, resultSet, 1);
						result.add(course);
					}

					return result;
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}								
		});	
	}

	@Override
	public String getMajor(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getClassesTakenByUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Course> getCoursesTakenByUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getClassesInCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getClassCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addClassToUser(String username, String className) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteClassFromUser(String username, String className) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<CourseSequencePairs> getCourseSequence(String major) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getCurrentClassSchedule(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addClassToSchedule(String username, String classInfo,
			String courseName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeClassFromSchedule(String username, String courseName) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) {
		DerbyDatabase db = new DerbyDatabase();
		System.out.println("Creating tables...");
		db.createTables();
		System.out.println("Loading initial data...");
		db.loadInitialData();
		System.out.println("Done!");
	}

	private void loadInitialData() {
		//We need to load a TON of initial data!

	}

	private void loadUser(User user, ResultSet resultSet, int index) throws SQLException {
		user.setId(resultSet.getInt(index++));
		user.setUsername(resultSet.getString(index++));
		user.setPassword(resultSet.getString(index++));
		user.setName(resultSet.getString(index++));
		user.setEmailAddress(resultSet.getString(index++));
		user.setMajor(resultSet.getString(index++));
		user.setMaxCredits(resultSet.getInt(index++));
	}

	private void loadAdvisor(Advisor advisor, ResultSet resultSet, int index) throws SQLException {
		advisor.setId(resultSet.getInt(index++));
		advisor.setName(resultSet.getString(index++));
		advisor.setDepartment(resultSet.getString(index++));
		advisor.setLocation(resultSet.getString(index++));
		advisor.setEmail(resultSet.getString(index++));
		advisor.setPhone(resultSet.getString(index++));				
	}
	private void loadCourses(Course course, ResultSet resultSet, int index) throws SQLException {
		course.setId(resultSet.getInt(index++));
		course.setStartTime(resultSet.getString(index++));
		course.setEndTime(resultSet.getString(index++));
		course.setPrereq_id(resultSet.getInt(index++));
		course.setInstructor(resultSet.getString(index++));
		course.setLocation(resultSet.getString(index++));
		course.setCategory(resultSet.getString(index++));
		course.setType(resultSet.getString(index++));
		course.setLevel(resultSet.getInt(index++));
		course.setSemester(resultSet.getInt(index++));		
	}
	
}
