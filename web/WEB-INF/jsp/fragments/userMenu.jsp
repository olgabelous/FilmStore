<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc"/><!-- locale_ru -->

<fmt:message bundle="${loc}" key="locale.menu.home" var="home"/>
<fmt:message bundle="${loc}" key="locale.menu.cart" var="cart"/>
<fmt:message bundle="${loc}" key="locale.menu.my_orders" var="my_orders"/>
<fmt:message bundle="${loc}" key="locale.menu.wish_list" var="wish_list"/>
<fmt:message bundle="${loc}" key="locale.menu.my_comments" var="my_comments"/>
<fmt:message bundle="${loc}" key="locale.menu.my_discount" var="my_discount"/>
<fmt:message bundle="${loc}" key="locale.menu.personal_info" var="personal_info"/>
<fmt:message bundle="${loc}" key="locale.menu.sign_out" var="sign_out"/>

<!-- Sidenav/menu -->
<nav class="w3-sidenav w3-collapse  w3-light-grey w3-animate-left" style="z-index:3;width:300px;" id="mySidenav"><br>

    <div class="w3-container">
        <a href="#" onclick="close()" class="w3-hide-large w3-right w3-jumbo w3-padding" title="close menu">
            <i class="fa fa-remove"></i>
        </a>

        <div class="w3-row">
            <c:choose>
                <c:when test="${not empty sessionScope.user.photo}">
                    <img src="ImageController?img=${sessionScope.user.photo}&type=photo"
                         class="img-circle" style="width:55%; max-height: 180px;"/>
                </c:when>
                <c:otherwise>
                    <img src="resources/images/user_placeholder.png"
                         class="img-circle" style="width:55%;"/>
                </c:otherwise>
            </c:choose></div>
        <div class="w3-row">
            <h4 class='w3-padding-0'><a href="Controller?command=get-user&id=${sessionScope.user.id}">${sessionScope.user.name}</a></h4>
        </div>
    </div>
    <br/>
    <a href="index.jsp" class="w3-padding" style="color: #38a7bb;">${home.toUpperCase()}</a>
    <a href="Controller?command=user-cart" class="w3-padding">${cart.toUpperCase()}</a>
    <a href="Controller?command=user-get-favorite-films" class="w3-padding">${wish_list.toUpperCase()}</a>
    <a href="Controller?command=user-get-orders" class="w3-padding">${my_orders.toUpperCase()}</a>
    <a href="Controller?command=user-get-comments" class="w3-padding">${my_comments.toUpperCase()}</a>
    <a href="Controller?command=user-get-discount" class="w3-padding">${my_discount.toUpperCase()}</a>
    <a href="Controller?command=user-personal-info" class="w3-padding">${personal_info.toUpperCase()}</a>
    <a href="Controller?command=logout" class="w3-padding">${sign_out.toUpperCase()}</a>

</nav>

<!-- Overlay effect when opening sidenav on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="close()" style="cursor:pointer"
     title="close side menu" id="myOverlay"></div>


