package com.diachuk.library.input.validators;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VA-N_ on 03.02.2017.
 */
public class BookIdInputValidator extends InputValidator {
    @Override
    public boolean validateInput(HttpServletRequest request) {
        String bookIdParameter = request.getParameter("bookId");

        inputValidationService.validateBookId(bookIdParameter);

        if (inputValidationService.isInputValid()) {
            return true;
        }
        return false;
    }
}
