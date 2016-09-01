<%--
  Created by IntelliJ IDEA.
  User: NotePad
  Date: 28.07.2016
  Time: 21:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FilmStore</title>
</head>
<body>
<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>
<jsp:useBean id="exception" class="java.lang.Exception" scope="request"/>

    <h2>Exception</h2>
    <h3>${errorMessage}</h3>
    <h3>${exception.message}</h3>

    <c:forEach var="stackTrace" items="${exception.stackTrace}" >
        <p>${stackTrace}</p>
    </c:forEach>
</body>
</html>
