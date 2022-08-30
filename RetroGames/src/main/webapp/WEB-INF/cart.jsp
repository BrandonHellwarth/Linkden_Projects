<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="ISO-8859-1">
<title>${ user.userName }'s cart</title>
<link rel="stylesheet" type="text/css" href="/css/cart.css">
</head>
<body>
	<div id="header">
		<h1>RetroGames</h1>
		<div id="head">
			<a href="/dashboard/${ user.id }">Home</a>
			<a href="/users/accountInfo/${ user.id }">Account</a>
			<a href="/logout">Logout</a>
		</div>
	</div>
	<div id="main">
		<h2>Items:</h2>
		<c:set var="total" scope="page" value="0"></c:set>
			<c:forEach var="item" items="${ user.items }">
				<div id="row">
					<p>${ item.name }</p>
					<p>$${ item.price }</p>
				</div>
			<c:set var="total" scope="page" value="${ total + item.price }"></c:set>
		</c:forEach>
		<form action='/charge/${ user.id }' method='POST' id='checkout-form'>
	    <input type='hidden' value='${amount}' name='amount' />
	    <label>Price: $${amount/100}</label>
	    <script
	       src='https://checkout.stripe.com/checkout.js' 
	       class='stripe-button'
	       data-key='${stripePublicKey}'
	       data-amount='${amount}'
	       data-currency='${currency}'
	       data-name='Baeldung'
	       data-description='Spring course checkout'
	       data-image
	         ='https://www.baeldung.com/wp-content/themes/baeldung/favicon/android-chrome-192x192.png'
	       data-locale='auto'
	       data-zip-code='false'>
		</script>
		</form>
	</div>
</body>
</html>