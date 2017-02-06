<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>Book library</title>
    <link href="css\bootstrap.min.css" rel="stylesheet">
    <link href="css\bootstrap-theme.min.css" rel="stylesheet">

    <link href="css\custom\library-header.css" rel="stylesheet">
    <link href="css\custom\login.css" rel="stylesheet">


    <script src="js\jquery-3.1.1.min.js"></script>
    <script src="js\bootstrap.min.js"></script>
    <script src="js\messages.js"></script>
    <script src="js\library-registration.js"></script>
</head>

<body>

<div class="row">
    <c:import url="WEB-INF/pages/parts/header.jsp"/>
</div>
    <div class="container-fluid login-section">


        <div class="row col-md-offset-4">
            <h3>Registration</h3>
        </div>
        <form id="register-form">
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
            <div class="row">
                <a href="/logIn.jsp">
                    <div class="col-md-offset-4 col-md-2">
                        <button type="button" class="btn btn-info" name="button">Login</button>
                    </div>
                </a>
                <div class="col-md-2">
                    <button type="submit" id="btn-register" class="btn btn-success" data-command="registration">
                        Register
                    </button>
                </div>
            </div>
        </form>
    </div>

</body>

</html>
