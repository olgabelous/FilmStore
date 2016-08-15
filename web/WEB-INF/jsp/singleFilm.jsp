<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<jsp:useBean id="film" class="by.epam.filmstore.domain.Film" scope="request" />

<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.film.duration" var="duration" />
<fmt:message bundle="${loc}" key="locale.film.country" var="country" />
<fmt:message bundle="${loc}" key="locale.film.year" var="year" />
<fmt:message bundle="${loc}" key="locale.film.genre" var="genre" />
<fmt:message bundle="${loc}" key="locale.film.director" var="director" />
<fmt:message bundle="${loc}" key="locale.film.actors" var="actors" />
<fmt:message bundle="${loc}" key="locale.film.age_restr" var="age_restr" />
<fmt:message bundle="${loc}" key="locale.film.price" var="price" />
<fmt:message bundle="${loc}" key="locale.film.rating" var="rating" />
<fmt:message bundle="${loc}" key="locale.film.comments" var="comments" />
<fmt:message bundle="${loc}" key="locale.film.buy" var="buy" />
<fmt:message bundle="${loc}" key="locale.film.plot" var="plot" />

<div class="p-y-3 section">
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-1">
                <img src="../resources/images/PenguinsOfMadagascar_800x1200.jpg" class="img-fluid img-thumbnail">
            </div>
            <div class="col-md-5">
                <h1 class="text-primary">${film.title}</h1>
                <h3>${film.title}</h3>
                <div class="movie__info">

                    <div class="col-sm-6 col-md-8">
                        <p class="movie__option"><strong>${rating}: </strong>${film.rating}</p>
                        <p class="movie__option"><strong>${duration}: </strong>${film.duration}</p>
                        <p class="movie__option"><strong>${country}: </strong><a href="#">${film.country.countryName}</a></p>
                        <p class="movie__option"><strong>${year}: </strong><a href="#">${film.year}</a></p>
                        <p class="movie__option"><strong>${genre}: </strong>
                            <c:forEach var="category" items="${film.genreList}">
                            <a href="#">${category.genreName}</a><span>, </span>
                            </c:forEach>
                        <p class="movie__option"><strong>${director}: </strong>
                            <c:forEach var="director" items="${film.filmMakerList}">
                                <c:if test="${director.profession == 'DIRECTOR'}">
                                    <a href="#">${director.name}</a><span>, </span>
                                </c:if>
                            </c:forEach>
                        <p class="movie__option"><strong>${actors}: </strong>
                            <c:forEach var="actor" items="${film.filmMakerList}">
                                <c:if test="${actor.profession == 'ACTOR'}">
                                    <a href="#">${actor.name}</a><span>, </span>
                                </c:if>
                            </c:forEach>
                        <p class="movie__option"><strong>${age_restr}: </strong>${film.ageRestriction}</p>
                        <p class="movie__option"><strong>${price}: </strong><a href="#">${film.price}$</a></p>

                        <a href="#" class="comment-link">${comments}:  ${film.commentsList.size()}</a>

                    </div>
                </div>
                <div class="col-md-5"><a href="payment.jsp" class="btn btn-block btn-primary" draggable="true">${buy}</a></div>
            </div>

        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-11 col-md-offset-1">
                <h1 class="">${plot}</h1></div>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-11 col-md-offset-1">
                <p class="movie__describe">${film.description}</p>
            </div>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="comment-sets col-md-11 col-md-offset-1">
            <div class="row">
                <div class="col-md-11 col-md-offset-1">
                    <h1 class="">${comments}</h1></div>
            </div>
            <div class="comment">
                <c:forEach var="comment" items="${film.commentsList}">
                    <p class="social-used fa fa-facebook">${comment.user.name}</p>
                    <p class="comment__date">${comment.dateComment}</p>
                    <p class="comment__message">${comment.text}</p>
                </c:forEach>
            </div>

            <div class="comment-more">
                <a href="#" class="watchlist">Show more comments</a>
            </div>
        </div>
    </div>
</div>

</body>
</html>
