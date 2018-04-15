# demitycho
###### \java\seedu\address\commons\events\model\ScheduleChangedEvent.java
``` java
/**
 * Indicates the AddressBook in the model has changed
 * */
public class ScheduleChangedEvent extends BaseEvent {

    private final ReadOnlySchedule schedule;
    private final ReadOnlyAddressBook addressBook;

    public ScheduleChangedEvent(ReadOnlySchedule schedule, ReadOnlyAddressBook addressBook) {
        this.schedule = schedule;
        this.addressBook = addressBook;
    }

    @Override
    public String toString() {
        return "number of lessons " + schedule.getSchedule().size();
    }

    public final ReadOnlySchedule getLessons() {
        return this.schedule;
    }

    public final ReadOnlyAddressBook getAddressBook() {
        return this.addressBook;
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowScheduleEvent.java
``` java
 * Indicates a request to show the milestones in a student's dashboard
 */
public class ShowScheduleEvent extends BaseEvent {

    private ReadOnlySchedule schedule;
    private ReadOnlyAddressBook addressBook;
    public ShowScheduleEvent(ReadOnlySchedule schedule, ReadOnlyAddressBook addressBook) {
        this.schedule = schedule;
        this.addressBook = addressBook;
    }

    public ReadOnlySchedule getSchedule() {
        return this.schedule;
    }

