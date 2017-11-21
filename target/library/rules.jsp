<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>Book library</title>

    <link href="css\bootstrap.min.css" rel="stylesheet">
    <link href="css\bootstrap-theme.min.css" rel="stylesheet">

    <link href="css\custom\library-header.css" rel="stylesheet">
    <%--<link href="css\custom\login.css" rel="stylesheet">--%>


    <script src="js\jquery-3.1.1.min.js"></script>
    <script src="js\bootstrap.min.js"></script>
    <script src="js\messages.js"></script>

</head>

<body style="background-color: #E2DCC5">

<div class="row">
    <c:import url="WEB-INF/pages/parts/header.jsp"/>
</div>


<div class="row container-fluid login-section">

    <div class="row col-md-offset-3" style="margin-top: 100px">
        <h3>Rules</h3>
    </div>
    <div class="col-md-offset-3 col-md-6" style="margin-top: 20px">
        <p>
            <span style="padding-left:4em"><b>The rules are</b></span> ... Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
        </p>
    </div>
</div>


</body>
</html>
