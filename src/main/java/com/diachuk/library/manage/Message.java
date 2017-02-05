package com.diachuk.library.manage;

import java.util.ResourceBundle;

/**
 * Created by VA-N_ on 06.01.2017.
 */
public class Message {private static Message instance;

    private ResourceBundle resource;
    private static final String BUNDLE_NAME = "messages";

    public static final String IMAGE_UPLOAD_FAILED = "image_upload_failed";
    public static final String ONLY_JPEG_IMAGES = "only_jpeg_images";
    public static final String TOO_BIG_FILE_OR_SMTH = "too_big_file_or_smth";
    public static final String INVALID_AMOUNT = "invalid_amount";
    public static final String INVALID_PAGES = "invalid_pages";
    public static final String NULL_DESCRIPTION = "null_description";
    public static final String INVALID_BOOK_YEAR = "invalid_book_year";
    public static final String INVALID_BOOK_AUTHOR = "invalid_author";
    public static final String INVALID_BOOK_NAME = "invalid_book_name";
    public static final String INVALID_GENRE = "invalid_genre";
    public static final String EMPTY_INPUT = "empty_input";
    public static final String INVALID_OFFSET = "invalid_offset";
    public static final String INVALID_SUBJECT = "invalid_subject";
    public static final String INVALID_RESERVE_DURATION = "invalid_reserve_duration";
    public static final String NOT_A_DIGIT = "not_a_digit";
    public static final String ONLY_CHARS = "only_chars";
    public static final String INVALID_QUESTION_TEXT = "invalid_question_text";
    public static final String INVALID_BOOK_LOAN_ID = "invalid_book_loan_id";
    public static final String INVALID_RESERVATION_ID = "invalid_reservation_id";
    public static final String INVALID_BOOK_ID = "invalid_book_id";
    public static final String INVALID_USER_ID = "invalid_user_id";
    public static final String INVALID_QUESTION_ID = "invalid_question_id";
    public static final String INVALID_ACCESS_LEVEL = "invalid_access_level";
    public static final String INVALID_ROLE_ID = "invalid_role_id";
    public static final String INVALID_ROLE_NAME = "invalid_role_name";
    public static final String INVALID_QUESTION_ANSWER = "invalid_question_answer";
    public static final String REGISTRATION_SUCCESS = "registration_success";
    public static final String NONEXISTENT_PAGE = "nonexistent_page";
    public static final String TOO_MUCH_BOOKS_WANTED = "too_much_books_wanted";
    public static final String SUCCESSFUL_ROLE_CHANGE = "successful_role_change";
    public static final String SUCCESSFUL_ROLE_DELETION = "successful_role_deletion";
    public static final String DENIED_ROLE_CHANGE = "denied_role_change";
    public static final String FAILED_ROLE_CHANGE = "failed_role_change";
    public static final String FAILED_ROLE_DELETION = "failed_role_deletion";
    public static final String SERVLET_EXCEPTION = "servlet_exception";
    public static final String IO_EXCEPTION = "io_exception";
    public static final String EMAIL_NOT_VALID = "email_not_valid";
    public static final String EMAIL_ALREADY_EXISTS = "email_already_exists";
    public static final String NO_USER_WITH_SUCH_EMAIL = "no_user_with_such_email";
    public static final String WEAK_PASSWORD = "weak_password";
    public static final String INVALID_NAME = "invalid_name";
    public static final String INVALID_SURNAME = "invalid_surname";
    public static final String WRONG_PASSWORD = "wrong_password";
    public static final String NOT_AUTHORIZED_USER = "not_authorized_user";
    public static final String UNDEFINED_ERROR = "undefined_error";
    public static final String ALREADY_REQUESTED = "already_requested";
    public static final String ALREADY_RESERVED = "already_reserved";
    public static final String ALREADY_ISSUED = "already_issued";
    public static final String BOOK_APPEARED = "book_appeared";
    public static final String BOOK_DISAPPEARED = "book_disappeared";
    public static final String BOOK_NOT_FOUND = "book_not_found";
    public static final String RESERVATION_SUCCEEDED = "reservation_succeeded";
    public static final String BOOKS_RETURNED_SUCCESSFULLY = "books_returned_successfully";
    public static final String ACCESS_DENIED = "access_denied";
    public static final String PROHIBITED_BOOK = "prohibited_book";
    public static final String INVALID_INPUT = "invalid_input";
    public static final String SUCCESSFULL_OPERATION = "successful_operation";
    public static final String FAILED_OPERATION = "failed_operation";
    public static final String DENIED_OPERATION = "denied_operation";
    public static final String SUCCESSFULL_OPERATION_VS_USER_QUESTIONS_BAN = "successful_operation_vs_user_questions_ban";
    public static final String BOOK_LOAN_NOT_FOUND = "book_loan_not_found";

    public static Message getInstance() {
        if (instance == null) {
            instance = new Message();
            instance.resource = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getMessage(String messageKey) {
        return (String) resource.getObject(messageKey);
    }
}
