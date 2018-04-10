<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
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

<title>SelectCourse</title>
</head>

<body>
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
        
         <sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
         url="jdbc:mysql://172.16.162.17/k530003021"
         user="k530003021"  password="student"/>
     
	     <sql:query dataSource="${snapshot}" var="result">
	         SELECT * from courses;
         </sql:query>
         
         <sql:query dataSource="${snapshot}" var="Teacher">
             SELECT * from teacher;
         </sql:query>
         
         
        <div class="row">
    <!-- Table one Courese Select -->
    		<form class="form-inline" action="Controller" method="POST">
            <div class="col-xs-12 col-md-8">  
                <table class="table table-hover">
                <thead>
                    <tr>
                        <td><h3>Course Select</h3></td>
                    </tr>
                </thead>
                <tbody>       
                <c:forEach var="row" items="${result.rows}" varStatus="index">
                    <tr>
                        <td>
                            <div class="checkbox"> 
                            <label>
                                <input type="checkbox" name="courses" value="${row.Subject}"><c:out value="${row.Subject}"/>
                            </label> 
                            </div>
                        </td>
                    </tr>               
                 </c:forEach>
                </tbody>
                </table>
            </div>
  <!-- Table two Teacher Select -->
            <div class="col-xs-12 col-md-8">
                <table class="table table-hover">
                <thead>
                    <tr>
                        <td><h3>Teacher Select</h3></td>
                    </tr>
                </thead>
                <tbody> 
                <c:forEach var="row" items="${Teacher.rows}">  
                    <tr>
                        <td>   
                            <div class="checkbox"> 
                            <label>
                                <input type="checkbox" name="teachers" value="${row.Name}"><c:out value="${row.Name}"/>
                            </label> 
                            </div>
                        </td>
                    </tr>
               </c:forEach>
                </tbody>
                </table>
             </div>

                <div class="col-md-3 col-md-offset-3">
                <button type="submit" class="btn btn-primary" name="TeaNext">Next</button> 
            		</div>
            </form>
        </div>
    </div>
</body>
</html>