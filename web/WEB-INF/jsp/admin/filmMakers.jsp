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
                <a href="#myModal" data-toggle="modal" class="btn btn-primary">Add fiml maker</a>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Имя</th>
                        <th>Профессия</th>
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
                            <td><button type="submit" class="btn btn-primary">Edit</button></td>
                            <td><a href="FilmStore/UserServlet?command=admin-delete-film-maker&id=${filmMakerItem.id}" class="btn btn-danger">Delete</a></td>
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
                <h4 class="modal-title">Edit film maker</h4>
            </div>
            <form action="/FilmStore/UserServlet" method="post">
                <input type="hidden" name="command" value="admin-add-film-maker"/><br/>

                <div class="modal-body">

                    <div class="form-group">
                        <label for="name">Name:</label>
                        <input type="text" class="form-control" name="name" id="name" value="">
                    </div>

                    <div class="form-group">
                        <label for="profession">Profession:</label>
                        <select class="form-control" name="profession" id="profession">
                            <option value="actor" selected>ACTOR</option>
                            <option value="director">DIRECTOR</option>
                        </select>
                    </div>

                    <%--<input type="hidden" name="id" id="id" value=""/>
                    <input type="hidden" name="photo" id="photo" value=""/>
                    <input type="hidden" name="date-reg" id="date-reg" value=""/>--%>
                </div>

                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">Submit</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </form>
        </div>
        <!-- Modal content end-->
    </div>
</div>
<!-- Modal End -->

</body>
</html>