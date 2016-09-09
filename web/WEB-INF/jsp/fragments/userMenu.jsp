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


<div class="col-lg-3">
    <ul class="nav nav-pills  nav-stacked">
        <li class="nav-item">
            <a href="Controller?command=user-cart" class="active nav-link">Корзина</a>
        </li>
        <li class="nav-item">
            <a href="Controller?command=user-get-orders" class="active nav-link">${my_orders}</a>
        </li>
       <%-- <li class="nav-item">
            <a class="nav-link" href="Controller?command=user-get-favorite-genres">${favorite_genres}</a>
        </li>--%>
        <li class="nav-item">
            <a class="nav-link" href="Controller?command=user-get-comments">${my_comments}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?command=user-get-discount">${my_discout}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="Controller?command=logout">${sign_out}</a>
        </li>
    </ul>
</div>




