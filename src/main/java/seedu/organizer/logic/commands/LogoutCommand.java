package seedu.organizer.logic.commands;

//@@author dominickenn
/**
 * Logout from organizer.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String COMMAND_ALIAS = "out";

    public static final String MESSAGE_LOGOUT_ACKNOWLEDGEMENT = "Logged out";

    @Override
    public CommandResult execute() {
        model.logout();
        history.clear();
        return new CommandResult(MESSAGE_LOGOUT_ACKNOWLEDGEMENT);
    }

}
