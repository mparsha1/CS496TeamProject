package edu.ycp.cs.cs496.collegeplanner.model.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
							"courseId integer," +
							")");
					
					//TODO: Create other tables!!!
					
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
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		return false;
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
	public String getPasswordOfUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeUsernameOfUser(String username, String newUsername) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addAdvisor(Advisor advsr) {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getAdvisorDepartments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Advisor> getAdvisorsByDepartment(String department) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNameOfUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getMajors() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
}
