package seedu.recipe.ui.util;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.google.common.base.Strings;

import seedu.recipe.commons.util.FileUtil;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.exceptions.UploadCommandException;

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

    private static String accessToken = "";

    /**
     * Creates a Dropbox client with the user's {@code getAccessToken()}
     * and uploads file specified by {@code RECIPE_BOOK_FILE} to their Dropbox account
     * @return {@code CommandResult}
     * @throws DbxException
     */
    private CommandResult upload() throws UploadCommandException, IOException {
        // Ensures access token has been obtained
        requireNonNull(CloudStorageUtil.getAccessToken());

        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder(CLIENT_IDENTIFIER).build();
        DbxClientV2 client = new DbxClientV2(config, CloudStorageUtil.getAccessToken());

        // Upload "recipebook.xml" to Dropbox
        try (InputStream in = new FileInputStream(RECIPE_BOOK_FILE)) {
            client.files().uploadBuilder("/" + xmlExtensionFilename)
                    .withAutorename(true)
                    .uploadAndFinish(in);
        } catch (IOException e) {
            throw new IOException(MESSAGE_FAILURE);
        } catch (DbxException dbe) {
            throw new UploadCommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        return new CommandResult(MESSAGE_SUCCESS);
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

    public static String getAccessToken() {
        return accessToken;
    }

    public static String getRedirectDomain() {
        return REDIRECT_DOMAIN;
    }
}
