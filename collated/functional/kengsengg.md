# kengsengg
###### \java\seedu\address\logic\commands\AppointmentCommand.java
``` java
/**
 * Creates an appointment for the student at the specified index.
 */
public class AppointmentCommand extends Command {

    public static final String COMMAND_WORD = "appointment";
    public static final String COMMAND_ALIAS = "appt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a consultation appointment for the student "
            + "at the specified index.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE "
            + PREFIX_START_TIME + "START TIME "
            + PREFIX_END_TIME + "END TIME \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "28031998 "
            + PREFIX_START_TIME + "1500 "
            + PREFIX_END_TIME + "1600 \n"
            + "Example: " + COMMAND_ALIAS + " 1 "
            + PREFIX_DATE + "28031998 "
            + PREFIX_START_TIME + "1500 "
            + PREFIX_END_TIME + "1600 ";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in EduBuddy";

    private final Index index;
    private final Appointment toAdd;

    private BrowserPanel browserPanel = new BrowserPanel();
    private CalendarDisplay calendarDisplay = new CalendarDisplay();
    private Person selectedPerson;

    /**
     * Creates an AppointmentCommand to add the specified {@code Appointment}
     */
    public AppointmentCommand(Index index, Appointment appointment) {
        requireNonNull(index);
        requireNonNull(appointment);

        this.index = index;
        this.toAdd = appointment;
    }

    @Override
    public CommandResult execute() throws CommandException, IOException {
        requireNonNull(model);
        try {
            model.addAppointment(toAdd);
            getDetails();
            showEventOnCalendar();
            refreshCalendarView();
            return new CommandResult(String.format(MESSAGE_SUCCESS, getDetails()));
        } catch (DuplicateAppointmentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }
    }

    public String getDetails() {
        List<Person> lastShownList = model.getFilteredPersonList();
        selectedPerson = lastShownList.get(index.getZeroBased());
        return toAdd.getStartTime() + " to " + toAdd.getEndTime() + " on " + toAdd.getDate()
                + " with " + selectedPerson.getName();
    }

    private void showEventOnCalendar() throws IOException {
        calendarDisplay.createEvent(toAdd, selectedPerson);
    }

    private void refreshCalendarView() {
        browserPanel.loadDefaultPage();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentCommand // instanceof handles nulls
                && toAdd.equals(((AppointmentCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sorts all the people in the list by their names in alphabetical order (case insensitive)
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS_SORT_BY_NAME = "Sorted all persons by name in alphabetical order";
    public static final String MESSAGE_SUCCESS_SORT_BY_TAG = "Sorted all persons by tag in alphabetical order";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the student list by the parameter provided "
            + "by the user.\n"
            + "Parameters: KEYWORD (valid keyword: name, tag)\n"
            + "Example: " + COMMAND_WORD + " name\n";

    private final String parameter;

    public SortCommand(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public CommandResult execute() {
        model.sortPersonList(parameter);
        if (("name").equals(parameter)) {
            return new CommandResult(MESSAGE_SUCCESS_SORT_BY_NAME);
        }
        return new CommandResult(MESSAGE_SUCCESS_SORT_BY_TAG);
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java

```
###### \java\seedu\address\logic\parser\AppointmentCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AppointmentCommand object
 */
