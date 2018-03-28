package seedu.recipe.ui.util;

import java.io.File;

import com.google.common.base.Strings;

import seedu.recipe.commons.util.FileUtil;

/**
 * Contains data and methods needed for cloud storage
 * (uploading to dropbox) purposes.
 */
public class CloudStorageUtil {

    public static final String RECIPE_DATA_FOLDER = FileUtil.getPath("data/");
    public static final File RECIPE_BOOK_FILE = new File(RECIPE_DATA_FOLDER + "recipebook.xml");
    public static final String CLIENT_IDENTIFIER = "dropbox/recirecipe";

    private static final String APP_KEY = "0kj3cb9w27d66n8";
    private static final String APP_SECRET = "7stnncfsyvgim60";
    private static final String REDIRECT_DOMAIN = "https://www.dropbox.com/h";
    private static final String AUTHORIZATION_URL = "https://www.dropbox.com/1/oauth2/authorize?"
                                + "response_type=token&client_id=" + APP_KEY + "&redirect_uri="
                                + REDIRECT_DOMAIN;

    private static String ACCESS_TOKEN = "";

    public static String getAccessToken() {
        return ACCESS_TOKEN;
    }

    public static void setAccessToken(String accessToken) {
        ACCESS_TOKEN = accessToken;
    }

    public static String getRedirectDomain() {
        return REDIRECT_DOMAIN;
    }

    public static String getAppropriateUrl() {
        if (Strings.isNullOrEmpty(ACCESS_TOKEN)) {
            return AUTHORIZATION_URL;
        }
        return REDIRECT_DOMAIN;
    }
}
