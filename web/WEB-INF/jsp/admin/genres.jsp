<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}"/><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc"/><!-- locale_ru -->

<fmt:message bundle="${loc}" key="locale.menu.genres" var="genres"/>
<fmt:message bundle="${loc}" key="locale.admin.add_genre" var="add_genre"/>
<fmt:message bundle="${loc}" key="locale.admin.edit_genre" var="edit_genre"/>
<fmt:message bundle="${loc}" key="locale.common.id" var="id"/>
<fmt:message bundle="${loc}" key="locale.common.action" var="action"/>
<fmt:message bundle="${loc}" key="locale.genre.genre" var="genre_name"/>
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
            <h1><b>${genres}</b></h1>
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
                            <input type="hidden" name="command" value="admin-save-genre"/> <br/>

                            <div class="form-group">
                                <label for="genre" class="control-label col-xs-3">${add_genre}: </label>

                                <div class="col-xs-5">
                                    <input class="form-control" id="genre" name="genreName" placeholder="${genre_name}">
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
                                        <th>#${id}</th>
                                        <th>${genre_name}</th>
                                        <th colspan="2" class="text-center">${action}</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="genreItem" items="${applicationScope.genreList}">
                                        <tr>
                                            <td>${genreItem.id}</td>
                                            <td>${genreItem.genreName}</td>
                                            <td class="text-center"><a href="#myModal" data-toggle="modal" data-id="${genreItem.id}"
                                                   class="edit-genre" data-genre-name="${genreItem.genreName}"
                                                   data-placement="top" title="${edit}"><i class="fa fa-pencil"></i></a></td>
                                            <td class="text-center"><a href="Controller?command=admin-delete-genre&id=${genreItem.id}"
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

            <div class="col-md-4 col-md-offset-4">
                <c:url var="searchUri" value="/Controller?command=admin-get-films&page=##"/>
                <paginator:display maxLinks="10" currPage="${requestScope.currentPage}" totalPages="${requestScope.totalPages}" uri="${searchUri}" />
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
                <h4 class="modal-title">${edit_genre}</h4>
            </div>
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="admin-save-genre"/> <br/>

                <div class="modal-body">
                    <input type="hidden" name="id" id="id" value=""/> <br/>

                    <div class="form-group">
                        <label for="name">${genre_name}:</label>
                        <input type="text" class="form-control" id="name" name="genreName" value="">
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
<!-- Modal End -->

</body>
</html>




