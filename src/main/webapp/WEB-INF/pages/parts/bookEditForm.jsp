<%@ taglib prefix="my" uri="/WEB-INF/myTags" %>
<form id="form-book-editor" enctype="multipart/form-data">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
            <my:bookImage/>
            <input id="file-input" type="file"/>
        </div>
        <div class="col-md-8">
            <div class="row book-description-item">
                <div class="col-md-6">
                    <strong>Name:</strong>
                </div>
                <div class="col-md-6">
                    <input id="name-mod-book" type="text" class="form-control" name="bookName"
                           value="">
                </div>
            </div>
            <div class="row book-description-item">
                <div class="col-md-6">
                    <strong>Author</strong>
                </div>
                <div class="col-md-6">
                    <input id="author-mod-book" type="text" class="form-control" name="author"
                           value="">
                </div>
            </div>
            <div class="row book-description-item">
                <div class="col-md-6">
                    <strong>Year</strong>
                </div>
                <div class="col-md-6">
                    <input id="year-mod-book" type="number"
                           class="form-control" name="year" value="">
                </div>
            </div>
            <div class="row book-description-item">
                <div class="col-md-6">
                    <strong>Genre</strong>
                </div>
                <div class="col-md-6">
                    <select id="genre-mod-book" class="form-control" name="genre">
                        <c:forEach var="genreItem" items="${genres}">
                            <option value="${genreItem}">${genreItem}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row book-description-item">
                <div class="col-md-6">
                    <strong>Pages</strong>
                </div>
                <div class="col-md-6">
                    <input id="pages-mod-book" type="number" class="form-control" name="pages"
                           value="">
                </div>
            </div>
            <div class="row book-description-item">
                <div class="col-md-6">
                    <strong>Amount for Home</strong>
                </div>
                <div class="col-md-6">
                    <input id="amountForHome-mod-book" type="number" class="form-control"
                           name="amountForHome" value="">
                </div>
            </div>
            <div class="row book-description-item">
                <div class="col-md-6">
                    <strong>Amount in Reading room:</strong>
                </div>
                <div class="col-md-6">
                    <input id="amountInRRoom-mod-book" type="number" class="form-control"
                           name="amountInRRoom" value="">
                </div>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="col-md-12">
            <h4>Description</h4>
            <textarea id="description-mod-book" name="description"
                      class="form-cotrol book-description-area" rows="8"></textarea>
        </div>
    </div>
    <div class="row">
        <div class="col-md-offset-8 col-md-4">
            <div class="btn-group" role="group" aria-label="...">
                <button id="btn-save-book" type="submit" class="btn btn-default col-md-2">Save
                </button>
                <button id="btn-cancel-book" type="button" class="btn btn-danger col-md-2">
                    Cancel
                </button>
            </div>
        </div>
    </div>
</form>