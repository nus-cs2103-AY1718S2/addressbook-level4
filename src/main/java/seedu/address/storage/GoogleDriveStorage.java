package seedu.address.storage;

import static com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp.browse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collections;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
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
    private static final String DIR_FOR_DOWNLOADS = "googledrive/";

    /**
     * Directory to store user credentials.
     */
    private static final java.io.File DATA_STORE_DIR =
            new java.io.File(System.getProperty("user.home"), ".google-credentials/google-drive-storage");

    private static FileDataStoreFactory dataStoreFactory;
    private static HttpTransport httpTransport;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Google Drive API client.
     */
    private static Drive drive;

    private static final Logger logger = LogsCenter.getLogger(GoogleDriveStorage.class);

    private final String uploadFilePath;
    private final java.io.File uploadFile;

    public GoogleDriveStorage(String uploadFilePath) throws GoogleAuthorizationException, RequestTimeoutException {
        this.uploadFilePath = uploadFilePath;
        uploadFile = new java.io.File(uploadFilePath);
        userAuthorize();
    }

    /**
     * Opens Google authentication link in user's default browser and request for authorization.
     * Sets up an instance of Google Drive API client after user authorized the application.
     *
     * @throws GoogleAuthorizationException     When application is unable to gain user's authorization
     */
    private void userAuthorize() throws GoogleAuthorizationException, RequestTimeoutException {
        Preconditions.checkArgument(
                !uploadFilePath.startsWith("Enter ") && !DIR_FOR_DOWNLOADS.startsWith("Enter "),
                "Please enter the upload file path and download directory in %s", GoogleDriveStorage.class);
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

            Credential credential = authorize();

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
    private Credential authorize() throws IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(GoogleDriveStorage.class.getResourceAsStream("/json/client_secret.json")));
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=drive "
                            + "into /src/main/resources/json/client_secret.json");
        }
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                Collections.singleton(DriveScopes.DRIVE_FILE)).setDataStoreFactory(dataStoreFactory)
                .build();


        CancellableServerReceiver receiver = new CancellableServerReceiver();
        try {
            Credential credential = flow.loadCredential("user");
            if (credential != null
                    && (credential.getRefreshToken() != null ||
                    credential.getExpiresInSeconds() == null ||
                    credential.getExpiresInSeconds() > 60)) {
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
            return flow.createAndStoreCredential(response, "user");
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

    /**
     * Downloads a file using direct media download.
     */
    private void downloadFile(File uploadedFile)
            throws IOException {
        // create parent directory (if necessary)
        java.io.File parentDir = new java.io.File(DIR_FOR_DOWNLOADS);
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw new IOException("Unable to create parent directory");
        }
        OutputStream out = new FileOutputStream(new java.io.File(parentDir, uploadedFile.getTitle()));

        MediaHttpDownloader downloader =
                new MediaHttpDownloader(httpTransport, drive.getRequestFactory().getInitializer());
        downloader.setDirectDownloadEnabled(true);
        downloader.download(new GenericUrl(uploadedFile.getDownloadUrl()), out);
    }

}
