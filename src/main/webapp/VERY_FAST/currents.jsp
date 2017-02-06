<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: VA-N_
  Date: 06.02.2017
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Currents</title>
</head>
<body>


<a href="/VERY_FAST/menu.jsp">
    <button type="button" name="button">Menu</button>
</a>

<a href="/Library?command=getUserCurrents">
    <button type="button" name="button">Menu</button>
</a>


<h3>Requests</h3>

<table>
    <thead>
    <tr>
        <td>Action</td>
        <td>ID</td>
        <td>Book Id</td>
        <td>Book name</td>
        <td>Author</td>
        <td>request date</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="request" items="${requests}">
        <tr>
            <td>request</td>
            <td>${request.id}</td>
            <td>${request.bookId}</td>
            <td>${request.book.name}</td>
            <td>${request.book.author}</td>
            <td>${request.requestDate}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<h3>Reservations</h3>

<table>
    <thead>
    <tr>
        <td>Action</td>
        <td>Type</td>
        <td>ID</td>
        <td>Book Id</td>
        <td>Book name</td>
        <td>Author</td>
        <td>Took date</td>
        <td>... date</td>
        <td>Return date</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="request" items="${requests}">
        <tr>
            <td>request</td>
            <td>Calambur</td>
            <td>12345</td>
            <td>78954</td>
            <td>Kak ubitsa</td>
            <td>Max</td>
            <td>45/45//87955</td>
            <td>465</td>
            <td>12/45/2222154</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:forEach var="reservation" items="${reservations}">

</c:forEach>


<h3>BookLoans</h3>

<table>
    <thead>
    <tr>
        <td>Action</td>
        <td>Type</td>
        <td>ID</td>
        <td>Book Id</td>
        <td>Book name</td>
        <td>Author</td>
        <td>Took date</td>
        <td>... date</td>
        <td>Return date</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="request" items="${requests}">
        <tr>
            <td>request</td>
            <td>Calambur</td>
            <td>12345</td>
            <td>78954</td>
            <td>Kak ubitsa</td>
            <td>Max</td>
            <td>45/45//87955</td>
            <td>465</td>
            <td>12/45/2222154</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:forEach var="bookLoan" items="${bookLoans}">

</c:forEach>


</body>
</html>
