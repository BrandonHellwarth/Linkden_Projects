<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dashboard</title>
<link rel="stylesheet" type="text/css" href="/css/dashboard.css">
</head>
<body>
	<% if(session.getAttribute("user") != null){ %>
		<div id="header">
		<h1>RetroGames</h1>
			<div id="head">
				<a href="/users/accountInfo/${ user.id }">Account</a>
				<a href="/users/cart/${ user.id }">Cart</a>
				<a href="/logout">Logout</a>
			</div>
		</div>
		<div id="nav">
			<c:forEach var="category" items="${ categories }">
				<a href="/categories/${ category.name }/${ user.id }/${ category.id }">${ category.name }</a>
			</c:forEach>
		</div>
	<% } %>
	<% if(session.getAttribute("admin") != null){ %>
		<div id="header">
		<h1>RetroGames</h1>
			<div id="head">
				<a href="/admins/addItem/${ admin.id }">Add Item</a>
				<a href="/admins/addCategory/${ admin.id }">Add Category</a>
				<a href="/logoutAdmin">Logout</a>
			</div>
		</div>
		<div id="nav">
			<c:forEach var="category" items="${ categories }">
				<a href="/categories/${ category.name }/${ admin.id }/${ category.id }">${ category.name }</a>
			</c:forEach>
		</div>
	<% } %>
	<% if(session.getAttribute("user") == null && session.getAttribute("admin") == null) { %>
	<div id="header">
	<h1>RetroGames</h1>
		<div id="head">
			<a href="/users/register">Register</a>
			<a href="/users/login">Login</a>
		</div>
	</div>
		<div id="nav">
			<c:forEach var="category" items="${ categories }">
				<a href="/categories/${ category.name }/0/${ category.id }">${ category.name }</a>
			</c:forEach>
		</div>
	<% } %>
	<div id="main">
		<h2>Popular Products</h2>
		<c:forEach var="item" items="${ items }">
			<div id="row">
				<div id="left">
					<% if(session.getAttribute("user") != null) { %>
						<a href="/items/show/${ user.id }/${ item.id }">${ item.name }</a>
					<% } %>
					<% if(session.getAttribute("admin") != null) { %>
						<a href="/items/show/${ admin.id }/${ item.id }">${ item.name }</a>
					<% } %>
					<% if(session.getAttribute("user") == null && session.getAttribute("admin") == null) { %>
						<a href="/items/show/0/${ item.id }">${ item.name }</a>
					<% } %>
				</div>
				<div id="right">
					<p>$${ item.price }</p>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>