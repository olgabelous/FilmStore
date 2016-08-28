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
                <button type="submit" class="btn btn-primary">Add user</button>
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
                            <td><a href="FilmStore/UserServlet?command=admin-delete-film&id=${filmItem.id}" class="btn btn-danger">Delete</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

</body>
</html>