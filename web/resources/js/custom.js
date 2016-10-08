// Script to open and close sidenav
function sidenav_open() {
    document.getElementById("mySidenav").style.display = "block";
    document.getElementById("myOverlay").style.display = "block";
}

function sidenav_close() {
    document.getElementById("mySidenav").style.display = "none";
    document.getElementById("myOverlay").style.display = "none";
}

// Script to add tooltip
$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();
});
