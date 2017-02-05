package com.diachuk.library.commands.free;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.manage.NavigationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 25.01.2017.
 */
public class CommandGoToMySpace implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String page = NavigationManager.getInstance().getPage(NavigationManager.MY_SPACE_PAGE);
        LibraryServlet.forward(page, request, response);
    }
}
