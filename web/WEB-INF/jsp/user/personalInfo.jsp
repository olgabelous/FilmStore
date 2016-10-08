<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<jsp:include page="../fragments/headTag.jsp"/>

<fmt:setLocale value="${sessionScope.locale}"/><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc"/><!-- locale_ru -->

<fmt:message bundle="${loc}" key="locale.menu.personal_info" var="personal_info"/>
<fmt:message bundle="${loc}" key="locale.persinfo.message" var="message"/>
<fmt:message bundle="${loc}" key="locale.persinfo.pers_details" var="pers_details"/>
<fmt:message bundle="${loc}" key="locale.persinfo.name" var="name"/>
<fmt:message bundle="${loc}" key="locale.persinfo.email" var="email"/>
<fmt:message bundle="${loc}" key="locale.persinfo.phone" var="phone"/>
<fmt:message bundle="${loc}" key="locale.persinfo.upload_photo" var="upload_photo"/>
<fmt:message bundle="${loc}" key="locale.persinfo.save_changes" var="save_changes"/>
<fmt:message bundle="${loc}" key="locale.persinfo.change_pass" var="change_pass"/>
<fmt:message bundle="${loc}" key="locale.persinfo.old_pass" var="old_pass"/>
<fmt:message bundle="${loc}" key="locale.persinfo.new_pass" var="new_pass"/>
<fmt:message bundle="${loc}" key="locale.persinfo.confirm_pass" var="confirm_pass"/>
<fmt:message bundle="${loc}" key="locale.persinfo.save_new_pass" var="save_new_pass"/>
<fmt:message bundle="${loc}" key="locale.persinfo.enter_new" var="enter_new"/>
<fmt:message bundle="${loc}" key="locale.persinfo.enter_old" var="enter_old"/>
<fmt:message bundle="${loc}" key="locale.persinfo.confirm_new_pass" var="confirm_new_pass"/>
<fmt:message bundle="${loc}" key="locale.navbar.pass_tooltip" var="pass_tooltip"/>
<fmt:message bundle="${loc}" key="locale.navbar.pass_not_correct" var="pass_not_correct"/>
<fmt:message bundle="${loc}" key="locale.navbar.pass_not_equals" var="pass_not_equals"/>

<c:choose>
    <c:when test="${not empty requestScope.errorMessage}">
        <c:set value="block" var="displayStatus"/>
    </c:when>
    <c:otherwise>
        <c:set value="none" var="displayStatus"/>
    </c:otherwise>
</c:choose>

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
<div class="w3-main page-content">

    <!-- Header -->
    <header class="w3-container">
        <jsp:include page="../fragments/userSmallPic.jsp"/>

        <div class="w3-section w3-bottombar">
            <h1><b>${personal_info}</b></h1>
            <br>
            <br>
        </div>
    </header>

    <!-- Change personal info-->
    <div id="content" class="clearfix">

        <div class="container">

            <div class="row">

                <div class="col-md-9 clearfix" id="customer-account">

                    <p class="lead text-muted">${message}</p>

                    <div class="box">
                        <div class="heading">
                            <h3 class="text-uppercase">${pers_details}</h3>
                        </div>

                        <form action="Controller" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="command" value="user-update-profile"/>
                            <input type="hidden" name="id" value=""/>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="name">${name}:</label>
                                        <input type="text" class="form-control" id="name" name="name"
                                               placeholder="Enter name" value="${sessionScope.user.name}" required>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="email">${email}:</label>
                                        <input type="email" class="form-control" name="email" id="email" placeholder="Enter email"
                                               value="${sessionScope.user.email}" required>
                                    </div>
                                </div>
                            </div>
                            <!-- /.row -->

                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="user-phone">${phone}:</label>
                                        <input type="text" class="form-control" name="phone" id="user-phone"
                                               value="${sessionScope.user.phone}" required>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="photo">${upload_photo}:</label>
                                        <input type="file" class="form-control" name="photo" id="photo">
                                    </div>
                                </div>

                                <div class="col-sm-12 text-center">
                                    <button type="submit" class="btn btn-template-main"><i class="fa fa-save"></i> ${save_changes}</button>

                                </div>
                            </div>
                        </form>

                    </div>

                    <div class="box  clearfix">

                        <div class="heading">
                            <h3 class="text-uppercase">${change_pass}</h3>
                        </div>
                        <div id="changePassAlert" style="display:${displayStatus}" class="alert alert-danger">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            <p>Error: <span>${requestScope.errorMessage}</span></p>
                        </div>

                        <form action="Controller" method="post" onsubmit="return checkFormPass(this);">
                            <input type="hidden" name="command" value="user-change-password">
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="oldPass">${old_pass}*</label>
                                        <input type="password" class="form-control" id="oldPass" name="oldPass"
                                               placeholder="${enter_old}" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="newPass1">${new_pass}*</label>
                                        <input type="password" class="form-control" name="newPass1" id="newPass1"
                                               placeholder="${enter_new}" data-toggle="tooltip" title="${pass_tooltip}"
                                               data-placement="top" autocomplete="off" required><span id='message0'></span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="newPass2">${confirm_pass}*</label>
                                        <input type="password" class="form-control" name="newPass2" id="newPass2"
                                               placeholder="${confirm_new_pass}" required><span id='message1'></span>
                                    </div>
                                </div>
                            </div>
                            <!-- /.row -->

                            <div class="text-center">
                                <button type="submit" class="btn btn-template-main"><i class="fa fa-save"></i> ${save_new_pass}</button>
                            </div>
                        </form>

                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col-md-9 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container -->
    </div>
    <!-- /#content --
    <!-- End page content -->
</div>
<jsp:include page="../fragments/footer.jsp"/>
<script>

    jQuery(function ($) {
        $("#user-phone").mask("+999 (99) 999-99-99", {placeholder: "+___ (__) ___-__-__"});
    });

    function checkFormPass(form)
    {
        $('#message0').html('');
        $('#message1').html('');

        var re = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;
        var bool = true;

        if (!re.test(form.newPass1.value)) {
            $('#message0').html('${pass_not_correct}').css('color', 'red');
            bool = false;
        }
        if(form.newPass1.value !== form.newPass2.value){
            $('#message1').html('${pass_not_equals}').css('color', 'red');
            bool = false;
        }
        return bool;
    }

    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
    });

</script>

</body>
</html>



