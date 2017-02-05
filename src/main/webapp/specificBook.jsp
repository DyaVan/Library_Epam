<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</head>

<body>

<c:import url="WEB-INF/pages/parts/header.jsp"/>

<div class="container-fluid book-description-section">
    <div class="row">
        <div class="col-sm-4">
            <img src="D:\EPAM\Library\src\main\webapp\WEB-INF\bookImages\78.jpg" alt="img\book-example">
        </div>
        <div class="col-sm-8">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-6">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-6">
                                    <p>ID:</p>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${specificBook.id}"/></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <p>Book name:</p>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${specificBook.name}"/></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <p>Author:</p>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${specificBook.author}"/></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <p>Year:</p>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${specificBook.year}"/></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <p>Genre:</p>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${specificBook.genre}"/></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <p>Pages:</p>
                                </div>
                                <div class="col-md-6">
                                    <p><c:out value="${specificBook.pages}"/></p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="container-fluid">
                            <div class="row">
                                <p>Your rate</p>
                                <p><c:out value="${specificBook.currentUserVote}"/> Zvezdochiki</p>
                            </div>
                            <div class="row">
                                <p>Overwall rate</p>
                                <p>
                                    <c:out value="${specificBook.readersRate}"/>/<c:out value="${specificBook.numberOfVotes}"/>
                                    Zvezdochiki
                                </p>
                            </div>
                            <div class="row">
                                <p>Not aviable *</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row description-text-section">
        <h3>Description</h3>
        <p>
            <c:out value="${specificBook.description}" escapeXml="true"/>
        </p>
    </div>
    <div class="row reserve-section">
        <div class="col-md-2">
            <select class="form-control" name="">
                <option value="1">1 day</option>
                <option value="2">2 days</option>
                <option value="3">3 days</option>
                <option value="4">4 days</option>
                <option value="5">5 days</option>
            </select>
        </div>
        <div class="col-sm-2">
            <button type="button" class="btn btn-default" name="button">Reserve for home</button>
        </div>
        <div class="col-sm-2">
            <button type="button" class="btn btn-default" name="button">Reserve for room</button>
        </div>
        <div class="col-sm-2">
            <button type="button" class="btn btn-default" name="button">Reserve for request</button>
        </div>
        <div class="col-sm-2">
            <button type="button" class="btn btn-default" name="button">Reserve for modify</button>
        </div>
    </div>
</div>


</body>
</html>
