<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.admin.add_country" var="addCountry"/>
<fmt:message bundle="${loc}" key="locale.common.id" var="id"/>
<fmt:message bundle="${loc}" key="locale.country.country" var="country"/>
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
                <form class="form-horizontal" id="detailsForm" action="Controller" method="post">
                    <input type="hidden" name="command" value="admin-save-country" /> <br />

                    <div class="form-group">
                        <label for="country" class="control-label col-xs-3">${addCountry}: </label>

                        <div class="col-xs-5">
                            <input class="form-control" id="country" name="countryName" placeholder="Country">
                        </div>
                        <button type="submit" class="btn btn-primary">${save}</button>

                    </div>
                </form>
                <hr>

                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>${id}</th>
                        <th>${country}</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="countryItem" items="${requestScope.countryList}">
                        <tr>
                            <td>${countryItem.id}</td>
                            <td>${countryItem.countryName}</td>
                            <td><a href="#myModal" data-toggle="modal" data-id="${countryItem.id}" class="edit-country btn btn-primary"
                                   data-country-name="${countryItem.countryName}">${edit}</a></td>
                            <td><a href="Controller?command=admin-delete-country&id=${countryItem.id}" class="btn btn-danger">${delete}</a></td>
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
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="admin-save-country" /> <br />
                <div class="modal-body">
                    <input type="hidden" name="id" id="id" value=""/> <br>

                    <div class="form-group">
                        <label for="name">${country}:</label>
                        <input type="text" class="form-control" id="name" name="countryName" placeholder="Enter country name" value="">
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
