<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="applicato/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>

<data>
<c:forEach var="book" items="${books}">
    <a href="/Library?command=goToSpecificBook&bookId=${book.id}">
        <div class="row item-section">
            <div class="col-md-2">
                <img class="book-logo" src="img\book-example.jpg" alt="img\book-example"/>
            </div>
            <div class="col-md-4">
                <p>ID:<c:out value="${book.id}"/></p>
                <p>Book name: <c:out value="${book.name}"/></p>
                <p>Author: <c:out value="${book.author}"/></p>
                <p>Year: <c:out value="${book.year}"/></p>
                <p>Genre: <c:out value="${book.genre}"/></p>
            </div>
            <div class="col-md-5">
                <p>Your:</p>
                <p>Типа звездочки : <c:out value="${book.currentUserVote}"/></p>
                <p>Overall:</p>
                <p>Типа звездочки : <c:out value="${book.readersRate}"/></p>
            </div>
            <div class="col-md-1">
                <a class="details-link" href="Library?command=goToSpecificBook&bookId=${book.id}">Details</a>
            </div>
        </div>
    </a>
</c:forEach>
</data>
