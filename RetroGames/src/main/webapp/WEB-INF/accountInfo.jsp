<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>${ user.userName } Information</title>
<link rel="stylesheet" type="text/css" href="/css/accountInfo.css">
</head>
<body>
	<div id="header">
		<h1>RetroGames</h1>
		<div id="head">
			<a href="/dashboard/${ user.id }">Home</a>
			<a href="/users/cart/${ user.id }">Cart</a>
			<a href="/logout">Logout</a>
		</div>
	</div>
	<div id="main">
		<h2>User Name: ${ user.userName }</h2>
		<h2>Email: ${ user.email }</h2>
		<a href="/users/accountEdit/${ user.id }">Edit Account</a>
	</div>
</body>
</html>