(function () {

    var reservationSearch = function () {

        var reservationId = $("#reservationId-input").val();
        var url = "Library?command=findReservationVSUser&reservationId=" + reservationId;
        $.ajax({
            url: url,
            type: "get",
            success: function (data) {

                if (!data.successFlag) {
                    $("#issueTable").empty();
                    $("#name-place-issue").empty();
                    $("#surname-place-issue").empty();
                    $("#email-place-issue").empty();
                } else {
                    $("#name-place-issue").html("<p>" + data.dataRoot.reservation.user.name + "</p>");
                    $("#surname-place-issue").html("<p>" + data.dataRoot.reservation.user.surname + "</p>");
                    $("#email-place-issue").html("<p>" + data.dataRoot.reservation.user.email + "</p>");

                    $("#issueTable").empty();

                    var htmlText = "<tr><td>" + "reservation" + "</td>" +
                        "<td>" + data.dataRoot.reservation.id + "</td>" +
                        "<td>" + data.dataRoot.reservation.reservationType + "</td>" +
                        "<td>" + data.dataRoot.reservation.bookId + "</td>" +
                        "<td>" + data.dataRoot.reservation.book.name + "</td>" +
                        "<td>" + data.dataRoot.reservation.book.author + "</td>" +
                        "<td>" + data.dataRoot.reservation.dueDate + "</td></tr>";

                    $("#issueTable").append(htmlText);
                }

            },
            error: function (data) {
                alert(data.responseText)
            }
        });

    };

    var issue = function () {
        var reservationId = $("#reservationId-input").val();
        var reservationType = $("#reservation-type").val();

        var url = "Library?command=issueBook&reservationId=" + reservationId + "&reservationType=" + reservationType;
        $.ajax({
            url: url,
            type: "get",
            success: function (data) {
                $("#issueTable").empty();
                $("#name-place-issue").empty();
                $("#surname-place-issue").empty();
                $("#email-place-issue").empty();
                if (data.successFlag) {
                    if (!data.successFlag) {
                        $("#error-message-text").html("<p>" + data.errorMessage + "</p>")
                        $("#error-message").show();
                    } else {
                        $("#success-message-text").html("<p>" + data.successMessage + "</p>")
                        $("#success-message").show();
                    }
                }
            }
        });
    };

    $(document).ready(function () {
        $("#btn-search-reservation").on('click', reservationSearch);
        $("#btn-issue").on('click', issue);
    });

})();
