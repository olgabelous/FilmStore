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
                <%--<a href="#myModal" data-toggle="modal" class="btn btn-primary">Add Film</a>--%>
                <a href="/FilmStore/UserServlet?command=admin-add-page-film" class="btn btn-primary">Add Film</a>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Название</th>
                        <th>Год выпуска</th>
                        <th>Страна</th>
                        <th>Возраст</th>
                        <th>Цена</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="filmItem" items="${requestScope.filmList}">
                        <tr>
                            <td>${filmItem.id}</td>
                            <td><a href="/FilmStore/UserServlet?command=get_film_by_id&id=${filmItem.id}">${filmItem.title}</a></td>
                            <td>${filmItem.year}</td>
                            <td>${filmItem.country.countryName}</td>
                            <td>${filmItem.ageRestriction}</td>
                            <td>${filmItem.price}</td>
                            <td><button type="submit" class="btn btn-primary">Edit</button></td>
                            <td><a href="/FilmStore/UserServlet?command=admin-delete-film&id=${filmItem.id}" class="btn btn-danger">Delete</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Add new film</h4>
            </div>
            <form class="form-horizontal" action="/FilmStore/UserServlet" method="post">
                <input type="hidden" name="command" value="admin-add-film"/><br/>

                <div class="modal-body">

                    <div class="form-group">
                        <label class="control-label col-sm-3" for="film-title">Title:</label>

                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="title" id="film-title"
                                   placeholder="Enter title">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-3" for="year">Year:</label>

                        <div class="col-sm-9">
                            <input type="number" class="form-control" name="year" id="year" placeholder="Enter year"
                                   value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="countryId">Country:</label>

                        <div class="col-sm-9">
                            <select class="form-control" name="countryId" id="countryId">
                                <c:forEach var="country" items="${requestScope.countries}">
                                    <option value="${country.id}">${country.countryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="duration">Duration:</label>

                        <div class="col-sm-9">
                            <input type="number" class="form-control" name="duration" id="duration"
                                   placeholder="Enter film duration"
                                   value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="ageRestriction">Age restriction:</label>

                        <div class="col-sm-9">
                            <input type="number" class="form-control" name="ageRestriction" id="ageRestriction"
                                   placeholder="Enter age restriction" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="genreName">Select genres:</label>

                        <div class="col-sm-9">
                            <select class="form-control" name="genres" id="genreName">
                                <c:forEach var="genre" items="${requestScope.genres}">
                                    <option value="${genre.id}">${genre.genreName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="director">Add director:</label>

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
                        <label class="control-label col-sm-3" for="actors">Add actors:</label>

                        <div class="col-sm-9">
                            <select class="form-control" name="actors" id="actors">
                                <c:forEach var="actor" items="${requestScope.filmMakers}">
                                    <c:if test="${actor.profession == 'ACTOR'}">
                                        <option value="${actor.id}">${actor.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="price">Price:</label>

                        <div class="col-sm-9">
                            <input type="number" class="form-control" name="price" id="price" placeholder="Enter price"
                                   value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="link">Poster:</label>

                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="link" id="link"
                                   placeholder="Enter link to poster" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="description">Description:</label>

                        <div class="col-sm-9">
                        <textarea class="form-control" rows="5" name="description" id="description"
                                  placeholder="Enter description"></textarea>
                        </div>
                    </div>

                </div>

                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">Submit</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </form>
        </div>
        <!-- Modal content end-->
    </div>
</div>
<!-- Modal End -->

</body>
</html>