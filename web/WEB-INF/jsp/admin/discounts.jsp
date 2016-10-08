<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.menu.discount" var="discounts"/>
<fmt:message bundle="${loc}" key="locale.admin.add_discount" var="add_discount"/>
<fmt:message bundle="${loc}" key="locale.admin.edit_discount" var="edit_discount"/>
<fmt:message bundle="${loc}" key="locale.common.id" var="id"/>
<fmt:message bundle="${loc}" key="locale.common.action" var="action"/>
<fmt:message bundle="${loc}" key="locale.discount.sum_from" var="sumFrom"/>
<fmt:message bundle="${loc}" key="locale.discount.value" var="value"/>
<fmt:message bundle="${loc}" key="locale.discount.enter_sum" var="enter_sum"/>
<fmt:message bundle="${loc}" key="locale.discount.enter_value" var="enter_value"/>
<fmt:message bundle="${loc}" key="locale.button.save" var="save"/>
<fmt:message bundle="${loc}" key="locale.button.edit" var="edit"/>
<fmt:message bundle="${loc}" key="locale.button.delete" var="delete"/>
<fmt:message bundle="${loc}" key="locale.button.close" var="close"/>

<body class="w3-content" style="max-width:1600px">

<jsp:include page="../fragments/adminMenu.jsp"/>
<!-- !PAGE CONTENT! -->
<div class="w3-main page-content">

    <!-- Header -->
    <header class="w3-container">
        <jsp:include page="../fragments/userSmallPic.jsp"/>

        <div class="w3-section w3-bottombar">
            <h1>${discounts}</h1>
            <br><br>
            <jsp:include page="../fragments/errorMessageAlert.jsp"/>
        </div>
    </header>

    <!-- Table-->
    <div id="content">
        <div class="container">

            <div class="row">

                <div class="col-md-9 clearfix">
                    <form class="form-horizontal" id="detailsForm" action="Controller" method="post">
                        <input type="hidden" name="command" value="admin-save-discount"/>

                        <div class="col-lg-12">
                            <h3 class="text-left">${add_discount}</h3><br>
                        </div>

                        <div class="form-group">
                            <label for="sumFrom" class="control-label col-xs-2">${sumFrom}: </label>

                            <div class="col-xs-3">
                                <input type="number" class="form-control" id="sumFrom" name="sumFrom" placeholder="${enter_sum}">
                            </div>

                            <label for="value" class="control-label col-xs-1">${value}: </label>

                            <div class="col-xs-3">
                                <input type="number" class="form-control" id="value" name="value" placeholder="${enter_value}">
                            </div>

                            <button type="submit" class="btn btn-template-primary">${save}</button>

                        </div>
                    </form>

                    <div class="box">
                        <div class="table table-responsive">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>${id}</th>
                                    <th>${sumFrom}</th>
                                    <th>${value}</th>
                                    <th colspan="2" class="text-center">${action}</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="discountItem" items="${requestScope.discountList}">
                                    <tr>
                                        <td>${discountItem.id}</td>
                                        <td>${discountItem.sumFrom}</td>
                                        <td>${discountItem.value}</td>
                                        <td class="text-center"><a href="#myModal" data-toggle="modal" data-id="${discountItem.id}"
                                               class="edit-discount" data-discount-sum="${discountItem.sumFrom}"
                                               data-discount-value="${discountItem.value}"
                                               data-placement="top" title="${edit}"><i class="fa fa-pencil"></i></a></td>
                                        <td class="text-center"><a href="Controller?command=admin-delete-discount&id=${discountItem.id}"
                                               data-toggle="tooltip" data-placement="top" title="${delete}"><i
                                                class="fa fa-trash-o"></i></a></td>
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
    <!-- End page content -->
</div>
<jsp:include page="../fragments/footer.jsp"/>

<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">${edit_discount}</h4>
            </div>
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="admin-save-discount"/> <br/>

                <div class="modal-body">
                    <input type="hidden" name="id" id="id" value=""/> <br>

                    <div class="form-group">
                        <label for="sum">${sumFrom}:</label>
                        <input type="number" class="form-control" id="sum" name="sumFrom" placeholder="${enter_sum}"
                               value="">
                    </div>
                    <div class="form-group">
                        <label for="val">${value}:</label>
                        <input type="number" class="form-control" id="val" name="value" placeholder="${enter_value}"
                               value="">
                    </div>

                    <div class="modal-footer">
                        <button type="submit" class="btn btn-template-primary">${save}</button>
                        <button type="button" class="btn btn-template-main" data-dismiss="modal">${close}</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>




