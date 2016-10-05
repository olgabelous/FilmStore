<!DOCTYPE HTML>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="fragments/headTag.jsp"/>

<body>
<div class="section">
    <div class="container">
        <div class="row" draggable="true">
            <div class="col-lg-4 col-lg-offset-4">
                <img src="resources/images/pic_error.png" alt="Not found 404"/>

                <h3>Page Not Found...</h3>

                <div class="error">
                    <a class="btn btn-info" href="index.jsp">Back To Home</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>