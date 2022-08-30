<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns='http://www.w3.org/1999/xhtml' xmlns:th='http://www.thymeleaf.org'>
    <head>
        <title>Result</title>
        <link rel="stylesheet" type="text/css" href="/css/result.css">
    </head>
    <body>
        <h3 th:if='${error}' th:text='${error}' style='color: red;'></h3>
        <div th:unless='${error}'>
            <h3 style='color: green;'>Success!</h3>
            <div>Id.: ${id}</div>
            <div>Status: ${status}</div>
            <div>Charge id.: ${chargeId}</div>
            <div>Balance transaction id.: ${balance_transaction}</div>
        </div>
        <a href='/users/cart/${ user.id }' id="link">Checkout Again</a>
    </body>
</html>