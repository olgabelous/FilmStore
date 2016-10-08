<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="paginator" uri="/WEB-INF/tld/paginator" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}"/><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc"/><!-- locale_ru -->

<fmt:message bundle="${loc}" key="locale.menu.films" var="films"/>
<fmt:message bundle="${loc}" key="locale.common.id" var="id"/>
<fmt:message bundle="${loc}" key="locale.common.action" var="action"/>
<fmt:message bundle="${loc}" key="locale.film.title" var="title"/>
<fmt:message bundle="${loc}" key="locale.film.year" var="year"/>
<fmt:message bundle="${loc}" key="locale.film.country" var="country"/>
<fmt:message bundle="${loc}" key="locale.film.age_restr" var="age_restriction"/>
<fmt:message bundle="${loc}" key="locale.film.price" var="price"/>
<fmt:message bundle="${loc}" key="locale.button.edit" var="edit"/>
<fmt:message bundle="${loc}" key="locale.button.delete" var="delete"/>
<fmt:message bundle="${loc}" key="locale.button.add_film" var="add_film"/>

<body class="w3-content" style="max-width:1600px">
<jsp:include page="../fragments/adminMenu.jsp"/>
<!-- !PAGE CONTENT! -->
<div class="w3-main page-content">

    <!-- Header -->
    <header class="w3-container">
        <jsp:include page="../fragments/userSmallPic.jsp"/>

        <div class="w3-section w3-bottombar">
            <h1><b>${films}</b></h1>
            <br><br>
            <jsp:include page="../fragments/errorMessageAlert.jsp"/>
        </div>
    </header>

    <!-- Table-->

    <div id="content">
        <div class="container">
            <div class="row">
                <div class="col-md-10 clearfix" id="basket">
                    <div>
                        <a href="Controller?command=admin-add-page-film" class="btn btn-template-primary">${add_film}</a>
                    </div>
                    <br>
                    <div class="box">
                        <div class="table table-responsive">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>#${id}</th>
                                    <th>${title}</th>
                                    <th>${year}</th>
                                    <th>${country}</th>
                                    <th>${age_restriction}</th>
                                    <th>${price}</th>
                                    <th colspan="2">${action}</th>

                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="filmItem" items="${requestScope.filmList}">
                                    <tr>
                                        <td>
                                            <a href="Controller?command=get-film-by-id&id=${filmItem.id}">
                                                <img src="ImageController?img=${filmItem.poster}&type=poster" alt="poster">
                                            </a>
                                        </td>
                                        <td>${filmItem.id}</td>
                                        <td>
                                            <a href="Controller?command=get-film-by-id&id=${filmItem.id}">${filmItem.title}</a>
                                        </td>
                                        <td>${filmItem.year}</td>
                                        <td>${filmItem.country.countryName}</td>
                                        <td>${filmItem.ageRestriction}</td>
                                        <td>${filmItem.price}</td>
                                        <td><a href="Controller?command=admin-add-page-film&id=${filmItem.id}&page=${requestScope.currentPage}"
                                               data-toggle="tooltip" data-placement="top" title="${edit}"><i
                                                class="fa fa-pencil"></i></a></td>
                                        <td><a href="Controller?command=admin-delete-film&id=${filmItem.id}&poster=${filmItem.poster}&page=${requestScope.currentPage}"
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
                <c:url var="searchUri" value="/Controller?command=admin-get-films&page=##"/>
                <paginator:display maxLinks="10" currPage="${requestScope.currentPage}"
                                   totalPages="${requestScope.totalPages}" uri="${searchUri}"/>
            </div>
        </div>
    </div>
    <!-- /.container -->
    <br>
    <br>

    <!-- End page content -->
</div>
<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>

