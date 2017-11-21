(function() {

    // function onTabLinkClick() {
    //     var commandName = $(this).attr("data-target");
    //
    //     $.ajax({
    //         url: "Library?command=" + commandName,
    //         type: "get",
    //         success: function (data) {
    //             $("#historyTableBody").empty();
    //
    //             for (var i = 0; i < data.length; i++) {
    //                 var htmlText = "<tr><td>" + data[i].action + "</td>" +
    //                     "<td>" + data[i].bookLoan.type + "</td>" +
    //                     "<td>" + data[i].bookLoan.id + "</td>" +
    //                     "<td>" + data[i].bookLoan.bookId + "</td>" +
    //                     "<td>" + data[i].bookName + "</td>" +
    //                     "<td>" + data[i].bookAuthor + "</td>" +
    //                     "<td>" + data[i].bookLoan.tookDate + "</td>" +
    //                     "<td>" + data[i].bookLoan.dueDate + "</td>" +
    //                     "<td>" + data[i].bookLoan.returnDate + "</td></tr>";
    //
    //                 $("#historyTableBody").append(htmlText);
    //             }
    //         },
    //         error: function (data) {
    //             alert(data.responseText);
    //
    //         }
    //
    //     });
    // }
    //
    // $(document).ready(function() {
    //
    //     $(".tab-link").each(function () {
    //         $(this).on("click",onTabLinkClick)
    //     });
    // });

})();
