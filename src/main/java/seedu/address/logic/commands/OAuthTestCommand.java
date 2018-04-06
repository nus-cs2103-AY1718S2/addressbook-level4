//@@author ifalluphill

package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.logic.OAuthManager;
import seedu.address.model.login.User;

/**
 * Opens a calendar window.
 */
public class OAuthTestCommand extends Command {

    public static final String COMMAND_WORD = "reauthenticate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tests OAuth certificate.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_TEST_OAUTH = "Renewed OAuth certificate.";

    @Override
    public CommandResult execute() {
        User user = model.getLoggedInUser();

        try {
            OAuthManager.deleteOauthCert(user);
            OAuthManager.authorize(user);
            OAuthManager.addEventTest(user);
        } catch (IOException e) {
            // Do nothing for now
        }

        return new CommandResult(MESSAGE_TEST_OAUTH);
    }
}

//@@author
