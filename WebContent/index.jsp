<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta name="viewport" charset="utf-8" content="width=device-width, initial-scale=1.0">
        <title>Staff Assignment System</title>
        <!-- CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
		<link rel="stylesheet" href="css/form-elements.css">
        <link rel="stylesheet" href="css/style.css">
         <!-- Javascript -->
        <script src="js/jquery-1.11.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.backstretch.min.js"></script>
        <script src="js/scripts.js"></script>
    </head>

    <body>
    <%
   	session = request.getSession(false);
    session.removeAttribute("name"); 
	String name = (String)session.getAttribute("name");
	System.out.println("got user "+name);
    %>
        <!-- Top content -->
        <div class="top-content">
            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-8 col-sm-offset-2">
                            <h1 style="color:white"><strong>Staff Assignment System</strong></h1>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 form-box">
                        	<div class="form-top">
                        		<div class="form-top-left">
                        			<h3>Login to our site</h3>
                            		<p>Enter your staff id and password to log in:</p>
                        		</div>
                            </div>
                            <div class="form-bottom">
			                    <form role="form" action="/workshop_beta_online/Controller" method="POST" class="login-form">
			                    	<div class="form-group">
			                    		<label class="sr-only" for="form-username">Staff ID</label>
			                        	<input type="text" name="id" placeholder="Staff ID..." class="form-username form-control" id="form-staffid">
			                        </div>
			                        <div class="form-group">
			                        	<label class="sr-only" for="form-password">Password</label>
			                        	<input type="password" name="password" placeholder="Password..." class="form-password form-control" id="form-password">
			                        </div>
			                        <button type="submit" class="btn" name="login" value="login">Sign in</button>
			                    </form>
		                    </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>