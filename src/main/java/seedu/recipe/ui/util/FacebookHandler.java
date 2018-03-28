//@@author RyanAngJY
package seedu.recipe.ui.util;

import static java.util.Objects.requireNonNull;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

import seedu.recipe.model.recipe.Recipe;

/**
 * Manages Facebook related commands and execution
 */
public class FacebookHandler {

    public static final String ACCESS_TOKEN_IDENTIFIER = "#access_token=";
    public static final String REDIRECT_DOMAIN = "https://www.facebook.com/connect/login_success.html";
    private static final String ACCESS_RIGHTS = "publish_actions";
    private static final String APP_ID = "177615459696708";
    private static final String AUTHENTICATION_URL = "https://graph.facebook.com/oauth/authorize?type=user_agent"
            + "&client_id=" + APP_ID + "&redirect_uri=" + REDIRECT_DOMAIN + "&scope=" + ACCESS_RIGHTS;

    private static final String ACCESS_TOKEN_REGEX = REDIRECT_DOMAIN + "#access_token=(.+)&.*";
    private static final String EXTRACT_PORTION = "$1";

    private static final String POST_TYPE_MESSAGE = "message";
    private static final String USER = "me";
    private static final String USER_FEED = "me/feed";

    private static String accessToken = null;

    /**
     * Returns true if FacebookHandler already has an access token.
     */
    public static boolean hasAccessToken() {
        if (accessToken == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Posts a recipe directly onto the User's Facebook wall.
     */
    public static void postRecipeOnFacebook(Recipe recipeToShare) {
        requireNonNull(recipeToShare);
        Version apiVersion = Version.VERSION_2_12;
        if (hasAccessToken()) {
            FacebookClient fbClient = new DefaultFacebookClient(accessToken, apiVersion);
            fbClient.fetchObject(USER, User.class);
            fbClient.publish(USER_FEED, FacebookType.class,
                    Parameter.with(POST_TYPE_MESSAGE, recipeToShare.getName().toString()));
        }
    }

    /**
     * Checks if an access token is embedded in the url.
     * If access token is found, set the accessToken variable to be the found access token.
     *
     * @return Returns true when access token is found.
     */
    public static boolean checkAndSetAccessToken(String url) {
        if (url.contains(ACCESS_TOKEN_IDENTIFIER)) {
            String token = extractAccessToken(url);
            setAccessToken(token);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Extracts access token from the given URL.
     */
    public static String extractAccessToken(String url) {
        assert (url.contains(ACCESS_TOKEN_IDENTIFIER));
        return url.replaceAll(ACCESS_TOKEN_REGEX, EXTRACT_PORTION);
    }

    public static void setAccessToken(String token) {
        accessToken = token;
    }

    public static String getAuthenticationUrl() {
        return AUTHENTICATION_URL;
    }
}
//@@author
