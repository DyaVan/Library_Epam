(function() {

    var reservationSearch = function () {

        var reservationId = $("#reservationId-input").val();
        var url = "Library?command=findReservationVSUser&reservationId=" + reservationId;
        $.ajax({
            url: url,
            type: "get",
            success: function (data) {
                $("#name-place").html("<p>" + data.dataRoot.reservation.user.name + "</p>");

            },
            error: function (data) {
                alert(data.responseText)
            }
        });

    };



    $(document).ready(function() {

        $("#btn-search-reservation").on('click', reservationSearch);

        $('#history .input-group.date').datepicker({
            format: "dd M yyyy",
            calendarWeeks: true,
            autoclose: true,
            todayHighlight: true
        });

        $("#btn-add-book").on('click', function(event) {
          $("#book-editor-section").show(450);
        });

        $("#btn-save-book").on('click', function(event) {
          $("#book-editor-section").hide(450);
        });

        $("#btn-cancel-book").on('click', function(event) {
          $("#book-editor-section").hide(450);
        });

        $("#btn-modify-role").on('click', function(event) {
          $(this).hide(200);
          $("#btn-group-role").show(200);

          $(".role-editing-item").each(function(index, el) {
            $(this).prop('disabled', false);
          });
        });

        $("#btn-save-role").on('click', function(event) {
          $("#btn-group-role").hide(200);
          $("#btn-modify-role").show(200);

          $(".role-editing-item").each(function() {
            $(this).prop('disabled', true);
          });
        });

        $("#btn-cancel-role").on('click', function(event) {
          $("#btn-group-role").hide(200);
          $("#btn-modify-role").show(200);

          $(".role-editing-item").each(function() {
            $(this).prop('disabled', true);
          });
        });

        $("#btn-search-bookLoan").on('click', function () {

        });

    });

})();
