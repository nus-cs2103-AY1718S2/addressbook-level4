# cambioforma
###### /java/seedu/address/commons/events/model/AppointmentChangedEvent.java
``` java

/**
 * Indicates when a person's birthday has been changed
 */
public class AppointmentChangedEvent extends BaseEvent {


    public final Person data;

    public AppointmentChangedEvent(Person data, String command) {
        this.data = data;
        try {
            if (command.equals("add")) {
                addAppointmentToCalendar();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add birthday event of person to the Google Calendar
     */
    private void addAppointmentToCalendar() throws IOException {
        Calendar service = GoogleAuthentication.getCalendarService();

        Event event = new Event()
                .setSummary(data.getName().fullName)
                .setDescription("appointment");


        try {
            String startDateString = data.getBirthday().value;
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat requiredFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDateString = requiredFormat.format(originalFormat.parse(startDateString));
            DateTime startDateTime = new DateTime(startDateString);
            EventDateTime start = new EventDateTime()
                    .setDate(startDateTime);
            event.setStart(start);
            event.setEnd(start);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("popup").setMinutes(24 * 60)
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }
    @Override
    public String toString() {
        return "new birthday " + data.getBirthday();
    }
}

```
###### /java/seedu/address/commons/events/model/BirthdayChangedEvent.java
``` java
/**
 * Indicates when a person's birthday has been changed
 */
public class BirthdayChangedEvent extends BaseEvent {


    public final Person data;

    public BirthdayChangedEvent(Person data, String command) {
        this.data = data;
        try {
            if (command.equals("add")) {
                addBirthdayToCalendar();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add birthday event of person to the Google Calendar
     */
    private void addBirthdayToCalendar() throws IOException {
        Calendar service = GoogleAuthentication.getCalendarService();

        Event event = new Event()
                .setSummary(data.getName().fullName)
                .setDescription("birthday");


        try {
            String startDateString = data.getBirthday().value;
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat requiredFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDateString = requiredFormat.format(originalFormat.parse(startDateString));
            DateTime startDateTime = new DateTime(startDateString);
            EventDateTime start = new EventDateTime()
                    .setDate(startDateTime);
            event.setStart(start);
            event.setEnd(start);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("popup").setMinutes(24 * 60)
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String[] recurrence = new String[] {"RRULE:FREQ=YEARLY;"};
        event.setRecurrence(Arrays.asList(recurrence));

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }
    @Override
    public String toString() {
        return "new birthday " + data.getBirthday();
    }
}

```
###### /java/seedu/address/commons/events/model/GoogleAuthentication.java
``` java
/**
 * Authenticates with oAuth Google API
 */
public class GoogleAuthentication {
    /** Application name. */
    private static final String APPLICATION_NAME = "reInsurance";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-java-quickstart");

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
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException if error reading oAuth key client_secret.json
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                BirthdayChangedEvent.class.getResourceAsStream("/oAuth/client_secret.json");
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
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException if error reading oAuth key client_secret.json
     */
    public static Calendar getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
```
