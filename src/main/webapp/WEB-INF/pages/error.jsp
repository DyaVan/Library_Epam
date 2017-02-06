<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <%--<meta name="viewport" content="width=device-width, initial-scale=1">--%>
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

    <c:import url="parts/header.jsp"/>

    <div class="row name-section" style="margin-top: 50px">
        <h4>Error</h4>
    </div>

    <div class="row search-section">

        <video loop muted autoplay class="bg-video">
            <source src="videos/bg-video-error.mp4" type="video/mp4">
        </video>

        <div class="container-fluid search-section-hovered-error">
            <div class="row">
                <div class="col-md-offset-4 col-md-4" style="color: #e4b9c0">
                    <h1>ERROR</h1>
                </div>
            </div>
            <div class="row">

            </div>
        </div>
    </div>


</div>
</body>

</html>

