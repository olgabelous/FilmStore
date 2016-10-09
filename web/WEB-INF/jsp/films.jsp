<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="paginator" uri="/WEB-INF/tld/paginator" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.films.empty_film_list" var="empty_film_list" />

<jsp:include page="fragments/bodyHeader.jsp"/>
<jsp:include page="fragments/filterPanel.jsp"/>
<jsp:include page="fragments/filter.jsp"/>

<c:choose>
    <c:when test="${not empty requestScope.warning}">
        <c:set value="block" var="displayStatus"/>
    </c:when>
    <c:otherwise>
        <c:set value="none" var="displayStatus"/>
    </c:otherwise>
</c:choose>

<div style="display:${displayStatus}" class="alert alert-danger col-md-10 col-md-offset-1">
    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    ${empty_film_list}
</div>

<div class="section" style="min-height: calc(100% - 388px)">
    <div class="container">
        <div class="row box">
            <c:forEach var="film" items="${requestScope.filmList}">
                <div class="img w3-hover-shadow">
                    <a href="Controller?command=get-film-by-id&id=${film.id}">
                        <img src="ImageController?img=${film.poster}&type=poster"  class="img-fluid m-y" alt="Обложка">
                        <div class="desc">
                        <h6 class="text-uppercase">${film.title}</h6>
                        <span style="font-size: medium; color: #2c8494;"><i class="fa fa-star" aria-hidden="true">${film.rating}</i></span>
                                ${film.year}
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
        <br>
        <div class="col-md-2 col-md-offset-5">
            <c:url var="searchUri" value="/Controller?${requestScope.query}&page=##"/>
            <paginator:display maxLinks="10" currPage="${requestScope.currentPage}" totalPages="${requestScope.totalPages}" uri="${searchUri}" />
        </div>
    </div>
    <br>
    <br>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
