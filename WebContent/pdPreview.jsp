<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<%@ page import="database.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0">
<%-- CSS-script here --%>
<link href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap-theme.min.css"></script>
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- import echarts file-->
<title>pdPreview</title>
</head>

<body>
<%! 
	ArrayList<String> tableTitle = new ArrayList<String>();
	int sizeOfTitle;
%>
  <div class="container" style="width:95%;">
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
              <li class="active"><a href="pdPreview.jsp">Submited</a></li>
              <li><a href="PDManagement.jsp">Management</a></li>
            </ul>
          </div>
        </div>
      </nav>
      </div>
    </div>
    <div class="row">
            <div class="col-xs-10 col-md-10">
                <%
				String msg2 = (String)request.getAttribute("msg2");
				if(msg2 != null){
					String printTitle = "<h2>" + msg2 + "</h2>";
					out.write(printTitle);
				}
				%>
            </div>

     </div>
     <div class="row">
        <div class="col-xs-12 col-md-10">
            <div class="table-responsive">
            <%
				String msg1 = (String)request.getAttribute("msg1");
				if(msg1 != null){
					out.write(msg1);
				}
			%>
            </div>
        </div>
        <div class="col-xs-12 col-md-2">
            <div class="list-group">
                <h4>Timetable Preview</h4>
                <%
                			tableTitle = Mydb.readMission("1");
                			sizeOfTitle = tableTitle.size();
                    		for(int i=0; i<sizeOfTitle; i++){
                %>
                <a href="PDPreviewServlet?tableName=<%= tableTitle.get(i) %>" class="list-group-item"><%= tableTitle.get(i) %></a>
                <%} %>
            </div>
            	<div class="form-group">
            	<%
            		String msg3 = (String)request.getAttribute("msg3");
            		if(msg3 != null){
            			out.write(msg3);
            		}
            	%>
            	</div>
        </div>
        </div>
    </div>
    
   <script type="text/javascript" src="echarts.js"></script>
    
     <sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
     url="jdbc:mysql://172.16.162.17/k530003021"
     user="k530003021"  password="student"/>
     
     <%  String sql1 = "SELECT * FROM workload WHERE term = ''";
     	 String sql2 = "SELECT * from workload WHERE term='" + msg2 + "'";
     %>
     
	<sql:query dataSource="${snapshot}" var="result">
	<% if(msg2 != null){
		out.write(sql2);
	} else{
		out.write(sql1);
	}
	%>
	</sql:query>
	<center>
    <div id="main" style="width: 600px;height:400px;"></div>
  	</center>
  <script type="text/javascript">
  
        var myChart = echarts.init(document.getElementById('main'));

        option = {
        	    title: {
        	        text: 'workload',
        	        left: 'center'
        	    },
        	    tooltip : {
        	        trigger: 'item',
        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
        	    },
        	    legend: {
        	        // orient: 'vertical',
        	        // top: 'middle',
        	        bottom: 10,
        	        left: 'center',
        	    },
        	    series : [
        	        {
        	            type: 'pie',
        	            radius : '65%',
        	            center: ['50%', '50%'],
        	            selectedMode: 'single',
        	            data:[
        	               
        	            	<c:forEach var="row" items="${result.rows}">
        	            	{value:<c:out value="${row.workload}"/>, name:'<c:out value="${row.lector}"/>'},     	                
        	            	</c:forEach>
        	            ],
        	            itemStyle: {
        	                emphasis: {
        	                    shadowBlur: 10,
        	                    shadowOffsetX: 0,
        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
        	                }
        	            }
        	        }
        	    ]
        	};

        myChart.setOption(option);
    </script>
    
</body>
</html>