# daviddalmaso
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Clears the reInsurance data
     */
    @FXML
    private void handleClear() {
        try {
            logic.execute("clear");
        } catch (CommandException | ParseException e) {
            logger.log(Level.WARNING, "Unable to clear the reInsurance data");
        }
    }
```
###### /java/seedu/address/commons/exceptions/PortfolioException.java
``` java
/**
 * Exception to handle all portfolio related exceptions when exporting
 */
public class PortfolioException extends Exception {
    public PortfolioException(String message) {
        super(message);
    }
}
```
###### /java/seedu/address/commons/exceptions/GoogleCalendarException.java
``` java
/**
 * Exception to handle all GoogleCalendar related exceptions
 */
public class GoogleCalendarException extends Exception {
    public GoogleCalendarException(String message) {
        super(message);
    }
}
```
###### /java/seedu/address/logic/parser/ExportCommandParser.java
``` java
/**
 * Parses input for correctly formatted export command
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        try {
            ExportType typeToExport = ParserUtil.parseExportType(args);
            if (typeToExport.equals(ExportType.PORTFOLIO)) {
                String filePath = ParserUtil.parseExportFilePath(args);
                return new ExportCommand(typeToExport, filePath);
            } else {
                return new ExportCommand(typeToExport);
            }
        } catch (IncorrectExportTypeException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * @param exportType
     * @return the corresponding ExportType
     * @throws IllegalArgumentException
     */
    public static ExportType parseExportType(String exportType) throws IllegalArgumentException {
        requireNonNull(exportType);
        String trimmedExportType = exportType.trim().toUpperCase();
        String[] allArgs = trimmedExportType.split(" ");
        String checkExportType = allArgs[0];
        if (!ExportType.isValidExportType(checkExportType)) {
            throw new IncorrectExportTypeException();
        }
        return ExportType.valueOf(checkExportType);
    }

    /**
     *
     * @param args args of a valid export portfolio command
     * @return the file path if specified, otherwise null
     * @throws IllegalArgumentException
     */
    public static String parseExportFilePath(String args) throws IllegalArgumentException {
        requireNonNull(args);
        String trimmedArgs = args.trim().toLowerCase();
        String[] pathArgs = trimmedArgs.split("portfolio", 2);
        if (pathArgs.length < 2) {
            return null;
        }
        String filePath = pathArgs[1].trim();
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }
        return filePath + ".csv";
    }
}
```
###### /java/seedu/address/logic/commands/CountCommand.java
``` java
/**
 * Return a count of all persons in the ClientBook
 */
public class CountCommand extends Command {

    public static final String COMMAND_WORD = "count";

