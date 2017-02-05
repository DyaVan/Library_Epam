package com.diachuk.library.commands.clients;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.manage.NavigationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 01.02.2017.
 */
public class CommandLogOut implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession(false);
        session.invalidate();

        LibraryServlet.forward(NavigationManager.getInstance().getPage(NavigationManager.MAIN_PAGE),request, response);
    }
}
