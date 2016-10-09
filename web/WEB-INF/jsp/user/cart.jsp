<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources.locale" var="loc"/>

<fmt:message bundle="${loc}" key="locale.cart.my_cart" var="my_cart"/>
<fmt:message bundle="${loc}" key="locale.cart.empty_cart" var="empty_cart"/>
<fmt:message bundle="${loc}" key="locale.film.film_title" var="film_title"/>
<fmt:message bundle="${loc}" key="locale.cart.film_price" var="film_price"/>
<fmt:message bundle="${loc}" key="locale.cart.discount" var="discount"/>
<fmt:message bundle="${loc}" key="locale.cart.price_with_discount" var="price_with_discount"/>
<fmt:message bundle="${loc}" key="locale.cart.total" var="total"/>
<fmt:message bundle="${loc}" key="locale.cart.continue_shop" var="continue_shop"/>
<fmt:message bundle="${loc}" key="locale.cart.pay" var="pay"/>
<fmt:message bundle="${loc}" key="locale.button.delete" var="delete"/>

<body>
<jsp:include page="../fragments/userMenu.jsp"/>
<!-- !PAGE CONTENT! -->
<div class="w3-main page-content">
    <!-- Header -->
    <header class="w3-container">
        <jsp:include page="../fragments/userSmallPic.jsp"/>

        <div class="w3-section w3-bottombar">
            <h1><b>${my_cart}</b></h1>
            <br><br>
        </div>
    </header>

    <div id="content">
        <div class="container">

            <div class="row">
                <c:choose>
                <c:when test="${empty requestScope.orderList}">
                    <div class="col-md-12">
                        <h4 class="text-muted lead">${empty_cart}</h4>
                    </div>
                </c:when>
                <c:otherwise>

                <div class="col-md-9 clearfix" id="basket">

                    <div class="box">

                        <form action="Controller" method="post">
                            <div class="table table-responsive">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th colspan="2">${film_title}</th>
                                        <th>${film_price}</th>
                                        <th>${discount}</th>
                                        <th colspan="2">${price_with_discount}</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="order" items="${requestScope.orderList}">
                                        <tr>
                                            <td>
                                                <a href="Controller?command=get-film-by-id&id=${order.film.id}">
                                                    <img src="ImageController?img=${order.film.poster}&type=poster"
                                                         alt="">
                                                </a>
                                            </td>
                                            <td><a href="Controller?command=get-film-by-id&id=${order.film.id}">
                                                <input hidden name="id" value="${order.id}">${order.film.title}</a>
                                            </td>
                                            <td>$${order.film.price}</td>
                                            <td>$<fmt:formatNumber var="expiry"
                                                                   value="${(order.film.price - order.sum)* 100.0 / 100.0}"
                                                                   maxFractionDigits="2"/>${expiry}
                                            </td>
                                            <td>$${order.sum}</td>
                                            <td><a href="Controller?command=user-delete-order&id=${order.id}" data-toggle="tooltip"
                                                   data-placement="top" title="${delete}"><i
                                                    class="fa fa-trash-o"></i></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th colspan="4">${total}</th>
                                        <th colspan="2">$${requestScope.totalSum}</th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <input hidden name="command" value="user-pay-order">
                            <input hidden name="orderList" value="${requestScope.orderList}">
                            <input hidden name="totalSum" value="${requestScope.totalSum}">

                            <div class="box-footer">
                                <div class="pull-left">
                                    <a href="Controller?command=get-filtered-films" class="btn btn-default"><i
                                            class="fa fa-chevron-left"></i> ${continue_shop}</a>
                                </div>
                                <div class="pull-right">

                                    <button type="submit" class="btn btn-template-main">${pay} <i
                                            class="fa fa-chevron-right"></i>
                                    </button>
                                </div>
                            </div>
                            <!-- /.box -->
                        </form>
                        </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- /.container -->
</div>
<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>
