(function ($) {


    var readyFunc = function () {
        $("#register-form").submit(function (event) {
            event.preventDefault();
            event.stopPropagation();

            command = $("#btn-logIn").attr("data-command");
            var url = "Library?command=" + command;


            $.post(url, $("#logIn-form").serialize())
                .done(function (data, status, xhr) {
                    var ct = xhr.getResponseHeader('Content-Type');
                    if (ct.indexOf("json") > -1) {
                        if(!data.successFlag){
                            $("#error-message-text").html("<p>"+ data.errorMessage+"</p>")
                            $("#error-message").show();
                        }else {
                            window.location.replace("/index.jsp");
                        }

                    }else {
                        window.location.replace("http://localhost:8080/index.jsp");
                        // var doc = document.documentElement;
                        // doc.innerHTML = data;
                    }
                });


        });

    };


    $(document).ready(readyFunc);

})(jQuery);