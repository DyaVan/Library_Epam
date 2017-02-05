<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">

<head>
    <%--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
    <meta http-equiv="content-type" content="text/html; charset=cp1251">

    <title>Book library</title>

    <link href="css\bootstrap.min.css" rel="stylesheet">
    <link href="css\bootstrap-theme.min.css" rel="stylesheet">

    <link href="css\custom\library-index.css" rel="stylesheet">
    <link href="css\custom\library-header.css" rel="stylesheet">

    <script src="js\jquery-3.1.1.min.js"></script>
    <script src="js\bootstrap.min.js"></script>
    <script src="js\library-index.js"></script>
    <script src="js\messages.js"></script>

</head>


<body>
<div class="container-fluid">

    <c:import url="WEB-INF/pages/parts/header.jsp"/>

    <div class="row search-section">
        <video loop muted autoplay class="bg-video">
            <source src="videos/bg-video.mp4" type="video/mp4">
        </video>

        <div class="container-fluid search-section-hovered">
            <div class="row">
                <div class="col-md-offset-4 col-md-4">
                    <img class="logo-img" src="img\logo.png" alt="Logo image">
                </div>
            </div>
            <div class="row">
                <div class="col-md-offset-5 col-md-2">
                    <select id="genreFilter" class="filter-select" name="">
                        <option value="" disabled selected>Select filter</option>
                        <option value="history">History</option>
                        <option value="poetry">Poetry</option>
                        <option value="">Test value 3</option>
                        <option value="">Test value 4</option>
                    </select>
                </div>
                <div class="col-md-offset-5 col-md-2">
                    <input id="searchLine" class="search-input" type="text" name="" value=""
                           placeholder="Input there text">
                </div>
                <div class="col-md-offset-6 col-md-1">
                    <button id="searchButton" class="search-btn" type="button" name="button">Search</button>
                    <input type="hidden" id="savedSearchLine" value=""/>
                    <input type="hidden" id="offset" value="0"/>
                </div>
            </div>
        </div>
    </div>
    <div class="row name-section">
        <h4>Library</h4>
    </div>
</div>

<div class="container">
    <div class="row info-section">
        <div class="info-header-section">
            <h4>Books</h4>
        </div>
        <div class="container-fluid" id="loader-section">
            <c:import url="WEB-INF/pages/parts/booksIterator.jsp"/>
        </div>
    </div>


</div>
</body>

</html>



