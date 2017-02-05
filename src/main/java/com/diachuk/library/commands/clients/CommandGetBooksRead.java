package com.diachuk.library.commands.clients;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.dao.entities.Book;
import com.diachuk.library.dao.entities.User;
import com.diachuk.library.services.LibraryService;
import com.diachuk.library.services.SearchBooksService;
import com.diachuk.library.services.SessionManagerService;
import com.diachuk.library.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by VA-N_ on 25.01.2017.
 */
public class CommandGetBooksRead implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        SessionManagerService sessionManagerService = new SessionManagerService(request);
        User user = sessionManagerService.getCurrentUser();

        String genreFilter = request.getParameter("genreFilter");
        Integer offset = Integer.parseInt(request.getParameter("offset"));

        SearchBooksService service = new SearchBooksService(genreFilter, offset);

        ArrayList<Book> books = service.getBooksRead(user.getId());
        request.setAttribute("books", books);

        String page = "/WEB-INF/pages/parts/booksIterator.jsp";
        LibraryServlet.forward(page, request, response);
    }
}
