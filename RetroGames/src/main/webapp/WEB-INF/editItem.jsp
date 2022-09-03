<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit ${ item.name }</title>
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
	<form:form action="/processEditItem/${ admin.id }/${ item.id }" method="post" modelAttribute="item1" id="main">
	<h2>Edit ${ item.name }</h2>
		<input type="hidden" name="_method" value="put"/>
		<div class="row">
			<form:label path="name">Name:</form:label>
			<form:input path="name" value="${ item.name }" id="input"></form:input>
			<form:errors path="name"></form:errors>
		</div>
		
		<div class="row">
			<form:label path="description">Description:</form:label>
			<form:textarea path="description" value="${ item.description }" id="input"></form:textarea>
			<form:errors path="description"></form:errors>
		</div>
		
		<div class="row">
			<form:label path="price">Price:</form:label>
			<form:input type="number" step="0.01" min="0" max="10000" path="price" value="${ item.price }" id="input"></form:input>
			<form:errors path="price"></form:errors>
		</div>
		
		<div class="row">
			<form:label path="stock">Stock:</form:label>
			<form:input type="number" min="0" max="10000" path="stock" value="${ item.stock }" id="input"></form:input>
			<form:errors path="stock"></form:errors>
		</div>
		
		<input type="submit" value="Update Product" id="btn"/>
	</form:form>
</body>
</html>