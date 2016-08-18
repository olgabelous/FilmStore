<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc"/><!-- locale_ru -->

<fmt:message bundle="${loc}" key="locale.user.my_orders" var="my_orders"/>
<fmt:message bundle="${loc}" key="locale.user.favorite_genres" var="favorite_genres"/>
<fmt:message bundle="${loc}" key="locale.user.my_comments" var="my_comments"/>
<fmt:message bundle="${loc}" key="locale.user.my_discout" var="my_discout"/>
<fmt:message bundle="${loc}" key="locale.user.sign_out" var="sign_out"/>


<div class="col-lg-2">
    <ul class="nav nav-pills  nav-stacked">
        <li class="nav-item">
            <a href="/FilmStore/UserServlet?command=get-orders-of-user" class="active nav-link">${my_orders}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/FilmStore/UserServlet?command=get-favorite-genres">${favorite_genres}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/FilmStore/UserServlet?command=get-comments-of-user">${my_comments}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/FilmStore/UserServlet?command=get-discount">${my_discout}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/FilmStore/UserServlet?command=logout">${sign_out}</a>
        </li>
    </ul>
</div>




