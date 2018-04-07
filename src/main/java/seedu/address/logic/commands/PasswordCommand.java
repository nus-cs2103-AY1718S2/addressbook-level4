package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.logic.commands.commandmode.PasswordMode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.storage.PasswordManger;

//@@author limzk1994
public class PasswordCommand extends Command {

    public static final String COMMAND_WORD = "password";
    public static final String COMMAND_WORD_ALIAS = "pw";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set, change or remove password\n"
            + "Set Password Parameters:" + COMMAND_WORD + " set/yourchosenpassword\n"
            + "Change Password Parameters:" + COMMAND_WORD + " change/yournewpassword\n"
            + "Remove Password Parameters: " + COMMAND_WORD + " remove/youroldpassword\n";

    public static final String MESSAGE_SUCCESS = "Password set!";
    public static final String MESSAGE_PASSWORD_CHANGE = "Password successfully changed!";
    public static final String MESSAGE_PASSWORD_EXISTS = "Password already exists!";
    public static final String MESSAGE_PASSWORD_REMOVE = "Password removed!";
    public static final String MESSAGE_NO_PASSWORD_EXISTS = "No password!";
    public static final String MESSAGE_WRONG_PASSWORD = "Wrong password!";

    private final PasswordMode mode;

    /**
     * Creates an PasswordCommand
     */
    public PasswordCommand(PasswordMode mode) {
        this.mode = mode;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            return mode.execute();
        } catch (IOException ioe) {
            throw new CommandException("Password File not found");
        }

    }

    /**
     * Set password if it does not exists
     */
    public static class setPassword extends PasswordMode {
        public setPassword(String password) {
            super(password);
        }

        @Override
        public CommandResult execute() throws IOException, CommandException {
            if (passExists()) {
                throw new CommandException(MESSAGE_PASSWORD_EXISTS);
            } else {
                PasswordManger.savePassword(getPass());
                return new CommandResult(MESSAGE_SUCCESS);
            }
        }
    }

    /**
     * Removes password if it exists
     */
    public static class clearPassword extends PasswordMode {
        public clearPassword(String password) {
            super(password);
        }

        @Override
        public CommandResult execute() throws IOException, CommandException {
            if (passExists()) {
                try {
                    PasswordManger.removePassword(getPass());
                } catch (WrongPasswordException e) {
                    throw new CommandException(MESSAGE_WRONG_PASSWORD);
                }
                return new CommandResult(MESSAGE_PASSWORD_REMOVE);
            } else {
                throw new CommandException(MESSAGE_NO_PASSWORD_EXISTS);
            }
        }
    }

    /**
     * Changes password if it exists
     */
    public static class changePassword extends PasswordMode {

        private String newPass;
        public changePassword(String newPassword) {
            super(newPassword);
            newPass = newPassword;
        }

        @Override
        public CommandResult execute() throws IOException {
            if (passExists()) {
                PasswordManger.savePassword(newPass);
                return new CommandResult(MESSAGE_PASSWORD_CHANGE);
            } else {
                return new CommandResult(MESSAGE_NO_PASSWORD_EXISTS);
            }
        }
    }
}
