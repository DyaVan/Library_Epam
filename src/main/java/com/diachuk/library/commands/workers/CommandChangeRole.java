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
 * Created by VA-N_ on 27.01.2017.
 */
public class CommandChangeRole implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Integer roleId = Integer.parseInt(request.getParameter("roleId"));
        String roleName = request.getParameter("roleName");
        Integer roleAccessLevel = Integer.parseInt(request.getParameter("roleAccessLevel"));

        LibraryService service = new LibraryService();
        service.changeRole(roleId, roleName, roleAccessLevel);

        String jsonResponse = service.buildJsonResponse().extractJsonString();

        LibraryServlet.sendJsonResponse(jsonResponse, response);
    }
}
