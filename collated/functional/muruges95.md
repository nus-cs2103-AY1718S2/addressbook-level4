# muruges95
###### \java\seedu\address\commons\events\model\StorageCalendarChangedEvent.java
``` java
/** Indicates the StorageCalendar in the model has changed*/
public class StorageCalendarChangedEvent extends BaseEvent {
    public final StorageCalendar data;

    public StorageCalendarChangedEvent(StorageCalendar data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowAppointmentListEvent.java
``` java
/**
 * Event to be raised when listappointment command is invoked.
 */
public class ShowAppointmentListEvent extends BaseEvent {

    private final List<Appointment> appointments;

    public ShowAppointmentListEvent(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\calendar\AddAppointmentCommand.java
``` java
/**
 * Adds an appointment to a calendar.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "addAppointment";
    public static final String COMMAND_ALIAS = "aa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment.\n"
            + "Parameters: "
            + PREFIX_NAME + "APPOINTMENT NAME "
            + "[" + PREFIX_START_TIME + "START TIME] "
            + "[" + PREFIX_START_DATE + "START DATE] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_END_TIME + "END TIME] "
            + "[" + PREFIX_END_DATE + "END DATE] "
            + "[" + PREFIX_CELEBRITY + "CELEBRITY_INDEX]... "
            + "[" + PREFIX_POINT_OF_CONTACT + "POINT_OF_CONTACT_INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Oscars 2018 "
            + PREFIX_START_TIME + "18:00 "
            + PREFIX_START_DATE + "23-04-2018 "
            + PREFIX_LOCATION + "Hollywood "
            + PREFIX_END_TIME + "20:00 "
            + PREFIX_END_DATE + "23-04-2018 "
            + PREFIX_CELEBRITY + "1 "
            + PREFIX_CELEBRITY + "2 "
            + PREFIX_POINT_OF_CONTACT + "3 "
            + PREFIX_POINT_OF_CONTACT + "4 ";

    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the application";

    public static final String MESSAGE_NOT_IN_COMBINED_CALENDAR = "Can only add appointment when "
            + "viewing combined calendar\n"
            + "currently viewing %1$s's calendar";
    public static final String MESSAGE_SUCCESS = "Added appointment: %1$s";

    private final Appointment appt;
    private final Set<Index> celebrityIndices;
    private final Set<Index> pointOfContactIndices;

    /**
     * Creates an AddAppointmentCommand with the following parameter
     * @param appt The created appointment
     * @param celebrityIndices The indices of the celebrities who are part of this appointment
     */
    public AddAppointmentCommand(Appointment appt, Set<Index> celebrityIndices, Set<Index> pointOfContactIndices) {
        requireNonNull(appt);
        this.appt = appt;
        this.celebrityIndices = celebrityIndices;
        this.pointOfContactIndices = pointOfContactIndices;
    }


    @Override
    public CommandResult execute() throws CommandException {
        if (model.getCurrentCelebCalendarOwner() != null) {
            throw new CommandException(
                    String.format(MESSAGE_NOT_IN_COMBINED_CALENDAR,
                            model.getCurrentCelebCalendarOwner().getName().toString()));
        }

        List<Celebrity> celebrityList =  model.getCelebritiesChosen(celebrityIndices);
        List<Person> pointOfContactList = model.getPointsOfContactChosen(pointOfContactIndices);
        try {
            model.addAppointmentToStorageCalendar(appt);
        } catch (DuplicateAppointmentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }
        appt.updateEntries(celebrityList, pointOfContactList);

        // reset calendar view to day view and set base date to the day when appointment starts
        model.setBaseDate(appt.getStartDate());
        EventsCenter.getInstance().post(new ShowCalendarBasedOnDateEvent(appt.getStartDate()));
        model.setCelebCalendarViewPage(DAY_VIEW_PAGE);
        EventsCenter.getInstance().post(new ChangeCalendarViewPageRequestEvent(DAY_VIEW_PAGE));
        if (model.getIsListingAppointments()) {
            model.setIsListingAppointments(false);
            EventsCenter.getInstance().post(new ShowCalendarEvent());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, appt.getTitle()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddAppointmentCommand
                && appt.equals(((AddAppointmentCommand) other).appt));
    }


}
```
###### \java\seedu\address\logic\commands\calendar\EditAppointmentCommand.java
``` java
/**
 * Edits an appointment in a calendar.
 */
public class EditAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "editAppointment";
    public static final String COMMAND_ALIAS = "ea";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an appointment.\n"
            + "Parameters: APPOINTMENT_INDEX (must be a positive integer)"
            + "[" + PREFIX_NAME + "APPOINTMENT NAME] "
            + "[" + PREFIX_START_TIME + "START TIME] "
            + "[" + PREFIX_START_DATE + "START DATE] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_END_TIME + "END TIME] "
            + "[" + PREFIX_END_DATE + "END DATE] "
            + "[" + PREFIX_CELEBRITY + "CELEBRITY_INDEX]... "
            + "[" + PREFIX_POINT_OF_CONTACT + "POINT_OF_CONTACT_INDEX]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Oscars 2018 "
            + PREFIX_START_TIME + "18:00 "
            + PREFIX_START_DATE + "23-04-2018 "
            + PREFIX_LOCATION + "Hollywood "
            + PREFIX_END_TIME + "20:00 "
            + PREFIX_END_DATE + "23-04-2018 "
            + PREFIX_CELEBRITY + "1 "
            + PREFIX_CELEBRITY + "2 "
            + PREFIX_POINT_OF_CONTACT + "3 "
            + PREFIX_POINT_OF_CONTACT + "4 ";

    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the application,"
            + " or the edited fields are the same as the original.";
    public static final String MESSAGE_SUCCESS = "Edited appointment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided";

    private final Index appointmentIndex;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    private Appointment appointmentToEdit;

    public EditAppointmentCommand(Index appointmentIndex, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireNonNull(appointmentIndex);
        requireNonNull(editAppointmentDescriptor);

        this.appointmentIndex = appointmentIndex;
        this.editAppointmentDescriptor = new EditAppointmentDescriptor(editAppointmentDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!model.getIsListingAppointments()) {
            throw new CommandException(Messages.MESSAGE_MUST_SHOW_LIST_OF_APPOINTMENTS);
        }
        try {
            appointmentToEdit = model.getChosenAppointment(appointmentIndex.getZeroBased());
            Appointment editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor);

            // either use existing celebrity list or get new one
            List<Celebrity> celebrityList = (editAppointmentDescriptor.getCelebrityIndices().isPresent())
                    ? model.getCelebritiesChosen(editAppointmentDescriptor.getCelebrityIndices().get())
                    : appointmentToEdit.getCelebrities();

            List<Person> pointOfContactList = (editAppointmentDescriptor.getPointOfContactIndices().isPresent())
                    ? model.getPointsOfContactChosen(editAppointmentDescriptor.getPointOfContactIndices().get())
                    : appointmentToEdit.getPointOfContactList();
            model.addAppointmentToStorageCalendar(editedAppointment);
            appointmentToEdit.removeAppointment();
            editedAppointment.updateEntries(celebrityList, pointOfContactList);

            // reset calendar view to day view
            model.setCelebCalendarViewPage(DAY_VIEW_PAGE);
            EventsCenter.getInstance().post(new ChangeCalendarViewPageRequestEvent(DAY_VIEW_PAGE));
            if (model.getIsListingAppointments()) {
                model.setIsListingAppointments(false);
                EventsCenter.getInstance().post(new ShowCalendarEvent());
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, editedAppointment.getTitle()));
        } catch (IndexOutOfBoundsException iobe) {
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        } catch (ParseException pe) {
            throw new CommandException(MESSAGE_START_DATE_TIME_NOT_BEFORE_END_DATE_TIME);
        } catch (DuplicateAppointmentException dae) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

    }

    /**
     * Creates and returns a {@code Appointment} with the details of {@code apptToEdit}
     * edited with {@code ead}.
     */
    public static Appointment createEditedAppointment(Appointment apptToEdit, EditAppointmentDescriptor ead)
            throws ParseException {
        assert apptToEdit != null;

        String apptName = ead.getAppointmentName().orElse(apptToEdit.getTitle());
        LocalTime startTime = ead.getStartTime().orElse(apptToEdit.getStartTime());
        LocalTime endTime = ead.getEndTime().orElse(apptToEdit.getEndTime());
        LocalDate startDate = ead.getStartDate().orElse(apptToEdit.getStartDate());
        LocalDate endDate = ead.getEndDate().orElse(apptToEdit.getEndDate());
        MapAddress location = ead.getLocation().orElse(apptToEdit.getMapAddress());

        if (isDateTimeNotValid(startDate, endDate, startTime, endTime)) {
            throw new ParseException(MESSAGE_START_DATE_TIME_NOT_BEFORE_END_DATE_TIME);
        }
        return new Appointment(apptName, startTime, startDate, location, endTime, endDate);
    }

    /**
     * Stores the details to edit an appointment with. Each non-empty field value will replace
     * the corresponding field value of the person.
     */
    public static class EditAppointmentDescriptor {
        private String appointmentName;
        private LocalTime startTime;
        private LocalTime endTime;
        private LocalDate startDate;
        private LocalDate endDate;
        private MapAddress location;
        private Set<Index> celebrityIndices;
        private Set<Index> pointOfContactIndices;

        // for JUnit Tests
        private List<Long> celebIds;
        private List<Long> pointOfContactIds;

        public EditAppointmentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code celebrityIndices} is used internally.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setAppointmentName(toCopy.appointmentName);
            setLocation(toCopy.location);
            setStartTime(toCopy.startTime);
            setStartDate(toCopy.startDate);
            setEndTime(toCopy.endTime);
            setEndDate(toCopy.endDate);
            setCelebrityIndices(toCopy.celebrityIndices);
            setPointOfContactIndices(toCopy.pointOfContactIndices);
            setCelebIds(toCopy.celebIds);
            setPointOfContactIds(toCopy.pointOfContactIds);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.appointmentName, this.startTime, this.startDate,
                    this.endTime, this.endDate, this.location, this.celebrityIndices, this.pointOfContactIndices);
        }

        public void setAppointmentName(String appointmentName) {
            this.appointmentName = appointmentName;
        }

        public Optional<String> getAppointmentName() {
            return Optional.ofNullable(appointmentName);
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public Optional<LocalTime> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public Optional<LocalDate> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }

        public Optional<LocalTime> getEndTime() {
            return Optional.ofNullable(endTime);
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public Optional<LocalDate> getEndDate() {
            return Optional.ofNullable(endDate);
        }

        public void setLocation(MapAddress location) {
            this.location = location;
        }

        public Optional<MapAddress> getLocation() {
            return Optional.ofNullable(location);
        }

        /**
         * Sets {@code celebrityIndices} to this object's {@code celebrityIndices}.
         * A defensive copy of {@code celebrityIndices} is used internally.
         */
        public void setCelebrityIndices(Set<Index> celebrityIndices) {
            this.celebrityIndices = (celebrityIndices != null) ? new HashSet<>(celebrityIndices) : null;
        }

        /**
         * Returns an unmodifiable celebrities set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code celebrityIndices} is null.
         */
        public Optional<Set<Index>> getCelebrityIndices() {
            return (celebrityIndices != null) ? Optional.of(Collections.unmodifiableSet(celebrityIndices))
                                              : Optional.empty();
        }

        /**
         * Sets {@code pointOfContactIndices} to this object's {@code pointOfContactIndices}.
         * A defensive copy of {@code pointOfContactIndices} is used internally.
         */
        public void setPointOfContactIndices(Set<Index> pointOfContactIndices) {
            this.pointOfContactIndices = (pointOfContactIndices != null) ? new HashSet<>(pointOfContactIndices) : null;
        }

        /**
         * Returns an unmodifiable points of contact set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code pointOfContactIndices} is null.
         */
        public Optional<Set<Index>> getPointOfContactIndices() {
            return (pointOfContactIndices != null) ? Optional.of(Collections.unmodifiableSet(pointOfContactIndices))
                    : Optional.empty();
        }

        /**
         * Sets {@code celebIds} to this object's {@code celebIds}.
         * A defensive copy of {@code celebIds} is used internally.
         */
        public void setCelebIds(List<Long> celebIds) {
            this.celebIds = (celebIds != null) ? new ArrayList<>(celebIds) : null;
        }

        /**
         * Returns an unmodifiable celebrities id list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code celebIds} is null.
         */
        public Optional<List<Long>> getCelebIds() {
            return (this.celebIds != null)
                    ? Optional.of(Collections.unmodifiableList(celebIds))
                    : Optional.empty();
        }

        /**
         * Sets {@code pointOfContactIds} to this object's {@code pointOfContactIds}.
         * A defensive copy of {@code pointOfContactIds} is used internally.
         */
        public void setPointOfContactIds(List<Long> pointOfContactIds) {
            this.pointOfContactIds = (pointOfContactIds != null) ? new ArrayList<>(pointOfContactIds) : null;
        }

        /**
         * Returns an unmodifiable points of contact id list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code pointOfContactIds} is null.
         */
        public Optional<List<Long>> getPointOfContactIds() {
            return (this.pointOfContactIds != null)
                    ? Optional.of(Collections.unmodifiableList(pointOfContactIds))
                    : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            } else if (!(other instanceof EditAppointmentDescriptor)) {
                return false;
            } else {

                EditAppointmentDescriptor e = (EditAppointmentDescriptor) other;

                return getAppointmentName().equals(e.getAppointmentName())
                        && getStartDate().equals(e.getStartDate())
                        && getEndDate().equals(e.getEndDate())
                        && getStartTime().equals(e.getStartTime())
                        && getEndTime().equals(e.getEndTime())
                        && getLocation().equals(e.getLocation())
                        && getCelebrityIndices().equals(e.getCelebrityIndices())
                        && getPointOfContactIndices().equals(e.getPointOfContactIndices())
                        && getCelebIds().equals(e.getCelebIds())
                        && getPointOfContactIndices().equals(e.getPointOfContactIndices());
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof EditAppointmentCommand)) {
            return false;
        } else {
            EditAppointmentCommand e = (EditAppointmentCommand) other;
            return appointmentIndex.equals(e.appointmentIndex)
                    && editAppointmentDescriptor.equals(e.editAppointmentDescriptor)
                    && Objects.equals(appointmentToEdit, e.appointmentToEdit);
        }
    }

}
```
###### \java\seedu\address\logic\commands\calendar\ListAppointmentCommand.java
``` java
/**
 * Lists all appointments within two dates in a calendar.
 */
public class ListAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "listAppointment";
    public static final String COMMAND_ALIAS = "la";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM[-yyyy]");

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists appointments of all celebrities within the specified date range."
            + "Parameter: [START_DATE END_DATE] (includes the space in between."
            + " lists all appointments when no range specified.)"
            + "START_DATE and END_DATE should be in DD-MM-YYYY or DD-MM format, including the dash."
            + "When latter is entered, YYYY will take the current year.\n"
            + "Example: " + COMMAND_WORD + " 23-04 01-05";

    public static final String MESSAGE_INVALID_DATE_RANGE = "Start date cannot be after end date";
    public static final String MESSAGE_SUCCESS = "Listed appointments from %s to %s successfully.";

    public static final String MESSAGE_NO_APPTS_ERROR = "No appointments to list";

    private LocalDate startDate;
    private LocalDate endDate;

    public ListAppointmentCommand() {}

    public ListAppointmentCommand(LocalDate startDateInput, LocalDate endDateInput) {
        requireNonNull(startDateInput);
        requireNonNull(endDateInput);

        startDate = startDateInput;
        endDate = endDateInput;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!model.getStorageCalendar().hasAtLeastOneAppointment()) {
            throw new CommandException(MESSAGE_NO_APPTS_ERROR);
        }

        if (startDate == null && endDate == null) {
            startDate = model.getStorageCalendar().getEarliestDate();
            endDate = model.getStorageCalendar().getLatestDate();
        }

        model.setIsListingAppointments(true);
        List<Appointment> newAppointmentList =
                model.getStorageCalendar().getAppointmentsWithinDate(startDate, endDate);
        model.setCurrentlyDisplayedAppointments(newAppointmentList);
        EventsCenter.getInstance().post(new ShowAppointmentListEvent(newAppointmentList));

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, startDate.format(FORMATTER), endDate.format(FORMATTER)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListAppointmentCommand // instanceof handles nulls
                && Objects.equals(startDate, ((ListAppointmentCommand) other).startDate)
                && Objects.equals(endDate, ((ListAppointmentCommand) other).endDate));
    }
}
```
###### \java\seedu\address\logic\parser\calendar\AddAppointmentCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution
     * @throws ParseException if the user input does not comform to the expected format
     */
    @Override
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START_TIME,
                PREFIX_START_DATE,  PREFIX_LOCATION, PREFIX_END_TIME, PREFIX_END_DATE, PREFIX_CELEBRITY,
                PREFIX_POINT_OF_CONTACT);

