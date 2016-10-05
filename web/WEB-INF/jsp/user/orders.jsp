<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}"/><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc"/><!-- locale_ru -->

<fmt:message bundle="${loc}" key="locale.menu.my_orders" var="my_orders"/>
<fmt:message bundle="${loc}" key="locale.film.film_title" var="film_title"/>
<fmt:message bundle="${loc}" key="locale.order.date" var="date"/>
<fmt:message bundle="${loc}" key="locale.order.sum" var="sum"/>
<fmt:message bundle="${loc}" key="locale.order.status" var="status"/>
<fmt:message bundle="${loc}" key="locale.order.paid" var="paid"/>
<fmt:message bundle="${loc}" key="locale.order.watch" var="watch"/>
<fmt:message bundle="${loc}" key="locale.order.message" var="message"/>

<body class="w3-content" style="max-width:1600px">

<jsp:include page="../fragments/userMenu.jsp"/>

<!-- !PAGE CONTENT! -->
<div class="w3-main w3-white" style="margin-left:300px">

    <!-- Header -->
    <header class="w3-container ">
        <jsp:include page="../fragments/userSmallPic.jsp"/>

        <div class="w3-section w3-bottombar">
            <h1><b>${my_orders}</b></h1>
            <br><br>
        </div>
    </header>

    <!-- Table-->

    <div id="content">
        <div class="container">


            <div class="row">
                <div class="col-md-9" id="basket">

                    <p class="text-muted lead">${message}</p>

                    <div class="box">

                        <div class="table table-responsive">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th colspan="2">${film_title}</th>
                                    <th>${date}</th>
                                    <th>${sum}</th>
                                    <th>${status}</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="order" items="${requestScope.orderList}">
                                    <tr>
                                        <td>
                                            <a href="Controller?command=get-film-by-id&id=${order.film.id}">
                                                <img src="ImageController?img=${order.film.poster}&type=poster" alt="poster">
                                            </a>
                                        </td>
                                        <td>${order.film.title}</td>
                                        <td>${order.dateSale.toString().replace('T', ' ')}</td>
                                        <td>$${order.sum}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${order.status=='PAID'}">
                                                    <span class="label label-success">${paid}</span>                                                </c:when>
                                                <c:otherwise>
                                                    <span class="label label-info">${order.status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                            </td>
                                        <td><a href="Controller?command=user-watch-film&order=${order.id}&id=${order.film.id}"
                                                target="_blank" class="btn btn-template-main btn-sm">${watch}!</a>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.table-responsive -->

                    </div>
                    <!-- /.box -->

                </div>
                <!-- /.col-md-9 -->
            </div>
        </div>
    </div>
    <jsp:include page="../fragments/footer.jsp"/>

    <!-- End page content -->
</div>
</body>
</html>




