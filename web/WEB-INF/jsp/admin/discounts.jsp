<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<jsp:include page="../fragments/bodyHeader.jsp"/>


<div class="section m-y-1">
    <div class="container">
        <div class="row">
            <jsp:include page="../fragments/adminMenu.jsp"/>
            <div class="col-lg-10 col-md-10">
                <form class="form-horizontal" id="detailsForm" action="/FilmStore/UserServlet" method="post">
                    <input type="hidden" name="command" value="admin-add-discount" />
                    <h3>Add discount</h3><br>
                    <div class="form-group">
                        <label for="sumFrom" class="control-label col-xs-2">Sum from: </label>
                        <div class="col-xs-3">
                            <input type="number" class="form-control" id="sumFrom" name="sumFrom" placeholder="Sum">
                        </div>

                        <label for="value" class="control-label col-xs-1">Value: </label>
                        <div class="col-xs-3">
                            <input type="number" class="form-control" id="value" name="value" placeholder="Value">
                        </div>

                        <button type="submit" class="btn btn-primary">Save</button>

                    </div>
                </form>
                <hr>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Сумма выкупа (от)</th>
                        <th>Процент скидки</th>
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
                            <td><button type="submit" class="btn btn-primary">Edit</button></td>
                            <td><a href="/FilmStore/UserServlet?command=admin-delete-discount&id=${discountItem.id}" class="btn btn-danger">Delete</a></td>
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
