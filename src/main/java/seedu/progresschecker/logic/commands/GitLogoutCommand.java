package seedu.progresschecker.logic.commands;

//@@author adityaa1998

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_GIT_PASSCODE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_GIT_REPO;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_GIT_USERNAME;

import java.io.IOException;

import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.credentials.GitDetails;

/**
 * Logins into github from app for issue creation
 */
public class GitLogoutCommand extends Command {

    public static final String COMMAND_WORD = "gitlogout";
    public static final String COMMAND_ALIAS = "glo";
    public static final String COMMAND_FORMAT = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "You have successfully logged out of github!";
    public static final String MESSAGE_FAILURE = "You haven't logged out yet";

    @Override
    public CommandResult execute() throws CommandException {

        try {
            model.logoutGithub();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

}
