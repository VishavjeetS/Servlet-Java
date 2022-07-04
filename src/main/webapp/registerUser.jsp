<%@page import="com.example.db.DB"%>
<%@page import="com.example.model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register!</title>
</head>
<body>

	<%
		User user = new User();
		user.name = request.getParameter("txtName");
		user.email = request.getParameter("txtEmail");
		user.password = request.getParameter("txtPassword");
		
		String sql = user.toRegisterSQL();
		
		DB db = DB.getDB();
		int result = db.executeUpdate(sql);
		String message = result>0 ? "Thank You! You are registered with us." : "Oops! Something went wrong, Try again.";
		if(result>0){
		%>
			<h3>Thank You! Your email <%=user.email%> is registered with us.</h3> 
			<a href="index.html">Click to navigate to Home</a>
		<%
		}
		else{
		%>
			<h3>Oops! Something Went Wrong...</h3>
			<p>Registration failed for <%=user.email%></p> 
		<%
		}
	%>

</body>
</html>