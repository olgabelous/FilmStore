<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.common.id" var="id" />
<fmt:message bundle="${loc}" key="locale.film.title" var="title" />
<fmt:message bundle="${loc}" key="locale.film.year" var="year" />
<fmt:message bundle="${loc}" key="locale.film.country" var="country" />
<fmt:message bundle="${loc}" key="locale.film.age_restr" var="ageRestriction" />
<fmt:message bundle="${loc}" key="locale.film.price" var="price" />
<fmt:message bundle="${loc}" key="locale.button.edit" var="edit" />
<fmt:message bundle="${loc}" key="locale.button.delete" var="delete" />
<fmt:message bundle="${loc}" key="locale.button.add_film" var="addFilm" />

<jsp:include page="../fragments/bodyHeader.jsp"/>


<div class="section m-y-1">
    <div class="container">
        <div class="row">
            <jsp:include page="../fragments/adminMenu.jsp"/>
            <div class="col-lg-10 col-md-10">
                <%--<a href="#myModal" data-toggle="modal" class="btn btn-primary">Add Film</a>--%>
                <a href="Controller?command=admin-add-page-film" class="btn btn-primary">${addFilm}</a>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>${id}</th>
                        <th>${title}</th>
                        <th>${year}</th>
                        <th>${country}</th>
                        <th>${ageRestriction}</th>
                        <th>${price}</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="filmItem" items="${requestScope.filmList}">
                        <tr>
                            <td>${filmItem.id}</td>
                            <td><a href="Controller?command=get-film-by-id&id=${filmItem.id}">${filmItem.title}</a></td>
                            <td>${filmItem.year}</td>
                            <td>${filmItem.country.countryName}</td>
                            <td>${filmItem.ageRestriction}</td>
                            <td>${filmItem.price}</td>
                            <td><a href="Controller?command=admin-add-page-film&id=${filmItem.id}" class="btn btn-primary">${edit}</a></td>
                            <td><a href="Controller?command=admin-delete-film&id=${filmItem.id}" class="btn btn-danger">${delete}</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

</body>
</html>