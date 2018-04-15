# Caijun7-reused
###### /java/seedu/address/storage/CancellableServerReceiver.java
``` java
/**
 * A cancellable Server Receiver.
 */
class CancellableServerReceiver implements VerificationCodeReceiver {

    private static final String LOCALHOST = "localhost";
    private static final String CALLBACK_PATH = "/Callback";

    private Server server;
    private String code;
    private String error;
    private final Lock lock = new ReentrantLock();
    private final Condition gotAuthorizationResponse = lock.newCondition();
    private int port;
    private final String host;
    private final String callbackPath;
    private String successLandingPageUrl;
    private String failureLandingPageUrl;

    /**
     * Constructor that starts the server on {@link #LOCALHOST} and an unused port.
     */
    public CancellableServerReceiver() {
        this(LOCALHOST, -1, CALLBACK_PATH, null, null);
    }

    /**
     * Constructor.
     *
     * @param host Host name to use
     * @param port Port to use or {@code -1} to select an unused port
     */
    CancellableServerReceiver(String host, int port, String callbackPath,
                              String successLandingPageUrl, String failureLandingPageUrl) {
        this.host = host;
        this.port = port;
        this.callbackPath = callbackPath;
        this.successLandingPageUrl = successLandingPageUrl;
        this.failureLandingPageUrl = failureLandingPageUrl;
    }

    @Override
    public String getRedirectUri() throws IOException {
        if (port == -1) {
            port = getUnusedPort();
        }
        server = new Server(port);
        for (Connector c : server.getConnectors()) {
            c.setHost(host);
        }
        server.addHandler(new CallbackHandler());
        try {
            server.start();
        } catch (Exception ex) {
            Throwables.propagateIfPossible(ex);
            throw new IOException(ex);
        }
        return "http://" + host + ":" + port + CALLBACK_PATH;
    }

    /**
     * Locks the thread and wait for the authorization code
     *
     * @throws IOException      When user decline access
     * @throws RuntimeException When authorization request timed out after 10 seconds
     */
    @Override
    public String waitForCode() throws IOException {
        lock.lock();
        try {
            long startTime = System.currentTimeMillis();
            while (code == null && error == null) {
                long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed > 10000) {
                    throw new RuntimeException("Request timeout (" + error + ")");
                }
                gotAuthorizationResponse.await(10, TimeUnit.SECONDS);
            }
            if (error != null) {
                throw new IOException("User authorization failed (" + error + ")");
            }
            return code;
        } catch (InterruptedException e) {
            throw new RuntimeException("Request timeout (" + error + ")");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void stop() throws IOException {
        if (server != null) {
            try {
                server.stop();
            } catch (Exception ex) {
                Throwables.propagateIfPossible(ex);
                throw new IOException(ex);
            }
            lock.lock();
            try {
                code = null;
                gotAuthorizationResponse.signal();
            } finally {
                lock.unlock();
            }
            server = null;
        }
    }

    private static int getUnusedPort() throws IOException {
        Socket socket = new Socket();
        socket.bind(null);
        try {
            return socket.getLocalPort();
        } finally {
            socket.close();
        }
    }

    /**
     * Jetty handler that takes the verifier token passed over from the OAuth provider and stashes it
     * where {@link #waitForCode} will find it.
     */
    class CallbackHandler extends AbstractHandler {

        @Override
        public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
                throws IOException {
            if (!CALLBACK_PATH.equals(target)) {
                return;
            }
            Request requestWrapper = (Request) request;
            requestWrapper.setHandled(true);
            lock.lock();
            try {
                error = request.getParameter("error");
                code = request.getParameter("code");
                gotAuthorizationResponse.signal();
            } finally {
                lock.unlock();
            }

            if (error == null && successLandingPageUrl != null) {
                response.sendRedirect(successLandingPageUrl);
            } else if (error != null && failureLandingPageUrl != null) {
                response.sendRedirect(failureLandingPageUrl);
            } else {
                writeLandingHtml(response);
            }
            response.flushBuffer();
        }

        /**
         * Produces the landing html page after user accept the authorization request
         *
         * @param response
         * @throws IOException
         */
        private void writeLandingHtml(HttpServletResponse response) throws IOException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html");

            PrintWriter doc = response.getWriter();
            doc.println("<html>");
            doc.println("<head><title>OAuth 2.0 Authentication Token Received</title></head>");
            doc.println("<body>");
            doc.println("Received verification code. You may now close this window.");
            doc.println("</body>");
            doc.println("</html>");
            doc.flush();
        }
    }
}
```
###### /java/seedu/address/storage/GoogleDriveStorage.java
``` java
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
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Removes all {@code tag}s that are not used by any {@code person} in this {@code AddressBook}.
     */
    private void removeUnusedTags() {
        Set<Tag> tagsInPersons = persons.asObservableList().stream()
                .map(Person::getTags)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        tags.setTags(tagsInPersons);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
    * Removes {@code tag} from {@code person} in this {@code AddressBook}.
    * @throws PersonNotFoundException if the {@code person} is not in this {@code AddressBook}.
    */
    private void removeTagFromPerson(Tag tag, Person person) throws PersonNotFoundException {
        Set<Tag> newTags = new HashSet<>(person.getTags());
        if (!newTags.remove(tag)) {
            return;
        }
        Person newPerson =
                new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                        person.getBirthday(), person.getTimetable(), newTags);
        try {
            updatePerson(person, newPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Modifying a person's tags only should not result in a duplicate. "
                    + "See Person#equals(Object).");
        }
    }

    /**
    * Removes {@code tag} from all persons in this {@code AddressBook}.
    */
    public void removeTag(Tag tag) {
        try {
            for (Person person : persons) {
                removeTagFromPerson(tag, person);
            }
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible exception: person is obtained from the address book.");
        }
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void deleteTag(Tag tag) {
        addressBook.removeTag(tag);
    }
```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Remove {@code tag} from all {@code person}s in the {@code AddressBook}.
     * @param tag
     */
    void deleteTag(Tag tag);
```
