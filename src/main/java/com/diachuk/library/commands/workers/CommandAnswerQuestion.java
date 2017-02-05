package com.diachuk.library.commands.workers;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.services.InputValidationService;
import com.diachuk.library.services.LibraryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 26.01.2017.
 */
public class CommandAnswerQuestion implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Integer questionId = Integer.parseInt(request.getParameter("questionId"));
        String answer = request.getParameter("answer");

        LibraryService libraryService = new LibraryService();
        libraryService.answerQuestion(questionId, answer);

        String jsonResponse = libraryService.buildJsonResponse().extractJsonString();

        LibraryServlet.sendJsonResponse(jsonResponse, response);
    }
}
