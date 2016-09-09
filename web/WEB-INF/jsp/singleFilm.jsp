<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<jsp:useBean id="film" class="by.epam.filmstore.domain.Film" scope="request" />
<jsp:useBean id="user" class="by.epam.filmstore.domain.User" scope="session"/>

<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.film.duration" var="duration" />
<fmt:message bundle="${loc}" key="locale.film.country" var="country" />
<fmt:message bundle="${loc}" key="locale.film.year" var="year" />
<fmt:message bundle="${loc}" key="locale.film.genres" var="genres" />
<fmt:message bundle="${loc}" key="locale.film.director" var="director" />
<fmt:message bundle="${loc}" key="locale.film.actors" var="actors" />
<fmt:message bundle="${loc}" key="locale.film.age_restr" var="age_restr" />
<fmt:message bundle="${loc}" key="locale.film.price" var="price" />
<fmt:message bundle="${loc}" key="locale.film.rating" var="rating" />
<fmt:message bundle="${loc}" key="locale.film.comments" var="comments" />
<fmt:message bundle="${loc}" key="locale.film.plot" var="plot" />
<fmt:message bundle="${loc}" key="locale.button.add_to_cart" var="addToCart" />

<div class="p-y-3 section">
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-1">
                <img src="ImageController?img=${film.link}" class="img-fluid img-thumbnail" width="250">
            </div>
            <div class="col-md-5">
                <h1 class="text-primary">${film.title}</h1>
                <%--<h3>${film.title}</h3>--%>
                <div class="movie__info">

                    <div class="col-sm-6 col-md-8">
                        <p class="movie__option"><strong>${rating}: </strong>${film.rating}</p>
                        <p class="movie__option"><strong>${duration}: </strong>${film.duration}</p>
                        <p class="movie__option"><strong>${country}: </strong><a href="#">${film.country.countryName}</a></p>
                        <p class="movie__option"><strong>${year}: </strong><a href="#">${film.year}</a></p>
                        <p class="movie__option"><strong>${genres}: </strong>
                            <c:forEach var="category" items="${film.genreList}">
                            <a href="#">${category.genreName}</a><span> </span>
                            </c:forEach>
                        <p class="movie__option"><strong>${director}: </strong>
                            <c:forEach var="director" items="${film.filmMakerList}">
                                <c:if test="${director.profession == 'DIRECTOR'}">
                                    <a href="#">${director.name}</a><span> </span>
                                </c:if>
                            </c:forEach>
                        <p class="movie__option"><strong>${actors}: </strong>
                            <c:forEach var="actor" items="${film.filmMakerList}">
                                <c:if test="${actor.profession == 'ACTOR'}">
                                    <a href="#">${actor.name}</a><span> </span>
                                </c:if>
                            </c:forEach>
                        <p class="movie__option"><strong>${age_restr}: </strong>${film.ageRestriction}</p>
                        <p class="movie__option"><strong>${price}: </strong><a href="#">${film.price}$</a></p>

                        <a href="#" class="comment-link">${comments}:  ${film.commentsList.size()}</a>

                    </div>
                </div>
                <c:if test="${user.role.name()=='USER'}">
                    <div class="col-md-5">
                        <form action="Controller" method="post">
                            <input type="hidden" name="command" value="user-add-to-cart" />
                            <input type="hidden" name="filmId" value="${film.id}">
                            <input type="hidden" name="price" value="${film.price}">
                            <button type="submit" class="btn btn-success">${addToCart}</button>
                        </form>
                    </div>
                </c:if>
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
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <form action="Controller" method="post" class="col-md-9 col-md-offset-1">
                    <input hidden name="command" value="user-add-comment">
                    <input hidden name="filmId" value="${film.id}">
                    <div class="form-group">
                        <label for="mark">Mark:</label>
                        <select class="form-control" id="mark" name="mark">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="51">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="comment">Comment:</label>
                        <textarea class="form-control" rows="7" name="comment" id="comment"></textarea>
                        <button type="submit" class="btn btn-success">Save comment</button>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <p>Комментарии могут оставлять только зарегистрированные пользователи</p>
            </c:otherwise>
        </c:choose>
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