public class AppointmentCommandParser implements Parser<AppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AppointmentCommand
     * and returns an AppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_START_TIME, PREFIX_END_TIME);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }

        try {
            String date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).get();
            String startTime = ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_START_TIME)).get();
            String endTime = ParserUtil.parseEndTime(argMultimap.getValue(PREFIX_END_TIME)).get();
            Appointment appointment = new Appointment(date, startTime, endTime);

            return new AppointmentCommand(index, appointment);
        } catch (IllegalValueException ive) {
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
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final String[] ALLOWED_PARAMETERS = {"name", "tag"};
    private int arrayCounter = 0;
    private boolean validParameter = false;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        while (arrayCounter < ALLOWED_PARAMETERS.length) {
            if (trimmedArgs.equals(ALLOWED_PARAMETERS[arrayCounter++])) {
                validParameter = true;
                break;
            }
        }

        if (!validParameter) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(trimmedArgs);
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void addAppointment(Appointment appointment) throws DuplicateAppointmentException {
        appointments.add(appointment);
    }

    //@author TeyXinHui
    /**
     * Removes all {@code Tag}s that are not used by any {@code Person} in this {@code AddressBook}.
     */
    private void removeUnusedTags() {
        Set<Tag> tagsInPersons = persons.asObservableList().stream()
                .map(Person::getTags)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        tags.setTags(tagsInPersons);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Chooses the sorting method based on the parameter given
     */
    public void sort(String parameter) {
        if (("name").equals(parameter)) {
            persons.sortNames();
        }
        if (("tag").equals(parameter)) {
            persons.sortTags();
        }
    }
```
###### \java\seedu\address\model\appointment\Appointment.java
``` java
/**
 * Represents an appointment in EduBuddy.
 */
public class Appointment {

    public static final String MESSAGE_APPOINTMENT_DATE_CONSTRAINTS = "Appointment date should be in DDMMYYYY format";
    public static final String MESSAGE_APPOINTMENT_START_TIME_CONSTRAINTS = "Appointment start time should be in "
            + "24 hour format";
    public static final String MESSAGE_APPOINTMENT_END_TIME_CONSTRAINTS = "Appointment end time should be in "
            + "24 hour format";
    public static final String APPOINTMENT_DATE_VALIDATION_REGEX = "^[0-9]{8}$";
    public static final String APPOINTMENT_START_TIME_VALIDATION_REGEX = "^[0-9]{4}$";
    public static final String APPOINTMENT_END_TIME_VALIDATION_REGEX = "^[0-9]{4}$";

    public final String date;
    public final String startTime;
    public final String endTime;

    public Appointment(String date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }


    /**
     * Returns true if a given string is a valid appointment date.
     */
    public static boolean isValidAppointmentDate(String date) {
        requireNonNull(date);
        return date.matches(APPOINTMENT_DATE_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid appointment start time.
     */
    public static boolean isValidAppointmentStartTime(String startTime) {
        requireNonNull(startTime);
        return startTime.matches(APPOINTMENT_START_TIME_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid appointment end time.
     */
    public static boolean isValidAppointmentEndTime(String endTime) {
        requireNonNull(endTime);
        return endTime.matches(APPOINTMENT_END_TIME_VALIDATION_REGEX);
    }
}
```
###### \java\seedu\address\model\appointment\exceptions\DuplicateAppointmentException.java
``` java
/**
 * Signals that the operation will result in duplicate Appointment objects.
 */
public class DuplicateAppointmentException extends DuplicateDataException {
    public DuplicateAppointmentException() {
        super("Operation would result in duplicate appointments");
    }
}
```
###### \java\seedu\address\model\appointment\UniqueAppointmentList.java
``` java
/**
 * A list of appointments that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Appointment#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueAppointmentList implements Iterable<Appointment> {

    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty AppointmentList.
     */
    public UniqueAppointmentList() {}

    public UniqueAppointmentList(Set<Appointment> appointments) {
        requireAllNonNull(appointments);
        internalList.addAll(appointments);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Appointment> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an Appointment to the list.
     *
     * @throws DuplicateAppointmentException if the Appointment to add is a duplicate of an existing Appointment
     * in the list.
     */
    public void add(Appointment toAdd) throws DuplicateAppointmentException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAppointmentException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    public void addAppointment(Appointment appointment) throws DuplicateAppointmentException {
        addressBook.addAppointment(appointment);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortPersonList(String parameter) {
        addressBook.sort(parameter);
    }

```
###### \java\seedu\address\ui\CalendarDisplay.java
``` java
/**
 * Calendar of the App
 */
public class CalendarDisplay {

    private static final String APPLICATION_NAME =  "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static HttpTransport HTTP_TRANSPORT;

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-java-quickstart");

    /** Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    private Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = Calendar.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =  GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    private Calendar getCalendarService() throws IOException {
        Credential credential = authorize();
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Creates an event on the Google Calendar
     * @throws IOException
     */
    public void createEvent(Appointment toAdd, Person selectedPerson) throws IOException {
        Calendar service = getCalendarService();

        Name name = selectedPerson.getName();

        Event event = new Event().setSummary(name.toString());

        DateTime startDateTime = new DateTime(formattedStartDateTime(toAdd));
        EventDateTime start = new EventDateTime().setDateTime(startDateTime);
        event.setStart(start);

        DateTime endDateTime = new DateTime(formattedEndDateTime(toAdd));
        EventDateTime end = new EventDateTime().setDateTime(endDateTime);
        event.setEnd(end);

        String calendarId = "primary";
        service.events().insert(calendarId, event).execute();
    }

    /**
     * Sets the required format for start time
     * @param toAdd appointment details provided by the user
     * @return the start time in the required format
     */
    private String formattedStartDateTime(Appointment toAdd) {
        String date = toAdd.getDate();
        String startTime = toAdd.getStartTime();
        return (date.substring(4, 8)).concat("-").concat(date.substring(2, 4)).concat("-")
                .concat(date.substring(0, 2)).concat("T").concat(startTime.substring(0, 2))
                .concat(":").concat(startTime.substring(2, 4)).concat(":00+08:00");
    }

    /**
     * Sets the required format for end time
     * @param toAdd appointment details provided by the user
     * @return the end time in the required format
     */
    private String formattedEndDateTime(Appointment toAdd) {
        String date = toAdd.getDate();
        String endTime = toAdd.getEndTime();
        return (date.substring(4, 8)).concat("-").concat(date.substring(2, 4)).concat("-")
                .concat(date.substring(0, 2)).concat("T").concat(endTime.substring(0, 2))
                .concat(":").concat(endTime.substring(2, 4)).concat(":00+08:00");
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Generates different colors for different tag labels
     *
     * @param tagName
     * @return a color style that is listed under tagColors array
     */
    private String generateTagColor(String tagName) {
        return tagColors[Math.abs(tagName.hashCode()) % tagColors.length];
    }

    /**
     * Creates the tag labels for {@code person} with different colors assigned to different tag names
     */
    private void setTags(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(generateTagColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

```
###### \resources\view\CalendarDisplay.html
``` html
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="DarkTheme.css">
    <meta http-equiv="refresh" content="1;url=https://calendar.google.com/calendar/embed?src=edubuddytest%40gmail.com&ctz=Asia%2FSingapore">
</head>
<body class="background">
</body>
</html>
```
###### \resources\view\DarkTheme.css
``` css
#tags .red {
    -fx-text-fill: black;
    -fx-background-color: red;
}

#tags .orange {
    -fx-text-fill: black;
    -fx-background-color: orange;
}

#tags .yellow {
    -fx-text-fill: black;
    -fx-background-color: yellow;
}

#tags .green {
    -fx-text-fill: black;
    -fx-background-color: green;
}

#tags .blue {
    -fx-text-fill: white;
    -fx-background-color: blue;
}

#tags .violet {
    -fx-text-fill: black;
    -fx-background-color: violet;
}

#tags indigo {
    -fx-text-fill: white;
    -fx-background-color: indigo;
}

#tags indigo {
    -fx-text-fill: white;
    -fx-background-color: purple;
}

#tags indigo {
    -fx-text-fill: black;
    -fx-background-color: pink;
}

#tags indigo {
    -fx-text-fill: white;
    -fx-background-color: grey;
}
```
