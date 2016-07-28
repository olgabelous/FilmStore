<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="resources/css/icon-font.min.css" type='text/css' />
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/font-awesome-4.6.3/css/font-awesome.css" >

</head>

<body>
<fmt:setLocale value="${sessionScope.locale}" /><!-- locale = ru -->
<fmt:setBundle basename="resources.locale" var="loc" /><!-- locale_ru  -->

<fmt:message bundle="${loc}" key="locale.change_language.ru" var="ru" />
<fmt:message bundle="${loc}" key="locale.index.login" var="login" />


<jsp:useBean id="errorMessage" class="java.lang.String" scope="request" />


<nav class="navbar navbar-full navbar-light bg-faded" draggable="true">
    <a class="navbar-brand" href="#">FilmStore</a>
    <ul class="nav navbar-nav pull-right">
        <li class="nav-item active">
            <a class="nav-link" href="#" data-toggle="modal" data-target="#login-modal">${login}<span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#" data-toggle="modal" data-target="#signupbox">Signup</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="FilmStore/UserServlet?command=change_language&language=ru">RU|</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="FilmStore/UserServlet?command=change_language&language=en">EN</a>
        </li>

    </ul>
</nav>
<div class="section">
    <div class="container">
        <br>
        <div id="myCarousel" class="carousel slide" data-ride="carousel">
            <!-- Indicators -->
            <ol class="carousel-indicators">
                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
                <li data-target="#myCarousel" data-slide-to="2"></li>
                <li data-target="#myCarousel" data-slide-to="3"></li>
            </ol>

            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox">

                <div class="item active">
                    <img src="resources/images/banner/Cinderella_3000x600.jpg" alt="Cinderella" width="460" height="345">
                    <div class="carousel-caption">
                        <h3>Chania</h3>
                        <p>The atmosphere in Chania has a touch of Florence and Venice.</p>
                    </div>
                </div>

                <div class="item">
                    <img src="resources/images/banner/HungerGamesMockingjayP1_3000x600.jpg" alt="Chania" width="460" height="345">
                    <div class="carousel-caption">
                        <h3>Chania</h3>
                        <p>The atmosphere in Chania has a touch of Florence and Venice.</p>
                    </div>
                </div>

                <div class="item">
                    <img src="resources/images/banner/Batman-V-Superman_Buy_3000x600.jpg" alt="Flower" width="460" height="345">
                    <div class="carousel-caption">
                        <h3>Flowers</h3>
                        <p>Beatiful flowers in Kolymbari, Crete.</p>
                    </div>
                </div>

                <div class="item">
                    <img src="resources/images/banner/KungFuPanda_3000x600.jpg" alt="Flower" width="460" height="345">
                    <div class="carousel-caption">
                        <h3>Flowers</h3>
                        <p>Beatiful flowers in Kolymbari, Crete.</p>
                    </div>
                </div>

            </div>

            <!-- Left and right controls -->
            <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="btn-group">

                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="#">Action</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">Separated link</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<nav class="navbar navbar-full navbar-light bg-faded" draggable="true">
    <a class="navbar-brand" href="#">Navbar</a>
    <ul class="nav navbar-nav pull-right">
        <li class="nav-item active">
            <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">Features</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">Pricing</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">About</a>
        </li>
    </ul>
</nav>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h1 class="">Heading</h1></div>
        </div>
    </div>
</div>
<div class="p-y-3 section">
    <div class="container">
        <div class="row">
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/food/cover-1.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet,</p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/food/photo-3.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet, </p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/food/photo-2.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit ame</p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/business/photo-3.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet, </p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/realestate/photo-3.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit am</p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/fashion/photo-1.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet,</p>
            </div>http://v4.pingendo.com/assets/photos/food/photo-3.jpg
        </div>
    </div>
</div>

<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-2 col-md-offset-5"><a href="#" class="btn btn-block btn-primary-outline">Button</a></div>
        </div>
    </div>
</div>
<br>
<br>
<br>
<br>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h1 class="" draggable="true">Heading</h1></div>
        </div>
    </div>
