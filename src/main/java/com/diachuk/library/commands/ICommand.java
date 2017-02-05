package com.diachuk.library.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 04.01.2017.
 */
public interface ICommand {

    void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException;

}
