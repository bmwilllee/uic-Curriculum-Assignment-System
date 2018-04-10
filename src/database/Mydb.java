package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import table.Course;

import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Mydb {
	
	public static final String dbDriver = "com.mysql.jdbc.Driver";
	public static final String dbURL = "jdbc:mysql://localhost:3306/assign";
	public static final String dbURLRemote = "jdbc:mysql://172.16.162.17/k530003021";
	public static final String dbUser = "k530003021", dbPassword = "student";
	
	public Mydb() throws SQLException{
		super();
	}
	
	
	public static Connection conn;
	public static PreparedStatement statement = null;
	public static ResultSet resultSet = null;
	
	public static void dbConnect() {
		try {
			Class.forName(dbDriver);
			System.out.print("Success to on load to the satabase driver\n");
		} catch(ClassNotFoundException e) {
			System.out.println("Cannot access to the database driver\n");
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(dbURLRemote, dbUser, dbPassword);
			System.out.print("Success connect Mysql server!");
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Cannot to access to the Mysql server!\n");
		}
	}
	
	public static ArrayList<String> readMission(String celect){
		ArrayList<String> tableTitle = new ArrayList<String>();
		String sql = "SELECT Semester FROM progress WHERE Progress = " + "'" + celect + "'";
		try {
			Mydb.dbConnect();
			System.out.println("read all the missions for taHead\n");
			try {
				statement = conn.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					tableTitle.add(resultSet.getString(1));
				}
				
			}catch (Exception e) {
				System.out.println("Query mission failed...\n");
				e.printStackTrace();
			}
		}catch (Exception e) {
			e.printStackTrace();
			Mydb.dbClose();
		}
		Mydb.dbClose();
		return tableTitle;
	}
	
	public static ArrayList<Course> loadTable(String tableTitle){
		ArrayList<Course> courses = new ArrayList<Course>();
		String sql = "SELECT * FROM plan WHERE Semester = '" + tableTitle + "'";
		try {
			Mydb.dbConnect();
			System.out.println("\nload the current table for taHead");
			try {
				statement = conn.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					Course course = new Course(resultSet.getString("Courese"), resultSet.getInt("GNumber"), resultSet.getString("Students"), resultSet.getString("Instructor"));
					courses.add(course);
				}
				
			}catch (Exception e) {
				System.out.println("\n Load table failed...");
				e.printStackTrace();
			}
		}catch (Exception e) {
			e.printStackTrace();
			Mydb.dbClose();
		}
		Mydb.dbClose();
		return courses;
	}
	
	public static ArrayList<Course> loadPreviewTable(String tableTitle){
		ArrayList<Course> courses = new ArrayList<Course>();
		String sql = "SELECT * FROM plan WHERE Semester = '" + tableTitle + "'";
		System.out.println(tableTitle);
		try {
			Mydb.dbConnect();
			System.out.println("\nload the current Preview table for taHead");
			try {
				statement = conn.prepareStatement(sql);
				resultSet = statement.executeQuery();
				System.out.println(resultSet.getFetchSize());
				while (resultSet.next()) {
					Course course = new Course(resultSet.getString("Courese"), resultSet.getInt("GNumber"), resultSet.getString("Students"), resultSet.getString("Instructor"), resultSet.getString("TA1"), resultSet.getString("TA2"), resultSet.getString("Time1"), resultSet.getString("Time2"));
					courses.add(course);
				}
				
			}catch (Exception e) {
				System.out.println("\nLoad preview table failed...");
				e.printStackTrace();
			}
		}catch (Exception e) {
			Mydb.dbClose();
			e.printStackTrace();
		}
		Mydb.dbClose();
		return courses;
	}
	
	public static ArrayList<String> loadTA(){
		ArrayList<String> taList = new ArrayList<String>();
		String sql = "SELECT Name FROM ta";
		try {
			Mydb.dbConnect();
			System.out.println("\n load the taList for taHead");
			try {
				statement = conn.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					taList.add(resultSet.getString(1));
				}
			}catch (Exception e) {
				System.out.println("\n Load taList failed...");
				e.printStackTrace();
			}
		}catch (Exception e) {
			Mydb.dbClose();
		}
		Mydb.dbClose();
		return taList;
	}
	
	public static String createSaveSql(String tableTitle, String courseName, String ta1, String ta2, String time1, String time2) {
		String sql = "UPDATE plan SET TA1='" + ta1 + "', TA2='" + ta2 + "', Time1='" + time1 + "', Time2='" + time2 + "' WHERE Semester='" + tableTitle + "'AND Courese='" + courseName + "'";
		return sql;
	}
	
	public static void updateProcess(String tableName, int index) {
		String sql = "UPDATE progress SET Progress=" + String.valueOf(index) + " WHERE Semester = '" + tableName + "'";
		Mydb.ExeSql(sql);
	}
	
	public static void ExeSql(String sql) {
		try {
			Mydb.dbConnect();
			try {
				statement = conn.prepareStatement(sql);
				statement.executeUpdate();
				System.out.println("Update table for TA success!\n");
			}catch (Exception e) {
				System.out.println("\n update table failed...");
				e.printStackTrace();
			}
		}catch (Exception e) {
			e.printStackTrace();
			Mydb.dbClose();
		}
		Mydb.dbClose();
	}
	
	public static void ExeMultiSql(ArrayList<String> sqlSet) {
		try {
			Mydb.dbConnect();
			try {
				for(int i=0; i<sqlSet.size(); i++) {
					statement = conn.prepareStatement(sqlSet.get(i));
					statement.executeUpdate();
				}
				System.out.println("exe multiple sql success!\n");
			}catch (Exception e) {
				System.out.println("exe multiple sql falied...\n");
				e.printStackTrace();
			}
		}catch (Exception e) {
			e.printStackTrace();
			Mydb.dbClose();
		}
		Mydb.dbClose();
	}
	
	public static void dbClose() {
		try {
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
