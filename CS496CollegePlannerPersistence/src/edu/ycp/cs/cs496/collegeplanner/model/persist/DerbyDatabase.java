package edu.ycp.cs.cs496.collegeplanner.model.persist;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.controllers.AdvisorParser;
import edu.ycp.cs.cs496.collegeplanner.controllers.CategoryParser;
import edu.ycp.cs.cs496.collegeplanner.controllers.CourseParser;
import edu.ycp.cs.cs496.collegeplanner.controllers.CourseSequenceParser;
import edu.ycp.cs.cs496.collegeplanner.controllers.DepartmentParser;
import edu.ycp.cs.cs496.collegeplanner.controllers.MajorParser;
import edu.ycp.cs.cs496.collegeplanner.controllers.UserParser;
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
									"courseName varchar(30)" +
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
	public boolean setNameOfUser(final String username, final String newUsername) {
		return executeTransaction(new Transaction<Boolean>() {
			//TODO: make sure username does not already exist
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement("update users set username=?, where username=?");
					stmt.setString(1, newUsername);
					stmt.setString(2, username);
					stmt.executeUpdate();
					return true;					
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(conn);
				}				
			}			
		});
	}

	@Override
	public boolean setPasswordOfUser(final String username, final String password) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement("update users set password=?, where username=?");
					stmt.setString(1, password);
					stmt.setString(2, username);
					stmt.executeUpdate();
					return true;					
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(conn);
				}				
			}			
		});
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
	public boolean changeUsernameOfUser(final String username, final String newUsername) {
		return executeTransaction(new Transaction<Boolean>() {
			//TODO: make sure new username does not already exist
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement("update users set username= ? where username= ?");
					stmt.setString(1, newUsername);
					stmt.setString(2, username);
					stmt.executeUpdate();
					return true;					
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(conn);
				}				
			}			
		});
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
					stmt.setString(1, username);
					resultSet = stmt.executeQuery();				

					if (!resultSet.next()) {
						// No such item
						return null;
					}
					
					User user = new User();
					loadUser(user, resultSet, 1);

					System.out.println(user.getUsername() + user.getId());
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
	public boolean setAdvisorForUser(final String advisorName, final String username) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				
				User user = new User();
				user = getUser(username);
				int userId = user.getId();
				
				Advisor advisor = new Advisor();
				advisor = getAdvisor(advisorName);
				int advisorId = advisor.getId();
				
				System.out.println("In set advisor For user!");
				
				try {
					stmt = conn.prepareStatement("delete from userAdvisorLink where userAdvisorLink.userId= ?");
					stmt.setInt(1, userId);					
					stmt.executeUpdate();
					
					stmt2 = conn.prepareStatement("insert into userAdvisorLink (userId, advisorId) values(?,?)");
					stmt2.setInt(1, userId);
					stmt2.setInt(2, advisorId);
					stmt2.executeUpdate();
					
					return true;					
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(conn);
				}				
			}			
		});
	}

	@Override
	public Advisor getAdvisorForUser(final User user) {
		return executeTransaction(new Transaction<Advisor>() {

			@Override
			public Advisor execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				User userWithId = new User();
				userWithId = getUser(user.getUsername());
				
				int userId = userWithId.getId();
				
				try {					
					stmt = conn.prepareStatement("select userAdvisorLink.advisorId from userAdvisorLink where userAdvisorLink.userId= ?");
					stmt.setInt(1, userId);
					resultSet = stmt.executeQuery();
					
					if (!resultSet.next()) {
						System.out.println("Advisor Not Found!");
						return null;
					}
					
					int advisorId = resultSet.getInt("advisorId");
					
					ArrayList<Advisor> all = getAdvisors();
					Advisor advisor = new Advisor();
					
					for(int i = 0; i < all.size(); i++) {
						if(all.get(i).getId() == advisorId) {
							advisor = all.get(i);
							System.out.println("Found Advisor" + advisor.getName());
						}
					}
					
					return advisor;
					
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
			
		});
	}

	@Override
	public Advisor getAdvisor(final String advisorName) {
		return executeTransaction(new Transaction<Advisor>() {

			@Override
			public Advisor execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {					
					stmt = conn.prepareStatement("select advisors.* from advisors where name=?");
					stmt.setString(1, advisorName);
					resultSet = stmt.executeQuery();
					if (!resultSet.next()) {
						return null;
					}
					Advisor advisor = new Advisor();
					loadAdvisor(advisor, resultSet, 1);
					return advisor;
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
			
		});
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
					
					while (resultSet.next()) {
						String name = resultSet.getString("name");
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
	public ArrayList<Advisor> getAdvisorsByDepartment(final String department) {
		return executeTransaction(new Transaction<ArrayList<Advisor>>() {

			@Override
			public ArrayList<Advisor> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {					
					stmt = conn.prepareStatement("select advisors.* from advisors where department=?");
					stmt.setString(1, department);
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
	public String getNameOfUser(final String username) {
		return executeTransaction(new Transaction<String>() {

			@Override
			public String execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {					
					stmt = conn.prepareStatement("select users.name from users where username=?");
					stmt.setString(1, username);
					resultSet = stmt.executeQuery();
					if (!resultSet.next()) {
						return null;
					}
					String name = resultSet.getString(1);
					return name;
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
			
		});
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
					
					while (resultSet.next()) {
						String name = resultSet.getString("major");
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
	public boolean setMajor(final String username,final String major) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement("update users set major= ? where username= ?");
					stmt.setString(1, major);
					stmt.setString(2, username);
					stmt.executeUpdate();
					return true;					
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(conn);
				}				
			}			
		});
	}

	@Override
	public boolean addCourse(final Course course) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement("insert into courses(startTime, endTime, name, prereq_id, " +
							"instructor, location, category, type, level, semester)" +
							" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
							PreparedStatement.RETURN_GENERATED_KEYS);
					stmt.setString(1, course.getStartTime());
					stmt.setString(2, course.getEndTime());
					stmt.setString(3, course.getName());
					stmt.setInt(4, course.getPrereq_id());
					stmt.setString(5, course.getInstructor());
					stmt.setString(6, course.getLocation());
					stmt.setString(7, course.getCategory());
					stmt.setString(8, course.getType());
					stmt.setInt(9, course.getLevel());
					stmt.setInt(10, course.getSemester());
					stmt.executeUpdate();
					return true;					
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(conn);
				}				
			}			
		});
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
	public String getMajor(final String username) {
			return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {					
					stmt = conn.prepareStatement("select users.major from users where users.username= ?");
					stmt.setString(1, username);
					resultSet = stmt.executeQuery();
					
					if (!resultSet.next()) {
						return null;
					}
					
					String major = resultSet.getString(1);
					return major;
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
			
		});
	}

	@Override
	public ArrayList<String> getClassesTakenByUser(final String username) {
		
				ArrayList<Course> courses = getCoursesTakenByUser(username);
				ArrayList<String> result = new ArrayList<String>();
				
				
				if(courses!=null) {
					System.out.println("foundCourses" + courses.get(0).getName());
					for(int i = 0; i < courses.size(); i++) {
						result.add(courses.get(i).getName());
						System.out.println(courses.get(i).getName());
					}
				}
				
				return result;
	}

	@Override
	public ArrayList<String> getClassesInCategory(final String category) {
		return executeTransaction(new Transaction<ArrayList<String>>() {
			@Override
			public ArrayList<String> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;					
				
				try {					
					stmt = conn.prepareStatement("select courses.* from courses where courses.category=?");
					stmt.setString(1, category);
					resultSet = stmt.executeQuery();
					
					ArrayList<String> classes = new ArrayList<String>();
					
					int index = 1;
					
					while(resultSet.next()) {
						classes.add(resultSet.getString("name"));
					}
					return classes;
	
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}			
		});
	}

	@Override
	public ArrayList<String> getClassCategories() {
		return executeTransaction(new Transaction<ArrayList<String>>() {
			@Override
			public ArrayList<String> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;					
				
				try {					
					stmt = conn.prepareStatement("select classCategories.* from classCategories");					
					resultSet = stmt.executeQuery();
					
					ArrayList<String> classes = new ArrayList<String>();
					
					int index = 1;
					
					while(resultSet.next()) {
						classes.add(resultSet.getString("category"));
					}
					return classes;
	
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}			
		});
	}

	@Override
	public boolean addClassToUser(final String username, final String className) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement getCourseId = null;
				ResultSet resultSet = null;	
				
				User user = getUser(username);
				int userId = user.getId();
				
				System.out.println("userId: " + userId);
				
				
				try {
					getCourseId = conn.prepareStatement("select courses.id from courses where courses.name = ?");
							getCourseId.setString(1, className);
							
							resultSet = getCourseId.executeQuery();
							
					if(resultSet.next()) {	
						
						System.out.println("Actually going to try to insert: " + className);
						int courseId = resultSet.getInt("id");
						System.out.println("courseId to be added " + courseId);
						stmt = conn.prepareStatement(
								"insert into courseUserLinks (userId, courseId)" +
										" values (?, ?)"
	
								);
						stmt.setInt(1, userId);
						stmt.setInt(2, courseId);
						stmt.executeUpdate();
						return true;	
					}
					
					return false;
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(conn);
				}				
			}			
		});
	}

	@Override
	public boolean deleteClassFromUser(final String username,final String className) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement getCourseId = null;
				ResultSet resultSet = null;
				
				User user = new User();
				user = getUser(username);
				int userId = user.getId();
				
				try {
					
					getCourseId = conn.prepareStatement("select courses.id from courses where courses.name = ?");
					getCourseId.setString(1, className);
					resultSet = getCourseId.executeQuery();
					
					if(resultSet.next()) {
						int courseId = resultSet.getInt("id");
						stmt = conn.prepareStatement("delete from courseUserLinks where courseUserLinks.userId= ? and courseUserLinks.courseId = ?");
						stmt.setInt(1, userId);
						stmt.setInt(2, courseId);
						
						
						stmt.executeUpdate();
						return true;
					}
					return false;
				} finally {
					DBUtil.closeQuietly(conn);					
					DBUtil.closeQuietly(stmt);
				}
				
			}
		});
	}

	@Override
	public ArrayList<CourseSequencePairs> getCourseSequence(final String major) {
		return executeTransaction(new Transaction<ArrayList<CourseSequencePairs>>() {
			@Override
			public ArrayList<CourseSequencePairs> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;					
				
				try {					
					stmt = conn.prepareStatement("select courseSequences.* from courseSequences where sequenceName=?");	
					stmt.setString(1, major);
					resultSet = stmt.executeQuery();
					
					ArrayList<CourseSequencePairs> sequences = new ArrayList<CourseSequencePairs>();
					
					int index = 1;
					
					while(resultSet.next()) {
						CourseSequencePairs csp = new CourseSequencePairs();
						loadSequences(csp, resultSet, index);
						sequences.add(csp);
					}
					return sequences;
	
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
						
		});
	}

	@Override
	public ArrayList<String> getCurrentClassSchedule(final String username) {
		return executeTransaction(new Transaction<ArrayList<String>>() {
			@Override
			public ArrayList<String> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;					
				
				try {	
					
					User user = new User();
					user = getUser(username);
					int userId = user.getId();
					
					stmt = conn.prepareStatement("select currentClasses.nameAndInfo from currentClasses where currentClasses.userId=?");	
					stmt.setInt(1, userId);
					resultSet = stmt.executeQuery();
					
					ArrayList<String> schedule = new ArrayList<String>();
					
					int index = 1;
					
					while(resultSet.next()) {						
						schedule.add(resultSet.getString(index++));						
					}
					return schedule;
	
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
						
		});
	}

	@Override
	public boolean addClassToSchedule(String username, final String classInfo,
			final String courseName) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				User user = new User();
				int userId = user.getId();
				
				try {
					stmt = conn.prepareStatement(
							"insert into currentClasses (userId, nameAndInfo, courseName)" +
									" values (?, ?, ?)",
									PreparedStatement.RETURN_GENERATED_KEYS
							);
					stmt.setInt(1, userId);
					stmt.setString(2, classInfo);
					stmt.setString(3, courseName);
					stmt.executeUpdate();
					return true;
					
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(conn);
				}				
			}			
		});
	}

	@Override
	public boolean removeClassFromSchedule(final String username, final String courseName) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				User user = new User();
				user = getUser(username);
				int userId = user.getId();
				
				try {
					stmt = conn.prepareStatement("delete from currentClasses where currentClasses.userId=? and currentClasses.courseName=?");
					stmt.setInt(1, userId);
					stmt.setString(2, courseName);
					
					stmt.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(conn);					
					DBUtil.closeQuietly(stmt);
				}
				
			}
		});
	}

	public static void main(String[] args) {
		DerbyDatabase db = new DerbyDatabase();
		System.out.println("Creating tables...");
		//TODO: alter tables created - 
		db.createTables();
		System.out.println("Loading initial data...");
		try {
			db.loadInitialData();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		System.out.println("Done!");
	}

	private void loadInitialData() throws IOException {
		UserParser up = new UserParser();
		CourseParser cp = new CourseParser();
		AdvisorParser ap = new AdvisorParser();
		CategoryParser cap = new CategoryParser();
		DepartmentParser dp = new DepartmentParser();
		MajorParser mp = new MajorParser();
		CourseSequenceParser csp = new CourseSequenceParser();
		
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<Course> courses = new ArrayList<Course>();
		ArrayList<String> majors = new ArrayList<String>();
		ArrayList<Advisor> advisors = new ArrayList<Advisor>();
		ArrayList<String> departments = new ArrayList<String>();
		ArrayList<String> categories = new ArrayList<String>();
		ArrayList<CourseSequencePairs> courseSequences = new ArrayList<CourseSequencePairs>();
		
		File userFile = new File("usersFile.txt");
		File coursesFile = new File("courseFile.txt");
		File majorFile = new File("majorsFile.txt");
		File advisorFile = new File("advisorFile.txt");
		File departmentFile = new File("departmentsFile.txt");
		File categoryFile = new File("categoryFile.txt");
		File courseSequenceFile = new File("courseSequenceFile.txt");
		
		users = up.parseUsers(userFile);
		courses = cp.parseCoursesFromFile(coursesFile);
		advisors = ap.parseAdvisors(advisorFile);
		categories = cap.parseCategories(categoryFile);
		majors = mp.parseMajors(majorFile);
		departments = dp.parseDepartments(departmentFile);
		courseSequences = csp.parseCourseSequences(courseSequenceFile);
		
		// make sure parsers worked
		
		for(User user : users) {
			System.out.println(user.getUsername());			
		}
		
		for(Course course : courses) {
			System.out.println(course.getName());
		}
		
		for(String major : majors) {
			System.out.println(major);
		}
		
		for(String department : departments) {
			System.out.println(department);
		}
		
		for(String category : categories) {
			System.out.println(category);
		}
		
		for(Advisor advisor : advisors) {
			System.out.println(advisor.getName());
		}
		
		for(CourseSequencePairs pairs : courseSequences) {
			System.out.println(pairs.getCourseName());
		}
		
		
		// make sure adding works
		
		for(User user : users) {
			this.addUser(user);
			System.out.println("added " + user.getUsername());
		}
		
		for(Course course : courses) {
			this.addCourse(course);
			System.out.println("added " + course.getName());
		}
		
		for(String major : majors) {	
			this.addMajor(major);
			System.out.println("added " + major);
		}
		
		for(String department : departments) {
			this.addDepartment(department);
			System.out.println("added " + department);
		}
		
		for(String category : categories) {
			this.addCategory(category);
			System.out.println("added " + category);
		}
		
		for(Advisor advisor : advisors) {
			this.addAdvisor(advisor);
			System.out.println("added " + advisor.getName());
		}
		
		for(CourseSequencePairs pairs : courseSequences) {
			this.addCourseSequencePair(pairs);
			System.out.println("added sequence " + pairs.getSequenceName() + " and course " + pairs.getCourseName());
		}

	}

	private void loadUser(User user, ResultSet resultSet, int index) throws SQLException {
		user.setId(resultSet.getInt("id"));
		user.setUsername(resultSet.getString("username"));
		user.setPassword(resultSet.getString("password"));
		user.setName(resultSet.getString("name"));
		user.setEmailAddress(resultSet.getString("emailAddress"));
		user.setMajor(resultSet.getString("major"));
		user.setMaxCredits(resultSet.getInt("maxCredits"));
	}

	private void loadAdvisor(Advisor advisor, ResultSet resultSet, int index) throws SQLException {
		advisor.setId(resultSet.getInt("id"));
		advisor.setName(resultSet.getString("name"));
		advisor.setDepartment(resultSet.getString("department"));
		advisor.setLocation(resultSet.getString("location"));
		advisor.setEmail(resultSet.getString("email"));
		advisor.setPhone(resultSet.getString("phone"));				
	}
	private void loadCourses(Course course, ResultSet resultSet, int index) throws SQLException {
		course.setId(resultSet.getInt("id"));
		course.setStartTime(resultSet.getString("startTime"));
		course.setEndTime(resultSet.getString("endTime"));
		course.setPrereq_id(resultSet.getInt("prereq_id"));
		course.setInstructor(resultSet.getString("instructor"));
		course.setLocation(resultSet.getString("location"));
		course.setName(resultSet.getString("name"));
		course.setCategory(resultSet.getString("category"));
		course.setType(resultSet.getString("type"));
		course.setLevel(resultSet.getInt("level"));
		course.setSemester(resultSet.getInt("semester"));		
	}
	
	private void loadSequences(CourseSequencePairs csp, ResultSet resultSet, int index) throws SQLException {
		csp.setSequenceName(resultSet.getString("sequenceName"));
		csp.setCourseName(resultSet.getString("courseName"));
		csp.setYearNum(resultSet.getInt("yearNum"));
		csp.setCredits(resultSet.getInt("credits"));
		csp.setPrereq(resultSet.getString("prereq"));		
	}

	@Override
	public ArrayList<Course> getCoursesTakenByUser(final String username) {
		return executeTransaction(new Transaction<ArrayList<Course>>() {
			@Override
			public ArrayList<Course> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;					

				System.out.println("###################### username in getCourses: " + username);
				
				User u = getUser(username);
				int userID = u.getId();

				try {					
					stmt = conn.prepareStatement("select courseUserLinks.courseId from courseUserLinks where courseUserLinks.userId= ?");
					stmt.setInt(1, userID);
					resultSet = stmt.executeQuery();
					
					ArrayList<Integer> courseIds = new ArrayList<Integer>();
					
					
					while(resultSet.next()) {
						System.out.println("Found actual results for user " + username);
						courseIds.add(resultSet.getInt("courseId"));
					}
					
					ArrayList<Course> courses = new ArrayList<Course>();
					
					for(int i = 0; i < courseIds.size(); i++) {
						
						System.out.println("Found actual results for user " + username);
						stmt = conn.prepareStatement("select courses.* from courses where courses.id= ?");
						stmt.setInt(1, courseIds.get(i));
						resultSet = stmt.executeQuery();
						
						while(resultSet.next()) {
							Course course = new Course();
							loadCourses(course, resultSet, 1);
							courses.add(course);
						}
					}
					
					return courses;
	
				} finally {
					DBUtil.closeQuietly(conn);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}			
		});
	}

	@Override
	public boolean addMajor(final String major) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet generatedKeys = null;

				try {
					stmt = conn.prepareStatement(
							"insert into majors (major)" +
									" values (?)",
									PreparedStatement.RETURN_GENERATED_KEYS
							);					
					
					stmt.setString(1, major);

					try {
						stmt.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
						return false;
					}

					generatedKeys = stmt.getGeneratedKeys();
					if (!generatedKeys.next()) {
						throw new SQLException("Could not get auto-generated key for inserted Major");
					}					
					
					return true;
				} finally {
					DBUtil.closeQuietly(conn);					
					DBUtil.closeQuietly(stmt);
				}
			}
		});		
	}

	@Override
	public boolean addCategory(final String category) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet generatedKeys = null;

				try {
					stmt = conn.prepareStatement(
							"insert into classCategories (category)" +
									" values (?)",
									PreparedStatement.RETURN_GENERATED_KEYS
							);					
					
					stmt.setString(1, category);

					try {
						stmt.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
						return false;
					}

					generatedKeys = stmt.getGeneratedKeys();
					if (!generatedKeys.next()) {
						throw new SQLException("Could not get auto-generated key for inserted Category");
					}					
					
					return true;
				} finally {
					DBUtil.closeQuietly(conn);					
					DBUtil.closeQuietly(stmt);
				}
			}
		});	
	}

	@Override
	public boolean addDepartment(final String department) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet generatedKeys = null;

				try {
					stmt = conn.prepareStatement(
							"insert into departments (name)" +
									" values (?)",
									PreparedStatement.RETURN_GENERATED_KEYS
							);					
					
					stmt.setString(1, department);

					try {
						stmt.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
						return false;
					}

					generatedKeys = stmt.getGeneratedKeys();
					if (!generatedKeys.next()) {
						throw new SQLException("Could not get auto-generated key for inserted department");
					}					
					
					return true;
				} finally {
					DBUtil.closeQuietly(conn);					
					DBUtil.closeQuietly(stmt);
				}
			}
		});	
	}

	@Override
	public boolean addCourseSequencePair(final CourseSequencePairs cspair) {
		return executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet generatedKeys = null;

				try {
					stmt = conn.prepareStatement(
							"insert into courseSequences (sequenceName, courseName, yearNum, credits, prereq)" +
									" values (?, ?, ?, ?, ?)",
									PreparedStatement.RETURN_GENERATED_KEYS
							);		
					


					int index = 1;
					stmt.setString(index++, cspair.getSequenceName());
					stmt.setString(index++, cspair.getCourseName());
					stmt.setInt(index++, cspair.getYearNum());
					stmt.setInt(index++, cspair.getCredits());
					stmt.setString(index++, cspair.getPrereq());

					try {
						stmt.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
						return false;
					}

					generatedKeys = stmt.getGeneratedKeys();
					if (!generatedKeys.next()) {
						throw new SQLException("Could not get auto-generated key for inserted CSequence Pair");
					}

					//TODO: set id of pair?					
					return true;
				} finally {
					DBUtil.closeQuietly(conn);					
					DBUtil.closeQuietly(stmt);
				}
			}
		});		
	}
	
}
