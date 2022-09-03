<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit ${ user.userName }'s Information</title>
<link rel="stylesheet" type="text/css" href="/css/login.css">
</head>
<body>
	<div id="header">
		<h1>RetroGames</h1>
		<div id="head">
			<a href="/dashboard/${ user.id }">Home</a>
			<a href="/users/accountInfo/${ user.id }">Account</a>
			<a href="/users/cart/${ user.id }">Cart</a>
			<a href="/logout">Logout</a>
		</div>
	</div>
	<form:form action="/processEditUser/${ user.id }" method="post" modelAttribute="user1" id="main">
		<input type="hidden" name="_method" value="put"/>
		<h2>Edit ${ user.userName }'s Information</h2>
		<div class="row">
			<form:label path="userName">User Name:</form:label>
			<form:input path="userName" value="${ user.userName }" id="input"></form:input>
			<form:errors path="userName"></form:errors>
		</div>
		<div class="row">
			<form:label path="email">Email:</form:label>
			<form:input path="email" value="${ user.email }" id="input"></form:input>
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
		<input type="submit" value="Edit Account" id="btn"/>
	</form:form>
</body>
</html>