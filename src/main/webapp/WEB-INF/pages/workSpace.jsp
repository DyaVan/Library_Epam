<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/WEB-INF/myTags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="src/main/webapp/css/custom/library-workers-page.css"/>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Work Space</title>

    <link href="css\bootstrap.min.css" rel="stylesheet">
    <link href="css\bootstrap-theme.min.css" rel="stylesheet">
    <link href="css\bootstrap-datepicker3.min.css" rel="stylesheet">

    <link href="css\custom\library-header.css" rel="stylesheet">
    <link href="css\custom\library-workers-page.css" rel="stylesheet">

    <script src="js\jquery-3.1.1.min.js"></script>
    <script src="js\bootstrap.min.js"></script>
    <script src="js\bootstrap-datepicker.min.js"></script>
    <script src="locales\bootstrap-datepicker.en-GB.min.js"></script>
    <script src="js\messages.js"></script>

    <script src="js\workspace\library-workers-page.js"></script>
    <script src="js\workspace\work-space-issue.js"></script>
    <script src="js\workspace\work-space-return.js"></script>
    <script src="js\workspace\work-space-books.js"></script>
    <%--<script src="js\workspace\work-space-answer.js"></script>--%>
    <%--<script src="js\workspace\work-space-config.js"></script>--%>
    <%--<script src="js\workspace\work-space-roles.js"></script>--%>
    <%--<script src="js\workspace\work-space-userConfig.js"></script>--%>

</head>
<body>

