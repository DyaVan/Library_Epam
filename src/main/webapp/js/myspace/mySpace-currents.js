(function() {

    var readyFunc = function () {
        $("#current-tab-btn").on('click',function () {
            var url ="Library?command=getUserCurrents"
            $.ajax({
                url: url,
                type: "get",
                success: function (data) {data.dataRoot

                    if (data.successFlag) {
                        $("#current-requests-table-body").empty();
                        for (var i = 0; i < data.dataRoot.userCurrentRequests.length; i++) {
                            var htmlText = "<tr><td> Request </td>" +
                                "<td>" + data.dataRoot.userCurrentRequests[i].id + "</td>" +
                                "<td>" + data.dataRoot.userCurrentRequests[i].bookId + "</td>" +
                                "<td>" +data.dataRoot.userCurrentRequests[i].book.name + "</td>" +
                                "<td>" + data.dataRoot.userCurrentRequests[i].book.author + "</td>" +
                                "<td>" + data.dataRoot.userCurrentRequests[i].requestDate + "</td>"+
                                "<td><button id-target=\"" + data.dataRoot.userCurrentRequests[i].id + "\" class=\"btn btn-default\" name=\"button\">Cancel</button></td></tr>"
                            $("#current-requests-table-body").append(htmlText);
                        }

                        $("#Reservations-table-body").empty();
                        for (var i = 0; i < data.dataRoot.userCurrentReservations.length; i++) {
                            var htmlText =
                                "<tr><td> Reservation </td>" +
                                "<td>" + data.dataRoot.userCurrentReservations[i].reservationType + "</td>" +
                                "<td>" + data.dataRoot.userCurrentReservations[i].id + "</td>" +
                                "<td>" + data.dataRoot.userCurrentReservations[i].bookId + "</td>" +
                                "<td>" +data.dataRoot.userCurrentReservations[i].book.name + "</td>" +
                                "<td>" + data.dataRoot.userCurrentReservations[i].book.author + "</td>" +
                                "<td>" + data.dataRoot.userCurrentReservations[i].dueDate + "</td>"+
                                "<td><button id-target=\"" + data.dataRoot.userCurrentReservations[i].id + "\" class=\"btn btn-default\" name=\"button\">Cancel</button></td></tr>"
                            $("#Reservations-table-body").append(htmlText);
                        }

                        $("#BookLoans-table-body").empty();
                        for (var i = 0; i < data.dataRoot.userCurrentBookLoans.length; i++) {
                            var htmlText =
                                "<tr><td> Book Loan </td>" +
                                "<td>"+ data.dataRoot.userCurrentBookLoans[i].type + "</td>" +
                                "<td>" + data.dataRoot.userCurrentBookLoans[i].id + "</td>" +
                                "<td>" + data.dataRoot.userCurrentBookLoans[i].bookId + "</td>" +
                                "<td>" +data.dataRoot.userCurrentBookLoans[i].book.name + "</td>" +
                                "<td>" + data.dataRoot.userCurrentBookLoans[i].book.author + "</td>" +
                                "<td>" + data.dataRoot.userCurrentBookLoans[i].tookDate + "</td>"+
                                "<td>" + data.dataRoot.userCurrentBookLoans[i].dueDate + "</td></tr>";
                            $("#BookLoans-table-body").append(htmlText);
                        }

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
