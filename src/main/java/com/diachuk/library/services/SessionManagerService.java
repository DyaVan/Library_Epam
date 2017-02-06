package com.diachuk.library.services;

import com.diachuk.library.dao.entities.User;
import com.diachuk.library.dao.implementations.MySql.MySqlRoleDAO;
import com.diachuk.library.manage.Message;
import com.diachuk.library.manage.NavigationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 17.01.2017.
 */
public class SessionManagerService {

    public static final int FREE_ACCESS = 0;
    public static final int NO_HOME_LOANS_CLIENT_ACCESS = 1;
    public static final int NO_QUESTION_CLIENT_ACCESS = 2;
    public static final int CLIENT_ACCESS = 3;
    public static final int NEWS_WRITING_ACCESS = 4;
    public static final int QUESTION_ANSWERING_ACCESS = 5;
    public static final int LIBRARIAN_ACCESS = 6;
    public static final int ADMIN_ACCESS = 10;

    private HttpServletRequest request;
    private HttpSession session;

    public SessionManagerService(HttpServletRequest request) {
        this.request = request;
        session = request.getSession(false);
    }

    public boolean checkAccessRights(Integer accessLevel) {
        if (accessLevel < 1) {
            return true;
        }

        if (session == null || session.getAttribute("currentUser") == null) {
            request.setAttribute("errorMessage", Message.getInstance().getMessage(Message.NOT_AUTHORIZED_USER));
            return false;
        }

        if (((Integer) (session.getAttribute("accessLevel") )) < accessLevel) {
            request.setAttribute("errorMessage", Message.getInstance().getMessage(Message.ACCESS_DENIED));
            return false;
        }

        return true;
    }

    public User getCurrentUser() {
        return (User) session.getAttribute("currentUser");
    }

    public void startNewSession(User user) throws SQLException {
        session = request.getSession();
        session.setAttribute("currentUser", user);
        Integer accessLevel = MySqlRoleDAO.getInstance().getAccessLevel(user.getRoleId());
        session.setAttribute("accessLevel", accessLevel);
    }

}
