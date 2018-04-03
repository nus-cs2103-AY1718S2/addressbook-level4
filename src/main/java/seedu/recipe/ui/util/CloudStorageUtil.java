//@@author nicholasangcx
package seedu.recipe.ui.util;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.google.common.base.Strings;

import seedu.recipe.commons.util.FileUtil;
import seedu.recipe.logic.commands.UploadCommand;

/**
 * Contains data and methods needed for cloud storage
 * (uploading to dropbox) purposes.
 */
public class CloudStorageUtil {

    public static final String RECIPE_DATA_FOLDER = FileUtil.getPath("data/");
    public static final File RECIPE_BOOK_FILE = new File(RECIPE_DATA_FOLDER + "recipebook.xml");
    public static final String CLIENT_IDENTIFIER = "dropbox/recirecipe";
    public static final String ACCESS_TOKEN_IDENTIFIER = "#access_token=";

    private static final String APP_KEY = "0kj3cb9w27d66n8";
    private static final String REDIRECT_DOMAIN = "https://www.dropbox.com/h";
    private static final String AUTHORIZATION_DOMAIN = "https://www.dropbox.com/1/oauth2/authorize?";
    private static final String AUTHORIZATION_URL = AUTHORIZATION_DOMAIN + "response_type=token&client_id="
                                                    + APP_KEY + "&redirect_uri=" + REDIRECT_DOMAIN;

    private static final String ACCESS_TOKEN_REGEX = REDIRECT_DOMAIN + "#access_token=(.+)&token(.*)";
    private static final String EXTRACT_PORTION = "$1";

    private static String accessToken = null;
    private static String uploadFilename = null;

    /**
     * Returns true if CloudStorageUtil already has an access token.
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

    /**
     * Creates a Dropbox client with the user's {@code getAccessToken()}
     * and uploads file specified by {@code RECIPE_BOOK_FILE} to their Dropbox account
     *
     * @throws DbxException
     */
    public static void upload() {
        // Ensures access token has been obtained
        requireNonNull(CloudStorageUtil.getAccessToken());

        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder(CLIENT_IDENTIFIER).build();
        DbxClientV2 client = new DbxClientV2(config, CloudStorageUtil.getAccessToken());

        // Upload "recipebook.xml" to Dropbox
        try (InputStream in = new FileInputStream(RECIPE_BOOK_FILE)) {
            client.files().uploadBuilder("/" + uploadFilename)
                    .withAutorename(true)
                    .uploadAndFinish(in);
            System.out.println("File has been uploaded");
        } catch (IOException | DbxException e) {
            throw new AssertionError(UploadCommand.MESSAGE_FAILURE + " Invalid access token.");
        }
    }

    /**
     * Checks is the access token has already been obtained by the app.
     * @return Returns the appropriate URL depending on whether authorization
     * has taken place yet.
     */
    public static String getAppropriateUrl() {
        if (Strings.isNullOrEmpty(accessToken)) {
            return AUTHORIZATION_URL;
        }
        return REDIRECT_DOMAIN;
    }

    public static void setAccessToken(String token) {
        accessToken = token;
    }

    private static String getAccessToken() {
        return accessToken;
    }

    public static void setUploadFilename(String uploadFilename) {
        CloudStorageUtil.uploadFilename = uploadFilename;
    }

    public static String getRedirectDomain() {
        return REDIRECT_DOMAIN;
    }
}
//@@author
