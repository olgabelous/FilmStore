<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.user.my_page" var="my_page" />
<fmt:message bundle="${loc}" key="locale.user.name" var="name" />
<fmt:message bundle="${loc}" key="locale.user.email" var="email" />
<fmt:message bundle="${loc}" key="locale.user.phone" var="phone" />
<fmt:message bundle="${loc}" key="locale.user.edit" var="edit" />
<fmt:message bundle="${loc}" key="locale.user.my_orders" var="my_orders" />

<jsp:useBean id="user" class="by.epam.filmstore.domain.User" scope="session"/>
<jsp:include page="../fragments/bodyHeader.jsp"/>

<div class="section m-y-1">
    <div class="container">
        <div class="row">

            <div class="col-md-3">
                <c:choose>
                    <c:when test="${not empty user.photo}">
                        <img src="ImageController?img=15.jpg"
                             class="center-block img-circle img-fluid" width="250">
                    </c:when>
                    <c:otherwise>
                        <img src="https://pingendo.github.io/pingendo-bootstrap/assets/user_placeholder.png"
                             class="center-block img-circle img-fluid" width="200" height="200">
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="col-md-9">
                <h1 class="p-y-2 text-xs-left">${my_orders}</h1>

                    <div class="col-lg-9">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Название фильма</th>
                                <th>Дата</th>
                                <th>Сумма</th>
                                <th>Статус</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="order" items="${requestScope.orderList}">
                                <tr>
                                    <td>${order.film.title}</td>
                                    <td>${order.dateSale}</td>
                                    <td>${order.sum}$</td>
                                    <td>${order.status}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
            </div>
        </div>

        <div class="row">
            <%--<div class="col-md-3 text-xs-center">
                <h3>${user.name}</h3>
            </div>--%>
            <div class="col-md-3 text-xs-center">
                <h3>User</h3>
            </div>
        </div>
        <div class="row">
            <jsp:include page="../fragments/userMenu.jsp"/>
        </div>


  </div>
</div>


</body>
</html>
