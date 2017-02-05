package com.diachuk.library.input.validators;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VA-N_ on 03.02.2017.
 */
public class RoleIdInputValidator extends InputValidator {
    @Override
    public boolean validateInput(HttpServletRequest request) {
        String roleIdParameter = request.getParameter("roleId");

        inputValidationService.validateRoleId(roleIdParameter);

        if (inputValidationService.isInputValid()) {
            return true;
        }
        return false;
    }
}
