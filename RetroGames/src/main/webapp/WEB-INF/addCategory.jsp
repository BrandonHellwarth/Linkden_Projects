<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add a Category</title>
</head>
<body>
	<div id="header">
		<h1>RetroGames</h1>
		<a href="/dashboard/${ admin.id }">Home</a>
		<a href="/logoutAdmin">logout</a>
	</div>
	<h2>Add a Category</h2>
	<form:form action="/processAddCategory/${ admin.id }" method="post" modelAttribute="category">
		<form:label path="name">Name:</form:label>
		<form:input path="name"></form:input>
		<form:errors path="name"></form:errors>
		
		<input type="submit" value="Add Category"/>
	</form:form>
</body>
</html>