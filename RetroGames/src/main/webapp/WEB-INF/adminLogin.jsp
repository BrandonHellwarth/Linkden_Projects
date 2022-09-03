<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Login</title>
<link rel="stylesheet" type="text/css" href="/css/login.css">
</head>
<body>
	<div id="header">
		<h1>RetroGames</h1>
		<div id="head">
			<a href="/dashboard/0">Home</a>
		</div>
	</div>
	<form:form action="/processAdminLogin" method="post" modelAttribute="admin" id="main">
		<h2>Admin Login</h2>
		<div class="row">
			<form:label path="email">Email:</form:label>
			<form:input path="email" id="input"></form:input>
			<form:errors path="email"></form:errors>
		</div>
		
		<div class="row">
			<form:label path="password">Password:</form:label>
			<form:input path="password" id="input"></form:input>
			<form:errors path="password"></form:errors>
		</div>
		
		<input type="submit" value="Submit" id="btn"/>
	</form:form>
</body>
</html>