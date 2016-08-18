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
                <button type="submit" class="btn btn-primary">Show all comments</button>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Название фильма</th>
                        <th>Имя пользователя</th>
                        <th>Комментарий</th>
                        <th>Дата</th>
                        <th>Статус</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="commentItem" items="${requestScope.commentList}">
                        <tr>
                            <td>${commentItem.id}</td>
                            <td>${commentItem.film.title}</td>
                            <td>${commentItem.user.name}</td>
                            <td>${commentItem.mark}<br>${commentItem.text}</td>
                            <td>${commentItem.dateComment}</td>
                            <td>${commentItem.status.name()}</td>
                            <td><button type="submit" class="btn btn-primary">Apply</button></td>
                            <td><button type="submit" class="btn btn-primary">Refuse</button></td>
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
