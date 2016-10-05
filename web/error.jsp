<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>FilmStore</title>
</head>
<body>

    <h2>Exception page</h2>

    <h3>${requestScope.errorMessage}</h3>
    <h3>${requestScope.exception.message}</h3>
    <h4>Cause1: ${requestScope.exception.cause.message}</h4>
    <h4>Cause2: ${requestScope.exception.cause.cause.message}</h4>
    <h4>Cause3: ${requestScope.exception.cause.cause.cause.message}</h4>
    <h4>Cause4: ${requestScope.exception.cause.cause.cause.cause.message}</h4>

</body>
</html>
