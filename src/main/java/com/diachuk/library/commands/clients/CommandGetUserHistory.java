package com.diachuk.library.commands.clients;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.dao.entities.BookLoan;
import com.diachuk.library.dao.entities.User;
import com.diachuk.library.dao.implementations.MySql.MySqlUserDAO;
import com.diachuk.library.manage.Message;
import com.diachuk.library.manage.NavigationManager;
import com.diachuk.library.services.SessionManagerService;
import com.diachuk.library.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by VA-N_ on 25.01.2017.
 */
public class CommandGetUserHistory implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        SessionManagerService sessionService = new SessionManagerService(request);
        User currentUser = sessionService.getCurrentUser();

        UserService userService = new UserService(MySqlUserDAO.getInstance());
        userService.getUserHistory(currentUser);

        String jsonResponse = userService.buildJsonResponse().extractJsonString();

        LibraryServlet.sendJsonResponse(jsonResponse, response);
    }
}

