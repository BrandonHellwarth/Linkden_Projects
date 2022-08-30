<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit ${ item.name }</title>
</head>
<body>
	<div id="header">
		<h1>RetroGames</h1>
		<a href="/dashboard/${ admin.id }">Home</a>
		<a href="/logoutAdmin">logout</a>
	</div>
	<h2>Edit ${ item.name }</h2>
	<form:form action="/processEditItem/${ admin.id }/${ item.id }" method="post" modelAttribute="item1">
		<input type="hidden" name="_method" value="put"/>
		<form:label path="name">Name:</form:label>
		<form:input path="name" value="${ item.name }"></form:input>
		<form:errors path="name"></form:errors>
		
		<form:label path="description">Description:</form:label>
		<form:textarea path="description" value="${ item.description }"></form:textarea>
		<form:errors path="description"></form:errors>
		
		<form:label path="price">Price:</form:label>
		<form:input type="number" step="0.01" min="0" max="10000" path="price" value="${ item.price }"></form:input>
		<form:errors path="price"></form:errors>
		
		<form:label path="stock">Stock:</form:label>
		<form:input type="number" min="0" max="10000" path="stock" value="${ item.stock }"></form:input>
		<form:errors path="stock"></form:errors>
		
		<input type="submit" value="Update Product"/>
	</form:form>
</body>
</html>