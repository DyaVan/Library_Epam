<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>Book library</title>
    <link href="css\bootstrap.min.css" rel="stylesheet">
    <link href="css\bootstrap-theme.min.css" rel="stylesheet">

    <link href="css\custom\library-header.css" rel="stylesheet">

    <link href="css\bootstrap.min.css" rel="stylesheet">
    <link href="css\custom\login.css" rel="stylesheet">

    <script src="js\messages.js"></script>
</head>

<body>

<c:import url="WEB-INF/pages/parts/header.jsp"/>

<form method="POST" action="Library?command=registration">
    <div class="container-fluid login-section">

        <div class="row col-md-offset-4">
            <h3>Registration</h3>
        </div>

        <div class="row">
            <div class="col-md-offset-4 col-md-4">
                <input name="name" type="text" class="form-control" placeholder="Name...">
            </div>
        </div>
        <div class="row">
            <div class="col-md-offset-4 col-md-4">
                <input name="surname" type="text" class="form-control" placeholder="Surname...">
            </div>
        </div>
        <div class="row">
            <div class="col-md-offset-4 col-md-4">
                <input name="email" type="text" class="form-control" placeholder="Email...">
            </div>
        </div>
        <div class="row">
            <div class="col-md-offset-4 col-md-4">
                <input name="password" type="password" class="form-control" placeholder="Password...">
            </div>
        </div>
        <%--<div class="row">--%>
        <%--<div class="col-md-offset-4 col-md-4">--%>
        <%--<input type="password" class="form-control" placeholder="Repeat password...">--%>
        <%--</div>--%>
        <%--</div>--%>
        <div class="row">
            <a href="/logIn.jsp">
                <div class="col-md-offset-4 col-md-2">
                    <button type="button" class="btn btn-info" name="button">Login</button>
                </div>
            </a>
            <div class="col-md-2">
                <button type="submit" class="btn btn-success" name="button">Register</button>
            </div>
        </div>
    </div>
</form>

</body>

</html>
