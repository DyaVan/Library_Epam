package com.diachuk.library.commands.clients;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.dao.entities.User;
import com.diachuk.library.manage.Message;
import com.diachuk.library.services.LibraryService;
import com.diachuk.library.services.SessionManagerService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 25.01.2017.
 */
public class CommandAskQuestion implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String subject = request.getParameter("subject");
        String questionText = request.getParameter("questionText");

        SessionManagerService sessionManagerService = new SessionManagerService(request);
        User currentUser = sessionManagerService.getCurrentUser();

        LibraryService libraryService = new LibraryService();
        libraryService.createNewQuestion(subject, questionText, currentUser.getId());

        String jsonResponse = libraryService.buildJsonResponse().extractJsonString();
        LibraryServlet.sendJsonResponse(jsonResponse, response);
    }
}
