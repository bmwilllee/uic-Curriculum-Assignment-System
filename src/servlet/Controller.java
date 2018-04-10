package servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DataBaseAccess;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Servlet implementation class Controller
 */

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String forward="";
		// Get a map of the request parameters
		
		Map<String, String[]> parameters = request.getParameterMap();
		DataBaseAccess DataBaseAccess = new DataBaseAccess();
		
		
		if (parameters.containsKey("login")){
			//Log in page index.html
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			try {
				String re = DataBaseAccess.Dlog(id, password); 
				String name = DataBaseAccess.DlogName(id, password); 
				if(re.equals("D")) {
					//if the user is teacher
					//request.setAttribute("name", name);
					//request.getRequestDispatcher("/CourseSelect.jsp").forward(request, response); 
					request.getSession().setAttribute("name", name);
					response.sendRedirect("/workshop_beta_online/CourseSelect.jsp");
				}else if(re.equals("A")) {
					//if the user is TA
					request.getSession().setAttribute("name", name);
					response.sendRedirect("/workshop_beta/taEditor.jsp");
				}else if(re.equals("error")) {
					response.sendRedirect("/workshop_beta_online/index.jsp");	
				} 
									
					System.out.println(re); //error collection
					
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(parameters.containsKey("courseadd")) {
			//function to add courses, PDmanagement.jsp
			String subject = request.getParameter("subject");
			String student = request.getParameter("student");
			int number = Integer.parseInt(request.getParameter("group"));
			String type = request.getParameter("type");
			
			if(subject.equals("") || student.equals("") ) {
				request.getRequestDispatcher("/PDManagement.jsp").forward(request, response); 
				}else 
			
			try {
				DataBaseAccess.CourseAdd(subject, student, type, number);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("/PDManagement.jsp").forward(request, response); 
					
		}else if(parameters.containsKey("teadd")) {
			//function to add teachers, PDmanagement.jsp
			String name = request.getParameter("name");
			
			if(name.equals("")) {
				request.getRequestDispatcher("/PDManagement.jsp").forward(request, response); 
				}else 
					
			try {
				String re= DataBaseAccess.StaffAdd(name, "te");
				System.out.println(re);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("/PDManagement.jsp").forward(request, response); 
			
		}else if(parameters.containsKey("taadd")) {
			//function to add ta, TAManagement.jsp
			String name = request.getParameter("name");
			if(name.equals("")) {
				request.getRequestDispatcher("/TAManagement.jsp").forward(request, response);
				}else 
			
			try {
				
				String re= DataBaseAccess.StaffAdd(name, "ta");
				System.out.println(re);
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("/TAManagement.jsp").forward(request, response);
			
		}else if(parameters.containsKey("teDelete")) {
			//function to delete teacher, PDmanagement.jsp
			String name = request.getParameter("teDelete");
			try {
				String re= DataBaseAccess.StaffDele(name, "te");
				System.out.println(re);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("/PDManagement.jsp").forward(request, response); 
			
		}else if(parameters.containsKey("taDelete")) {
			//function to delete ta, TAManagement.jsp
			String name = request.getParameter("taDelete");
			try {
				String re= DataBaseAccess.StaffDele(name, "ta");
				System.out.println(re);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("/TAManagement.jsp").forward(request, response); 
			
		}else if(parameters.containsKey("CourseDelete")) {
			//function to delete Courses, PDmanagement.jsp
			System.out.println("Course delete");
			String subject = request.getParameter("CourseDelete");
			try {
				String re= DataBaseAccess.CourseDele(subject);
				System.out.println(re);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("/PDManagement.jsp").forward(request, response); 
		}
		else if(parameters.containsKey("TeaNext")) 
		{	
			String[] test=request.getParameterValues("courses");
			String[] Teacher=request.getParameterValues("teachers");
							
			int size1=java.lang.reflect.Array.getLength(test); 
			int size2=java.lang.reflect.Array.getLength(Teacher);
			
			try {
				String[] GNumber= DataBaseAccess.searchGNumber(test, size1);
				String[] Students=DataBaseAccess.searchStudents(test, size1);

				int size3=java.lang.reflect.Array.getLength(GNumber);
				int size4=java.lang.reflect.Array.getLength(Students);
				
				request.setAttribute("size4", size4);
				request.setAttribute("GNumber", GNumber);
				request.setAttribute("Students", Students);
				request.setAttribute("size2", size2);
				request.setAttribute("size1", size1);
				request.setAttribute("test", test);
				request.setAttribute("size3", size3);
				request.setAttribute("Teacher", Teacher);
				request.getRequestDispatcher("/instructorSelecte.jsp").forward(request, response); 	
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(parameters.containsKey("Confirm")) 
		{
			
			
			String semester=request.getParameter("Timetable");
			String[] select=request.getParameterValues("select");
			String[] Course = (String[]) request.getSession().getAttribute("temporary");
			String[] demo= (String[])request.getSession().getAttribute("demo");
			String[] Oriteacher = (String[]) request.getSession().getAttribute("Oriteacher");
			
			int sizeOfWorkload=java.lang.reflect.Array.getLength(Oriteacher); 
			
			String[] Finalworkload=new String[sizeOfWorkload];
			
			for(int b=0;b<Finalworkload.length;b++) 
			{
				Finalworkload[b]=demo[b+1];
			}
		
			ArrayList<String> CourseFinal = (ArrayList<String>) request.getSession().getAttribute("Course");
			ArrayList<String> StudentFinal = (ArrayList<String>) request.getSession().getAttribute("Student");
			ArrayList<Integer> GroupFinal = (ArrayList<Integer>) request.getSession().getAttribute("Group");
			ArrayList<String> TeacherFinal = (ArrayList<String>) request.getSession().getAttribute("Teacher");
			
			try {
				DataBaseAccess.Progress(semester,"1");
				DataBaseAccess.CreateTable(semester, CourseFinal, GroupFinal, StudentFinal,select);
				DataBaseAccess.Workload(semester,Oriteacher,Finalworkload);
				
				request.setAttribute("semester", semester);
				request.getRequestDispatcher("/pdPreview.jsp").forward(request, response); 	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
				else if(parameters.containsKey("TaNext")) 
		{
			String[] TA1=request.getParameterValues("select1");
			String[] TA2=request.getParameterValues("select2");
			String[] Time1=request.getParameterValues("Time1");
			String[] Time2=request.getParameterValues("Time2");
			String semaster = (String) request.getSession().getAttribute("semaster");
			
			int size1=TA1.length;

			for (int i = 0; i < TA1.length; i++) {  
				System.out.print(TA1[i]);
				System.out.print(TA2[i]);
				System.out.print(Time1[i]);
				System.out.println(Time2[i]);
				
            }  
			System.out.println(semaster);
			
			try {
				String[] course=DataBaseAccess.searchCourse(semaster,size1);
				for(int h=0;h<course.length;h++) 
				{
					System.out.println(course[h]);	
				}

				DataBaseAccess.updateTable(semaster,course,TA1, TA2, Time1, Time2);
				DataBaseAccess.updateProcess(semaster);
				
				request.setAttribute("semester", semaster);
				request.getRequestDispatcher("/taPreview.jsp").forward(request, response); 	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
		//RequestDispatcher view = request.getRequestDispatcher(forward);
		//view.forward(request, response);
	}
	
	

	
}
