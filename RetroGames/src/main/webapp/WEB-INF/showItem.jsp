<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>${ item.name }</title>
<link rel="stylesheet" type="text/css" href="/css/showItem.css">
</head>
<body>
	<% if(session.getAttribute("user") != null){ %>
		<div id="header">
		<h1>RetroGames</h1>
			<div id="head">
				<a href="/dashboard/${ user.id }">Home</a>
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
				<a href="/dashboard/${ admin.id }">Home</a>
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
			<a href="/dashboard/0">Home</a>
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
		<div id="left">
			<h2>${ item.name }</h2>
			<!-- add image here later on -->
			<p>Description:</p>
			<p>${ item.description }</p>
		</div>
		<div id="right">
			<p id="price">Price: $${ item.price }</p>
			<% if(session.getAttribute("user") != null) { %>
				<c:choose>
					<c:when test="${ item.stock == 0 }">
						<p>Out of Stock</p>
					</c:when>
					<c:otherwise>
						<a href="/items/addToCart/${ user.id }/${ item.id }" id="addToCart">Add to Cart</a>
					</c:otherwise>
				</c:choose>
			<% } %>
			<% if(session.getAttribute("admin") != null) { %>
				<a href="/admins/editItem/${ admin.id }/${ item.id }" class="link">Edit</a>
				<a href="/items/delete/${ admin.id }/${ item.id }" class="link">Delete</a>
			<% } %>
		</div>
	</div>
</body>
</html>