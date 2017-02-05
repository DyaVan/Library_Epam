package com.diachuk.library.input.validators;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VA-N_ on 03.02.2017.
 */
public class HomeReserveInputValidator extends InputValidator {
    @Override
    public boolean validateInput(HttpServletRequest request) {
        String bookIdParameter = request.getParameter("bookId");
        String reserveDurationParameter = request.getParameter("reserveDuration");

        inputValidationService.validateBookId(bookIdParameter);
        inputValidationService.validateHomeReserveDuration(reserveDurationParameter);

        if (inputValidationService.isInputValid()) {
            return true;
        }
        return false;
    }
}
