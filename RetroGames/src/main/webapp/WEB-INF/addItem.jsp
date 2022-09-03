<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add a Product</title>
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
	<form:form action="/processAddItem/${ admin.id }" method="post" modelAttribute="item" id="main">
	<h2>Add a new Product</h2>
		<div class="row">
			<form:label path="name">Name:</form:label>
			<form:input path="name" id="input"></form:input>
			<form:errors path="name"></form:errors>
		</div>
		
		<div class="row">
			<form:label path="description">Description:</form:label>
			<form:textarea path="description" id="input"></form:textarea>
			<form:errors path="description"></form:errors>
		</div>
		
		<div class="row">
			<form:label path="price">Price:</form:label>
			<form:input type="number" step="0.01" min="0" max="10000" path="price" id="input"></form:input>
			<form:errors path="price"></form:errors>
		</div>
		
		<div class="row">
			<form:label path="stock">Stock:</form:label>
			<form:input type="number" min="0" max="10000" path="stock" id="input"></form:input>
			<form:errors path="stock"></form:errors>
		</div>
		
		<div class="row">
			<label for="Category">Category:</label>
			<select name="category" id="input">
				<c:forEach var="category" items="${ categories }">
					<option value="${ category.id }">${ category.name }</option>
				</c:forEach>
			</select>
			<p><c:out value="${ error }"></c:out></p>
		</div>
		
		<input type="submit" value="Add Product" id="btn"/>
	</form:form>
</body>
</html>