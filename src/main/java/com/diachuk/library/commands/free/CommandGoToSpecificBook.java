package com.diachuk.library.commands.free;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.dao.entities.Book;
import com.diachuk.library.manage.NavigationManager;
import com.diachuk.library.services.BooksService;
import com.diachuk.library.services.InputValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 16.01.2017.
 */
public class CommandGoToSpecificBook implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Integer bookId = Integer.parseInt(request.getParameter("bookId"));

        BooksService booksService = new BooksService();
        Book book = booksService.findBookById(bookId);

        if (book != null) {
            request.setAttribute("specificBook", book);
            String page = NavigationManager.getInstance().getPage(NavigationManager.SPECIFIC_BOOK_PAGE);
            LibraryServlet.forward(page, request, response);
        }else {
            LibraryServlet.sendJsonResponse(booksService.buildJsonResponse().extractJsonString(),response);
        }
    }
}
