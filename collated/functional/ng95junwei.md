# ng95junwei
###### \java\seedu\address\commons\core\Messages.java
``` java
    public static final String MESSAGE_EMAIL_SENT = "Email sent to %1$d persons!";
    public static final String MESSAGE_TEMPLATE_NOT_FOUND = "No such templates found!";
```
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
###### \java\seedu\address\logic\commands\AddTemplateCommand.java
``` java
/**
 * Adds an appointment to the address book.
 */
public class AddTemplateCommand extends Command {

    public static final String COMMAND_WORD = "addtemplate";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a template to the address book. "
            + "Parameters: "
            + PREFIX_PURPOSE + "PURPOSE "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_MESSAGE + "MESSAGE BODY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PURPOSE + "greeting "
            + PREFIX_SUBJECT + "Hello there "
            + PREFIX_MESSAGE + "Luke, I am your father";

    public static final String MESSAGE_SUCCESS = "New template added: %1$s";
    public static final String MESSAGE_DUPLICATE_TEMPLATE = "A template with the "
            + "same purpose already exists in the address book";

    private final Template toAdd;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddTemplateCommand(Template template) {
        requireNonNull(template);
        toAdd = template;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.addTemplate(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTemplateException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TEMPLATE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTemplateCommand // instanceof handles nulls
                && toAdd.equals(((AddTemplateCommand) other).toAdd)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\DeleteTemplateCommand.java
``` java
/**
 * Deletes an Template that matches all the input fields from the address book.
 */
public class DeleteTemplateCommand extends Command {

    public static final String COMMAND_WORD = "deletetemplate";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the template which has the specified purpose.\n"
            + "Parameters: PURPOSE "
            + "Example: " + COMMAND_WORD + " "
            + "greeting";

    public static final String MESSAGE_DELETE_TEMPLATE_SUCCESS = "Deleted Template with purpose : %1$s";

    private String purposeToDelete;

    public DeleteTemplateCommand(String purposeToDelete) {

        this.purposeToDelete = purposeToDelete;
    }

    @Override
    public CommandResult execute() throws CommandException {
        System.out.println(purposeToDelete);
        requireNonNull(purposeToDelete);
        try {
            model.deleteTemplate(purposeToDelete);
        } catch (TemplateNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_TEMPLATE_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TEMPLATE_SUCCESS, purposeToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTemplateCommand // instanceof handles nulls
                && this.purposeToDelete.equals(((DeleteTemplateCommand) other).purposeToDelete));
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

    /**
     *
     * @param displaySize
     * @return summary message for persons emailed
     */
    public static String getMessageForPersonEmailSummary(int displaySize) {
        return String.format(Messages.MESSAGE_EMAIL_SENT, displaySize);
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
            } catch (TemplateNotFoundException e) {
                return new CommandResult(Messages.MESSAGE_TEMPLATE_NOT_FOUND);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Some Exception occurred");
            }
        }
        return new CommandResult(getMessageForPersonEmailSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.predicate.equals(((EmailCommand) other).predicate)); // state check
    }
}



