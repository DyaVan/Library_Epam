package com.diachuk.library.input.validators;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VA-N_ on 03.02.2017.
 */
public class RoleModifyingInputValidator extends InputValidator {
    @Override
    public boolean validateInput(HttpServletRequest request) {
        String roleIdParameter = request.getParameter("roleId");
        String roleNameParameter = request.getParameter("roleName");
        String roleAccessLevelParameter = request.getParameter("roleAccessLevel");

        inputValidationService.validateRoleId(roleIdParameter);
        inputValidationService.validateRoleName(roleNameParameter);
        inputValidationService.validateAccessLevel(roleAccessLevelParameter);

        if (inputValidationService.isInputValid()) {
            return true;
        }
        return false;
    }
}
