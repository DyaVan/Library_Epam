package com.diachuk.library.input.validators;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VA-N_ on 03.02.2017.
 */
public class QuestionAskInputValidator extends InputValidator {
    @Override
    public boolean validateInput(HttpServletRequest request) {
        String subjectParameter = request.getParameter("subject");
        String questionTextParameter = request.getParameter("questionText");

        inputValidationService.validateQuestionSubject(subjectParameter);
        inputValidationService.validateQuestionText(questionTextParameter);

        if (inputValidationService.isInputValid()) {
            return true;
        }
        return false;
    }
}
