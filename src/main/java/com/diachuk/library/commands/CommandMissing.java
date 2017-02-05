package com.diachuk.library.commands;

import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.manage.Message;
import com.diachuk.library.manage.NavigationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by VA-N_ on 06.01.2017.
 */
public class CommandMissing implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
//        // TODO: 06.01.2017 implement logging
        request.setAttribute("errorMessage", Message.getInstance().getMessage(Message.NONEXISTENT_PAGE));
        page = NavigationManager.getInstance().getPage(NavigationManager.ERROR_PAGE);
        LibraryServlet.forward(page, request, response);

    }
}
