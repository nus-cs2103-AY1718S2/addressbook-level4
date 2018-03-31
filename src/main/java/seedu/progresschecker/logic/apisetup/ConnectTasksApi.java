package seedu.progresschecker.logic.apisetup;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;

/**
 * Sets up Google Tasks API.
 * i. Authorizes data access based on client credentials.
 * ii. Builds service (initializes API).
 */
public class ConnectTasksApi {
    private JsonFactory jsonFactory;
    private HttpTransport transport;
    private Credential credentials;

    public ConnectTasksApi() {
        this.jsonFactory = new JacksonFactory();
        this.transport = new NetHttpTransport();
        this.credentials = null;
    }

    /**
     * Authorizes the data access requested by Tasks API by loading client secrets file.
     */
    public void authorize() throws Exception {
        final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(
                FileDataStoreFactory.class.getName());
        buggyLogger.setLevel(java.util.logging.Level.SEVERE);

        // Sets up files to store access token
        DataStoreFactory datastore = new FileDataStoreFactory(
                new File("tokens")
        );

        InputStream in =
                ConnectTasksApi.class.getResourceAsStream("/client_id.json");

        // Loads Client Secrets file downloaded from Google developer console.
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                this.jsonFactory,
                new InputStreamReader(in)
        );

        // Sets Up authorization code flow.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                this.transport,
                this.jsonFactory,
                clientSecrets,
                Collections.singleton(TasksScopes.TASKS)
        ).setDataStoreFactory(datastore).build();

        // Authorizes with client credentials.
        this.credentials = new AuthorizationCodeInstalledApp(
                flow,
                new LocalServerReceiver()
        ).authorize("user");
    }

    /**
     * Builds Google Tasks service.
     */
    public Tasks getTasksService() {
        return new Tasks.Builder(
                this.transport,
                this.jsonFactory,
                this.credentials
        ).build();
    }
}