<div class="container">

    <!-- header  ********************************************************-->
    <c:import url="parts/header.jsp"/>
    <!-- *************************************************************** -->

    <div class="row">

        <ul class="nav nav-stacked nav-tab-custom col-md-2 col-xs-3">
            <li class="active"><a data-toggle="tab" href="#book-issue">Book issue</a></li>
            <li><a data-toggle="tab" href="#book-return">Book return</a></li>
            <li><a data-toggle="tab" href="#books">Books</a></li>
            <li><a data-toggle="tab" href="#answer-questions">Answer Questions</a></li>
            <li><a data-toggle="tab" href="#history">History</a></li>
            <li><a data-toggle="tab" href="#library-config">Library config</a></li>
            <li><a data-toggle="tab" href="#users-configurations">Users configurations</a></li>
            <li><a data-toggle="tab" href="#roles">Roles</a></li>
        </ul>

        <div class="tab-content col-md-10 col-xs-9 block">

            <div id="book-issue" class="tab-pane fade in active ">
                <div class="container-fluid">
                    <h3>BOOK ISSUE</h3>

                    <div class="row measured-block">
                        <div class="col-md-3">
                            <h4>Reservation ID:</h4>
                        </div>
                        <div class="col-md-3">
                            <input id="reservationId-input" type="text" class="form-control" name="reservationId"
                                   value="">
                        </div>
                        <div class="col-md-3">
                            <select id="reservation-type" class="form-control" name="reservation-type">
                                <option value="Home">Home</option>
                                <option value="RRoom">Reading room</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <button id="btn-search-reservation" class="btn btn-default ">Search</button>
                        </div>
                    </div>


                    <div class="row measured-block">
                        <div class="col-md-2">
                            <p><strong>Name:</strong></p>
                            <div id="name-place-issue">

                            </div>
                        </div>
                        <div class="col-md-2">
                            <p><strong>Surname:</strong></p>
                            <div id="surname-place-issue">

                            </div>
                        </div>
                        <div class="col-md-4">
                            <p><strong>Email:</strong></p>
                            <div id="email-place-issue">

                            </div>
                        </div>
                    </div>


                    <div class="row table-responsive history-section">
                        <table class="table table-hover table-bordered table-striped">
                            <thead>
                            <tr>
                                <td>Action</td>
                                <td>ID</td>
                                <td>Type</td>
                                <td>Book Id</td>
                                <td>Book name</td>
                                <td>Author</td>
                                <td>Due date</td>
                            </tr>
                            </thead>
                            <tbody id="issueTable">
                            </tbody>
                        </table>
                    </div>

                    <div class="row">
                        <div class="col-md-3">
                            <button id="btn-issue" class="btn btn-success">Issue</button>
                        </div>
                    </div>

                </div>
            </div>

            <div id="book-return" class="tab-pane fade">
                <div class="container-fluid">
                    <h3>RETURN BOOK</h3>

                    <div class="row measured-block">
                        <div class="col-md-3">
                            <h4>Book loan ID:</h4>
                        </div>
                        <div class="col-md-3">
                            <input id="bookLoanId-input" type="text" class="form-control" name="bookLoanId" value="">
                        </div>
                        <div class="col-md-3">
                            <button id="btn-search-bookLoan" class="btn btn-default ">Search</button>
                        </div>
                    </div>


                    <div class="row measured-block">
                        <div class="col-md-2">
                            <p><strong>Name:</strong></p>
                            <div id="name-place-return">

                            </div>
                        </div>
                        <div class="col-md-2">
                            <p><strong>Surname:</strong></p>
                            <div id="surname-place-return">

                            </div>
                        </div>
                        <div class="col-md-4">
                            <p><strong>Email:</strong></p>
                            <div id="email-place-return">

                            </div>
                        </div>
                    </div>


                    <div class="row table-responsive history-section">
                        <table class="table table-hover table-bordered table-striped">
                            <thead>
                            <tr>
                                <td>Action</td>
                                <td>ID</td>
                                <td>Type</td>
                                <td>Book Id</td>
                                <td>Book name</td>
                                <td>Author</td>
                                <td>Took date</td>
                                <td>Due date</td>
                            </tr>
                            </thead>
                            <tbody id="returnTable">
                            </tbody>
                        </table>
                    </div>

                    <div class="row">
                        <div class="col-md-3">
                            <button id="btn-return" class="btn btn-success">Accept return</button>
                        </div>
                    </div>

                </div>
            </div>

            <div id="history" class="tab-pane fade">
                <h3>HISTORY</h3>

                <div class="container-fluid">
                    <div class="row history-search-section">
                        <div class="col-md-3">
                            <div class="input-group date">
                                <input type="text" class="form-control" placeholder="From">
                                <span class="input-group-addon">
                  <i class="glyphicon glyphicon-th"></i>
                </span>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="input-group date">
                                <input type="text" class="form-control" placeholder="To">
                                <span class="input-group-addon">
                  <i class="glyphicon glyphicon-th"></i>
                </span>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <input type="text" class="form-control" placeholder="Search for...">
                        </div>
                        <div class="col-md-2">
                            <button type="button" class="btn btn-default" name="button">
                                Search
                            </button>
                        </div>
                        <div class="col-md-1">
                            <input type="checkbox"> Out of Date
                        </div>
                    </div>

                    <div class="row table-responsive history-section">
                        <table class="table table-hover table-bordered table-striped">
                            <thead>
                            <tr>
                                <td>Action</td>
                                <td>ID</td>
                                <td>Type</td>
                                <td>Book Id</td>
                                <td>Book name</td>
                                <td>Author</td>
                                <td>Took date</td>
                                <td>Due date</td>
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

            </div>

            <div id="books" class="tab-pane fade">
                <h3>BOOKS</h3>

                <div class="container-fluid">
                    <div class="row">
                        <button id="btn-add-book" type="button" class="btn btn-success btn-add-book" name="button">
                            Add new book
                        </button>
                    </div>

                    <div id="book-editor-section" class="row">
                        <form id="form-book-editor" enctype="multipart/form-data">
                            <div class="row">
                                <div class="col-md-4">
                                    <my:bookImage/>
                                    <input id="file-input" type="file"/>
                                </div>
                                <div class="col-md-8">
                                    <div class="row book-description-item">
                                        <div class="col-md-6">
                                            <strong>Name:</strong>
                                        </div>
                                        <div class="col-md-6">
                                            <input id="name-mod-book" type="text" class="form-control" name="bookName"
                                                   value="">
                                        </div>
                                    </div>
                                    <div class="row book-description-item">
                                        <div class="col-md-6">
                                            <strong>Author</strong>
                                        </div>
                                        <div class="col-md-6">
                                            <input id="author-mod-book" type="text" class="form-control" name="author"
                                                   value="">
                                        </div>
                                    </div>
                                    <div class="row book-description-item">
                                        <div class="col-md-6">
                                            <strong>Year</strong>
                                        </div>
                                        <div class="col-md-6">
                                            <input id="year-mod-book" type="number"
                                                   class="form-control" name="year" value="">
                                        </div>
                                    </div>
                                    <div class="row book-description-item">
                                        <div class="col-md-6">
                                            <strong>Genre</strong>
                                        </div>
                                        <div class="col-md-6">
                                            <select id="genre-mod-book" class="form-control" name="genre">
                                                <c:forEach var="genreItem" items="${genres}">
                                                    <option value="${genreItem}">${genreItem}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="row book-description-item">
                                        <div class="col-md-6">
                                            <strong>Pages</strong>
                                        </div>
                                        <div class="col-md-6">
                                            <input id="pages-mod-book" type="number" class="form-control" name="pages"
                                                   value="">
                                        </div>
                                    </div>
                                    <div class="row book-description-item">
                                        <div class="col-md-6">
                                            <strong>Amount for Home</strong>
                                        </div>
                                        <div class="col-md-6">
                                            <input id="amountForHome-mod-book" type="number" class="form-control"
                                                   name="amountForHome" value="">
                                        </div>
                                    </div>
                                    <div class="row book-description-item">
                                        <div class="col-md-6">
                                            <strong>Amount in Reading room:</strong>
                                        </div>
                                        <div class="col-md-6">
                                            <input id="amountInRRoom-mod-book" type="number" class="form-control"
                                                   name="amountInRRoom" value="">
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-md-12">
                                    <h4>Description</h4>
                                    <textarea id="description-mod-book" name="description"
                                              class="form-cotrol book-description-area" rows="8"></textarea>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-offset-8 col-md-4">
                                    <div class="btn-group" role="group" aria-label="...">
                                        <button id="btn-save-book" type="submit" class="btn btn-default col-md-2">Save
                                        </button>
                                        <button id="btn-cancel-book" type="button" class="btn btn-danger col-md-2">
                                            Cancel
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="container-fluid">
                    <div class="row block-tem">
                        <div class="col-xs-3 col-md-1">
                            <strong>ID: </strong>
                            <p>12345</p>
                        </div>
                        <div class="col-xs-4 col-md-3">
                            <strong>Author: </strong>
                            <p>Makishimu</p>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <strong>Name: </strong>
                            <p>Calambur</p>
                        </div>
                        <div class="col-md-3">
                            <button type="button" class="btn btn-default btn-book-modify" name="button">
                                Modify
                            </button>
                            <%--<button type="button" class="btn btn-default btn-book-save hide" name="button">--%>
                            <%--Save--%>
                            <%--</button>--%>
                        </div>
                    </div>
                </div>
            </div>

            <div id="answer-questions" class="tab-pane fade">
                <h3>ANSWER QUESTION</h3>
            </div>

            <div id="library-config" class="tab-pane fade">
                <h3>LIBRARY CONFIG</h3>
            </div>

            <div id="users-configurations" class="tab-pane fade">
                <h3>USERS CONFIG</h3>
            </div>

            <div id="roles" class="tab-pane fade">
                <h3>ROLES</h3>

                <div class="row block-tem">
                    <div class="col-md-2">
                        <p><strong>ID:</strong></p>
                        <p>132456</p>
                    </div>
                    <div class="col-md-3">
                        <p><strong>Name:</strong></p>
                        <input type="text" class="form-control role-editing-item" disabled name="" value="Gay">
                    </div>
                    <div class="col-md-3">
                        <p><strong>Value:</strong></p>
                        <input type="number" class="form-control role-editing-item" disabled name="" value="5" min="1"
                               max="5">
                    </div>
                    <div class="col-md-3">
                        <button id="btn-modify-role" type="button" class="btn btn-default" name="button">Modify</button>
                        <div id="btn-group-role" class="btn-group" role="group" aria-label="...">
                            <button id="btn-save-role" type="button" class="btn btn-default col-md-2">Save</button>
                            <button id="btn-cancel-role" type="button" class="btn btn-danger col-md-2">Cancel</button>
                        </div>
                    </div>
                </div>

            </div>

        </div>
    </div>

</div>


</body>
</html>
