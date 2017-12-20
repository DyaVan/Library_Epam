package com.diachuk.library.commands.clients;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.dao.entities.User;
import com.diachuk.library.dao.implementations.MySql.MySqlUserDAO;
import com.diachuk.library.manage.Message;
import com.diachuk.library.services.SessionManagerService;
import com.diachuk.library.services.UserService;
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
public class CommandChangeProfile implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        SessionManagerService sessionManagerService = new SessionManagerService(request);
        User currentUser = sessionManagerService.getCurrentUser();

        UserService userService = new UserService(MySqlUserDAO.getInstance());
        userService.updateUser(currentUser, name, surname, email, password);

        String jsonResponse = userService.buildJsonResponse().extractJsonString();
        LibraryServlet.sendJsonResponse(jsonResponse, response);
    }
}