</div>
<div class="p-y-3 section">
    <div class="container">
        <div class="row">
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/food/cover-1.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet,</p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/food/photo-3.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet, </p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/food/photo-2.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit ame</p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/business/photo-3.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet, </p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/realestate/photo-3.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit am</p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/fashion/photo-1.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet,</p>
            </div>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-2 col-md-offset-5"><a href="#" class="btn btn-block btn-primary-outline">Button</a></div>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h1 class="">Heading</h1></div>
        </div>
    </div>
</div>
<div class="p-y-3 section">
    <div class="container">
        <div class="row">
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/food/cover-1.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet,</p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/food/photo-3.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet, </p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/food/photo-2.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit ame</p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/business/photo-3.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet, </p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/realestate/photo-3.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit am</p>
            </div>
            <div class="col-md-2">
                <img src="http://v4.pingendo.com/assets/photos/fashion/photo-1.jpg" class="img-fluid m-y">
                <p class="m-y-1">Lorem ipsum dolor sit amet,</p>
            </div>
        </div>
    </div>
</div>

<div class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-2 col-md-offset-5"><a href="#" class="btn btn-block btn-primary-outline">Button</a></div>
        </div>
    </div>
</div>
<br>

<%--login--%>
<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="loginmodal-container">
            <div id="loginbox" style="margin-top:30px;" class="mainbox col-md-8 col-md-offset-2 col-sm-6 col-sm-offset-3">
                <div class="panel panel-info" >
                    <div class="panel-heading">
                        <div class="panel-title">Sign In</div>
                        <div style="float:right; font-size: 80%; position: relative; top:-10px"><a href="#">Forgot password?</a></div>
                    </div>

                    <div style="padding-top:30px" class="panel-body" >

                        <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>

                        <form id="loginform" class="form-horizontal" role="form" action="FilmStore/UserServlet" method="post">
                            <input type="hidden" name="command" value="user-authorization" /> <br />
                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"><i class="fa fa-user" aria-hidden="true"></i></span>
                                <input id="login-username" type="text" class="form-control" name="login" value="" placeholder="email">
                            </div>

                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"><i class="fa fa-lock" aria-hidden="true"></i></span>
                                <input id="login-password" type="password" class="form-control" name="password" placeholder="password">
                            </div>



                            <div class="input-group">
                                <div class="checkbox">
                                    <label>
                                        <input id="login-remember" type="checkbox" name="remember" value="1"> Remember me
                                    </label>
                                </div>
                            </div>


                            <div style="margin-top:10px" class="form-group">
                                <!-- Button -->

                                <div class="col-sm-12 controls">
                                    <button id="btn-login" type="submit" class="btn btn-success">Login </button>
                                    <a id="btn-fblogin" href="#" class="btn btn-primary">Login with Facebook</a>

                                </div>
                            </div>


                            <div class="form-group">
                                <div class="col-md-12 control">
                                    <div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                        Don't have an account!
                                        <a href="#" onClick="$('#loginbox').hide(); $('#signupbox').show()">
                                            Sign Up Here
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
                    <div class="panel-title">Sign Up</div>
                    <div style="float:right; font-size: 85%; position: relative; top:-10px"><a id="signinlink" href="#" onclick="$('#signupbox').hide(); $('#loginbox').show()">Sign In</a></div>
                </div>
                <div class="panel-body" >
                    <form id="signupform" class="form-horizontal" role="form">

                        <div id="signupalert" style="display:none" class="alert alert-danger">
                            <p>Error:</p>
                            <span></span>
                        </div>



                        <div class="form-group">
                            <label for="email" class="col-md-3 control-label">Email</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="email" placeholder="Email Address">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="firstname" class="col-md-3 control-label">First Name</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="firstname" placeholder="First Name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-md-3 control-label">Last Name</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="lastname" placeholder="Last Name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-md-3 control-label">Password</label>
                            <div class="col-md-9">
                                <input type="password" class="form-control" name="passwd" placeholder="Password">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="icode" class="col-md-3 control-label">Invitation Code</label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="icode" placeholder="">
                            </div>
                        </div>

                        <div class="form-group">
                            <!-- Button -->
                            <div class="col-md-offset-3 col-md-9">
                                <button id="btn-signup" type="button" class="btn btn-info"><i class="icon-hand-right"></i> &nbsp Sign Up</button>
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

<script type="text/javascript" src="resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap.min.js"></script>

</body>

</html>
