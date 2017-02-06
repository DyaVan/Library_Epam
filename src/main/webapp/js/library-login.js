(function ($) {

    var offset = 0;
    var savedSearchLine;
    var filter;
    var win = $(window);

    var readyFunc = function () {
        $("#logIn-form").submit(function (event) {
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


    // $.ajax({
    //     type: "post",
    //     url: url,
    //     data: widgetForm.serialize(),
    //     success: function(response, status, xhr){
    //         var ct = xhr.getResponseHeader("content-type") || "";
    //
    //         if (ct.indexOf(‘html’) > -1) {
    //             widgetForm.replaceWith(response);
    //         }
    //
    //         if (ct.indexOf(‘json’) > -1) {
    //             // handle json here
    //         }
    //     }
    // });


    // var readyFunc2 = function () {
    //     $("#searchform").submit(function (event) {
    //         event.preventDefault();
    //
    //         savedSearchLine = $("#searchLine").val();
    //         filter = $("#genreFilter option:selected").val();
    //
    //         offset = 0;
    //
    //         var url = "Library?command=searchBooks&searchLine=" + savedSearchLine + "&genreFilter=" + filter + "&offset=" + offset;
    //
    //         $.ajax({
    //             url: url,
    //             type: "get",
    //             success: function (data) {
    //                 $("#loader-section").html(data);
    //                 offset++;
    //             },
    //             error: function (data) {
    //                 alert(data.responseText)
    //             }
    //         })
    //     });
    //
    // };

    $(document).ready(readyFunc);

})(jQuery);