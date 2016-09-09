<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="WEB-INF/jsp/fragments/headTag.jsp"/>

<body>
    <c:redirect url="Controller?command=load-main-page&year=2016"/>
</body>

</html>