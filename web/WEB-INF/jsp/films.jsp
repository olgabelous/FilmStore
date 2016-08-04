<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.index.new2016" var="new2016" />
<fmt:message bundle="${loc}" key="locale.index.best" var="best" />

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request" />

<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h1 class="">${new2016}</h1></div>
        </div>
    </div>
</div>
<div class="p-y-3 section">
    <div class="container">
        <div class="row">

        <c:forEach var="film" items="${requestScope.filmlist}">
            <div class="col-md-2">
                <img src="../resources/images/Barbershop_small.jpg" class="img-fluid m-y">
                <p class="m-y-1">${film.title}</p>
            </div>
        </c:forEach>

        </div>
    </div>
</div>

</body>
</html>