    public ReadOnlyAddressBook getAddressBook() {
        return this.addressBook;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\external\exceptions\CredentialsException.java
``` java
/**
 * An exception related to stored Google credentials
 */
public class CredentialsException extends Exception {
    /**
     * Returns a CredentialsExceptions when misuse happens, when
     * calling functions involving credentials
     * @param message
     */
    public CredentialsException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\external\GCalendarService.java
``` java

/**
 * Constructs a new GContactsService object to communicate with Google's APIs
 */
public class GCalendarService {
    public static final String MESSAGE_SUMMARY = "Lesson with %1$s";
    public static final String SINGAPORE_TIME_ZONE = "Asia/Singapore";
    public static final String STRING_COLON = ":";
    public static final String STRING_DASH = "-";
    public static final String STRING_GMT_SG = "+08:00";
    public static final String STRING_STUDENT_CALENDAR_DESCRIPTION = "Calendar for your Student Lessons";
    public static final String STRING_STUDENT_CALENDAR_NAME = "Student Lessons";

    private static final Logger logger = LogsCenter.getLogger(GCalendarService.class);

    private Credential credential;
    private HttpTransport httpTransport;
    private JsonFactory jsonFactory;

    public GCalendarService() {}

    public GCalendarService(Credential credential,
                            HttpTransport httpTransport, JsonFactory jsonFactory) {
        this.credential = credential;
        this.httpTransport = httpTransport;
        this.jsonFactory = jsonFactory;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setHttpTransport(HttpTransport httpTransport) {
        this.httpTransport = httpTransport;
    }

    public HttpTransport getHttpTransport() {
        return httpTransport;
    }

    public void setJsonFactory(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

    public JsonFactory getJsonFactory() {
        return jsonFactory;
    }

    /**
     * Synchronizes the user's Google Calendar with the local version
     * @param schedule
     */
    public void synchronize(ReadOnlySchedule schedule, ReadOnlyAddressBook addressBook) {
        Calendar service = new Calendar.Builder(
                httpTransport, jsonFactory, credential)
                .setApplicationName(GServiceManager.APPLICATION_NAME)
                .build();
        /**
         * 1. Clear all old Calendar events
         *  a) Get the old "Student Lesson" Calendar ID from CalenderList
         *  b) Delete all Events of that CalendarID
         * 2. Create a new "Student Lesson" Calendar
         * 3. Insert new schedule
         */
        try {
            Integer displayIndex = 1;
            deleteExistingCalendarCopy(service);
            String newCalenderId = createStudentLessonCalendar(service);
            for (Lesson lesson : schedule.getSchedule()) {
                Event newEvent = lessonToCalendarEvent(lesson, addressBook);
                Event insertedEvent = service.events().insert(newCalenderId, newEvent).execute();
                logger.info(String.format("Event created for Lesson %d: %s\n",
                        displayIndex++, insertedEvent.getHtmlLink()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Successfully updated Google Calendar.");
    }

    /**
     * Converts a lesson to a recurring Calendar Event format
     * @param lesson
     * @param addressBook
     * @return
     */
    public Event lessonToCalendarEvent(Lesson lesson, ReadOnlyAddressBook addressBook) {
        Event event = new Event();

        Student student = addressBook.findStudentByKey(lesson.getUniqueKey());

        event.setSummary(String.format(MESSAGE_SUMMARY, student.getName()));
        event.setLocation(student.getAddress().toString());

        DateTime startDateTime = new DateTime(
                dayTimeToDateTimeString(lesson.getDay(), lesson.getStartTime()));
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone(SINGAPORE_TIME_ZONE);
        event.setStart(start);

        DateTime endDateTime = new DateTime(
                dayTimeToDateTimeString(lesson.getDay(), lesson.getEndTime()));
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone(SINGAPORE_TIME_ZONE);
        event.setEnd(end);
        event.setLocation(student.getAddress().toString());
        event.setAttendees(studentToAttendees(student));

        //Set recurring for a month
        event.setRecurrence(Arrays.asList("RRULE:FREQ=WEEKLY;COUNT=4"));
        return event;
    }

    /**
     * Converts a Day and Time object to a RFC822 String format
     * @param day
     * @param time
     * @return
     */
    public static String dayTimeToDateTimeString(Day day, Time time) {
        LocalDate next = LocalDate.now().with(
                TemporalAdjusters.next(Day.dayToDayOfWeekEnum(day)));

        StringBuilder dateTimeString = new StringBuilder();
        dateTimeString.append(Integer.toString(next.getYear()))
                .append(STRING_DASH)
                .append(String.format("%02d", next.getMonthValue()))
                .append(STRING_DASH)
                .append(String.format("%02d", next.getDayOfMonth()))
                .append("T")
                .append(time)
                .append(STRING_COLON)
                .append("00")
                .append(STRING_GMT_SG);
        return dateTimeString.toString();
    }

    /**
     * Creates an Attendee object using the attributes of Student
     * @param student
     * @return
     */
    public static List<EventAttendee> studentToAttendees(Student student) {
        EventAttendee newAttendee = new EventAttendee();
        newAttendee.setDisplayName(student.getName().toString());
        newAttendee.setEmail(student.getEmail().toString());

        return Arrays.asList(newAttendee);
    }

    /**
     * Creates a secondary Google Calendar with the Name "Student Lessons"
     * @param service
     * @return
     * @throws IOException
     */
    public static String createStudentLessonCalendar(Calendar service) throws IOException {
        com.google.api.services.calendar.model.Calendar newCalendar =
                new com.google.api.services.calendar.model.Calendar();
        newCalendar.setSummary(STRING_STUDENT_CALENDAR_NAME);
        newCalendar.setTimeZone(SINGAPORE_TIME_ZONE);
        newCalendar.setDescription(STRING_STUDENT_CALENDAR_DESCRIPTION);

        com.google.api.services.calendar.model.Calendar createdCalendar =
                service.calendars().insert(newCalendar).execute();

        logger.info("Successfully created Google Calendar: Student Lessons");

        return createdCalendar.getId();
    }

    /**
     * Deletes the existing Calendar with the name "Student Lessons"
     * @param service
     * @throws IOException
     */
    public static void deleteExistingCalendarCopy(Calendar service) throws IOException {
        String pageToken = null;
        do {
            CalendarList calendarList = service.calendarList().list().execute();
            List<CalendarListEntry> items = calendarList.getItems();

            for (CalendarListEntry calendarListEntry : items) {
                if (calendarListEntry.getSummary().equals(STRING_STUDENT_CALENDAR_NAME)) {
                    deleteLessonsOfCalendar(service, calendarListEntry.getId());
                    service.calendars().delete(calendarListEntry.getId()).execute();
                }
            }
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);


        logger.info("Successfully deleted all previous events");
    }

    /**
     * Batch delete the Events from Calendar with {@code calendarID}
     * @param service
     * @param calendarId
     * @throws IOException
     */
    public static void deleteLessonsOfCalendar(Calendar service, String calendarId) throws IOException {
        DateTime now = new DateTime(System.currentTimeMillis());

        Events events = service.events().list(calendarId)
                .setMaxResults(200)
                .setTimeMin(now)
                .setSingleEvents(true)
                .execute();
        for (Event event : events.getItems()) {
            service.events().delete(calendarId, event.getId()).execute();
        }
    }
}
```
###### \java\seedu\address\external\GContactsService.java
``` java
/**
 * Constructs a new GContactsService object to communicate with Google's APIs
 */
public class GContactsService {
    public static final String STRING_STUDENT_GROUP_NAME = "Students";
    public static final String STRING_BASE_GROUP_ATOM_ID =
            "http://www.google.com/m8/feeds/groups/codeducatorapp%40gmail.com/base/6";

    private static final Logger logger = LogsCenter.getLogger(GContactsService.class);

    private Credential credential;
    public GContactsService() {}

    public GContactsService(Credential credential) {
        this.credential = credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public Credential getCredential() {
        return credential;
    }

    /**
     * Converts a Student object to a ContactEntry for insertion
     * @param student
     * @return
     */
    private static ContactEntry studentToContactEntry(Student student) {
        ContactEntry contact = new ContactEntry();

        Name name = new Name();
        FullName newFullName = new FullName();
        newFullName.setValue(student.getName().toString());
        name.setFullName(newFullName);
        contact.setName(name);

        PhoneNumber primaryPhoneNumber = new PhoneNumber();
        primaryPhoneNumber.setPhoneNumber(student.getPhone().toString());
        primaryPhoneNumber.setRel("http://schemas.google.com/g/2005#work");
        primaryPhoneNumber.setPrimary(true);
        contact.addPhoneNumber(primaryPhoneNumber);

        Email primaryMail = new Email();
        primaryMail.setAddress(student.getEmail().toString());
        primaryMail.setRel("http://schemas.google.com/g/2005#home");
        primaryMail.setPrimary(true);
        contact.addEmailAddress(primaryMail);

        contact.addGroupMembershipInfo(
                new GroupMembershipInfo(false, STRING_BASE_GROUP_ATOM_ID));
        contact.setContent(new PlainTextConstruct(student.getUniqueKey().toString()));

        return contact;
    }

    /**
     * Syncs the Addressbook with the user's Google account's Google Contacts.
     * @param addressBook
     * @throws ServiceException
     * @throws IOException
     */
    public void synchronize(ReadOnlyAddressBook addressBook)
            throws ServiceException, IOException {

        ContactsService myService = new ContactsService(GServiceManager.APPLICATION_NAME);
        myService.setOAuth2Credentials(credential);
        URL postUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");

        clearAllOldContacts(myService);

        ContactGroupEntry studentGroupEntry = createStudentGroupEntry(myService);

        for (Student student : addressBook.getStudentList()) {
            ContactEntry toBeInserted = studentToContactEntry(student);

            toBeInserted.addGroupMembershipInfo(
                    new GroupMembershipInfo(false, studentGroupEntry.getId()));

            logger.info(String.format("Contact created for: %s", student.getName()));
        }
        logger.info("Successfully updated Google Contacts");

    }

    /**
     * Gets the Atomic Id {@code ContactGroupEntry.getId()} of the "Students" group
     * that the contacts will be uploaded to
     * @param myService
     * @return
     * @throws ServiceException
     * @throws IOException
     */
    public static ContactGroupEntry getStudentGroupEntry(ContactsService myService)
            throws ServiceException, IOException, NullPointerException {
        // Request the feed
        URL feedUrl = new URL("https://www.google.com/m8/feeds/groups/default/full");
        ContactGroupFeed resultFeed = myService.getFeed(feedUrl, ContactGroupFeed.class);

        for (ContactGroupEntry groupEntry : resultFeed.getEntries()) {
            if (groupEntry.getTitle().getPlainText().equals(STRING_STUDENT_GROUP_NAME)) {
                return groupEntry;
            }
        }

        return null;
    }

    /**
     * Creates a new ContactGroupEntry called "Students"
     * Gets the Atomic Id {@code ContactGroupEntry.getId()} that the contacts will be uploaded to
     * @param myService
     * @return
     * @throws ServiceException
     * @throws IOException
     */
    public static ContactGroupEntry createStudentGroupEntry(ContactsService myService)
            throws ServiceException, IOException {
        // Create the entry to insert
        ContactGroupEntry newGroup = new ContactGroupEntry();
        newGroup.setTitle(new PlainTextConstruct(STRING_STUDENT_GROUP_NAME));

        // Ask the service to insert the new entry
        URL postUrl = new URL("https://www.google.com/m8/feeds/groups/default/full");
        ContactGroupEntry createdGroup = myService.insert(postUrl, newGroup);

        logger.info("Successfully created Google Contact group: Student");
        return createdGroup;
    }

    /**
     * Clears all old contacts that were uploaded, then overwrites them with current data.
     * @param myService
     * @throws ServiceException
     * @throws IOException
     */
    public static void clearAllOldContacts(ContactsService myService)
            throws ServiceException, IOException {

        ContactGroupEntry studentGroupEntry = getStudentGroupEntry(myService);
        URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);

        if (studentGroupEntry != null) {
            deleteAllStudentContactsWithStudentGroup(
                    studentGroupEntry.getSelfLink().getHref(), resultFeed);
            try {
                studentGroupEntry.delete();
            } catch (PreconditionFailedException e) {
                System.out.println("Student Group does not Exists!");
                e.printStackTrace();
            }
        }
        logger.info("Successfully deleted old contacts");
    }

    /**
     * Returns true if the list of Groups of a Student in GroupMemberShipInfo has the "Student" Group
     * @param link
     * @param groupMembershipInfo
     * @return
     */
    public static boolean isStudentGroup(String link, List<GroupMembershipInfo> groupMembershipInfo) {
        String[] linkParts = link.split("/");

        for (GroupMembershipInfo group : groupMembershipInfo) {
            String[] hrefParts = group.getHref().split("/");

            if (hrefParts[hrefParts.length - 1].equals(linkParts[linkParts.length - 1])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes all the Student contacts under Contact Group "Students"
     * @param studentGroupHref
     * @param resultFeed
     * @throws ServiceException
     * @throws IOException
     */
    public static void deleteAllStudentContactsWithStudentGroup(
            String studentGroupHref, ContactFeed resultFeed) throws ServiceException, IOException {
        for (ContactEntry entry : resultFeed.getEntries()) {
            if (isStudentGroup(studentGroupHref, entry.getGroupMembershipInfos())) {
                entry.delete();
            }
        }
    }
}

```
###### \java\seedu\address\external\GServiceManager.java
``` java

/**
 * Manages the two Google Services, Google Contacts as well as Google Calendar
 */
public class GServiceManager {
    public static final String[] SCOPES_ARRAY = new String[]{
        "https://www.googleapis.com/auth/userinfo.profile",
        "https://www.googleapis.com/auth/userinfo.email",
        "https://www.google.com/m8/feeds/",
        CalendarScopes.CALENDAR};

    /** OAuth 2.0 scopes. */
    public static final List<String> SCOPES = Arrays.asList(SCOPES_ARRAY);

    private static final Logger logger = LogsCenter.getLogger(GServiceManager.class);

    public static final String APPLICATION_NAME = "codeducator/v1.5";

    /** Credential information from Google Credentials. Change if any credentials online change**/
    private static final String CLIENT_ID = "126472549776-8cd9bk56sfubm9rkacjivecikppte982.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "nyBpzm1OjnKNZOd0-kT1uo7W";

    private static FileDataStoreFactory dataStoreFactory;
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    private static GoogleClientSecrets clientSecrets;

    private static final File DATA_STORE_DIR = new java.io.File(
            "./credentials");

    private Credential credential;

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public GServiceManager() { }

    /**
     * Login method for user to login to their Google account
     * @throws CredentialsException
     */
    public void login() throws CredentialsException, IOException {
        if (credential != null) {
            Oauth2 oauth2 = new Oauth2.Builder(
                    new NetHttpTransport(), new JacksonFactory(), credential)
                    .setApplicationName(APPLICATION_NAME).build();
            Userinfoplus userInfo = oauth2.userinfo().get().execute();

            logger.warning("Already logged in to " + userInfo.getEmail());
            throw new CredentialsException("You are already logged in.");
        }
        // Build flow and trigger user authorization request.

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPES)
                        .setDataStoreFactory(dataStoreFactory)
                        .setAccessType("offline")
                        .build();

        credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        Oauth2 oauth2 = new Oauth2.Builder(
                new NetHttpTransport(), new JacksonFactory(), credential)
                .setApplicationName(APPLICATION_NAME).build();
        Userinfoplus userInfo = oauth2.userinfo().get().execute();

        logger.info("Successfully logged in to " + userInfo.getEmail());
    }

    /**
     * Logout method for the user to logout of their Google account locally
     * @throws CredentialsException
     */
    public void logout() throws CredentialsException {
        // Delete credentials from data store directory
        File dataStoreDirectory = dataStoreFactory.getDataDirectory();
        if (dataStoreDirectory.list().length == 0 || credential == null) {
            throw new CredentialsException("You are not logged in");
        }
        credential = null;
        for (File file : dataStoreDirectory.listFiles()) {
            file.delete();
        }
        try {
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uploads the schedule to Google Calendar
     * @param addressBook
     * @param schedule
     */
    public void synchronize(ReadOnlyAddressBook addressBook, ReadOnlySchedule schedule)
            throws IOException, ServiceException {
        GContactsService gContactsService = new GContactsService(credential);

        logger.info("====== Starting Google Contacts sync ======");
        gContactsService.synchronize(addressBook);

        logger.info("====== Starting Google Calendar sync ======");
        GCalendarService gCalendarService = new GCalendarService(
                credential, httpTransport, JSON_FACTORY);
        gCalendarService.synchronize(schedule, addressBook);

        logger.info("====== Google Contacts and Calendar synced! ======");
    }
}
```
###### \java\seedu\address\logic\commands\AddLessonCommand.java
``` java
/**
 * Adds a lesson to the schedule for a student in the address book.
 */
public class AddLessonCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addLesson";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the schedule, "
            + "for a student who is in the address book. "
            + "Parameters: "
            + "INDEX " + PREFIX_DAY + " DAY "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_END_TIME + "END_TIME \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DAY + "mon "
            + PREFIX_START_TIME + "10:00 "
            + PREFIX_END_TIME + "10:30 ";

    public static final String MESSAGE_SUCCESS = "New lesson added for %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the schedule";
    public static final String MESSAGE_INVALID_TIME_SLOT = "This lesson clashes with another lesson in the schedule";

    private final Index index;
    private final Day day;
    private final Time startTime;
    private final Time endTime;
    private Student studentToAddLesson;
    /**
     * Creates an AddLessonCommand to add the specified {@code Lesson}
     */
    public AddLessonCommand(Index index, Day day, Time startTime, Time endTime) {
        requireNonNull(index);
        requireNonNull(day);
        requireNonNull(startTime);
        requireNonNull(endTime);

        this.index = index;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     *
     * TODO add model.updateSchedule();
     * @throws CommandException
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.addLesson(studentToAddLesson, day, startTime, endTime);
        } catch (InvalidLessonTimeSlotException iltse) {
            throw new CommandException(MESSAGE_INVALID_TIME_SLOT);
        } catch (DuplicateLessonException dle) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        } catch (StudentNotFoundException pnfe) {
            throw new AssertionError("The target student cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, studentToAddLesson.getName()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        } else if (startTime.compareTo(endTime) >= 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_START_END_TIME);
        }

        studentToAddLesson = lastShownList.get(index.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
```
###### \java\seedu\address\logic\commands\DeleteLessonCommand.java
``` java
/**
 * Deletes a lesson identified using it's last displayed index from the address book.
 */
public class DeleteLessonCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteLesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the lesson identified by the index number used in the last lesson listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson: %1$s";

    private final Index targetIndex;

    private Lesson lessonToDelete;

    public DeleteLessonCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(lessonToDelete);

        try {
            model.deleteLesson(lessonToDelete);
        } catch (LessonNotFoundException lnfe) {
            throw new AssertionError("The target lesson cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS,  lessonToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Lesson> lessonList = model.getSchedule().getSchedule();

        if (targetIndex.getZeroBased() >= lessonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        lessonToDelete = lessonList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLessonCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteLessonCommand) other).targetIndex) // state check
                && Objects.equals(this.lessonToDelete, ((DeleteLessonCommand) other).lessonToDelete));
    }
}
```
###### \java\seedu\address\logic\commands\FindTagCommand.java
``` java
/**
 * Finds and lists all students in address book whose tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose tag contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friend family colleague";

    private final TagContainsKeywordsPredicate predicate;

    public FindTagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredStudentList(predicate);
        return new CommandResult(getMessageForStudentListShownSummary(model.getFilteredStudentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagCommand // instanceof handles nulls
                && this.predicate.equals(((FindTagCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\LoginCommand.java
``` java
/**
 * Logs in to user's Google Account.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": login to your Google account";

    public static final String MESSAGE_SUCCESS = "Google account logged in";

    private static final String MESSAGE_ALREADY_LOGGED_IN = "You are already logged in!";
    private static final String MESSAGE_AUTH_DENIED = "Google account's authorisation denied!";
    private static final String MESSAGE_LOGIN_SUCCESS = "Successfully logged in to Google accounts";
    private static final String MESSAGE_TIME_OUT = "Login timeout!";
    private static final String MESSAGE_UNKNOWN_FAILURE = "Unable to login(reason unknown)!";

    private static final Integer INTEGER_TIME_ALLOWED = 45;

    public LoginCommand() {}

    @Override
    public CommandResult execute() throws CommandException {
        final Duration timeout = Duration.ofSeconds(INTEGER_TIME_ALLOWED);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        final Future<String> handler = executor.submit(new Callable() {
            @Override
            public String call() throws Exception {
                try {
                    model.loginGoogleAccount();
                    return MESSAGE_LOGIN_SUCCESS;
                } catch (IOException ioe) {
                    return MESSAGE_AUTH_DENIED;
                } catch (CredentialsException ce) {
                    return MESSAGE_ALREADY_LOGGED_IN;
                }
            }
        });

        try {
            String result = handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
            return new CommandResult(result);
        } catch (TimeoutException e) {
            handler.cancel(true);
            executor.shutdownNow();
            return new CommandResult(MESSAGE_TIME_OUT);
        } catch (Exception e) {
            e.printStackTrace();
            executor.shutdownNow();
            return new CommandResult(MESSAGE_UNKNOWN_FAILURE);
        }
    }
}
```
###### \java\seedu\address\logic\commands\ScheduleCommand.java
``` java
/**
 * Displays the user's schedule.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views your weekly schedule";

    public static final String MESSAGE_SUCCESS = "Schedule displayed";

    public ScheduleCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(
                new ShowScheduleEvent(model.getSchedule(), model.getAddressBook()));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\SyncCommand.java
``` java

/**
 * Syncs the user's contact list and schedule to Google Contacts and Calendar.
 */
public class SyncCommand extends Command {

    public static final String COMMAND_WORD = "sync";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": syncs your schedule to the cloud";

    public static final String MESSAGE_SUCCESS = "Google Contacts and Calendar synced!";
    public static final String MESSAGE_FAILED_SYNC = "Failed to sync!";
    public static final String MESSAGE_GOOGLE_SERVICE_ISSUE = "There was a problem with the Google Service. Try again!";
    public static final String MESSAGE_NOT_LOGGED_IN = "You are not logged in!\n"
            + "Or your credentials have expired, logout and re-login to sync";

    public SyncCommand() {}

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.synchronize();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_FAILED_SYNC);
        } catch (ServiceException se) {
            throw new CommandException(MESSAGE_GOOGLE_SERVICE_ISSUE);
        } catch (NullPointerException ne) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddLessonCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommandCommand object
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLessonCommand
     * and returns an AddLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        Index index;
        Day day;
        Time startTime;
        Time endTime;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        try {
            day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY)).get();
            startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME)).get();
            endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME)).get();

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new AddLessonCommand(index, day, startTime, endTime);
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
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case AddLessonCommand.COMMAND_WORD:
            return new AddLessonCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case DeleteLessonCommand.COMMAND_WORD:
            return new DeleteLessonCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FindTagCommand.COMMAND_WORD:
            return new FindTagCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case LoginCommand.COMMAND_WORD:
            return new LoginCommand();

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();
        //@author
        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommand();

        case SyncCommand.COMMAND_WORD:
            return new SyncCommand();
```
###### \java\seedu\address\logic\parser\FindTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindTagCommand object
 */
public class FindTagCommandParser implements Parser<FindTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagCommand
     * and returns an FindTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \java\seedu\address\model\lesson\Day.java
``` java
/**
 * Represents a Student's day in a lesson in the Schedule.
 * Guarantees: immutable; is valid as declared in {@link #isValidDay String)}
 */
public class Day implements Comparable<Day> {

    private static final String STRING_MON = "mon";
    private static final String STRING_TUE = "tue";
    private static final String STRING_WED = "wed";
    private static final String STRING_THU = "thu";
    private static final String STRING_FRI = "fri";
    private static final String STRING_SAT = "sat";
    private static final String STRING_SUN = "sun";

    private static final Integer INTEGER_MON = 1;
    private static final Integer INTEGER_TUE = 2;
    private static final Integer INTEGER_WED = 3;
    private static final Integer INTEGER_THU = 4;
    private static final Integer INTEGER_FRI = 5;
    private static final Integer INTEGER_SAT = 6;
    private static final Integer INTEGER_SUN = 7;

    private static final String FULL_STRING_MON = "Monday";
    private static final String FULL_STRING_TUE = "Tuesday";
    private static final String FULL_STRING_WED = "Wednesday";
    private static final String FULL_STRING_THU = "Thursday";
    private static final String FULL_STRING_FRI = "Friday";
    private static final String FULL_STRING_SAT = "Saturday";
    private static final String FULL_STRING_SUN = "Sunday";

    public static final String MESSAGE_DAY_CONSTRAINTS = "Day should be of the format: first 3 letters of Day"
            + "(not case sensitive) i,e.\n"
            + STRING_MON + ", " + STRING_TUE + ", " + STRING_WED + ", " + STRING_THU + ", "
            + STRING_FRI + ", " + STRING_SAT + ", " + STRING_SUN + "\n\n";

    private static final HashMap<String, Integer> dayToIntMap = new HashMap<>();
    private static final HashMap<String, String> dayToFullDayMap = new HashMap<>();
    private static final HashMap<String, DayOfWeek> dayToDayOfWeekEnumMap = new HashMap<>();
    private static final String DAY_REGEX = "^(mon|tue|wed|thu|fri|sat|sun)";

    public final String value;

    /**
     * 1. Builds the {@code dayToIntMap} for the integer value of the day in week
     *    Week starts on Monday, with value of 1, end on Sunday, with value of 7
     *
     * 2. Builds the {@code dayToFullDayMap} for short form to long form names of days,
     *
     * 3. Builds the {@code dayToDayOfWeekEnumMap} for use with Google Calendar upload
     */
    static {
        dayToIntMap.put(STRING_MON, INTEGER_MON);
        dayToIntMap.put(STRING_TUE, INTEGER_TUE);
        dayToIntMap.put(STRING_WED, INTEGER_WED);
        dayToIntMap.put(STRING_THU, INTEGER_THU);
        dayToIntMap.put(STRING_FRI, INTEGER_FRI);
        dayToIntMap.put(STRING_SAT, INTEGER_SAT);
        dayToIntMap.put(STRING_SUN, INTEGER_SUN);

        dayToFullDayMap.put(STRING_MON, FULL_STRING_MON);
        dayToFullDayMap.put(STRING_TUE, FULL_STRING_TUE);
        dayToFullDayMap.put(STRING_WED, FULL_STRING_WED);
        dayToFullDayMap.put(STRING_THU, FULL_STRING_THU);
        dayToFullDayMap.put(STRING_FRI, FULL_STRING_FRI);
        dayToFullDayMap.put(STRING_SAT, FULL_STRING_SAT);
        dayToFullDayMap.put(STRING_SUN, FULL_STRING_SUN);

        dayToDayOfWeekEnumMap.put(STRING_MON, DayOfWeek.MONDAY);
        dayToDayOfWeekEnumMap.put(STRING_TUE, DayOfWeek.TUESDAY);
        dayToDayOfWeekEnumMap.put(STRING_WED, DayOfWeek.WEDNESDAY);
        dayToDayOfWeekEnumMap.put(STRING_THU, DayOfWeek.THURSDAY);
        dayToDayOfWeekEnumMap.put(STRING_FRI, DayOfWeek.FRIDAY);
        dayToDayOfWeekEnumMap.put(STRING_SAT, DayOfWeek.SATURDAY);
        dayToDayOfWeekEnumMap.put(STRING_SUN, DayOfWeek.SUNDAY);
    }

    /**
     * Constructs an {@code Day}.
     *
     * @param day A valid day string.
     */
    public Day(String day) {
        requireNonNull(day);
        checkArgument(isValidDay(day), MESSAGE_DAY_CONSTRAINTS);
        this.value = day;
    }

    /**
     * Returns if a given string is a valid student day.
     */
    public static boolean isValidDay(String test) {
        return test.matches(DAY_REGEX);
    }

    public static int dayToIntValue(String day) {
        return dayToIntMap.get(day);
    }

    public static DayOfWeek dayToDayOfWeekEnum(Day day) {
        return dayToDayOfWeekEnumMap.get(day.toString());
    }

    public int intValue() {
        return dayToIntMap.get(this.value);
    }
    public String fullDayName() {
        return dayToFullDayMap.get(this.value);
    }

    /**
     * Gets a DateTime dateString formatted in dd/MM/yyyy
     * @return
     */
    public String toDateString() {
        LocalDate now = new LocalDate();
        LocalDate day = now.withDayOfWeek(dayToIntValue(this.toString()));
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
            Date date = format1.parse(day.toString());
            return format2.format(date).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "01/01/1994";
    }
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Day // instanceof handles nulls
                && this.value.equals(((Day) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Day other) {
        return this.intValue() - other.intValue();
    }
}
```
###### \java\seedu\address\model\lesson\exceptions\DuplicateLessonException.java
``` java
/**
 * Signals that the operation will result in duplicate Lesson objects.
 */
public class DuplicateLessonException extends DuplicateDataException {
    public DuplicateLessonException() {
        super("Operation would result in duplicate lessons");
    }
}
```
###### \java\seedu\address\model\lesson\exceptions\InvalidLessonTimeSlotException.java
``` java
/**
 * Signals that the operation will result in duplicate Lesson objects.
 */
public class InvalidLessonTimeSlotException extends IllegalValueException {
    public InvalidLessonTimeSlotException() {
        super("Operation would result in lessons that clash with each other");
    }
}
```
###### \java\seedu\address\model\lesson\exceptions\LessonNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified lesson.
 */
public class LessonNotFoundException extends Exception { }
```
###### \java\seedu\address\model\lesson\Hour.java
``` java
/**
 * Represents the Hour value in the Time.
 */
public class Hour implements Comparable<Hour> {

    private int value;

    public Hour(String hour) {
        this.value = Integer.parseInt(hour);
    }

    /**
     * Get the integer value of the Hour string
     * @return value
     */
    public int getHour() {
        return this.value;
    }

    /**
     * Compares 2 Hour values and returns whether the comparison is larger than itself
     * @return the compareTo value
     */
    public int compareTo(Hour other) {
        return this.getHour() - other.getHour();
    }

}
```
###### \java\seedu\address\model\lesson\Lesson.java
``` java
/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson implements Comparable<Lesson> {

    private Student student;
    private final UniqueKey uniqueKey;
    private final Day day;
    private final Time startTime;
    private final Time endTime;

    /**
     * Every field must be present and not null.
     */
    public Lesson(UniqueKey uniqueKey, Day day, Time startTime, Time endTime) {
        requireAllNonNull(uniqueKey, day, startTime, endTime);

        this.uniqueKey = uniqueKey;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Student getStudent() {
        return student;
    }

    public UniqueKey getUniqueKey() {
        return uniqueKey;
    }

    public Day getDay() {
        return day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    /**
     * To check if a lesson will clash with another lesson on the same day
     *
     * @return true/false
     */
    public boolean clashesWith(Lesson other) {
        return this.getDay().compareTo(other.getDay()) == 0
                ? ((this.getStartTime().compareTo(other.getStartTime()) >= 0    //Same day
                && this.getStartTime().compareTo(other.getEndTime()) < 0)
                || (this.getEndTime().compareTo(other.getStartTime()) > 0
                && this.getEndTime().compareTo(other.getEndTime()) <= 0))
                : this.getDay().compareTo(other.getDay()) == 0; //Different day
    }

    public Lesson getLesson() {
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return otherLesson.getUniqueKey().equals(this.getUniqueKey())
                && otherLesson.getDay().equals(this.getDay())
                && otherLesson.getStartTime().equals(this.getEndTime())
                && otherLesson.getEndTime().equals(this.getEndTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(uniqueKey, day, startTime, endTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Day: ")
                .append(getDay().fullDayName())
                .append(" Time: ")
                .append(getStartTime() + " - " + getEndTime());
        return builder.toString();
    }

    @Override
    public int compareTo(Lesson other) {
        return this.getDay().intValue() - other.getDay().intValue() != 0
                ? this.getDay().intValue() - other.getDay().intValue()
                : this.getStartTime().compareTo(other.getStartTime());
    }
}
```
###### \java\seedu\address\model\lesson\LessonList.java
``` java
/**
 * A list of lessons that enforces non clashes int timings between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Lesson#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class LessonList implements Iterable<Lesson> {

    private final ObservableList<Lesson> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent student as the given argument.
     */
    public boolean contains(Lesson toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a lesson to the list.
     */
    public void add(Lesson toAdd)
            throws InvalidLessonTimeSlotException, DuplicateLessonException {
        requireNonNull(toAdd);
        if (!isValidSlot(toAdd)) {
            throw new InvalidLessonTimeSlotException();
        }
        if (contains(toAdd)) {
            throw new DuplicateLessonException();
        }
        internalList.add(toAdd);
        Collections.sort(internalList);
    }
    /**
     * Checks if lesson clashes with other lessons in the schedule
     * @return true/false
     */
    private boolean isValidSlot(Lesson l) {
        for (Lesson lesson : internalList) {
            if (l.clashesWith(lesson)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Lesson> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Removes the equivalent lesson from the list.
     *
     * @throws LessonNotFoundException if no such lesson could be found in the list.
     */
    public boolean remove(Lesson toRemove) throws LessonNotFoundException {
        requireNonNull(toRemove);
        final boolean lessonFoundAndDeleted = internalList.remove(toRemove);
        if (!lessonFoundAndDeleted) {
            throw new LessonNotFoundException();
        }
        return lessonFoundAndDeleted;
    }
    public void setLessons(LessonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setLessons(List<Lesson> lessons)
            throws InvalidLessonTimeSlotException, DuplicateLessonException {
        requireAllNonNull(lessons);
        final LessonList replacement = new LessonList();
        for (final Lesson lesson : lessons) {
            replacement.add(lesson);
        }
        setLessons(replacement);
    }

    /**
     * Reconstructs a new {@code LessonList} replacement based on Lessons not associated with {@code Student}
     * {@code internalList} will setLessons of replacement
     * @param target
     */
    public void removeStudentLessons(Student target)
            throws InvalidLessonTimeSlotException, DuplicateLessonException {
        final LessonList replacement = new LessonList();
        for (Lesson lesson : internalList) {
            if (!target.getUniqueKey().equals(lesson.getUniqueKey())) {
                replacement.add(lesson);
            }
        }
        setLessons(replacement);
    }
    @Override
    public Iterator<Lesson> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LessonList // instanceof handles nulls
                        && this.internalList.equals(((LessonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\lesson\Min.java
``` java
/**
 * Represents the Hour value in the Time.
 */
public class Min implements Comparable<Min> {

    private int value;

    public Min(String min) {
        this.value = Integer.parseInt(min);
    }

    /**
     * Get the integer value of the Hour string
     * @return value
     */
    public int getMin() {
        return this.value;
    }

    /**
     * Compares 2 Hour values and returns whether the comparison is larger than itself
     * @return the compareTo value
     */
    public int compareTo(Min other) {
        return this.getMin() - other.getMin();
    }
}
```
###### \java\seedu\address\model\lesson\Time.java
``` java
/**
 * Represents a Student's time in a lesson in the Schedule.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime String)}
 */
public class Time implements Comparable<Time> {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time should be of the format HH:MM "
            + "and adhere to the following constraints:\n"
            + "1. The hour HH should only contain numbers and be in range [00, 23] inclusive\n"
            + "2. This is followed by a ':' and then minutes MM.\n"
            + "3. The minutes MM should only contain numbers and be in range [00, 59] inclusive\n";
    // Numeric characters in Hour or Minute ranges
    private static final String HOUR_PART_REGEX = "^(0[0-9]|1[0-9]|2[0-3])";
    private static final String MINUTE_PART_REGEX = "([0-5][0-9])";
    private static final String TIME_DELIMITER = ":";
    public static final String TIME_VALIDATION_REGEX = HOUR_PART_REGEX
            + TIME_DELIMITER
            + MINUTE_PART_REGEX;
    private static final int INDEX_HOUR = 0;
    private static final int INDEX_MIN = 1;

    public final String value;
    private final Hour hour;
    private final Min min;


    /**
     * Constructs an {@code Time}.
     *
     * @param time A valid time string.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        this.value = time;
        this.hour = new Hour(value.split(TIME_DELIMITER)[INDEX_HOUR]);
        this.min = new Min(value.split(TIME_DELIMITER)[INDEX_MIN]);
    }

    /**
     * Returns if a given string is a valid student time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    public Hour getHour() {
        return this.hour;
    }
    public Min getMin() {
        return this.min;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && this.value.equals(((Time) other).value)); // state check
    }

    @Override
    public int compareTo(Time other) {
        return this.getHour().compareTo(other.getHour()) != 0
                ? this.getHour().compareTo(other.getHour())
                : this.getMin().compareTo(other.getMin());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void loginGoogleAccount() throws CredentialsException, IOException {
        this.gServiceManager.login();
    }

    @Override
    public void logoutGoogleAccount() throws CredentialsException {
        this.gServiceManager.logout();
    }

    @Override
    public void synchronize() throws ServiceException, IOException {
        this.gServiceManager.synchronize(addressBook, schedule);
    }
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    Student findStudentByKey(UniqueKey key);
}
```
###### \java\seedu\address\model\ReadOnlySchedule.java
``` java
/**
 * Unmodifiable view of a schedule
 */
public interface ReadOnlySchedule {

    /**
     * Returns an unmodifiable view of the schedule.
     * This list will not contain any duplicate lessons or lessons that clash.
     */
    ObservableList<Lesson> getSchedule();

    Time getEarliestStartTime();

    Time getLatestEndTime();
}
```
###### \java\seedu\address\model\Schedule.java
``` java
/**
 * Wraps all data at the schedule level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Schedule implements ReadOnlySchedule {

    private final LessonList lessons;
    {
        lessons = new LessonList();
    }

    public Schedule() {}
    /**
     * Creates an Schedule using the Lesson in the {@code toBeCopied}
     */
    public Schedule(ReadOnlySchedule toBeCopied) {
        this();
        resetData(toBeCopied);
    }
    /**
     * Adds lesson to schedule
     * @param lessonToBeAdded
     * @throws InvalidLessonTimeSlotException if invalid
     */
    public void addLesson(Lesson lessonToBeAdded)
            throws InvalidLessonTimeSlotException, DuplicateLessonException {
        if (!isValidSlot(lessonToBeAdded)) {
            throw new InvalidLessonTimeSlotException();
        }
        lessons.add(lessonToBeAdded);
    }

    /**
     * Checks if lesson clashes with other lessons in the schedule
     * @return true/false
     */
    private boolean isValidSlot(Lesson l) {
        for (Lesson lesson : lessons) {
            if (l.clashesWith(lesson)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes {@code key} from this {@code Schedule}.
     * @throws LessonNotFoundException if the {@code key} is not in this {@code Schedule}.
     */
    public boolean removeLesson(Lesson key) throws LessonNotFoundException {
        if (lessons.remove(key)) {
            return true;
        } else {
            throw new LessonNotFoundException();
        }
    }

    /**
     * Resets the existing data of this {@code Schedule} with {@code newData}.
     */
    public void resetData(ReadOnlySchedule newData) {
        requireNonNull(newData);
        List<Lesson> newList = newData.getSchedule().stream()
                .map(Lesson::getLesson)
                .collect(Collectors.toList());

        try {
            setLessons(newList);
        } catch (DuplicateLessonException e) {
            throw new AssertionError("Schedules should not have duplicate lessons");
        } catch (InvalidLessonTimeSlotException iltse) {
            throw new AssertionError("Schedules should not have clashing time slots");
        }
    }

    public void setLessons(List<Lesson> lessons)
            throws InvalidLessonTimeSlotException, DuplicateLessonException {
        this.lessons.setLessons(lessons);
    }

    /**
     * Deletes all Lessons in LessonList associated with a Student {@code UniqueKey key}
     * @param target
     * @throws LessonNotFoundException
     */
    public void removeStudentLessons(Student target)
            throws InvalidLessonTimeSlotException, DuplicateLessonException, LessonNotFoundException {
        lessons.removeStudentLessons(target);
    }

    /**
     * Finds the latest EndTime in the Schedule
     * @return
     */
    @Override
    public Time getLatestEndTime() {
        Time latest = new Time("00:00");
        for (Lesson l : lessons) {
            if (l.getEndTime().compareTo(latest) > 0) {
                latest = l.getEndTime();
            }
        }
        return latest;
    }

    /**
     * Finds the earliest StartTime in the Schedule
     * @return
     */
    @Override
    public Time getEarliestStartTime() {
        Time earliest = new Time("23:59");

        for (Lesson l : lessons) {
            if (l.getStartTime().compareTo(earliest) < 0) {
                earliest = l.getStartTime();
            }
        }
        return earliest;
    }

    //// util methods
    @Override
    public String toString() {
        return lessons.asObservableList().size() + " lessons";
    }

    @Override
    public ObservableList<Lesson> getSchedule() {
        return lessons.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && this.lessons.equals(((Schedule) other).lessons));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(lessons);
    }
}
```
###### \java\seedu\address\model\student\UniqueKey.java
``` java
/**
 * Represents a Student's UniqueKey in the address book. Generated by {@code UniqueStudentList.add()} method
 * Transforms the addressbook and schedule into relational database, related by this Uniquekey value
 * Guarantees: immutable; is valid as declared in {@link #isValidUniqueKey(String)}
 */
public class UniqueKey {


    public static final String MESSAGE_UNIQUE_KEY_CONSTRAINTS =
            "UniqueKey is a 6 digit hexadecimal string";
    public static final String UNIQUE_KEY_VALIDATION_REGEX = "^(?:[0-9a-fA-F]){6}$";
    public final String uniqueKey;
    /**
     * Constructs a {@code Phone}.
     *
     * @param uniqueKey A valid phone number.
     */
    public UniqueKey(String uniqueKey) {
        requireNonNull(uniqueKey);
        checkArgument(isValidUniqueKey(uniqueKey), MESSAGE_UNIQUE_KEY_CONSTRAINTS);
        this.uniqueKey = uniqueKey;
    }

    /**
     * Generates a random hexadecimal string of length 6
     * @return
     */
    public static UniqueKey generateRandomKey() {
        Random random = new Random();
        int nextInt;
        String finalStringKey = "";
        for (int i = 0; i < 6; i++) {
            nextInt = random.nextInt(16);
            finalStringKey = finalStringKey + Integer.toHexString(nextInt);
        }
        return new UniqueKey(finalStringKey);
    }

    /**
     * Returns true if a given string is a valid student phone number.
     */
    public static boolean isValidUniqueKey(String test) {
        return test.matches(UNIQUE_KEY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return uniqueKey;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueKey // instanceof handles nulls
                && this.uniqueKey.equals(((UniqueKey) other).uniqueKey)); // state check
    }

    @Override
    public int hashCode() {
        return uniqueKey.hashCode();
    }

}
```
###### \java\seedu\address\model\student\UniqueStudentList.java
``` java
    /**
     * Finds a unique key not in the {@code internalList}
     * @return A Unique length 6 hexadecimal string
     */
    private UniqueKey generateValidUniqueKey() {
        UniqueKey test = UniqueKey.generateRandomKey();
        while (!isUnique(test)) {
            test = UniqueKey.generateRandomKey();
        }
        return test;
    }

    /**
     * Tests if the input {@code UniqueKey} is unique in the list
     * @return true if unique, false if not unique
     */
    private boolean isUnique(UniqueKey test) {
        for (Student testStudent : internalList) {
            if (test.equals(testStudent.getUniqueKey())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finds a student identified by UniqueKey, or throws a StudentNotFoundException
     * @return Student
     * @throws StudentNotFoundException
     */
    public Student findKey(UniqueKey key) throws StudentNotFoundException {
        boolean found = false;
        Student foundStudent = null;
        for (Student student : internalList) {
            if (key.equals(student.getUniqueKey())) {
                return student;
            }
        }
        if (!found) {
            throw new StudentNotFoundException();
        }
        return foundStudent;
    }
```
###### \java\seedu\address\model\tag\TagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Student}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Student> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Student student) {
        Set<Tag> tagList = student.getTags();
        for (Tag studentsTag: tagList) {
            if (keywords.stream().anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(studentsTag.tagName, keyword))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\storage\ScheduleStorage.java
``` java
/**
 * Represents a storage for {@link seedu.address.model.Schedule}.
 */
public interface ScheduleStorage {

    /**
     * Returns the file path of the data file.
     */
    String getScheduleFilePath();

    /**
     * Returns Schedule data as a {@link ReadOnlySchedule}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySchedule> readSchedule() throws DataConversionException, IOException;

    /**
     * @see #getScheduleFilePath()
     */
    Optional<ReadOnlySchedule> readSchedule(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySchedule} to the storage.
     * @param schedule cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSchedule(ReadOnlySchedule schedule) throws IOException;

    /**
     * @see #saveSchedule(ReadOnlySchedule)
     */
    void saveSchedule(ReadOnlySchedule schedule, String filePath) throws IOException;

    /**
     * Saves the given {@link ReadOnlySchedule} to the fixed temporary storage.
     * @param schedule cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupSchedule(ReadOnlySchedule schedule) throws IOException;

}
```
###### \java\seedu\address\storage\Storage.java
``` java
    @Override
    String getScheduleFilePath();

    @Override
    Optional<ReadOnlySchedule> readSchedule() throws DataConversionException, IOException;

    @Override
    void saveSchedule(ReadOnlySchedule schedule) throws IOException;
```
###### \java\seedu\address\storage\Storage.java
``` java
    /**
     * Handles the event where the required student index for displaying misc info is changed
     */
    void handleRequiredStudentIndexChangedEvent(RequiredStudentIndexChangeEvent rsice);
```
###### \java\seedu\address\storage\Storage.java
``` java
    /**
     * Handles the event where the profile picture of a student is being changed
     */
    void handleProfilePictureChangeEvent(ProfilePictureChangeEvent pce);

    /**
     * Sets up the view files required for the profile page display for students
     */
    void setupViewFiles() throws IOException;
}
```
###### \java\seedu\address\storage\XmlAdaptedLesson.java
``` java
/**
 * JAXB-friendly version of the Lesson.
 */
public class XmlAdaptedLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    @XmlElement(required = true)
    private String key;
    @XmlElement(required = true)
    private String day;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;

    /**
     * Constructs an XmlAdaptedLesson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLesson() {}

    /**
     * Constructs an {@code XmlAdaptedLesson} with the given student details.
     */
    public XmlAdaptedLesson(String key, String day, String startTime, String endTime) {
        this.key = key;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given Student into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedLesson
     */
    public XmlAdaptedLesson(Lesson source) {
        key = source.getUniqueKey().uniqueKey;
        day = source.getDay().value;
        startTime = source.getStartTime().value;
        endTime = source.getEndTime().value;
    }

    /**
     * Converts this jaxb-friendly adapted student object into the model's Student object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student
     */
    public Lesson toModelType() throws IllegalValueException {
        if (this.key == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, UniqueKey.class.getSimpleName()));
        }
        if (!UniqueKey.isValidUniqueKey(this.key)) {
            throw new IllegalValueException(UniqueKey.MESSAGE_UNIQUE_KEY_CONSTRAINTS);
        }
        final UniqueKey uniqueKey = new UniqueKey(this.key);

        if (this.day == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName()));
        }
        if (!Day.isValidDay(this.day)) {
            throw new IllegalValueException(Day.MESSAGE_DAY_CONSTRAINTS);
        }
        final Day day = new Day(this.day);

        if (this.startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(this.startTime)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        final Time startTime = new Time(this.startTime);

        if (this.endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(this.endTime)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        final Time endTime = new Time(this.endTime);

        return new Lesson(uniqueKey, day, startTime, endTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedLesson)) {
            return false;
        }

        XmlAdaptedLesson otherLesson = (XmlAdaptedLesson) other;
        return Objects.equals(key, otherLesson.key)
                && Objects.equals(day, otherLesson.day)
                && Objects.equals(startTime, otherLesson.startTime)
                && Objects.equals(endTime, otherLesson.endTime);
    }
}
```
###### \java\seedu\address\storage\XmlScheduleStorage.java
``` java
/**
 * A class to access Schedule data stored as an xml file on the hard disk.
 */
public class XmlScheduleStorage implements ScheduleStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlScheduleStorage.class);

    private String filePath;

    public XmlScheduleStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getScheduleFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySchedule> readSchedule() throws DataConversionException, IOException {
        return readSchedule(filePath);
    }

    /**
     * Similar to {@link #readSchedule()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySchedule> readSchedule(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File scheduleFile = new File(filePath);

        if (!scheduleFile.exists()) {
            logger.info("Schedule file "  + scheduleFile + " not found");
            return Optional.empty();
        }

        XmlSerializableSchedule xmlSchedule = XmlFileStorage.loadScheduleDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlSchedule.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + scheduleFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveSchedule(ReadOnlySchedule schedule) throws IOException {
        saveSchedule(schedule, filePath);
    }

    /**
     * Similar to {@link #saveSchedule(ReadOnlySchedule)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveSchedule(ReadOnlySchedule schedule, String filePath) throws IOException {
        requireNonNull(schedule);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveScheduleDataToFile(file, new XmlSerializableSchedule(schedule));
    }

    @Override
    public void backupSchedule(ReadOnlySchedule schedule) throws IOException {
        saveSchedule(schedule, filePath + ".backup");
    }
}
```
###### \java\seedu\address\storage\XmlSerializableSchedule.java
``` java
/**
 * An Immutable Schedule that is serializable to XML format
 */
@XmlRootElement(name = "schedule")
public class XmlSerializableSchedule {

    @XmlElement
    private List<XmlAdaptedLesson> lessons;

    /**
     * Creates an empty XmlSerializableSchedule.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableSchedule() {
        lessons = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableSchedule(ReadOnlySchedule src) {
        this();
        lessons.addAll(src.getSchedule().stream().map(XmlAdaptedLesson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this schedule into the model's {@code Schedule} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedLesson}
     */
    public Schedule toModelType() throws IllegalValueException {
        Schedule schedule = new Schedule();
        for (XmlAdaptedLesson l : lessons) {
            schedule.addLesson(l.toModelType());
        }
        return schedule;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableSchedule)) {
            return false;
        }

        XmlSerializableSchedule otherSchedule = (XmlSerializableSchedule) other;
        return lessons.equals(otherSchedule.lessons);
    }
}
```
###### \java\seedu\address\ui\CalendarPanel.java
``` java
/**
 * The Calendar Panel using the InfoPanel of Codeducator.
 */
public class CalendarPanel extends UiPart<CalendarView> {

    private static final String FXML = "CalendarPanel.fxml";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
    private static final String STRING_ENTRY_TITLE = "%d Lesson: %s";

    private static final Time TIME_DEFAULT_START = new Time("07:00");
    private static final Time TIME_DEFAULT_END = new Time("22:00");
    private static final LocalTime TEMPORAL_TIME_DEFAULT_START = LocalTime.of(00, 30);
    private static final LocalTime TEMPORAL_TIME_DEFAULT_END = LocalTime.of(23, 59);

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @javafx.fxml.FXML
    private CalendarView calendarView;
    private Calendar calendar;
    private PageBase pageBase;

    private Integer lessonDisplayIndex;

    private ReadOnlyAddressBook addressBook;

    private ReadOnlySchedule schedule;

    public CalendarPanel(ReadOnlySchedule readOnlySchedule, ReadOnlyAddressBook addressBook) {
        super(FXML);
        initializeCalendar();
        setUpCalendarView(readOnlySchedule);
        loadEntries(readOnlySchedule, addressBook);
        updateTime();
        registerAsAnEventHandler(this);
    }

    public CalendarView getCalendarView() {
        return calendarView;
    }

    public PageBase getPageBase() {
        return pageBase;
    }

    /**
     * Initializes the calendar
     */
    private void initializeCalendar() {
        calendar = new Calendar("Lessons");
    }

    /**
     * Sets up the calendar view
     * Uses many methods to modify the default CalendarView
     */
    private void setUpCalendarView(ReadOnlySchedule schedule) {
        CalendarSource calendarSource = new CalendarSource("My Calendar");
        calendarSource.getCalendars().addAll(calendar);

        calendarView.getCalendarSources().addAll(calendarSource);
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.showWeekPage();
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowSourceTray(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowPageToolBarControls(false);
        calendarView.setShowPageSwitcher(false);
        calendarView.setShowAddCalendarButton(false);
        calendarView.showWeekPage();
        calendarView.setShowDeveloperConsole(true);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowToolBar(false);

        calendarView.setOnMouseClicked(null);
        calendarView.setDisable(true);
        calendar.setReadOnly(true);

        calendarView.getWeekPage().setShowNavigation(false);
        calendarView.getWeekPage().setShowDate(false);
        calendarView.weekFieldsProperty().setValue(WeekFields.of(Locale.FRANCE)); // Start week from Monday

        DetailedWeekView detailedWeekView = calendarView.getWeekPage().getDetailedWeekView();
        detailedWeekView.setEarlyLateHoursStrategy(DayViewBase.EarlyLateHoursStrategy.HIDE);
        detailedWeekView.setHoursLayoutStrategy(DayViewBase.HoursLayoutStrategy.FIXED_HOUR_COUNT);
        resizeCalendar(calendarView, schedule);
    }

    /**
     * Loads {@code lessons} into the calendar
     */
    private void loadEntries(ReadOnlySchedule readOnlySchedule, ReadOnlyAddressBook addressBook) {
        lessonDisplayIndex = 1;
        this.addressBook = addressBook;
        this.schedule = readOnlySchedule;
        readOnlySchedule.getSchedule().stream().forEach(this::loadEntry);
        resizeCalendar(calendarView, readOnlySchedule);
    }

    /**
     * Resizes the calendar view if any time slots are outside of the default time range
     * @param calendarView
     * @param schedule
     */
    private void resizeCalendar(CalendarView calendarView, ReadOnlySchedule schedule) {
        DetailedWeekView detailedWeekView = calendarView.getWeekPage().getDetailedWeekView();
        Time start = TIME_DEFAULT_START.compareTo(schedule.getEarliestStartTime()) < 0
                ? TIME_DEFAULT_START : schedule.getEarliestStartTime();
        Time end = TIME_DEFAULT_END.compareTo(schedule.getLatestEndTime()) > 0
                ? TIME_DEFAULT_END : schedule.getLatestEndTime();
        LocalTime startTime = LocalTime.parse(start.toString());
        LocalTime endTime = LocalTime.parse(end.toString());

        detailedWeekView.setVisibleHours((int) ChronoUnit.HOURS.between(startTime, endTime));
        calendarView.setStartTime(startTime);
        calendarView.setEndTime(endTime);
    }
    /**
     * Creates an entry with the {@code lesson} details and loads it into the calendar
     */
    private void loadEntry(Lesson lesson) {
        String dateString = lesson.getDay().toDateString();
        String startTimeString = lesson.getStartTime().toString();
        String endTimeString = lesson.getEndTime().toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime startDateTime = LocalDateTime.parse(dateString + " " + startTimeString, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(dateString + " " + endTimeString, formatter);

        Student student = addressBook.findStudentByKey(lesson.getUniqueKey());
        Entry entry = new Entry();
        entry.setInterval(startDateTime, endDateTime);

        entry.setTitle(String.format(STRING_ENTRY_TITLE, lessonDisplayIndex++, student.getName()));
        entry.setCalendar(calendar);



    }


    /**
     * Handles the event where a lesson is deleted by loading the updated lessons list into the calendar
     * @param event contains the updated lessons list
     */
    @Subscribe
    private void handleScheduleChangedEvent(ScheduleChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        calendar.clear();
        loadEntries(event.getLessons(), event.getAddressBook());
    }

```
###### \resources\view\CalendarPanel.fxml
``` fxml
<?import javafx.scene.layout.StackPane?>
<?import com.calendarfx.view.CalendarView?>

<StackPane xmlns:fx="http://javafx.com/fxml/1">
  <CalendarView fx:id="calendarView"/>
</StackPane>
```
###### \resources\view\main.css
``` css
 */
@font-face {
    src: url("./ProFontWindows.ttf");
}
@font-face {
    src: url("./Montserrat-Semibold.ttf");
}
.background {
    -fx-background-color: derive(#1d1d1d, 20%);
    background-color: #383838; /* Used in the default.html file */
}

.root * {
    base: black;
    light: white;
    primary: #71b280;
    secondary: #134e5e;
    -fx-background-color: transparent;
    -fx-text-fill: black;
    -fx-font-size: 13px;
    -fx-font-family: "Montserrat Light", sans-serif;
}

#details {
    -fx-background-color: linear-gradient(to right, #134e5e, #71b280);
}
#studentListView {
    -fx-background-color: white;

    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.9) , 15, 0.0 , 0 , 2 );
}

#commandBoxPlaceholder {
    -fx-border-color: transparent transparent white transparent;

}

#commandTypeLabel {
    -fx-font-size: 20px;
    -fx-text-fill: white;

}

.context-menu {
    -fx-background-color: derive(secondary, 30%);
}

.context-menu .label {
    -fx-text-fill: white;
}
#commandTextField {
    -fx-font-family: "ProFontWindows", "Courier New";
    -fx-font-size: 18pt;
    -fx-text-fill: white;

}

.result-display {
    -fx-font-size: 15pt;
    -fx-text-fill: white;
}

/*
 * Student panels
 */
.list-cell {
    -fx-border-color: transparent transparent lightgrey transparent;
    -fx-border-width: 3;
}
.list-cell:filled:selected {
    -fx-background-color: rgba(0, 0, 0, 0.6);
}
.list-cell:filled:hover {
    -fx-background-color: rgba(0, 0, 0, 0.4);
}

/*
 *  Scroll bars
 */

.scroll-bar {
    -fx-background-color: derive(darkgrey, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(lightgray, 80%);
    -fx-background-insets: 1;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 4 1 4;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 4 1 4 1;
}

#infoPanel {
    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.8) , 15, 0.0 , 0 , 2 );
}

