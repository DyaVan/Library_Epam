package com.diachuk.library.commands.free;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.dao.entities.Book;
import com.diachuk.library.manage.Message;
import com.diachuk.library.manage.NavigationManager;
import com.diachuk.library.services.SearchBooksService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by VA-N_ on 11.01.2017.
 */
public class CommandSearchBooks implements ICommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String searchLine = request.getParameter("searchLine");
        String genreFilter = request.getParameter("genreFilter");
        Integer offset = Integer.parseInt(request.getParameter("offset"));

        SearchBooksService service = new SearchBooksService(searchLine, genreFilter, offset);

        ArrayList<Book> books = service.search();

        request.setAttribute("books", books);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/parts/booksIterator.jsp");
        dispatcher.forward(request, response);
    }


}
