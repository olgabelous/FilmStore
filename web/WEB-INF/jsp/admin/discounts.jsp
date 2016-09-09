<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.admin.add_discount" var="addDiscount"/>
<fmt:message bundle="${loc}" key="locale.admin.edit_discount" var="editDiscount"/>
<fmt:message bundle="${loc}" key="locale.common.id" var="id"/>
<fmt:message bundle="${loc}" key="locale.discount.sum_from" var="sumFrom"/>
<fmt:message bundle="${loc}" key="locale.discount.value" var="value"/>
<fmt:message bundle="${loc}" key="locale.button.save" var="save"/>
<fmt:message bundle="${loc}" key="locale.button.edit" var="edit"/>
<fmt:message bundle="${loc}" key="locale.button.delete" var="delete"/>
<fmt:message bundle="${loc}" key="locale.button.close" var="close"/>

<jsp:include page="../fragments/bodyHeader.jsp"/>


<div class="section m-y-1">
    <div class="container">
        <div class="row">
            <jsp:include page="../fragments/adminMenu.jsp"/>
            <div class="col-lg-10 col-md-10">
                <form class="form-horizontal" id="detailsForm" action="Controller" method="post">
                    <input type="hidden" name="command" value="admin-save-discount" />
                    <h3>${addDiscount}</h3><br>
                    <div class="form-group">
                        <label for="sumFrom" class="control-label col-xs-2">${sumFrom}: </label>
                        <div class="col-xs-3">
                            <input type="number" class="form-control" id="sumFrom" name="sumFrom" placeholder="Sum">
                        </div>

                        <label for="value" class="control-label col-xs-1">${value}: </label>
                        <div class="col-xs-3">
                            <input type="number" class="form-control" id="value" name="value" placeholder="Value">
                        </div>

                        <button type="submit" class="btn btn-primary">${save}</button>

                    </div>
                </form>
                <hr>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>${id}</th>
                        <th>${sumFrom}</th>
                        <th>${value}</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="discountItem" items="${requestScope.discountList}">
                        <tr>
                            <td>${discountItem.id}</td>
                            <td>${discountItem.sumFrom}</td>
                            <td>${discountItem.value}</td>
                            <td><a href="#myModal" data-toggle="modal" data-id="${discountItem.id}" class="edit-discount btn btn-primary"
                                   data-discount-sum="${discountItem.sumFrom}" data-discount-value="${discountItem.value}">${edit}</a></td>
                            <td><a href="Controller?command=admin-delete-discount&id=${discountItem.id}" class="btn btn-danger">${delete}</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">${editDiscount}</h4>
            </div>
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="admin-save-discount" /> <br />
                <div class="modal-body">
                    <input type="hidden" name="id" id="id" value="" /> <br>

                    <div class="form-group">
                        <label for="sum">${sumFrom}:</label>
                        <input type="number" class="form-control" id="sum" name="sumFrom" placeholder="Enter sum from" value="">
                    </div>
                    <div class="form-group">
                        <label for="val">${value}:</label>
                        <input type="number" class="form-control" id="val" name="value" placeholder="Enter value" value="">
                    </div>

                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">${save}</button>
                        <button type="button" class="btn btn-danger" data-dismiss="modal">${close}</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
