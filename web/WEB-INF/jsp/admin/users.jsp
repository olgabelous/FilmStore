<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="paginator" uri="/WEB-INF/tld/paginator" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources.locale" var="loc"/>

<fmt:message bundle="${loc}" key="locale.menu.users" var="users"/>
<fmt:message bundle="${loc}" key="locale.admin.edit_user" var="edit_user"/>
<fmt:message bundle="${loc}" key="locale.common.id" var="id"/>
<fmt:message bundle="${loc}" key="locale.common.action" var="action"/>
<fmt:message bundle="${loc}" key="locale.user.name" var="name"/>
<fmt:message bundle="${loc}" key="locale.user.email" var="email"/>
<fmt:message bundle="${loc}" key="locale.user.password" var="password"/>
<fmt:message bundle="${loc}" key="locale.user.phone" var="phone"/>
<fmt:message bundle="${loc}" key="locale.user.date_reg" var="date_registration"/>
<fmt:message bundle="${loc}" key="locale.user.role" var="role"/>
<fmt:message bundle="${loc}" key="locale.user.user" var="user"/>
<fmt:message bundle="${loc}" key="locale.user.admin" var="admin"/>
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
            <h1><b>${users}</b></h1>
            <br><br>
            <jsp:include page="../fragments/errorMessageAlert.jsp"/>
        </div>
    </header>

    <!-- Table-->
    <div id="content">
        <div class="container">
            <div class="row">
                <div class="col-md-10 clearfix" id="basket">
                        <div class="table table-responsive">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>#${id}</th>
                                    <th>${name}</th>
                                    <th>${email}</th>
                                    <th>${phone}</th>
                                    <th>${date_registration}</th>
                                    <th>${role}</th>
                                    <th colspan="2">${action}</th>

                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="userItem" items="${requestScope.userList}">
                                    <tr>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty userItem.photo}">
                                                    <img src="ImageController?img=${userItem.photo}&type=photo" class="w3-circle" alt="${userItem.name} photo">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="resources/images/user_placeholder.png" class="w3-circle">
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${userItem.id}</td>
                                        <td>${userItem.name}</td>
                                        <td>${userItem.email}</td>
                                        <td>${userItem.phone}</td>
                                        <td>${userItem.dateRegistration.toString().replace('T', ' ')}</td>
                                        <td>${userItem.role.name().toLowerCase()}</td>
                                        <td><a href="#myModal" data-toggle="modal" data-id="${userItem.id}" class="edit-user"
                                               data-user-name="${userItem.name}" data-user-email="${userItem.email}" data-user-phone="${userItem.phone}"
                                               data-user-photo="${userItem.photo}" data-user-date-reg="${userItem.dateRegistration}"
                                               data-user-pass="${userItem.pass}" data-user-role="${userItem.role.name()}"
                                               data-placement="top" title="${edit}">
                                                <i class="fa fa-pencil"></i></a></td>
                                        <td><a href="Controller?command=admin-delete-user&id=${userItem.id}"
                                               data-toggle="tooltip" data-placement="top" title="${delete}"><i
                                                class="fa fa-trash-o"></i></a></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    <hr>
                    <!-- /.table-responsive -->
                </div>
                <!-- /.col-md-9 -->
            </div>
            <div class="col-md-4 col-md-offset-4">
                <c:url var="searchUri" value="/Controller?command=admin-get-users&page=##"/>
                <paginator:display maxLinks="10" currPage="${requestScope.currentPage}"
                                   totalPages="${requestScope.totalPages}" uri="${searchUri}"/>
            </div>
        </div>
    </div>
    <!-- /.container -->
    <br>
    <br>
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
                <h4 class="modal-title">${edit_user}</h4>
            </div>
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="admin-update-user"/>

                <div class="modal-body">

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
                            <option value="USER">${user}</option>
                            <option value="ADMIN">${admin}</option>
                        </select>
                    </div>

                    <input type="hidden" name="id" id="id" value=""/>
                    <input type="hidden" name="photo" id="photo" value=""/>
                    <input type="hidden" name="dateReg" id="dateReg" value=""/>
                </div>

                <div class="modal-footer">
                    <button type="submit" class="btn btn-template-primary">${save}</button>
                    <button type="button" class="btn btn-template-main" data-dismiss="modal">${close}</button>
                </div>
            </form>
        </div>
        <!-- Modal content end-->

    </div>
</div>
<!-- Modal End -->
</body>
</html>




