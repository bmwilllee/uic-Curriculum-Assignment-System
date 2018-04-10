package servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import database.Mydb;
import table.Course;
import table.Table;

/**
 * Servlet implementation class TAPreviewServlet
 */
@WebServlet("/TAPreviewServlet")
public class TAPreviewServlet extends HttpServlet {
	ArrayList<Course> courses = new ArrayList<Course>();
	Table table;
	String tableRequest = "";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TAPreviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String msg = "";
		tableRequest = request.getParameter("tableName");
		System.out.println(tableRequest);
		try {
			courses = Mydb.loadPreviewTable(tableRequest);
			
		} catch (Exception e) {
			e.printStackTrace();
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
			for(int i=0; i< table.courseSize(); i++) {
				tableBody += "<tr>\n" + 
						"                            <td>" + table.getCourses().get(i).getCourseTitle() +"</td>\n" +
						"                            <td>" + table.getCourses().get(i).getGroup() + "</td>\n" +
						"                            <td>" + table.getCourses().get(i).getStudent() + "</td>\n" +
						"                            <td>" + table.getCourses().get(i).getInstructor() + "</td>\n" + 
						"                            <td>" + table.getCourses().get(i).getTa1() + "</td>\n" + 
						"                            <td>" + table.getCourses().get(i).getTa2() + "</td>\n" + 
						"                            <td>" + table.getCourses().get(i).getTime1() + "</td>\n" + 
						"                            <td>" + table.getCourses().get(i).getTime2() + "</td>\n" + 
						"</tr>";
				
			}
			msg = (tableHead + tableBody + tableTail);
			
		} catch (Exception e) {
			System.out.println("creat table falied...\n");
			e.printStackTrace();
		}
		String msg3 = "<form role=\"form\" action=\"/workshop_beta_online/TAPreviewServlet\" method=\"post\">\n" + 
				"            		<div class=\"col-xs-4 col-sm-12 col-md-6\" style=\"width:105px; margin:3px 3px 0px 0px\">\n" + 
				"            			<button type=\"submit\" class=\"btn btn-danger\" name=\"Delete\" style=\"width:100%\">Delect</button>\n" + 
				"            		</div>\n" + 
				"            	</form>\n" + 
				"            	<form role=\"form\" action=\"/workshop_beta_online/TAPreviewServlet\" method=\"post\">\n" + 
				"            		<div class=\"col-xs-4 col-sm-12 col-md-6\" style=\"width:105px; margin:3px 3px 0px 0px\">\n" + 
				"            			<button type=\"submit\" class=\"btn btn-success\" name=\"Export\" style=\"width:100%\">Export</button>\n" + 
				"            		</div>\n" + 
				"            	</form>\n" + 
				"            	<form role=\"form\" action=\"/workshop_beta_online/TAPreviewServlet\" method=\"post\">\n" + 
				"            		<div class=\"col-xs-4 col-sm-12 col-md-6\" style=\"width:105px; margin:3px 3px 0px 0px\">\n" + 
				"            			<button type=\"submit\" class=\"btn btn-default btn-primary\" name=\"Edit\" style=\"width:100%\">Edit</button>\n" + 
				"            		</div>\n" + 
				"            	</form>";
		request.setAttribute("msg3", msg3);
		request.setAttribute("msg1", msg);
		request.setAttribute("msg2", tableRequest);
		request.getRequestDispatcher("/taPreview.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> parameters = request.getParameterMap();
		if(parameters.containsKey("Delete")) {
			String sql1 = "DELETE * FROM plan WHERE Semester = '" + tableRequest + "'";
			String sql2 = "DELETE FROM progress WHERE Semester = '" + tableRequest + "'";
			try{
				Mydb.ExeSql(sql1);
				Mydb.ExeSql(sql2);
				System.out.println("Delete table success!\n");
				}catch (Exception e) {
					System.out.println("Delete table failed...\n");
					e.printStackTrace();
				}
			request.getRequestDispatcher("/taPreview.jsp").forward(request, response);
		}
		if(parameters.containsKey("Export")) {
			String fileName = tableRequest + ".xls";
			response.setHeader("Content-Disposition", "attachment; filename="+fileName);
			response.setContentType("application/vnd.ms-excel");
			OutputStream out = response.getOutputStream();
			HSSFWorkbook wb = ExportExcel.Export(table);
			try {
				out.flush();
		        wb.write(out);
		        out.close();
		        System.out.println("Export success");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(parameters.containsKey("Edit")) {
			try {
				Mydb.updateProcess(tableRequest, 1);
				System.out.println("Now you reEdit your table!\n");
			} catch (Exception e) {
				System.out.println("reEdit table failed...\n");
				e.printStackTrace();
			}
			request.getRequestDispatcher("/taPreview.jsp").forward(request, response);
		}
	}

}
