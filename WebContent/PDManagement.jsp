<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap-theme.min.css"></script>
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- import echarts file-->

<title>PDManagement</title>
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
              <li><a href="CourseSelect.jsp">Create</a></li>
              <li><a href="pdPreview.jsp">Submitted</a></li>
              <li class="active"><a href="PDManagement.jsp">Management</a></li>
            </ul>
          </div>
        </div>
      </nav>
      </div>
    </div>

    <div class="row"><h3>Subjects</h3></div>
    <div class="row">
      <div class="col-md-6">
      
     <sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
     url="jdbc:mysql://172.16.162.17/k530003021"
     user="k530003021"  password="student"/>
     
	<sql:query dataSource="${snapshot}" var="result">
	SELECT * from courses;
	</sql:query>
	
        <table class="table table-hover">
        
        <!--  Generate the Subjects form -->
            <tbody> 
            
            <c:forEach var="row" items="${result.rows}">
                            
                <tr>
                    <td>
                      <form class="form-inline" action="/workshop_beta_online/Controller" method="POST">
                        <button type="submit" class="btn btn-danger" name="CourseDelete" value="<c:out value="${row.Subject}"/>"> 
                        Delete
                        </button>
                        <c:out value="${row.Subject}"/>
                      </form>
                    </td>
                    <td><c:out value="${row.Students}"/></td>
                    <td><c:out value="${row.Type}"/></td>
                    <td><c:out value="${row.Gnumber}"/> </td>
                </tr>
                
             </c:forEach>
             
               
            </tbody>
        </table>
      </div>
      <div class="col-md-6">

<!-- Teacher Add-->
        <form class="form-inline" action="/workshop_beta_online/Controller" method="POST">
          <div>
          <div class="col-xs-12 col-md-6 col-sm-6">
            <p>Subject Title:</p>
            <div class="form-group">
              <label class="sr-only" for="exampleInputEmail3">Subject Title</label>
              <input name="subject" type="text" class="form-control" id="exampleInputEmail3" placeholder="eg.Computer Organization">
            </div>
          </div>
          <div class="col-xs-12 col-md-6 col-sm-6"> 
            <p>Student:</p>
              <div class="form-group">
                <label class="sr-only" for="exampleInputEmail3">Student</label>
                <input name="student" type="text" class="form-control" id="exampleInputEmail3" placeholder="eg.Y2 CS">
              </div>
          </div>
          <div class="col-xs-6 col-md-6 col-sm-6">
            <p>Group Number:</p>
            <div class="form-group">
            <select name="group" class="form-control" >
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
            </select>
            </div>
          </div>
          <div class="col-xs-6 col-md-6 col-sm-6">
            <p>Type:</p>
            <div class="form-group">
            <select name="type" class="form-control" >
                <option>GE</option>
                <option>MC</option>
                <option>MC for other majors</option>
            </select>
            </div>
          </div>
          </div><br><br>
          <div class="col-xs-12 col-sm-6 col-md-6">
          <div class="form-group">            
            <button type="submit" class="btn btn-primary" name="courseadd" style="width:105px; margin:3px 3px 0px 0px">Add</button>
          </div>
          </div>
        </form>

      </div>
    </div>

  <div class="row"><h3>Staffs</h3></div>
  <div class="row">
      <div class="col-md-6">
    <sql:query dataSource="${snapshot}" var="result">
	SELECT * from teacher;
	</sql:query>
        <table class="table table-hover">
            <tbody>                
        <!-- generate the TA list -->
               <c:forEach var="row" items="${result.rows}">
               <tr>
                    <td>
                    <form class="form-inline" action="/workshop_beta_online/Controller" method="POST">
                    <button type="submit" class="btn btn-danger" name="teDelete" value="<c:out value="${row.Name}"/>" >
                    Delete
                    </button>             
                    <c:out value="${row.Name}"/> 
                    </form>
                    </td>
                </tr>
         		</c:forEach>
            </tbody>
        </table>
      </div>
      <div class="col-md-6">

        <!-- the TA add -->
        <form class="form-inline" action="/workshop_beta_online/Controller" method="POST">
          <div class="col-xs-12 col-sm-6 col-md-6">
          <div class="form-group">
            <label class="sr-only" for="exampleInputEmail3">Staff</label>
            <input name="name" type="text" class="form-control" id="exampleInputEmail3" placeholder="Staff Name">
          </div>
          </div>
          <div class="col-xs-12 col-sm-12 col-md-12">
            <div class="form-group">
              <button type="submit" class="btn btn-primary" name="teadd" style="width:105px; margin:3px 3px 0px 0px">Add</button>
            </div>
          </div>
        </form>


      </div>
    </div>
  </div>
</body>
</html>