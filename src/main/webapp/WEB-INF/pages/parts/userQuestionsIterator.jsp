<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="applicato/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>

<data>
<c:forEach var="userQuestion" items="${userQuestions}">
    <div class="row">
        <div class="col-md-4">
            <strong>${userQuestion.subject}</strong>
            <br/>
        </div>
        <div class="col-md-4">
            <strong>DATE:</strong>
            <p><fmt:formatDate value="${userQuestion.date}" timeStyle="short"/> </p>
        </div>
        <div class="col-md-4">
            <strong>Is Answered:</strong>
            <input type="checkbox" value="${userQuestion.isAnswered}" disabled="disabled"/>
        </div>
    </div>
    <div class="row">
        <p class="question-text">
                ${userQuestion.text}
        </p>
    </div>
    <div class="col-xs-offset-8 col-md-4 btn-group" role="group" aria-label="...">
        <button type="button" class="btn btn-default col-md-2">View</button>
        <button type="button" class="btn btn-danger col-md-2">Delete</button>
    </div>
</c:forEach>
</data>