    public static final String MESSAGE_SUCCESS = " persons in the address book";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(Integer.toString(model.getAddressBook().getPersonList().size()) + MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/ExportCommand.java
``` java
/**
 * Export different types of data from the application to the user
 */
public class ExportCommand extends UndoableCommand {

    public static final String CALENDAR_ACCESS_DENIED_MESSAGE =
            "Unable to export calendar: access denied";

    public static final String CALENDAR_MESSAGE_SUCCESS =
            "Successfully exported birthdays and appointments to Google Calendar";

    public static final String COMMAND_WORD = "export";

    public static final String CONNECTIVITY_ISSUE_MESSAGE =
            "Unable to export calendar: no internet connection";

    public static final String INVALID_FILE_MESSAGE =
            "Unable to export portfolio: invalid filename";

    public static final String PORTFOLIO_MESSAGE_SUCCESS =
            "Successfully exported portfolio to %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the specified type of content.\n"
            + "Parameters: ExportType (must be one of the following - calendar, portfolio)\n"
            + "Example: " + COMMAND_WORD + " portfolio";


    private static final Logger logger = LogsCenter.getLogger(ExportCommand.class);

    public final UserPrefs userPrefs = new UserPrefs();

    private ExportType typeToExport;
    private String filePath;

    public ExportCommand(ExportType typeToExport) {
        this.typeToExport = typeToExport;
        this.filePath = userPrefs.getExportPortfolioFilePath();
    }

    public ExportCommand(ExportType typeToExport, String filePath) {
        this.typeToExport = typeToExport;
        if (filePath == null || filePath.isEmpty()) {
            this.filePath = userPrefs.getExportPortfolioFilePath();
        } else {
            this.filePath = filePath;
        }
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        requireNonNull(typeToExport);
        try {
            if (typeToExport.equals(ExportType.PORTFOLIO)) {
                model.exportPortfolio(filePath);
                logger.info("Successfully exported portfolio");
                return new CommandResult(String.format(PORTFOLIO_MESSAGE_SUCCESS,
                        filePath));
            } else {
                model.exportCalendar();
                logger.info("Successfully exported calendar");
                return new CommandResult(CALENDAR_MESSAGE_SUCCESS);
            }
        } catch (InvalidFileNameException e) {
            logger.info("Unable to use " + filePath + " for export file path");
            throw new CommandException(INVALID_FILE_MESSAGE);
        } catch (CalendarAccessDeniedException e) {
            logger.info("Did not receive authorization for Google Calendar usage");
            throw new CommandException(CALENDAR_ACCESS_DENIED_MESSAGE);
        } catch (ConnectivityIssueException e) {
            logger.info("Unable to connect to the internet to export calendar");
            throw new CommandException(CONNECTIVITY_ISSUE_MESSAGE);
        }
    }
}
```
###### /java/seedu/address/model/util/GoogleCalendarClient.java
``` java
/**
 * Client for the Google Calendar API
 */
public class GoogleCalendarClient {

    private static final Logger logger = LogsCenter.getLogger(GoogleCalendarClient.class);

    /** Application name. */
    private static final String applicationName =
            "reInsurance Events";

    /** Global instance of the JSON factory. */
    private static final JsonFactory jsonFactory =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the scopes required by this application.
     *
     */
    private static final List<String> SCOPES =
            Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Checks internet connectivity of application.
     * Export calendar can only be run with internet access
     * @throws ConnectivityIssueException
     */
    public static void checkInternetConnection() throws ConnectivityIssueException {
        try {
            InetAddress byName = InetAddress.getByName("www.google.com");
            if (!byName.isReachable(1000)) {
                throw new ConnectivityIssueException();
            }
            logger.info("Connected to internet");
        } catch (IOException e) {
            logger.warning("Unable to connect to internet");
            throw new ConnectivityIssueException();
        }
    }

    /**
     * Authorizes a user using the authorization code flow supported by Google OAuth API
     * @return a Credential object
     */
    public static Credential authorize() throws CalendarAccessDeniedException, ConnectivityIssueException {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    GoogleCalendarClient.class.getResourceAsStream("/oAuth/client_secret.json"));
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
                    inputStreamReader);
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, jsonFactory, clientSecrets, SCOPES
            ).setAccessType("offline").build();
            checkInternetConnection();
            Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
                    .authorize("user");
            logger.info("Received user authorization");
            return credential;
        } catch (IOException e) {
            logger.info("User denied authorization");
            throw new CalendarAccessDeniedException();
        }
    }

    /**
     * Builds calendar service
     * @return Calendar object
     */
    public static Calendar getCalendarService()
            throws CalendarAccessDeniedException, ConnectivityIssueException {
        Credential credential = authorize();
        return new Calendar.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(applicationName).build();
    }

    /**
     * @param date string in "dd-mm-yyyy" format
     * @return string in "yyyy-mm-dd" format
     */
    private static String formatDate(String date) {
        String[] dates = date.split("-");
        return dates[2] + "-" + dates[1] + "-" + dates[0];
    }

    /**
     * @param date date of the event
     * @return an EventDateTime object for the specified date
     */
    private static EventDateTime createEventDateTime(String date) {
        DateTime dateTime = new DateTime(formatDate(date));
        return new EventDateTime().setDate(dateTime);
    }

    /**
     * @param event event to set start and end dateTimes
     * @param date date to use for the event
     */
    private static void setEventDates(Event event, String date) {
        EventDateTime eventDateTime = createEventDateTime(date);
        event.setStart(eventDateTime).setEnd(eventDateTime);
    }

    /**
     * @param person a Person object
     * @return an Event for their birthday
     */
    private static Event createBirthdayEvent(Person person) {
        Event birthday = new Event()
                .setSummary(person.getName().fullName + "'s Birthday");
        setEventDates(birthday, person.getBirthday().value);
        String[] reccurrence = new String[] {"RRULE:FREQ=YEARLY;COUNT=100"};
        birthday.setRecurrence(Arrays.asList(reccurrence));
        return birthday;
    }

    /**
     * @param person a Person object
     * @return an Event for their appointment
     */
    private static Event createAppointmentEvent(Person person) {
        Event appointment = new Event()
                .setSummary("Appointment with " + person.getName().fullName);
        setEventDates(appointment, person.getAppointment().value);
        return appointment;
    }

    /**
     *
     * @param persons UniquePersonList: all Person objects in the reInsurance application
     * @return returns a list of events, being the birthdays and appointments of each person
     */
    private static List<Event> createEvents(UniquePersonList persons) {
        List<Event> events = new ArrayList<>();

        // Iterate through persons to get their birthdays and appointments
        for (Person person : persons) {
            Event birthday = createBirthdayEvent(person);
            Event appointment = createAppointmentEvent(person);

            events.add(birthday);
            events.add(appointment);
        }

        return events;
    }

    /**
     * @param persons UniquePersonList to make the Calendar out of
     * @throws Exception if the Google API Client fails
     */
    public static void insertCalendar(UniquePersonList persons)
            throws CalendarAccessDeniedException, ConnectivityIssueException {
        Calendar service = getCalendarService();

        String existingCalendarId = getExistingCalendarId(service, "reInsurance Events");

        try {
            // If the reInsurance Events calendar already exists, delete it
            if (existingCalendarId != null) {
                service.calendars().delete(existingCalendarId).execute();
            }

            // Create a new calendar
            com.google.api.services.calendar.model.Calendar calendar =
                    new com.google.api.services.calendar.model.Calendar();
            calendar.setSummary("reInsurance Events");
            calendar.setTimeZone("Asia/Singapore");

            // Insert the new calendar into the Google Account
            com.google.api.services.calendar.model.Calendar createdCalendar =
                    service.calendars().insert(calendar).execute();

            // Get created calendar Id
            String calendarId = createdCalendar.getId();

            // Insert events into create calendar
            List<Event> events = createEvents(persons);
            for (Event event : events) {
                event = service.events().insert(calendarId, event).execute();
                logger.info("Event " + event.getSummary() + " added to calendar " + createdCalendar.getSummary());
            }
        } catch (IOException e) {
            logger.warning("Unable to insert reInsurance Events calendar");
        }
    }

    public static String getExistingCalendarId(Calendar service, String calendarSummary) {
        try {
            // Get list of calendars that user has
            List<CalendarListEntry> calendarList = service.calendarList().list().execute().getItems();

            // Iterate through the list of calendars to find the calendar id of the calendarSummary that matches
            for (CalendarListEntry calendarListEntry : calendarList) {
                if (calendarListEntry.getSummary().compareTo(calendarSummary) == 0) {
                    return calendarListEntry.getId();
                }
            }
        } catch (IOException e) {
            logger.warning("Unable to get list of calendars owned by user");
        }

        return null;
    }
}

```
###### /java/seedu/address/model/UserPrefs.java
``` java
    public String getExportPortfolioFilePath() {
        return exportPortfolioFilePath;
    }

    public void setExportPortfolioFilePath(String exportPortfolioFilePath) {
        this.exportPortfolioFilePath = exportPortfolioFilePath;
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java

    /**
     * Transfers persons in reInsurance data to portfolio data
     * @return a String representing the portfolio as a csv
     */
    private String portfolioToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name,Phone,Email,Address,Birthday,Appointment,Group,Total Commission,Insurances,Tags\n");
        for (Person person : persons) {
            sb.append("\"" + person.getName().toString() + "\",");
            sb.append("\"" + person.getPhone().toString() + "\",");
            sb.append("\"" + person.getEmail().toString() + "\",");
            sb.append("\"" + person.getAddress().toString() + "\",");
            sb.append("\"" + person.getBirthday().toString() + "\",");
            sb.append("\"" + person.getAppointment().toString() + "\",");
            sb.append("\"" + person.getGroup().toString() + "\",");
            sb.append("\"" + person.getTotalCommission() + "\",");
            sb.append("\"" + person.getInsurance().toString() + "\",");
            sb.append("\"" + person.getTags().toString() + "\",");
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Exports the current reInsurance data to the specified file path
     * @param filePath the file path to export the portfolio to
     */
    public void exportPortfolio(String filePath) throws InvalidFileNameException {
        try {
            PrintWriter pw = new PrintWriter(new File(filePath));
            String portfolioAsString = portfolioToString();
            pw.write(portfolioAsString);
            pw.close();
        } catch (FileNotFoundException e) {
            throw new InvalidFileNameException();
        }
    }

    /**
     * Exports the calendar events to the user's Google Calendar
     */
    public void exportCalendar() throws CalendarAccessDeniedException, ConnectivityIssueException {
        GoogleCalendarClient.insertCalendar(persons);
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void exportCalendar() throws CalendarAccessDeniedException, ConnectivityIssueException {
        addressBook.exportCalendar();
    }

    @Override
    public void exportPortfolio(String filePath) throws InvalidFileNameException {
        requireNonNull(filePath);
        addressBook.exportPortfolio(filePath);
    }
```
###### /java/seedu/address/model/export/exceptions/InvalidFileNameException.java
``` java
/**
 * Exception class to handle invalid file names during portfolio export
 */
public class InvalidFileNameException extends PortfolioException {
    public InvalidFileNameException() {
        super("Invalid file name to use for exporting portfolio");
    }
}
```
###### /java/seedu/address/model/export/exceptions/CalendarAccessDeniedException.java
``` java
/**
 * Exception for handling users that do not want to allow access to their Google Calendar
 */
public class CalendarAccessDeniedException extends GoogleCalendarException {
    public CalendarAccessDeniedException() {
        super("Access denied to calendar");
    }
}
```
###### /java/seedu/address/model/export/exceptions/ConnectivityIssueException.java
``` java
/**
 * Exception to handle users trying to export calendar without internet connectivity
 */
public class ConnectivityIssueException extends GoogleCalendarException {
    public ConnectivityIssueException() {
        super("Unable to connect to the internet");
    }
}
```
###### /java/seedu/address/model/export/ExportType.java
``` java
/**
 * Types of valid exports that the user can use
 */
public enum ExportType {
    CALENDAR, PORTFOLIO;

    /**
     * @param exportType user's inputted ExportType
     * @return whether it is a valid ExportType
     */
    public static boolean isValidExportType(String exportType) {
        try {
            ExportType.valueOf(exportType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
```
