//@@author ifalluphill

package seedu.address.logic.commands;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import seedu.address.logic.OAuthManager;

/**
 * Opens a calendar window.
 */
public class OAuthTestCommand extends Command {

    public static final String COMMAND_WORD = "reauthenticate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tests OAuth certificate.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_TEST_OAUTH = "Tested OAuth certificate.";

    @Override
    public CommandResult execute() {
        try {
            OAuthManager.authorize();
        } catch (IOException e) {
            // Do nothing for now
        }

        return new CommandResult(MESSAGE_TEST_OAUTH);
    }
}

//@@author
