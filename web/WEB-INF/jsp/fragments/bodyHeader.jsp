<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc"/><!-- locale_ru -->

<fmt:message bundle="${loc}" key="locale.navbar.sign_in" var="sign_in"/>
<fmt:message bundle="${loc}" key="locale.navbar.sign_up" var="sign_up"/>
<fmt:message bundle="${loc}" key="locale.navbar.sign_out" var="sign_out"/>
<fmt:message bundle="${loc}" key="locale.navbar.profile" var="profile"/>
<fmt:message bundle="${loc}" key="locale.navbar.comments" var="comments"/>
<fmt:message bundle="${loc}" key="locale.navbar.cart" var="cart"/>
<fmt:message bundle="${loc}" key="locale.navbar.email" var="email"/>
<fmt:message bundle="${loc}" key="locale.navbar.name" var="name"/>
<fmt:message bundle="${loc}" key="locale.navbar.phone" var="phone"/>
<fmt:message bundle="${loc}" key="locale.navbar.password" var="password"/>
<fmt:message bundle="${loc}" key="locale.navbar.pass" var="pass"/>
<fmt:message bundle="${loc}" key="locale.navbar.pass_tooltip" var="pass_tooltip"/>
<fmt:message bundle="${loc}" key="locale.navbar.repeat_pass" var="repeat_pass"/>
<fmt:message bundle="${loc}" key="locale.navbar.enter" var="enter"/>
<fmt:message bundle="${loc}" key="locale.navbar.create_account" var="create_account"/>
<fmt:message bundle="${loc}" key="locale.navbar.email_exist" var="email_exist"/>
<fmt:message bundle="${loc}" key="locale.navbar.pass_not_correct" var="pass_not_correct"/>
<fmt:message bundle="${loc}" key="locale.navbar.pass_not_equals" var="pass_not_equals"/>
<fmt:message bundle="${loc}" key="locale.navbar.name_not_correct" var="name_not_correct"/>

<nav class="navbar navbar-full navbar-inverse ">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.jsp" style="font-family: 'Cookie', cursive; font-size: 3em;">Movie
                Time</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">

            <ul class="nav navbar-nav pull-right">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <li class="nav-item dropdown-open">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"
                               aria-expanded="true">${sessionScope.user.name}<b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="Controller?command=get-user&id=${sessionScope.user.id}">${profile}</a></li>
                                <li><a href="Controller?command=logout">${sign_out}</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="#" data-toggle="modal" data-target="#login-modal"><i
                                    class="fa fa-sign-in" aria-hidden="true"></i> ${sign_in}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#" data-toggle="modal"
                               data-target="#signupbox"><i class="fa fa-user" aria-hidden="true"></i> ${sign_up}
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>

                <c:if test="${not empty sessionScope.user}">
                    <c:choose>
                        <c:when test="${sessionScope.user.role.name()=='ADMIN'}">
                            <li class="nav-item">
                                <a class="nav-link" href="Controller?command=admin-get-comments">${comments}
                                    <c:if test="${sessionScope.commentNum!=0}"><span
                                            class="badge">${sessionScope.commentNum}</span></c:if></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="Controller?command=user-cart"><i class="fa fa-shopping-cart"
                                                                                           aria-hidden="true"></i>${cart}
                                    <c:if test="${sessionScope.orderInCartNum!=0}"><span
                                            class="badge">${sessionScope.orderInCartNum}</span></c:if> </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <li class="nav-item dropdown-open">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"
                       aria-expanded="true">${sessionScope.locale}<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a onclick="show('en')">English</a></li>
                        <li><a onclick="show('ru')">Русский</a></li>
                    </ul>
                </li>
                <script type="text/javascript">
                    function show(lang) {
                        var _url = window.location.href;
                        if (_url.indexOf("lang") !== -1) {
                            _url = _url.substring(0, _url.lastIndexOf("lang") - 1);
                        }
                        _url += (_url.split('?')[1] ? '&' : '?') + "lang=" + lang;
                        window.location.href = _url;
                    }
                </script>
            </ul>
        </div>
    </div>
</nav>

