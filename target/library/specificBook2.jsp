<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%--<%@page import="com.diachuk.dao.implementations.MySql.MySqlBookDAO" %>--%>

<body>


<h1 align="center">This is a specific book page!</h1>
<h2 align="center">Welcome</h2>

<br/>
<br/>BOOK
<br/>
<center>
    <div>
        Book ID: <c:out value="${specificBook.id}"/> <br><br>
        <c:out value="${specificBook.name}"/><br><br>
        Author: <c:out value="${specificBook.author}"/><br><br>
        Year: <c:out value="${specificBook.year}"/><br><br>
        Genre: <c:out value="${specificBook.genre}"/><br><br>
        Pages: <c:out value="${specificBook.pages}"/><br><br>
        Readers rate: <c:out value="${specificBook.readersRate}"/><br><br>
        Your rate: <c:out value="${specificBook.currentUserVote}"/><br><br>
        Number of votes: <c:out value="${specificBook.numberOfVotes}"/><br><br>
        Description:
        <div style="width: 35%">
            <c:out value="${specificBook.description}"/><br><br>
        </div>
        Available to take home: <c:out value="${specificBook.availableForHome}"/>
        (from <c:out value="${specificBook.amountForHome}"/>)<br><br>
        Available in reading room: <c:out value="${specificBook.availableInRRoom}"/>
        (from <c:out value="${specificBook.amountInRRoom}"/>)<br><br>
    </div>
</center>
<form method="POST">
    <input name="bookId" type="hidden" value="${specificBook.id}">
    <c:if test="${currentUser != null}">
        <button formaction="/Library?command=setBookForFuture">Want to read</button>
    </c:if>
    <c:choose>
        <c:when test="${(currentUser != null) && (specificBook.availableForHome > 0) }">
            <button formaction="/Library?command=reserveBook">Reserve the book</button>
        </c:when>
        <c:when test="${(currentUser != null) && (specificBook.availableForHome == 0) }">
            <button formaction="/Library?command=requestBook">Request the book</button>
        </c:when>
    </c:choose>
</form>
<form>

</form>


</body>
</html>
