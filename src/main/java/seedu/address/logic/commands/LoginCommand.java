package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Logs in to social media platforms using the user's name and password.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException("Test");
    }
}
