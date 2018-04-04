# yuxiangSg
###### /java/seedu/address/ui/CalendarPanel.java
``` java
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel {

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private CalendarView calendarPage;

    public CalendarPanel(CalendarSource calendar) {
        calendarPage = new CalendarView();
        assignCalendar(calendar);
        configurCalendarPage();
        EventsCenter.getInstance().registerHandler(this);
    }

    /**
     * Configure the calendarView to fit the browser panel.
     */
    void configurCalendarPage() {
        calendarPage.setShowAddCalendarButton(false);
        calendarPage.setShowDeveloperConsole(false);
        calendarPage.setShowPageSwitcher(true);
        calendarPage.setShowPageToolBarControls(true);
        calendarPage.setShowPrintButton(false);
        calendarPage.setShowSearchField(false);
        calendarPage.setShowSearchResultsTray(false);
        calendarPage.setShowSourceTray(false);
        calendarPage.setShowToolBar(false);
        calendarPage.showMonthPage();


    }

    /**
     * Assign calendar to Calendar GUI
     */
    void assignCalendar(CalendarSource calendar) {
        calendarPage.getCalendarSources().setAll(calendar);
    }

    @Subscribe
    private void handleCalendarFocusEvent(CalendarFocusEvent event) {
        calendarPage.showDate(event.dateToLook);
    }

    @Subscribe
    private void handleCalendarUnFocusEvent(CalendarUnfocusEvent event) {
        calendarPage.showMonthPage();
    }

    public CalendarView getCalendarPage() {
        return calendarPage;
    }
}
```
###### /java/seedu/address/ui/AgendaPanel.java
``` java
/**
 * The Agenda Panel of the App.
 */
public class AgendaPanel {
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private AgendaView agendaView;

    public AgendaPanel(CalendarSource calendar) {
        agendaView = new AgendaView();
        assignCalendar(calendar);
        EventsCenter.getInstance().registerHandler(this);
    }

    /**
     * Assign ca to Agenda panel GUI
     */
    void assignCalendar(CalendarSource calendar) {
        agendaView.getCalendarSources().setAll(calendar);
    }

    public AgendaView getAgendaView() {
        return agendaView;
    }
}
```
###### /java/seedu/address/commons/events/ui/CalendarFocusEvent.java
``` java
/**
 * Represents a calendar GUI focus date request
 */
public class CalendarFocusEvent extends BaseEvent {
    public final LocalDate dateToLook;

    public CalendarFocusEvent(LocalDate dateToLook) {
        this.dateToLook = dateToLook;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/CalendarUnfocusEvent.java
``` java
/**
 * Represents a calendar GUI unfocus date request
 */
public class CalendarUnfocusEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
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
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START_INTERVAL, PREFIX_END_INTERVAL);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_START_INTERVAL, PREFIX_END_INTERVAL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            String appointmentTitle = ParserUtil.parseString(argMultimap.getValue(PREFIX_NAME)).get();
            LocalDateTime startDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_INTERVAL)).get();
            LocalDateTime endDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_INTERVAL)).get();
            Interval interval = new Interval(startDateTime, endDateTime);


            AppointmentEntry appointmentEntry = new AppointmentEntry(appointmentTitle, interval);

            return new AddAppointmentCommand(appointmentEntry);
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
###### /java/seedu/address/logic/parser/RemoveAppointmentCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemoveAppointmentsCommand object
 */
