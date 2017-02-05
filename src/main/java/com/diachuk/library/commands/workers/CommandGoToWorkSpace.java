package com.diachuk.library.commands.workers;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.manage.LibraryConfig;
import com.diachuk.library.manage.NavigationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 26.01.2017.
 */
public class CommandGoToWorkSpace implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String page = NavigationManager.getInstance().getPage(NavigationManager.WORK_SPACE_PAGE);
        request.setAttribute("genres", LibraryConfig.genresList);
        LibraryServlet.forward(page, request, response);
    }
}
