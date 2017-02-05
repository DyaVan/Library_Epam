package com.diachuk.library.commands.clients;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.controller.LibraryServlet;
import com.diachuk.library.dao.entities.*;
import com.diachuk.library.manage.Message;
import com.diachuk.library.manage.NavigationManager;
import com.diachuk.library.services.JsonResponse;
import com.diachuk.library.services.SessionManagerService;
import com.diachuk.library.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by VA-N_ on 25.01.2017.
 */
public class CommandGetUserCurrents implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        SessionManagerService sessionService = new SessionManagerService(request);
        User currentUser = sessionService.getCurrentUser();

        UserService userService = new UserService();
        ArrayList<IReservation> userCurrentReservations = userService.getUserCurrentReservations(currentUser);
        ArrayList<Request> userCurrentRequests = userService.getUserCurrentRequests(currentUser);
        ArrayList<BookLoan> userCurrentBookLoans = userService.getUserCurrentBookLoans(currentUser);

        String jsonResponse = userService.buildJsonResponse().extractJsonString();

        LibraryServlet.sendJsonResponse(jsonResponse,response);
    }

    public static void main(String[] args) {
        ArrayList<Object> userCurrentReservations = new ArrayList<>();
        HomeReservation hr = new HomeReservation();
        hr.setId(1);
        hr.setDueDate(new Date());
        hr.setUserId(2);
        hr.setBookId(2);
        userCurrentReservations.add(hr);
        userCurrentReservations.add(hr);
        ArrayList<Object> userCurrentRequests = new ArrayList<>();
        Request rq = new Request();
        rq.setId(1);
        rq.setRequestDate(new Date());
        rq.setUserId(2);
        rq.setBookId(2);
        userCurrentRequests.add(rq);
        userCurrentRequests.add(rq);

        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        Gson gson2 = new GsonBuilder().setDateFormat("dd-MM-yyyy ").create();

//
//        JsonObject rootObject = new JsonObject();
//        rootObject.add("userCurrentReservations",gson.toJsonTree(userCurrentReservations));
//        rootObject.add("userCurrentRequests",gson2.toJsonTree(userCurrentRequests));
//        rootObject.add("property",gson.toJsonTree(true));

//
//        System.out.println(gson.toJson(true));
//        System.out.println(gson.toJson(rootObject));
//
//        int a = Integer.MAX_VALUE ;
//        System.out.println(a+1);
//
//        System.out.println(a*2);
//
//        JsonResponse jsonResponse = new JsonResponse();
//        jsonResponse.setSuccessFlag(true);
////        jsonResponse.setErrorMessage("");
//        jsonResponse.setNotificationMessage("Cool");
//        jsonResponse.setSuccessMessage("Error messageEmpty");
//        jsonResponse.addDataObject("userCurrentReservations",userCurrentReservations,gson);
//        jsonResponse.addDataObject("userCurrentRequests",userCurrentRequests,gson2);

//        System.out.println(jsonResponse.extractJsonString());

    }

}
