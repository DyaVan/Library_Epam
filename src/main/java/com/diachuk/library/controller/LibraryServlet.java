package com.diachuk.library.controller;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.manage.NavigationManager;
import com.diachuk.library.manage.Message;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 04.01.2017.
 */
@WebServlet
public class LibraryServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            ICommand comm = ControllerHelper.getCommand(req);
            comm.execute(req, resp);
        } catch (ServletException e) {
            req.setAttribute("errorMessage", Message.getInstance().getMessage(Message.SERVLET_EXCEPTION));
            //todo: logging
            forward(NavigationManager.getInstance().getPage(NavigationManager.ERROR_PAGE),req,resp);
        } catch (IOException e) {
            req.setAttribute("errorMessage", Message.getInstance().getMessage(Message.IO_EXCEPTION));
            //todo: logging
            forward(NavigationManager.getInstance().getPage(NavigationManager.ERROR_PAGE),req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", Message.getInstance().getMessage(Message.UNDEFINED_ERROR));
            forward(NavigationManager.getInstance().getPage(NavigationManager.ERROR_PAGE),req,resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public static void forward(String page, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(page);
        dispatcher.forward(req, resp);
    }

    public static void sendJsonResponse(String jsonResponse,HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonResponse);
        writer.flush();
        writer.close();
    }
}
