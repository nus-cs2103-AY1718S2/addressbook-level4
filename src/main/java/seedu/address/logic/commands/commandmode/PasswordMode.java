package seedu.address.logic.commands.commandmode;

import java.io.IOException;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.storage.PasswordManger;

//@@author limzk1994-reused
public abstract class PasswordMode {

    private String pass;

    public PasswordMode(String pass){
        this.pass = pass;
    }

    public String getPass(){
        return pass;
    }

    protected boolean passExists(){
        return PasswordManger.passwordExists();
    }

    public abstract CommandResult execute() throws IOException, CommandException;
}
