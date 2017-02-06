package com.diachuk.library.commands.free;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.dao.entities.User;
import com.diachuk.library.manage.NavigationManager;
import com.diachuk.library.services.InputValidationService;
import com.diachuk.library.services.SessionManagerService;
import com.diachuk.library.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 15.01.2017.
 */
public class CommandLogIn implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String emailParameter = request.getParameter("email");
        String passwordParameter = request.getParameter("password");

        UserService userService = new UserService();
        User currentUser = userService.logIn(emailParameter, passwordParameter);

        if (currentUser != null) {
            SessionManagerService sessionManagerService = new SessionManagerService(request);
            sessionManagerService.startNewSession(currentUser);

            userService.addDataObject("newPage", NavigationManager.getInstance().getPage(NavigationManager.MAIN_PAGE));
        }
        String jsonResponse = userService.buildJsonResponse().extractJsonString();
        LibraryServlet.sendJsonResponse(jsonResponse, response);
    }
}
