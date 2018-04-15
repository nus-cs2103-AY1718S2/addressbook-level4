package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LoadGoogleLoginEvent;
import seedu.address.commons.events.ui.SwitchTabRequestEvent;
import seedu.address.logic.GoogleAuthentication;

//@@author KevinCJH

/**
 * Command to open the authentication/login page for google authentication
 */
public class GoogleLoginCommand extends Command {

    public static final String COMMAND_WORD = "googlelogin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Direct user to the login page for google authentication.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Please log in to Google.";

    public static final int TAB_ID = 4;

    private final GoogleAuthentication googleAuthentication = new GoogleAuthentication();

    @Override
    public CommandResult execute() {
        String authenticationUrl = googleAuthentication.getAuthenticationUrl();
        EventsCenter.getInstance().post(new LoadGoogleLoginEvent(authenticationUrl));
        EventsCenter.getInstance().post(new SwitchTabRequestEvent(TAB_ID));

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
