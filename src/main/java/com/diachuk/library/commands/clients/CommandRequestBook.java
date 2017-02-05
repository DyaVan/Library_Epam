package com.diachuk.library.commands.clients;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.dao.entities.User;
import com.diachuk.library.manage.Message;
import com.diachuk.library.manage.NavigationManager;
import com.diachuk.library.services.BooksService;
import com.diachuk.library.services.SessionManagerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 17.01.2017.
 */
public class CommandRequestBook implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        SessionManagerService sessionService = new SessionManagerService(request);
        User user = sessionService.getCurrentUser();

        Integer bookId = Integer.parseInt(request.getParameter("bookId"));

        BooksService service = new BooksService();
        service.makeBookRequest(bookId, user);

        String jsonResponse = service.buildJsonResponse().extractJsonString();
        LibraryServlet.sendJsonResponse(jsonResponse, response);
    }
}
