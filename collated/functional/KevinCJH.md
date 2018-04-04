# KevinCJH
###### /java/seedu/address/logic/commands/person/FindCommand.java
``` java
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " " + PREFIX_NAME;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose"
            + " NAME or SKILL "
            + "contains any of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: n/NAME_KEYWORDS [MORE_NAME_KEYWORDS] or t/SKILL_KEYWORDS [MORE_SKILL_KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " n/Alice Bob\n"
            + "Example: " + COMMAND_WORD + " t/accountant manager";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }
```
###### /java/seedu/address/logic/commands/EmailCommand.java
``` java
/**
 * Send an email to the person identified using it's last displayed index from the address book.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Send an email to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final int TAB_ID_EMAIL = 3;

    public static final String MESSAGE_EMAIL_PERSON_SUCCESS = "Drafting email to: %1$s";

    private final Index targetIndex;

    private Person personToEmail;

    public EmailCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEmail = lastShownList.get(targetIndex.getZeroBased());

        EventsCenter.getInstance().post(new SwitchTabRequestEvent(TAB_ID_EMAIL));
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));

        GmailClient.getInstance();

        return new CommandResult(String.format(MESSAGE_EMAIL_PERSON_SUCCESS, personToEmail.getEmail()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.targetIndex.equals(((EmailCommand) other).targetIndex) // state check
                && Objects.equals(this.personToEmail, ((EmailCommand) other).personToEmail));
    }
}
```
###### /java/seedu/address/logic/GmailClient.java
``` java
/**
 * Creates an authorized Gmail client for all services that uses Gmail API.
 */
public class GmailClient {

    private static final String APPLICATION_NAME = "contactHeRo";

    private static GmailClient instance = null;
    private static Gmail service;

    private GmailClient() {
        try {
            service = createGmailClientService();
        } catch (IOException e) {
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
    public static Gmail createGmailClientService() throws IOException {
        Credential credential = GmailAuthentication.authorize();
        return new Gmail.Builder(GmailAuthentication.getHttpTransport(),
                GmailAuthentication.getJsonFactory(), credential)
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
###### /java/seedu/address/logic/GmailAuthentication.java
``` java
    public static HttpTransport getHttpTransport() {
        return httpTransport;
    }

    public static JsonFactory getJsonFactory() {
        return JSON_FACTORY;
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
            return new FindCommand(new SkillContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
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
###### /java/seedu/address/logic/parser/EmailCommandParser.java
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
###### /java/seedu/address/model/GmailMessage.java
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

    public static String getReceiverEmail() {
        return receiver;
    }
}

```
###### /java/seedu/address/model/skill/SkillContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Skill} matches any of the keywords given.
 */
public class SkillContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public SkillContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Iterator tagsIterator = person.getSkills().iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(tagsIterator.next());
        while (tagsIterator.hasNext()) {
            sb.append(" " + tagsIterator.next());
        }
        String tagLists = sb.toString()
                .replace("[", "")
                .replace("]", "");
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tagLists, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SkillContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((SkillContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/ui/EmailPanel.java
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

            showAlertDialogAndWait("Success", "Email has been sent successfully!");

            clearAllFields();

        } catch (MessagingException e) {
            e.printStackTrace();
            showAlertDialogAndWait("Error", "Email was not sent. Please try again.");
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
###### /java/seedu/address/ui/DetailsPanel.java
``` java
    /**
     * Adds the EmailPanel to the DetailsPanel
     */
    public void addEmailPanel() {
        emailPanel = new EmailPanel();
        email.setContent(emailPanel.getRoot());
    }

```
###### /java/seedu/address/ui/PersonCard.java
``` java
    private static final String[] TAG_COLOR_STYLES =
        { "teal", "red", "green", "blue", "orange", "brown",
            "yellow", "pink", "lightgreen", "grey", "purple" };
```
###### /java/seedu/address/ui/PersonCard.java
``` java
        initSkills(person);
    }

```
###### /java/seedu/address/ui/PersonCard.java
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
###### /resources/view/DarkTheme.css
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
###### /resources/view/EmailPanel.fxml
``` fxml
<AnchorPane prefHeight="478.0" prefWidth="686.0" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <HTMLEditor fx:id="bodyTxtField" layoutY="98.0" prefHeight="257.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" />
      <TextField fx:id="subjectTxtField" layoutY="55.0" prefHeight="29.0" promptText="Subject:" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" />
      <TextField fx:id="toTxtField" layoutY="10.0" prefHeight="25.0" promptText="To:" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" />
      <Button fx:id="sendBtn" layoutY="356.0" mnemonicParsing="false" onAction="#handleButtonAction" prefHeight="36.0" text="Send" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" />
   </children>
</AnchorPane>
```