```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case EmailCommand.COMMAND_WORD:
            return new EmailCommandParser().parse(arguments);

        case EmailCommand.COMMAND_ALIAS:
            return new EmailCommandParser().parse(arguments);

        case AddTemplateCommand.COMMAND_WORD:
            return new AddTemplateCommandParser().parse(arguments);

        case AddTemplateCommand.COMMAND_ALIAS:
            return new AddTemplateCommandParser().parse(arguments);

        case DeleteTemplateCommand.COMMAND_WORD:
            return new DeleteTemplateCommandParser().parse(arguments);

        case DeleteTemplateCommand.COMMAND_ALIAS:
            return new DeleteTemplateCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddTemplateCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddTemplateCommandParser implements Parser<AddTemplateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTemplateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_PURPOSE, PREFIX_SUBJECT, PREFIX_MESSAGE);

        if (!arePrefixesPresent(
                argMultimap, PREFIX_PURPOSE, PREFIX_SUBJECT, PREFIX_MESSAGE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTemplateCommand.MESSAGE_USAGE));
        }

        try {
            String purpose = (argMultimap.getValue(PREFIX_PURPOSE)).get().trim();
            String subject = (argMultimap.getValue(PREFIX_SUBJECT)).get().trim();
            String message = (argMultimap.getValue(PREFIX_MESSAGE)).get().trim();

            Template template = new Template(purpose, subject, message);

            return new AddTemplateCommand(template);
        } catch (java.lang.IllegalArgumentException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
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

    /**
     * Adds a template to the unique template list
     * @param template
     * @throws DuplicateTemplateException
     */
    public void addTemplate(Template template) throws DuplicateTemplateException {
        templates.add(template);
    }

    /**
     * Removes template whose purpose is purpose
     * @param purpose
     * @return true if template was removed, throw an exception otherwise
     * @throws TemplateNotFoundException
     */
    public boolean removeTemplate(String purpose) throws TemplateNotFoundException {
        if (templates.remove(purpose)) {
            return true;
        } else {
            throw new TemplateNotFoundException();
        }
    }

    public void setTemplates() throws DuplicateTemplateException {
        this.templates.setTemplates(generateTemplates());
    }

    public synchronized UniqueTemplateList getAllTemplates() {
        return this.templates;
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<Template> getTemplateList() {
        return templates.asObservableList();
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
        String newPurpose = toCheck.getPurpose();
        for (Template t : internalList) {
            if (t.getPurpose().equals(newPurpose)) {
                return true;
            }
        }
        return false;
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
    public boolean remove(String purpose) throws TemplateNotFoundException {
        requireNonNull(purpose);
        for (Template template : internalList) {
            if (template.getPurpose().equals(purpose)) {
                internalList.remove(template);
                return true;
            }
        }
        throw new TemplateNotFoundException();
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

    /**
     * Updates the filter of the filtered template list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTemplateList(Predicate<Template> predicate);

    /** Deletes the given template */
    void deleteTemplate(String purpose) throws TemplateNotFoundException;

    /** Selects template based on search string */
    Template selectTemplate(String search) throws TemplateNotFoundException;

    /** Adds the given template */
    void addTemplate(Template template) throws DuplicateTemplateException;

    /** Returns an unmodifiable view of all templates */
    ObservableList<Template> getAllTemplates();

    /** Returns an unmodifiable view of the filtered template list */
    ObservableList<Template> getFilteredTemplateList();

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** Raises an event to indicate a template has been deleted */
    private void indicateTemplateDeleted(String purpose) {
        //raise(new NewTemplateAddedEvent(template)); TO IMPLEMENT
    }
    /** Raises an event to indicate a template has been added */
    private void indicateTemplateAdded(Template template) {
        //raise(new NewTemplateAddedEvent(template)); TO IMPLEMENT
    }



```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void deleteTemplate(String purpose) throws TemplateNotFoundException {
        addressBook.removeTemplate(purpose);
        updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
        indicateTemplateDeleted(purpose);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTemplate(Template template) throws DuplicateTemplateException {
        addressBook.addTemplate(template);
        updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
        indicateTemplateAdded(template);
        indicateAddressBookChanged();
    }
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
###### \java\seedu\address\model\ModelManager.java
``` java

    @Override
    public void updateFilteredTemplateList(Predicate<Template> predicate) {
        requireNonNull(predicate);
        filteredTemplates.setPredicate(predicate);
    }

    @Override
    public ObservableList<Template> getFilteredTemplateList() {
        return FXCollections.unmodifiableObservableList(filteredTemplates);
    }
```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
``` java
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override




    public boolean test(Person person) {
        final List<String> personDetails = person.toStringList();
        return keywords.stream()
                .anyMatch(keyword -> personDetails.stream()
                        .anyMatch(details -> details.toLowerCase().contains(keyword.toLowerCase())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}

```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    ObservableList<Template> getTemplateList();
```
