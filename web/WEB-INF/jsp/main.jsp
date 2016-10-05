<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="fragments/headTag.jsp"/>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.index.new2016" var="new2016"/>
<fmt:message bundle="${loc}" key="locale.index.best" var="best"/>
<fmt:message bundle="${loc}" key="locale.index.see_more" var="see_more"/>
<fmt:message bundle="${loc}" key="locale.index.common_err_mess" var="common_err_mess"/>
<fmt:message bundle="${loc}" key="locale.index.admin_err_mess" var="admin_err_mess"/>
<fmt:message bundle="${loc}" key="locale.index.user_err_mess" var="user_err_mess"/>

<jsp:include page="fragments/bodyHeader.jsp"/>
<jsp:include page="fragments/filterPanel.jsp"/>
<jsp:include page="fragments/carousel.jsp"/>
<br>
<div class="section ">
    <div class="container ">
        <div class="row">
            <div class="heading col-md-12">
                <h3>${new2016}</h3>
            </div>
        </div>
        <div class="row">
                <div class="box">
                    <div class="owl-carousel">
                        <c:forEach var="newfilm" items="${requestScope.newfilms}">
                            <div class="img w3-hover-shadow">
                                <a href="Controller?command=get-film-by-id&id=${newfilm.id}">
                                    <img src="ImageController?img=${newfilm.poster}&type=poster" class="img-fluid m-y"
                                         alt="Обложка">

                                    <div class="desc"><h6 class="text-uppercase">${newfilm.title}</h6></div>
                                </a>
                            </div>
                        </c:forEach>
                    </div>

                <div class="col-md-2 col-md-offset-5"><a href="Controller?command=get-filtered-films&year=2016"
                                                         class="btn btn-template-main">${see_more}</a></div>

            </div>
        </div>
    </div>
</div>

<div class="section">
    <div class="container">
        <div class="row">
            <div class="heading col-md-12">
                <h3>${best}</h3>
            </div>
        </div>
        <div class="row">
            <div class="box">
                <div class="owl-carousel">
                    <c:forEach var="bestfilm" items="${requestScope.bestfilms}">
                        <div class="img w3-hover-shadow">
                            <a href="Controller?command=get-film-by-id&id=${bestfilm.id}">
                                <img src="ImageController?img=${bestfilm.poster}&type=poster" class="img-fluid m-y"
                                     alt="Обложка">

                                <div class="desc"><h6 class="text-uppercase">${bestfilm.title}</h6></div>
                            </a>
                        </div>
                    </c:forEach>
                </div>

            <div class="col-md-2 col-md-offset-5"><a href="Controller?command=get-filtered-films&rating=4"
                                                     class="btn btn-template-main">${see_more}</a></div>
            </div>
        </div>
    </div>
</div>


<jsp:include page="fragments/footer.jsp"/>

<!-- Error message modal -->
<div class="modal fade" id="accessDeniedModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content w3-pale-blue">
            <div class="modal-body">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <div class="text-center">
                    <br>
                    <h4 id="commonErrMess" style="display: none">${common_err_mess}</h4>
                    <h4 id="adminErrMess" style="display: none">${admin_err_mess}</h4>
                    <h4 id="userErrMess" style="display: none">${user_err_mess}</h4>
                    <br>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Error message modal end-->
<!-- Errors -->
<c:choose>
    <c:when test="${not empty requestScope.accessDeniedError}">
        <c:set value="showCommonErrMess" var="err"/>
    </c:when>
    <c:when test="${not empty requestScope.accessDeniedErrorAdmin}">
        <c:set value="showAdminErrMess" var="err"/>
    </c:when>
    <c:when test="${not empty requestScope.accessDeniedErrorUser}">
        <c:set value="showUserErrMess" var="err"/>
    </c:when>
    <c:otherwise>
        <c:set value="none" var="err"/>
    </c:otherwise>
</c:choose>
<!-- Errors end-->

<script>
    $(window).load(function () {
        if ('${err}' === 'showCommonErrMess') {
            $('#accessDeniedModal').modal('show');
            $('#commonErrMess').show();
        }
        if ('${err}' === 'showAdminErrMess') {
            $('#accessDeniedModal').modal('show');
            $('#adminErrMess').show();
        }
        if ('${err}' === 'showUserErrMess') {
            $('#accessDeniedModal').modal('show');
            $('#userErrMess').show();
        }
    });

    $(document).ready(function () {
        $('.owl-carousel').owlCarousel({
            nav: true,
            navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
            loop: true,
            margin: 10,
            responsiveClass: true,
            responsive: {
                0: {
                    items: 2,
                    nav: true
                },
                600: {
                    items: 4,
                    nav: true
                },
                1000: {
                    items: 6,
                    nav: true,
                    loop: false
                }
            }
        })
    });

</script>
</body>
</html>
