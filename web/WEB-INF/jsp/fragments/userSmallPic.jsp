<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<c:choose>
    <c:when test="${not empty sessionScope.user.photo}">
        <a href="#"><img src="ImageController?img=${sessionScope.user.photo}&type=photo" style="width:65px;"
                 class="w3-circle w3-right w3-margin w3-hide-large w3-hover-opacity"></a>
    </c:when>
    <c:otherwise>
        <a href="#"><img src="resources/images/user_placeholder.png" style="width:65px;"
                 class="w3-circle w3-right w3-margin w3-hide-large w3-hover-opacity"></a>
    </c:otherwise>
</c:choose>

<span class="w3-opennav w3-hide-large w3-xxlarge w3-hover-text-grey" onclick="w3_open()"><i
        class="fa fa-bars"></i></span>

</html>
