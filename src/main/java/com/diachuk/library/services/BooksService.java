package com.diachuk.library.services;

import com.diachuk.library.dao.entities.*;
import com.diachuk.library.dao.implementations.MySql.*;
import com.diachuk.library.manage.LibraryConfig;
import com.diachuk.library.manage.Message;
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

    public Book findBookById(Integer bookId) throws SQLException {
        if (bookId != null) {
            Book book = MySqlBookDAO.getInstance().findBookById(bookId);
            if (book == null) {

                appendErrorMessage(Message.getInstance().getMessage(Message.BOOK_NOT_FOUND)+ "Book ID: " + bookId);
                setSuccessFlag(false);
            }
            return book;
        }
        appendErrorMessage(Message.getInstance().getMessage(Message.INVALID_INPUT) + "Book ID.");
        setSuccessFlag(false);
        return null;
    }

    public boolean makeBookRequest(Integer bookId, User user) throws SQLException {
        if (bookId == null || user == null) {
            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
            setSuccessFlag(false);
            return false;
        }

        if (isAlreadyRequesed(bookId, user)) {
            appendErrorMessage(Message.getInstance().getMessage(Message.ALREADY_REQUESTED));
            setSuccessFlag(false);
            setReloadPage(true);
            return false;
        }
        synchronized (MONITOR) {
            Book book = findBookById(bookId);
            if (book == null) {
                appendErrorMessage(Message.getInstance().getMessage(Message.BOOK_NOT_FOUND));
                setSuccessFlag(false);
                setReloadPage(true);
                return false;
            }
            if (book.getAvailableForHome() == 0) {
                if (MySqlRequestDAO.getInstance().insertRequest(book, user)) {
                    appendSuccessMessage(Message.getInstance().getMessage(Message.BOOK_NOT_FOUND));
                    setSuccessFlag(true);
                    setReloadPage(true);
                    return true;
                } else {
                    appendErrorMessage(Message.getInstance().getMessage(Message.UNDEFINED_ERROR));
                    setSuccessFlag(false);
                    return false;
                }
            } else {
                appendNotificationMessage(Message.getInstance().getMessage(Message.BOOK_APPEARED));
                setSuccessFlag(false);
                setReloadPage(true);
                return false;
            }
        }

    }

    private boolean isAlreadyRequesed(Integer bookId, User user) throws SQLException {
        return MySqlRequestDAO.getInstance().isCreatedByBookAndUser(bookId, user);
    }

    public boolean makeHomeReservation(Integer bookId, User user, Integer reserveDuration) throws SQLException {
        if (bookId == null || user == null || reserveDuration == null ||
                reserveDuration > LibraryConfig.getInstance().getMaxHomeReservationDuration() || reserveDuration < 1) {
            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
            setSuccessFlag(false);
            return false;
        }

        if (isAlreadyReservedForHome(bookId, user)) {
            appendErrorMessage(Message.getInstance().getMessage(Message.ALREADY_RESERVED));
            setSuccessFlag(false);
            setReloadPage(true);
            return false;
        }

        synchronized (MONITOR) {
            Book book = findBookById(bookId);
            if (book == null) {
                appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
                setSuccessFlag(false);
                return false;
            }
            if (book.getAvailableForHome() > 0) {
                Date dueDate = calculateDueDate(reserveDuration);
                return MySqlCrossTableDAO.getInstance().insertHomeReservation(book, user, dueDate);
            } else {
                appendErrorMessage(Message.getInstance().getMessage(Message.BOOK_DISAPPEARED));
                setSuccessFlag(false);
                setReloadPage(true);
                return false;
            }
        }

    }

    public boolean makeRRoomReservation(Integer bookId, User user) throws SQLException {
        if (bookId == null || user == null) {
            appendErrorMessage(Message.getInstance().getMessage(Message.BOOK_DISAPPEARED));
            setSuccessFlag(false);
            return false;
        }

        if (isAlreadyReservedForRRoom(bookId, user)) {
            appendErrorMessage(Message.getInstance().getMessage(Message.ALREADY_RESERVED));
            setSuccessFlag(false);
            setReloadPage(true);
            return false;
        }

        synchronized (MONITOR) {
            Book book = findBookById(bookId);
            if (book == null) {
                appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
                setSuccessFlag(false);
                return false;
            }
            if (book.getAvailableInRRoom() > 0) {
                Date dueTime = calculateDueTime(LibraryConfig.getInstance().getMaxRRoomReservationDuration());
                appendSuccessMessage(Message.getInstance().getMessage(Message.RESERVATION_SUCCEEDED));
                setSuccessFlag(true);
                setReloadPage(true);
                return MySqlCrossTableDAO.getInstance().insertRRoomReservation(book, user, dueTime);
            } else {
                appendErrorMessage(Message.getInstance().getMessage(Message.BOOK_DISAPPEARED));
                setSuccessFlag(false);
                setReloadPage(true);
                return false;
            }
        }


    }

    private boolean isAlreadyReservedForHome(Integer bookId, User user) throws SQLException {
        return MySqlHomeReservationDAO.getInstance().isCreatedByBookAndUser(bookId, user);
    }

    private boolean isAlreadyReservedForRRoom(Integer bookId, User user) throws SQLException {
        return MySqlRRoomReservationDAO.getInstance().isCreatedByBookAndUser(bookId, user);
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

    private boolean isAvailable(Integer bookId, String issueType) throws SQLException {
        switch (issueType) {
            case HOME_ISSUE_TYPE:
                return MySqlBookDAO.getInstance().getAvailableForHomeByBookId(bookId) > 0;
            case RROOM_ISSUE_TYPE:
                return MySqlBookDAO.getInstance().getAvailableInRRoomByBookId(bookId) > 0;
            default:
                return false;
        }
    }

    private boolean excessCheck(Integer userId, Integer bookId, Integer maxAmount) throws SQLException {
        return MySqlCrossTableDAO.getInstance().bookIssuingExcessCheck(userId, bookId, maxAmount);
    }

    private boolean isAlreadyIssued(Integer bookId, Integer userId) throws SQLException {
        return MySqlBookLoanDAO.getInstance().checkExistenceByBookVSUser(bookId, userId);
    }

    public boolean isProhibited(Integer bookId) throws SQLException {
        return MySqlBannedBookDAO.getInstance().checkExistence(bookId);
    }

    public boolean returnBook(Integer bookLoanId) throws SQLException {
        Request bookRequest = null;
        MySqlBookDAO bookDAO = MySqlBookDAO.getInstance();

        BookLoan bookLoan = MySqlBookLoanDAO.getInstance().findBookLoanById(bookLoanId);
        if (bookLoan == null || bookLoan.getReturnDate() != null) {
            appendErrorMessage(Message.getInstance().getMessage(Message.BOOK_LOAN_NOT_FOUND));
            setSuccessFlag(false);
            return false;
        }

        synchronized (MONITOR) {
            MySqlBookLoanDAO.getInstance().setReturnDate(bookLoanId);

            if (isProhibited(bookLoan.getBookId())) {
                MySqlBookDAO.getInstance().increaseAvailableForHome(bookLoan.getBookId());
                appendSuccessMessage(Message.getInstance().getMessage(Message.BOOKS_RETURNED_SUCCESSFULLY));
                setSuccessFlag(true);
                return true;
            }

            boolean endOfSearch = false;

            for (int offset = 1; offset > 0; offset++) {
                bookRequest = MySqlRequestDAO.getInstance().getOldestByBook(bookLoan.getBookId(), offset);
                if (bookRequest == null || excessCheck(bookRequest.getUserId(), bookLoan.getBookId(), LibraryConfig.getInstance().getMaxBooksToGive())) {
                    endOfSearch = true;
                    break;
                }
            }

            if (bookRequest == null) {
                MySqlBookDAO.getInstance().increaseAvailableForHome(bookLoan.getBookId());
                appendSuccessMessage(Message.getInstance().getMessage(Message.BOOKS_RETURNED_SUCCESSFULLY));
                setSuccessFlag(true);
                return true;
            }

            if (MySqlCrossTableDAO.getInstance().createReservationFromRequest(bookRequest, LibraryConfig.getInstance().getMaxDurationOfBookLoan())) {
                sendEmailReservationNotification(bookRequest.getUserId());
                appendSuccessMessage(Message.getInstance().getMessage(Message.BOOKS_RETURNED_SUCCESSFULLY));
                setSuccessFlag(true);
                return true;
            } else {
                appendNotificationMessage(Message.getInstance().getMessage(Message.UNDEFINED_ERROR));
                appendSuccessMessage(Message.getInstance().getMessage(Message.BOOKS_RETURNED_SUCCESSFULLY));
                setSuccessFlag(true);
                return true;
            }

        }


    }

    public void sendEmailReservationNotification(Integer userId) throws SQLException {
        User user = MySqlUserDAO.getInstance().findById(userId);
        EmailService emailService = new EmailService();
        emailService.sendReservationNotification(user, userId);
    }

    public boolean issueBook(Integer reservationId) throws SQLException {
        Book book = null;
        Integer userId = null;
        String issueType = null;
        MySqlBookDAO bookDAO = MySqlBookDAO.getInstance();

        HomeReservation homeReservation = MySqlHomeReservationDAO.getInstance().getById(reservationId);
        if (homeReservation != null) {
            book = bookDAO.findBookById(homeReservation.getBookId());
            userId = homeReservation.getUserId();
            issueType = HOME_ISSUE_TYPE;
        } else {
            RRoomReservation rRoomReservation = MySqlRRoomReservationDAO.getInstance().getById(reservationId);
            if (rRoomReservation != null) {
                book = bookDAO.findBookById(rRoomReservation.getBookId());
                userId = rRoomReservation.getUserId();
                issueType = RROOM_ISSUE_TYPE;
            }
        }

        if (book == null) {
            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
            setSuccessFlag(false);
            return false;
        }

        synchronized (MONITOR) {
            if (!isAlreadyIssued(book.getId(), userId)) {
                appendErrorMessage(Message.getInstance().getMessage(Message.ALREADY_ISSUED));
                setSuccessFlag(false);
                setReloadPage(true);
                return false;
            }

            if (isProhibited(book.getId())) {
                appendErrorMessage(Message.getInstance().getMessage(Message.PROHIBITED_BOOK));
                setSuccessFlag(false);
                setReloadPage(true);
                return false;
            }

            if (!excessCheck(userId, book.getId(), LibraryConfig.getInstance().getMaxBooksToGive())) {
                appendErrorMessage(Message.getInstance().getMessage(Message.TOO_MUCH_BOOKS_WANTED));
                setSuccessFlag(false);
                return false;
            }

            if (!isAvailable(book.getId(), issueType)) {
                appendNotificationMessage(Message.getInstance().getMessage(Message.BOOK_DISAPPEARED));
                setSuccessFlag(false);
                setReloadPage(true);
                return false;
            }

            if (MySqlCrossTableDAO.getInstance().createBookLoanFromReservation(reservationId, book.getId(), userId,
                    issueType, LibraryConfig.getInstance().getMaxDurationOfBookLoan())) {
                return true;
            }

            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
            setSuccessFlag(false);
            return false;
        }

    }

    public BookLoan findBookLoanVSUser(Integer bookLoanId) throws SQLException {
        BookLoan bookLoan = MySqlCrossTableDAO.getInstance().findBookLoanVSUser(bookLoanId);
        if (bookLoan == null) {
            setSuccessFlag(false);
            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
        }else {
            Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
            addDataObject("bookLoan", bookLoan,gson);
        }
        return bookLoan;
    }

    public IReservation findReservationVSUser(Integer reservationId) throws SQLException {
        IReservation reservation = MySqlCrossTableDAO.getInstance().findReservationVSUser(reservationId);
        if (reservation == null) {
            setSuccessFlag(false);
            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
        }else {
            if (reservation.getType() == "Home") {
                Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
                addDataObject("reservation", reservation, gson);
            } else {
                addDataObject("reservation", reservation);
            }
        }
        return reservation;
    }

    public boolean addBook(String bookName, String author, Integer year, String genre, String description,
                           Integer pages, Integer amountForHome, Integer amountInRRoom) throws SQLException {
        if (MySqlBookDAO.getInstance().insertBook(bookName, author, year, genre, description, pages, amountForHome, amountInRRoom)) {
            appendSuccessMessage(Message.getInstance().getMessage(Message.SUCCESSFULL_OPERATION));
            return true;
        }
        appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
        setSuccessFlag(false);
        return false;
    }

    public Integer findBookIdByNameAuthorYear(String bookName, String author, Integer year) throws SQLException {
        return MySqlBookDAO.getInstance().findBookIdByNameAuthorYear(bookName, author, year);
    }
}
