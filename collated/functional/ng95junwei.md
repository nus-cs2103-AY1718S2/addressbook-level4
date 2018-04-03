# ng95junwei
###### \java\seedu\address\commons\util\GmailUtil.java
``` java

/**
 * Provides handler to send emails from logged in user
 */
public class GmailUtil {

    /** Application name. */
    private static final String APPLICATION_NAME =
            "Gmail API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/gmail-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory dataStoreFactory;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;


    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/gmail-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(GmailScopes.GMAIL_SEND);
    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
    private Gmail service;

    public GmailUtil() throws IOException {
        this.service = getGmailService();
    }
    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                GmailUtil.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(dataStoreFactory)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Gmail client service.
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Using inputs, generate MimeMessage type object to use to encapsulate message states.
     * @param to
     * @param cc
     * @param from
     * @param subject
     * @param bodyText
     * @return MimeMessage to pass to createMessageWithEmail
     * @throws MessagingException
     */
    private static MimeMessage createEmail(String to, String cc, String from, String subject, String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        InternetAddress tAddress = new InternetAddress(to);
        InternetAddress cAddress = cc.isEmpty() ? null : new InternetAddress(cc);
        InternetAddress fAddress = new InternetAddress(from);

        email.setFrom(fAddress);
        if (cAddress != null) {
            email.addRecipient(javax.mail.Message.RecipientType.CC, cAddress);
        }
        email.addRecipient(javax.mail.Message.RecipientType.TO, tAddress);
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    /**
     * Taking in MimeMessage email, generate a Message which can be used to send through Gmail api
     * @param email
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    private static Message createMessageWithEmail(MimeMessage email) throws MessagingException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        email.writeTo(baos);
        String encodedEmail = Base64.encodeBase64URLSafeString(baos.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    /**
     * Start of the chain, takes all the inputs and generates Message using createMessageWithEmail and uses gmail
     * api to send the email out
     *
     * @param service
     * @param recipientEmail
     * @param ccEmail
     * @param fromEmail
     * @param title
     * @param message
     * @throws IOException
     * @throws MessagingException
     */
    public static void send(Gmail service, String recipientEmail, String ccEmail, String fromEmail, String title,
                            String message) throws IOException, MessagingException {
        Message m = createMessageWithEmail(createEmail(recipientEmail, ccEmail, fromEmail, title, message));
        service.users().messages().send("me", m).execute();
    }

    public Gmail getService() {
        return this.service;
    }

}
```
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java

/**
 * Finds and emails all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "em";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Emails all persons whose names matches any of "
            + "the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: NAME TEMPLATE\n"
            + "Example: " + COMMAND_WORD + " alice coldemail";

    private final NameContainsKeywordsPredicate predicate;
    private final String search;

    public EmailCommand(NameContainsKeywordsPredicate predicate, String search) {

        this.predicate = predicate;
        this.search = search;
    }

    @Override
    public CommandResult execute() {
        // Build a new authorized API client service.

        model.updateFilteredPersonList(predicate);
        ObservableList<Person> emailList = model.getFilteredPersonList();
        for (Person p : emailList) {
            try {
                Template template = model.selectTemplate(this.search);
                GmailUtil handler = new GmailUtil();
                Gmail service = handler.getService();
                handler.send(service, p.getEmail().toString(), "",
                        service.users().getProfile("me").getUserId(), template.getTitle(),
                        template.getMessage());
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Some Exception occurred");
            }
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.predicate.equals(((EmailCommand) other).predicate)); // state check
    }
}



```
###### \java\seedu\address\model\AddressBook.java
``` java

    /**
     * TBD replace with DB seed.
     * @return a List of template to initialize AddressBook with
     */
    private static List<Template> generateTemplates() {
        List<Template> list = new ArrayList<>();
        Template template1 = new Template("coldEmail", "Meet up over Coffee",
                "Hey, I am from Addsurance and would like you ask if you are interested in planning your"
                        + " finances with us. Would you care to meet over coffee in the next week or so?");
        Template template2 = new Template("followUpEmail", "Follow up from last week",
                "Hey, we met last week and I was still hoping if you would like to leave your "
                        + "finances with us at Addsurance. Would you care to meet over coffee in the next week or so"
                        + " to discuss further?");
        list.add(template1);
        list.add(template2);
        return list;
    }

    public void setTemplates() throws DuplicateTemplateException {
        this.templates.setTemplates(generateTemplates());
    }

    public synchronized UniqueTemplateList getAllTemplates() {
        return this.templates;
    }
```
###### \java\seedu\address\model\email\exceptions\DuplicateTemplateException.java
``` java

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Template objects.
 */

public class DuplicateTemplateException extends DuplicateDataException {
    public DuplicateTemplateException() {
        super("Operation would result in duplicate templates");
    }
}
```
###### \java\seedu\address\model\email\exceptions\TemplateNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified template.
 */
public class TemplateNotFoundException extends Exception {}
```
###### \java\seedu\address\model\email\UniqueTemplateList.java
``` java
/**
 * A list of templates that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Template#equals(Object)
 */
public class UniqueTemplateList implements Iterable<Template> {

    private final ObservableList<Template> internalList = FXCollections.observableArrayList();

    /**
     * Returns one Template closest to the template searched for
     */
    public Template search(String search) throws TemplateNotFoundException {
        for (Template t : internalList) {
            if (t.getPurpose().contains(search)) {
                return t;
            }
        }
        throw new TemplateNotFoundException();
    }

    /**
     * Returns true if the list contains an equivalent template as the given argument.
     */
    public boolean contains(Template toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a template to the list.
     *
     * @throws DuplicateTemplateException if the template to add is a duplicate of an existing template
     * in the list.
     */
    public void add(Template toAdd) throws DuplicateTemplateException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTemplateException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent template from the list.
     *
     * @throws TemplateNotFoundException if no such template could be found in the list.
     */
    public boolean remove(Template toRemove) throws TemplateNotFoundException {
        requireNonNull(toRemove);
        final boolean templateFoundAndDeleted = internalList.remove(toRemove);
        if (!templateFoundAndDeleted) {
            throw new TemplateNotFoundException();
        }
        return templateFoundAndDeleted;
    }

    public void setTemplates(UniqueTemplateList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTemplates(List<Template> templates) throws DuplicateTemplateException {
        requireAllNonNull(templates);
        final UniqueTemplateList replacement = new UniqueTemplateList();
        for (final Template template : templates) {
            replacement.add(template);
        }
        setTemplates(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Template> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Template> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTemplateList // instanceof handles nulls
                && this.internalList.equals(((UniqueTemplateList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

```
###### \java\seedu\address\model\Model.java
``` java

    Template selectTemplate(String search) throws TemplateNotFoundException;

    ObservableList<Template> getAllTemplates();

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ObservableList<Template> getAllTemplates() {
        return addressBook.getAllTemplates().asObservableList();
    }

    @Override
    public Template selectTemplate(String search) throws TemplateNotFoundException {
        return addressBook.getAllTemplates().search(search);
    }

```
