package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.account.PrivilegeLevel;

/**
 * Logs out as student or librarian.
 */

public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logout as student or librarian.\n"
        + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_LOGGED_OUT = "You are logged out.";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_STUDENT;

    @Override
    public CommandResult execute() {
        model.logout();
        return new CommandResult(MESSAGE_LOGGED_OUT);
    }

    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LogoutCommand);
    }
}
