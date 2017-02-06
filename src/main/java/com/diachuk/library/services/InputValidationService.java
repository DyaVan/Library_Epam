package com.diachuk.library.services;

import com.diachuk.library.manage.LibraryConfig;
import com.diachuk.library.manage.Message;
import com.diachuk.library.services.json.JsonResponseBuilder;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by VA-N_ on 15.01.2017.
 */
public class InputValidationService extends JsonResponseBuilder {

    private boolean inputValid = true;

    public boolean validateNewPassword(String passwordParameter) {
        if (validateNotEmpty(passwordParameter) && passwordParameter.length() >= 7) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.WEAK_PASSWORD);
    }

    public boolean validateNewEmail(String emailParameter) {
        EmailValidator validator = EmailValidator.getInstance();
        if (validateNotEmpty(emailParameter) && validator.isValid(emailParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.EMAIL_NOT_VALID);
    }

    public boolean validateSurname(String surnameParameter) {
        if (validateCharsLine(surnameParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_SURNAME);
    }

    public boolean validateName(String nameParameter) {
        if (validateCharsLine(nameParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_NAME);
    }

    public boolean isInputValid() {
        return inputValid;
    }

    public boolean validateQuestionId(String questionIdParameter) {
        if (validateNotEmpty(questionIdParameter) && validateDigit(questionIdParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_QUESTION_ID);
    }

    public boolean validateCharsLine(String charsLineParameter) {
        Pattern pattern = Pattern.compile("[^a-zA-Zа-яА-Я.,\\s]");
        Matcher matcher = pattern.matcher(charsLineParameter);
        if (!matcher.find()) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.ONLY_CHARS);
    }

    public boolean validateDigit(String questionIdParameter) {
        Pattern pattern = Pattern.compile("\\D"); //A non digit [^0-9]
        Matcher matcher = pattern.matcher(questionIdParameter);
        if (!matcher.find()) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.NOT_A_DIGIT);
    }

    public boolean validateQuestionAnswer(String answerParameter) {
        if (validateNotEmpty(answerParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_QUESTION_ANSWER);
    }

    public boolean validateRoleId(String roleIdParameter) {
        if (validateNotEmpty(roleIdParameter) && validateDigit(roleIdParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_ROLE_ID);
    }

    public boolean validateRoleName(String roleNameParameter) {
        if (validateNotEmpty(roleNameParameter) && validateCharsLine(roleNameParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_ROLE_NAME);
    }

    public boolean validateAccessLevel(String roleAccessLevelParameter) {
        if (validateNotEmpty(roleAccessLevelParameter) && validateDigit(roleAccessLevelParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_ACCESS_LEVEL);
    }

    public boolean validateUserId(String userIdParameter) {
        if (validateNotEmpty(userIdParameter) && validateDigit(userIdParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_USER_ID);
    }

    public boolean validatebookLoanId(String bookLoanIdParameter) {
        if (validateNotEmpty(bookLoanIdParameter) && validateDigit(bookLoanIdParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_BOOK_LOAN_ID);
    }

    public boolean validateReservationId(String reservationIdParameter) {
        if (validateNotEmpty(reservationIdParameter) && validateDigit(reservationIdParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_RESERVATION_ID);
    }

    public boolean validateBookId(String bookIdParameter) {
        if (validateNotEmpty(bookIdParameter) && validateDigit(bookIdParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_BOOK_ID);
    }

    public boolean validateGenre(String genreFilterParameter) {
        if (genreFilterParameter != null && validateCharsLine(genreFilterParameter) &&
                ( genreFilterParameter.isEmpty() || LibraryConfig.genresList.contains(genreFilterParameter))) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_GENRE);
    }

    public boolean validateNotEmpty(String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.EMPTY_INPUT);
    }

    public boolean validateOffset(String offsetParameter) {
        if (validateNotEmpty(offsetParameter) && validateDigit(offsetParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_OFFSET);
    }

    public boolean validateQuestionSubject(String subjectParameter) {
        if (validateNotEmpty(subjectParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_SUBJECT);
    }

    public boolean validateQuestionText(String questionTextParameter) {
        if (validateNotEmpty(questionTextParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_QUESTION_TEXT);
    }

    public boolean validateHomeReserveDuration(String reserveDurationParameter) {
        if (validateNotEmpty(reserveDurationParameter) && validateDigit(reserveDurationParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_RESERVE_DURATION);
    }

    /**
     * Sets success flag and {@code this.inputValid} to {@code false}, appends error message and returns false;
     * Made for refactoring reasons.
     *
     * @param messageBundleName Message identificator in the Message-resource bundle;
     * @return Always returns {@code false}
     */
    public boolean inputInvalidVSErrorMessage(String messageBundleName) {
        inputValid = false;
        appendErrorMessage(Message.getInstance().getMessage(messageBundleName));
        return false;
    }

    public boolean validateBookName(String bookNameParameter) {
        if (validateNotEmpty(bookNameParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_BOOK_NAME);
    }

    public boolean validateBookAuthor(String authorParameter) {
        if (validateNotEmpty(authorParameter) && validateCharsLine(authorParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_BOOK_AUTHOR);
    }

    public boolean validateBookYear(String yearParameter) {
        if (validateNotEmpty(yearParameter) && yearParameter.length() <= 4 && validateDigit(yearParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_BOOK_YEAR);
    }

    public boolean validateBookDescription(String descriptionParameter) {
        return (descriptionParameter!=null) ? true : inputInvalidVSErrorMessage(Message.NULL_DESCRIPTION);
    }

    public boolean validatePages(String pagesParameter) {
        if (validateNotEmpty(pagesParameter) && validateDigit(pagesParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_PAGES);
    }

    public boolean validateBookAmount(String amountForHomeParameter) {
        if (validateNotEmpty(amountForHomeParameter) && validateDigit(amountForHomeParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_AMOUNT);
    }

    public void validateBookEditParameters(HashMap<String, String> parameters) {
        validateBookId(parameters.get("bookId"));
        validateBookName(parameters.get("bookName"));
        validateBookAuthor(parameters.get("bookAuthor"));
        validateBookYear(parameters.get("year"));
        validateNewBookGenre(parameters.get("genre"));
        validateBookDescription(parameters.get("description"));
        validatePages(parameters.get("pages"));
        validateBookAmount(parameters.get("amountForHome"));
        validateBookAmount(parameters.get("amountInRRoom"));
    }

    private boolean validateNewBookGenre(String genreFilterParameter) {
        if (genreFilterParameter != null && !genreFilterParameter.isEmpty() && validateCharsLine(genreFilterParameter) &&
                LibraryConfig.genresList.contains(genreFilterParameter)) {
            return true;
        }
        return inputInvalidVSErrorMessage(Message.INVALID_GENRE);
    }

    public void validateBookAddParameters(HashMap<String, String> parameters) {
        validateBookName(parameters.get("bookName"));
        validateBookAuthor(parameters.get("bookAuthor"));
        validateBookYear(parameters.get("year"));
        validateNewBookGenre(parameters.get("genre"));
        validateBookDescription(parameters.get("description"));
        validatePages(parameters.get("pages"));
        validateBookAmount(parameters.get("amountForHome"));
        validateBookAmount(parameters.get("amountInRRoom"));
    }

    public boolean validateReservationType(String reservationTypeParameter) {
        validateNotEmpty(reservationTypeParameter);
        return reservationTypeParameter.equals("Home") || reservationTypeParameter.equals("RRoom")
                || inputInvalidVSErrorMessage(Message.INVALID_INPUT);
    }
}
