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

<title>taEditor</title>
</head>

<body>
  <%!
    ArrayList<String> allTa = Mydb.loadTA();
    int TaNum = allTa.size();
  %>

  <script type="text/javascript">
$(document).ready(function(){
  var oldVal="";
  var workload=new Array();
  workload[0]=0;
  
  <%   
    for(int i=0;i<TaNum;i++)
    {%>
    workload[<%=i+1%>]=0;
    <%}%>

    $(".table-responsive select").change(function(){
      oldVal=$(this).attr("old");
        var _thisVal=$(this).find('option:selected').attr("id");
        var id=$(this).attr("id");
        $(this).parent().siblings("td").find("option[id="+_thisVal+"]").not("option[id=0]").hide();
        $(this).parent().siblings("td").find("option[id="+oldVal+"]").show();
      workload[oldVal]=workload[oldVal]-1;
        workload[_thisVal]=workload[_thisVal]+1;
        
        
        if(workload[_thisVal]==3)
        {
          alert("The current TA's workload is over 3!")
          $(this).parent().parent().siblings("tr").children().find("option[id="+_thisVal+"]").not("option[id=0]").hide();
        }
        else if(workload[oldVal]!=3)
        {
          $(this).parent().parent().siblings("tr").children().find("option[id="+oldVal+"]").show();
        }
        $(this).find("option[id="+oldVal+"]").show();
        $(this).attr("old",_thisVal)
   })
})
</script>
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
              			<li class="active"><a href="taEditor.jsp">Create</a></li>
              			<li><a href="taPreview.jsp">Submited</a></li>
              			<li><a href="TAManagement.jsp">Management</a></li>
            			</ul>
          		</div>
        		</div>
      		</nav>
		</div>
	</div>
	<form role="form" action="/workshop_beta_online/TAServlet" method="post">
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
		<div class="col-xs-2 col-md-2"></div>
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
        <div class="col-xs-6 col-md-2">
            <div class="list-group">
                <h4>Mission</h4>
                <%
                			tableTitle = Mydb.readMission("1");
                			sizeOfTitle = tableTitle.size();
                    		for(int i=0; i<sizeOfTitle; i++){
                %>
                <a href="TAServlet?tableName=<%= tableTitle.get(i) %>" class="list-group-item"><%= tableTitle.get(i) %></a>
                <%} %>
            </div>
        </div>
        <div class="col-xs-6 col-md-2">
        		<%
        		String msg3 = (String)request.getAttribute("msg3");
        		if(msg3 != null){
    				out.write(msg3);
    			}
        		%>
        </div>
	</div>
	</form>
    </div>
</body>
</html>