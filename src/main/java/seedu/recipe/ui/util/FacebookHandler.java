//@@author RyanAngJY
package seedu.recipe.ui.util;

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

    private final String ACCESS_TOKEN_REGEX = REDIRECT_DOMAIN + "#access_token=(.+)&.*";
    private final String ACCESS_TOKEN_IDENTIFIER = "#access_token=";
    private final String POST_TYPE_MESSAGE = "message";
    private final String USER = "me";
    private final String USER_FEED = "me/feed";

    private String accessToken = null;

    /**
     * Returns true if FacebookHandler already has an access token.
     */
    public boolean hasAccessToken() {
        if (accessToken == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Posts a recipe directly onto the User's Facebook wall.
     */
    public void postRecipeOnFacebook(Recipe recipeToShare) {
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
    public void setAccessToken(String url) {
        accessToken = url.replaceAll(ACCESS_TOKEN_REGEX, "$1");
    }

    /**
     * Checks if an access token is embedded in the url.
     * If access token is found, set the accessToken variable to be the found access token.
     */
    public boolean checkAndSetAccessToken(String url) {
        if (url.contains(ACCESS_TOKEN_IDENTIFIER)) {
            setAccessToken(url);
            return true;
        } else {
            return false;
        }
    }
}
//@@author
