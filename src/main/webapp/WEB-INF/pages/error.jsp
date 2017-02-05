<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%--<%@page import="com.diachuk.dao.implementations.MySql.MySqlBookDAO" %>--%>

<body>


<h1 align="center">This is error page!</h1>
<h2 align="center">Welcome</h2>

<br/>
<br/>ERRORR
<br/>

<c:out value="${currentUser.name}"/>
<c:out value="${errorMessage}"/>



</body>
</html>
