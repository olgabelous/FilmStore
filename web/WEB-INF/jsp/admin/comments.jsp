<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="paginator" uri="/WEB-INF/tld/paginator" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.menu.comments" var="comments"/>
<fmt:message bundle="${loc}" key="locale.comment.admin_mess" var="admin_mess"/>
<fmt:message bundle="${loc}" key="locale.button.apply" var="apply"/>
<fmt:message bundle="${loc}" key="locale.button.refuse" var="refuse"/>

<body class="w3-content" style="max-width:1600px">

<jsp:include page="../fragments/adminMenu.jsp"/>
<!-- !PAGE CONTENT! -->
<div class="w3-main page-content">

    <!-- Header -->
    <header class="w3-container">
        <jsp:include page="../fragments/userSmallPic.jsp"/>

        <div class="w3-section w3-bottombar">
            <h1><b>${comments}</b></h1>
            <br>
            <jsp:include page="../fragments/errorMessageAlert.jsp"/><br>
        </div>
    </header>

    <!-- Comments-->
    <div id="content">
        <div class="container">
            <div class="row">

                <div class="col-md-9" id="comment-medium">
                    <c:choose>
                        <c:when test="${empty requestScope.commentList}">
                            <p class="text-muted lead">${admin_mess}</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="comment" items="${requestScope.commentList}">
                                <div class="box">
                                        <section class="post">
                                            <div class="row">
                                                <div class="col-md-2 col-md-offset-1">
                                                    <div class="image">
                                                        <a href="Controller?command=get-film-by-id&id=${comment.film.id}">
                                                            <img src="ImageController?img=${comment.film.poster}&type=poster"
                                                                 class="img-responsive" style="width: 150px;" alt="">
                                                        </a>
                                                    </div>
                                                </div>
                                                <div class="col-md-9">
                                                    <h2>
                                                        <a href="Controller?command=get-film-by-id&id=${comment.film.id}">${comment.film.title}</a>
                                                    </h2>

                                                    <div class="clearfix">
                                                        <p class="author-category">${comment.user.name} </p>

                                                        <p style="font-size: medium;">
                                                            <c:forEach begin="0" end="4" varStatus="loop">
                                                                <span style="color: ${loop.index < comment.mark ? "gold" : "lightgrey"}">
                                                                    <c:out value="★"/>
                                                                </span>
                                                            </c:forEach>
                                                        </p>

                                                        <p class="date-comments">
                                                            <i class="fa fa-calendar-o"></i> ${comment.dateComment.toString().replace('T', ' ')}
                                                        </p>
                                                    </div>
                                                    <p class="intro">${comment.text}</p>

                                                    <form action="Controller" method="post" style=" float: right; display: inline-block;">
                                                        <input hidden name="command" value="admin-update-comment">
                                                        <input hidden name="id" value=${comment.id}>
                                                        <input hidden name="status" value="rejected">
                                                        <button type="submit"
                                                                class="btn btn-template-main reject">${refuse}</button>
                                                    </form>
                                                    <form action="Controller" method="post" style=" float: right; display: inline-block; margin-right: 2px;">
                                                        <input hidden name="command" value="admin-update-comment">
                                                        <input hidden name="id" value=${comment.id}>
                                                        <input hidden name="status" value="checked">
                                                        <button type="submit"
                                                                class="btn btn-template-primary apply">${apply}</button>
                                                    </form>
                                                </div>
                                            </div>
                                        </section>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- /.col-md-9 -->
                <br>

                <div class="col-md-4 col-md-offset-4">
                    <c:url var="searchUri" value="/Controller?command=admin-get-comments&page=##"/>
                    <paginator:display maxLinks="10" currPage="${requestScope.currentPage}"
                                       totalPages="${requestScope.totalPages}" uri="${searchUri}"/>
                </div>
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container -->
    </div>
    <br>
    <br>

    <!-- End page content -->
</div>
<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>




