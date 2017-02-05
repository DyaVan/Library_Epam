package com.diachuk.library.controller;

import com.diachuk.library.commands.CommandMissing;
import com.diachuk.library.commands.ICommand;
import com.diachuk.library.commands.CommandWrapper;
import com.diachuk.library.services.SessionManagerService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by VA-N_ on 05.01.2017.
 */
public class ControllerHelper {

    private static ControllerHelper instance = new ControllerHelper();

    //todo: make private when implement reflection
    public static HashMap<String, CommandWrapper> commands = new HashMap<>();

    public static CommandWrapper getCommandWrapper(HttpServletRequest request) {
        String test = request.getParameter("command");
        CommandWrapper commandWrapper = commands.get(request.getParameter("command"));
        if (commandWrapper == null) {
            commandWrapper = new CommandWrapper(new CommandMissing(), SessionManagerService.FREE_ACCESS) ;
        }
        return commands.get(request.getParameter("command"));
    }

    public static ICommand getCommand(HttpServletRequest request) {
        ICommand command = commands.get(request.getParameter("command")).getCommand();
        if (command == null) {
            command = new CommandMissing() ;
        }
        return command;
    }

    private ControllerHelper() {
    }

//    public ControllerHelper getInstance(){
//        if (instance == null) {
//            return instance = new ControllerHelper();
//        }
//        return instance;
//    }
}
