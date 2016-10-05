<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="WEB-INF/jsp/fragments/headTag.jsp"/>

<body>
    <c:redirect url="Controller?command=load-main-page"/>
<%--
    <script type="text/javascript">
            $('#inset_form').html('<form action="Controller" name="form" method="post" style="display:none;"><input type="hidden" name="command" value="load-main-page" /><input type="hidden" name="year" value="2016" /></form>');
            document.forms['form'].submit();
    </script>
--%>

</body>

</html>