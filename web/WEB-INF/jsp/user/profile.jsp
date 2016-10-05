<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources.locale" var="loc"/>

<fmt:message bundle="${loc}" key="locale.menu.my_orders" var="my_orders"/>
<fmt:message bundle="${loc}" key="locale.menu.my_orders_info" var="my_orders_info"/>
<fmt:message bundle="${loc}" key="locale.menu.wish_list" var="wish_list"/>
<fmt:message bundle="${loc}" key="locale.menu.wish_list_info" var="wish_list_info"/>
<fmt:message bundle="${loc}" key="locale.menu.my_comments" var="my_comments"/>
<fmt:message bundle="${loc}" key="locale.menu.my_comments_info" var="my_comments_info"/>
<fmt:message bundle="${loc}" key="locale.menu.my_discount" var="my_discount"/>
<fmt:message bundle="${loc}" key="locale.menu.my_discount_info" var="my_discount_info"/>
<fmt:message bundle="${loc}" key="locale.menu.users" var="users"/>
<fmt:message bundle="${loc}" key="locale.menu.users_info" var="users_info"/>
<fmt:message bundle="${loc}" key="locale.menu.add_film" var="add_film"/>
<fmt:message bundle="${loc}" key="locale.menu.add_film_info" var="add_film_info"/>
<fmt:message bundle="${loc}" key="locale.menu.new_comments" var="new_comments"/>
<fmt:message bundle="${loc}" key="locale.menu.new_comments_info" var="new_comments_info"/>
<fmt:message bundle="${loc}" key="locale.menu.art_people" var="art_people"/>
<fmt:message bundle="${loc}" key="locale.menu.art_people_info" var="art_people_info"/>
<fmt:message bundle="${loc}" key="locale.menu.my_page" var="my_page"/>

<body class="w3-content" style="max-width:1600px">

<c:choose>
    <c:when test="${sessionScope.user.role.name()=='ADMIN'}">
        <jsp:include page="../fragments/adminMenu.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="../fragments/userMenu.jsp"/>
    </c:otherwise>
</c:choose>

<!-- !PAGE CONTENT! -->
<div class="w3-main w3-white" style="margin-left:300px">

    <!-- Header -->
    <header class="w3-container">
        <jsp:include page="../fragments/userSmallPic.jsp"/>

        <div class="w3-section w3-bottombar">
            <h1><b>${my_page}</b></h1>
            <br><br>
        </div>
    </header>

    <c:choose>
        <c:when test="${sessionScope.user.role.name()=='USER'}">
            <%--user quick link--%>
            <div class="w3-row-padding">
                <div class="w3-half w3-container w3-margin-bottom">
                    <a href="Controller?command=user-get-orders">
                        <div class="w3-col text-center quick-link">
                            <div><i class="fa fa-3x fa-shopping-cart text-center" aria-hidden="true"></i></div>
                            <h2>${my_orders}</h2>

                            <p>${my_orders_info}</p>
                        </div>
                    </a>
                </div>
                <div class="w3-half w3-container w3-margin-bottom">
                    <a href="Controller?command=user-get-favorite-films">
                        <div class="w3-col text-center quick-link-grey">
                            <div><i class="fa fa-3x fa-fw fa-heart"></i></div>
                            <h2>${wish_list}</h2>

                            <p>${wish_list_info}</p>
                        </div>
                    </a>
                </div>
            </div>
            <div class="w3-row-padding">
                <div class="w3-half w3-container w3-margin-bottom">
                    <a href="Controller?command=user-get-discount">
                        <div class="w3-col text-center quick-link-grey">
                            <div><i class="fa fa-3x fa-percent" aria-hidden="true"></i></div>
                            <h2>${my_discount}</h2>

                            <p>${my_discount_info}</p>
                        </div>
                    </a>
                </div>
                <div class="w3-half w3-container w3-margin-bottom">
                    <a href="Controller?command=user-get-comments">
                        <div class="w3-col text-center quick-link">
                            <div><i class="fa fa-3x fa-commenting" aria-hidden="true"></i></div>
                            <h2>${my_comments}</h2>

                            <p>${my_comments_info}</p>
                        </div>
                    </a>
                </div>
            </div>
            <%--user quick link end--%>
        </c:when>
        <c:otherwise>
            <%--admin quick link--%>
            <div class="w3-row-padding">
                <div class="w3-half w3-container w3-margin-bottom">
                    <a href="Controller?command=admin-add-page-film" class="">
                        <div class="w3-col text-center quick-link">
                            <div><i class="fa fa-3x fa-film" aria-hidden="true"></i></div>
                            <h2>${add_film}</h2>

                            <p>${add_film_info}</p>
                        </div>
                    </a>
                </div>
                <div class="w3-half w3-container w3-margin-bottom">
                    <a href="Controller?command=admin-get-users">
                        <div class="w3-col text-center quick-link-grey">
                            <div><i class="fa fa-3x fa-users" aria-hidden="true"></i></div>
                            <h2>${users}</h2>

                            <p>${users_info}</p>
                        </div>
                    </a>
                </div>
            </div>
            <div class="w3-row-padding">
                <div class="w3-half w3-container w3-margin-bottom">
                    <a href="Controller?command=admin-get-comments" class="">
                        <div class="w3-col text-center quick-link-grey">
                            <div><i class="fa fa-3x fa-commenting" aria-hidden="true"></i></div>
                            <h2>${new_comments}</h2>

                            <p>${new_comments_info}</p>
                        </div>
                    </a>
                </div>
                <div class="w3-half w3-container w3-margin-bottom">
                    <a href="Controller?command=admin-get-film-makers">
                        <div class="w3-col text-center quick-link">
                            <div><i class="fa fa-3x fa-star" aria-hidden="true"></i></div>
                            <h2>${art_people}</h2>

                            <p>${art_people_info}</p>
                        </div>
                    </a>
                </div>
            </div>
            <%--admin quick link end--%>
        </c:otherwise>
    </c:choose>
    <!-- End page content -->
    <!-- Footer -->
<jsp:include page="../fragments/footer.jsp"/>

</div>
</body>
</html>


