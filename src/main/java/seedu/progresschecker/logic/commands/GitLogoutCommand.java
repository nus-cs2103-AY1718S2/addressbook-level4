package seedu.progresschecker.logic.commands;

import seedu.progresschecker.logic.commands.exceptions.CommandException;

//@@author adityaa1998
/**
 * Logs out of github
 */
public class GitLogoutCommand extends Command {

    public static final String COMMAND_WORD = "gitlogout";
    public static final String COMMAND_ALIAS = "glo";
    public static final String COMMAND_FORMAT = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "You have successfully logged out of github!";
    public static final String MESSAGE_FAILURE = "You are currently not logged in";

    @Override
    public CommandResult execute() throws CommandException {

        try {
            model.logoutGithub();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (CommandException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

}
