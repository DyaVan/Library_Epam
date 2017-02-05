package com.diachuk.library.commands.workers;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.dao.entities.BookLoan;
import com.diachuk.library.services.BooksService;
import com.diachuk.library.services.InputValidationService;
import com.diachuk.library.services.LibraryService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 25.01.2017.
 */
public class CommandFindBookLoanVSUser implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Integer bookLoanId = Integer.parseInt(request.getParameter("bookLoanId"));

        BooksService booksService = new BooksService();
        booksService.findBookLoanVSUser(bookLoanId);

        String jsonResponse = booksService.buildJsonResponse().extractJsonString();

        LibraryServlet.sendJsonResponse(jsonResponse, response);
    }
}
