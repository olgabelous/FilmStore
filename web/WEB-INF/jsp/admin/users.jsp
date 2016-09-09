<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.admin.edit_user" var="editUser"/>
<fmt:message bundle="${loc}" key="locale.common.id" var="id"/>
<fmt:message bundle="${loc}" key="locale.user.name" var="name"/>
<fmt:message bundle="${loc}" key="locale.user.email" var="email"/>
<fmt:message bundle="${loc}" key="locale.user.password" var="password"/>
<fmt:message bundle="${loc}" key="locale.user.phone" var="phone"/>
<fmt:message bundle="${loc}" key="locale.user.date_reg" var="dateRegistration"/>
<fmt:message bundle="${loc}" key="locale.user.role" var="role"/>
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
                <br>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>${id}</th>
                        <th>${name}</th>
                        <th>${email}</th>
                        <th>${phone}</th>
                        <th>${dateRegistration}</th>
                        <th>${role}</th>
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
                            <td>${userItem.role.name().toLowerCase()}</td>
                            <td><a href="#myModal" data-toggle="modal" data-id="${userItem.id}" class="edit-user btn btn-primary"
                                   data-user-name="${userItem.name}" data-user-email="${userItem.email}" data-user-phone="${userItem.phone}"
                                   data-user-photo="${userItem.photo}" data-user-date-reg="${userItem.dateRegistration}"
                                   data-user-pass="${userItem.pass}" data-user-role="${userItem.role.name()}">${edit}</a></td>
                            <td><a href="/Controller?command=admin-delete-user&id=${userItem.id}" class="btn btn-danger">${delete}</a></td>
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
                <h4 class="modal-title">${editUser}</h4>
            </div>
            <form action="/Controller" method="post">
                <input type="hidden" name="command" value="admin-update-user"/><br/>

                <div class="modal-body">
                    <jsp:useBean id="user" class="by.epam.filmstore.domain.User" scope="request"/>

                    <div class="form-group">
                        <label for="name">${name}:</label>
                        <input type="text" class="form-control" name="name" id="name" value="">
                    </div>
                    <div class="form-group">
                        <label for="email">${email}:</label>
                        <input type="email" class="form-control" name="email" id="email" placeholder="Enter email"
                               value="">
                    </div>
                    <div class="form-group">
                        <label for="pwd">${password}:</label>
                        <input type="password" class="form-control" name="password" id="pwd"
                               placeholder="Enter password" value="">
                    </div>
                    <div class="form-group">
                        <label for="phone">${phone}:</label>
                        <input type="text" class="form-control" name="phone" id="phone" placeholder="Enter phone"
                               value="">
                    </div>
                    <div class="form-group">
                        <label for="role">${role}:</label>
                        <select class="form-control" name="role" id="role">
                            <option value="USER">USER</option>
                            <option value="ADMIN">ADMIN</option>
                        </select>
                    </div>

                    <input type="hidden" name="id" id="id" value=""/>
                    <input type="hidden" name="photo" id="photo" value=""/>
                    <input type="hidden" name="date-reg" id="date-reg" value=""/>
                </div>

                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">${save}</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">${close}</button>
                </div>
            </form>
        </div>
        <!-- Modal content end-->

    </div>
</div>
<!-- Modal End -->

</body>
</html>