<%--sign in modal--%>
<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="loginmodal-container">
            <div id="loginbox" style="margin-top:30px;"
                 class="mainbox col-md-8 col-md-offset-2">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <div class="panel-title">${sign_in}</div>
                    </div>

                    <div style="padding-top:30px" class="panel-body">

                        <div style="display:none" id="signinalert" class="alert alert-danger col-sm-12">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            ${requestScope.authError}
                        </div>

                        <form id="loginform" class="form-horizontal" role="form" action="Controller" method="post">
                            <input type="hidden" name="command" value="user-authorization"/> <br/>

                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"><i class="fa fa-user" aria-hidden="true"></i></span>
                                <input id="login-username" type="email" class="form-control" name="login" value=""
                                       placeholder="email" required>
                            </div>

                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"><i class="fa fa-lock" aria-hidden="true"></i></span>
                                <input id="login-password" type="password" class="form-control" name="password"
                                       placeholder="${password}" required>
                            </div>

                            <div class="col-sm-12 col-md-10 col-md-offset-1">
                                <p>User: user@mail.ru User111</p>

                                <p>Admin: admin@mail.ru Admin111</p>
                            </div>

                            <div style="margin-top:10px" class="form-group">
                                <!-- Button -->

                                <div class="col-sm-12 col-md-4 col-md-offset-4">
                                    <button id="btn-login" type="submit" class="btn btn-template-main">${enter}
                                        <i class="fa fa-sign-in" aria-hidden="true"></i>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--sign in modal end--%>
<%--sign up modal--%>
<div id="signupbox" style="display:none; margin-top:30px" class="modal fade mainbox col-md-4 col-md-offset-4 ">
    <div class="panel panel-info">
        <div class="panel-heading">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
            <div class="panel-title">${create_account}</div>
        </div>
        <div class="panel-body">

            <form id="signupform" class="form-horizontal" role="form" action="Controller" method="post"
                  onsubmit="return checkForm(this);">
                <input type="hidden" name="command" value="save-new-user"/> <br>

                <div id="signupalert" style="display:none" class="alert alert-danger">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <p>${requestScope.saveNewUserError}</p>
                </div>

                <div class="form-group">
                    <label for="email" class="col-md-3 control-label">Email*</label>

                    <div class="col-md-9">
                        <input type="email" class="form-control" name="email" id="email"
                               placeholder="Email" required><span id='message0'></span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="name" class="col-md-3 control-label">${name}*</label>

                    <div class="col-md-9">
                        <input type="text" class="form-control" name="name" id="name" placeholder="${name}"
                               required><span id='message3'></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="phone" class="col-md-3 control-label">${phone}*</label>

                    <div class="col-md-9">
                        <input type="text" class="form-control" name="phone" id="phone" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-md-3 control-label">${pass}*</label>

                    <div class="col-md-9">
                        <input type="password" class="form-control" name="password" id="password"
                               placeholder="${pass}" data-toggle="tooltip"
                               title="${pass_tooltip}" data-placement="top" autocomplete="off" required>
                        <span id='message1'></span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="confirm_password" class="col-md-3 control-label">${repeat_pass}*</label>

                    <div class="col-md-9">
                        <input type="password" class="form-control" name="confirm_password" id="confirm_password"
                               placeholder="${repeat_pass}" autocomplete="off" required><span id='message2'></span>
                    </div>
                </div>

                <div class="form-group">
                    <!-- Button -->
                    <div class="col-md-4 col-md-offset-4">
                        <button id="btn-signup" type="submit" class="btn btn-info">${sign_up}</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>
<%--sign in modal end--%>

<%--errors--%>
<c:if test="${not empty requestScope.authError}">
    <c:set value="showAuthErrMess" var="authErr"/>
</c:if>
<c:if test="${not empty requestScope.saveNewUserError}">
    <c:set value="showErrMess" var="saveNewUserErr"/>
</c:if>
<%--errors end--%>

<script type="text/javascript">
    $(window).load(function () {
        if ('${authErr}' === 'showAuthErrMess') {
            $('#login-modal').modal('show');
            $('#signinalert').show();
        }

        if ('${saveNewUserErr}' === 'showErrMess') {
            $('#signupbox').modal('show');
            $('#signupalert').show();
        }
    });

    function isEmailExist() {

        var usernameVal = $('#email').val();
        var result;
        $.ajax({
                    url: "Controller",
                    method: 'POST',
                    data: {
                        command: "check-email",
                        email: usernameVal
                    },
                    async: false,
                    success: function (data) {
                        result = (data === '1');
                    }
                }
        );
        return result;
    }

    function checkForm(form) {
        $('#message0').html('');
        $('#message1').html('');
        $('#message2').html('');
        $('#message3').html('');

        var re = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;
        var bool = true;

        if (isEmailExist()) {
            $('#message0').html('${email_exist}').css('color', 'red');
            bool = false;
        }
        if(!re.test(form.password.value)) {
            $('#message1').html('${pass_not_correct}').css('color', 'red');
            bool = false;
        }
        if (form.password.value !== form.confirm_password.value) {
            $('#message2').html('${pass_not_equals}').css('color', 'red');
            bool = false;
        }
        if(form.name.value.length > 50) {
            $('#message3').html('${name_not_correct}').css('color', 'red');
            bool = false;
        }
        return bool;
    }

    jQuery(function ($) {
        $("#phone").mask("+999(99)999-99-99", {placeholder: "+___(__)___-__-__"});
    });
</script>