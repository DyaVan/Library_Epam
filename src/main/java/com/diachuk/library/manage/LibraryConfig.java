package com.diachuk.library.manage;

import com.diachuk.library.commands.*;
import com.diachuk.library.commands.NEW.CommandAddBook;
import com.diachuk.library.commands.NEW.CommandEditBook;
import com.diachuk.library.commands.clients.*;
import com.diachuk.library.commands.free.*;
import com.diachuk.library.commands.workers.*;
import com.diachuk.library.controller.ControllerHelper;
import com.diachuk.library.dao.entities.Config;
import com.diachuk.library.dao.implementations.MySql.MySqlConfigDAO;
import com.diachuk.library.input.validators.*;
import com.diachuk.library.services.SessionManagerService;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.diachuk.library.services.SessionManagerService.*;

/**
 * Created by VA-N_ on 22.01.2017.
 */
public class LibraryConfig {

    private static Config maxBooksToGive;
    private static Config maxDurationOfBookLoan;
    private static Config maxHomeReservationDuration;
    private static Config maxRRoomReservationDuration;
    private static Config questionLifeTime;
    private static Config newsLifeTime;

    private static LibraryConfig instance;

    public static ArrayList<String> genresList = new ArrayList<>();


    public static LibraryConfig getInstance() {
        if (instance == null) {
            return instance = new LibraryConfig();
        }
        return instance;
    }

    private LibraryConfig() {
        fillGenresList();
    }

    private void fillGenresList() {
        genresList.add("history");
        genresList.add("poetry");
        genresList.add("fiction");
        genresList.add("science");
        genresList.add("classics");
        genresList.add("business");
        genresList.add("programming");
        genresList.add("romance");
        genresList.add("contemporary");
        genresList.add("dystopia");
        genresList.add("horror");
        genresList.add("non fiction");
        genresList.add("economics");
        genresList.add("philosophy");
        genresList.add("fantasy");
        genresList.add("childrens");
    }

