//@@author philos22
package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.Calendar;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";
    public static final String COMMAND_ALIAS = "cal";
    public static final String ERROR_MESSAGE = "calendar execution failed";
    public static final int MIN_SECURITY_LEVEL = 1;

    @Override
    public CommandResult execute() {
        try {
            String messagesuccess = new Calendar().listEvents();
            return new CommandResult(messagesuccess);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommandResult(ERROR_MESSAGE);
    }

    @Override
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }

}