public class RemoveAppointmentCommandParser implements Parser<RemoveAppointmentsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveAppointmentsCommand
     * and returns a RemoveAppointsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RemoveAppointmentsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SEARCH_TEXT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SEARCH_TEXT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveAppointmentsCommand.MESSAGE_USAGE));
        }

        try {
            String searchText = ParserUtil.parseString(argMultimap.getValue(PREFIX_SEARCH_TEXT)).get();
            return new RemoveAppointmentsCommand(searchText);
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
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String input} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     */
    static String parseString(String input) {
        requireNonNull(input);
        String trimmedInput = input.trim();

        return trimmedInput;
    }

    /**
     * Parses a {@code Optional<String> input} into an {@code Optional<String>} if {@code input} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseString(Optional<String> input) throws IllegalValueException {
        requireNonNull(input);
        return input.isPresent() ? Optional.of(parseString(input.get())) : Optional.empty();
    }


    /**
     * Parses a {@code String input} into a {@code LocalDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    static LocalDateTime parseDateTime(String input) throws  IllegalValueException {
        requireNonNull(input);
        String trimmedInput = input.trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppointmentEntry.DATE_VALIDATION);

        try {

            LocalDateTime dateTime = LocalDateTime.parse(trimmedInput, formatter);
            return dateTime;

        } catch (DateTimeParseException e) {
            throw new IllegalValueException(AppointmentEntry.MESSAGE_DATE_TIME_CONSTRAINTS);
        }



    }

    /**
     * Parses a {@code Optional<String> input} into an {@code Optional<LocalDateTime>} if {@code input} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<LocalDateTime> parseDateTime(Optional<String> input) throws IllegalValueException {
        requireNonNull(input);
        return input.isPresent() ? Optional.of(parseDateTime(input.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String input} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    static LocalDate parseDate(String input) throws  IllegalValueException {
        requireNonNull(input);
        String trimmedInput = input.trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LookDateCommand.DATE_VALIDATION);

        try {

            LocalDate date = LocalDate.parse(trimmedInput, formatter);
            return date;

        } catch (DateTimeParseException e) {
            throw new IllegalValueException(LookDateCommand.MESSAGE_DATE_CONSTRAINTS);
        }



    }

    /**
     * Parses a {@code Optional<String> input} into an {@code Optional<LocalDate>} if {@code input} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<LocalDate> parseDate(Optional<String> input) throws IllegalValueException {
        requireNonNull(input);
        return input.isPresent() ? Optional.of(parseDate(input.get())) : Optional.empty();
    }
```
###### /java/seedu/address/logic/parser/LookDateCommandParser.java
``` java
/**
 * Parses input arguments and creates a new LookDateCommand object
 */
public class LookDateCommandParser implements Parser<LookDateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LookDateCommand
     * and returns anLookDateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LookDateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE_FOCUS);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE_FOCUS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LookDateCommand.MESSAGE_USAGE));
        }

        try {

            LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_FOCUS)).get();

            return new LookDateCommand(date);
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
###### /java/seedu/address/logic/parser/EditAppointmentCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditAppointmentCommand object
 */
