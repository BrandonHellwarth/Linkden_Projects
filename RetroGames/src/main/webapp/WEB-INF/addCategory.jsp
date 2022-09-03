<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add a Category</title>
<link rel="stylesheet" type="text/css" href="/css/login.css">
</head>
<body>
	<div id="header">
		<h1>RetroGames</h1>
		<div id="head">
			<a href="/dashboard/${ admin.id }">Home</a>
			<a href="/logoutAdmin">logout</a>
		</div>
	</div>
	<form:form action="/processAddCategory/${ admin.id }" method="post" modelAttribute="category" id="main">
		<h2>Add a Category</h2>
		<div class="row">
			<form:label path="name">Name:</form:label>
			<form:input path="name" id="input"></form:input>
			<form:errors path="name"></form:errors>
		</div>
		
		<input type="submit" value="Add Category" id="btn"/>
	</form:form>
</body>
</html>