<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}"/><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc"/><!-- locale_ru -->

<fmt:message bundle="${loc}" key="locale.menu.wish_list" var="wish_list"/>
<fmt:message bundle="${loc}" key="locale.button.add_to_cart" var="add_to_cart"/>
<fmt:message bundle="${loc}" key="locale.button.delete" var="delete"/>

<body class="w3-content" style="max-width:1600px">

<jsp:include page="../fragments/userMenu.jsp"/>
<!-- !PAGE CONTENT! -->
<div class="w3-main w3-white" style="margin-left:300px">

    <!-- Header -->
    <header class="w3-container">
        <jsp:include page="../fragments/userSmallPic.jsp"/>

        <div class="w3-section w3-bottombar">
            <h1><b>${wish_list}</b></h1>
            <br>
            <br>
        </div>
    </header>

    <!-- Table-->

    <div class="w3-row-padding">
        <div class="row box" style="padding-left: 4%; background: #dbdbdb">
            <c:forEach var="film" items="${requestScope.filmList}">
                <div class="img w3-hover-shadow">
                    <a href="Controller?command=get-film-by-id&id=${film.id}">
                        <img src="ImageController?img=${film.poster}&type=poster"  class="img-fluid m-y" alt="Poster">
                        <div class="desc">
                            <h6 class="text-uppercase">${film.title}</h6>
                            <div>
                                <span style="font-size: medium; color: #2c8494;"><i class="fa fa-star" aria-hidden="true">${film.rating}</i></span>
                                    ${film.year}
                            </div>
                            <br>
                            <div>
                                <form action="Controller" method="post">
                                    <input type="hidden" name="command" value="user-add-to-cart"/>
                                    <input type="hidden" name="filmId" value="${film.id}">
                                    <input type="hidden" name="price" value="${film.price}">
                                    <button type="submit" class="btn btn-template-main" data-toggle="tooltip" data-placement="top" title="${add_to_cart}"><i class="fa fa-shopping-cart"></i></button>
                                    <a class="btn btn-default" href="Controller?command=user-delete-favorite-film&id=${film.id}"
                                       data-toggle="tooltip" data-placement="top" title="${delete}"><i
                                            class="fa fa-trash-o"></i></a>
                                </form>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- End page content -->
    <jsp:include page="../fragments/footer.jsp"/>
</div>

</body>
</html>



