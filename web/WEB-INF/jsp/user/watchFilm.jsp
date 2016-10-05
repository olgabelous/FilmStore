<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="../fragments/headTag.jsp"/>
<body style="background: white">
<div style="margin: 0 auto; width: 560px; padding: 120px 0 50px;">
    <iframe id="frame" width="560" height="315" src="${requestScope.film.video}" frameborder="0" allowfullscreen></iframe>
</div>
<img src="resources/images/movie-cat.jpg"  style="float: right; width: 20%;">
</body>
</html>
