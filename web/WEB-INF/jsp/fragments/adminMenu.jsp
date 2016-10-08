<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.menu.home" var="home" />
<fmt:message bundle="${loc}" key="locale.menu.users" var="users" />
<fmt:message bundle="${loc}" key="locale.menu.films" var="films" />
<fmt:message bundle="${loc}" key="locale.menu.genres" var="genres" />
<fmt:message bundle="${loc}" key="locale.menu.countries" var="countries" />
<fmt:message bundle="${loc}" key="locale.menu.art_people" var="art_people" />
<fmt:message bundle="${loc}" key="locale.menu.discount" var="discount" />
<fmt:message bundle="${loc}" key="locale.menu.comments" var="comments" />
<fmt:message bundle="${loc}" key="locale.menu.personal_info" var="personal_info"/>
<fmt:message bundle="${loc}" key="locale.menu.sign_out" var="sign_out" />

<!-- Sidenav/menu -->
<nav class="w3-sidenav w3-collapse w3-light-grey w3-animate-left" style="z-index:3;width:300px;" id="mySidenav"><br>

    <div class="w3-container">
        <a href="#" onclick="sidenav_close()" class="w3-hide-large w3-right w3-jumbo w3-padding" title="close menu">
            <i class="fa fa-remove"></i>
        </a>

        <div class="w3-row">
            <c:choose>
                <c:when test="${not empty sessionScope.user.photo}">
                    <img src="ImageController?img=${sessionScope.user.photo}&type=photo"
                         class="img-circle" style="width:55%;"/>
                </c:when>
                <c:otherwise>
                    <img src="resources/images/user_placeholder.png"
                         class="img-circle" style="width:55%; max-height: 180px"/>
                </c:otherwise>
            </c:choose></div>
        <div class="w3-row">
            <h4 class='w3-padding-0'><a href="Controller?command=get-user&id=${sessionScope.user.id}">${sessionScope.user.name}</a></h4>
        </div>
    </div>
    <br/>
    <a href="index.jsp" class="w3-padding" style="color: #38a7bb;">${home.toUpperCase()}</a>
    <a class="w3-padding" href="Controller?command=admin-get-users" >${users.toUpperCase()}</a>
    <a class="w3-padding" href="Controller?command=admin-get-films">${films.toUpperCase()}</a>
    <a class="w3-padding" href="Controller?command=admin-get-genres">${genres.toUpperCase()}</a>
    <a class="w3-padding" href="Controller?command=admin-get-countries">${countries.toUpperCase()}</a>
    <a class="w3-padding" href="Controller?command=admin-get-film-makers">${art_people.toUpperCase()}</a>
    <a class="w3-padding" href="Controller?command=admin-get-comments">${comments.toUpperCase()}</a>
    <a class="w3-padding" href="Controller?command=admin-get-discounts">${discount.toUpperCase()}</a>
    <a class="w3-padding" href="Controller?command=user-personal-info">${personal_info.toUpperCase()}</a>
    <a class="w3-padding" href="Controller?command=logout">${sign_out.toUpperCase()}</a>

</nav>

<!-- Overlay effect when opening sidenav on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="sidenav_close()" style="cursor:pointer"
     title="close side menu" id="myOverlay"></div>

