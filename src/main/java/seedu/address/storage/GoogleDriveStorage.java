package seedu.address.storage;

import static com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp.browse;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import seedu.address.commons.core.LogsCenter;
import seedu.address.storage.exceptions.GoogleAuthorizationException;
import seedu.address.storage.exceptions.RequestTimeoutException;

//@@author Caijun7-reused
/**
 * A class to read and write files stored in the user's Google Drive storage.
 */
public class GoogleDriveStorage {

    private static final String APPLICATION_NAME = "StardyTogether";

    /**
     * Directory to store user credentials.
     */
    private static java.io.File dataStoreDir =
            new java.io.File(System.getProperty("user.home"), ".google-credentials/google-drive-storage");
    private static String uploadFileFolder = "./googledrive/";
    private static String user = "user";
    private static FileDataStoreFactory dataStoreFactory;
    private static HttpTransport httpTransport;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static Credential credential;

    /**
     * Google Drive API client.
     */
    private static Drive drive;

    private static final Logger logger = LogsCenter.getLogger(GoogleDriveStorage.class);

    private final String uploadFilePath;
    private final java.io.File uploadFile;

    public GoogleDriveStorage(String uploadFilePath) throws GoogleAuthorizationException, RequestTimeoutException {
        this.uploadFilePath = uploadFileFolder + uploadFilePath;
        uploadFile = new java.io.File(this.uploadFilePath);
        userAuthorize();
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    /**
     * Sets the variables for test environment
     */
    public static void setTestEnvironment() {
        uploadFileFolder = "";
        user = "test";
        dataStoreDir = new java.io.File("./src/test/resources/GoogleCredentials/");
    }

    /**
     * Resets the variables for user environment
     */
    public static void resetTestEnvironment() {
        uploadFileFolder = "./googledrive/";
        user = "user";
        dataStoreDir = new java.io.File(System.getProperty("user.home"), ".google-credentials/google-drive-storage");
    }

    /**
     * Opens Google authentication link in user's default browser and request for authorization.
     * Sets up an instance of Google Drive API client after user authorized the application.
     *
     * @throws GoogleAuthorizationException When application is unable to gain user's authorization
     * @throws RequestTimeoutException      When authorization request timed out
     */
    private void userAuthorize() throws GoogleAuthorizationException, RequestTimeoutException {
        Preconditions.checkArgument(
                !uploadFilePath.startsWith("Enter "),
                "Please enter the upload file path in %s", GoogleDriveStorage.class);
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(dataStoreDir);
            credential = authorizationRequest();
            drive = new Drive.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(
                    APPLICATION_NAME).build();
            return;
        } catch (IOException e) {
            logger.warning(e.getMessage());
            throw new GoogleAuthorizationException();
        } catch (RuntimeException e) {
            logger.warning(e.getMessage());
            throw new RequestTimeoutException();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    /**
     * Authorizes the installed application to access user's protected data.
     */
    private Credential authorizationRequest() throws IOException {
        GoogleClientSecrets clientSecrets = retrieveClientSecrets();
        GoogleAuthorizationCodeFlow flow = buildFlow(clientSecrets);
        CancellableServerReceiver receiver = new CancellableServerReceiver();

        Credential credential = getUserCredential(flow, receiver);
        return credential;
    }

    /**
     * Retrieves application's client secrets in resource file folder
     *
     * @throws IOException When client secrets is not found
     */
    private GoogleClientSecrets retrieveClientSecrets() throws IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(GoogleDriveStorage.class.getResourceAsStream("/json/client_secret.json")));
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=drive "
                            + "into /src/main/resources/json/client_secret.json");
        }
        return clientSecrets;
    }

    /**
     * Builds {@code GoogleAuthorizationCodeFlow} object from client secrets
     *
     * @param clientSecrets Application's client secrets
     * @throws IOException
     */
    private GoogleAuthorizationCodeFlow buildFlow(GoogleClientSecrets clientSecrets) throws IOException {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                Collections.singleton(DriveScopes.DRIVE_FILE)).setDataStoreFactory(dataStoreFactory)
                .setAccessType("offline")
                .setApprovalPrompt("force")
                .build();
        return flow;
    }

    /**
     * Creates user's {@code Credential} by redirecting user to authorization request url and get access token
     *
     * @param flow          Authorization request flow
     * @param receiver      Server receiver to receive access token
     * @throws IOException  If user rejects access to his/her Google Drive
     */
    private Credential getUserCredential(GoogleAuthorizationCodeFlow flow, CancellableServerReceiver receiver)
            throws IOException {
        try {
            Credential credential = flow.loadCredential(user);
            if (credential != null
                    && (credential.getRefreshToken() != null
                    || credential.getExpiresInSeconds() == null
                    || credential.getExpiresInSeconds() > 60)) {
                return credential;
            }
            // open in browser
            String redirectUri = receiver.getRedirectUri();
            AuthorizationCodeRequestUrl authorizationUrl =
                    flow.newAuthorizationUrl().setRedirectUri(redirectUri);
            browse(authorizationUrl.build());
            // receive authorization code and exchange it for an access token
            String code = receiver.waitForCode();
            TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
            // store credential and return it
            return flow.createAndStoreCredential(response, user);
        } finally {
            receiver.stop();
        }
    }

    /**
     * Uploads a file using direct media upload.
     */
    public File uploadFile() throws IOException {
        File fileMetadata = new File();
        fileMetadata.setTitle(uploadFile.getName());

        FileContent mediaContent = new FileContent("image/jpeg", uploadFile);

        Drive.Files.Insert insert = drive.files().insert(fileMetadata, mediaContent);
        MediaHttpUploader uploader = insert.getMediaHttpUploader();
        uploader.setDirectUploadEnabled(true);
        return insert.execute();
    }
}
