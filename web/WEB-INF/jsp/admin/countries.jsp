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
                <br>
                <form class="form-horizontal" id="detailsForm" action="/FilmStore/UserServlet" method="post">
                    <input type="hidden" name="command" value="admin-add-country" /> <br />

                    <div class="form-group">
                        <label for="country" class="control-label col-xs-3">Add country: </label>

                        <div class="col-xs-5">
                            <input class="form-control" id="country" name="countryName" placeholder="Country">
                        </div>
                        <button type="submit" class="btn btn-primary">Save</button>

                    </div>
                </form>
                <hr>

                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Название</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="countryItem" items="${requestScope.countryList}">
                        <tr>
                            <td>${countryItem.id}</td>
                            <td>${countryItem.countryName}</td>
                            <td><button type="submit" class="btn btn-primary"  data-toggle="modal" data-target="#myModal">Edit</button></td>
                            <td><a href="/FilmStore/UserServlet?command=admin-delete-country&id=${countryItem.id}" class="btn btn-danger">Delete</a></td>
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
                <h4 class="modal-title">Edit country</h4>
            </div>
            <form action="/FilmStore/UserServlet" method="post">
                <input type="hidden" name="command" value="admin-update-user" /> <br />
                <div class="modal-body">
                    <jsp:useBean id="county" class="by.epam.filmstore.domain.Country" scope="request" />

                    <div class="form-group">
                        <label for="name">Name:</label>
                        <input type="text" class="form-control" id="name" name="countryName" placeholder="Enter country name" value="${county.countryName}">
                    </div>

                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">Submit</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </form>


        </div>

    </div>
</div>

</body>
</html>
