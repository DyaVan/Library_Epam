(function() {

    var readyFunc =  function () {
        //show chosen image
        $("#file-input").on('change',function (event){
            if (this.files && this.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#book-img')
                        .attr('src', e.target.result)
                        .width(210)
                        .height(280);
                };

                reader.readAsDataURL(this.files[0]);
            }
        });

        //submit add new book
        $("#form-book-editor").submit( function (event) {
            event.preventDefault();
            event.stopPropagation();

            var formData = new FormData();

            formData.append("bookName",$("#name-mod-book").val());
            formData.append("bookAuthor",$("#author-mod-book").val());
            formData.append("year",$("#year-mod-book").val());
            formData.append("genre",$("#genre-mod-book").val());
            formData.append("pages",$("#pages-mod-book").val());
            formData.append("amountForHome",$("#amountForHome-mod-book").val());
            formData.append("amountInRRoom",$("#amountInRRoom-mod-book").val());
            formData.append("description",$("#description-mod-book").val());

            formData.append("imagefile", $("#file-input")[0].files[0]);

            var url = "Library?command=addBook";

            $.ajax({
                url: url,
                data: formData,
                cache: false,
                contentType: false,
                processData: false,
                type: 'POST',
                success: function(data){
                    if(!data.successFlag){
                        $("#error-message-text").html("<p>"+ data.errorMessage+"</p>")
                        $("#error-message").show();
                    }else {
                        $("#success-message-text").html("<p>"+ data.successMessage+"</p>")
                        $("#success-message").show();
                        $("#book-editor-section").hide(450);

                        $("#name-mod-book").empty();
                        $("#author-mod-book").empty();
                        $("#year-mod-book").empty();
                        $("#genre-mod-book").empty();
                        $("#pages-mod-book").empty();
                        $("#amountForHome-mod-book").empty();
                        $("#amountInRRoom-mod-book").empty();
                        $("#description-mod-book").empty();
                        $("#file-input").empty();
                    }
                },
                error:function (data) {
                    alert(data);
                }
            });


            // $.post(url, formData)
            //     .done(function (data, status, xhr) {
            //         if(!data.successFlag){
            //             $("#error-message-text").html("<p>"+ data.errorMessage+"</p>")
            //             $("#error-message").show();
            //         }else {
            //             $("#success-message-text").html("<p>"+ data.successMessage+"</p>")
            //             $("#success-message").show();
            //             $("#book-editor-section").hide(450);
            //             bookName.empty();
            //             author.empty();
            //             year.empty();
            //             genre.empty();
            //             pages.empty();
            //             amountForHome.empty();
            //             amountInRRoom.empty();
            //             description.empty();
            //             fileInputElement.empty();
            //         }
            //     });

        });

        //cancel book add/edit
        $("#btn-cancel-book").on('click',  function(event) {
            $("#book-editor-section").hide(450);
        });

        //show book add panel
        $("#btn-add-book").on('click', function(event) {
            $("#book-editor-section").show(450);
        });

        $("#books-tabList-item").on('click', function(event) {

        });

    };

    $(document).ready(readyFunc);

})();
