<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="paginator" uri="/WEB-INF/tld/paginator" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.menu.art_people" var="art_people"/>
<fmt:message bundle="${loc}" key="locale.common.id" var="id"/>
<fmt:message bundle="${loc}" key="locale.common.action" var="action"/>
<fmt:message bundle="${loc}" key="locale.admin.add_art_person" var="add_art_person"/>
<fmt:message bundle="${loc}" key="locale.admin.edit_art_person" var="edit_art_person"/>
<fmt:message bundle="${loc}" key="locale.filmmaker.name" var="name"/>
<fmt:message bundle="${loc}" key="locale.filmmaker.profession" var="profession"/>
<fmt:message bundle="${loc}" key="locale.filmmaker.actor" var="actor"/>
<fmt:message bundle="${loc}" key="locale.filmmaker.director" var="director"/>
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
            <h1><b>${art_people}</b></h1>
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
                        <a href="#myModal" data-toggle="modal" class="btn btn-template-primary">${add_art_person}</a>
                    </div>
                    <br>
                    <div class="box">
                        <div class="table table-responsive">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>${id}</th>
                                    <th>${name}</th>
                                    <th>${profession}</th>
                                    <th colspan="2" class="text-center">${action}</th>

                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="filmMakerItem" items="${requestScope.filmMakerList}">
                                    <tr>
                                        <td>${filmMakerItem.id}</td>
                                        <td>${filmMakerItem.name}</td>
                                        <td>${filmMakerItem.profession.name()}</td>
                                        <td class="text-center"><a href="#myModal" data-toggle="modal" data-id="${filmMakerItem.id}"
                                               class="edit-film-maker" data-film-maker-name="${filmMakerItem.name}"
                                               data-film-maker-prof="${filmMakerItem.profession.name()}"
                                               data-placement="top" title="${edit}"><i
                                                class="fa fa-pencil"></i></a></td>
                                        <td class="text-center"><a href="Controller?command=admin-delete-film-maker&id=${filmMakerItem.id}"
                                               data-toggle="tooltip" data-placement="top" title="${delete}"><i
                                                class="fa fa-trash-o"></i></a></td>
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
            <div class="col-md-4 col-md-offset-4">
                <c:url var="searchUri" value="/Controller?command=admin-get-film-makers&page=##"/>
                <paginator:display maxLinks="10" currPage="${requestScope.currentPage}"
                                   totalPages="${requestScope.totalPages}" uri="${searchUri}"/>
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
                <h4 class="modal-title">${edit_art_person}</h4>
            </div>
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="admin-save-film-maker"/><br/>

                <div class="modal-body">
                    <input type="hidden" name="id" id="id" value=""/> <br/>

                    <div class="form-group">
                        <label for="name">${name}:</label>
                        <input type="text" class="form-control" name="name" id="name" value="">
                    </div>

                    <div class="form-group">
                        <label for="profession">${profession}:</label>
                        <select class="form-control" name="profession" id="profession">
                            <option value="ACTOR" selected>${actor}</option>
                            <option value="DIRECTOR">${director}</option>
                        </select>
                    </div>

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





