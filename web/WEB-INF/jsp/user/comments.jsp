<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}"/><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc"/><!-- locale_ru -->

<fmt:message bundle="${loc}" key="locale.menu.my_comments" var="my_comments"/>
<fmt:message bundle="${loc}" key="locale.comment.comment_empty" var="comment_empty"/>
<fmt:message bundle="${loc}" key="locale.comment.new_stat" var="new_stat"/>
<fmt:message bundle="${loc}" key="locale.comment.checked" var="checked"/>
<fmt:message bundle="${loc}" key="locale.comment.rejected" var="rejected"/>

<body class="w3-content" style="max-width:1600px">

<jsp:include page="../fragments/userMenu.jsp"/>
<!-- !PAGE CONTENT! -->
<div class="w3-main page-content">

    <!-- Header -->
    <header class="w3-container">
        <jsp:include page="../fragments/userSmallPic.jsp"/>

        <div class="w3-section w3-bottombar">
            <h1><b>${my_comments}</b></h1>
            <br><br>
        </div>
    </header>

    <!-- Comments-->
    <div id="content">
        <div class="container">
            <div class="row">

                <div class="col-md-9" id="blog-listing-medium">
                    <c:choose>
                        <c:when test="${empty requestScope.commentList}">
                            <p class="text-muted lead">${comment_empty}</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="comment" items="${requestScope.commentList}">
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

                                            <p class="read-more">
                                                <c:choose>
                                                    <c:when test="${comment.status=='NEW'}">
                                                        <span class="label label-info">${new_stat}</span>
                                                    </c:when>
                                                    <c:when test="${comment.status=='REJECTED'}">
                                                        <span class="label label-danger">${rejected}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="label label-success">${checked}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </p>
                                        </div>
                                    </div>
                                </section>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
                <!-- /.col-md-9 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container -->
    </div>
    <!-- End page content -->
</div>
<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>