<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.filter.genres" var="genres" />
<fmt:message bundle="${loc}" key="locale.filter.countries" var="countries" />
<fmt:message bundle="${loc}" key="locale.filter.year" var="year" />
<fmt:message bundle="${loc}" key="locale.filter.search" var="search" />
<fmt:message bundle="${loc}" key="locale.filter.s" var="s" />

<div class="navbar navbar-full navbar-light" style="background: #f1f1f1; border-bottom: 1px solid #d4d4d4;">
    <div class="container">
        <ul class="nav navbar-nav pull-left ">
            <li class="nav-item active dropdown-open">
                <a class="nav-link dropdown-toggle" data-toggle="dropdown" aria-expanded="true" href="#">${genres}</a>
                <ul class="dropdown-menu">
                    <c:forEach var="genre" items="${sessionScope.genreList}">
                        <li><a href="Controller?command=get-filtered-films&genre=${genre.id}">${genre.genreName}</a></li>
                    </c:forEach>
                </ul>
            </li>
            <li class="nav-item active dropdown">
                <a class="nav-link dropdown-toggle" data-toggle="dropdown" aria-expanded="true" href="#">${countries}</a>
                <ul class="dropdown-menu">
                    <c:forEach var="country" items="${sessionScope.countryList}">
                        <li><a href="Controller?command=get-filtered-films&country=${country.id}">${country.countryName}</a></li>
                    </c:forEach>
                </ul>
            </li>
            <li class="nav-item active dropdown-open">
                <a class="nav-link dropdown-toggle" data-toggle="dropdown" aria-expanded="true" href="#">${year}</a>
                <ul class="dropdown-menu">
                    <li><a href="Controller?command=get-filtered-films&year=2016">2016</a></li>
                    <li><a href="Controller?command=get-filtered-films&year=2010">2010-${s}</a></li>
                    <li><a href="Controller?command=get-filtered-films&year=2000">2000-${s}</a></li>
                    <li><a href="Controller?command=get-filtered-films&year=1990">90-${s}</a></li>
                    <li><a href="Controller?command=get-filtered-films&year=1980">80-${s}</a></li>
                    <li><a href="Controller?command=get-filtered-films&year=1970">70-${s}</a></li>
                    <li><a href="Controller?command=get-filtered-films&year=1960">60-${s}</a></li>
                </ul>
            </li>
        </ul>
        <div class="" style="max-width: 350px; display: inline-block; max-height: 62px; padding: 15px 20px; float:right;">
            <form role="search" method="get" action="Controller">
                <input type="hidden" name="command" value="search-film">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="${search}" name="q" value="${requestScope.searchQuery}" required>
                    <span class="input-group-btn">
    		            <button type="submit" class="btn btn-template-main" style="padding: 8px 12px 9px;"><i class="fa fa-search"></i></button>
                    </span>
                </div>
            </form>
        </div>
    </div>
</div>