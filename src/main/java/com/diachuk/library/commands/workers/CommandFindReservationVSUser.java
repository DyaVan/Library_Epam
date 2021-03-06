package com.diachuk.library.commands.workers;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.services.BooksService;
import com.diachuk.library.services.InputValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 25.01.2017.
 */
public class CommandFindReservationVSUser implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Integer reservationId = Integer.parseInt(request.getParameter("reservationId"));
        String reservationType = request.getParameter("reservationType");

        BooksService booksService = new BooksService();
        booksService.findReservationVSUser(reservationId,reservationType);

        String jsonResponse = booksService.buildJsonResponse().extractJsonString();

        LibraryServlet.sendJsonResponse(jsonResponse, response);
    }
}
