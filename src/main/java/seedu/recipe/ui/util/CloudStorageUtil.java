package seedu.recipe.ui.util;

import java.io.File;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuth;

import seedu.recipe.commons.util.FileUtil;

public class CloudStorageUtil {

    public static final String APP_KEY = "0kj3cb9w27d66n8";
    public static final String APP_SECRET = "7stnncfsyvgim60";
    public static final String ACCESS_TOKEN = "";

    public static final String RECIPE_DATA_FOLDER = FileUtil.getPath("data/");
    public static final File RECIPE_BOOK_FILE = new File(RECIPE_DATA_FOLDER + "recipebook.xml");
    public static final String clientIdentifier = "dropbox/recirecipe";

    private static String authorizationUrl;

    public static void getDbAuthorization() {
        // Read app info file (contains app key and app secret)
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        // Run through Dropbox API authorization process
        DbxRequestConfig requestConfig = new DbxRequestConfig("ReciRecipe");
        DbxWebAuth webAuth = new DbxWebAuth(requestConfig, appInfo);

        DbxWebAuth.Request webAuthRequest = DbxWebAuth.newRequestBuilder()
                .withNoRedirect()
                .withForceReapprove(Boolean.FALSE)
                .build();

        authorizationUrl = webAuth.authorize(webAuthRequest);
    }
}
