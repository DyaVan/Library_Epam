package com.diachuk.library.input.validators;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VA-N_ on 03.02.2017.
 */
public class QuestionIdInputValidator extends InputValidator {
    @Override
    public boolean validateInput(HttpServletRequest request) {
        String questionIdParameter = request.getParameter("questionId");

        inputValidationService.validateQuestionId(questionIdParameter);

        if (inputValidationService.isInputValid()) {
            return true;
        }
        return false;
    }
}
