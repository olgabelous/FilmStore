<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.admin.add_new_film" var="add_new_film"/>
<fmt:message bundle="${loc}" key="locale.film.title" var="title"/>
<fmt:message bundle="${loc}" key="locale.film.year" var="year"/>
<fmt:message bundle="${loc}" key="locale.film.country" var="country"/>
<fmt:message bundle="${loc}" key="locale.film.age_restr" var="ageRestriction"/>
<fmt:message bundle="${loc}" key="locale.film.director" var="director"/>
<fmt:message bundle="${loc}" key="locale.film.actors" var="actors"/>
<fmt:message bundle="${loc}" key="locale.film.duration" var="duration"/>
<fmt:message bundle="${loc}" key="locale.film.genres" var="genres"/>
<fmt:message bundle="${loc}" key="locale.film.price" var="price"/>
<fmt:message bundle="${loc}" key="locale.film.plot" var="plot"/>
<fmt:message bundle="${loc}" key="locale.film.poster" var="poster"/>
<fmt:message bundle="${loc}" key="locale.film.video" var="video"/>
<fmt:message bundle="${loc}" key="locale.button.save" var="save"/>
<fmt:message bundle="${loc}" key="locale.addfilm.year_not_correct" var="year_not_correct"/>
<fmt:message bundle="${loc}" key="locale.addfilm.duration_not_correct" var="duration_not_correct"/>
<fmt:message bundle="${loc}" key="locale.addfilm.age_not_correct" var="age_not_correct"/>
<fmt:message bundle="${loc}" key="locale.addfilm.price_not_correct" var="price_not_correct"/>
<fmt:message bundle="${loc}" key="locale.addfilm.file_not_correct" var="file_not_correct"/>
<fmt:message bundle="${loc}" key="locale.addfilm.enter_title" var="enter_title"/>
<fmt:message bundle="${loc}" key="locale.addfilm.enter_year" var="enter_year"/>
<fmt:message bundle="${loc}" key="locale.addfilm.enter_duration" var="enter_duration"/>
<fmt:message bundle="${loc}" key="locale.addfilm.enter_age" var="enter_age"/>
<fmt:message bundle="${loc}" key="locale.addfilm.enter_price" var="enter_price"/>
<fmt:message bundle="${loc}" key="locale.addfilm.enter_video" var="enter_video"/>
<fmt:message bundle="${loc}" key="locale.addfilm.enter_description" var="enter_description"/>

<body class="w3-content" style="max-width:1600px">

