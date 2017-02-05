(function () {

    $(document).ready(function () {

        $('#history .input-group.date').datepicker({
            format: "dd M yyyy",
            calendarWeeks: true,
            autoclose: true,
            todayHighlight: true
        });


        $("#btn-modify-role").on('click', function (event) {
            $(this).hide(200);
            $("#btn-group-role").show(200);

            $(".role-editing-item").each(function (index, el) {
                $(this).prop('disabled', false);
            });
        });

        $("#btn-save-role").on('click', function (event) {
            $("#btn-group-role").hide(200);
            $("#btn-modify-role").show(200);

            $(".role-editing-item").each(function () {
                $(this).prop('disabled', true);
            });
        });

        $("#btn-cancel-role").on('click', function (event) {
            $("#btn-group-role").hide(200);
            $("#btn-modify-role").show(200);

            $(".role-editing-item").each(function () {
                $(this).prop('disabled', true);
            });
        });

        $("#btn-search-bookLoan").on('click', function () {

        });

    });

})();
