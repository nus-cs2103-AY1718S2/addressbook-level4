package seedu.address.logic;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleBrowserClientRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.GmailScopes;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LoadGoogleLoginRedirectEvent;
import seedu.address.logic.commands.exceptions.GoogleAuthenticationException;

//@@author KevinCJH

/**
 * This class contains methods of the google Auth Api. For authentication after login.
 */
public class GoogleAuthentication {

    private static final List<String> SCOPES =
            Arrays.asList(GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_COMPOSE);

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport = new NetHttpTransport();

    private String clientId = "112256917735-poce4h6lutpunet3umse12o6c38phsng.apps.googleusercontent.com";
    private String clientSecret = "G65SxgADh3r9UxSTKFiUNEow";

    /** The url which user will be redirected to after logging in successfully. */
    private String redirectUrl = "https://www.google.com.sg";

    private String authenticationUrl;

    public GoogleAuthentication() {
        //Builds a Google OAuth2 URL
        this.authenticationUrl = new GoogleBrowserClientRequestUrl(clientId, redirectUrl, SCOPES).build();
    }

    //Get the OAuth URL for user to login
    public String getAuthenticationUrl() {
        return authenticationUrl;
    }

    /**
     * Gets the access token from the redirected URL after user login to google
     */
    public String getToken() throws GoogleAuthenticationException {
        String token;
        boolean validToken;
        try {
            LoadGoogleLoginRedirectEvent event = new LoadGoogleLoginRedirectEvent();
            EventsCenter.getInstance().post(event);
            String url = event.getRedirectUrl();
            //@@author KevinCJH-reused
            validToken = checkTokenIndex(url.indexOf("token="));
            if (validToken) {
                token = url.substring(url.indexOf("token=") + 6, url.indexOf("&"));
            } else {
                throw new StringIndexOutOfBoundsException();
            }
            //@@author KevinCJH
        } catch (Exception e) {
            throw new GoogleAuthenticationException("Google login has failed. Please try again.");
        }

        return token;
    }

    /**
     * Gets the google credentials from access token
     */
    public GoogleCredential getCredential(String token) throws IOException {

        GoogleTokenResponse googleToken = new GoogleTokenResponse();
        googleToken.setAccessToken(token);

        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setClientSecrets(clientId, clientSecret)
                .build()
                .setFromTokenResponse(googleToken);
        return credential;
    }

    /**
     * Checks if token from google login is valid
     * @returns true if index is not -1 or false if index is -1
     */
    public boolean checkTokenIndex(int index) {
        if (index == -1) {
            return false;
        }
        return true;
    }

    public static HttpTransport getHttpTransport() {
        return httpTransport;
    }

    public static JsonFactory getJsonFactory() {
        return JSON_FACTORY;
    }
}
