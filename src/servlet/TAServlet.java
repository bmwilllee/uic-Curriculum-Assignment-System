package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import database.Mydb;
import table.Course;
import table.Table;

/**
 * Servlet implementation class TAServlet
 */
@WebServlet("/TAServlet")
public class TAServlet extends HttpServlet {
	ArrayList<Course> courses = new ArrayList<Course>();
	Table table; // now, table is global value
	private static final long serialVersionUID = 1L;
	String tableRequest = ""; // now, tableTitle is global value
	ArrayList<String> allTa = new ArrayList<String>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TAServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String msg = "";
		tableRequest = request.getParameter("tableName");
		System.out.println(tableRequest);
		try {
			courses = Mydb.loadTable(tableRequest);
		} catch (Exception e) {
			System.out.println("create courses list in taServelet failud...");
		}
		try {
			table = new Table(tableRequest, courses);
			String tableHead = "<table class=\"table table-hover\">\n" + 
					"                    <thead>\n" + 
					"                        <tr>\n" + 
					"                            <th>Subject</th>\n" + 
					"                            <th>Group</th>\n" + 
					"                            <th>Student</th>\n" + 
					"                            <th>Instrcutor</th>\n" + 
					"                            <th>TA 1</th>\n" + 
					"                            <th>TA 2</th>\n" + 
					"                            <th>Time 1</th>\n" + 
					"                            <th>Time 2</th>\n" + 
					"                        </tr>\n" + 
					"                    </thead>\n" + 
					"                    <tbody>";
			String tableTail = "</tbody>\n" + 
					"            </table>";
			String tableBody = "";
			String taTD = "";
			allTa = Mydb.loadTA();
			
			for(int j=0; j<allTa.size(); j++) {
				taTD += "                                		<option value=\"" + allTa.get(j) + "\" id=\"" + String.valueOf(j+1) + "\">" + allTa.get(j) + "</option>\n";
			}
			for(int i=0; i< table.courseSize(); i++) {
				tableBody += "<tr>\n" + 
						"                            <td>" + table.getCourses().get(i).getCourseTitle() +"</td>\n" +
						"                            <td>" + table.getCourses().get(i).getGroup() + "</td>\n" +
						"                            <td>" + table.getCourses().get(i).getStudent() + "</td>\n" +
						"                            <td>" + table.getCourses().get(i).getInstructor() + "</td>\n" + 
						"                            <td>\n" + 
						"                                <select name=\"" + String.valueOf(i) + "_ta1\" class=\"form-control\">\n" +
						"                                	<option value='0'>None</option>" + taTD + 
						"                                </select>\n" + 
						"                            </td>\n" + 
						"                            <td>\n" + 
						"                                <select name=\"" + String.valueOf(i) + "_ta1\" class=\"form-control\">\n" +
						"                                	<option value='0'>None</option>" + taTD + 
						"                                </select>\n" + 
						"                            </td>\n" + 
						"                            <td><input type=\"text\" name=\"" + String.valueOf(i) + "_time1\"" + " class=\"form-control\" placeholder=\"Monday 14:00-15:50\"/></td>\n" + 
						"                            <td><input type=\"text\" name=\"" + String.valueOf(i) + "_time2\"" + " class=\"form-control\" placeholder=\"Monday 14:00-15:50\"/></td>\n" + 
						"</tr>";
				
			}
			msg = (tableHead + tableBody + tableTail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String msg3 = "<button type=\"submit\" class=\"btn btn-success\">Save</button>";
		String msg4 = "null";
		
		request.setAttribute("msg1", msg); // add table to request
		request.setAttribute("msg2", tableRequest); // add tableTitle to request
		request.setAttribute("msg3", msg3); // add save button into request
		request.setAttribute("msg4", msg4);
		request.setAttribute("taNum", allTa.size());
		
		
		request.getRequestDispatcher("/taEditor.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> upDateCourse =  new ArrayList<String>();
		String sql = "";
		try {
			for(int i=0; i<table.courseSize(); i++) {
				String ta1 = String.valueOf(i) + "_ta1";
				String ta2 = String.valueOf(i) + "_ta2";
				String time1 = String.valueOf(i) + "_time1";
				String time2 = String.valueOf(i) + "_time2";
				sql = Mydb.createSaveSql(tableRequest, table.getCourses().get(i).getCourseTitle(), request.getParameter(ta1), request.getParameter(ta2), request.getParameter(time1), request.getParameter(time2)) + "\n";
				upDateCourse.add(sql);
			}
			System.out.println("create update statement success!\n");
		}catch (Exception e) {
			System.out.println("create uptate failed...\n");
			e.printStackTrace();
		}
		
		try {
			Mydb.ExeMultiSql(upDateCourse);
			Mydb.updateProcess(tableRequest, 2);
		}catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<String> tableList = Mydb.readMission("1");
		request.setAttribute("meg4", tableList);
		
		// 执行request之前还应讲process中该表的值设为1
		
		request.getRequestDispatcher("/taEditor.jsp").forward(request, response); // 跳转回该页
	}

}
