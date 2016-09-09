<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.admin.add_art_person" var="addArtPerson"/>
<fmt:message bundle="${loc}" key="locale.admin.edit_art_person" var="editArtPerson"/>
<fmt:message bundle="${loc}" key="locale.filmmaker.name" var="name"/>
<fmt:message bundle="${loc}" key="locale.filmmaker.profession" var="profession"/>
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
                <a href="#myModal" data-toggle="modal" class="btn btn-primary">${addArtPerson}</a>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>${id}</th>
                        <th>${name}</th>
                        <th>${profession}</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="filmMakerItem" items="${requestScope.filmMakerList}">
                        <tr>
                            <td>${filmMakerItem.id}</td>
                            <td>${filmMakerItem.name}</td>
                            <td>${filmMakerItem.profession.name()}</td>
                            <td><a href="#myModal" data-toggle="modal" data-id="${filmMakerItem.id}" class="edit-film-maker btn btn-primary"
                                   data-film-maker-name="${filmMakerItem.name}" data-film-maker-prof="${filmMakerItem.profession.name()}" >${edit}</a></td>
                            <td><a href="/Controller?command=admin-delete-film-maker&id=${filmMakerItem.id}" class="btn btn-danger">${delete}</a></td>
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
                <h4 class="modal-title">${editArtPerson}</h4>
            </div>
            <form action="/Controller" method="post">
                <input type="hidden" name="command" value="admin-save-film-maker"/><br/>

                <div class="modal-body">
                    <input type="hidden" name="id" id="id" value="" /> <br />

                    <div class="form-group">
                        <label for="name">${name}:</label>
                        <input type="text" class="form-control" name="name" id="name" value="">
                    </div>

                    <div class="form-group">
                        <label for="profession">${profession}:</label>
                        <select class="form-control" name="profession" id="profession">
                            <option value="ACTOR" selected>ACTOR</option>
                            <option value="DIRECTOR">DIRECTOR</option>
                        </select>
                    </div>

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