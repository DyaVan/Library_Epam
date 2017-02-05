package com.diachuk.library.commands.free;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.dao.entities.Book;
import com.diachuk.library.manage.NavigationManager;
import com.diachuk.library.services.SearchBooksService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by VA-N_ on 26.01.2017.
 */
public class CommandGetTop10 implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String genreFilter = request.getParameter("genreFilter");

        SearchBooksService service = new SearchBooksService(genreFilter);
        ArrayList<Book> books = service.getTop10();

        request.setAttribute("books", books);

        String page = NavigationManager.getInstance().getPage(NavigationManager.TOP_10_PAGE);
        LibraryServlet.forward(page,request, response);
    }
}
