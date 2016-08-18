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
                <button type="submit" class="btn btn-primary">Add user</button>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Имя</th>
                        <th>Email</th>
                        <th>Телефон</th>
                        <th>Дата регистрации</th>
                        <th>Роль</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="userItem" items="${requestScope.userList}">
                        <tr>
                            <td>${userItem.id}</td>
                            <td>${userItem.name}</td>
                            <td>${userItem.email}</td>
                            <td>${userItem.phone}</td>
                            <td>${userItem.dateRegistration}</td>
                            <td>${userItem.role.name()}</td>
                            <td><button type="submit" class="btn btn-primary">Edit</button></td>
                            <td><button type="submit" class="btn btn-primary">Delete</button></td>
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
