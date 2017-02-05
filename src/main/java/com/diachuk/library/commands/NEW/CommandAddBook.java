package com.diachuk.library.commands.NEW;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.dao.entities.Book;
import com.diachuk.library.manage.Message;
import com.diachuk.library.manage.NavigationManager;
import com.diachuk.library.services.BooksService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by VA-N_ on 04.02.2017.
 */
public class CommandAddBook implements ICommand {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String filePath = request.getServletContext().getInitParameter("file-upload");
        HashMap<String, String> parameters = (HashMap<String, String>) request.getAttribute("parametersMap");
        FileItem imageFile = (FileItem) request.getAttribute("imageFile");

        String bookName = parameters.get("bookName");
        String author = parameters.get("bookAuthor");
        Integer year = Integer.parseInt(parameters.get("year"));
        String genre = parameters.get("genre");
        String description = parameters.get("description");
        Integer pages = Integer.parseInt(parameters.get("pages"));
        Integer amountForHome = Integer.parseInt(parameters.get("amountForHome"));
        Integer amountInRRoom = Integer.parseInt(parameters.get("amountInRRoom"));

        BooksService booksService = new BooksService();
        if (booksService.addBook(bookName, author, year, genre, description, pages, amountForHome, amountInRRoom)
                && imageFile != null) {
            Integer bookId = booksService.findBookIdByNameAuthorYear(bookName, author, year);
            if (bookId > 0) {
                File file = new File(filePath + bookId.toString() + ".jpg");
                try {
                    imageFile.write(file);
                } catch (Exception e) {
                    booksService.appendNotificationMessage(Message.getInstance().getMessage(Message.IMAGE_UPLOAD_FAILED));
                }
            }
        }

        String jsonResponse = booksService.buildJsonResponse().extractJsonString();
        LibraryServlet.sendJsonResponse(jsonResponse, response);
    }
}
