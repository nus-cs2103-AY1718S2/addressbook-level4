package seedu.recipe.ui.util;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuth;

public class DropboxUtil {

    private static final String APP_KEY = "0kj3cb9w27d66n8";
    private static final String APP_SECRET = "7stnncfsyvgim60";

    private String authorizeUrl;

    public void getDbAuthorization() {
        // Read app info file (contains app key and app secret)
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        // Run through Dropbox API authorization process
        DbxRequestConfig requestConfig = new DbxRequestConfig("ReciRecipe");
        DbxWebAuth webAuth = new DbxWebAuth(requestConfig, appInfo);

        DbxWebAuth.Request webAuthRequest = DbxWebAuth.newRequestBuilder()
                .withNoRedirect()
                .withForceReapprove(Boolean.FALSE)
                .build();

        this.authorizeUrl = webAuth.authorize(webAuthRequest);
    }

    public String getAuthorizeUrl() {
        return this.authorizeUrl;
    }

}
