package com.diachuk.library.services;

import com.diachuk.library.dao.entities.Question;
import com.diachuk.library.dao.entities.User;
import com.diachuk.library.dao.implementations.MySql.MySqlQuestionDAO;
import com.diachuk.library.dao.implementations.MySql.MySqlRoleDAO;
import com.diachuk.library.dao.implementations.MySql.MySqlUserDAO;
import com.diachuk.library.manage.Message;
import com.diachuk.library.services.json.JsonResponseBuilder;

import java.sql.SQLException;

/**
 * Created by VA-N_ on 27.01.2017.
 */
public class LibraryService extends JsonResponseBuilder {

    public static int getAccessLevel(int roleId) throws SQLException {
        return MySqlRoleDAO.getInstance().getAccessLevel(roleId);
    }

    public boolean deleteRole(Integer roleId) throws SQLException {
        if (MySqlRoleDAO.getInstance().deleteRole(roleId)) {
            appendSuccessMessage(Message.getInstance().getMessage(Message.SUCCESSFUL_ROLE_DELETION));
            setReloadPage(true);
            return true;
        } else {
            setSuccessFlag(false);
            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_ROLE_DELETION));
            return false;
        }
    }

    public boolean changeRole(Integer roleId, String roleName, Integer roleAccessLevel) throws SQLException {
        if (roleId > 1) {
            if (roleAccessLevel < 10 && MySqlRoleDAO.getInstance().updateRole(roleId, roleName, roleAccessLevel)) {
                appendSuccessMessage(Message.getInstance().getMessage(Message.SUCCESSFUL_ROLE_CHANGE));
                setReloadPage(true);
                return true;
            } else {
                appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_ROLE_CHANGE));
                setSuccessFlag(false);
                return false;
            }
        } else {
            appendErrorMessage(Message.getInstance().getMessage(Message.DENIED_ROLE_CHANGE));
            setSuccessFlag(false);
            return false;
        }
    }

    public boolean deleteBadQuestion(Integer questionId) throws SQLException {
        Question question = MySqlQuestionDAO.getInstance().getQuestionById(questionId);
        User user = MySqlUserDAO.getInstance().findById(question.getUserId());
        EmailService emailService = new EmailService();
        emailService.sendBadQuestionNotification(user);
        if (MySqlQuestionDAO.getInstance().deleteQuestion(questionId)) {
            appendSuccessMessage(Message.getInstance().getMessage(Message.SUCCESSFULL_OPERATION));
            return true;
        } else {
            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
            setSuccessFlag(false);
            return false;
        }
    }

    public boolean deleteBadQuestionVSBan(Integer questionId) throws SQLException {
        Question question = MySqlQuestionDAO.getInstance().getQuestionById(questionId);
        User user = MySqlUserDAO.getInstance().findById(question.getUserId());
        EmailService emailService = new EmailService();
        emailService.sendQuestionsBanNotification(user);
        if (MySqlQuestionDAO.getInstance().deleteQuestion(questionId) &&
                MySqlUserDAO.getInstance().updateRole(SessionManagerService.NO_QUESTION_CLIENT_ACCESS, question.getUserId())) {
            appendSuccessMessage(Message.getInstance().getMessage(Message.SUCCESSFULL_OPERATION_VS_USER_QUESTIONS_BAN));
            return true;
        } else {
            setSuccessFlag(false);
            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
            return false;
        }
    }

    public boolean deleteQuestion(Integer questionId,Integer userId) throws SQLException {
        if (MySqlQuestionDAO.getInstance().deleteUserQuestion(questionId, userId)) {
            appendSuccessMessage(Message.getInstance().getMessage(Message.SUCCESSFULL_OPERATION));
            setReloadPage(true);
            return true;
        } else {
            setSuccessFlag(false);
            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
            return false;
        }
    }

    public boolean createNewQuestion(String subject, String questionText, Integer userId) throws SQLException {
        if (MySqlQuestionDAO.getInstance().insertQuestion(subject, questionText, userId)) {
            setReloadPage(true);
            return true;
        } else {
            setSuccessFlag(false);
            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
            return false;
        }
    }

    public boolean answerQuestion(Integer questionId, String answer) throws SQLException {
        if (MySqlQuestionDAO.getInstance().updateQuestionAnswer(questionId, answer)) {
            setReloadPage(true);
            return true;
        } else {
            setSuccessFlag(false);
            appendErrorMessage(Message.getInstance().getMessage(Message.FAILED_OPERATION));
            return false;
        }
    }
}
