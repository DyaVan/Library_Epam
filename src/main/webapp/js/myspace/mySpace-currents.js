(function() {

    var readyFunc = function () {
        $("#current-tab").on('click',function () {
            var url ="Library?command=getUserCurrents"
            $.ajax({
                url: url,
                type: "get",
                success: function (data) {

                    if (!data.successFlag) {

                    } else {

                    }

                },
                error: function (data) {
                    alert(data.responseText)
                }
            });


        });

    };

    $(document).ready(readyFunc);

})();
