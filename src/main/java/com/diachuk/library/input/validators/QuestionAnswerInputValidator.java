package com.diachuk.library.input.validators;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VA-N_ on 03.02.2017.
 */
public class QuestionAnswerInputValidator extends InputValidator {
    @Override
    public boolean validateInput(HttpServletRequest request) {
        String questionIdParameter = request.getParameter("questionId");
        String answerParameter = request.getParameter("answer");

        inputValidationService.validateQuestionAnswer(answerParameter);
        inputValidationService.validateQuestionId(questionIdParameter);

        if (inputValidationService.isInputValid()) {
            return true;
        }
        return false;
    }
}
