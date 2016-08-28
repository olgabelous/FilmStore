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
                <button type="submit" class="btn btn-primary">Add</button>
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
                            <td><a href="FilmStore/UserServlet?command=admin-delete-discount&id=${discountItem.id}" class="btn btn-danger">Delete</a></td>
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
