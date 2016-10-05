<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}"/><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc"/><!-- locale_ru -->

<fmt:message bundle="${loc}" key="locale.menu.my_discount" var="my_discount"/>
<fmt:message bundle="${loc}" key="locale.discount.amount" var="amount"/>
<fmt:message bundle="${loc}" key="locale.discount.sum_from" var="sum_from"/>
<fmt:message bundle="${loc}" key="locale.discount.value" var="value"/>
<fmt:message bundle="${loc}" key="locale.discount.from" var="from"/>
<fmt:message bundle="${loc}" key="locale.discount.message" var="message"/>

<body class="w3-content" style="max-width:1600px">

<jsp:include page="../fragments/userMenu.jsp"/>
<!-- !PAGE CONTENT! -->
<div class="w3-main w3-white" style="margin-left:300px">

    <!-- Header -->
    <header class="w3-container">
        <jsp:include page="../fragments/userSmallPic.jsp"/>

        <div class="w3-section w3-bottombar">
            <h1><b>${my_discount}</b></h1>


            <h3><span class="label label-danger">${requestScope.discount}%</span></h3>
            <br>
            <h4 class="">${amount} <span class="label label-success">${requestScope.totalAmount}$</span></h4>

        </div>
    </header>

    <!-- Table-->
    <div id="content">
        <div class="container">

            <div class="row">
                <div class="col-md-12">
                    <p class="text-muted lead">${message}</p>
                </div>

                <div class="col-md-9 clearfix">

                    <div class="box">

                        <div class="table table-responsive">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>${sum_from}</th>
                                    <th>${value}</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="discountItem" items="${requestScope.discountList}">
                                    <tr>
                                        <td>${from} ${discountItem.sumFrom}$</td>
                                        <td>${discountItem.value}%</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <!-- /.box -->
                    </div>

                    <!-- /.table-responsive -->

                </div>
                <!-- /.col-md-9 -->
            </div>

        </div>
    </div>
    <!-- /.container -->
    <jsp:include page="../fragments/footer.jsp"/>

    <!-- End page content -->
</div>
</body>
</html>



