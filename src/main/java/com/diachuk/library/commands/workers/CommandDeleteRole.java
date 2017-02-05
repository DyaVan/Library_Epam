package com.diachuk.library.commands.workers;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.manage.Message;
import com.diachuk.library.services.InputValidationService;
import com.diachuk.library.services.LibraryService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 27.01.2017.
 */
public class CommandDeleteRole implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Integer roleId = Integer.parseInt(request.getParameter("roleId"));

        LibraryService service = new LibraryService();
        service.deleteRole(roleId);

        String jsonResponse = service.buildJsonResponse().extractJsonString();

        LibraryServlet.sendJsonResponse(jsonResponse, response);
    }
}
