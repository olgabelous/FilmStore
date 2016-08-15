<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.change_language.ru" var="ru" />
<fmt:message bundle="${loc}" key="locale.index.sign_in" var="sign_in" />
<fmt:message bundle="${loc}" key="locale.index.sign_out" var="sign_out" />
<fmt:message bundle="${loc}" key="locale.index.profile" var="profile" />
<fmt:message bundle="${loc}" key="locale.index.forgot_pass" var="forgot_pass" />
<fmt:message bundle="${loc}" key="locale.index.email" var="email" />
<fmt:message bundle="${loc}" key="locale.index.password" var="password" />
<fmt:message bundle="${loc}" key="locale.index.pass" var="pass" />
<fmt:message bundle="${loc}" key="locale.index.remember_me" var="remember_me" />
<fmt:message bundle="${loc}" key="locale.index.enter" var="enter" />
<fmt:message bundle="${loc}" key="locale.index.dont_have_account" var="dont_have_account" />
<fmt:message bundle="${loc}" key="locale.index.sign_up_here" var="sign_up_here" />
<fmt:message bundle="${loc}" key="locale.index.sign_up" var="sign_up" />
<fmt:message bundle="${loc}" key="locale.index.name" var="name" />
<fmt:message bundle="${loc}" key="locale.index.phone" var="phone" />
<fmt:message bundle="${loc}" key="locale.index.repeat_pass" var="repeat_pass" />


<div class="navbar navbar-full navbar-inverse" role="navigation">
    <div class="container">
    <a class="navbar-header navbar-brand" href="/index.jsp">FilmStore</a>
    <ul class="nav navbar-nav pull-right">
        <li class="nav-item active">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <li class="nav-item dropdown-open">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">${sessionScope.user.name}<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="/FilmStore/UserServlet?command=get-user&id=${sessionScope.user.id}">${profile}</a></li>
                            <li><a href="/FilmStore/UserServlet?command=logout">${sign_out}</a></li>
                        </ul>
                    </li>
                    <a class="nav-link" href="#"></a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="#" data-toggle="modal" data-target="#login-modal">${sign_in}<span class="sr-only">(current)</span></a>
                </c:otherwise>
            </c:choose>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="FilmStore/UserServlet?command=change_language&language=ru">RU|</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="FilmStore/UserServlet?command=change_language&language=en">EN</a>
        </li>

        <li class="nav-item dropdown-open">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">${pageContext.response.locale}<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a onclick="show('en')">English</a></li>
                            <li><a onclick="show('ru')">Русский</a></li>
                        </ul>
        </li>
        <script type="text/javascript">
            function show(lang){
                window.location.href = window.location.href.split('?')[0]+"?lang="+lang;
            }
        </script>
    </ul>
    </div>
</div>

<%--login--%>
<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="loginmodal-container">
            <div id="loginbox" style="margin-top:30px;" class="mainbox col-md-8 col-md-offset-2 col-sm-6 col-sm-offset-3">
                <div class="panel panel-info" >
                    <div class="panel-heading">
                        <div class="panel-title">${sign_in}</div>
                        <div style="float:right; font-size: 80%; position: relative; top:-10px"><a href="#">${forgot_pass}</a></div>
                    </div>

                    <div style="padding-top:30px" class="panel-body" >

                        <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>

                        <form id="loginform" class="form-horizontal" role="form" action="/FilmStore/UserServlet" method="post">
                            <input type="hidden" name="command" value="user-authorization" /> <br />
                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"><i class="fa fa-user" aria-hidden="true"></i></span>
                                <input id="login-username" type="text" class="form-control" name="login" value="" placeholder="email">
                            </div>

                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"><i class="fa fa-lock" aria-hidden="true"></i></span>
                                <input id="login-password" type="password" class="form-control" name="password" placeholder="${password}">
                            </div>



                            <div class="input-group">
                                <div class="checkbox">
                                    <label>
                                        <input id="login-remember" type="checkbox" name="remember" value="1">${remember_me}
                                    </label>
                                </div>
                            </div>


                            <div style="margin-top:10px" class="form-group">
                                <!-- Button -->

                                <div class="col-sm-12 controls">
                                    <button id="btn-login" type="submit" class="btn btn-success">${enter}</button>
                                    <a id="btn-fblogin" href="#" class="btn btn-primary">Login with Facebook</a>

                                </div>
                            </div>


                            <div class="form-group">
                                <div class="col-md-12 control">
                                    <div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                        ${dont_have_account}
                                        <a href="#" onClick="$('#loginbox').hide(); $('#signupbox').show()">
                                            ${sign_up_here}
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
        <div id="signupbox" style="display:none; margin-top:30px" class="mainbox col-md-8 col-md-offset-2 col-sm-6 col-sm-offset-3">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <div class="panel-title">${sign_up}</div>
                    <div style="float:right; font-size: 85%; position: relative; top:-10px"><a id="signinlink" href="#" onclick="$('#signupbox').hide(); $('#loginbox').show()">${sign_in}</a></div>
                </div>
                <div class="panel-body" >
                    <form id="signupform" class="form-horizontal" role="form" action="/FilmStore/UserServlet" method="post">
                        <input type="hidden" name="command" value="save-new-user" /> <br />
                        <div id="signupalert" style="display:none" class="alert alert-danger">
                            <p>Error:</p>
                            <span></span>
                        </div>

                        <div class="form-group">
                            <label for="email" class="col-md-3 control-label">Email</label>
                            <div class="col-md-9">
                                <input type="email" class="form-control" name="email" placeholder="Email Address" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="name" class="col-md-3 control-label">${name}</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="name" placeholder="${name}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="phone" class="col-md-3 control-label">${phone}</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="phone" placeholder="${phone}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-md-3 control-label">${pass}</label>
                            <div class="col-md-9">
                                <input type="password" class="form-control" name="password" placeholder="${pass}" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="repeat-pass" class="col-md-3 control-label">${repeat_pass}</label>
                            <div class="col-md-9">
                                <input type="password" class="form-control" name="repeat-pass" placeholder="${repeat_pass}" required >
                            </div>
                        </div>

                        <div class="form-group">
                            <!-- Button -->
                            <div class="col-md-offset-3 col-md-9">
                                <button id="btn-signup" type="submit" class="btn btn-info"><i class="icon-hand-right"></i> &nbsp ${sign_up}</button>
                                <span style="margin-left:8px;">or</span>
                            </div>
                        </div>

                        <div style="border-top: 1px solid #999; padding-top:20px"  class="form-group">

                            <div class="col-md-offset-3 col-md-9">
                                <button id="btn-fbsignup" type="button" class="btn btn-primary"><i class="icon-facebook"></i>   Sign Up with Facebook</button>
                            </div>

                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>
</div>
<%--login end--%>
