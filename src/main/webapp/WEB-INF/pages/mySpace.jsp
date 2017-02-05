<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>My space</title>

    <link href="css\bootstrap.min.css" rel="stylesheet">
    <link href="css\bootstrap-theme.min.css" rel="stylesheet">

    <link href="css\custom\library-my-space.css" rel="stylesheet">
    <link href="css\custom\library-header.css" rel="stylesheet">

    <script src="js\jquery-3.1.1.min.js"></script>
    <script src="js\bootstrap.min.js"></script>
    <script src="js\myspace\library-my-space.js"></script>
    <script src="js\messages.js"></script>

</head>

<body>

<div class="container">

    <!-- header  ********************************************************-->
    <c:import url="parts/header.jsp"/>
    <!-- *************************************************************** -->

    <div class="row">

        <ul class="nav nav-stacked nav-tab-custom col-md-2 col-xs-12">
            <li class="active"><a data-toggle="tab" href="#my-history-tab">My history</a></li>
            <li><a data-toggle="tab" href="#current-tab">Current</a></li>
            <li><a data-toggle="tab" href="#my-questions-tab">My questions</a></li>
            <li><a data-toggle="tab" href="#ask-question-tab">Ask question</a></li>
            <li><a data-toggle="tab" href="#book-read-tab">Book read</a></li>
            <li><a data-toggle="tab" href="#books-to-read">Books to read</a></li>
            <li><a data-toggle="tab" href="#configure-profile-tab">Configure profile</a></li>
        </ul>

        <hr>

        <div class="tab-content col-md-10 col-xs-12 block">

            <div id="my-history-tab" class="tab-pane fade in active ">
                <h4>MY HISTORY</h4>
                <div class="table-responsive">
                    <table class="table table-hover table-bordered table-striped">
                        <thead>
                        <tr>
                            <td>Action</td>
                            <td>Type</td>
                            <td>ID</td>
                            <td>Book Id</td>
                            <td>Book name</td>
                            <td>Author</td>
                            <td>Took date</td>
                            <td>... date</td>
                            <td>Return date</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Max</td>
                            <td>Calambur</td>
                            <td>12345</td>
                            <td>78954</td>
                            <td>Kak ubitsa</td>
                            <td>Max</td>
                            <td>45/45//87955</td>
                            <td>465</td>
                            <td>12/45/2222154</td>
                        </tr>
                        <tr>
                            <td>Max</td>
                            <td>Calambur</td>
                            <td>12345</td>
                            <td>78954</td>
                            <td>Kak ubitsa</td>
                            <td>Max</td>
                            <td>45/45//87955</td>
                            <td>465</td>
                            <td>12/45/2222154</td>
                        </tr>
                        <tr>
                            <td>Max</td>
                            <td>Calambur</td>
                            <td>12345</td>
                            <td>78954</td>
                            <td>Kak ubitsa</td>
                            <td>Max</td>
                            <td>45/45//87955</td>
                            <td>465</td>
                            <td>12/45/2222154</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div id="current-tab" class="tab-pane fade">
                <h4>CURRENT</h4>
                <div class="table-responsive">
                    <table class="table table-hover table-bordered table-striped">
                        <thead>
                        <tr>
                            <td>Action</td>
                            <td>Type</td>
                            <td>ID</td>
                            <td>Book Id</td>
                            <td>Book name</td>
                            <td>Author</td>
                            <td>Took date</td>
                            <td>... date</td>
                            <td>Return date</td>
                            <td></td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Max</td>
                            <td>Calambur</td>
                            <td>12345</td>
                            <td>78954</td>
                            <td>Kak ubitsa</td>
                            <td>Max</td>
                            <td>45/45//87955</td>
                            <td>465</td>
                            <td>12/45/2222154</td>
                            <td>
                                <button class="btn btn-default" name="button">Cancel</button>
                            </td>
                        </tr>
                        <tr>
                            <td>Max</td>
                            <td>Calambur</td>
                            <td>12345</td>
                            <td>78954</td>
                            <td>Kak ubitsa</td>
                            <td>Max</td>
                            <td>45/45//87955</td>
                            <td>465</td>
                            <td>12/45/2222154</td>
                            <td>
                                <button class="btn btn-default" name="button">Cancel</button>
                            </td>
                        </tr>
                        <tr>
                            <td>Max</td>
                            <td>Calambur</td>
                            <td>12345</td>
                            <td>78954</td>
                            <td>Kak ubitsa</td>
                            <td>Max</td>
                            <td>45/45//87955</td>
                            <td>465</td>
                            <td>12/45/2222154</td>
                            <td>
                                <button class="btn btn-default" name="button">Cancel</button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div id="my-questions-tab" class="tab-pane fade">
                <h4>MY QUESTION</h4>
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-1">Subject</div>
                        <div class="col-md-4">
                            <input type="text" class="form-control" name="" value="">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-1">Question</div>
                        <div class="col-md-4">
                            <textarea class="form-control" rows="3"></textarea>
                        </div>
                        <div class="col-md-2">
                            <button type="button" class="btn btn-default" name="button">Ask</button>
                        </div>
                    </div>
                    <div class="row">
                        <!-- Пример одного вопроса -->
                        <div class="container-fluid asked-question" id="question-loader-section">

                        </div>
                    </div>
                </div>
            </div>

            <div id="ask-question-tab" class="tab-pane fade"></div>

            <div id="book-read-tab" class="tab-pane fade"></div>

            <div id="books-to-read" class="tab-pane fade"></div>

            <div id="configure-profile-tab" class="tab-pane fade">
                <h4>CONFIGURE PROFILE</h4>
                <div class="container-fluid configure-profile-section">
                    <form action="/Library?command=changeProfile">
                        <div class="row">
                            <div class="col-md-offset-3 col-md-6">
                                <input name="name" type="text" class="form-control" placeholder="Name">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-offset-3 col-md-6">
                                <input name="surname" type="text" class="form-control" placeholder="Surname">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-offset-3 col-md-6">
                                <input name="email" type="email" class="form-control" placeholder="Email">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-offset-3 col-md-6">
                                <input name="password" type="password" class="form-control" placeholder="Password">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-offset-7 col-md-2">
                                <button type="submit" class="btn btn-default" name="button">Save</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>


</body>

</html>
