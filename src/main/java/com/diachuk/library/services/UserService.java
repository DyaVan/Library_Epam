package com.diachuk.library.services;

import com.diachuk.library.dao.entities.*;
import com.diachuk.library.dao.implementations.MySql.MySqlCrossTableDAO;
import com.diachuk.library.dao.implementations.MySql.MySqlQuestionDAO;
import com.diachuk.library.dao.implementations.MySql.MySqlUserDAO;
import com.diachuk.library.manage.Message;
import com.diachuk.library.manage.NavigationManager;
import org.apache.commons.validator.routines.EmailValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by VA-N_ on 17.01.2017.
 */
public class UserService extends JsonResponseBuilder {

    public boolean addUser(String name, String surname, String email, String password) throws SQLException {
        if (MySqlUserDAO.getInstance().checkExistenceByEmail(email)) {
            appendErrorMessage(Message.getInstance().getMessage(Message.EMAIL_ALREADY_EXISTS));
            setSuccessFlag(false);
            return false;
        }

        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoleId(User.CLIENT_ROLE_ID);

        if (MySqlUserDAO.getInstance().insertUser(user)) {
            appendSuccessMessage(Message.getInstance().getMessage(Message.REGISTRATION_SUCCESS));
            return true;
        } else {
            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
            return false;
        }
    }

    public User getUserByEmail(String email) throws SQLException {
        EmailValidator validator = EmailValidator.getInstance();
        if (email == null || !validator.isValid(email)) {
            appendErrorMessage(Message.getInstance().getMessage(Message.EMAIL_NOT_VALID));
            setSuccessFlag(false);
            return null;
        } else {
            User user = MySqlUserDAO.getInstance().findByEmail(email);
            if (user == null) {
                appendErrorMessage(Message.getInstance().getMessage(Message.NO_USER_WITH_SUCH_EMAIL));
                setSuccessFlag(false);
            }
            return user;
        }
    }

    public ArrayList<BookLoan> getUserHistory(User currentUser) throws SQLException {
        ArrayList<BookLoan> userBookLoans = new ArrayList<>();
        if (currentUser == null) {
            setSuccessFlag(false);
            setReloadPage(true);
            addDataObject("userBookLoans", userBookLoans);
            return userBookLoans;
        }
        userBookLoans = MySqlCrossTableDAO.getInstance().getUserHistory(currentUser.getId());
        addDataObject("userBookLoans", userBookLoans);
        return userBookLoans;
    }

    public ArrayList<IReservation> getUserCurrentReservations(User currentUser) throws SQLException {
        ArrayList<IReservation> userCurrentReservations = new ArrayList<>();
        if (currentUser == null) {
            setSuccessFlag(false);
            setReloadPage(true);
            addDataObject("userCurrentReservations", userCurrentReservations);
        }
        userCurrentReservations = MySqlCrossTableDAO.getInstance().getUserCurrentReservations(currentUser.getId());
        addDataObject("userCurrentReservations", userCurrentReservations);
        return userCurrentReservations;
    }

    public ArrayList<Request> getUserCurrentRequests(User currentUser) throws SQLException {
        ArrayList<Request> userCurrentRequests = new ArrayList<>();
        if (currentUser == null) {
            setSuccessFlag(false);
            setReloadPage(true);
            addDataObject("userCurrentRequests", userCurrentRequests);
        }
        userCurrentRequests = MySqlCrossTableDAO.getInstance().getUserCurrentRequests(currentUser.getId());
        addDataObject("userCurrentRequests", userCurrentRequests);
        return userCurrentRequests;
    }

    public ArrayList<BookLoan> getUserCurrentBookLoans(User currentUser) throws SQLException {
        ArrayList<BookLoan> userCurrentBookLoans = new ArrayList<>();
        if (currentUser == null) {
            setSuccessFlag(false);
            setReloadPage(true);
            addDataObject("userCurrentBookLoans", userCurrentBookLoans);
            return userCurrentBookLoans;
        }
        userCurrentBookLoans = MySqlCrossTableDAO.getInstance().getUserCurrentBookLoans(currentUser.getId());
        addDataObject("userCurrentBookLoans", userCurrentBookLoans);
        return userCurrentBookLoans;
    }

    public ArrayList<Question> getUserQuestions(User currentUser) throws SQLException {
        if (currentUser == null) {
            return new ArrayList<>();
        }
        return MySqlQuestionDAO.getInstance().getUserQuestions(currentUser.getId());
    }

    public ArrayList<Question> getAllQuestions(Boolean onlyNotAnswered) throws SQLException {
        ArrayList<Question> questions = new ArrayList<>();
        if (onlyNotAnswered == null) {
            questions = MySqlQuestionDAO.getInstance().getAllQuestions(false);
        } else {
            questions = MySqlQuestionDAO.getInstance().getAllQuestions(onlyNotAnswered);
        }
        addDataObject("questions", questions);
        return questions;
    }

    public boolean changeUserRole(Integer userId, Integer roleId) throws SQLException {
        if (roleId > 1) {
            if (MySqlUserDAO.getInstance().updateRole(roleId, userId)) {
                return true;

            } else {
                appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
                setSuccessFlag(false);
                return false;
            }
        } else {
            setSuccessFlag(false);
            appendErrorMessage(Message.getInstance().getMessage(Message.DENIED_OPERATION));
            return false;
        }
    }

    public boolean updateUser(User currentUser, String name, String surname, String email, String password) throws SQLException {
        currentUser.setName(name);
        currentUser.setSurname(surname);
        currentUser.setEmail(email);
        currentUser.setPassword(password);
        if (MySqlUserDAO.getInstance().updateBasic(currentUser)) {
            appendSuccessMessage(Message.getInstance().getMessage(Message.SUCCESSFULL_OPERATION));
            return true;
        } else {
            setSuccessFlag(false);
            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
            return false;
        }
    }

    public User logIn(String email, String password) throws SQLException {
        User user = getUserByEmail(email);
        if (user!= null && validateUserPassword(password, user)) {
            return user;
        }
        return null;
    }

    public boolean validateUserPassword(String password, User user) {
        if (password == null || user == null || !user.getPassword().equals(password)) {
            appendErrorMessage(Message.getInstance().getMessage(Message.WRONG_PASSWORD));
            setSuccessFlag(false);
            return false;
        }
        return true;
    }

}
