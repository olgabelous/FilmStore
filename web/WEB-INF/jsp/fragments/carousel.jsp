<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
            <img src="resources/images/banner/KungFuPanda_3000x600.jpg" alt="KungFuPanda">
        </div>

        <div class="item">
            <img src="resources/images/banner/HungerGamesMockingjayP1_3000x600.jpg" alt="HungerGamesMockingjayP1" >
        </div>

        <div class="item">
            <img src="resources/images/banner/Batman-V-Superman_Buy_3000x600.jpg" alt="Batman-V-Superman" >
        </div>

        <div class="item">
            <img src="resources/images/banner/Cinderella_3000x600.jpg" alt="Batman-V-Superman" >
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