        if (!arePrefixesPresent(argMultiMap, PREFIX_NAME)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            String appointmentName = ParserUtil.parseGeneralName(argMultiMap.getValue(PREFIX_NAME)).get();
            Optional<LocalTime> startTimeInput = ParserUtil.parseTime(argMultiMap.getValue(PREFIX_START_TIME));
            Optional<LocalDate> startDateInput = ParserUtil.parseDate(argMultiMap.getValue(PREFIX_START_DATE));
            Optional<LocalTime> endTimeInput = ParserUtil.parseTime(argMultiMap.getValue(PREFIX_END_TIME));
            Optional<LocalDate> endDateInput = ParserUtil.parseDate(argMultiMap.getValue(PREFIX_END_DATE));
            Optional<MapAddress> locationInput = ParserUtil.parseMapAddress(argMultiMap.getValue(PREFIX_LOCATION));
            Set<Index> celebrityIndices = ParserUtil.parseIndices(argMultiMap.getAllValues(PREFIX_CELEBRITY));
            Set<Index> pointOfContactIndices = ParserUtil
                    .parseIndices(argMultiMap.getAllValues(PREFIX_POINT_OF_CONTACT));

            MapAddress location = null;
            LocalTime startTime = LocalTime.now();
            LocalDate startDate = LocalDate.now();
            LocalTime endTime = LocalTime.now().plusMinutes(15);
            LocalDate endDate = LocalDate.now();

            if (startTimeInput.isPresent()) {
                startTime = startTimeInput.get();
                endTime = startTimeInput.get().plusMinutes(15);
            }
            if (endTimeInput.isPresent()) {
                endTime = endTimeInput.get();
            }
            if (startDateInput.isPresent()) {
                startDate = startDateInput.get();
                endDate = startDateInput.get();
            }
            if (endDateInput.isPresent()) {
                endDate = endDateInput.get();
            }
            if (locationInput.isPresent()) {
                location = locationInput.get();
            }

            // Checking if date and time take in correct values
            if (isDateTimeNotValid(startDate, endDate, startTime, endTime)) {
                throw new ParseException(MESSAGE_START_DATE_TIME_NOT_BEFORE_END_DATE_TIME);
            }

            Appointment appt = new Appointment(appointmentName, startTime, startDate, location, endTime, endDate);
            return new AddAppointmentCommand(appt, celebrityIndices, pointOfContactIndices);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\address\logic\parser\calendar\EditAppointmentCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditAppointmentCommand object
 */
public class EditAppointmentCommandParser implements Parser<EditAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditAppointmentCommand
     * and returns an EditAppointmentCommand object for execution
     * @throws ParseException if the user input does not comform to the expected format
     */
    @Override
    public EditAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START_TIME,
                PREFIX_START_DATE,  PREFIX_LOCATION, PREFIX_END_TIME, PREFIX_END_DATE, PREFIX_CELEBRITY,
                PREFIX_POINT_OF_CONTACT);

