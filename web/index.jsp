<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="WEB-INF/jsp/fragments/headTag.jsp"/>

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

<jsp:include page="WEB-INF/jsp/fragments/bodyHeader.jsp"/>

<div class="carousel">
    <div class="carousel-inner">
        <input class="carousel-open" type="radio" id="carousel-1" name="carousel" aria-hidden="true" hidden="" checked="checked">
        <div class="carousel-item">
            <img src="resources/images/banner/KungFuPanda_3000x600.jpg">
        </div>
        <input class="carousel-open" type="radio" id="carousel-2" name="carousel" aria-hidden="true" hidden="">
        <div class="carousel-item">
            <img src="resources/images/banner/HungerGamesMockingjayP1_3000x600.jpg">
        </div>
        <input class="carousel-open" type="radio" id="carousel-3" name="carousel" aria-hidden="true" hidden="">
        <div class="carousel-item">
            <img src="resources/images/banner/Batman-V-Superman_Buy_3000x600.jpg">
        </div>
        <label for="carousel-3" class="carousel-control prev control-1">‹</label>
        <label for="carousel-2" class="carousel-control next control-1">›</label>
        <label for="carousel-1" class="carousel-control prev control-2">‹</label>
        <label for="carousel-3" class="carousel-control next control-2">›</label>
        <label for="carousel-2" class="carousel-control prev control-3">‹</label>
        <label for="carousel-1" class="carousel-control next control-3">›</label>
        <ol class="carousel-indicators">
            <li>
                <label for="carousel-1" class="carousel-bullet">•</label>
            </li>
            <li>
                <label for="carousel-2" class="carousel-bullet">•</label>
            </li>
            <li>
                <label for="carousel-3" class="carousel-bullet">•</label>
            </li>
        </ol>
    </div>
</div>

<nav class="navbar navbar-full navbar-light bg-faded" draggable="true">

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
            <div class="col-md-2">
                <img src="resources/images/Barbershop_small.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet,</p>
            </div>
            <div class="col-md-2">
                <img src="resources/images/BigShort_small.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet, </p>
            </div>
            <div class="col-md-2">
                <img src="resources/images/GodsOfEgypt_small.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit ame</p>
            </div>
            <div class="col-md-2">
                <img src="resources/images/GoodDinosaurThe_small.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet, </p>
            </div>
            <div class="col-md-2">
                <img src="resources/images/HelloMyNameIsDoris_small.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit am</p>
            </div>
            <div class="col-md-2">
                <img src="resources/images/HungerGamesThe_Mockingjay_Pt2_small.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet,</p>
            </div>
        </div>
    </div>
</div>

<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-2 col-md-offset-5"><a href="FilmStore/UserServlet?command=get_films_by_year&year=2016" class="btn btn-block btn-primary-outline">${see_more}</a></div>
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
            <div class="col-md-2">
                <img src="resources/images/KungFu%20Panda%203.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet,</p>
            </div>
            <div class="col-md-2">
                <img src="resources/images/MadMaxFuryRoad_small.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet, </p>
            </div>
            <div class="col-md-2">
                <img src="resources/images/malcomx_small.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit ame</p>
            </div>
            <div class="col-md-2">
                <img src="resources/images/PenguinsOfMadagascar_small.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet, </p>
            </div>
            <div class="col-md-2">
                <img src="resources/images/Race_small.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit am</p>
            </div>
            <div class="col-md-2">
                <img src="resources/images/GoodDinosaurThe_small.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet,</p>
            </div>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-2 col-md-offset-5"><a href="#" class="btn btn-block btn-primary-outline">Button</a></div>
        </div>
    </div>
</div>



<script type="text/javascript" src="resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap.min.js"></script>

</body>

</html>
