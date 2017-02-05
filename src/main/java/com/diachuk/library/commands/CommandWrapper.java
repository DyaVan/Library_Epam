package com.diachuk.library.commands;

/**
 * Created by VA-N_ on 20.01.2017.
 */
public class CommandWrapper {

    private ICommand command;
    private int accessLevel;
    private Class inputValidator ;

    public CommandWrapper(ICommand command, int accessLevel) {
        this.command = command;
        this.accessLevel = accessLevel;
    }

    public CommandWrapper(ICommand command, int accessLevel, Class inputValidator) {
        this.command = command;
        this.accessLevel = accessLevel;
        this.inputValidator = inputValidator;
    }

    public ICommand getCommand() {
        return command;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public Class getInputValidator() {
        return inputValidator;
    }
}