        Index appointmentIndex;

        try {
            appointmentIndex = ParserUtil.parseIndex(argMultiMap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAppointmentCommand.MESSAGE_USAGE));
        }

        EditAppointmentDescriptor editApptDescriptor = new EditAppointmentDescriptor();

        try {
            ParserUtil.parseGeneralName(argMultiMap.getValue(PREFIX_NAME))
                    .ifPresent(editApptDescriptor::setAppointmentName);
            ParserUtil.parseTime(argMultiMap.getValue(PREFIX_START_TIME)).ifPresent(editApptDescriptor::setStartTime);
            ParserUtil.parseDate(argMultiMap.getValue(PREFIX_START_DATE)).ifPresent(editApptDescriptor::setStartDate);
            ParserUtil.parseTime(argMultiMap.getValue(PREFIX_END_TIME)).ifPresent(editApptDescriptor::setEndTime);
            ParserUtil.parseDate(argMultiMap.getValue(PREFIX_END_DATE)).ifPresent(editApptDescriptor::setEndDate);
            ParserUtil.parseMapAddress(argMultiMap.getValue(PREFIX_LOCATION))
                    .ifPresent(editApptDescriptor::setLocation);
            parseIndicesForEditAppointment(argMultiMap.getAllValues(PREFIX_CELEBRITY))
                    .ifPresent(editApptDescriptor::setCelebrityIndices);
            parseIndicesForEditAppointment(argMultiMap.getAllValues(PREFIX_POINT_OF_CONTACT))
                    .ifPresent(editApptDescriptor::setPointOfContactIndices);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editApptDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAppointmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAppointmentCommand(appointmentIndex, editApptDescriptor);
    }

    /**
     * Parses {@code Collection<String> indices} into a {@code Set<Index>}
     * if {@code indices} is non-empty. If {@code indices} contain only one element
     * which is an empty string, it will be parsed into a {@code Set<Index>} containing zero indices.
     */
    private Optional<Set<Index>> parseIndicesForEditAppointment(Collection<String> indices)
        throws IllegalValueException {
        assert indices != null;

        if (indices.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> indexSet = (indices.size() == 1 && indices.contains(""))
                ? Collections.emptySet() : indices;
        return Optional.of(ParserUtil.parseIndices(indexSet));
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java

    /**
     * Parses a {@code Optional<LocalTime> time} into an {@code Optional<LocalTime>} if {@code time} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<LocalTime> parseTime(Optional<String> time) throws IllegalValueException {
        requireNonNull(time);
        return time.isPresent() ? Optional.of(parseTime(time.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            LocalDate ld = LocalDate.parse(date, Appointment.DATE_FORMAT);
            return ld;
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Appointment.MESSAGE_DATE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code Optional<LocalDate> date} into an {@code Optional<LocalDate>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<LocalDate> parseDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(parseDate(date.get())) : Optional.empty();
    }

    /**
     * Validates if a {@code String name} into a valid name.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static String parseGeneralName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Appointment.isValidName(trimmedName)) {
            throw new IllegalValueException(Appointment.MESSAGE_NAME_CONSTRAINTS);
        }
        return trimmedName;
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<String>} if {@code name} is present and valid.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseGeneralName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseGeneralName(name.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> indices} into a {@code Set<Index>}.
     */
    public static Set<Index> parseIndices(Collection<String> indices) throws IllegalValueException {
        requireNonNull(indices);
        final Set<Index> indexSet = new HashSet<>();
        for (String index : indices) {
            indexSet.add(parseIndex(index));
        }
        return indexSet;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\model\appointment\Appointment.java
``` java
/**
 * Wraps all data required for an appointment, inheriting from a class of our calendar library
 * Each appointment also creates child entries for every celebrity associated with the appointment
 * and then attaches the entries to their respective calendars while keeping a reference to them
 * in an ArrayList of Entries. Appointments are stored in our StorageCalendar.
 */
public class Appointment extends Entry {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Appointment names should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Time should be a 2 digit number between 00 to 23 followed by a :"
            + " followed by a 2 digit number beetween 00 to 59. Some examples include "
            + "08:45, 13:45, 00:30";
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should be a 2 digit number between 01 to 31 followed by a -"
            + " followed by a 2 digit number between 01 to 12 followed by a -"
            + " followed by a 4 digit number describing a year. Some months might have less than 31 days."
            + " Some examples include: 13-12-2018, 02-05-2019, 28-02-2018";

    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT); // prevent incorrect dates

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    private static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";


    // Minimum duration for an appointment is at least 1 minute
    private static final Duration minDuration = Duration.ofMinutes(15);

    private final List<Entry> childEntryList;
    private final List<Celebrity> celebrityList;
    private final List<Long> celebrityIds;
    private final List<Person> pointOfContactList;
    private final List<Long> pointOfContactIds;
    private MapAddress mapAddress;

    public Appointment(String title, LocalTime startTime, LocalDate startDate,
                       MapAddress mapAddress, LocalTime endTime, LocalDate endDate) {
        super(requireNonNull(title));
        requireNonNull(startTime);
        requireNonNull(startDate);
        requireNonNull(endTime);
        requireNonNull(endDate);

        this.setMinimumDuration(minDuration);
        this.changeStartDate(startDate);
        this.changeEndDate(endDate);
        this.changeStartTime(startTime);
        this.changeEndTime(endTime);


        this.mapAddress = mapAddress;
        if (mapAddress == null) {
            this.setLocation(null);
        } else {
            this.setLocation(mapAddress.toString());
        }
        this.childEntryList = new ArrayList<>();
        this.celebrityList = new ArrayList<>();
        this.celebrityIds = new ArrayList<>();
        this.pointOfContactList = new ArrayList<>();
        this.pointOfContactIds = new ArrayList<>();
    }

    public Appointment(Appointment appointment) {
        this.setTitle(appointment.getTitle());
        this.changeStartDate(appointment.getStartDate());
        this.changeEndDate(appointment.getEndDate());
        this.changeStartTime(appointment.getStartTime());
        this.changeEndTime(appointment.getEndTime());
        this.mapAddress = appointment.getMapAddress();

        this.childEntryList = appointment.getChildEntryList();
        this.celebrityList = appointment.getCelebrities();
        this.celebrityIds = appointment.getCelebIds();
        this.pointOfContactList = appointment.getPointOfContactList();
        this.pointOfContactIds = appointment.getPointOfContactIds();
    }

    /**
     * Checks if the start date/time is NOT at least 15 min before end date/time
     */
    public static boolean isDateTimeNotValid(LocalDate startDate, LocalDate endDate, LocalTime startTime,
                                             LocalTime endTime) {
        LocalDateTime sdt = LocalDateTime.of(startDate, startTime);
        LocalDateTime edt = LocalDateTime.of(endDate, endTime);

        return edt.isBefore(sdt.plusMinutes(15));
    }

    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    public void setCelebIds(List<Long> ids) {
        celebrityIds.clear();
        celebrityIds.addAll(ids);
    }

    public List<Long> getCelebIds() {
        return celebrityIds;
    }

    public List<Person> getPointOfContactList() {
        return pointOfContactList;
    }

    public List<Long> getPointOfContactIds() {
        return pointOfContactIds;
    }

    public void setPointOfContactIds(List<Long> ids) {
        pointOfContactIds.clear();
        pointOfContactIds.addAll(ids);
    }

    @Override
    public boolean equals (Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppt = (Appointment) other;
        return Objects.equals(otherAppt.getTitle(), this.getTitle())
                && Objects.equals(otherAppt.getMapAddress(), this.getMapAddress())
                && (otherAppt.getStartTime().getHour() == this.getStartTime().getHour())
                && (otherAppt.getStartTime().getMinute() == this.getStartTime().getMinute())
                && Objects.equals(otherAppt.getStartDate(), this.getStartDate())
                && (otherAppt.getEndTime().getHour() == this.getEndTime().getHour())
                && (otherAppt.getEndTime().getMinute() == this.getEndTime().getMinute())
                && Objects.equals(otherAppt.getEndDate(), this.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getMapAddress(), getStartTime().getHour(), getEndTime().getHour(),
                getStartDate(), getEndDate(), getStartTime().getMinute(), getEndTime().getMinute());
    }

    /**
     * Resets the stores celebrities and points of contacts, along with their associated
     * information stored in the Appointment object
     */
    public void updateEntries(List<Celebrity> celebrities, List<Person> pointsOfContact) {

        updateCelebEntries(celebrities);
        updatePointsOfContacts(pointsOfContact);
    }

    /**
     * Removes old child entries and creates a new child entry for every celebrity
     * and then stores it in childEntryList. Also stores the id of each celeb and the
     * celebrities themselves
     */
    private void updateCelebEntries(List<Celebrity> celebrities) {
        clearChildEntries();
        celebrityList.clear();
        childEntryList.clear();
        celebrityIds.clear();
        for (Celebrity celebrity : celebrities) {
            childEntryList.add(createChildEntry(celebrity));
            celebrityIds.add(celebrity.getId());
        }
        celebrityList.addAll(celebrities);
    }

    /**
     * Update points of contact list stored and their ids.
     */
    private void updatePointsOfContacts(List<Person> pointsOfContact) {
        pointOfContactList.clear();
        pointOfContactIds.clear();
        for (Person p : pointsOfContact) {
            pointOfContactIds.add(p.getId());
        }
        pointOfContactList.addAll(pointsOfContact);
    }

    /**
     * Returns the current list of Celebrities attending this appointment
     */
    public List<Celebrity> getCelebrities() {
        return celebrityList;
    }

    /**
     * Sets old child entries to the new entries.
     */
    public void setChildEntries(List<Entry> newChildEntryList) {
        childEntryList.clear();
        childEntryList.addAll(newChildEntryList);
    }

    public List<Entry> getChildEntryList() {
        return childEntryList;
    }

    /**
     * Removes all child entries and then removes the appointment itself from
     * the StorageCalendar.
     */
    public void removeAppointment() {
        clearChildEntries();
        this.setCalendar(null);
    }

    /**
     * Creates new childEntry for a given celebrity and sets the entry to point
     * to the celebrity's calendar.
     */
    private Entry createChildEntry(Celebrity celebrity) {
        Entry childEntry = new Entry(this.getTitle());

        childEntry.setMinimumDuration(minDuration);
        childEntry.changeStartTime(this.getStartTime());
        childEntry.changeEndTime(this.getEndTime());
        childEntry.changeStartDate(this.getStartDate());
        childEntry.changeEndDate(this.getEndDate());
        childEntry.setLocation(this.getLocation());
        childEntry.setCalendar(celebrity.getCelebCalendar());

        return childEntry;
    }

    /**
     * Remove all existing child entries
     */
    private void clearChildEntries() {
        for (Entry e : childEntryList) {
            // removed entries from the calendars
            e.setCalendar(null);
        }
    }

    /**
     * Returns the MapAddress of the Appointment.
     */
    public MapAddress getMapAddress() {
        return mapAddress;
    }

```
###### \java\seedu\address\model\calendar\CelebCalendar.java
``` java
/**
 * Stores the entries for a single celebrity
 */
public class CelebCalendar extends Calendar {

    public CelebCalendar(String name) {
        super(name);
    }

}
```
###### \java\seedu\address\model\calendar\StorageCalendar.java
``` java
/**
 * Stores the list of all the celebrity appointments
 */
public class StorageCalendar extends Calendar {

    private static String storageCalendarName = "Storage Calendar";

    public StorageCalendar() {
        super(storageCalendarName);
    }

    public StorageCalendar(StorageCalendar cal) {
        super(storageCalendarName);
        for (Appointment a : cal.getAllAppointments()) {
            this.addEntry(new Appointment(a));
        }
    }

    public boolean hasAtLeastOneAppointment() {
        return this.getEarliestTimeUsed() != null;
    }

    public LocalDate getEarliestDate() {
        return LocalDateTime.ofInstant(this.getEarliestTimeUsed(), ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDate getLatestDate() {
        return LocalDateTime.ofInstant(this.getLatestTimeUsed(), ZoneId.systemDefault()).toLocalDate();
    }

    public List<Appointment> getAllAppointments() {
        LocalDate startDate;
        LocalDate endDate;
        // handle the case when no entries in calendar
        try {
            startDate = getEarliestDate();
            endDate = getLatestDate();
        } catch (NullPointerException e) {
            return new ArrayList<>();
        }
        return getAppointmentsWithinDate(startDate, endDate);
    }

    public List<Appointment> getAppointmentsWithinDate(LocalDate startDate, LocalDate endDate) {
        List<Appointment> appointmentsWithinDate = new ArrayList<>();
        Map<LocalDate, List<Entry<?>>> dateListMap = this.findEntries(startDate, endDate, ZoneId.systemDefault());
        SortedSet<LocalDate> sortedKeySet = new TreeSet<>(dateListMap.keySet());

        Set<Appointment> storedAppointments = new HashSet<>();
        for (LocalDate date : sortedKeySet) {
            for (Entry e : dateListMap.get(date)) {
                Appointment currentAppt = (Appointment) e;
                // because same entry might show up on different dates
                if (!storedAppointments.contains(currentAppt)) {
                    appointmentsWithinDate.add(currentAppt);
                    storedAppointments.add(currentAppt);
                }
            }
        }

        return appointmentsWithinDate;

    }

    /**
     * Checks if appointment already exists in the model, and if it doesnt adds it to the calendar
     */
    public void addAppointment(Appointment appt) throws DuplicateAppointmentException {
        if (getAllAppointments().contains(appt)) {
            throw new DuplicateAppointmentException();
        }
        this.addEntry(appt);
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    public static final String DAY_VIEW_PAGE = "day";
    public static final String WEEK_VIEW_PAGE = "week";
    public static final String MONTH_VIEW_PAGE = "month";

    public static final Tag CELEBRITY_TAG = new Tag("celebrity");

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private static final String CELEB_CALENDAR_SOURCE_NAME  = "Celeb Calendar Source";

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private final CalendarSource celebCalendarSource;
    private final StorageCalendar storageCalendar;
    private List<Appointment> appointments;

    // attributes related to calendarPanel status
    private String currentCelebCalendarViewPage;
    private Celebrity currentCelebCalendarOwner;
    private List<Appointment> currentlyDisplayedAppointments;
    private boolean isListingAppointments;
    private LocalDate baseDate;

    public ModelManager() {
        this(new AddressBook(), new StorageCalendar(), new UserPrefs());
    }

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, StorageCalendar storageCalendar, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());

        celebCalendarSource = new CalendarSource(CELEB_CALENDAR_SOURCE_NAME);
        resetCelebCalendars();

        this.storageCalendar = new StorageCalendar(storageCalendar);
        appointments = getStoredAppointmentList();
        associateAppointmentsWithCelebritiesAndPointsOfContact();

        currentCelebCalendarViewPage = DAY_VIEW_PAGE;
        currentCelebCalendarOwner = null;
        currentlyDisplayedAppointments = new ArrayList<>();
        isListingAppointments = false;
        baseDate = LocalDate.now();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException {
        if (person.isCelebrity()) {
            addCelebrity(person);
        } else {
            addressBook.addPerson(person);
        }
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void addCelebrity(Person person) throws DuplicatePersonException {
        Celebrity celebrity = addressBook.addCelebrity(person);
        celebCalendarSource.getCalendars().add(celebrity.getCelebCalendar());
    }

    @Override
    public ArrayList<Celebrity> getCelebrities() {
        return addressBook.getCelebritiesList();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void associateAppointmentsWithCelebritiesAndPointsOfContact() {
        List<Celebrity> celebrityList;
        List<Person> pointOfContactList;
        appointments = getStoredAppointmentList();
        for (Appointment a : appointments) {
            celebrityList = getCelebritiesFromId(a.getCelebIds());
            pointOfContactList = getPointsOfContactFromId(a.getPointOfContactIds());
            a.updateEntries(celebrityList, pointOfContactList);
        }
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ObservableList<Calendar> getCelebCalendars() {
        return celebCalendarSource.getCalendars();
    }

    @Override
    public CalendarSource getCelebCalendarSource() {
        return celebCalendarSource;
    }

    @Override
    public StorageCalendar getStorageCalendar() {
        return storageCalendar;
    }

    @Override
    public List<Appointment> getAppointmentList() {
        return this.appointments;
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public Appointment getChosenAppointment(int chosenIndex) throws IndexOutOfBoundsException {
        if (chosenIndex < 0 || chosenIndex >= currentlyDisplayedAppointments.size()) {
            throw new IndexOutOfBoundsException();
        }
        return currentlyDisplayedAppointments.get(chosenIndex);
    }

    @Override
    public void addAppointmentToStorageCalendar(Appointment appt) throws DuplicateAppointmentException {
        storageCalendar.addAppointment(appt);
        appointments.add(appt);
        indicateAppointmentListChanged();
    }

    @Override
    public Appointment deleteAppointment(int index) throws IndexOutOfBoundsException {
        Appointment apptToDelete = getChosenAppointment(index);
        apptToDelete.removeAppointment();
        removeAppointmentFromInternalList(index);
        currentlyDisplayedAppointments.remove(apptToDelete);
        indicateAppointmentListChanged();
        return apptToDelete;
    }

    /** Makes changes to model's internal appointment list */
    private void removeAppointmentFromInternalList(int index) {
        getAppointmentList().remove(index);
    }


    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public List<Celebrity> getCelebritiesChosen(Set<Index> indices) throws CommandException {
        List<Celebrity> celebrities = new ArrayList<>();
        for (Index index : indices) {
            celebrities.add(getCelebrityChosen(index));
        }
        return celebrities;
    }

    @Override
    public Celebrity getCelebrityChosen(Index index) throws CommandException {
        int zeroBasedIndex = index.getZeroBased();
        if (zeroBasedIndex >= filteredPersons.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else if (!filteredPersons.get(zeroBasedIndex).isCelebrity()) {
            throw new CommandException(Messages.MESSAGE_NOT_CELEBRITY_INDEX);
        } else {
            return (Celebrity) filteredPersons.get(zeroBasedIndex);
        }
    }

    @Override
    public List<Person> getPointsOfContactChosen(Set<Index> indices) throws CommandException {
        List<Person> pointsOfContact = new ArrayList<>();
        for (Index index : indices) {
            pointsOfContact.add(getPointOfContactChosen(index));
        }
        return pointsOfContact;
    }

    @Override
    public Person getPointOfContactChosen(Index index) throws CommandException {
        int zeroBasedIndex = index.getZeroBased();
        if (zeroBasedIndex >= filteredPersons.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else if (filteredPersons.get(zeroBasedIndex).isCelebrity()) {
            throw new CommandException(Messages.MESSAGE_NOT_POINT_OF_CONTACT_INDEX);
        } else {
            return filteredPersons.get(zeroBasedIndex);
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons)
                && isListingAppointments == other.isListingAppointments
                && getStoredAppointmentSet().equals(other.getStoredAppointmentSet());
    }
    //=========== Private inner methods =============================================================

    /**
     * Returns all stored appointments in a set
     */
    private Set<Appointment> getStoredAppointmentSet() {
        return new HashSet<>(getStoredAppointmentList());
    }

    /**
     * Populates our CelebCalendar CalendarSource by creating a calendar for every celebrity in our addressbook
     */
    private void resetCelebCalendars() {
        // reset calendars in celebCalendarSource to the restored calendars
        List<Celebrity> celebrities = addressBook.getCelebritiesList();
        List<Calendar> calendars = new ArrayList<>();

        for (Celebrity celebrity: celebrities) {
            calendars.add(celebrity.getCelebCalendar());
        }
        celebCalendarSource.getCalendars().clear();
        celebCalendarSource.getCalendars().addAll(calendars);
    }

    private List<Person> getPointsOfContactFromId(List<Long> pointOfContactIds) {
        List<Person> pointsOfContact = new ArrayList<>();
        for (long pointOfContactId : pointOfContactIds) {
            for (Person p : addressBook.getPersonList()) {
                if (!p.isCelebrity() && (p.getId() == pointOfContactId)) {
                    pointsOfContact.add(p);
                    break;
                }
            }
        }
        return pointsOfContact;
    }

    /**
     * Gets the celebrities based on their ids from our person list
     */
    private List<Celebrity> getCelebritiesFromId(List<Long> celebrityIds) {
        List<Celebrity> celebrities = new ArrayList<>();
        for (long celebId : celebrityIds) {
            for (Person p : addressBook.getPersonList()) {
                if (p.isCelebrity() && (p.getId() == celebId)) {
                    celebrities.add((Celebrity) p);
                    break;
                }
            }
        }
        return celebrities;
    }

    /**
     * Raises an event to indicate the addressbook has changed
     * and reassoicates appointments with relevant celebrities and points of contact
     **/
    private void indicateAddressBookChanged() {
        resetCelebCalendars();
        associateAppointmentsWithCelebritiesAndPointsOfContact();
        raise(new AddressBookChangedEvent(addressBook));
    }

}
```
###### \java\seedu\address\model\person\Celebrity.java
``` java
/**
 *  Child class of Person for those who are tagged celebrities
 */
public class Celebrity extends Person {
    // for creation of different style for each celebrity calendar to differentiate
    private static final Random random = new Random();

    private CelebCalendar celebCalendar;

    /**
     * Every field must be present and not null.
     */
    public Celebrity(Person celeb) {
        super(celeb.getName(), celeb.getPhone(), celeb.getEmail(), celeb.getAddress(), celeb.getTags(), celeb.getId());
        celebCalendar = new CelebCalendar(this.getName().fullName);
        celebCalendar.setStyle(Calendar.Style.getStyle(random.nextInt(7)));
    }

    public CelebCalendar getCelebCalendar() {
        return celebCalendar;
    }

    /**
     * Sets the celebCalendar to another one.
     */
    public void setCelebCalendar(CelebCalendar newCelebCalendar) {
        this.celebCalendar = newCelebCalendar;
    }

    /**
     * Returns if input celeb is a copy of this celebrity
     */
    public boolean isCopyOf(Celebrity celeb) {
        return super.equals(celeb);
    }
}
```
###### \java\seedu\address\model\person\exceptions\DuplicateAppointmentException.java
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
###### \java\seedu\address\ui\AppointmentCard.java
``` java
/**
 * Individual card for each appointment in our list view
 */
public class AppointmentCard extends UiPart<Region> {
    private static final String FXML = "AppointmentListCard.fxml";

    public final Appointment appt;

    @FXML
    private HBox appointmentCardPane;

    @FXML
    private Label name;

    @FXML
    private Label startTime;

    @FXML
    private Label startDate;

    @FXML
    private Label endTime;

    @FXML
    private Label endDate;

    @FXML
    private Label appointmentLocation;

    @FXML
    private Label celebrities;

    @FXML
    private Label pointsOfContact;

    @FXML
    private Label id;

    public AppointmentCard(Appointment appt, int displayedIndex) {
        super(FXML);
        this.appt = appt;
        id.setText(displayedIndex + ". ");
        name.setText(appt.getTitle());
        startTime.setText("Start time: " + appt.getStartTime().format(Appointment.TIME_FORMAT));
        startDate.setText("Start date: " + appt.getStartDate().format(Appointment.DATE_FORMAT));
        endTime.setText("End time: " + appt.getEndTime().format(Appointment.TIME_FORMAT));
        endDate.setText("End date: " + appt.getEndDate().format(Appointment.DATE_FORMAT));
        appointmentLocation.setText(getLocation(appt));
        celebrities.setText(getCelebrities(appt));
        pointsOfContact.setText(getPointsOfContact(appt));
    }

    private static String getLocation(Appointment appt) {
        if (appt.getLocation() == null) {
            return "No Location Data";
        } else {
            return "Location: " + appt.getLocation();
        }
    }

    private static String getCelebrities(Appointment appt) {
        List<Entry> childEntries = appt.getChildEntryList();
        if (childEntries.size() == 0) {
            return "No celebrities attending this appointment";
        } else {
            StringBuilder sb = new StringBuilder("Celebrities attending: ");
            for (Entry e : appt.getChildEntryList()) {
                sb.append(e.getCalendar().getName());
                sb.append(", ");
            }
            return sb.substring(0, sb.length() - 2);
        }

    }

    private static String getPointsOfContact(Appointment appt) {
        List<Person> pointsOfContact = appt.getPointOfContactList();
        if (pointsOfContact.size() == 0) {
            return "No points of contact for this appointment.";
        } else {
            StringBuilder sb = new StringBuilder("Points of contact: ");
            for (Person p :pointsOfContact) {
                sb.append(p.getName().fullName);
                sb.append(", ");
            }
            return sb.substring(0, sb.length() - 2);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof AppointmentCard)) {
            return false;
        } else {
            AppointmentCard card = (AppointmentCard) other;
            return id.getText().equals(card.id.getText())
                    && appt.equals(card.appt);

        }
    }
}
```
###### \java\seedu\address\ui\AppointmentListWindow.java
``` java
/**
 * Window to display the list of appointments using appointment cards
 */
public class AppointmentListWindow extends UiPart<Region> {

    private static final String FXML = "AppointmentListWindow.fxml";


    @FXML
    private ListView<AppointmentCard> appointmentListView;

    public AppointmentListWindow(Stage root, List<Appointment> appointments) {
        super(FXML);
        ObservableList<Appointment> observableAppts = FXCollections.observableArrayList(appointments);
        setConnections(observableAppts);
    }

    public AppointmentListWindow(List<Appointment> appointments) {
        this(new Stage(), appointments);
    }

    private void setConnections(ObservableList<Appointment> appointmentList) {
        ObservableList<AppointmentCard> mappedList = EasyBind.map(
                appointmentList, (appointment) -> new AppointmentCard(appointment,
                        appointmentList.indexOf(appointment) + 1));
        appointmentListView.setItems(mappedList);
        appointmentListView.setCellFactory(listView -> new AppointmentListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code AppointmentCard}.
     */
    class AppointmentListViewCell extends ListCell<AppointmentCard> {

        @Override
        protected void updateItem(AppointmentCard appointment, boolean empty) {
            super.updateItem(appointment, empty);

            if (empty || appointment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(appointment.getRoot());
            }
        }
    }

}
```
###### \java\seedu\address\ui\CalendarPanel.java
``` java
/**
 * The panel for the Calendar. Constructs a calendar view and attaches to it a CalendarSource.
 * The view is then returned by calling getCalendarView in MainWindow to attach it to the
 * calendarPlaceholder.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private CalendarView celebCalendarView;

    private final CalendarSource celebCalendarSource;

    public CalendarPanel(CalendarSource celebCalendarSource) {
        super(FXML);
        this.celebCalendarView = new CalendarView();
        this.celebCalendarSource = celebCalendarSource;

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        registerAsAnEventHandler(this);

        // To set up the calendar view.
        setUpCelebCalendarView();
    }

    private void setUpCelebCalendarView() {
        celebCalendarView.getCalendarSources().clear(); // there is an existing default source when creating the view
        celebCalendarView.getCalendarSources().add(celebCalendarSource);
        celebCalendarView.setRequestedTime(LocalTime.now());
        celebCalendarView.getDayPage().setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
        celebCalendarView.showDayPage();

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        celebCalendarView.setToday(LocalDate.now());
                        celebCalendarView.setTime(LocalTime.now());
                    });
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        };
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
        celebCalendarView.setLayout(DateControl.Layout.SWIMLANE);
        hideButtons();
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java

    /**
     * Creates appointmentListWindow and replaces calendarPanel with it
     */
    private void handleAppointmentList(List<Appointment> appointments) {
        AppointmentListWindow apptListWindow = new AppointmentListWindow(appointments);
        calendarPlaceholder.getChildren().clear();
        calendarPlaceholder.getChildren().add(apptListWindow.getRoot());
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    /**
     * Replaces appointmentListWindow with calendarPanel if necessary,
     * and then handles the changing of calendarview command.
     * @param event
     */
    @Subscribe
    private void handleShowCalendarEvent(ShowCalendarEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        calendarPlaceholder.getChildren().clear();
        calendarPlaceholder.getChildren().add(calendarPanel.getCalendarView());
    }

    @Subscribe
    private void handleShowAppointmentListEvent(ShowAppointmentListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleAppointmentList(event.getAppointments());
    }
}
```
###### \resources\view\AppointmentListCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.VBox?>
<HBox id="appointmentCardPane" fx:id="appointmentCardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets top="5" right="5" bottom="5" left="15" />
            </padding>
            <HBox spacing="5" alignment="CENTER_LEFT">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="name" text="\$first" styleClass="cell_big_label" />
            </HBox>
            <Label fx:id="startTime" styleClass="cell_small_label" text="\$startTime" />
            <Label fx:id="startDate" styleClass="cell_small_label" text="\$startDate" />
            <Label fx:id="endTime" styleClass="cell_small_label" text="\$endTime" />
            <Label fx:id="endDate" styleClass="cell_small_label" text="\$endDate" />
            <Label fx:id="appointmentLocation" styleClass="cell_small_label" text="\$location" />
            <Label fx:id="celebrities" styleClass="cell_small_label" text="\$celebrities" />
            <Label fx:id="pointsOfContact" styleClass="cell_small_label" text="\$pointsOfContact" />
        </VBox>
    </GridPane>
</HBox>
```
###### \resources\view\AppointmentListWindow.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>
<VBox xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
  <ListView fx:id="appointmentListView" prefWidth="248.0" VBox.vgrow="ALWAYS" />
</VBox>
```
###### \resources\view\CalendarPanel.fxml
``` fxml

<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.web.WebView?>
<StackPane xmlns:fx="http://javafx.com/fxml/1">
  <WebView fx:id="calendarViewPane"/>
</StackPane>
```