public class EditAppointmentCommandParser implements Parser<EditAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditAppointCommand
     * and returns an ditAppointCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SEARCH_TEXT, PREFIX_NAME,
                        PREFIX_START_INTERVAL, PREFIX_END_INTERVAL);

        if (!arePrefixesPresent(argMultimap, PREFIX_SEARCH_TEXT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAppointmentCommand.MESSAGE_USAGE));
        }

        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();
        String searchText;

        try {
            searchText = ParserUtil.parseString(argMultimap.getValue(PREFIX_SEARCH_TEXT)).get();

            ParserUtil.parseString(argMultimap.getValue(PREFIX_NAME))
                    .ifPresent(editAppointmentDescriptor::setGivenTitle);
            ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_INTERVAL))
                    .ifPresent(editAppointmentDescriptor::setStartDateTime);
            ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_INTERVAL))
                    .ifPresent(editAppointmentDescriptor::setEndDateTime);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editAppointmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAppointmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAppointmentCommand(searchText, editAppointmentDescriptor);
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
###### /java/seedu/address/logic/commands/RemoveAppointmentsCommand.java
``` java
/**
 * removes appointment whose title match with the searchText in the address book's calendar.
 */

public class RemoveAppointmentsCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remove_appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": remove appointment whose title match with the search text in the calendar. "
            + "Parameters: "
            + PREFIX_SEARCH_TEXT + "SEARCH TEXT "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_SEARCH_TEXT + "title of the appointment ";

    public static final String MESSAGE_SUCCESS = "Appointment with title %1$s removed";
    public static final String MESSAGE_NO_SUCH_APPOINTMENT = "No such appointment exists in the calendar";

    private final String searchText;

    /**
     * Creates an RemoveAppointmentCommand to remove the specified {@code searchText}
     */
    public RemoveAppointmentsCommand(String searchText) {
        requireNonNull(searchText);
        this.searchText = searchText;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.removeAppointment(searchText);
            return new CommandResult(String.format(MESSAGE_SUCCESS, searchText));
        } catch (AppointmentNotFoundException e) {
            throw new CommandException(MESSAGE_NO_SUCH_APPOINTMENT);
        }
    }
}
```
###### /java/seedu/address/logic/commands/LookDateCommand.java
``` java
/**
 * Look at a specific date give the date to look
 */
public class LookDateCommand extends Command {
    public static final String COMMAND_WORD = "look";

    public static final String DATE_VALIDATION = "d/MM/yyyy";

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should be in the format of dd/MM/yyyy";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Focus on a given date ."
            + "Parameters: "
            + PREFIX_DATE_FOCUS + "DATE TO FOCUS "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_DATE_FOCUS + "11/03/2018";

    public static final String FOCUS_DATE_MESSAGE = "FOCUS ON DATE";

    /**
     * Creates an RemoveAppointmentCommand to remove the specified {@code searchText}
     */
    final LocalDate dateToLook;

    public LookDateCommand(LocalDate dateToLook) {
        requireNonNull(dateToLook);
        this.dateToLook = dateToLook;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new CalendarFocusEvent(dateToLook));
        return new CommandResult(FOCUS_DATE_MESSAGE);
    }
}
```
###### /java/seedu/address/logic/commands/AddAppointmentCommand.java
``` java
/**
 * Adds a appointment to the address book's calendar.
 */

public class AddAppointmentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add_appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the calendar. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_START_INTERVAL + "START DATE TIME "
            + PREFIX_END_INTERVAL + "END DATE TIME"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Meet John "
            + PREFIX_START_INTERVAL + "14/08/2018 06:12 "
            + PREFIX_END_INTERVAL + "14/08/2018 07:12 ";

    public static final String MESSAGE_SUCCESS = "New Appointment Added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT =
            "appointment with the same title already exists in the calendar";

    private final AppointmentEntry toAdd;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code AppointmentEntry}
     */
    public AddAppointmentCommand(AppointmentEntry appointmentEntry) {
        requireNonNull(appointmentEntry);
        toAdd = appointmentEntry;
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
###### /java/seedu/address/logic/commands/ReturnMonthViewCommand.java
``` java
/**
 * return to month view for the calendar GUI.
 */
public class ReturnMonthViewCommand extends Command {
    public static final String COMMAND_WORD = "back";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unfocus the Calendar to Month view.\n"
            + "Example: " + COMMAND_WORD;

    public static final String RETURN_MONTH_VIEW_MESSAGE = "Calendar back to Month view";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new CalendarUnfocusEvent());
        return new CommandResult(RETURN_MONTH_VIEW_MESSAGE);
    }
}
```
###### /java/seedu/address/logic/commands/EditAppointmentCommand.java
``` java
/**
 * Edit an appointment in the address book's calendar.
 */
