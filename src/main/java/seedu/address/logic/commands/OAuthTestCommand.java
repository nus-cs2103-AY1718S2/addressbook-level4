//@@author ifalluphill

package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.logic.OAuthManager;

/**
 * Opens a calendar window.
 */
public class OAuthTestCommand extends Command {

    public static final String COMMAND_WORD = "reauthenticate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tests OAuth.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_TEST_OAUTH = "Testing OAuth.";

    @Override
    public CommandResult execute() {
        try {
            OAuthManager.authorize();
            OAuthManager.addEvent();
        } catch (IOException e) {
            // Do nothing for now
        }

        return new CommandResult(MESSAGE_TEST_OAUTH);
    }
}

//@@author
