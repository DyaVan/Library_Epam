package com.diachuk.library.filters;

import com.diachuk.library.commands.CommandWrapper;
import com.diachuk.library.controller.ControllerHelper;
import com.diachuk.library.dao.entities.Request;
import com.diachuk.library.manage.NavigationManager;
import com.diachuk.library.services.SessionManagerService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by VA-N_ on 19.01.2017.
 */
public class SessionFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        CommandWrapper commandWrapper = ControllerHelper.getCommandWrapper(httpRequest);
        SessionManagerService sessionManagerService = new SessionManagerService(httpRequest);
        if (commandWrapper != null) {
            if (sessionManagerService.checkAccessRights(commandWrapper.getAccessLevel())) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                RequestDispatcher dispatcher = servletRequest.getServletContext().getRequestDispatcher(
                        NavigationManager.getInstance().getPage(NavigationManager.LOGIN_PAGE));
                dispatcher.forward(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
