# trafalgarandre
###### /java/seedu/address/model/person/ProfilePicture.java
``` java
/**
 * Represents a ProfilePicture in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ProfilePicture {
    public static final String MESSAGE_PROFILEPICTURE_CONSTRAINTS =
            "Profile picture name should be a valid image name,"
                    + " and it should end with either jpeg, jpg, png, gif or bmp";
    public static final String MESSAGE_PROFILEPICTURE_NOT_EXISTS =
            "Profile picture does not exists. Please give another profile picture";

    // alphanumeric and special characters
    public static final String PROFILE_PICTURE_VALIDATION_REGEX = "^$|([^\\s]+(\\.(?i)(jpeg|jpg|png|gif|bmp))$)";

    private static final String defaultImgPath = "./src/test/data/images/default.png";
    private static final String defaultImgURL = "file:src/test/data/images/default.png";
    private static final String profilePictureFolder =
            "./src/main/resources/ProfilePictures/";

    private static int id = 0;

    public final String value;
    public final String path;

    /**
     * Constructs an {@code Email}.
     *
     * @param profilePicture A valid image path.
     */
    public ProfilePicture(String... profilePicture) {
        if (profilePicture.length != 0 && profilePicture[0] != null) {
            checkArgument(isValidProfilePicture(profilePicture[0]), MESSAGE_PROFILEPICTURE_CONSTRAINTS);
            checkArgument(hasValidProfilePicture(profilePicture[0]), MESSAGE_PROFILEPICTURE_NOT_EXISTS);

            this.value = profilePicture[0];
            String temp = new String();
            try {
                temp = new File(copyImageToProfilePictureFolder(profilePicture[0])).toURI().toURL().toExternalForm();
            } catch (MalformedURLException e) {
                temp = defaultImgURL;
            } finally {
                this.path = temp;
            }
        } else {
            this.value = defaultImgPath;
            this.path = defaultImgURL;
        }
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidProfilePicture(String test) {
        return test.matches(PROFILE_PICTURE_VALIDATION_REGEX);
    }

    /**
     * Returns if there exists profile picture.
     * @param profilePicture
     * @return
     */
    public static boolean hasValidProfilePicture(String profilePicture) {
        File file = new File(profilePicture);
        return file.exists() && !file.isDirectory();
    }

    public Image getImage() {
        return new Image(path);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePicture // instanceof handles nulls
                && this.value.equals(((ProfilePicture) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * copy the image from the absolute path to the Profile Picture Folder
     * @param profilePicture
     * @return destination path
     */
    private String copyImageToProfilePictureFolder(String profilePicture) {
        String destPath = "";
        try {
            File source = new File(profilePicture);
            String fileExtension = extractFileExtension(profilePicture);
            destPath = profilePictureFolder.concat(Integer.toString(id).concat(".").concat(fileExtension));
            File dest = new File(destPath);
            Files.copy(source.toPath(), dest.toPath());
            id++;
        } catch (IOException e) {
            // Exception will not happen as the profile picture path has been check through hasValidProfilePicture
        }
        return destPath;
    }

    /**
     * extract FileExtension from fileName
     * @param fileName
     * @return fileExtension
     */
    private String extractFileExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }
}
```
###### /java/seedu/address/logic/parser/AddAppointmentCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_TITLE, PREFIX_START_DATE_TIME, PREFIX_END_DATE_TIME);
        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_START_DATE_TIME, PREFIX_END_DATE_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }
        try {
            Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE)).get();
            StartDateTime startDateTime =
                    ParserUtil.parseStartDateTime(argMultimap.getValue(PREFIX_START_DATE_TIME)).get();
            EndDateTime endDateTime = ParserUtil.parseEndDateTime(argMultimap.getValue(PREFIX_END_DATE_TIME)).get();

            Appointment appointment = new Appointment(title, startDateTime, endDateTime);

            return new AddAppointmentCommand(appointment);
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
###### /java/seedu/address/logic/commands/AddAppointmentCommand.java
``` java
/**
 * Add appointment to calendar of addressbook
 */
public class AddAppointmentCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addapp";

    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_TITLE + " "
            + PREFIX_START_DATE_TIME + " "
            + PREFIX_END_DATE_TIME;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to calendar. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_START_DATE_TIME + "START DATE TIME "
            + PREFIX_END_DATE_TIME + "END DATE TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Birthday "
            + PREFIX_START_DATE_TIME + "2018-03-26 12:00 "
            + PREFIX_END_DATE_TIME + "2018-03-26 12:30 ";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the calendar";

    private final Appointment toAdd;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Appointment appointment) {
        requireNonNull(appointment);
        toAdd = appointment;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addAppointment(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateAppointmentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && toAdd.equals(((AddAppointmentCommand) other).toAdd));
    }
}
```
###### /java/seedu/address/logic/commands/CalendarCommand.java
``` java
/**
 * Switch tab to Calendar
 */
public class CalendarCommand extends Command {
    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_SUCCESS = "Opened your calendar";

    public static final int TAB_ID = 2;

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new SwitchTabRequestEvent(TAB_ID));
        return new CommandResult(String.format(MESSAGE_SUCCESS));

    }

}
```
###### /java/seedu/address/ui/DetailsPanel.java
``` java
    @Subscribe
    private void handleReloadCalendarEvent(ReloadCalendarEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        addCalendarPanel(event.appointments);
    }
}
```
###### /java/seedu/address/ui/CalendarPanel.java
``` java
/**
 * The CalendarPanel of the App
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private CalendarSource source = new CalendarSource("My Calendars");
    private Calendar calendar = new Calendar("My Calendar");
    private ArrayList<Entry> entries = new ArrayList<Entry>();

    @FXML
    private CalendarView calendarView;



    public CalendarPanel(List<Appointment> appointmentsList) {
        super(FXML);
        System.out.println(appointmentsList.size());
        calendar.setStyle(Calendar.Style.STYLE1);
        addAppointments(appointmentsList);
        source.getCalendars().add(calendar);
        setUpCalendarView();
        startTimeThread();
        registerAsAnEventHandler(this);
    }

    /**
     * Add appointment to Calendar
     * @param appointments
     */
    private void addAppointments(List<Appointment> appointments) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Appointment appointment: appointments) {
            Entry entry = new Entry();
            entry.setCalendar(calendar);
            System.out.println(appointment.getTitle().title);
            LocalDateTime start = LocalDateTime.parse(appointment.getStartDateTime().startDateTime, formatter);
            LocalDateTime end = LocalDateTime.parse(appointment.getEndDateTime().endDateTime, formatter);
            entry.setInterval(start, end);
            entry.setTitle(appointment.getTitle().title);
            calendar.addEntry(entry);
        }
    }

    /**
     * Set up calendar View
     */
    public void setUpCalendarView() {
        calendarView.getCalendarSources().addAll(source);
        calendarView.setRequestedTime(LocalTime.now());
    }

    /**
     * Start time thread
     */
    private void startTimeThread() {
        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }
}
```
###### /resources/view/CalendarPanel.fxml
``` fxml
<StackPane xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/9">
    <CalendarView fx:id="calendarView" />
</StackPane>
```
