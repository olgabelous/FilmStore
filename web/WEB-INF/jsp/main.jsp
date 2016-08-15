<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.index.new2016" var="new2016" />
<fmt:message bundle="${loc}" key="locale.index.best" var="best" />
<fmt:message bundle="${loc}" key="locale.index.see_more" var="see_more" />
<fmt:message bundle="${loc}" key="locale.index.genres" var="genres" />
<fmt:message bundle="${loc}" key="locale.index.year" var="year" />
<fmt:message bundle="${loc}" key="locale.index.actors" var="actors" />
<fmt:message bundle="${loc}" key="locale.index.directors" var="directors" />
<fmt:message bundle="${loc}" key="locale.index.countries" var="countries" />

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request" />

<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="carousel">
    <div class="carousel-inner">
        <input class="carousel-open" type="radio" id="carousel-1" name="carousel" aria-hidden="true" hidden="" checked="checked">
        <div class="carousel-item">
            <img src="../resources/images/banner/KungFuPanda_3000x600.jpg">
        </div>
        <input class="carousel-open" type="radio" id="carousel-2" name="carousel" aria-hidden="true" hidden="">
        <div class="carousel-item">
            <img src="../resources/images/banner/HungerGamesMockingjayP1_3000x600.jpg">
        </div>
        <input class="carousel-open" type="radio" id="carousel-3" name="carousel" aria-hidden="true" hidden="">
        <div class="carousel-item">
            <img src="../resources/images/banner/Batman-V-Superman_Buy_3000x600.jpg">
        </div>
        <label for="carousel-3" class="carousel-control prv control-1">‹</label>
        <label for="carousel-2" class="carousel-control nxt control-1">›</label>
        <label for="carousel-1" class="carousel-control prv control-2">‹</label>
        <label for="carousel-3" class="carousel-control nxt control-2">›</label>
        <label for="carousel-2" class="carousel-control prv control-3">‹</label>
        <label for="carousel-1" class="carousel-control nxt control-3">›</label>
        <ol class="carousel-indicators">
            <li>
                <label for="carousel-1" class="carousel-bulet">•</label>
            </li>
            <li>
                <label for="carousel-2" class="carousel-bulet">•</label>
            </li>
            <li>
                <label for="carousel-3" class="carousel-bulet">•</label>
            </li>
        </ol>
    </div>
</div>

<nav class="navbar navbar-full navbar-light bg-faded bg-info">

    <ul class="nav navbar-nav pull-left">
        <li class="nav-item active">
            <a class="nav-link" href="#">${genres}<span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">${year}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">${actors}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">${directors}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">${countries}</a>
        </li>
    </ul>
</nav>

<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h1 class="">${new2016}</h1></div>
        </div>
    </div>
</div>
<div class="p-y-3 section">
    <div class="container">
        <div class="row">
            <c:forEach var="newfilm" items="${requestScope.newfilms}">
            <div class="col-md-2">
                <a href="/FilmStore/UserServlet?command=get_film_by_id&id=${newfilm.id}"><img src="../resources/images/Barbershop_small.jpg"  class="img-fluid m-y" alt="Обложка">
                    <p class="m-y-1">${newfilm.title}</p>
                </a>
            </div>
            </c:forEach>
        </div>
    </div>
</div>

<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-2 col-md-offset-5"><a href="/FilmStore/UserServlet?command=get_films_by_year&year=2016" class="btn btn-block btn-primary-outline">${see_more}</a></div>
        </div>
    </div>
</div>
<br>
<br>
<br>
<br>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h1 class="" draggable="true">${best}</h1></div>
        </div>
    </div>
</div>
<div class="p-y-3 section">
    <div class="container">
        <div class="row">
            <c:forEach var="bestfilm" items="${requestScope.bestfilms}">
                <div class="col-md-2">
                    <a href="/FilmStore/UserServlet?command=get_film_by_id&id=${bestfilm.id}"><img src="../resources/images/Barbershop_small.jpg"  class="img-fluid m-y" alt="Обложка">
                        <p class="m-y-1">${bestfilm.title}</p>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-2 col-md-offset-5"><a href="/FilmStore/UserServlet?command=get_films_by_rating" class="btn btn-block btn-primary-outline">${see_more}</a></div>
        </div>
    </div>
</div>

</body>

</html>
