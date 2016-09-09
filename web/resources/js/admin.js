$(document).on("click", ".edit-user", function () {
    var id = $(this).data('id');
    var user_name = $(this).data('user-name');
    var user_email = $(this).data('user-email');
    var user_pass = $(this).data('user-pass');
    var user_phone = $(this).data('user-phone');
    var user_photo = $(this).data('user-photo');
    var user_date_reg = $(this).data('user-date-reg');
    var user_role = $(this).data('user-role');
    $(".modal-body #id").val( id );
    $(".modal-body #name").val( user_name );
    $(".modal-body #email").val( user_email );
    $(".modal-body #pwd").val( user_pass );
    $(".modal-body #phone").val( user_phone );
    $(".modal-body #photo").val( user_photo );
    $(".modal-body #date-reg").val( user_date_reg );
    $(".modal-body #role").val( user_role ).attr('selected', 'selected');
});

$(document).on("click", ".edit-genre", function () {
    var id = $(this).data('id');
    var genre_name = $(this).data('genre-name');
    $(".modal-body #id").val( id );
    $(".modal-body #name").val( genre_name );
});

$(document).on("click", ".edit-film-maker", function () {
    var id = $(this).data('id');
    var name = $(this).data('film-maker-name');
    var prof = $(this).data('film-maker-prof');
    $(".modal-body #id").val( id );
    $(".modal-body #name").val( name );
    $(".modal-body #profession").val( prof ).attr('selected', 'selected');
});

$(document).on("click", ".edit-discount", function () {
    var id = $(this).data('id');
    var sumFrom = $(this).data('discount-sum');
    var value = $(this).data('discount-value');
    $(".modal-body #id").val( id );
    $(".modal-body #sum").val( sumFrom );
    $(".modal-body #val").val( value );
});

$(document).on("click", ".edit-country", function () {
    var id = $(this).data('id');
    var country_name = $(this).data('country-name');
    $(".modal-body #id").val( id );
    $(".modal-body #name").val( country_name );
});

$(document).on("click", ".apply", function () {
    var input = $("<input>")
        .attr("type", "hidden")
        .attr("name", "status").val("checked");
    $('#commentForm').append($(input));
    $('#commentForm').submit();
});

$(document).on("click", ".reject", function () {
    var input = $("<input>")
        .attr("type", "hidden")
        .attr("name", "status").val("rejected");
    $('#commentForm').append($(input));
    $('#commentForm').submit();
});

/*$(document).ready(function(){
    $("#department").val("${requestScope.selectedDepartment}").attr('selected', 'selected');
});*/


