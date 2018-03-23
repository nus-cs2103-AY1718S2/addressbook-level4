//@@author RyanAngJY
package seedu.recipe.ui.util;

import static java.util.Objects.requireNonNull;

import static seedu.recipe.ui.BrowserPanel.REDIRECT_DOMAIN;

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
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, apiVersion);
        fbClient.fetchObject(USER , User.class);
        fbClient.publish(USER_FEED, FacebookType.class,
                Parameter.with(POST_TYPE_MESSAGE, recipeToShare.getName().toString()));
    }

    /**
     * Extracts access token from the given URL.
     *
     * @param url Url should contain embedded access token.
     */
    public static void setAccessToken(String url) {
        assert(url.contains(ACCESS_TOKEN_IDENTIFIER));
        accessToken = url.replaceAll(ACCESS_TOKEN_REGEX, EXTRACT_PORTION);
    }

    /**
     * Checks if an access token is embedded in the url.
     * If access token is found, set the accessToken variable to be the found access token.
     *
     * @return Returns true when access token is found.
     */
    public static boolean checkAndSetAccessToken(String url) {
        if (url.contains(ACCESS_TOKEN_IDENTIFIER)) {
            setAccessToken(url);
            return true;
        } else {
            return false;
        }
    }

    public static String getAccessToken() {
        return accessToken;
    }
}
//@@author
