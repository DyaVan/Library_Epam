package com.diachuk.library.services;

import com.diachuk.library.dao.entities.*;
import com.diachuk.library.dao.implementations.MySql.*;
import com.diachuk.library.manage.LibraryConfig;
import com.diachuk.library.manage.Message;
import com.diachuk.library.services.json.JsonResponseBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by VA-N_ on 16.01.2017.
 */
public class BooksService extends JsonResponseBuilder {

    private static final String RROOM_ISSUE_TYPE = "RRoom";
    private static final String HOME_ISSUE_TYPE = "Home";

    private static final Object MONITOR = new Object();

    private IReservation addReservationDataObject(IReservation reservation) {
        if (reservation.getType() == "Home") {
            Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
            addDataObject("reservation", reservation, gson);
        } else {
            addDataObject("reservation", reservation);
        }
        return reservation;
    }

    private boolean errorVSErrorMessage(String messageBundleName) {
        appendErrorMessage(Message.getInstance().getMessage(messageBundleName));
        return false;
    }

    private boolean errorVSMessageAndPageReload(String messageBundleName) {
        appendErrorMessage(Message.getInstance().getMessage(messageBundleName));
        setReloadPage(true);
        return false;
    }

    private boolean successVSMessage(String messageBundleName) {
        appendSuccessMessage(Message.getInstance().getMessage(messageBundleName));
        setSuccessFlag(true);
        return true;
    }

    private boolean successVSMessageAndPageReload(String messageBundleName) {
        appendSuccessMessage(Message.getInstance().getMessage(messageBundleName));
        setSuccessFlag(true);
        setReloadPage(true);
        return true;
    }

    private boolean isAlreadyRequesed(Integer bookId, User user) throws SQLException {
        return MySqlRequestDAO.getInstance().isCreatedByBookAndUser(bookId, user);
    }

    private boolean reserveDurationCheck(Integer reserveDuration) {
        return reserveDuration < LibraryConfig.getInstance().getMaxHomeReservationDuration() && reserveDuration >= 1
                || errorVSErrorMessage(Message.INVALID_RESERVE_DURATION);
    }

    private boolean AlreadyReservedForHomeCheck(Integer bookId, User user) throws SQLException {
        return !MySqlHomeReservationDAO.getInstance().isCreatedByBookAndUser(bookId, user)
                || errorVSErrorMessage(Message.ALREADY_RESERVED);
    }

    private boolean AlreadyReservedForRRoomCheck(Integer bookId, User user) throws SQLException {
        return !MySqlRRoomReservationDAO.getInstance().isCreatedByBookAndUser(bookId, user)
                || errorVSErrorMessage(Message.ALREADY_RESERVED);
    }

