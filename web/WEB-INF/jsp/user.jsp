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

<jsp:useBean id="user" class="by.epam.filmstore.domain.User" scope="request"/>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="section m-y-1">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <c:choose>
                    <c:when test="${not empty user.photo}">
                        <img src="${user.photo}"
                             class="center-block img-circle img-fluid m-y m-y-1">
                    </c:when>
                    <c:otherwise>
                        <img src="https://pingendo.github.io/pingendo-bootstrap/assets/user_placeholder.png"
                             class="center-block img-circle img-fluid m-y m-y-1">
                    </c:otherwise>
                </c:choose>

                <div class="col-md-12 text-xs-center">

                    <h3>${user.name}</h3>

                </div>

            </div>

            <div class="col-md-8">
                <h1 class="p-y-2 text-xs-left">${my_page}</h1></div>


            <div class="col-md-8 text-xs-left">
                <div class="td-data">
                    <div>${name}</div>
                    <div>${email}</div>
                    <div>${phone}</div>
                </div>
                <div class="dd-data">
                    <div>${user.name}</div>
                    <div>${user.email}</div>
                    <div>${user.phone}</div>
                </div>
            </div>
            <div class="col-md-2"><a href="#" class="btn btn-block btn-primary" draggable="true">${edit}</a></div>

        </div>

    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row" draggable="true">
            <div class="col-lg-4">
                <ul class="nav nav-pills  nav-stacked" draggable="true">
                    <li class="nav-item">
                        <a href="#" class="active nav-link">${my_orders}</a>
                    </li>
                    <li class="nav-item">
                        <a href="#" class="nav-link">${wish_list}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">${favorite_genres}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">${my_comments}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">${my_discout}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">${sign_out}</a>
                    </li>
                </ul>
            </div>
            <div class="col-lg-4">
                <div class="col-md-12 text-xs-center">
                    <i class="fa fa-5x fa-fw fa-star m-y-1 m-y-lg text-primary"></i>

                    <h1>${my_orders}</h1>

                    <p>${my_orders_info}</p>
                </div>
            </div>
            <div class="col-lg-4">
                <div class="col-md-12 text-xs-center">
                    <i class="fa fa-5x fa-fw fa-heart m-y-1 m-y-lg text-primary"></i>

                    <h1>${wish_list}</h1>

                    <p>${wish_list_info}</p>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="section text-xs-center">
    <div class="container">
        <div class="row">
            <div class="col-md-4">

            </div>
            <div class="col-md-4">
                <i class="fa fa-5x fa-fw fa-star m-y-1 m-y-lg text-primary"></i>

                <h1>${favorite_genres}</h1>

                <p>${favorite_genres_info}</p>
            </div>
            <div class="col-md-4">
                <i class="fa fa-5x fa-fw fa-star m-y-1 m-y-lg text-primary"></i>

                <h1>${my_comments}</h1>

                <p>${my_comments_info}</p>
            </div>

        </div>
    </div>
</div>
</body>
</html>
