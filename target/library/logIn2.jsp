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

</head>

<body>

<div class="row">
<c:import url="WEB-INF/pages/parts/header.jsp"/>
</div>


<div class="row container-fluid login-section">

    <form action="/Library?command=logIn" method="POST">

        <div class="row col-md-offset-4">
            <h3>Log In</h3>
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
            <a href="registration.jsp">
                <div class="col-md-offset-4 col-md-2">
                    <button type="button" class="btn btn-danger" >Register</button>
                </div>
            </a>
            <div class="col-md-2">
                <button type="submit" class="btn btn-info">Login</button>
            </div>
        </div>
    </form>
</div>


</body>
</html>
