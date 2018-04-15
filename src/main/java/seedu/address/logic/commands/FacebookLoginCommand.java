package seedu.address.logic.commands;

import com.restfb.DefaultFacebookClient;
import com.restfb.Version;
import com.restfb.types.User;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.ToggleFacebookPanelEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author tiny767
/**
 * Connects the address book to a personal Facebook account.
 */
public class FacebookLoginCommand extends Command {
    public static final String COMMAND_WORD = "facebooklogin";
    public static final String COMMAND_ALIAS = "fblogin";
    public static final String MESSAGE_LOGIN_INIT = "Initiating authentication. "
            + "Please log into your Facebook account.";
    public static final String MESSAGE_SUCCESS = "You are logged in to Facebook";
    public static final String MESSAGE_FAILURE = "Error in Facebook authorisation";
    public static final String FACEBOOK_DOMAIN = "https://www.facebook.com/";
    private static final String FACEBOOK_APP_ID = "199997423936335";

    private static final String FACEBOOK_PERMISSIONS = "user_about_me,email,publish_actions,user_birthday,"
            + "user_education_history,user_friends,user_games_activity,user_hometown,user_likes,"
            + "user_location,user_photos,user_posts,user_relationship_details,user_relationships,"
            + "user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history,"
            + "ads_management,ads_read,business_management,manage_pages,pages_manage_cta,publish_pages,"
            + "user_actions.books,user_actions.fitness,read_custom_friendlists,"
            + "pages_messaging_phone_number,pages_messaging_subscriptions,pages_show_list,user_actions.music,"
            + "user_actions.news,rsvp_event,user_events,user_managed_groups,"
            + "pages_manage_instant_articles,user_actions.video,instagram_basic,instagram_manage_comments,"
            + "instagram_manage_insights,read_audience_network_insights,read_insights";

    private static final String FACEBOOK_AUTH_URL =
            "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + FACEBOOK_APP_ID
                    + "&redirect_uri=" + FACEBOOK_DOMAIN + "&scope=" + FACEBOOK_PERMISSIONS;

    private static WebEngine webEngine;

    private static String accessToken;
    private static String authenticatedUsername;
    private static String authenticatedUserId;
    private static boolean isAuthenticated = false;
    private static String authenticatedUserPage;


    private static DefaultFacebookClient fbClient;
    private static User user;

    public static DefaultFacebookClient getFbClient() {
        return fbClient;
    }

    public static String getAuthenticatedUsername() {
        return authenticatedUsername;
    }

    public static WebEngine getWebEngine() {
        return webEngine;
    }

    public static String getAuthenticatedUserPage() {
        return authenticatedUserPage;
    }

    public static boolean getAuthenticateState() {
        return isAuthenticated;
    }


    /**
     * Sets a WebEngine
     */
    public static void setWebEngine(WebEngine webEngine) {
        FacebookLoginCommand.webEngine = webEngine;
    }

    /**
     * Completes the authentication process by retrieving the token and setting up username and id
     */
    public static void completeAuth(String urlWithToken) throws CommandException {

        accessToken = urlWithToken.replaceAll(".*#access_token=(.+)&.*", "$1");

        fbClient = new DefaultFacebookClient(accessToken, Version.LATEST);
        user = fbClient.fetchObject("me", User.class);

        try {
            authenticatedUsername = user.getName();
            authenticatedUserId = user.getId();
        } catch (Exception e) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        if (accessToken != null) {
            isAuthenticated = true;
            authenticatedUserPage = FACEBOOK_DOMAIN + authenticatedUserId;
            EventsCenter.getInstance().post(new NewResultAvailableEvent(
                    MESSAGE_SUCCESS + "\n" + "User name: " + authenticatedUsername));
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    @Override
    public CommandResult execute() throws CommandException {

        EventsCenter.getInstance().post(new ToggleFacebookPanelEvent());

        try {
            Platform.runLater(() -> webEngine.load(FACEBOOK_AUTH_URL));
        } catch (Exception e) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(MESSAGE_LOGIN_INIT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FacebookLoginCommand); // instanceof handles nulls
    }
}
