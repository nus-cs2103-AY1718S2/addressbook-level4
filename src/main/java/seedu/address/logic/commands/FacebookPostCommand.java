package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ToggleFacebookPanelEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author tiny767
/**
 * Posts a message to a personal Facebook account.
 */
public class FacebookPostCommand extends Command {
    public static final String COMMAND_WORD = "facebookpost";
    public static final String COMMAND_ALIAS = "fbpost";
    public static final String EXAMPLE_POST = "Good morning!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": make a post to Facebook wall \n"
            + "Alias: " + COMMAND_ALIAS + "\n"
            + "Parameters: MESSAGE\n"
            + "Example: " + COMMAND_WORD + " " + EXAMPLE_POST;

    public static final String MESSAGE_FACEBOOK_POST_SUCCESS = "Attempted to post to Facebook";
    public static final String MESSAGE_FACEBOOK_POST_LOGIN = "User has not been authenticated" + "\n"
            + "Please log in and then try posting again!";
    public static final String MESSAGE_FACEBOOK_POST_FAIL = "There was an error posting to Facebook";

    private static WebEngine webEngine;

    private String toPost;

    /**
     * Creates an FacebookPostCommand based on the message
     */
    public FacebookPostCommand(String message) {
        requireNonNull(message);
        toPost = message;
    }

    /**
     * Completes the post command
     */
    public void completePost() throws CommandException {

        DefaultFacebookClient fbClient = FacebookLoginCommand.getFbClient();
        try {
            fbClient.publish("me/feed", FacebookType.class, Parameter.with("message", toPost));
        } catch (Exception e) {
            e.printStackTrace();
            new CommandException(MESSAGE_FACEBOOK_POST_FAIL);
        }

        EventsCenter.getInstance().post(new ToggleFacebookPanelEvent());

        webEngine = FacebookLoginCommand.getWebEngine();
        Platform.runLater(() -> webEngine.load(FacebookLoginCommand.getAuthenticatedUserPage()));
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!FacebookLoginCommand.getAuthenticateState()) {
            FacebookLoginCommand fbLoginCommand = new FacebookLoginCommand();
            fbLoginCommand.execute();
            return new CommandResult(MESSAGE_FACEBOOK_POST_LOGIN);
        } else {
            completePost();
            return new CommandResult(MESSAGE_FACEBOOK_POST_SUCCESS
                +    "\nUsername: " + FacebookLoginCommand.getAuthenticatedUsername()
                +    "\nmessage: '" + toPost + "'");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FacebookPostCommand // instanceof handles nulls
                && toPost.equals(((FacebookPostCommand) other).toPost));
    }
}
