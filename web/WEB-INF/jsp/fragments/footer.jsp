<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources.locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.footer" var="final_project"/>

<footer>
    <div>
        <div class="container">
            <div class="col-md-12">
                <p style="float: right">&copy; 2016. Film Store Movie Time. ${final_project}</p>
            </div>
        </div>
    </div>
</footer>
