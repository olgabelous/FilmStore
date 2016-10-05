<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}"/><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" />
<fmt:message bundle="${loc}" key="locale.film.duration" var="duration"/>
<fmt:message bundle="${loc}" key="locale.film.country" var="country"/>
<fmt:message bundle="${loc}" key="locale.film.year" var="year"/>
<fmt:message bundle="${loc}" key="locale.film.genres" var="genres"/>
<fmt:message bundle="${loc}" key="locale.film.director" var="director"/>
<fmt:message bundle="${loc}" key="locale.film.actors" var="actors"/>
<fmt:message bundle="${loc}" key="locale.film.age_restr" var="age_restr"/>
<fmt:message bundle="${loc}" key="locale.film.price" var="price"/>
<fmt:message bundle="${loc}" key="locale.film.rating" var="rating"/>
<fmt:message bundle="${loc}" key="locale.film.comments" var="comments"/>
<fmt:message bundle="${loc}" key="locale.film.plot" var="plot"/>
<fmt:message bundle="${loc}" key="locale.film.message" var="message"/>
<fmt:message bundle="${loc}" key="locale.film.leave_comment" var="leave_comment"/>
<fmt:message bundle="${loc}" key="locale.film.comment" var="comment"/>
<fmt:message bundle="${loc}" key="locale.film.post_comment" var="post_comment"/>
<fmt:message bundle="${loc}" key="locale.film.post_comment_mess" var="post_comment_mess"/>
<fmt:message bundle="${loc}" key="locale.film.in_wishlist" var="in_wishlist"/>
<fmt:message bundle="${loc}" key="locale.button.add_to_cart" var="add_to_cart"/>
<fmt:message bundle="${loc}" key="locale.button.add_to_wishlist" var="add_to_wishlist"/>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<jsp:include page="fragments/filterPanel.jsp"/>

