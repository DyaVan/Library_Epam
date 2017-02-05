package com.diachuk.library.input.validators;

import com.diachuk.library.services.InputValidationService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VA-N_ on 03.02.2017.
 */
public abstract class InputValidator {

    protected InputValidationService inputValidationService = new InputValidationService();

    public abstract boolean validateInput(HttpServletRequest request);

    public String getJsonResponseString() {
        return inputValidationService.buildJsonResponse().extractJsonString();
    }


}
