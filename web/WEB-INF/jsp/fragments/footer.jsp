<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

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
