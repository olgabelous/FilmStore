<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.user.my_page" var="my_page" />
<fmt:message bundle="${loc}" key="locale.user.name" var="name" />
<fmt:message bundle="${loc}" key="locale.user.email" var="email" />
<fmt:message bundle="${loc}" key="locale.user.phone" var="phone" />
<fmt:message bundle="${loc}" key="locale.user.edit" var="edit" />
<fmt:message bundle="${loc}" key="locale.user.my_orders" var="my_orders" />
<fmt:message bundle="${loc}" key="locale.user.my_orders_info" var="my_orders_info" />
<fmt:message bundle="${loc}" key="locale.user.wish_list" var="wish_list" />
<fmt:message bundle="${loc}" key="locale.user.wish_list_info" var="wish_list_info" />
<fmt:message bundle="${loc}" key="locale.user.favorite_genres" var="favorite_genres" />
<fmt:message bundle="${loc}" key="locale.user.favorite_genres_info" var="favorite_genres_info" />
<fmt:message bundle="${loc}" key="locale.user.my_comments" var="my_comments" />
<fmt:message bundle="${loc}" key="locale.user.my_comments_info" var="my_comments_info" />
<fmt:message bundle="${loc}" key="locale.user.my_discout" var="my_discout" />
<fmt:message bundle="${loc}" key="locale.user.sign_out" var="sign_out" />

<jsp:useBean id="user" class="by.epam.filmstore.domain.User" scope="session"/>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="section m-y-1">
    <div class="container">
        <div class="row">
            <div class="col-md-2">
                <c:choose>
                    <c:when test="${not empty user.photo}">
                        <img src="ImageController?img=15.jpg"
                             class="center-block img-circle img-fluid" width="250">
                    </c:when>
                    <c:otherwise>
                        <img src="https://pingendo.github.io/pingendo-bootstrap/assets/user_placeholder.png"
                             class="center-block img-circle img-fluid" width="200">
                    </c:otherwise>
                </c:choose>

                <div class="col-md-12 text-xs-center">

                    <h3>${user.name}</h3>

                </div>

            </div>

            <div class="col-md-8 col-lg-offset-2">
                <h1 class="p-y-2 text-xs-left">${my_page}</h1></div>


            <div class="col-md-5 col-lg-offset-2">
                <table class="table table-hover">
                    <tbody>
                    <tr>
                        <td>${name}</td>
                        <td>${user.name}</td>
                    </tr>
                    <tr>
                        <td>${email}</td>
                        <td>${user.email}</td>
                    </tr>
                    <tr>
                        <td>${phone}</td>
                        <td>${user.phone}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-md-2 col-md-offset-3"><a href="#" class="btn btn-block btn-primary" draggable="true">${edit}</a></div>
            </div>


        </div>
    </div>
<br>
<hr>
<div class="section ">
    <div class="container">
        <div class="row">
            <c:choose>
                <c:when test="${user.role.name()=='ADMIN'}">
                    <jsp:include page="fragments/adminMenu.jsp"/>
                </c:when>
                <c:otherwise>
                    <jsp:include page="fragments/userMenu.jsp"/>

            <div class="col-lg-4">
                <div class="col-md-12 text-xs-center">

                    <div><i class="fa fa-3x fa-shopping-cart text-center" aria-hidden="true"></i></div>

                    <h1><a href="Controller?command=user-get-orders" class="active nav-link">${my_orders}</a></h1>

                    <p>${my_orders_info}</p>
                </div>
            </div>
            <div class="col-lg-4">
                <div class="col-md-12 text-xs-center">
                    <div><i class="fa fa-3x fa-fw fa-heart"></i></div>

                    <h1><a href="Controller?command=user-get-comments" class="active nav-link">${wish_list}</a></h1>

                    <p>${wish_list_info}</p>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="section text-xs-center">
    <div class="container">
        <div class="row">
            <div class="col-md-3">

            </div>
            <div class="col-md-4  text-xs-center">
                <div><i class="fa fa-3x fa-percent" aria-hidden="true"></i></div>

                <h1><a class="nav-link" href="Controller?command=user-get-discount">${my_discout}</a></h1>

                <p>${favorite_genres_info}</p>
            </div>
            <div class="col-md-4  text-xs-center">
                <div><i class="fa fa-3x fa-commenting" aria-hidden="true"></i></div>

                <h1><a href="Controller?command=user-get-comments" class="active nav-link">${my_comments}</a></h1>

                <p>${my_comments_info}</p>
            </div>
            </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