<jsp:include page="../fragments/adminMenu.jsp"/>
<!-- !PAGE CONTENT! -->
<div class="w3-main w3-white" style="margin-left:300px">

    <!-- Header -->
    <header class="w3-container">
        <jsp:include page="../fragments/userSmallPic.jsp"/>

        <div class="w3-section w3-bottombar">
            <h1><b>${add_new_film}</b></h1>
            <br>
            <jsp:include page="../fragments/errorMessageAlert.jsp"/>
            <br>
        </div>
    </header>

    <!-- Table-->

    <div class="w3-row-padding">
        <div class="w3-col w3-container w3-margin-bottom">
            <div class="w3-container w3-white">
                 <form class="form-horizontal" action="Controller" method="post" enctype="multipart/form-data" onsubmit="return checkForm(this);">
                    <input type="hidden" name="command" value="admin-save-film"/>
                    <input type="hidden" name="id" value="${requestScope.film.id}"/>
                     <input type="hidden" name="dateAdd" value="${requestScope.film.dateAdd}"/>
                     <input type="hidden" name="page" value="${requestScope.page}"/>

                        <div class="form-group">
                            <label class="control-label col-sm-2" for="film-title">${title}:</label>

                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="title" id="film-title"
                                       placeholder="${enter_title}" value="${requestScope.film.title}" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-2" for="year">${year}:</label>

                            <div class="col-sm-9">
                                <input type="number" class="form-control" name="year" id="year" placeholder="${enter_year}"
                                       value="${requestScope.film.year}"  required><span id='message0'></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="countryId">${country}:</label>

                            <div class="col-sm-9">
                                <select class="form-control" name="countryId" id="countryId" required>
                                    <c:forEach var="country" items="${sessionScope.countryList}">
                                        <option value="${country.id}" ${country.id == requestScope.film.country.id ? 'selected="selected"' : ''}>
                                                ${country.countryName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="duration">${duration}:</label>

                            <div class="col-sm-9">
                                <input type="number" class="form-control" name="duration" id="duration"
                                       placeholder="${enter_duration}"
                                       value="${requestScope.film.duration}" required><span id='message1'></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="ageRestriction">${ageRestriction}:</label>

                            <div class="col-sm-9">
                                <input type="number" class="form-control" name="ageRestriction" id="ageRestriction"
                                       placeholder="${enter_age}"
                                       value="${requestScope.film.ageRestriction}" required><span id='message2'></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="genreName">${genres}:</label>

                            <div class="col-sm-9">
                                <select multiple class="form-control" name="genres" id="genreName"  required>
                                    <c:forEach var="genre" items="${sessionScope.genreList}">
                                        <option value="${genre.id}"
                                                <c:forEach var="filmGenre" items="${requestScope.film.genreList}">
                                                    ${genre.id == filmGenre.id ? 'selected="selected"' : ''}
                                                </c:forEach>>${genre.genreName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="director">${director}:</label>

                            <div class="col-sm-9">
                                <select class="form-control" name="filmMakers" id="director" required>
                                    <c:forEach var="dir" items="${requestScope.filmMakers}">
                                        <c:if test="${dir.profession == 'DIRECTOR'}">
                                            <option value="${dir.id}"
                                                    <c:forEach var="filmMaker" items="${requestScope.film.filmMakerList}">
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
                                <select multiple="multiple" class="form-control" name="filmMakers" id="actors" required>
                                    <c:forEach var="actor" items="${requestScope.filmMakers}">
                                        <c:if test="${actor.profession == 'ACTOR'}">
                                            <option value="${actor.id}"
                                                    <c:forEach var="filmMaker" items="${requestScope.film.filmMakerList}">
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
                                <input type="number" class="form-control" name="price" id="price" placeholder="${enter_price}"
                                       value="${requestScope.film.price}" required><span id='message3'></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="poster">${poster}:</label>

                            <div class="col-sm-9">
                                <input type="file" class="form-control" name="poster" id="poster">
                                <p>${requestScope.film.poster}</p>
                                <input type="hidden" name="oldPoster" value="${requestScope.film.poster}"><span id='message4'></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="video">${video}:</label>

                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="video" id="video"
                                       placeholder="${enter_video}" value="${requestScope.film.video}"  required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="description">${plot}:</label>

                            <div class="col-sm-9">
                        <textarea class="form-control" rows="5" name="description" id="description"
                                  placeholder="${enter_description}" required>${requestScope.film.description}</textarea>
                            </div>
                        </div>

                    <div class="col-md-offset-2">
                        <button type="submit" class="btn btn-template-main"><i class="fa fa-save"></i> ${save}</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- End page content -->
</div>

<script type="text/javascript">
    function checkForm(form)
    {
        $('#message0').html('');
        $('#message1').html('');
        $('#message2').html('');
        $('#message3').html('');

        var re = /^(19|20)\d{2}$/;
        var suffix = /(jpg|JPG|jpeg|JPEG|png|PNG)$/;

        var bool = true;
        if (!re.test(form.year.value)) {
            $('#message0').html('${year_not_correct}').css('color', 'red');
            bool = false;
        }
        if(form.duration.value <= 0){
            $('#message1').html('${duration_not_correct}').css('color', 'red');
            bool = false;
        }
        if(form.ageRestriction.value < 0 || form.ageRestriction.value > 21){
            $('#message2').html('${age_not_correct}').css('color', 'red');
            bool = false;
        }
        if(form.price.value <= 0){
            $('#message3').html('${price_not_correct}').css('color', 'red');
            bool = false;
        }
        if(form.poster.value && !suffix.test(form.poster.value)){
            $('#message4').html('${file_not_correct}').css('color', 'red');
            bool = false;
        }
        return bool;
    }
</script>

</body>
</html>




