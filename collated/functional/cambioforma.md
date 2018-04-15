# cambioforma
###### /java/seedu/address/MainApp.java
``` java
    /**
     * Initialises Google Calendar Settings for {@code BrowserPanel}
     */
    private void initGoogleCalendar() {
        GoogleCalendar calendar = new GoogleCalendar();
        try {
            calendar.getCalendarId();
            calendar.resetCalendar();
            logger.info("Reinitialised a new Google Calendar");
        } catch (IOException e) {
            try {
                GoogleCalendarInit.init();
            } catch (IOException ex) {
                logger.severe("Failed to initialise Google Calendar");
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```
###### /java/seedu/address/model/calendar/GoogleCalendarInit.java
``` java

/**
 * Initialise a new Google Calendar for the current user of reInsurance
 */
public class GoogleCalendarInit {
    private static final Logger logger = LogsCenter.getLogger(GoogleCalendarInit.class);

    /**
     * Creates a new calendar in Google Calendar for birthday and appointment event
     * @throws IOException Unable to create Google Calendar
     */
    public static void init() throws IOException {
        com.google.api.services.calendar.Calendar service = GoogleCalendarApiAuthentication.getCalendarService();

        // Create a new calendar
        Calendar calendar = new Calendar();
        calendar.setSummary("reInsurance");
        calendar.setTimeZone("Asia/Singapore");

        // Insert the new calendar
        Calendar createdCalendar = service.calendars().insert(calendar).execute();
        logger.info("New Google Calendar Created:" + createdCalendar.getId());

        writeCalendarIdToFile(createdCalendar.getId());

        // Create access rule with associated scope
        AclRule rule = new AclRule();
        AclRule.Scope scope = new AclRule.Scope();
        scope.setType("default");
        rule.setScope(scope).setRole("reader");

        // Insert new access rule
        AclRule createdRule = service.acl().insert(createdCalendar.getId(), rule).execute();
        logger.fine("Added read rule to calendar: " + createdRule.getId());
    }

    /**
     * Saves the calendar Id to a text file
     * @param calendarId Id of Calendar of reInsurance in Google Calendar
     */
    private static void writeCalendarIdToFile (String calendarId) {
        try {
            UserPrefs userPrefs = new UserPrefs();
            File f = new File("data");
            f.mkdir();
            PrintWriter writer = new PrintWriter(new File(userPrefs.getCalendarIdFilePath()));
            writer.print(calendarId);
            writer.close();
            logger.info("Calendar ID written to: " + userPrefs.getCalendarIdFilePath());
        } catch (FileNotFoundException e) {
            logger.severe("Unable to write new calendar id to file");
        }
    }
}
```
###### /java/seedu/address/model/calendar/GoogleCalendar.java
``` java

/**
 * Class to handle insert, update and delete operations to Google Calendar
 */
public class GoogleCalendar {

    private static final Logger logger = LogsCenter.getLogger(GoogleCalendar.class);
    private static final String APPOINTMENT_DESCRIPTION = "Appointment";
    private static final String BIRTHDAY_DESCRIPTION = "Birthday";
    private Person data;

    public String getCalendarId() throws IOException {
        UserPrefs userPrefs = new UserPrefs();
        byte[] encoded = Files.readAllBytes(Paths.get(userPrefs.getCalendarIdFilePath()));
        return new String(encoded, "UTF-8");
    }

    /**
     * Adds the appointment of a {@code Person} to the Google Calendar
     * @param data {@code Person} to be added to the Google Calendar
     */
    public void addPersonToCalendar(Person data) {
        this.data = data;
        try {
            addBirthdayEvent();
            if (data.getAppointment() != null) {
                addAppointmentEvent();
            }
        } catch (IOException e) {
            logger.warning("Unable to add Event");
        }
    }

    /**
     * Adds an appointment {@code Event} to the Google Calendar
     * @throws IOException Unable to create calendarId from file
     */
    private void addAppointmentEvent() throws IOException {
        Event event = createAppointmentEvent();
        Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
        String calendarId = this.getCalendarId();
        event = service.events().insert(calendarId, event).execute();
        logger.info("Event created: " + event.getHtmlLink());
    }

    /**
     * Creates a Google Calendar Event for appointment
     * @return Returns an {@code Event} of appointment of {@code Person}
     */
    private Event createAppointmentEvent() {
        Event event = new Event()
            .setSummary(data.getName().fullName)
            .setDescription(APPOINTMENT_DESCRIPTION);

        String startDateString = getAppointmentDateFromPerson();
        DateTime startDateTime = new DateTime(startDateString);
        EventDateTime start = new EventDateTime().setDate(startDateTime);
        event.setStart(start);
        event.setEnd(start);

        EventReminder[] reminderOverrides = new EventReminder[] {
            new EventReminder().setMethod("popup").setMinutes(24 * 60)
        };
        Event.Reminders reminders = new Event.Reminders()
            .setUseDefault(false)
            .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);
        return event;
    }
    /**
     * Adds an appointment {@code Event} to the Google Calendar
     * @throws IOException Unable to create calendarId from file
     */
    private void addBirthdayEvent() throws IOException {
        Event event = createBirthdayEvent();
        Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
        String calendarId = this.getCalendarId();
        event = service.events().insert(calendarId, event).execute();
        logger.info("Event created: " + event.getHtmlLink());
    }
    /**
     * Creates a Google Calendar Event for birthday
     * @return Returns an {@code Event} of birthday of {@code Person}
     */
    private Event createBirthdayEvent() {
        Event event = new Event()
                .setSummary(data.getName().fullName)
                .setDescription(BIRTHDAY_DESCRIPTION);

        String startDateString = getBirthdayDateFromPerson();
        DateTime startDateTime = new DateTime(startDateString);
        EventDateTime start = new EventDateTime().setDate(startDateTime);
        event.setStart(start);
        event.setEnd(start);

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("popup").setMinutes(24 * 60)
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String[] recurrence = new String[] {"RRULE:FREQ=YEARLY;"};
        event.setRecurrence(Arrays.asList(recurrence));

        return event;
    }

    private String getAppointmentDateFromPerson() {
        try {
            String startDateString = data.getAppointment().value;
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat requiredFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDateString = requiredFormat.format(originalFormat.parse(startDateString));
            return startDateString;
        } catch (ParseException e) {
            logger.warning("Unable to convert date format for appointment");
        }
        return null;
    }

    private String getBirthdayDateFromPerson() {
        try {
            String startDateString = data.getBirthday().value;
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat requiredFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDateString = requiredFormat.format(originalFormat.parse(startDateString));
            return startDateString;
        } catch (ParseException e) {
            logger.warning("Unable to convert date format for birthday");
        }
        return null;
    }

    /**
     * Clear all events from the Google Calendar
     * @throws IOException Unable to read calendarId from file
     */
    public void resetCalendar() {
        try {
            Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
            String calendarId = this.getCalendarId();
            service.calendars().delete(calendarId).execute();
            GoogleCalendarInit.init();
        } catch (IOException e) {
            e.printStackTrace();
            logger.warning("Unable to delete calendar");
        }
    }

    /**
     * Deletes the appointment of a {@code Person} from the Google Calendar
     * @param data {@code Person} to be deleted from the Google Calendar
     */
    public void removePersonFromCalendar(Person data) {
        this.data = data;
        try {
            deleteBirthdayEvent();
            if (data.getAppointment() != null) {
                deleteAppointmentEvent();
            }
        } catch (IOException e) {
            logger.warning("Deleting appointment event failed");
        }
    }

    /**
     * Deletes an appoinement {@code Event} from the Google Calendar
     * @throws IOException Deleting appointment event failed
     */
    private void deleteAppointmentEvent() throws IOException {
        Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
        // Iterate over the events in the specified calendar
        String pageToken = null;
        do {
            Events events = service.events().list(this.getCalendarId()).setPageToken(pageToken).execute();
            List<Event> items = events.getItems();
            for (Event event : items) {
                if (event.getDescription().equals(APPOINTMENT_DESCRIPTION)
                    && event.getSummary().equals(data.getName().fullName)) {
                    service.events().delete(this.getCalendarId(), event.getId()).execute();
                    logger.fine("Appointment deleted: " + event.getId());
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
    }

    /**
     * Deletes a birthday {@code Event} from the Google Calendar
     * @throws IOException Deleting birthday event failed
     */
    private void deleteBirthdayEvent() throws IOException {
        Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
        // Iterate over the events in the specified calendar
        String pageToken = null;
        do {
            Events events = service.events().list(this.getCalendarId()).setPageToken(pageToken).execute();
            List<Event> items = events.getItems();
            for (Event event : items) {
                if (event.getDescription().equals(BIRTHDAY_DESCRIPTION)
                    && event.getSummary().equals(data.getName().fullName)) {
                    service.events().delete(this.getCalendarId(), event.getId()).execute();
                    logger.fine("Birthday deleted: " + event.getId());
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
    }

    /**
     * Edits a Google Calendar Event for Appointment
     * @param target Target {@code Person} to be edited
     * @param data New {@code Person} be edited into the Google Calendar
     */
    public void updatePersonToCalendar(Person target, Person data) {
        this.data = data;
        if (!target.getBirthday().equals(data.getBirthday())) {
            try {
                editBirthdayEvent(target);
            } catch (IOException e) {
                logger.warning("Failed to edit birthday event in Google Calendar");
            }
        }
        if (!target.getAppointment().equals(data.getAppointment())) {
            try {
                editAppointmentEvent(target);
            } catch (IOException e) {
                logger.warning("Failed to edit appointment event in Google Calendar");
            }
        }
        if (!target.getName().fullName.equals((data.getName().fullName))) {
            try {
                editEventsSummary(target);
            } catch (IOException e) {
                logger.warning("Failed to edit Person name in Google Calendar");
            }
        }
    }

    /**
     * Edits an appointment {@code Event} from the Google Calendar
     * @param target Target {@code Person} to be edited
     * @throws IOException
     */
    private void editAppointmentEvent(Person target) throws IOException {
        Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
        // Iterate over the events in the specified calendar
        String pageToken = null;
        do {
            Events events = service.events().list(this.getCalendarId()).setPageToken(pageToken).execute();
            List<Event> items = events.getItems();
            for (Event event : items) {
                if (event.getDescription().equals(APPOINTMENT_DESCRIPTION)
                    && event.getSummary().equals(target.getName().fullName)) {
                    String startDateString = getAppointmentDateFromPerson();
                    DateTime startDateTime = new DateTime(startDateString);
                    EventDateTime start = new EventDateTime().setDate(startDateTime);
                    event.setStart(start);
                    event.setEnd(start);
                    service.events().update(this.getCalendarId(), event.getId(), event).execute();
                    logger.fine("Appointment updated: " + event.getId());
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
    }
    /**
     * Edits an appointment {@code Event} from the Google Calendar
     * @param target Target {@code Person} to be edited
     * @throws IOException
     */
    private void editBirthdayEvent(Person target) throws IOException {
        Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
        // Iterate over the events in the specified calendar
        String pageToken = null;
        do {
            Events events = service.events().list(this.getCalendarId()).setPageToken(pageToken).execute();
            List<Event> items = events.getItems();
            for (Event event : items) {
                if (event.getDescription().equals(BIRTHDAY_DESCRIPTION)
                    && event.getSummary().equals(target.getName().fullName)) {
                    String startDateString = getBirthdayDateFromPerson();
                    DateTime startDateTime = new DateTime(startDateString);
                    EventDateTime start = new EventDateTime().setDate(startDateTime);
                    event.setStart(start);
                    event.setEnd(start);
                    service.events().update(this.getCalendarId(), event.getId(), event).execute();
                    logger.fine("Birthday updated: " + event.getId());
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
    }
    /**
     * Edits an appointment {@code Event} from the Google Calendar
     * @param target Target {@code Person} to be edited
     * @throws IOException
     */
    private void editEventsSummary(Person target) throws IOException {
        Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
        // Iterate over the events in the specified calendar
        String pageToken = null;
        do {
            Events events = service.events().list(this.getCalendarId()).setPageToken(pageToken).execute();
            List<Event> items = events.getItems();
            for (Event event : items) {
                if (event.getSummary().equals(target.getName().fullName)) {
                    event.setSummary(data.getName().fullName);
                    service.events().update(this.getCalendarId(), event.getId(), event).execute();
                    logger.fine("Name updated: " + event.getId());
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
    }
}
```
###### /java/seedu/address/model/calendar/GoogleCalendarApiAuthentication.java
``` java
/**
 * Authenticates with oAuth Google API
 */
public class GoogleCalendarApiAuthentication {
    private static final Logger logger = LogsCenter.getLogger(GoogleCalendarApiAuthentication.class);
    /** Application name. */
    private static final String APPLICATION_NAME = "reInsurance";

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the scopes required by this quickstart. */
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
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
    private static GoogleCredential authorize() {
        try {
            InputStream in = GoogleCalendarApiAuthentication.class
                    .getResourceAsStream("/oAuth/reInsurance-2f4f33627950.json");
            return GoogleCredential.fromStream(in).createScoped(SCOPES);
        } catch (IOException e) {
            logger.severe("Unable to create oAuth key from file");
            return null;
        }
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException if error reading oAuth key client_secret.json
     */
    public static Calendar getCalendarService() {
        Credential credential = authorize();
        return new Calendar.Builder(
                httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

}
```
###### /java/seedu/address/model/person/PersonContainsTagsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class PersonContainsTagsPredicate extends ComponentManager implements Predicate<Person> {
    private final List<String> keywords;

    public PersonContainsTagsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        for (Iterator<String> i = keywords.iterator(); i.hasNext();) {
            String keyword = i.next();
            UniqueTagList personTagList = new UniqueTagList(person.getTags());
            if (Tag.isValidTagName(keyword)) {
                Tag keyTag = new Tag(keyword);
                if (personTagList.contains(keyTag)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsTagsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsTagsPredicate) other).keywords)); // state check
    }

}
```
