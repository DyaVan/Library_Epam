package com.diachuk.library.commands.clients;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.dao.entities.User;
import com.diachuk.library.services.BooksService;
import com.diachuk.library.services.SessionManagerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 17.01.2017.
 */
public class CommandReserveForHome implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Integer bookId = Integer.parseInt(request.getParameter("bookId"));
        Integer reserveDuration = Integer.parseInt(request.getParameter("reserveDuration"));

        SessionManagerService sessionService = new SessionManagerService(request);
        User user = sessionService.getCurrentUser();

        BooksService service = new BooksService();
        service.makeHomeReservation(bookId, user, reserveDuration);

        String jsonResponse = service.buildJsonResponse().extractJsonString();
        LibraryServlet.sendJsonResponse(jsonResponse, response);
    }
}
