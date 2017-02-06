<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
<div id="main-section" class="container-fluid">

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
               <form id="searchform">
                <div class="col-md-offset-5 col-md-2">
                    <select id="genreFilter" class="filter-select" name="">
                        <option value="" selected >All genres</option>
                        <option value="history">History</option>
                        <option value="poetry">Poetry</option>
                        <option value="fiction">Fiction</option>
                        <option value="childrens">Childrens</option>
                        <option value="fantasy">Fantasy</option>
                        <option value="philosophy">Philosophy</option>
                        <option value="economics">Economics</option>
                        <option value="non fiction">Non fiction</option>
                        <option value="horror">Horror</option>
                        <option value="dystopia">Dystopia</option>
                        <option value="contemporary">Contemporary</option>
                        <option value="romance">Romance</option>
                        <option value="programming">Programming</option>
                        <option value="business">Business</option>
                        <option value="classics">Classics</option>
                        <option value="science">Science</option>
                    </select>
                </div>
                <div class="col-md-offset-5 col-md-2">
                    <input id="searchLine" class="search-input" type="text" name="" value=""
                           placeholder="Input there text">
                </div>
                <div class="col-md-offset-6 col-md-1">
                    <button id="searchButton" class="search-btn" type="submit" name="button">Search</button>

                </div>
               </form>
            </div>
        </div>
    </div>

    <div class="row name-section">
        <h4>Library</h4>

        <a href="/VERY_FAST/menu.jsp">
            <button type="button" name="button">Menu</button>
        </a>

    </div>
</div>

<div class="container">
    <div class="row info-section">
        <div class="info-header-section">
            <h4>Books</h4>
        </div>
        <div class="container-fluid" id="loader-section">

        </div>
    </div>


</div>
</body>

</html>

