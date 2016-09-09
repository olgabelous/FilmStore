<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.admin.users" var="users" />
<fmt:message bundle="${loc}" key="locale.admin.films" var="films" />
<fmt:message bundle="${loc}" key="locale.admin.genres" var="genres" />
<fmt:message bundle="${loc}" key="locale.admin.countries" var="countries" />
<fmt:message bundle="${loc}" key="locale.admin.art_people" var="film_makers" />
<fmt:message bundle="${loc}" key="locale.admin.discount" var="discount" />
<fmt:message bundle="${loc}" key="locale.admin.comments" var="comments" />
<fmt:message bundle="${loc}" key="locale.admin.sign_out" var="sign_out" />

<div class="col-lg-2 col-md-2">
    <ul class="nav nav-pills  nav-stacked">
        <li class="nav-item">
            <a href="Controller?command=admin-get-users" class="active nav-link">${users}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?command=admin-get-films">${films}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?command=admin-get-genres">${genres}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?command=admin-get-countries">${countries}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?command=admin-get-film-makers">${film_makers}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?command=admin-get-comments">${comments}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?command=admin-get-discounts">${discount}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?command=logout">${sign_out}</a>
        </li>
    </ul>
</div>
