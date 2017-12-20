package com.diachuk.library.commands.free;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.dao.entities.User;
import com.diachuk.library.dao.implementations.MySql.MySqlUserDAO;
import com.diachuk.library.manage.Message;
import com.diachuk.library.manage.NavigationManager;
import com.diachuk.library.services.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 15.01.2017.
 */
public class CommandRegistration implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String nameParameter = request.getParameter("name");
        String surnameParameter = request.getParameter("surname");
        String emailParameter = request.getParameter("email");
        String passwordParameter = request.getParameter("password");

        UserService userService = new UserService(MySqlUserDAO.getInstance());

        if (userService.addUser(nameParameter, surnameParameter, emailParameter, passwordParameter)) {
            startNewSession(request, userService, emailParameter);
            response.sendRedirect(NavigationManager.getInstance().getPage(NavigationManager.MAIN_PAGE));
        }
        else {
            String jsonResponse = userService.buildJsonResponse().extractJsonString();
            LibraryServlet.sendJsonResponse(jsonResponse, response);
        }
    }

    private void startNewSession(HttpServletRequest request,UserService userService,String userEmail) throws SQLException {
        User newUser = userService.getUserByEmail(userEmail);
        SessionManagerService sessionManagerService = new SessionManagerService(request);
        sessionManagerService.startNewSession(newUser);
    }
}
