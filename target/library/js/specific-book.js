(function ($) {

    var sendFunc =  function (url) {

        $.get(url)
            .done(function (data, status, xhr) {
                var ct = xhr.getResponseHeader('Content-Type');
                if (ct.indexOf("json") > -1) {
                    if(!data.successFlag){
                        $("#error-message-text").html("<p>"+ data.errorMessage+"</p>")
                        $("#error-message").show();
                    }else {
                        $("#success-message-text").html("<p>"+ data.errorMessage+"</p>")
                        $("#success-message").show();
                    }
                }
            });


    };

    var readyFunc = function () {
        $("#btn-homeReserve").on('click',function () {
            var url = $("#btn-homeReserve").attr("data-url") + "&bookId=" + $("#hidden-bookId").val() +
                "&reserveDuration=" + $("#home-reserve-diration").val();
            console.log(url);
            sendFunc(url);
        });

        // $("#btn-setForFuture").on('click',function () {
        //     var url = $("#btn-setForFuture").attr("data-url") + "&bookId=" + $("#hidden-bookId").val();
        //     console.log(url);
        //     sendFunc(url);
        // });

        $("#btn-request").on('click',function () {
            var url = $("#btn-request").attr("data-url") + "&bookId=" + $("#hidden-bookId").val();
            console.log(url);
            sendFunc(url);
        });

        $("#btn-rRoomReserve").on('click',function () {
            var url = $("#btn-rRoomReserve").attr("data-url") + "&bookId=" + $("#hidden-bookId").val();
            console.log(url);
            sendFunc(url);
        });

        $("#btn-modify-book").click(function(){
            $("#book-edit-modal").modal();
        });
    };


    $(document).ready(readyFunc);

})(jQuery);