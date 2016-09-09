<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.admin.add_genre" var="addGenre"/>
<fmt:message bundle="${loc}" key="locale.admin.edit_genre" var="editGenre"/>
<fmt:message bundle="${loc}" key="locale.common.id" var="id"/>
<fmt:message bundle="${loc}" key="locale.genre.genre" var="genreName"/>
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
                    <input type="hidden" name="command" value="admin-save-genre"/> <br/>

                    <div class="form-group">
                        <label for="genre" class="control-label col-xs-3">${addGenre}: </label>

                        <div class="col-xs-5">
                            <input class="form-control" id="genre" name="genreName" placeholder="Genre">
                        </div>
                        <button type="submit" class="btn btn-primary">${save}</button>

                    </div>
                </form>
                <hr>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>${id}</th>
                        <th>${genreName}</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="genreItem" items="${requestScope.genreList}">
                        <tr>
                            <td>${genreItem.id}</td>
                            <td>${genreItem.genreName}</td>
                            <td><a href="#myModal" data-toggle="modal" data-id="${genreItem.id}"
                                   class="edit-genre btn btn-primary"
                                   data-genre-name="${genreItem.genreName}">${edit}</a></td>
                            <td><a href="Controller?command=admin-delete-genre&id=${genreItem.id}"
                                   class="btn btn-danger">${delete}</a></td>
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
                <h4 class="modal-title">${editGenre}</h4>
            </div>
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="admin-save-genre"/> <br/>

                <div class="modal-body">
                    <input type="hidden" name="id" id="id" value=""/> <br/>

                    <div class="form-group">
                        <label for="name">${genreName}:</label>
                        <input type="text" class="form-control" id="name" name="genreName" value="">
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
