package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class DataBaseAccess {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://172.16.162.17/k530003021";
	static final String USER ="k530003021";
	static final String PASS = "student";
	static 	Connection conn = null;
	static  Statement stmt = null;
	
	/*
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	*/
	
	public String Dlog(String id, String pass) throws Exception{
		//Connection conn = null;
		//Statement stmt = null;
		
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			System.out.println("connecting");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			System.out.println("Innitialize");
			stmt = conn.createStatement();
			String sql = "SELECT id, Password, Type FROM `log` WHERE 1";
			ResultSet resultSet = stmt.executeQuery(sql);
			
			while (resultSet.next()){
				String reid = resultSet.getString("id");
				String repass = resultSet.getString("Password");
				String Type = resultSet.getString("Type");
						
				if(reid.equals(id)  && repass.equals(pass)  ) {
					System.out.println("log success"+" with account: "+ id);
					return Type;
				}
					
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			
			try {
				if(stmt!= null) stmt.close();
				
			}catch(SQLException se2) {}
			
			try {
				if(conn!=null) conn.close();
			}catch(SQLException se) {
				se.printStackTrace();
			}
		}	
		return "error";

	}
	
	public String DlogName(String id, String pass) throws Exception{
		//Connection conn = null;
		//Statement stmt = null;
		
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			System.out.println("connecting");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			System.out.println("Innitialize");
			stmt = conn.createStatement();
			String sql = "SELECT id, Password, Name FROM `log` WHERE 1";
			ResultSet resultSet = stmt.executeQuery(sql);
			
			while (resultSet.next()){
				String reid = resultSet.getString("id");
				String repass = resultSet.getString("Password");
				String name = resultSet.getString("Name");
						
				if(reid.equals(id)  && repass.equals(pass)  ) {
					System.out.println("log success"+" with account: "+ id);
					return name;
				}
					
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			
			try {
				if(stmt!= null) stmt.close();
				
			}catch(SQLException se2) {}
			
			try {
				if(conn!=null) conn.close();
			}catch(SQLException se) {
				se.printStackTrace();
			}
		}	
		return "error";

	}
	
	public String CourseAdd(String subject, String student, String type, int number) throws Exception {

		try {
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		System.out.println("connecting");
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
		
		System.out.println("Innitialize");
		stmt = conn.createStatement();
		String sql = "INSERT into courses(Subject, Students, Type, GNumber) VALUES('"+subject+"','"+student+"','"+type+"','"+number+"')";
		System.out.println(sql);
		
		stmt.execute(sql);
				
		} catch (Exception e) {
			throw e;
		} finally {
			
			try {
				if(stmt!= null) stmt.close();
				
			}catch(SQLException se2) {}
			
			try {
				if(conn!=null) conn.close();
			}catch(SQLException se) {
				se.printStackTrace();
			}
		}	
		System.out.println("add Course success");
		return "success";
	
		
	}
	
	public String CourseDele(String subject) throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			System.out.println("connecting");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			System.out.println("Innitialize");
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		String sql = "		DELETE FROM `courses` WHERE subject='"+subject+"'";
		stmt.execute(sql);
		return "Course delete success";

	}
	
	public String StaffAdd(String name, String type) throws SQLException {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			System.out.println("connecting");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			System.out.println("Innitialize");
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		if(type.equals("ta")) {
			String sql = "INSERT into ta(Name) VALUES('"+name+"')";
			stmt.execute(sql);
			return "ta added success";
			
		}else if(type.equals("te")) {
			String sql = "INSERT into teacher(Name) VALUES('"+name+"')";
			stmt.execute(sql);
			return "te added success";
		}
		return "error";
	}
	
	public String StaffDele(String name, String type) throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			System.out.println("connecting");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			System.out.println("Innitialize");
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(type.equals("ta")) {
			String sql = "DELETE FROM `ta` WHERE Name='"+name+"'";
			stmt.execute(sql);
			return "ta added success";
			
		}else if(type.equals("te")) {
			String sql = "DELETE FROM `teacher` WHERE Name='"+name+"'";
			stmt.execute(sql);
			return "te added success";
		}
		
		return "error";
	}
	// You need to close the resultSet

	public static void Progress(String semester, String string) throws Exception {
		
		// TODO Auto-generated method stub
		
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			System.out.println("connecting");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			System.out.println("Innitialize");
			stmt = conn.createStatement();
			String sql = "INSERT into Progress(Semester, Progress) VALUES('"+semester+"','"+string+"')";
			stmt.execute(sql);
					
			} catch (Exception e) {
				throw e;
			} finally {
				
				try {
					if(stmt!= null) stmt.close();
					
				}catch(SQLException se2) {}
				
				try {
					if(conn!=null) conn.close();
				}catch(SQLException se) {
					se.printStackTrace();
				}
			}	
			System.out.println("Progress success");
		
	}

	public static void CreateTable(String semester, String[] Course, String[] select) throws Exception {
		// TODO Auto-generated method stub
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			System.out.println("connecting");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			System.out.println("Innitialize");
			stmt = conn.createStatement();
			for(int i=0;Course[i]!=null;i++) 
			{
				System.out.print(Course[i]);
				System.out.println(select[i]);
				String test1=Course[i];
				String test2=select[i];
				String sql = "INSERT into Plan(Semester, Courese,Instructor) VALUES('"+semester+"','"+test1+"','"+test2+"')";
				stmt.execute(sql);
			}
						
			} catch (Exception e) {
				throw e;
			} finally {
				
				try {
					if(stmt!= null) stmt.close();
					
				}catch(SQLException se2) {}
				
				try {
					if(conn!=null) conn.close();
				}catch(SQLException se) {
					se.printStackTrace();
				}
			}	
			System.out.println("Progress success");
		
	}

	public static String[] searchGNumber(String[] course, int size3) throws ClassNotFoundException, SQLException{
		// TODO Auto-generated method stub
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		System.out.println("connecting");
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
		System.out.println("Innitialize");
		stmt = conn.createStatement();
		String[] GNumber=new String[size3];
		String[] Students=new String[size3];
		
		for(int h=0;h<size3;h++)
		{
			String sql="SELECT GNumber,Students from courses where Subject='"+course[h]+"'";
			ResultSet rs=stmt.executeQuery(sql);
			
			while(rs.next()) 
			{				
				GNumber[h]=rs.getString("GNumber")+"";
				Students[h]=rs.getString("Students")+"";
				System.out.println(GNumber[h]);
				System.out.println(Students[h]);
				
			}
			rs.close();
		}
		
        System.out.println("\n");
		System.out.println("Tifanaire is handsome searchGNumber");
		System.out.println("Progress success");
		System.out.println("\n");
		return GNumber;
		}

	public static String[] searchStudents(String[] test, int size1) throws ClassNotFoundException, SQLException
	{
		// TODO Auto-generated method stub
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		System.out.println("connecting");
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
		System.out.println("Innitialize");
		stmt = conn.createStatement();
		String[] GNumber=new String[size1];
		String[] Students=new String[size1];
		
		for(int h=0;h<size1;h++)
		{
			String sql="SELECT GNumber,Students from courses where Subject='"+test[h]+"'";
			ResultSet rs=stmt.executeQuery(sql);
			
			while(rs.next()) 
			{				
				GNumber[h]=rs.getString("GNumber")+"";
				Students[h]=rs.getString("Students")+"";
			}
			rs.close();
		}
		return Students;
	}

	public static void CreateTable(String semester, ArrayList<String> courseFinal, ArrayList<Integer> groupFinal, ArrayList<String> studentFinal,
			String[] select) throws ClassNotFoundException, SQLException {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			System.out.println("connecting");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			System.out.println("Innitialize");
			stmt = conn.createStatement();
			for(int i=0;i<courseFinal.size();i++) 
			{
				stmt.execute("INSERT INTO `plan`(`Semester`, `Courese`, `GNumber`, `Students`, `Instructor`, `TA1`, `TA2`) VALUES ('"+semester+"','"+courseFinal.get(i)+"','"+groupFinal.get(i)+"','"+studentFinal.get(i)+"','"+select[i]+"',null,null)");
			}
						
			} catch (Exception e) {
				throw e;
			} finally {
				
				try {
					if(stmt!= null) stmt.close();
					
				}catch(SQLException se2) {}
				
				try {
					if(conn!=null) conn.close();
				}catch(SQLException se) {
					se.printStackTrace();
				}
			}	
			System.out.println("Progress success");
	}

	public static void Workload(String semester, String[] oriteacher, String[] demo) throws Exception {
		// TODO Auto-generated method stub
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();

			for(int p=0;p<oriteacher.length;p++) 	
			{
				if(demo[p].equals("0")) 
				{
					continue;
				}
				stmt.execute("INSERT INTO `workload`(`term`, `lector`, `workload`) VALUES ('"+semester+"','"+oriteacher[p]+"','"+demo[p]+"')");
			}
						
			} catch (Exception e) {
				throw e;
			} finally {
				
				try {
					if(stmt!= null) stmt.close();
					
				}catch(SQLException se2) {}
				
				try {
					if(conn!=null) conn.close();
				}catch(SQLException se) {
					se.printStackTrace();
				}
			}	
	}

		public void updateTable(String semaster, String[] course, String[] TA1, String[] TA2, String[] Time1, String[] Time2) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		System.out.println("connecting");
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
		System.out.println("Innitialize");
		stmt = conn.createStatement();
		
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			System.out.println("connecting");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			System.out.println("Innitialize");
			stmt = conn.createStatement();
			for(int i=0;i<course.length;i++) 
			{
				stmt.execute("UPDATE `plan` SET `TA1`='"+TA1[i]+"',`TA2`='"+TA2[i]+"',`Time1`='"+Time1[i]+"',`Time2`='"+Time2[i]+"' WHERE Semester='"+semaster+"' and Courese='"+course[i]+"'");
			}
						
			} catch (Exception e) {
				throw e;
			} finally {
				
				try {
					if(stmt!= null) stmt.close();
					
				}catch(SQLException se2) {}
				
				try {
					if(conn!=null) conn.close();
				}catch(SQLException se) {
					se.printStackTrace();
				}
			}	
			System.out.println("Progress success");
		
	}

	public String[] searchCourse(String semaster, int size1) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		System.out.println("connecting");
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
		System.out.println("Innitialize");
		stmt = conn.createStatement();
		String[] course=new String[size1];
		int h=0;
		
		String sql="SELECT courese from plan where Semester='"+semaster+"'";
		ResultSet rs=stmt.executeQuery(sql);
			
		while(rs.next()) 
		{				
			course[h]=rs.getString("courese")+"";	
			h++;
		}
		rs.close();
	    System.out.println("\n");
	    System.out.println("Tifanaire is handsome searchWorkload");
		System.out.println("Progress success");
		System.out.println("\n");
		
		return course;
	}

	public void updateProcess(String semaster) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		System.out.println("connecting");
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
		System.out.println("Innitialize");
		stmt = conn.createStatement();
		stmt.execute("UPDATE `progress` SET `Progress`='2' WHERE Semester='"+semaster+"'");
	}
	
}
