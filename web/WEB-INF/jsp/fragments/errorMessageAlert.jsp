<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${not empty requestScope.errorMessage}">
        <c:set value="block" var="displayStatus"/>
    </c:when>
    <c:otherwise>
        <c:set value="none" var="displayStatus"/>
    </c:otherwise>
</c:choose>
<div style="display:${displayStatus}" id="login-alert" class="alert alert-danger col-sm-12">
    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    ${requestScope.errorMessage}
</div>

