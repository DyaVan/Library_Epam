package com.diachuk.library.services;

import com.diachuk.library.dao.entities.Book;
import com.diachuk.library.dao.entities.BookLoan;
import com.diachuk.library.dao.entities.User;
import com.diachuk.library.dao.implementations.MySql.MySqlBookDAO;
import com.diachuk.library.manage.LibraryConfig;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by VA-N_ on 30.01.2017.
 */
public class EmailService {

    private static ResourceBundle resource;
    private static final String BUNDLE_NAME = "email";
    private static final String EMAIL_ADDRESS = "email_address";
    private static final String EMAIL_PASSWORD = "email_password";

    private static final String SMTP_HOST = "mail_smtp_host";
    private static final String PERSONAL = "personal";
    private static final String BAD_QUESTION_SUBJECT  = "bad_question_subject";
    private static final String BAD_QUESTION_MESSAGE = "bad_question_message";
    private static final String QUESTIONS_BAN_SUBJECT  = "question_ban_subject";
    private static final String QUESTIONS_BAN_MESSAGE = "question_ban_message";
    private static final String RESERVATION_NOTIFICATION_SUBJECT = "reservation_notification_subject";
    private static final String RESERVATION_NOTIFICATION_MESSAGE = "reservation_notification_message";
    private static final String OVERDUE_NOTIFICATION_SUBJECT = "ovardue_notification_subject";
    private static final String OVERDUE_NOTIFICATION_MESSAGE = "ovardue_notification_message";

    private String emailAddress;
    private String emailPassword;
    private String smtpHost;
    private String personal;

    public EmailService() {
        if (resource == null) {
            resource = ResourceBundle.getBundle(BUNDLE_NAME);
        }

        emailAddress = resource.getString(EMAIL_ADDRESS);
        emailPassword =  resource.getString(EMAIL_PASSWORD);
        smtpHost =  resource.getString(SMTP_HOST);
        personal = resource.getString(PERSONAL);
    }

    public void sendEmail(String toEmail, String subject, String body) {

        Session session = createSession();

        try {
            MimeMessage msg = new MimeMessage(session);

            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply." + emailAddress, personal + "_No-Reply"));

            msg.setReplyTo(InternetAddress.parse("no_reply." + emailAddress, false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAddress, emailPassword);
            }
        };

        return Session.getDefaultInstance(props, auth);
    }

    public void sendBadQuestionNotification(User user) {
        String subject = resource.getString(BAD_QUESTION_SUBJECT);
        String messageBody = resource.getString(BAD_QUESTION_MESSAGE);
        sendEmail(user.getEmail(), subject, messageBody);
    }

    public void sendQuestionsBanNotification(User user) {
        String subject = resource.getString(QUESTIONS_BAN_SUBJECT);
        String messageBody = resource.getString(QUESTIONS_BAN_MESSAGE);
        sendEmail(user.getEmail(), subject, messageBody);
    }

    public void sendReservationNotification(User user, Integer bookId) throws SQLException {
        Book book = MySqlBookDAO.getInstance().findBookById(bookId);
        String subject = resource.getString(RESERVATION_NOTIFICATION_SUBJECT);
        String messageBody = book + "\n" + resource.getString(RESERVATION_NOTIFICATION_MESSAGE) + "\n" +
                "Reservation duration: " + LibraryConfig.getInstance().getMaxHomeReservationDuration();
        sendEmail(user.getEmail(), subject, messageBody);
    }

    public void sendOverdueNotification(BookLoan bookLoan) {
        if (bookLoan == null || bookLoan.getUser() == null || bookLoan.getBook()==null) {
            // TODO: 01.02.2017 logging
            return;
        }
        String subject = resource.getString(OVERDUE_NOTIFICATION_SUBJECT);
        String messageBody = formOverdueNotificationMsg(bookLoan);
        sendEmail(bookLoan.getUser().getEmail(), subject, messageBody);
    }

    private String formOverdueNotificationMsg(BookLoan bookLoan) {
        String messagePattern = resource.getString(OVERDUE_NOTIFICATION_MESSAGE);
        return String.format(messagePattern, bookLoan.getUser().getFullName(), bookLoan.getBook().toString());
    }


}
