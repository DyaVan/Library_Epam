<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Book library</title>

    <link href="css\bootstrap.min.css" rel="stylesheet">
    <link href="css\bootstrap-theme.min.css" rel="stylesheet">

    <link href="css\custom\library-header.css" rel="stylesheet">
    <link href="css\custom\login.css" rel="stylesheet">


    <script src="js\jquery-3.1.1.min.js"></script>
    <script src="js\bootstrap.min.js"></script>
    <script src="js\library-login.js"></script>
    <script src="js\messages.js"></script>

</head>

<body>

<div class="row">
    <c:import url="WEB-INF/pages/parts/header.jsp"/>
</div>


<div class="row container-fluid login-section">

    <div class="row col-md-offset-4">
        <h3>Log In</h3>
    </div>
    <form id="logIn-form">
        <div class="row">
            <div class="col-md-offset-4 col-md-4">
                <input id="email-input" name="email" type="text" class="form-control" placeholder="Email...">
            </div>
        </div>

        <div class="row">
            <div class="col-md-offset-4 col-md-4">
                <input id="password-input" name="password" type="password" class="form-control"
                       placeholder="Password...">
            </div>
        </div>

        <div class="row">
            <a href="registration.jsp">
                <div class="col-md-offset-4 col-md-2">
                    <button type="button" class="btn btn-danger">Register</button>
                </div>
            </a>
            <div class="col-md-2">
                <button data-command="logIn" id="btn-logIn" type="submit" class="btn btn-info">Login</button>
            </div>
        </div>
    </form>
</div>


</body>
</html>
