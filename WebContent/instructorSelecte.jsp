<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap-theme.min.css"></script>
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- import echarts file-->

<title>InstructorSelect</title>
</head>

<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
         url="jdbc:mysql://172.16.162.17/k530003021"
         user="k530003021"  password="student"/>
         
<%
      int current;
      
      String[] temp1=(String[])request.getAttribute("test");
      String[] temp2=(String[])request.getAttribute("Teacher");
      String[] GNumber=(String[])request.getAttribute("GNumber");
      String[] Students=(String[])request.getAttribute("Students");
      String[] workload=new String[temp2.length];
      
      ArrayList<String> Course = new ArrayList<String>();
      ArrayList<String> Student=new ArrayList<String>();
      ArrayList<Integer> Group=new ArrayList<Integer>();
      ArrayList<String> Teacher=new ArrayList<String>();
      
      request.setAttribute("GNumber", GNumber);
      request.setAttribute("Students", Students);
      request.setAttribute("temp1",temp1);  
      request.setAttribute("temp2",temp2);
      
      int valueCount=1;
      int SelectCount=100;
      
      
      request.getSession().setAttribute("Course",Course);
      request.getSession().setAttribute("Student",Student);
      request.getSession().setAttribute("Group",Group);
      request.getSession().setAttribute("Teacher",Teacher);
      
      session.setAttribute("Oriteacher",temp2);
      session.setAttribute("temporary",temp1);
      
      int g=0;
      int t=0; 
      int cnt=0;
%>

          <sql:query dataSource="${snapshot}" var="result">      
	          SELECT Students, GNumber from courses;	         
          </sql:query>
 


<body>
         
<script type="text/javascript">
$(document).ready(function(){
    var oldVal=0;    
    var workload = new Array();
    workload[0]=0;
    var teacher=new Array();
    var course=new Array();
    
    <%   
    for(int i=0;i<temp2.length;i++)
    {%>
    workload[<%=i+1%>]=0;
    <%}%>
    
    $(".table-responsive select").change(function(){
       oldVal=$(this).attr("old");
       var _thisVal=$(this).find('option:selected').attr("id");
       var CurrentValue=$(this).find('option:selected').val();
       var Currentid=$(this).find('option:selected').parent().attr("id")-100;
       var CurrentCourse=$(this).find('option:selected').parent().parent().siblings('#currentCourse').text();
       var temp2=0;
       var temp3=0;
       var temp4=0;
       
       if(teacher.length == 0)
       {
    	   teacher[Currentid]=CurrentValue;
    	   course[Currentid]=CurrentCourse;
    	   workload[oldVal]=workload[oldVal]-1;
           workload[_thisVal]=workload[_thisVal]+1;
       }
       
       else if(teacher.length != 0 && teacher[Currentid]==null)
       {
    	   teacher[Currentid]=CurrentValue;
    	   course[Currentid]=CurrentCourse;
    	   for(var i=0;i<Currentid;i++)
    	   {
    		   if(teacher[i]==teacher[Currentid]&&course[i]==course[Currentid])
    		   {
    			   workload[_thisVal]=workload[_thisVal]+0.5;
    			   break;
    		   }
    		   temp2++;
    	   }
    	   if(temp2==Currentid)
    	   {
        	   workload[_thisVal]=workload[_thisVal]+1;
    	   }
       }
       
       else if(teacher[Currentid]!=null)
       {
    	   for(var i=0;i<Currentid;i++)
    	   {
    		   if(teacher[i]==teacher[Currentid]&&course[i]==course[Currentid])
    		   {
    			   workload[oldVal]=workload[oldVal]-0.5;
    			   break;
    		   }
    		   temp3++;
    	   }
    	   if(temp3==Currentid)
    	   {
    		   workload[oldVal]=workload[oldVal]-1;
    	   }
    	   

    	   teacher[Currentid]=CurrentValue;
    	   course[Currentid]=CurrentCourse;

    	   
    	   for(var i=0;i<Currentid;i++)
    	   {
    		   if(teacher[i]==teacher[Currentid]&&course[i]==course[Currentid])
    		   {
    			   workload[_thisVal]=workload[_thisVal]+0.5;
    			   break;
    		   }
    		   temp4++;
    	   }
    	   if(temp4==Currentid)
    	   {
    		   workload[_thisVal]=workload[_thisVal]+1;
    	   }
       }
       
       var id=$(this).attr("id");
       if(workload[_thisVal]>6)
       {
    	   alert("The current teacher's workload is over 6!");
       }
       $(this).attr("old",_thisVal);
   })
   
	   $("#hahaha").click(function(){
	   $.ajax({
			type : "POST",
			url:"/workshop_beta/temp",
			data:{"workload":workload},
			traditional: true,
			success:function(result){
			}
		});
   })
})

