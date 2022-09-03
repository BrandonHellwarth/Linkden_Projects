<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>
<link rel="stylesheet" type="text/css" href="/css/login.css">
</head>
<body>
	<div id="header">
		<h1>RetroGames</h1>
		<div id="head">
			<a href="/dashboard/0">Home</a>
		</div>
	</div>
	<form:form action="/processRegister" method="post" modelAttribute="user" id="main">
		<h2>Register</h2>
		<div class="row">
			<form:label path="userName">User Name:</form:label>
			<form:input path="userName" id="input"></form:input>
			<form:errors path="userName"></form:errors>
		</div>
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
		<div class="row">
			<form:label path="confirm">Confirm Password:</form:label>
			<form:input path="confirm" id="input"></form:input>
			<form:errors path="confirm"></form:errors>
		</div>
		<input type="submit" value="submit" id="btn"/>
	</form:form>
</body>
</html>