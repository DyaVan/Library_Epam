<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="navbar navbar-fixed-top navbar-custom">
    <div class="row">
        <div class="container-fluid">
            <div class="navbar-header header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#header-menu"
                        aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>

            <div id="header-menu" class="collapse navbar-collapse">
                <div class="col-md-2">
                    <a class="header-link" href="/Library?command=goToWorkSpace">For workers</a>
                </div>
                <div class="col-md-offset-2 col-md-1">
                    <a class="header-link" href="/rules.jsp">Rules</a>
                </div>
                <div class="col-md-1">
                    <a class="header-link" href="/news.jsp">News</a>
                </div>
                <div class="col-md-1">
                    <a class="header-link" href="/index.jsp">Home</a>
                </div>
                <div class="col-md-1">
                    <a class="header-link" href="/Library?command=getTop10&genreFilter=">Top 10</a>
                </div>
                <div class="col-md-2">
                    <a class="header-link" href="/Library?command=goToMySpace">My Space</a>
                </div>

                <div class="col-md-2">

                    <c:choose>
                        <c:when test="${currentUser != null}">
                            <a class="header-link" href="/Library?command=logOut">LogOut</a>
                        </c:when>
                        <c:when test="${empty currentUser}">
                            <a class="header-link" href="/logIn.jsp">LogIn/Reg</a>
                        </c:when>
                    </c:choose>
                </div>

            </div>

        </div>

    </div>

</div>

<div id="error-message" class="message error-message">
    <h4>ERROR</h4>
    <div id="error-message-text"></div>

</div>

<div id="success-message" class="message success-message">
    <h4>SUCCESS</h4>
    <div id="success-message-text"></div>
</div>






