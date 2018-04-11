# daviddalmaso
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

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the specified type of content.\n"
            + "Parameters: ExportType (must be one of the following - calendar, portfolio)\n"
            + "Example: " + COMMAND_WORD + " portfolio";

    public static final String PORTFOLIO_MESSAGE_SUCCESS =
            "Successfully exported portfolio to %1$s";

    public static final String CALENDAR_MESSAGE_SUCCESS =
            "Successfully exported birthdays and appointments to Google Calendar";

    public final UserPrefs userPrefs = new UserPrefs();

    private ExportType typeToExport;

    public ExportCommand(ExportType typeToExport) {
        this.typeToExport = typeToExport;
    }

    @Override
    protected CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.export(typeToExport);
        if (typeToExport.equals(ExportType.PORTFOLIO)) {
            return new CommandResult(String.format(PORTFOLIO_MESSAGE_SUCCESS,
                    userPrefs.getExportPortfolioFilePath()));
        } else {
            return new CommandResult(CALENDAR_MESSAGE_SUCCESS);
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
    /** Application name. */
    private static final String applicationName =
            "reInsurance Events";

    /** Directory to store user credentials for this application. */
    private static final java.io.File dataStoreDir = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory dataStoreFactory;

    /** Global instance of the JSON factory. */
    private static final JsonFactory jsonFactory =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(dataStoreDir);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     *
     * @return a Credential object
     */
    public static Credential authorize() {
        try {
            InputStream in = GoogleCalendarClient.class.getResourceAsStream("/client_secret.json");
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
                    inputStreamReader);
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, jsonFactory, clientSecrets, SCOPES
            ).setDataStoreFactory(dataStoreFactory).build();

            return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private static void setEventDates (Event event, String date) {
        String[] dates = date.split("-");
        String day = dates[0];
        String month = dates[1];
        String year = dates[2];

        DateTime startDateTime = new DateTime(year + "-" + month + "-" + day + "T00:00:00+08:00");
        EventDateTime startEventDateTime = new EventDateTime().setDateTime(startDateTime).setTimeZone("Asia/Singapore");

        DateTime endDateTime = new DateTime(year + "-" + month + "-" + day + "T23:59:59+08:00");
        EventDateTime endEventDateTime = new EventDateTime().setDateTime(endDateTime).setTimeZone("Asia/Singapore");

        event.setStart(startEventDateTime).setEnd(endEventDateTime);
    }

    /**
     *
     * @param persons UniquePersonList: all Person objects in the address book
     * @return returns a list of events, being the birthdays and appointments of each person
     */
    private static List<Event> createEvents(UniquePersonList persons) {
        List<Event> events = new ArrayList<>();

        // Iterate through persons to get their birthdays and appointments
        for (Person person : persons) {
            String name = person.getName().fullName;

            // Create birthday event for current Person object
            Event birthday = new Event()
                    .setSummary(name + "'s Birthday");

            String birthdayDate = person.getBirthday().value;
            setEventDates(birthday, birthdayDate);
            String[] recc = new String[] {"RRULE:FREQ=YEARLY;COUNT=100"};
            birthday.setRecurrence(Arrays.asList(recc));

            // Create appointment event for current Person object
            Event appointment = new Event()
                    .setSummary("Appointment with " + name);

            String appointmentDate = person.getAppointment().value;
            setEventDates(appointment, appointmentDate);

            events.add(birthday);
            events.add(appointment);
        }

        return events;
    }

    /**
     * @param persons UniquePersonList to make the Calendar out of
     * @throws Exception if the Google API Client fails
     */
    public static void insertCalendar(UniquePersonList persons) throws Exception {
        Credential credentials = GoogleCalendarClient.authorize();

        Calendar service = new Calendar.Builder(httpTransport, jsonFactory, credentials)
                .setApplicationName(applicationName).build();

        String existingCalendarId = getExistingCalendarId(service, "reInsurance Events");

        // If the reInsurance Events calendar already exists, delete it
        if (existingCalendarId != null) {
            service.calendars().delete(existingCalendarId).execute();
        }

        // Create a new calendar
        com.google.api.services.calendar.model.Calendar calendar =
                new com.google.api.services.calendar.model.Calendar();
        calendar.setSummary("reInsurance Events");
        calendar.setTimeZone("Asia/Singapore");

        // Insert the new calendar
        com.google.api.services.calendar.model.Calendar createdCalendar =
                service.calendars().insert(calendar).execute();

        // Get created calendar Id
        String calendarId = createdCalendar.getId();

        // Insert events into create calendar
        List<Event> events = createEvents(persons);
        for (Event event : events) {
            service.events().insert(calendarId, event).execute();
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
        } catch (Exception e) {
            System.out.println("Unable to get CalendarList from Google Calendar");
            System.out.println(e);
        }

        return null;
    }
}

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Exports the current address book to data/portfolio.csv
     */
    public void exportPortfolio() {
        try {
            PrintWriter pw = new PrintWriter(new File(userPrefs.getExportPortfolioFilePath()));
            StringBuilder sb = new StringBuilder();
            sb.append("Name,Phone,Email,Address,Tags\n");
            for (Person person : persons) {
                sb.append("\"" + person.getName().toString() + "\"");
                sb.append(",");
                sb.append("\"" + person.getPhone().toString() + "\"");
                sb.append(",");
                sb.append("\"" + person.getEmail().toString() + "\"");
                sb.append(",");
                sb.append("\"" + person.getAddress().toString() + "\"");
                sb.append(",");
                sb.append("\"" + person.getTags().toString() + "\"");
                sb.append("\n");
            }
            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        }
    }

    /**
     * Exports the calendar events to the user's Google Calendar
     */
    public void exportCalendar() {
        List<Birthday> birthdays = new ArrayList<>();
        List<Appointment> appointments = new ArrayList<>();
        for (Person person : persons) {
            birthdays.add(person.getBirthday());
            appointments.add(person.getAppointment());
        }
        try {
            GoogleCalendarClient.insertCalendar(persons);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void export(ExportType typeToExport) {
        requireAllNonNull(typeToExport);
        if (typeToExport.equals(ExportType.PORTFOLIO)) {
            addressBook.exportPortfolio();
        }
        if (typeToExport.equals(ExportType.CALENDAR)) {
            addressBook.exportCalendar();
        }
    }
```
###### /java/seedu/address/model/export/exceptions/IncorrectExportTypeException.java
``` java
/**
 * Exception to handle when the given export type is not valid
 */
public class IncorrectExportTypeException extends IllegalArgumentException {

    public IncorrectExportTypeException() {
        super("Unable to map given argument to a valid Export Type");
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
