//@@author RyanAngJY
package seedu.recipe.ui.util;

/**
 * Manages Facebook related commands and execution
 */
public class FacebookHandler {

    public static final String ACCESS_TOKEN_IDENTIFIER = "#access_token=";
    public static final String REDIRECT_DOMAIN = "https://www.facebook.com/";
    public static final String REDIRECT_EMBEDDED = "&redirect_uri=" + REDIRECT_DOMAIN;
    private static final String APP_ID = "177615459696708";
    public static final String POST_DOMAIN = "https://www.facebook.com/dialog/feed?%20app_id="
            + APP_ID + "&display=popup&amp;&link=";

    private static final String ACCESS_RIGHTS = "user_about_me";
    private static final String AUTH_URL = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id="
            + APP_ID + "&redirect_uri=" + REDIRECT_DOMAIN + "&scope=" + ACCESS_RIGHTS;

    private static final String ACCESS_TOKEN_REGEX = REDIRECT_DOMAIN + "#access_token=(.+)&.*";
    private static final String EXTRACT_PORTION = "$1";

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

    public static String getPostDomain() {
        return POST_DOMAIN;
    }

    public static String getRedirectEmbedded() {
        return REDIRECT_EMBEDDED;
    }
}
//@@author
