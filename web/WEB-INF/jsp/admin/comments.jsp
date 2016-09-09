<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.film.film_title" var="filmTitle"/>
<fmt:message bundle="${loc}" key="locale.user.username" var="userName"/>
<fmt:message bundle="${loc}" key="locale.comment.comment" var="comment"/>
<fmt:message bundle="${loc}" key="locale.comment.date" var="date"/>
<fmt:message bundle="${loc}" key="locale.comment.status" var="status"/>
<fmt:message bundle="${loc}" key="locale.button.apply" var="apply"/>
<fmt:message bundle="${loc}" key="locale.button.refuse" var="refuse"/>
<fmt:message bundle="${loc}" key="locale.button.show_all_comments" var="showAllComments"/>

<jsp:include page="../fragments/bodyHeader.jsp"/>


<div class="section m-y-1">
    <div class="container">
        <div class="row">
            <jsp:include page="../fragments/adminMenu.jsp"/>
            <div class="col-lg-10 col-md-10">
                <button type="submit" class="btn btn-primary">${showAllComments}</button>
                <form action="Controller" method="post" id="commentForm">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>${filmTitle}</th>
                            <th>${userName}</th>
                            <th>${comment}</th>
                            <th>${date}</th>
                            <th>${status}</th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <input hidden name="command" value="admin-update-comment">
                        <c:forEach var="commentItem" items="${requestScope.commentList}">
                            <input hidden name="filmId" value="${commentItem.film.id}">
                            <input hidden name="userId" value="${commentItem.user.id}">
                            <tr>
                                <td>${commentItem.film.title}</td>
                                <td>${commentItem.user.name}</td>
                                <td>${commentItem.mark}<br>${commentItem.text}</td>
                                <td>${commentItem.dateComment}</td>
                                <td>${commentItem.status.name()}</td>
                                <td>
                                    <button type="submit" class="btn btn-success apply">${apply}</button>
                                </td>
                                <td>
                                    <button type="submit" class="btn btn-danger reject">${refuse}</button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
