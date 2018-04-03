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
    private static final String AUTHORIZATION_DOMAIN = "https://www.dropbox.com/1/oauth2/authorize?";
    private static final String AUTHORIZATION_URL = AUTHORIZATION_DOMAIN + "response_type=code&client_id="
                                                    + APP_KEY + "&redirect_uri=";

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

    public static void setAccessToken(String token) {
        accessToken = token;
    }

    private static String getAccessToken() {
        return accessToken;
    }

    public static void setUploadFilename(String uploadFilename) {
        CloudStorageUtil.uploadFilename = uploadFilename;
    }

    public static String getAuthorizationUrl() {
        return AUTHORIZATION_URL;
    }
}
//@@author