<div id="content">
    <div class="container main1">

        <div class="row">

            <div class="col-md-10 col-md-offset-1">

                <div class="row" id="productMain">
                    <div class="col-sm-6">
                        <img src="ImageController?img=${requestScope.film.poster}&type=poster" alt="" class="img-fluid"
                             style="max-height: 550px; min-height: 550px; margin: 0 auto;">
                    </div>
                    <div class="col-sm-6">
                        <div class="box" id="order-summary">

                            <div class="box-header text-center" style="padding: 10px; margin-bottom: 0;">
                                <h3>${requestScope.film.title}</h3>
                            </div>


                            <table class="table table-responsive" style="margin-top: 0;">
                                <tbody>
                                <tr>
                                    <td>${rating}:</td>
                                    <td>${requestScope.film.rating}</td>
                                </tr>
                                <tr>
                                    <td>${duration}:</td>
                                    <td>${requestScope.film.duration}</td>
                                </tr>
                                <tr>
                                    <td>${country}:</td>
                                    <td>
                                        <a href="Controller?command=get-filtered-films&country=${requestScope.film.country.id}">${requestScope.film.country.countryName}</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>${year}:</td>
                                    <td>
                                        <a href="Controller?command=get-filtered-films&year=${requestScope.film.year}">${requestScope.film.year}</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>${genres}:</td>
                                    <td><c:forEach var="category" items="${requestScope.film.genreList}">
                                        <a href="Controller?command=get-filtered-films&genre=${category.id}">${category.genreName}</a><span> </span>
                                    </c:forEach></td>
                                </tr>
                                <tr>
                                    <td>${director}:</td>
                                    <td><c:forEach var="director" items="${requestScope.film.filmMakerList}">
                                        <c:if test="${director.profession == 'DIRECTOR'}">
                                            <a href="#">${director.name}</a><span> </span>
                                        </c:if>
                                    </c:forEach></td>
                                </tr>
                                <tr>
                                    <td>${actors}:</td>
                                    <td><c:forEach var="actor" items="${requestScope.film.filmMakerList}">
                                        <c:if test="${actor.profession == 'ACTOR'}">
                                            <a href="#">${actor.name}</a><span> </span>
                                        </c:if>
                                    </c:forEach></td>
                                </tr>
                                <tr>
                                    <td>${age_restr}:</td>
                                    <td>${requestScope.film.ageRestriction}+</td>
                                </tr>
                                </tbody>
                            </table>


                            <p class="price" style="margin: 10px;">$${requestScope.film.price}</p>

                            <c:choose>
                                <c:when test="${sessionScope.user.role.name()=='USER'}">
                                    <form action="Controller" method="post">

                                        <input type="hidden" name="command" value="user-add-to-cart"/>
                                        <input type="hidden" name="filmId" value="${requestScope.film.id}">
                                        <input type="hidden" name="price" value="${requestScope.film.price}">

                                        <div class="btn-group" style="margin: 10px 90px 0;">

                                            <button type="submit" class="btn btn-template-main"
                                                    style="margin-right: 3px;"><i
                                                    class="fa fa-shopping-cart"></i> ${add_to_cart}</button>
                                            <c:choose>
                                                <c:when test="${requestScope.isInWishList}">
                                                    <a href="Controller?command=user-get-favorite-films"
                                                       class="btn btn-template-primary" data-toggle="tooltip"
                                                       data-placement="top" title="${in_wishlist}"
                                                       style="padding: 8px 12px 9px;"><i class="fa fa-heart-o"></i></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="Controller?command=user-add-favorite-film&id=${requestScope.film.id}"
                                                       class="btn btn-default" data-toggle="tooltip"
                                                       data-placement="top" title="${add_to_wishlist}"
                                                       style="padding: 8px 12px 9px;"><i class="fa fa-heart-o"></i></a>
                                                </c:otherwise>
                                            </c:choose>

                                        </div>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <div class="btn-group" style="margin: 10px 90px 0;">

                                        <button disabled class="btn btn-template-main" style="margin-right: 3px;"><i
                                                class="fa fa-shopping-cart"></i> ${add_to_cart}</button>
                                        <a href="" disabled class="btn btn-default" style="padding: 8px 12px 9px;"><i
                                                class="fa fa-heart-o"></i></a>

                                    </div>
                                    <br>

                                    <p class="text-muted" style="font-size: smaller; padding-top: 20px;">${message}</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>


                <div class="box" id="detail">
                    <p>
                    <h4 class="text-uppercase">${plot}</h4>

                    <blockquote>
                        <p>${requestScope.film.description}</p>
                    </blockquote>
                </div>

                <div id="comment-form">
                    <c:choose>
                        <c:when test="${sessionScope.user.role.name()=='USER'}">
                            <form action="Controller" method="post" onsubmit="return checkFormComment(this);">
                                <input hidden name="command" value="user-add-comment">
                                <input hidden name="filmId" value="${requestScope.film.id}">

                                <h4 class="text-uppercase">${leave_comment}</h4>

                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <span class="starRating">
                                                <input id="rating5" type="radio" name="mark" value="5">
                                                <label for="rating5">5</label>
                                                <input id="rating4" type="radio" name="mark" value="4">
                                                <label for="rating4">4</label>
                                                <input id="rating3" type="radio" name="mark" value="3">
                                                <label for="rating3">3</label>
                                                <input id="rating2" type="radio" name="mark" value="2">
                                                <label for="rating2">2</label>
                                                <input id="rating1" type="radio" name="mark" value="1">
                                                <label for="rating1">1</label>
                                            </span>
                                        </div>
                                        <span id='message5'></span>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label for="comment">${comment}*</label>
                                            <textarea class="form-control" id="comment" name="comment"
                                                      rows="4" required></textarea>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-sm-12 text-right">
                                        <button class="btn btn-template-main"><i
                                                class="fa fa-comment-o"></i>${post_comment}
                                        </button>
                                    </div>
                                </div>

                            </form>
                        </c:when>
                        <c:when test="${sessionScope.user.role.name()=='ADMIN'}"/>
                        <c:otherwise>
                            <br>

                            <p class="text-muted padding-big">${post_comment_mess}</p>
                            <br>
                        </c:otherwise>
                    </c:choose>


                </div>
                <c:choose>
                    <c:when test="${requestScope.film.commentsList.size() != 0}">
                        <div id="comments">
                            <h4 class="text-uppercase">${comments}</h4>
                            <br>
                            <c:forEach var="comment" items="${requestScope.film.commentsList}">
                                <div class="row comment">

                                    <div class="col-sm-3 col-md-2 text-center-xs">
                                        <p>
                                            <img src="ImageController?img=${comment.user.photo}&type=photo"
                                                 class="img-responsive img-circle" alt="">
                                        </p>
                                    </div>
                                    <div class="col-sm-9 col-md-10">
                                        <h5 class="text-uppercase">${comment.user.name}</h5>

                                        <p class="posted" style="float: right;"><i
                                                class="fa fa-clock-o"></i> ${comment.dateComment.toString().replace('T', ' ')}
                                        </p>

                                        <p style="font-size: medium;">
                                            <c:forEach begin="0" end="4" varStatus="loop">
                                                <span style="color: ${loop.index < comment.mark ? "gold" : "lightgrey"}">
                                                    <c:out value="★"/>
                                                </span>
                                            </c:forEach>
                                        </p>

                                        <p>${comment.text}</p>
                                    </div>
                                </div>
                            </c:forEach>

                            <!-- /.comment -->
                        </div>
                        <!-- /#comments -->
                    </c:when>
                </c:choose>
                <!-- /#comment-form -->
            </div>
            <!-- /.col-md-9 -->

        </div>
        <br>
        <br>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
<script>
    function checkFormComment(form) {
        $('#message5').html('');

        var bool = true;

        if ($('input[name=mark]:checked').size() == 0) {
            $('#message5').html('fail').css('color', 'red');
            bool = false;
        }
        if(form.comment.value === null) {
            $('#message1').html('${comment_empty}').css('color', 'red');
            bool = false;
        }
        return bool;
    }
</script>
</body>
</html>
