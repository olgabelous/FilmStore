<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.user.my_discout" var="my_discount" />
<fmt:message bundle="${loc}" key="locale.user.name" var="name" />
<fmt:message bundle="${loc}" key="locale.user.my_orders" var="my_orders" />

<jsp:useBean id="user" class="by.epam.filmstore.domain.User" scope="session"/>
<jsp:include page="../fragments/bodyHeader.jsp"/>

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
                             class="img-circle img-fluid" width="200">
                    </c:otherwise>
                </c:choose>

                <div class="col-md-12 text-xs-center">
                    <h3>${user.name}</h3>
                </div>

            </div>

            <div class="col-md-8 col-lg-offset-2">
                <h1 class="">${my_discount}</h1>
                <c:out value="${requestScope.discount}%"/>
                <h3 class="">Сумма выкупа</h3>
                <c:out value="${requestScope.totalAmount}$"/>
            </div>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <jsp:include page="../fragments/userMenu.jsp"/>
            <div class="col-lg-7">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Сумма выкупа</th>
                        <th>Процент скидки</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="discountItem" items="${requestScope.discountList}">
                        <tr>
                            <td>от ${discountItem.sumFrom}$</td>
                            <td>${discountItem.value}%</td>
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
