(function () {

    var bookLoanSearch = function () {
        var bookLoanId = $("#bookLoanId-input").val();
        var url = "Library?command=findBookLoanVSUser&bookLoanId=" + bookLoanId;
        $.ajax({
            url: url,
            type: "get",
            success: function (data) {
                var returnTable = $("#returnTable");

                if (!data.successFlag) {
                    returnTable.empty();
                    $("#name-place-return").empty();
                    $("#surname-place-return").empty();
                    $("#email-place-return").empty();
                } else {
                    $("#name-place-return").html("<p>" + data.dataRoot.bookLoan.user.name + "</p>");
                    $("#surname-place-return").html("<p>" + data.dataRoot.bookLoan.user.surname + "</p>");
                    $("#email-place-return").html("<p>" + data.dataRoot.bookLoan.user.email + "</p>");

                    returnTable.empty();

                    var htmlText = "<tr><td>" + "reservation" + "</td>" +
                        "<td>" + data.dataRoot.bookLoan.id + "</td>" +
                        "<td>" + data.dataRoot.bookLoan.type + "</td>" +
                        "<td>" + data.dataRoot.bookLoan.bookId + "</td>" +
                        "<td>" + data.dataRoot.bookLoan.book.name + "</td>" +
                        "<td>" + data.dataRoot.bookLoan.book.author + "</td>" +
                        "<td>" + data.dataRoot.bookLoan.tookDate + "</td>" +
                        "<td>" + data.dataRoot.bookLoan.dueDate + "</td></tr>";

                    returnTable.append(htmlText);
                }

            },
            error: function (data) {
                alert(data.responseText)
            }
        });

    };

    var acceptReturn = function () {
        var bookLoanId = $("#bookLoanId-input").val();
        var url = "Library?command=returnBook&bookLoanId=" + bookLoanId;
        $.ajax({
            url: url,
            type: "get",
            success: function (data) {
                $("#returnTable").empty();
                $("#name-place-return").empty();
                $("#surname-place-return").empty();
                $("#email-place-return").empty();
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
        $("#btn-search-bookLoan").on('click', bookLoanSearch);
        $("#btn-return").on('click', acceptReturn);
    });

})();
