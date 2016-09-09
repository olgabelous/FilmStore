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
                             class="center-block img-circle img-fluid m-y m-y-1" width="200" height="200">
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="col-md-9">
                <h1 class="p-y-2 text-xs-left">Моя корзина</h1>

                <div class="col-lg-9">
                    <form action="Controller" method="post">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Название фильма</th>
                            <th>Цена фильма</th>
                            <th>Сумма со скидкой</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="order" items="${requestScope.orderList}">
                            <tr>
                                <td>${order.film.title}</td>
                                <td>${order.film.price}</td>
                                <td>${order.sum}$</td>
                                <td><a href="Controller?command=user-delete-order&id=${order.id}" class="btn btn-danger">Delete</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <%--<jsp:useBean id="totalSum" class="java.lang.Double" scope="request"/>--%>
                        <%--<c:set var="totalSum" scope="request"/>--%>
                    <div>Сумма к оплате: ${requestScope.totalSum}</div>
                        <input hidden name="command" value="user-pay-order">
                        <input hidden name="order-list" value="${requestScope.orderList}">
                        <input hidden name="total-sum" value="${requestScope.totalSum}">
                        <button type="submit" class="btn btn-success">Оплатить</button>
                    </form>
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
