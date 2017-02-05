package com.diachuk.library.input.validators;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VA-N_ on 03.02.2017.
 */
public class BookLoanIdInputValidator extends InputValidator {
    @Override
    public boolean validateInput(HttpServletRequest request) {
        String bookLoanIdParameter = request.getParameter("bookLoanId");

        inputValidationService.validatebookLoanId(bookLoanIdParameter);

        if (inputValidationService.isInputValid()) {
            return true;
        }
        return false;
    }
}
