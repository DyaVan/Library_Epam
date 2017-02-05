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

    <link href="css\custom\library-header.css" rel="stylesheet">
    <link href="css\custom\news.css" rel="stylesheet">

    <script src="js\jquery-3.1.1.min.js"></script>
    <script src="js\bootstrap.min.js"></script>
    <script src="js\messages.js"></script>

</head>

<body>

<c:import url="WEB-INF/pages/parts/header.jsp"/>

<div class="container-fluid">
    <div class="row news-header">
        <div class="col-md-offset-4 col-md-4">
            <h4>News</h4>
        </div>
    </div>

    <div class="row">
        <div class="col-md-offset-3 col-md-6 add-news-section">
            <div class="row">
                <div class="col-md-4">
                    <img src="" alt="news image">
                    <button type="button" class="btn btn-default" name="button">Choose image</button>
                </div>
                <div class="col-md-8">
                    <div class="row">
                        <input type="text" class="form-control" placeholder="Enter news title" name="" value="">
                    </div>
                    <div class="row">
                        <textarea name="name" class="form-control" rows="2" placeholder="Enter news description"></textarea>
                    </div>
                    <div class="row">
                        <div class="col-md-offset-8 col-md-4">
                            <button type="button" class="btn btn-info" name="button">Add</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-offset-3 col-md-6 news-section">
            <div class="row">
                <div class="col-md-4">
                    <img src="" alt="news image">
                </div>
                <div class="col-md-8">
                    <div class="row">
                        <h4>News title</h4>
                    </div>
                    <div class="row">
                        <p>
                            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

</body>
</html>
