package com.diachuk.library.input.validators;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VA-N_ on 03.02.2017.
 */
public class RegistrationInputValidator extends InputValidator {
    @Override
    public boolean validateInput(HttpServletRequest request) {
        String nameParameter = request.getParameter("name");
        String surnameParameter = request.getParameter("surname");
        String emailParameter = request.getParameter("email");
        String passwordParameter = request.getParameter("password");

        inputValidationService.validateName(nameParameter);
        inputValidationService.validateSurname(surnameParameter);
        inputValidationService.validateNewEmail(emailParameter);
        inputValidationService.validateNewPassword(passwordParameter);

        if (inputValidationService.isInputValid()) {
            return true;
        }
        return false;
    }
}
