package com.diachuk.library.commands.workers;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.dao.entities.BookLoan;
import com.diachuk.library.dao.entities.Question;
import com.diachuk.library.dao.entities.User;
import com.diachuk.library.dao.implementations.MySql.MySqlUserDAO;
import com.diachuk.library.services.SessionManagerService;
import com.diachuk.library.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by VA-N_ on 25.01.2017.
 */
public class CommandGetQuestions implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Boolean onlyNotAnswered;
        try {
            onlyNotAnswered = Boolean.parseBoolean(request.getParameter("onlyNotAnswered"));
        } catch (Exception e) {
            onlyNotAnswered = false;
        }

        UserService userService = new UserService(MySqlUserDAO.getInstance());
        ArrayList<Question> userQuestions = userService.getAllQuestions(onlyNotAnswered);

        String jsonResponse = userService.buildJsonResponse().extractJsonString();

        LibraryServlet.sendJsonResponse(jsonResponse, response);
    }
}
