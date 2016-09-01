<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<jsp:include page="../fragments/bodyHeader.jsp"/>


<div class="section m-y-1">
    <div class="container">
        <div class="row">
            <jsp:include page="../fragments/adminMenu.jsp"/>
            <div class="col-lg-10 col-md-10">
                <div >
                    <div class="modal-header">
                        <h4 class="modal-title">Add new film</h4>
                    </div>
                    <form class="form-horizontal" action="/FilmStore/UserServlet" method="post">
                        <input type="hidden" name="command" value="admin-add-film"/><br/>

                        <div class="modal-body">

                            <div class="form-group">
                                <label class="control-label col-sm-2" for="film-title">Title:</label>

                                <div class="col-sm-9">
                                    <input type="text" class="form-control" name="title" id="film-title"
                                           placeholder="Enter title">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2" for="year">Year:</label>

                                <div class="col-sm-9">
                                    <input type="number" class="form-control" name="year" id="year" placeholder="Enter year"
                                           value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="countryId">Country:</label>

                                <div class="col-sm-9">
                                    <select class="form-control" name="countryId" id="countryId">
                                        <c:forEach var="country" items="${requestScope.countries}">
                                            <option value="${country.id}">${country.countryName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="duration">Duration:</label>

                                <div class="col-sm-9">
                                    <input type="number" class="form-control" name="duration" id="duration"
                                           placeholder="Enter film duration" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="ageRestriction">Age restriction:</label>

                                <div class="col-sm-9">
                                    <input type="number" class="form-control" name="ageRestriction" id="ageRestriction"
                                           placeholder="Enter age restriction" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="genreName">Select genres:</label>

                                <div class="col-sm-9">
                                    <select multiple class="form-control" name="genres" id="genreName">
                                        <c:forEach var="genre" items="${requestScope.genres}">
                                            <option value="${genre.id}">${genre.genreName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="director">Add director:</label>

                                <div class="col-sm-9">
                                    <select class="form-control" name="director" id="director">
                                        <c:forEach var="dir" items="${requestScope.filmMakers}">
                                            <c:if test="${dir.profession == 'DIRECTOR'}">
                                                <option value="${dir.id}">${dir.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="actors">Add actors:</label>

                                <div class="col-sm-9">
                                    <select multiple class="form-control" name="actors" id="actors">
                                        <c:forEach var="actor" items="${requestScope.filmMakers}">
                                            <c:if test="${actor.profession == 'ACTOR'}">
                                                <option value="${actor.id}">${actor.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="price">Price:</label>

                                <div class="col-sm-9">
                                    <input type="number" class="form-control" name="price" id="price" placeholder="Enter price"
                                           value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="link">Poster:</label>

                                <div class="col-sm-9">
                                    <input type="text" class="form-control" name="link" id="link"
                                           placeholder="Enter link to poster" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="description">Description:</label>

                                <div class="col-sm-9">
                        <textarea class="form-control" rows="5" name="description" id="description"
                                  placeholder="Enter description"></textarea>
                                </div>
                            </div>

                        </div>

                        <div class="modal-footer">
                            <button type="submit" class="btn btn-success">Submit</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
