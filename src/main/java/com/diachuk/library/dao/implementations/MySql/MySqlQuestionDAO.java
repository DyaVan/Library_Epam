package com.diachuk.library.dao.implementations.MySql;

import com.diachuk.library.dao.entities.Question;
import com.diachuk.library.dao.entities.Request;
import com.diachuk.library.dao.factory.MySql.MySqlDAOFactory;
import com.diachuk.library.dao.interfaces.IQuestionDAO;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by VA-N_ on 29.12.2016.
 */
public class MySqlQuestionDAO implements IQuestionDAO {

    static final String ID_COLUMN = "id";
    static final String USER_ID_COLUMN = "userId";
    static final String SUBJECT_COLUMN = "subject";
    static final String TEXT_COLUMN = "text";
    static final String DATE_COLUMN = "date";
    static final String IS_ANSWERED_COLUMN = "isAnswered";
    static final String ANSWER_COLUMN = "answer";

    private static MySqlQuestionDAO instance;

    public static MySqlQuestionDAO getInstance() {
        if (instance == null) {
            return instance = new MySqlQuestionDAO();
        }
        return instance;
    }

    private MySqlQuestionDAO() {

    }


    public ArrayList<Question> getUserQuestions(Integer userId) throws SQLException {
        ArrayList<Question> questions = new ArrayList<>();
        if (userId == null) {
            return questions;
        }

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT question.id, question.subject, question.text, question.date, question.isAnswered, question.answer " +
                             "FROM question WHERE question.userId = ?")) {

            stm.setInt(1, userId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt(MySqlQuestionDAO.ID_COLUMN));
                question.setSubject(rs.getString(MySqlQuestionDAO.SUBJECT_COLUMN));
                question.setText(rs.getString(MySqlQuestionDAO.TEXT_COLUMN));
                question.setDate(rs.getDate(MySqlQuestionDAO.DATE_COLUMN));
                question.setAnswered(rs.getBoolean(MySqlQuestionDAO.IS_ANSWERED_COLUMN));
                question.setAnswer(rs.getString(MySqlQuestionDAO.ANSWER_COLUMN));
                questions.add(question);
            }

            return questions;
        }

    }

    public ArrayList<Question> getAllQuestions(boolean onlyNotAnswered) throws SQLException {
        ArrayList<Question> questions = new ArrayList<>();

        try (Connection connection = MySqlDAOFactory.createConnection()) {
            StringBuilder query = new StringBuilder("SELECT question.id, question.userId, question.subject, question.text, question.date, question.isAnswered, question.answer " +
                    "FROM question");

            query.append(onlyNotAnswered);

            if (onlyNotAnswered) {
                query.append(" WHERE question.isAnswered = false");
            }

            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query.toString());

            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt(MySqlQuestionDAO.ID_COLUMN));
                question.setUserId(rs.getInt(MySqlQuestionDAO.USER_ID_COLUMN));
                question.setSubject(rs.getString(MySqlQuestionDAO.SUBJECT_COLUMN));
                question.setText(rs.getString(MySqlQuestionDAO.TEXT_COLUMN));
                question.setDate(rs.getDate(MySqlQuestionDAO.DATE_COLUMN));
                question.setAnswered(rs.getBoolean(MySqlQuestionDAO.IS_ANSWERED_COLUMN));
                question.setAnswer(rs.getString(MySqlQuestionDAO.ANSWER_COLUMN));
                questions.add(question);
            }

            return questions;
        }
    }

    public boolean deleteQuestion(Integer questionId) throws SQLException {
        if (questionId == null) {
            return false;
        }
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("DELETE FROM question WHERE question.id = ? LIMIT 1")) {

            stm.setInt(1, questionId);

            if (stm.executeUpdate() > 0) {
                return true;
            }
            return false;
        }
    }

    public Question getQuestionById(Integer questionId) throws SQLException {
        if (questionId == null) {
            return null;
        }

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT question.id, question.userId, question.subject, question.text, question.date, question.isAnswered, question.answer " +
                             "FROM question WHERE question.id = ?")) {

            stm.setInt(1, questionId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt(MySqlQuestionDAO.ID_COLUMN));
                question.setUserId(rs.getInt(MySqlQuestionDAO.USER_ID_COLUMN));
                question.setSubject(rs.getString(MySqlQuestionDAO.SUBJECT_COLUMN));
                question.setText(rs.getString(MySqlQuestionDAO.TEXT_COLUMN));
                question.setDate(rs.getDate(MySqlQuestionDAO.DATE_COLUMN));
                question.setAnswered(rs.getBoolean(MySqlQuestionDAO.IS_ANSWERED_COLUMN));
                question.setAnswer(rs.getString(MySqlQuestionDAO.ANSWER_COLUMN));
                return question;
            }

            return null;
        }

    }

    public boolean insertQuestion(String subject, String questionText, Integer userId) throws SQLException {
        if (subject == null || questionText == null || userId == null) {
            return false;
        }
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "INSERT INTO question (userId,subject,text,date) VALUES (?,?, ?, CURRENT_TIME())")) {

            stm.setInt(1, userId);
            stm.setString(2, subject);
            stm.setString(3, questionText);

            if (stm.executeUpdate() > 0) {
                return true;
            }
            return false;
        }

    }

    public boolean updateQuestionAnswer(Integer questionId, String answer) throws SQLException {
        if (questionId == null || answer == null) {
            return false;
        }
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "UPDATE question SET question.isAnswered = 1, question.answer = ? WHERE question.id = ?")) {

            stm.setString(1, answer);
            stm.setInt(2, questionId);

            if (stm.executeUpdate() > 0) {
                return true;
            }
            return false;
        }
    }

    @Override
    public void deleteOlderThan(Integer questionLifeTime) throws SQLException {
        if (questionLifeTime == null) {
            return;
        }

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "DELETE FROM question WHERE homereservation.dueDate < NOW() - INTERVAL ? MONTH")) {
            stm.setInt(1,questionLifeTime);
            stm.executeUpdate();
        }
    }

    public boolean deleteUserQuestion(Integer questionId, Integer userId) throws SQLException {
        if (questionId == null || userId == null) {
            return false;
        }
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("DELETE FROM question WHERE question.id = ? AND question.userId = ? LIMIT 1")) {

            stm.setInt(1, questionId);
            stm.setInt(2, userId);

            if (stm.executeUpdate() > 0) {
                return true;
            }
            return false;
        }
    }
}