    private Date calculateDueDate(Integer reserveDuration) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, reserveDuration);
        return cal.getTime();
    }

    private Date calculateDueTime(Integer reserveDuration) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, reserveDuration);
        return cal.getTime();
    }

    private boolean isAvailableCheck(Integer bookId, String issueType) throws SQLException {
        switch (issueType) {
            case HOME_ISSUE_TYPE:
                return MySqlBookDAO.getInstance().getAvailableForHomeByBookId(bookId) > 0;
            case RROOM_ISSUE_TYPE:
                return MySqlBookDAO.getInstance().getAvailableInRRoomByBookId(bookId) > 0;
            default:
                return errorVSMessageAndPageReload(Message.BOOK_DISAPPEARED);
        }
    }

    private boolean excessCheck(Integer userId, Integer bookId, Integer maxAmount) throws SQLException {
        return MySqlCrossTableDAO.getInstance().bookIssuingExcessCheck(userId, bookId, maxAmount)
                || errorVSErrorMessage(Message.TOO_MUCH_BOOKS_WANTED);
    }

    private boolean alreadyIssuedCheck(Integer bookId, Integer userId) throws SQLException {
        return !MySqlBookLoanDAO.getInstance().checkExistenceByBookVSUser(bookId, userId)
                || errorVSMessageAndPageReload(Message.ALREADY_ISSUED);
    }

    private boolean successVSIncreaseAvailableForHome(Integer bookId) throws SQLException {
        MySqlBookDAO.getInstance().increaseAvailableForHome(bookId);
        appendSuccessMessage(Message.getInstance().getMessage(Message.BOOKS_RETURNED_SUCCESSFULLY));
        return true;
    }

    private Request findOldestRequest(Integer bookId) throws SQLException {
        Request bookRequest = null;
        for (int offset = 0; true; offset++) {
            bookRequest = MySqlRequestDAO.getInstance().getOldestByBook(bookId, offset);
            if (bookRequest == null || excessCheck(bookRequest.getUserId(), bookId, LibraryConfig.getInstance().getMaxBooksToGive())) {
                break;
            }
        }
        return bookRequest;
    }

    private boolean successVSEmailReservationNotification(Integer userId, Integer bookId) throws SQLException {
        sendEmailReservationNotification(userId, bookId);
        appendSuccessMessage(Message.getInstance().getMessage(Message.BOOKS_RETURNED_SUCCESSFULLY));
        return true;
    }

    private boolean reserveReturnedBook(Integer bookId) throws SQLException {
        Request bookRequest = findOldestRequest(bookId);
        Integer reserveDuration = LibraryConfig.getInstance().getMaxDurationOfBookLoan();
        if (bookRequest == null && MySqlCrossTableDAO.getInstance().createReservationFromRequest(bookRequest, reserveDuration)) {
            return successVSEmailReservationNotification(bookRequest.getUserId(), bookRequest.getBookId());
        } else {
            appendNotificationMessage(Message.getInstance().getMessage(Message.UNDEFINED_ERROR));
            return false;
        }
    }

    public boolean returnBook(Integer bookLoanId) throws SQLException {
        BookLoan bookLoan = MySqlBookLoanDAO.getInstance().findBookLoanById(bookLoanId);
        if (bookLoan == null || bookLoan.getReturnDate() != null) {
            appendErrorMessage(Message.getInstance().getMessage(Message.BOOK_LOAN_NOT_FOUND));
            return false;
        }

        synchronized (MONITOR) {
            MySqlBookLoanDAO.getInstance().setReturnDate(bookLoanId);
            if (isProhibited(bookLoan.getBookId()) && !reserveReturnedBook(bookLoan.getBookId())) {
                return successVSIncreaseAvailableForHome(bookLoan.getBookId());
            }
            return true;
        }
    }

    public boolean isProhibited(Integer bookId) throws SQLException {
        return MySqlBannedBookDAO.getInstance().checkExistence(bookId);
    }

    public boolean makeHomeReservation(Integer bookId, User user, Integer reserveDuration) throws SQLException {
        if (!reserveDurationCheck(reserveDuration)
                || !AlreadyReservedForHomeCheck(bookId, user)
                || !alreadyIssuedCheck(bookId, user.getId())) {
            return false;
        }

        synchronized (MONITOR) {
            Book book = findBookById(bookId);
            if (book == null) {
                appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
                return false;
            }

            Date dueDate = calculateDueDate(reserveDuration);
            if (isAvailableCheck(book.getId(), "Home")
                    && MySqlCrossTableDAO.getInstance().insertHomeReservation(book, user, dueDate)) {

                return successVSMessageAndPageReload(Message.RESERVATION_SUCCEEDED);
            } else {
                return errorVSErrorMessage(Message.FAILED_OPERATION);
            }
        }
    }

    public boolean makeRRoomReservation(Integer bookId, User user) throws SQLException {
        if (!AlreadyReservedForRRoomCheck(bookId, user) || !alreadyIssuedCheck(bookId, user.getId())) {
            return false;
        }

        synchronized (MONITOR) {
            Book book = findBookById(bookId);
            if (book == null) {
                return errorVSErrorMessage(Message.FAILED_OPERATION);
            }

            Date dueTime = calculateDueTime(LibraryConfig.getInstance().getMaxRRoomReservationDuration());
            if (isAvailableCheck(book.getId(), "RRoom")
                    && MySqlCrossTableDAO.getInstance().insertRRoomReservation(book, user, dueTime)) {

                return successVSMessageAndPageReload(Message.RESERVATION_SUCCEEDED);

            } else {
                return errorVSErrorMessage(Message.FAILED_OPERATION);
            }
        }
    }

    public void sendEmailReservationNotification(Integer userId, Integer bookId) throws SQLException {
        User user = MySqlUserDAO.getInstance().findById(userId);
        EmailService emailService = new EmailService();
        emailService.sendReservationNotification(user, bookId);
    }

    public boolean issueBook(Integer reservationId, String reservationType) throws SQLException {
        IReservation reservation = getDataFromReservation(reservationId, reservationType);

        if (reservation.getBook() == null) {
            return errorVSErrorMessage(Message.FAILED_OPERATION);
        }

        synchronized (MONITOR) {
            if (alreadyIssuedCheck(reservation.getBookId(), reservation.getUserId())
                    && !isProhibited(reservation.getBookId())
                    && excessCheck(reservation.getUserId(), reservation.getBookId(), LibraryConfig.getInstance().getMaxBooksToGive())
                    && isAvailableCheck(reservation.getBookId(), reservationType)
                    && MySqlCrossTableDAO.getInstance().createBookLoanFromReservation(reservationId,reservation.getBookId(), reservation.getUserId(),
                    reservationType, LibraryConfig.getInstance().getMaxDurationOfBookLoan())) {

                return successVSMessage(Message.SUCCESSFULL_OPERATION);
            }
            return errorVSErrorMessage(Message.FAILED_OPERATION);
        }
    }

    public BookLoan findBookLoanVSUser(Integer bookLoanId) throws SQLException {
        BookLoan bookLoan = MySqlCrossTableDAO.getInstance().findBookLoanVSUser(bookLoanId);
        if (bookLoan == null) {
            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
        } else {
            Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
            addDataObject("bookLoan", bookLoan, gson);
        }
        return bookLoan;
    }

    private IReservation getDataFromReservation(Integer reservationId, String issueType) throws SQLException {
        MySqlBookDAO bookDAO = MySqlBookDAO.getInstance();
        IReservation reservation = null;
        switch (issueType) {
            case HOME_ISSUE_TYPE: {
                reservation = MySqlHomeReservationDAO.getInstance().getById(reservationId);
                break;
            }
            case RROOM_ISSUE_TYPE: {
                reservation = MySqlRRoomReservationDAO.getInstance().getById(reservationId);
            }
        }
        if (reservation != null) {
            reservation.setBook(bookDAO.findBookById(reservation.getBookId()));
        }
        return reservation;
    }

    public IReservation findReservationVSUser(Integer reservationId, String reservationType) throws SQLException {
        IReservation reservation = null;
        switch (reservationType) {
            case HOME_ISSUE_TYPE: {
                reservation = MySqlHomeReservationDAO.getInstance().getById(reservationId);
                break;
            }
            case RROOM_ISSUE_TYPE: {
                reservation = MySqlRRoomReservationDAO.getInstance().getById(reservationId);
            }
        }
        if (reservation != null) {
            reservation.setUser(MySqlUserDAO.getInstance().findById(reservation.getUserId()));
            reservation.setBook(MySqlBookDAO.getInstance().findBookById(reservation.getBookId()));
            return addReservationDataObject(reservation);
        }
        setSuccessFlag(false);
        return null;
    }

    public boolean addBook(String bookName, String author, Integer year, String genre, String description,
                           Integer pages, Integer amountForHome, Integer amountInRRoom) throws SQLException {
        if (MySqlBookDAO.getInstance().insertBook(bookName, author, year, genre, description, pages, amountForHome, amountInRRoom)) {
            appendSuccessMessage(Message.getInstance().getMessage(Message.SUCCESSFULL_OPERATION));
            return true;
        }
        appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
        return false;
    }

    public Integer findBookIdByNameAuthorYear(String bookName, String author, Integer year) throws SQLException {
        return MySqlBookDAO.getInstance().findBookIdByNameAuthorYear(bookName, author, year);
    }

    public Book findBookById(Integer bookId) throws SQLException {
        Book book = MySqlBookDAO.getInstance().findBookById(bookId);
        if (book == null) {
            appendErrorMessage(Message.getInstance().getMessage(Message.BOOK_NOT_FOUND) + "Book ID: " + bookId);
            setSuccessFlag(false);
        }
        return book;
    }

    public boolean makeBookRequest(Integer bookId, User user) throws SQLException {
        if (bookId == null || user == null) {
            return errorVSErrorMessage(Message.FAILED_OPERATION);
        }

        if (isAlreadyRequesed(bookId, user)) {
            return errorVSErrorMessage(Message.ALREADY_REQUESTED);
        }
        synchronized (MONITOR) {
            Book book = findBookById(bookId);
            if (book != null
                    && book.getAvailableForHome() == 0
                    && MySqlRequestDAO.getInstance().insertRequest(book, user)) {

                return successVSMessageAndPageReload(Message.SUCCESSFULL_OPERATION);
            } else {
                return errorVSMessageAndPageReload(Message.UNDEFINED_ERROR);
            }
        }
    }
}
