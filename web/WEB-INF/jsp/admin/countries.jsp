<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.menu.countries" var="countries"/>
<fmt:message bundle="${loc}" key="locale.admin.add_country" var="add_country"/>
<fmt:message bundle="${loc}" key="locale.common.id" var="id"/>
<fmt:message bundle="${loc}" key="locale.common.action" var="action"/>
<fmt:message bundle="${loc}" key="locale.country.country" var="country"/>
<fmt:message bundle="${loc}" key="locale.country.edit_country" var="edit_country"/>
<fmt:message bundle="${loc}" key="locale.country.enter_country" var="enter_country"/>
<fmt:message bundle="${loc}" key="locale.button.save" var="save"/>
<fmt:message bundle="${loc}" key="locale.button.edit" var="edit"/>
<fmt:message bundle="${loc}" key="locale.button.delete" var="delete"/>
<fmt:message bundle="${loc}" key="locale.button.close" var="close"/>

<body class="w3-content" style="max-width:1600px">

<jsp:include page="../fragments/adminMenu.jsp"/>
<!-- !PAGE CONTENT! -->
<div class="w3-main w3-white" style="margin-left:300px">

    <!-- Header -->
    <header class="w3-container">
        <jsp:include page="../fragments/userSmallPic.jsp"/>

        <div class="w3-section w3-bottombar">
            <h1><b>${countries}</b></h1>
            <br><br>
            <jsp:include page="../fragments/errorMessageAlert.jsp"/>
        </div>
    </header>

    <!-- Table-->
    <div id="content">
        <div class="container">
            <div class="row">
                <div class="col-md-9 clearfix" id="basket">
                    <div>
                        <form class="form-horizontal" id="detailsForm" action="Controller" method="post">
                            <input type="hidden" name="command" value="admin-save-country"/> <br/>

                            <div class="form-group">
                                <label for="country" class="control-label col-xs-3">${add_country}: </label>

                                <div class="col-xs-5">
                                    <input class="form-control" id="country" name="countryName" placeholder="${country}">
                                </div>
                                <button type="submit" class="btn btn-template-primary">${save}</button>

                            </div>
                        </form>
                    </div><br>
                    <div class="box">
                        <div class="table table-responsive">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>${id}</th>
                                    <th>${country}</th>
                                    <th colspan="2" class="text-center">${action}</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="countryItem" items="${requestScope.countryList}">
                                    <tr>
                                        <td>${countryItem.id}</td>
                                        <td>${countryItem.countryName}</td>
                                        <td class="text-center"><a href="#myModal" data-toggle="modal" data-id="${countryItem.id}"
                                               class="edit-country" data-country-name="${countryItem.countryName}"
                                               data-placement="top" title="${edit}"><i class="fa fa-pencil"></i></a></td>
                                        <td class="text-center"><a href="Controller?command=admin-delete-country&id=${countryItem.id}"
                                               data-toggle="tooltip" data-placement="top" title="${delete}"><i class="fa fa-trash-o"></i></a></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <!-- /.box -->
                    </div>
                    <!-- /.table-responsive -->
                </div>
                <!-- /.col-md-9 -->
            </div>
        </div>
    </div>
    <!-- /.container -->
    <br>
    <br>
    <jsp:include page="../fragments/footer.jsp"/>
    <!-- End page content -->
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">${edit_country}</h4>
            </div>
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="admin-save-country"/> <br/>

                <div class="modal-body">
                    <input type="hidden" name="id" id="id" value=""/> <br>

                    <div class="form-group">
                        <label for="name">${country}:</label>
                        <input type="text" class="form-control" id="name" name="countryName"
                               placeholder="${enter_country}" value="">
                    </div>

                    <div class="modal-footer">
                        <button type="submit" class="btn btn-template-primary">${save}</button>
                        <button type="button" class="btn btn-template-main" data-dismiss="modal">${close}</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>

