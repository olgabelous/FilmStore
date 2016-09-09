<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.admin.add_new_film" var="add_new_film" />
<fmt:message bundle="${loc}" key="locale.film.title" var="title" />
<fmt:message bundle="${loc}" key="locale.film.year" var="year" />
<fmt:message bundle="${loc}" key="locale.film.country" var="country" />
<fmt:message bundle="${loc}" key="locale.film.age_restr" var="ageRestriction" />
<fmt:message bundle="${loc}" key="locale.film.director" var="director" />
<fmt:message bundle="${loc}" key="locale.film.actors" var="actors" />
<fmt:message bundle="${loc}" key="locale.film.duration" var="duration" />
<fmt:message bundle="${loc}" key="locale.film.genres" var="genres" />
<fmt:message bundle="${loc}" key="locale.film.price" var="price" />
<fmt:message bundle="${loc}" key="locale.film.plot" var="plot" />
<fmt:message bundle="${loc}" key="locale.film.poster" var="poster" />
<fmt:message bundle="${loc}" key="locale.button.save" var="save" />

<jsp:include page="../fragments/bodyHeader.jsp"/>


<div class="section m-y-1">
    <div class="container">
        <div class="row">
            <jsp:include page="../fragments/adminMenu.jsp"/>
            <div class="col-lg-10 col-md-10">
                <div >
                    <div class="modal-header">
                        <h4 class="modal-title">${add_new_film}</h4>
                    </div>
                    <jsp:useBean id="film" class="by.epam.filmstore.domain.Film" scope="request" />
                    <form class="form-horizontal" action="Controller" method="post" enctype="multipart/form-data">
                       <input type="hidden" name="command" value="admin-save-film"/>
                        <input type="hidden" name="id" value="${film.id}"/><br>

                        <div class="modal-body">

                            <div class="form-group">
                                <label class="control-label col-sm-2" for="film-title">${title}:</label>

                                <div class="col-sm-9">
                                    <input type="text" class="form-control" name="title" id="film-title"
                                           placeholder="Enter title" value="${film.title}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2" for="year">${year}:</label>

                                <div class="col-sm-9">
                                    <input type="number" class="form-control" name="year" id="year" placeholder="Enter year"
                                           value="${film.year}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="countryId">${country}:</label>

                                <div class="col-sm-9">
                                    <select class="form-control" name="countryId" id="countryId">
                                        <c:forEach var="country" items="${requestScope.countries}">
                                            <option value="${country.id}" ${country.id == film.country.id ? 'selected="selected"' : ''}>
                                            ${country.countryName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="duration">${duration}:</label>

                                <div class="col-sm-9">
                                    <input type="number" class="form-control" name="duration" id="duration"
                                           placeholder="Enter film duration" value="${film.duration}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="ageRestriction">${ageRestriction}:</label>

                                <div class="col-sm-9">
                                    <input type="number" class="form-control" name="ageRestriction" id="ageRestriction"
                                           placeholder="Enter age restriction" value="${film.ageRestriction}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="genreName">${genres}:</label>

                                <div class="col-sm-9">
                                    <select multiple class="form-control" name="genres" id="genreName">
                                        <c:forEach var="genre" items="${requestScope.genres}">
                                            <option value="${genre.id}"
                                            <c:forEach var="filmGenre" items="${film.genreList}">
                                                ${genre.id == filmGenre.id ? 'selected="selected"' : ''}
                                            </c:forEach>>${genre.genreName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="director">${director}:</label>

                                <div class="col-sm-9">
                                    <select class="form-control" name="filmMakers" id="director">
                                        <c:forEach var="dir" items="${requestScope.filmMakers}">
                                            <c:if test="${dir.profession == 'DIRECTOR'}">
                                                <option value="${dir.id}"
                                                        <c:forEach var="filmMaker" items="${film.filmMakerList}">
                                                            ${dir.id == filmMaker.id ? 'selected="selected"' : ''}
                                                        </c:forEach>>${dir.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="actors">${actors}:</label>

                                <div class="col-sm-9">
                                    <select multiple class="form-control" name="filmMakers" id="actors">
                                        <c:forEach var="actor" items="${requestScope.filmMakers}">
                                            <c:if test="${actor.profession == 'ACTOR'}">
                                                <option value="${actor.id}"
                                                        <c:forEach var="filmMaker" items="${film.filmMakerList}">
                                                            ${actor.id == filmMaker.id ? 'selected="selected"' : ''}
                                                        </c:forEach>>${actor.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="price">${price}:</label>

                                <div class="col-sm-9">
                                    <input type="number" class="form-control" name="price" id="price" placeholder="Enter price"
                                           value="${film.price}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="poster">${poster}:</label>

                                <div class="col-sm-9">
                                    <input type="file" class="form-control" name="poster" id="poster"
                                           placeholder="Enter link to poster">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="description">${plot}:</label>

                                <div class="col-sm-9">
                        <textarea class="form-control" rows="5" name="description" id="description"
                                  placeholder="Enter description">${film.description}</textarea>
                                </div>
                            </div>

                        </div>

                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success">${save}</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