.pane-with-border {

}
/**
 * Menu Bar
 */

#menuBar {
    -fx-background-color: linear-gradient(to right, #134e5e, #71b280);
    -fx-text-fill: white;
    -fx-border-color: transparent transparent lightgray transparent;

}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

.anchor-pane{
    -fx-text-fill: white;
}


/*For Tags*/
#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
    -fx-opacity: 0.8;
}

#tags .label {
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

#tags .teal {
    -fx-text-fill: black;
    -fx-background-color: teal;
}

#tags .red {
    -fx-text-fill: white;
    -fx-background-color: red;
}

#tags .blue {
    -fx-text-fill: white;
    -fx-background-color: blue;
}

#tags .orange {
    -fx-text-fill: black;
    -fx-background-color: orange;
}

#tags .yellow {
    -fx-text-fill: black;
    -fx-background-color: yellow;
}

#tags .cyan {
    -fx-text-fill: black;
    -fx-background-color: cyan;
}

#tags .gold {
    -fx-text-fill: white;
    -fx-background-color: gold;
}
#tags .khaki {
    -fx-text-fill: black;
    -fx-background-color: khaki;
}

#tags .green {
    -fx-text-fill: black;
    -fx-background-color: green;
}

#tags .olive {
    -fx-text-fill: white;
    -fx-background-color: olive;
}

.test {
    -fx-font-size: 25px !important;

}
```