</script>
	     

<form class="form-inline" action="Controller" method="POST">
  <div class="container" style="width:95%">
<!-- Title log out or admin -->
        <div class="row">
            <div class="col-xs-12 col-sm-6 col-md-8">
                <h1>Staff Assignment System</h1>
            </div>
        <div class="col-xs-6 col-md-4">
        <h4><small>
        			<%
		String name = (String)session.getAttribute("name");
		System.out.println("got user "+name);
		if(name == null) 
		{
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}	
		%>
		<%= name %>
		</small></h4>
      </div>
    </div>

<!-- Navegation bar -->
       <div class="row">
      <div class="col-md-12">
        <nav class="navbar navbar-inverse" role="navigation">
        <div class="container-fluid">
          <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp">Home</a>
          </div>
            <div>
            <ul class="nav navbar-nav">
              <li class="active"><a href="CourseSelect.jsp">Create</a></li>
              <li><a href="pdPreview.jsp">Submitted</a></li>
              <li><a href="PDManagement.jsp">Management</a></li>
            </ul>
          </div>
        </div>
      </nav>
      </div>
    </div>

    <div class="row">
            <div class="col-xs-10 col-md-10">
                <h2>Instructor Select</h2>
            </div>
            <div class="col-xs-2 col-md-2">
                <a class="btn btn-default btn-primary" href="CourseSelect.jsp" role="button">Back</a>
            </div>
        </div>
        <div class="row">
        <div class="col-xs-12 col-md-8">
            <div class="table-responsive">
            <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Subject</th>
                            <th>Group</th>
                            <th>Student</th>
                            <th>Instructor</th>
                        </tr>
                        </thead>
                      <tbody>
                      <c:forEach var="row" items="${temp1}">
                      <%   
                        	 int count = Integer.parseInt(GNumber[g]);
                             int curCount=count;
                        	 String curCourse=temp1[g];
                        	 String curStudents=Students[g];
                        	 String curTeachers=temp2[t];
                        	 if(count==1)
                        	 {
                        		 %>
                            <tr>                           
                            <td id="currentCourse"><c:out value="${row}"></c:out></td> <% Course.add(curCourse);%>
                            <td><c:out value="<%=count %>"></c:out></td><%Group.add(curCount);%>
                            <td><c:out value="<%=Students[g] %>"></c:out></td><% Student.add(curStudents);%>
                            <td>       
                                <select name="select" id="<%=SelectCount %>" class="form-control">
                                        <option value='0'>Select</option>
                                        <c:forEach var="a" items="${temp2}">
	                                              <option id='<%=valueCount %>' value="${a}">${a}</option><%valueCount++;%>
	                                    </c:forEach>
                                </select>
                            </td>
                            <%SelectCount++; %>
                            </tr>
                            <%
                             cnt++;
                             valueCount=1;
                             t=0;
                        	 }
                        	 else if(count!=1)
                        	 {
                        		 for(int i=1;i<=count;i++)
                        		 {                  			 
                            %>
                        	<tr>                           
                            <td id="currentCourse"><c:out value="${row}"></c:out></td> <% Course.add(curCourse);%>
                            <td><c:out value="<%=i %>"></c:out></td> <%Group.add(i);%>
                            <td><c:out value="<%=Students[g] %>"></c:out></td> <% Student.add(curStudents);%>
                            <td>       
                                <select name="select" id="<%=SelectCount %>" class="form-control">
                                        <option id='0'>Select</option>
                                        <c:forEach var="a" items="${temp2}">
	                                              <option id='<%=valueCount%>' value="${a}">${a}</option><%valueCount++;%>
	                                    </c:forEach>
                                </select>
                            </td>
                            <%SelectCount++; %>
                            </tr>
                        			 <%
                        		 cnt++;
                        	     valueCount=1;
                        	     t=0;
                        		 }
                        	 }
                        	 g++;
                      %>
                      </c:forEach>
                      </tbody>
            </table>    
            </div>
        </div>

        <div class="col-xs-6 col-md-4">
            <h4>Naming</h4>
            <input type="text" class="form-control" name="Timetable" placeholder="Summer 2018">
                   <button type="submit" class="btn btn-success" name="Confirm" id="hahaha">Save</button>
        </div>
        </div>
    </div>
    </form>
</body>
</html>