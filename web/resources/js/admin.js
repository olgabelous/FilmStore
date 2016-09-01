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
    //$(".modal-body #role").val( user_role );
    // As pointed out in comments,
    // it is superfluous to have to manually call the modal.
    // $('#addBookDialog').modal('show');
});