    public void fillCommandsMapping() {

        //------ WORKERS --------
        ControllerHelper.commands.put(
                "answerQuestion", new CommandWrapper(new CommandAnswerQuestion(), QUESTION_ANSWERING_ACCESS, QuestionAnswerInputValidator.class));
        ControllerHelper.commands.put(
                "badQuestionVSBan", new CommandWrapper(new CommandBadQuestionVSBan(), QUESTION_ANSWERING_ACCESS, QuestionIdInputValidator.class));
        ControllerHelper.commands.put(
                "badQuestionVSDelete", new CommandWrapper(new CommandBadQuestionVSDelete(), QUESTION_ANSWERING_ACCESS, QuestionIdInputValidator.class));
        ControllerHelper.commands.put(
                "changeRole", new CommandWrapper(new CommandChangeRole(), ADMIN_ACCESS, RoleModifyingInputValidator.class));
        ControllerHelper.commands.put(
                "changeUserRole", new CommandWrapper(new CommandChangeUserRole(), ADMIN_ACCESS, UserRoleChangingInputValidator.class));
        ControllerHelper.commands.put(
                "changeDeleteRole", new CommandWrapper(new CommandDeleteRole(), ADMIN_ACCESS, RoleIdInputValidator.class));
        ControllerHelper.commands.put(
                "deleteUserQuestion", new CommandWrapper(new CommandDeleteUserQuestion(), NO_HOME_LOANS_CLIENT_ACCESS, QuestionIdInputValidator.class));
        ControllerHelper.commands.put(
                "findBookLoanVSUser", new CommandWrapper(new CommandFindBookLoanVSUser(), LIBRARIAN_ACCESS, BookLoanIdInputValidator.class));
        ControllerHelper.commands.put(
                "findReservationVSUser", new CommandWrapper(new CommandFindReservationVSUser(), LIBRARIAN_ACCESS, ReservationIdInputValidator.class));
        ControllerHelper.commands.put(
                "getQuestions", new CommandWrapper(new CommandGetQuestions(), QUESTION_ANSWERING_ACCESS));
        ControllerHelper.commands.put(
                "goToWorkSpace", new CommandWrapper(new CommandGoToWorkSpace(), QUESTION_ANSWERING_ACCESS));
        ControllerHelper.commands.put(
                "issueBook", new CommandWrapper(new CommandIssueBook(), LIBRARIAN_ACCESS, ReservationIdInputValidator.class));
        ControllerHelper.commands.put(
                "returnBook", new CommandWrapper(new CommandReturnBook(), LIBRARIAN_ACCESS, BookLoanIdInputValidator.class));
        ControllerHelper.commands.put(
                "addBook", new CommandWrapper(new CommandAddBook(), LIBRARIAN_ACCESS, BookAddInputValidator.class));
        ControllerHelper.commands.put(
                "editBook", new CommandWrapper(new CommandEditBook(), LIBRARIAN_ACCESS, BookEditInputValidator.class));

        //------ FREE --------
        ControllerHelper.commands.put(
                "getTop10", new CommandWrapper(new CommandGetTop10(), FREE_ACCESS, GenreInputValidator.class));
        ControllerHelper.commands.put(
                "goToMySpace", new CommandWrapper(new CommandGoToMySpace(), CLIENT_ACCESS));
        ControllerHelper.commands.put(
                "goToSpecificBook", new CommandWrapper(new CommandGoToSpecificBook(), FREE_ACCESS, BookIdInputValidator.class));
        ControllerHelper.commands.put(
                "logIn", new CommandWrapper(new CommandLogIn(), FREE_ACCESS));
        ControllerHelper.commands.put(
                "searchBooks", new CommandWrapper(new CommandSearchBooks(), FREE_ACCESS, GetBooksInputValidator.class));
        ControllerHelper.commands.put(
                "registration", new CommandWrapper(new CommandRegistration(), FREE_ACCESS, RegistrationInputValidator.class));

        //------ CLIENTS --------
        ControllerHelper.commands.put(
                "askQuestion", new CommandWrapper(new CommandAskQuestion(), CLIENT_ACCESS, QuestionAskInputValidator.class));
        ControllerHelper.commands.put(
                "changeProfile", new CommandWrapper(new CommandChangeProfile(), NO_HOME_LOANS_CLIENT_ACCESS, RegistrationInputValidator.class));
        ControllerHelper.commands.put(
                "getBooksRead", new CommandWrapper(new CommandGetBooksRead(), NO_HOME_LOANS_CLIENT_ACCESS, GetBooksInputValidator.class));
        ControllerHelper.commands.put(
                "getBooksToRead", new CommandWrapper(new CommandGetBooksToRead(), NO_HOME_LOANS_CLIENT_ACCESS, GetBooksInputValidator.class));
        ControllerHelper.commands.put(
                "getUserCurrents", new CommandWrapper(new CommandGetUserCurrents(), NO_HOME_LOANS_CLIENT_ACCESS));
        ControllerHelper.commands.put(
                "getUserHistory", new CommandWrapper(new CommandGetUserHistory(), NO_HOME_LOANS_CLIENT_ACCESS));
        ControllerHelper.commands.put(
                "getUserQuestions", new CommandWrapper(new CommandGetUserQuestions(), NO_HOME_LOANS_CLIENT_ACCESS));
        ControllerHelper.commands.put(
                "logOut", new CommandWrapper(new CommandLogOut(), SessionManagerService.NO_HOME_LOANS_CLIENT_ACCESS));
        ControllerHelper.commands.put(
                "requestBook", new CommandWrapper(new CommandRequestBook(), CLIENT_ACCESS, BookIdInputValidator.class));
        ControllerHelper.commands.put(
                "reserveForHome", new CommandWrapper(new CommandReserveForHome(), CLIENT_ACCESS, HomeReserveInputValidator.class));
        ControllerHelper.commands.put(
                "reserveForRRoom", new CommandWrapper(new CommandReserveForRRoom(), NO_HOME_LOANS_CLIENT_ACCESS, BookIdInputValidator.class));

    }

    public void refreshLibraryConfig() {
        try {
            maxDurationOfBookLoan = MySqlConfigDAO.getInstance().getMaxDurationOfBookLoan();
            maxBooksToGive = MySqlConfigDAO.getInstance().getMaxBooksToGive();
            maxHomeReservationDuration = MySqlConfigDAO.getInstance().getMaxHomeReservationDuration();
            maxRRoomReservationDuration = MySqlConfigDAO.getInstance().getMaxRRoomReservationDuration();
            questionLifeTime = MySqlConfigDAO.getInstance().getQuestionLifetime();
            newsLifeTime = MySqlConfigDAO.getInstance().getNewsLifetime();
        } catch (SQLException e) {
            //todo loging
            e.printStackTrace();
        }
    }

    public Integer getMaxBooksToGive() {
        return maxBooksToGive.getValue();
    }

    public Integer getMaxDurationOfBookLoan() {
        return maxDurationOfBookLoan.getValue();
    }

    public Integer getMaxHomeReservationDuration() {
        return maxHomeReservationDuration.getValue();
    }

    public Integer getMaxRRoomReservationDuration() {
        return maxRRoomReservationDuration.getValue();
    }

    public Integer getQuestionLifeTime() {
        return questionLifeTime.getValue();
    }

    public Integer getNewsLifeTime() {
        return newsLifeTime.getValue();
    }
}
