<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/WEB-INF/myTags.tld" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="src/main/webapp/css/custom/library-workers-page.css"/>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>News</title>

    <link href="css\bootstrap.min.css" rel="stylesheet">
    <link href="css\bootstrap-theme.min.css" rel="stylesheet">
    <link href="css\custom\book-description.css" rel="stylesheet">
    <link href="css\custom\library-header.css" rel="stylesheet">

    <script src="js\jquery-3.1.1.min.js"></script>
    <script src="js\bootstrap.min.js"></script>

    <script src="js\messages.js"></script>
    <script src="js\specific-book.js"></script>
</head>

<body>

<c:import url="WEB-INF/pages/parts/header.jsp"/>

<div class="container-fluid book-description-section">
    <div class="row">
        <div class="col-sm-4">
            <my:bookImage bookId="${specificBook.id}"/>
        </div>
        <div class="col-sm-8">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-6">
                        <div class="container-fluid fields">
                            <div class="row">
                                <div class="col-md-6">
                                    <p>ID:</p>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${specificBook.id}" escapeXml="true" /></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <p>Book name:</p>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${specificBook.name}" escapeXml="true" /></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <p>Author:</p>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${specificBook.author}" escapeXml="true" /></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <p>Year:</p>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${specificBook.year}" escapeXml="true" /></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <p>Genre:</p>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${specificBook.genre}" escapeXml="true" /></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <p>Pages:</p>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${specificBook.pages}" escapeXml="true" /></p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="container-fluid fields">
                            <div class="row">
                                <p>Your rate</p>
                                <p><c:out value="${specificBook.currentUserVote}"/> Zvezdochiki</p>
                            </div>
                            <div class="row">
                                <p>Overwall rate</p>
                                <p>
                                    <c:out value="${specificBook.readersRate}"/>/<c:out
                                        value="${specificBook.numberOfVotes}"/>
                                    Zvezdochiki
                                </p>
                            </div>
                            <c:if test="${specificBook.banned}">
                                <div class="row">
                                    <p>Not aviable for loan*</p>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="row col-md-offset-1" style="margin-top: 28px">
                    <p>Available for home: <c:out value="${specificBook.availableForHome}" escapeXml="true" />/<c:out
                        value="${specificBook.amountForHome}" escapeXml="true" /></p><br/>
                    <p>Available in reading room:<c:out value="${specificBook.availableInRRoom}"/>/<c:out
                        value="${specificBook.amountInRRoom}" escapeXml="true" /></p><br/>
                </div>
            </div>
        </div>
    </div>

    <div class="row reserve-section">

        <input name="bookId" id="hidden-bookId" type="hidden" value="${specificBook.id}">

        <c:choose>
            <c:when test="${currentUser != null}">
                <div class="col-md-offset-2 col-sm-2">
                    <button type="button" id="btn-setForFuture" class="btn btn-default" name="setForFuture"
                            data-url="Library?command=setBookForFuture">
                        Want to read
                    </button>
                </div>
            </c:when>
            <c:otherwise>
                <div class="col-md-offset-5 col-sm-5">
                    <h4>Login to request or reserve book.</h4>
                </div>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${(currentUser != null) && accessLevel >= 2 && (specificBook.availableForHome > 0) && !specificBook.banned }">
                <div class="col-sm-2">
                    <select class="form-control" id="home-reserve-diration" name="">
                        <option value="1">1 day</option>
                        <option value="2">2 days</option>
                        <option value="3">3 days</option>
                        <option value="4">4 days</option>
                        <option value="5">5 days</option>
                    </select>
                </div>
                <div class="col-sm-2">
                    <button type="button" id="btn-homeReserve" class="btn btn-default" name="homeReserve"
                            data-url="Library?command=reserveForHome">Reserve for home
                    </button>
                </div>
            </c:when>
            <c:when test="${(currentUser != null) && accessLevel >= 2 && (specificBook.availableForHome == 0) && !specificBook.banned}">
                <div class="col-sm-2">
                    <button type="button" id="btn-request" class="btn btn-default" name="request"
                            data-url="Library?command=requestBook">Request book
                    </button>
                </div>
            </c:when>
        </c:choose>
        <c:if test="${currentUser != null && (specificBook.availableInRRoom > 0) && !specificBook.banned}">
            <div class="col-sm-2">
                <button type="button" id="btn-rRoomReserve" class="btn btn-default" name="rRoomReserve"
                        data-url="Library?command=reserveForRRoom">Reserve for room
                </button>
            </div>
        </c:if>


    </div>
    <div class="row description-text-section">
        <h3>Description</h3>
        <p>
            <c:out value="${specificBook.description}" escapeXml="true"/>
        </p>
    </div>
</div>


</body>
</html>
