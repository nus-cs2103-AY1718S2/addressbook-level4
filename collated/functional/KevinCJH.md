# KevinCJH

###### \java\seedu\address\commons\events\ui\LoadGoogleLoginEvent.java
``` java

/**
 * Loads the url of google authentication
 */
public class LoadGoogleLoginEvent extends BaseEvent {

    private final String authenticationUrl;

    public LoadGoogleLoginEvent(String authenticationUrl) {
        this.authenticationUrl = authenticationUrl;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getAuthenticationUrl() {
        return authenticationUrl;
    }
}
```
###### \java\seedu\address\commons\events\ui\LoadGoogleLoginRedirectEvent.java
``` java

/**
 * Loads the redirected url of google login authentication
 */
public class LoadGoogleLoginRedirectEvent extends BaseEvent {

    private String redirectUrl;

    public LoadGoogleLoginRedirectEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
```
###### \java\seedu\address\logic\commands\EmailCommand.java

``` java
/**
 * Creates an email message containing contents to be sent via gmail
 */
public class GmailMessage {

    private static Gmail service;
    private static MimeMessage emailContent;
    private static String subject;
    private static String bodyText;
    private static String receiver;

    public GmailMessage(String receiver, String subject, String bodyText) {
        GmailClient client = GmailClient.getInstance();
        service = client.getGmailService();

        this.receiver = receiver;
        this.subject = subject;
        this.bodyText = bodyText;

        try {
            emailContent = createEmailContent(receiver, getSenderEmail(), subject, bodyText);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public static MimeMessage createEmailContent(String to,
                                          String from,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage emailContent = new MimeMessage(session);

        emailContent.setFrom(new InternetAddress(from));
        emailContent.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        emailContent.setSubject(subject);
        emailContent.setContent(bodyText, "text/html; charset=utf-8");
        return emailContent;
    }

    /**
     * Get the email address of the authenticated user.
     * @return String of the email address
     * @throws IOException
     */
    private static String getSenderEmail() throws IOException {
        return service.users().getProfile("me").execute().getEmailAddress();
    }

    public static MimeMessage getEmailContent() {
        return emailContent;
    }

    public static String getSubject() {
        return subject;
    }

    public static String getBodyText() {
        return bodyText;
    }

        EventsCenter.getInstance().post(new SwitchTabRequestEvent(TAB_ID_EMAIL));
        EventsCenter.getInstance().post(new JumpToPersonListRequestEvent(targetIndex));

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EmailCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/person/FindCommandParser.java
``` java
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SKILL);

        if (!(arePrefixesPresent(argMultimap, PREFIX_NAME)
                || arePrefixesPresent(argMultimap, PREFIX_SKILL))
                || (arePrefixesPresent(argMultimap, PREFIX_NAME) && arePrefixesPresent(argMultimap, PREFIX_SKILL))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }



        if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            List<String> testnovalue = argMultimap.getAllValues(PREFIX_NAME);
            if (testnovalue.contains("")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\W+");
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        } else if (arePrefixesPresent(argMultimap, PREFIX_SKILL)) {
            List<String> testnovalue = argMultimap.getAllValues(PREFIX_SKILL);
            if (testnovalue.contains("")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            String[] tagKeywords = argMultimap.getValue(PREFIX_SKILL).get().split("\\W+");
            return new FindCommand(new PersonSkillContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\commands\exceptions\GoogleAuthenticationException.java
``` java

/**
 * Represents an exception which occurs during google authentication
 */
public class GoogleAuthenticationException extends Exception {
    public GoogleAuthenticationException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\logic\commands\GoogleLoginCommand.java
``` java

/**
 * Command to open the authentication/login page for google authentication
 */
public class GoogleLoginCommand extends Command {

    public static final String COMMAND_WORD = "googlelogin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Direct user to the login page for google authentication.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Please log in to Google.";

    public static final int TAB_ID = 4;

    private final GoogleAuthentication googleAuthentication = new GoogleAuthentication();

    @Override
    public CommandResult execute() {
        String authenticationUrl = googleAuthentication.getAuthenticationUrl();
        EventsCenter.getInstance().post(new LoadGoogleLoginEvent(authenticationUrl));
        EventsCenter.getInstance().post(new SwitchTabRequestEvent(TAB_ID));

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
```
###### \java\seedu\address\logic\commands\person\FindCommand.java
``` java
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " " + PREFIX_NAME;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose"
            + " NAME or SKILL "
            + "contains any of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: n/NAME_KEYWORDS [MORE_NAME_KEYWORDS] or s/SKILL_KEYWORDS [MORE_SKILL_KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " n/Alice Bob\n"
            + "Example: " + COMMAND_WORD + " s/accountant manager";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }
```
###### \java\seedu\address\logic\GmailClient.java
``` java
/**
 * Creates an authorized Gmail client for all services that uses Gmail API.
 */
public class GmailClient {

    private static final String APPLICATION_NAME = "contactHeRo";

    private static GmailClient instance = null;
    private static Gmail service;

    private static final GoogleAuthentication googleAuthentication = new GoogleAuthentication();

    private GmailClient() {
        try {
            service = createGmailClientService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GmailClient getInstance() {
        if (instance == null) {
            instance = new GmailClient();
        }
        return instance;
    }

    /**
     * Create and return an authorized Gmail client service.
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static Gmail createGmailClientService() throws IOException, GoogleAuthenticationException {
        String token = googleAuthentication.getToken();
        GoogleCredential credential = googleAuthentication.getCredential(token);
        return new Gmail.Builder(googleAuthentication.getHttpTransport(),
                googleAuthentication.getJsonFactory(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Create a message from an email to be send out.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    /**
     * Send an email from the user's mailbox to its recipient.
     * @param emailContent Email content to be sent.
     * @throws MessagingException
     * @throws IOException
     */
    public static void sendEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send("me", message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
    }


    public static Gmail getGmailService() {
        return service;
    }
}

```

###### \java\seedu\address\logic\GoogleAuthentication.java
``` java

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
```
###### \java\seedu\address\logic\GoogleAuthentication.java
``` java
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
```
###### \java\seedu\address\logic\parser\EmailCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EmailCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\person\FindCommandParser.java
``` java
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SKILL);

        if (!(arePrefixesPresent(argMultimap, PREFIX_NAME)
                || arePrefixesPresent(argMultimap, PREFIX_SKILL))
                || (arePrefixesPresent(argMultimap, PREFIX_NAME) && arePrefixesPresent(argMultimap, PREFIX_SKILL))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }



        if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            List<String> testnovalue = argMultimap.getAllValues(PREFIX_NAME);
            if (testnovalue.contains("")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\W+");
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        } else if (arePrefixesPresent(argMultimap, PREFIX_SKILL)) {
            List<String> testnovalue = argMultimap.getAllValues(PREFIX_SKILL);
            if (testnovalue.contains("")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            String[] tagKeywords = argMultimap.getValue(PREFIX_SKILL).get().split("\\W+");
            return new FindCommand(new PersonSkillContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose"
            + " NAME or SKILL "
            + "contains any of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: n/NAME_KEYWORDS [MORE_NAME_KEYWORDS] or s/SKILL_KEYWORDS [MORE_SKILL_KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " n/Alice Bob\n"
            + "Example: " + COMMAND_WORD + " s/accountant manager";


}
```
###### \java\seedu\address\model\GmailMessage.java

``` java
/**
 * Send an email to the person identified using it's last displayed index from the address book.
 */
public class EmailCommand extends Command {


    private static final String SENDER_EMAIL = "me"; //unique identifier recognized by Google

    public GmailMessage(String receiver, String subject, String bodyText) {
        GmailClient client = GmailClient.getInstance();
        service = client.getGmailService();


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Send an email to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

        try {
            emailContent = createEmailContent(receiver, SENDER_EMAIL, subject, bodyText);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    public static final String MESSAGE_EMAIL_PERSON_SUCCESS = "Drafting email to: %1$s";

    private final Index targetIndex;

    private Person personToEmail;

    public EmailCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    public static MimeMessage getEmailContent() {
        return emailContent;
    }


        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEmail = lastShownList.get(targetIndex.getZeroBased());

        EventsCenter.getInstance().post(new SwitchTabRequestEvent(TAB_ID_EMAIL));
        EventsCenter.getInstance().post(new JumpToPersonListRequestEvent(targetIndex));

```
###### \java\seedu\address\model\skill\PersonSkillContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Skill} matches any of the keywords given.
 */
public class PersonSkillContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonSkillContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object

                || (other instanceof PersonSkillContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonSkillContainsKeywordsPredicate) other).keywords)); // state check

    }

    public static JsonFactory getJsonFactory() {
        return JSON_FACTORY;
    }
}
```
###### \java\seedu\address\ui\DetailsPanel.java
``` java
    /**
     * Adds the EmailPanel to the DetailsPanel
     */
    public void addEmailPanel() {
        emailPanel = new EmailPanel();
        email.setContent(emailPanel.getRoot());
    }

    /**
     * Adds the GoogleLoginPanel to the DetailsPanel
     */
    public void addGoogleLoginPanel() {
        googleLoginPanel = new GoogleLoginPanel();
        googlelogin.setContent(googleLoginPanel.getRoot());
    }

```
###### \java\seedu\address\ui\EmailPanel.java
``` java
/**
 * Shows the email drafting tab
 */
public class EmailPanel extends UiPart<Region> {

    private static final String FXML = "EmailPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EmailPanel.class);
    private String recipientEmail;

    @FXML
    private TextField toTxtField;

    @FXML
    private TextField subjectTxtField;

    @FXML
    private HTMLEditor bodyTxtField;

    @FXML
    private Button sendBtn;

    public EmailPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     *Handles the send button click
     */
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        String bodyText = bodyTxtField.getHtmlText();
        String subjectText = subjectTxtField.getText();

        executeSendEmail(subjectText, bodyText);
    }

    /**
     *Executes the sending of email.
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @throws IOException
     */
    private void executeSendEmail(String subject, String bodyText) throws IOException {

        GmailMessage gmailMessage = new GmailMessage(recipientEmail, subject, bodyText);

        try {
            GmailClient client = GmailClient.getInstance();
            client.sendEmail(gmailMessage.getEmailContent());

            showAlertDialogAndWait("Email sent", "Email has been sent successfully!");

            clearAllFields();

        } catch (Exception e) {
            showAlertDialogAndWait("Email not sent",
                    "Please ensure you are connected to the internet and has logged into google");
        }

    }

    /**
     *Fill up the email draft with details of the recipient
     * @param person person that was selected in the list
     */
    private void fillEmailDraft(Person person) {
        recipientEmail = person.getEmail().value;
        toTxtField.setText(recipientEmail);
        bodyTxtField.setHtmlText("<font face=\"Segoe UI\">Dear " + person.getName().fullName + ",</font>");
        subjectTxtField.requestFocus();
    }

    /**
     *Shows the alert dialog after email is sent successfully
     * @param content the text which will be display to the user
     */
    private void showAlertDialogAndWait(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("contactHeRo");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initStyle(StageStyle.UTILITY);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.showAndWait();
    }

    /**
     *Clear the textfield and htmleditor textbox
     */
    private void clearAllFields() {
        toTxtField.clear();
        subjectTxtField.clear();
        bodyTxtField.setHtmlText("");
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        fillEmailDraft(event.getNewSelection().person);
    }
}
```
###### \java\seedu\address\ui\GoogleLoginPanel.java
``` java

/**
 * The Google Login Panel of the App.
 */
public class GoogleLoginPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "https://www.google.com/";

    private static final String FXML = "GoogleLoginPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView loginbrowser;

    public GoogleLoginPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> loginbrowser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        loginbrowser = null;
    }

    @Subscribe
    private void loadLoginUrl(LoadGoogleLoginEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPage(event.getAuthenticationUrl());
    }

    @Subscribe
    private void getRedirectUrlEvent (LoadGoogleLoginRedirectEvent event) {
        logger.info((LogsCenter.getEventHandlingLogMessage(event)));
        event.setRedirectUrl(loginbrowser.getEngine().getLocation());
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static final String[] SKILL_COLOR_STYLES =
        { "teal", "red", "green", "blue", "orange", "brown",
            "yellow", "pink", "lightgreen", "grey", "purple" };
    private static final String DEFAULT_IMAGE = "/images/default.png";
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        initSkills(person);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));

    }

```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Returns the color style for {@code skillName}'s label.
     */
    private String getSkillColorStyleFor(String skillName) {
        // we use the hash code of the skill name to generate a random color, so that the color remain consistent
        // between different runs of the program while still making it random enough between skills.
        return SKILL_COLOR_STYLES[Math.abs(skillName.hashCode()) % SKILL_COLOR_STYLES.length];

    }

```
###### \resources\view\DarkTheme.css
``` css
#skills .teal {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
}

#skills .red {
    -fx-text-fill: black;
    -fx-background-color: red;
}

#skills .yellow {
    -fx-text-fill: black;
    -fx-background-color: yellow;
}

+#skills .blue {
    -fx-text-fill: white;
    -fx-background-color: blue;
}

#skills .orange {
    -fx-text-fill: black;
    -fx-background-color: orange;
}

#skills .brown {
    -fx-text-fill: white;
    -fx-background-color: brown;
}

#skills .green {
    -fx-text-fill: black;
    -fx-background-color: green;
}

#skills .pink {
    -fx-text-fill: black;
    -fx-background-color: pink;
}

#skills .lightgreen {
    -fx-text-fill: black;
    -fx-background-color: lightgreen;
}

#skills .grey {
    -fx-text-fill: black;
    -fx-background-color: grey;
}

#skills .purple {
    -fx-text-fill: white;
    -fx-background-color: purple;
}
```
###### \resources\view\EmailPanel.fxml
``` fxml
<AnchorPane prefHeight="478.0" prefWidth="686.0" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
   <children>
       <TextField fx:id="toTxtField" layoutY="10.0" prefHeight="25.0" promptText="To:" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" />
       <TextField fx:id="subjectTxtField" layoutY="55.0" prefHeight="29.0" promptText="Subject:" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" />
       <HTMLEditor fx:id="bodyTxtField" layoutY="98.0" prefHeight="257.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" />
       <Button fx:id="sendBtn" layoutY="356.0" mnemonicParsing="false" onAction="#handleButtonAction" prefHeight="36.0" text="Send" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" />
   </children>
</AnchorPane>
```