public class EditAppointmentCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "edit_appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the appointment specified by "
            + "searching the title. Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_SEARCH_TEXT + "SEARCH TEXT "
            + PREFIX_NAME + "NEW NAME "
            + PREFIX_START_INTERVAL + "NEW START DATE TIME "
            + PREFIX_END_INTERVAL + "NEW END DATE TIME"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_SEARCH_TEXT + "Meet Peter "
            + PREFIX_START_INTERVAL + "14/08/2018 06:12 "
            + PREFIX_END_INTERVAL + "14/08/2018 07:12 ";

    public static final String MESSAGE_SUCCESS = "Appointment Edited: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_FAIL_EDIT_APPOINTMENT = "appointment do not exit or duplicate new title";

    private final String searchText;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    private AppointmentEntry appointmentEdited;


    /**
     * Creates an EditAppointmentCommand to Edit the specified {@code AppointmentEntry}
     */
    public EditAppointmentCommand(String searchText, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireNonNull(searchText);
        requireNonNull(editAppointmentDescriptor);

        this.searchText = searchText;
        this.editAppointmentDescriptor = editAppointmentDescriptor;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.editAppointment(searchText, appointmentEdited);
            return new CommandResult(String.format(MESSAGE_SUCCESS, appointmentEdited));
        } catch (EditAppointmentFailException e) {
            throw new CommandException(MESSAGE_FAIL_EDIT_APPOINTMENT);
        }

    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        AppointmentEntry appointmentToEdit;
        try {
            appointmentToEdit = model.findAppointment(searchText);
        } catch (AppointmentNotFoundException e) {
            throw new CommandException(MESSAGE_FAIL_EDIT_APPOINTMENT);
        }

        appointmentEdited = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor);
    }

    /**
     * Creates and returns a {@code Appointment} with the details of Appointmententry found by SearchText
     * edited with {@code editAppointmentDescriptor}.
     */
    private static AppointmentEntry createEditedAppointment(AppointmentEntry appointmentToEdit,
                                                            EditAppointmentDescriptor descriptor) {
        requireNonNull(appointmentToEdit);

        String updatedTitle = descriptor.getGivenTitle().orElse(appointmentToEdit.getGivenTitle());
        LocalDateTime updatedStartDateTime =
                descriptor.getStartDateTime().orElse(appointmentToEdit.getStartDateTime());
        LocalDateTime updatedEndDateTime = descriptor.getEndDateTime().orElse(appointmentToEdit.getEndDateTime());
        Interval updatedInterval = new Interval(updatedStartDateTime, updatedEndDateTime);

        return new AppointmentEntry(updatedTitle, updatedInterval);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditAppointmentCommand // instanceof handles nulls
                && editAppointmentDescriptor.equals(((EditAppointmentCommand) other).editAppointmentDescriptor));
    }

    /**
     * Stores the details to edit the appointmentEntry with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditAppointmentDescriptor {
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private String givenTitle;

        public EditAppointmentDescriptor(){

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.givenTitle, this.startDateTime, this.endDateTime);
        }

        public void setStartDateTime(LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        public Optional<LocalDateTime> getStartDateTime() {
            return Optional.ofNullable(startDateTime);
        }

        public void setEndDateTime(LocalDateTime endDateTime) {
            this.endDateTime = endDateTime;

        }

        public Optional<LocalDateTime> getEndDateTime() {
            return Optional.ofNullable(endDateTime);
        }

        public void setGivenTitle(String givenTitle) {
            this.givenTitle = givenTitle;
        }

        public Optional<String> getGivenTitle() {
            return Optional.ofNullable(givenTitle);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentCommand.EditAppointmentDescriptor)) {
                return false;
            }

            // state check
            EditAppointmentCommand.EditAppointmentDescriptor e =
                    (EditAppointmentCommand.EditAppointmentDescriptor) other;

            return getEndDateTime().equals(e.getEndDateTime())
                    && getStartDateTime().equals(e.getStartDateTime())
                    && getGivenTitle().equals(e.getGivenTitle());
        }

    }
}
```
###### /java/seedu/address/storage/XmlAdptedAppointmentEntry.java
``` java
/**
 * JAXB-friendly version of the Appointment Entry.
 */
public class XmlAdptedAppointmentEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment Entry's %s field is missing!";
    public static final String MISSING_FIELD_TITLE = "[TITLE]";
    public static final String MISSING_FIELD_START_DATE = "[START_DATE]";
    public static final String MISSING_FIELD_END_DATE = "[END_DATE]";
    public static final String DATE_VALIDATION = "yyyy-MM-d'T'HH:mm";
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should be in the format of yyyy-MM-d'T'HH:mm";

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private String endDate;

    /**
     * Constructs an XmlAdaptedCalendarEntry.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdptedAppointmentEntry() {
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdptedAppointmentEntry(String title, String startDate, String endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    /**
     * Converts a given appoinmentEntry into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAppointmentEntry
     */
    public XmlAdptedAppointmentEntry(AppointmentEntry source) {
        title = source.getGivenTitle();
        startDate = source.getStartDateTime().toString();
        endDate = source.getEndDateTime().toString();
    }

    /**
     * Converts this jaxb-friendly adapted calendarEntry object into the model's AppointmentEntry object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public AppointmentEntry toModelType() throws IllegalValueException {

        if (this.title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MISSING_FIELD_TITLE));
        }

        final String title = this.title;

        if (this.startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MISSING_FIELD_START_DATE));
        }

        final String newStartDate = this.startDate;

        if (this.endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MISSING_FIELD_END_DATE));
        }

        final String newEndDate = this.endDate;

        final Interval interval = new Interval(getLocatDateTime(newStartDate), getLocatDateTime(newEndDate));

        return new AppointmentEntry(title, interval);
    }

    LocalDateTime getLocatDateTime(String date) throws IllegalValueException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_VALIDATION);

        try {

            LocalDateTime localDatetime = LocalDateTime.parse(date, formatter);
            return localDatetime;

        } catch (DateTimeParseException e) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdptedAppointmentEntry otherEntry = (XmlAdptedAppointmentEntry) other;
        return Objects.equals(title, otherEntry.title)
                && Objects.equals(startDate, otherEntry.startDate)
                && Objects.equals(endDate, otherEntry.endDate);
    }
}
```
###### /java/seedu/address/model/calendar/AppointmentEntry.java
``` java
/**
 * Represents a appointment in the calendar.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class AppointmentEntry {

    public static final String MESSAGE_DATE_TIME_CONSTRAINTS =
            "Date and Time should be in the format of dd/MM/yyyy HH:mm";
    public static final String DATE_VALIDATION = "d/MM/yyyy HH:mm";

    private final Entry appointmentEntry;
    private Interval interval;
    private String givenTitle;

    public AppointmentEntry(String title, Interval timeSlot) {
        requireAllNonNull(title, timeSlot);
        appointmentEntry = new Entry(title, timeSlot);
        interval = timeSlot;
        givenTitle = title;
    }

    public AppointmentEntry(AppointmentEntry clonedEntry) {
        requireAllNonNull(clonedEntry);
        appointmentEntry = new Entry(clonedEntry.getGivenTitle(), clonedEntry.getInterval());
        interval = clonedEntry.getInterval();
        givenTitle = clonedEntry.getGivenTitle();
    }

    public LocalDateTime getStartDateTime() {
        return interval.getStartDateTime();
    }

    public LocalDateTime getEndDateTime() {
        return interval.getEndDateTime();
    }

    public Entry getAppointmentEntry() {
        return appointmentEntry;
    }

    public String getGivenTitle() {
        return givenTitle;
    }

    public Interval getInterval() {
        return interval;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(givenTitle)
                .append(" Start Date: ")
                .append(interval.getStartDate().toString())
                .append(" End Date: ")
                .append(interval.getEndDate().toString());

        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AppointmentEntry)) {
            return false;
        }

        AppointmentEntry otherAppointment = (AppointmentEntry) other;
        return otherAppointment.givenTitle.equals(this.getGivenTitle());
    }
}
```
###### /java/seedu/address/model/calendar/exceptions/EditAppointmentFailException.java
``` java
/**
 * Signals that the operation will result in either a duplicate AppointmentEntry objects
 * or unable to find an specified appointment .
 */

