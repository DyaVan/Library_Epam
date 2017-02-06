(function ($) {

    var offset = 0;
    var savedSearchLine;
    var filter;
    var win = $(window);
    var lastSearch;

    var readyFunc = function () {
        $("#searchform").submit(function (event) {
            event.preventDefault();
            event.stopPropagation();

            lastSearch = "";
            savedSearchLine = $("#searchLine").val();
            filter = $("#genreFilter option:selected").val();

            offset = 0;

            var url = "Library?command=searchBooks&searchLine=" + savedSearchLine + "&genreFilter=" + filter + "&offset=" + offset;

            if (url != lastSearch) {
                lastSearch = url;
                $.ajax({
                    url: url,
                    type: "get",
                    success: function (data) {
                        $("#loader-section").html(data);
                        offset++;
                    },
                    error: function (data) {
                        alert(data.responseText)
                    }
                })
            }

        });

        $(window).on("scroll", function (event) {
            event.preventDefault();
            event.stopPropagation();

            // End of the document reached?
            if ($(document).height() - win.height() <= win.scrollTop() + 1) {
                $('#loading').show();

                var url = "Library?command=searchBooks&searchLine=" + savedSearchLine + "&genreFilter=" + filter + "&offset=" + offset;
                if (lastSearch != url) {
                    lastSearch = url;
                    $.ajax({
                        url: url,
                        type: "get",
                        success: function (data) {
                            offset++;
                            $("#loader-section").append(data);
                        },
                        error: function (data) {
                            alert(data.responseText)
                        }
                    });
                }
            }

        });
    };

    $(document).ready(readyFunc);

})(jQuery);