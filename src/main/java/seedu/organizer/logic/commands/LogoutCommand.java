package seedu.organizer.logic.commands;

import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;

//@@author dominickenn
/**
 * Logout current user from PrioriTask.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String COMMAND_ALIAS = "out";

    public static final String MESSAGE_LOGOUT_ACKNOWLEDGEMENT = "Logged out";

    @Override
    public CommandResult execute() {
        requireAllNonNull(model, history);
        model.logout();
        history.clear();
        return new CommandResult(MESSAGE_LOGOUT_ACKNOWLEDGEMENT);
    }
}