public class EditAppointmentFailException extends Exception {
}
```
###### /java/seedu/address/model/calendar/exceptions/DuplicateAppointmentException.java
``` java
/**
 * Signals that the operation will result in duplicate AppointmentEntry objects.
 */

public class DuplicateAppointmentException extends DuplicateDataException {
    public DuplicateAppointmentException() {
        super("Operation would result in duplicate appointments");
    }
}
```
###### /java/seedu/address/model/calendar/exceptions/AppointmentNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified appointment.
 */
public class AppointmentNotFoundException extends Exception {
}
```
###### /java/seedu/address/model/calendar/InsuranceCalendar.java
``` java
/**
 * The calendar in the address book.
 */
public class InsuranceCalendar {

    private CalendarSource calendarSource;
    private Calendar calendar;
    private ArrayList<AppointmentEntry> appointmentEntries;

    public InsuranceCalendar() {

        calendar = new Calendar();
        calendarSource = new CalendarSource();
        calendarSource.getCalendars().add(calendar);
        appointmentEntries = new ArrayList<>();
    }
    /**
     * Clear all appointments to the calendar.
     *
     */
    public void clearAppointments () {
        appointmentEntries.clear();
        calendar.clear();
    }

    /**
     * copy all appointments to the calendar given a new calendar.
     *
     */
    public void copyAppointments (InsuranceCalendar copyingCalendar) {

        for (AppointmentEntry entry: copyingCalendar.getAppointmentEntries()) {
            AppointmentEntry addedEntry = new AppointmentEntry(entry);
            calendar.addEntry(addedEntry.getAppointmentEntry());
            appointmentEntries.add(addedEntry);
        }
    }

    /**
     * Adds an appointment to the calendar.
     *
     * @throws DuplicateAppointmentException if the appointment to add is a duplicate of an existing appointments.
     */
    public void addAppointment(AppointmentEntry entry) throws DuplicateAppointmentException {
        if (contains(entry)) {
            throw new DuplicateAppointmentException();
        }

        calendar.addEntry(new AppointmentEntry(entry).getAppointmentEntry());
        appointmentEntries.add(entry);

    }

    /**
     * Remove appointments found with the given keywords in the calendar.
     *
     * @throws AppointmentNotFoundException if the appointment to remove does not exist.
     */
    public void removeAppointment(String searchText) throws AppointmentNotFoundException {

        List<Entry<?>> foundEntires = calendar.findEntries(searchText);
        if (foundEntires.isEmpty()) {
            throw new AppointmentNotFoundException();
        }

        for (Entry entry: foundEntires) {
            removeAppointmentEntry (entry);
        }
        calendar.removeEntries(foundEntires);


    }

    /**
     * remove a given entry in the appointmentEntries
     *
     */
    public void removeAppointmentEntry(Entry entryToCheck) {

        String givenTitle = entryToCheck.getTitle();
        Interval givenInterval = entryToCheck.getInterval();
        AppointmentEntry appointmentEntryToCheck = new AppointmentEntry(givenTitle, givenInterval);
        appointmentEntries.remove(appointmentEntryToCheck);
    }

    /**
     * edit appointments found with the the given searchText
     *
     * @throws EditAppointmentFailException if the appointment to remove does not exist or duplicate appointment to add.
     */
    public void editAppointmentEntry(String searchText, AppointmentEntry referenceEntry)
            throws EditAppointmentFailException {

        try {
            removeAppointment(searchText);
        } catch (AppointmentNotFoundException e) {
            throw new EditAppointmentFailException();
        }

        try {
            addAppointment(referenceEntry);
        } catch (DuplicateAppointmentException e) {
            throw new EditAppointmentFailException();
        }
    }

    /**
     * return appointments found with the given keywords in the calendar.
     *
     * @throws AppointmentNotFoundException if the appointment to find does not exist.
     */
    public AppointmentEntry findAppointment(String searchText) throws AppointmentNotFoundException {

        List<Entry<?>> foundEntires = calendar.findEntries(searchText);

        if (foundEntires.isEmpty()) {
            throw new AppointmentNotFoundException();
        } else {
            return findAppointmentEntry(foundEntires.get(0));
        }
    }

    /**
     * return a given entry in the appointmentEntries
     *
     */
    public AppointmentEntry findAppointmentEntry(Entry entryToCheck) {

        String givenTitle = entryToCheck.getTitle();
        Interval givenInterval = entryToCheck.getInterval();
        AppointmentEntry appointmentEntryToCheck = new AppointmentEntry(givenTitle, givenInterval);

        for (AppointmentEntry entry : appointmentEntries) {
            if (entry.equals(appointmentEntryToCheck)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Returns true if the calender contains an equivalent appointment as the given argument.
     */
    public boolean contains(AppointmentEntry toCheck) {
        requireNonNull(toCheck);
        return appointmentEntries.contains(toCheck);
    }

    public CalendarSource getCalendar() {
        return calendarSource;
    }

    public ArrayList<AppointmentEntry> getAppointmentEntries() {
        return appointmentEntries;
    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void setCalendar(InsuranceCalendar calendar) {
        this.calendar.clearAppointments();
        this.calendar.copyAppointments(calendar);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    //// calendar-level operations

    /**
     * Adds a appointment entry to the calendar.
     *
     * @throws DuplicateAppointmentException if an equivalent appointment already exists.
     */
    public void addAppointment(AppointmentEntry entry) throws DuplicateAppointmentException {
        calendar.addAppointment(entry);
    }

    /**
     * remove appointment entries related to the searchText in the calendar.
     *
     * @throws AppointmentNotFoundException if an equivalent appointment already exists.
     */
    public void removeAppointment(String searchText) throws AppointmentNotFoundException {
        calendar.removeAppointment(searchText);
    }

    /**
     * edit an existing appointment entry in the calendar.
     *
     * @throws EditAppointmentFailException if an equivalent appointment already exists.
     */
    public void editAppointment(String searchText, AppointmentEntry referenceEntry)
            throws EditAppointmentFailException {
        calendar.editAppointmentEntry(searchText, referenceEntry);
    }

    /**
     * find an existing appointment entry in the calendar given the searchText.
     *
     * @throws AppointmentNotFoundException if an equivalent appointment already exists.
     */
    public AppointmentEntry findAppointment(String searchText) throws AppointmentNotFoundException {
        return calendar.findAppointment(searchText);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    CalendarSource getCalendar() {
        return calendar.getCalendar();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void addAppointment(AppointmentEntry appointmentEntry) throws DuplicateAppointmentException {
        addressBook.addAppointment(appointmentEntry);
        indicateAddressBookChanged();

    }

    @Override
    public void removeAppointment(String searchText) throws AppointmentNotFoundException {
        addressBook.removeAppointment(searchText);
        indicateAddressBookChanged();
    }

    @Override
    public void editAppointment(String searchText, AppointmentEntry reference) throws EditAppointmentFailException {
        addressBook.editAppointment(searchText, reference);
        indicateAddressBookChanged();

    }
```
###### /java/seedu/address/model/Model.java
``` java
    /** Adds the given appointment entry */
    void addAppointment(AppointmentEntry appointmentEntry) throws DuplicateAppointmentException;

    /** remove appointments associated with the given searchText */
    void removeAppointment(String searchText) throws AppointmentNotFoundException;

    /** edit appointment associated with the given searchText */
    void editAppointment(String searchText, AppointmentEntry reference) throws EditAppointmentFailException;

    /** find an appointment associated with the given searchText */
    AppointmentEntry findAppointment(String searchText) throws AppointmentNotFoundException;

    /** returns the calendar in the addressbook */
    CalendarSource getCalendar();
```
