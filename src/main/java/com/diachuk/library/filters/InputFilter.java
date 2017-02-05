package com.diachuk.library.filters;

import com.diachuk.library.input.validators.InputValidator;
import com.diachuk.library.commands.CommandWrapper;
import com.diachuk.library.controller.ControllerHelper;
import com.diachuk.library.controller.LibraryServlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by VA-N_ on 03.02.2017.
 */
public class InputFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        CommandWrapper commandWrapper = ControllerHelper.getCommandWrapper(httpRequest);

        try {
            Class inputValidatorTemplate = commandWrapper.getInputValidator();
            if (inputValidatorTemplate == null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                InputValidator inputValidator = (InputValidator) commandWrapper.getInputValidator().newInstance();
                if (inputValidator.validateInput(httpRequest)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    String jsonResponse = inputValidator.getJsonResponseString();
                    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                    LibraryServlet.sendJsonResponse(jsonResponse, httpServletResponse);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            // TODO: 03.02.2017 logging
        } catch (IllegalAccessException e) {
            // TODO: 03.02.2017 logging
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
