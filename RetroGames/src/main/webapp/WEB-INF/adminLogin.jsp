<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Login</title>
</head>
<body>
	<div id="header">
		<h1>RetroGames</h1>
		<a href="/dashboard/0">Home</a>
	</div>
	<form:form action="/processAdminLogin" method="post" modelAttribute="admin" id="main">
		<h2>Admin Login</h2>
		<form:label path="email">Email:</form:label>
		<form:input path="email"></form:input>
		<form:errors path="email"></form:errors>
			
		<form:label path="password">Password:</form:label>
		<form:input path="password"></form:input>
		<form:errors path="password"></form:errors>
		<input type="submit" value="submit"/>
	</form:form>
</body>
</html>