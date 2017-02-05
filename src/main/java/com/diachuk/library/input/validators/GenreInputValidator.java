package com.diachuk.library.input.validators;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VA-N_ on 03.02.2017.
 */
public class GenreInputValidator extends InputValidator {
    @Override
    public boolean validateInput(HttpServletRequest request) {
        String genreFilterParameter = request.getParameter("genreFilter");

        inputValidationService.validateGenre(genreFilterParameter);

        if (inputValidationService.isInputValid()) {
            return true;
        }
        return false;
    }
}
