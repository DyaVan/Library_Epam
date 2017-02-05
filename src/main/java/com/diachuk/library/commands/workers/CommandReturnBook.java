package com.diachuk.library.commands.workers;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.services.BooksService;
import com.diachuk.library.services.InputValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 17.01.2017.
 */
public class CommandReturnBook implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Integer bookLoanId = Integer.parseInt(request.getParameter("bookLoanId"));

        BooksService booksService = new BooksService();
        booksService.returnBook(bookLoanId);

        String jsonResponse = booksService.buildJsonResponse().extractJsonString();

        LibraryServlet.sendJsonResponse(jsonResponse, response);
    }
}
