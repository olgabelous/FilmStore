<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.filter.add_filter" var="add_filter" />
<fmt:message bundle="${loc}" key="locale.filter.show_films" var="show_films" />
<fmt:message bundle="${loc}" key="locale.filter.clear_all" var="clear_all" />
<fmt:message bundle="${loc}" key="locale.filter.genres" var="genres" />
<fmt:message bundle="${loc}" key="locale.filter.countries" var="countries" />
<fmt:message bundle="${loc}" key="locale.filter.year" var="year" />
<fmt:message bundle="${loc}" key="locale.filter.search" var="search" />
<fmt:message bundle="${loc}" key="locale.filter.s" var="s" />

<div class="section main">
    <div class="container">
        <div class="row">
            <form action="Controller" method="get">
                <input type="hidden" name="command" value="get-filtered-films">
                <div class="col-md-12">
                    <div class="col-lg-4" >
                        <div class="" style="margin-top: 5px;">
                            <a class="btn btn-template-main" id="showFilterList">${add_filter}<b class="caret"></b></a>
                            <button class="btn btn-template-main" type="submit">${show_films}<i class="fa fa-arrow-right" aria-hidden="true"></i></button>
                        </div>
                    </div>

                    <div class="col-lg-8 filters">
                        <ul id="filters-tags" class="filters-tags">
                            <c:forEach  var="entry" items="${requestScope.genreFilter}">
                                <li>${entry.value}<input type="hidden" name="genre" value="${entry.key}"></li>
                            </c:forEach>
                            <c:forEach  var="entry" items="${requestScope.countryFilter}">
                                <li>${entry.value}<input type="hidden" name="country" value="${entry.key}"></li>
                            </c:forEach>
                            <c:forEach  var="item" items="${requestScope.yearFilter}">
                                <c:choose>
                                    <c:when test="${item eq 2016}">
                                        <li>${item}<input type="hidden" name="year" value="${item}"></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li>${item}-${s}<input type="hidden" name="year" value="${item}"></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </form>

        </div>
        <div class="row" id="filterlist" style="display:none">
            <div class="col-md-12 wrapper">
                <div class="">
                    <h4 class="text-uppercase">${genres}</h4>
                    <ul id="genreUl" class="list-unstyled twocol">
                        <c:forEach var="genre" items="${applicationScope.genreList}">
                            <li><a onclick="myF(this)" name="genre" id="g${genre.id}">${genre.genreName}</a></li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="">
                    <h4 class="text-uppercase">${countries}</h4>
                    <ul id="countryUl" class="list-unstyled twocol">
                        <c:forEach var="country" items="${applicationScope.countryList}">
                            <li><a class="filter-item" onclick="myF(this)" name="country" id="c${country.id}">${country.countryName}</a></li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="">
                    <h4 class="text-uppercase">${year}</h4>
                    <ul id="yearUl" class="list-unstyled " draggable="true">
                        <li><a onclick="myF(this)" name="year" id="2016" class="one">2016</a></li>
                        <li><a onclick="myF(this)" name="year" id="2010" class="one">2010-${s}</a></li>
                        <li><a onclick="myF(this)" name="year" id="2000" class="one">2000-${s}</a></li>
                        <li><a onclick="myF(this)" name="year" id="1990" class="one">1990-${s}</a></li>
                        <li><a onclick="myF(this)" name="year" id="1980" class="one">1980-${s}</a></li>
                        <li><a onclick="myF(this)" name="year" id="1970" class="one">1970-${s}</a></li>
                        <li><a onclick="myF(this)" name="year" id="1960" class="one">1960-${s}</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-md-12"><div class="col-lg-3">
                <a class="btn btn-template-main" id="clearFilterList">${clear_all}</a>
            </div></div>
        </div>

    </div>
</div>

<script>
    $(document).ready(function () {
        $("#showFilterList").click(function () {
            first();
            $("#filterlist").toggle(1000);
        });
    });

    function noop() {}

    function first() {
    first = noop;
        var childrenLi = document.getElementById("filters-tags").getElementsByTagName("li");
        for (var i = 0; i < childrenLi.length; i++) {
            var inputEl = childrenLi[i].getElementsByTagName("input")[0];
            if(inputEl.getAttribute('name')=='genre'){
                var elem = document.getElementById('g'+inputEl.getAttribute('value'));
            }
            else if(inputEl.getAttribute('name')=='country') {
                elem = document.getElementById('c' + inputEl.getAttribute('value'));
            }
            else{
                elem = document.getElementById(inputEl.getAttribute('value'));
            }

            if(elem.classList.contains('one')){
                elem.parentElement.parentElement.parentElement.style.display = "none";
            }
            else{
                elem.parentElement.classList.add('hid');
                elem.parentElement.style.display = "none";
            }
        }
    }

    $(document).ready(function () {
        $("#clearFilterList").click(function () {
            var myNode = document.getElementById("filters-tags");
            while (myNode.firstChild) {
                myNode.removeChild(myNode.firstChild);
            }
            var childrenDiv = document.getElementById("filterlist").getElementsByTagName("div");
            for (var j = 0; j < childrenDiv.length; j++) {
                childrenDiv[j].style.display = "inline-block";
            }
            var childrenLi = document.getElementById("filterlist").getElementsByTagName("li");
            for (var i = 0; i < childrenLi.length; i++) {
                childrenLi[i].style.display = "block";
                childrenLi[i].classList.remove('hid');
            }
        });
    });

    function myF(str) {
        var ul = document.getElementById("filters-tags");
        var li = document.createElement("li");
        var inp = document.createElement("input");
        inp.name = str.getAttribute('name');
        if(inp.name === 'genre' || inp.name === 'country'){
            inp.value = str.getAttribute('id').substring(1);
        }
        else{
            inp.value = str.getAttribute('id');
        }
        inp.type = 'hidden';
        li.appendChild(document.createTextNode(str.innerHTML));
        li.appendChild(inp);
        ul.appendChild(li);

        if(str.classList.contains('one')){
            str.parentElement.parentElement.parentElement.style.display = "none";
        }
        else{
            var elementLi = str.parentElement;
            elementLi.style.display = "none";
            elementLi.classList.add('hid');
            var parentUl = elementLi.parentElement;
            var childrenHidden = parentUl.getElementsByClassName("hid").length;
            var childrenLi = parentUl.getElementsByTagName("li").length;
            if (childrenHidden === childrenLi){
                parentUl.parentElement.style.display = "none";
            }
        }
    }

</script>
