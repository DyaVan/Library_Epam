package com.diachuk.library.input.validators;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by VA-N_ on 03.02.2017.
 */
public class ReservationIdInputValidator extends InputValidator {
    @Override
    public boolean validateInput(HttpServletRequest request) {
        String reservationIdParameter = request.getParameter("reservationId");

        inputValidationService.validateReservationId(reservationIdParameter);

        if (inputValidationService.isInputValid()) {
            return true;
        }
        return false;
    }
}
