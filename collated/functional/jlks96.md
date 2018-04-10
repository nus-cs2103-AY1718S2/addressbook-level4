# jlks96
###### \java\seedu\address\commons\events\logic\ZoomInEvent.java
``` java
/** Indicates the user is trying to zoom in on the calendar*/
public class ZoomInEvent extends BaseEvent {

    public ZoomInEvent() { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\logic\ZoomOutEvent.java
``` java
/** Indicates the user is trying to zoom out on the calendar*/
public class ZoomOutEvent extends BaseEvent {

    public ZoomOutEvent() { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\model\AppointmentDeletedEvent.java
``` java
/**
 * Indicates that an appointment is deleted.
 */
public class AppointmentDeletedEvent extends BaseEvent {
    private final ObservableList<Appointment> updatedAppointments;

    public AppointmentDeletedEvent(ObservableList<Appointment> updatedAppointments) {
        this.updatedAppointments = updatedAppointments;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<Appointment> getUpdatedAppointments() {
        return updatedAppointments;
    }
}
```
###### \java\seedu\address\commons\events\model\NewAppointmentAddedEvent.java
``` java
/**
 * Indicates that a new appointment is added.
 */
public class NewAppointmentAddedEvent extends BaseEvent {

    private final Appointment appointmentAdded;

    public NewAppointmentAddedEvent(Appointment appointmentAdded) {
        this.appointmentAdded = appointmentAdded;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Appointment getAppointmentAdded() {
        return appointmentAdded;
    }
}
```
###### \java\seedu\address\logic\commands\AddAppointmentCommand.java
``` java
/**
 * Adds an appointment to the address book.
 */
public class AddAppointmentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addappointment";
    public static final String COMMAND_ALIAS = "aa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE (must be in the format: dd/MM/yyyy) "
            + PREFIX_STARTTIME + "STARTTIME (must be in the 24 format: HH:mm) "
            + PREFIX_ENDTIME + "ENDTIME (must be in the 24 format: HH:mm) "
            + PREFIX_LOCATION + "LOCATION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DATE + "03/04/2018 "
            + PREFIX_STARTTIME + "10:30 "
            + PREFIX_ENDTIME + "11:30 "
            + PREFIX_LOCATION + "Century Garden";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the address book";

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
                && toAdd.equals(((AddAppointmentCommand) other).toAdd)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\Command.java
``` java
    /**
     * Raises the event via {@link EventsCenter#post(BaseEvent)}
     * @param event
     */
    protected void raise(BaseEvent event) {
        EventsCenter.getInstance().post(event);
    }
```
###### \java\seedu\address\logic\commands\DeleteAppointmentCommand.java
``` java
/**
 * Deletes an appointment that matches all the input fields from the address book.
 */
public class DeleteAppointmentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteappointment";
    public static final String COMMAND_ALIAS = "da";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the appointment that matches all the input fields from the address book.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE (must be in the format: dd/MM/yyyy) "
            + PREFIX_STARTTIME + "STARTTIME (must be in the 24 format: HH:mm) "
            + PREFIX_ENDTIME + "ENDTIME (must be in the 24 format: HH:mm) "
            + PREFIX_LOCATION + "LOCATION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DATE + "03/04/2018 "
            + PREFIX_STARTTIME + "10:30 "
            + PREFIX_ENDTIME + "11:30 "
            + PREFIX_LOCATION + "Century Garden";

    public static final String MESSAGE_DELETE_APPT_SUCCESS = "Deleted Appointment: %1$s";

    private Appointment appointmentToDelete;

    public DeleteAppointmentCommand(Appointment appointmentToDelete) {
        this.appointmentToDelete = appointmentToDelete;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(appointmentToDelete);
        try {
            model.deleteAppointment(appointmentToDelete);
        } catch (AppointmentNotFoundException anfe) {
            throw new CommandException(Messages.MESSAGE_APPT_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_APPT_SUCCESS, appointmentToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAppointmentCommand // instanceof handles nulls
                && this.appointmentToDelete.equals(((DeleteAppointmentCommand) other).appointmentToDelete));
    }
}
```
###### \java\seedu\address\logic\commands\DeleteBeforeCommand.java
``` java
/**
 * Deletes all persons with specified tags added before the date input by user.
 */
public class  DeleteBeforeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletebefore";
    public static final String COMMAND_ALIAS = "db";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all persons added before the input date.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE (must be in the format: dd/MM/yyyy) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "01/01/2010 "
            + PREFIX_TAG + "non-client"
            + PREFIX_TAG + "cash cow";

    public static final String MESSAGE_DELETE_PERSONS_SUCCESS = "Deleted %d persons with tags %s added before %s";

    private final DateAdded inputDate;
    private final Set<Tag> inputTags;
    private final PersonIsAddedBeforeDateInputAndContainsTagsPredicate predicate;

    private int totalPersonsDeleted;

    public DeleteBeforeCommand(DateAdded inputDate, Set<Tag> inputTags) {
        this.inputDate = inputDate;
        this.inputTags = inputTags;
        this.predicate = new PersonIsAddedBeforeDateInputAndContainsTagsPredicate(inputTags, inputDate.toString());
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(predicate);
        model.updateFilteredPersonList(predicate);
        totalPersonsDeleted = model.getFilteredPersonList().size();

        if (totalPersonsDeleted <= 0) {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(Messages.MESSAGE_PERSONS_NOT_FOUND);
        }

        try {
            model.deletePersons(model.getFilteredPersonList());
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Must have a least one person in the list");
        }
        return new CommandResult(
                String.format(MESSAGE_DELETE_PERSONS_SUCCESS, totalPersonsDeleted, inputTags, inputDate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBeforeCommand // instanceof handles nulls
                && this.inputDate.equals(((DeleteBeforeCommand) other).inputDate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ZoomInCommand.java
``` java
/**
 * Zooms in the calendar view to show a more detailed view
 */
public class ZoomInCommand extends Command {
    public static final String COMMAND_WORD = "zoomin";
    public static final String COMMAND_ALIAS = "zi";

    public static final String MESSAGE_SUCCESS = "Calendar zoomed in";

    @Override
    public CommandResult execute() {
        raise(new ZoomInEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\ZoomOutCommand.java
``` java
/**
 * Zooms out the calendar view to show a more general view
 */
public class ZoomOutCommand extends Command {
    public static final String COMMAND_WORD = "zoomout";
    public static final String COMMAND_ALIAS = "zo";

    public static final String MESSAGE_SUCCESS = "Calendar zoomed out";

    @Override
    public CommandResult execute() {
        raise(new ZoomOutEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return model.getFilteredAppointmentList();
    }
```
###### \java\seedu\address\logic\parser\AddAppointmentCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_DATE, PREFIX_STARTTIME, PREFIX_ENDTIME, PREFIX_LOCATION);

        if (!arePrefixesPresent(
                argMultimap, PREFIX_NAME, PREFIX_DATE, PREFIX_STARTTIME, PREFIX_ENDTIME, PREFIX_LOCATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            PersonName name = ParserUtil.parsePersonName(argMultimap.getValue(PREFIX_NAME)).get();
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).get();
            StartTime startTime = ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_STARTTIME).get());
            EndTime endTime = ParserUtil.parseEndTime(argMultimap.getValue(PREFIX_ENDTIME).get());
            Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());

            Appointment appointment = new Appointment(name, date, startTime, endTime, location);

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
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
    /**
     * Creates and returns a {@code DateAdded} with the dateAdded attribute representing the current date
     * @return current date in the following format: dd/MM/yyyy
     */
    public DateAdded createDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        return new DateAdded(dateFormatter.format(calendar.getTime()));
    }
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case DeleteBeforeCommand.COMMAND_WORD:
            return new DeleteBeforeCommandParser().parse(arguments);

        case DeleteBeforeCommand.COMMAND_ALIAS:
            return new DeleteBeforeCommandParser().parse(arguments);

        case AddAppointmentCommand.COMMAND_WORD:
            return new AddAppointmentCommandParser().parse(arguments);

        case AddAppointmentCommand.COMMAND_ALIAS:
            return new AddAppointmentCommandParser().parse(arguments);

        case DeleteAppointmentCommand.COMMAND_WORD:
            return new DeleteAppointmentCommandParser().parse(arguments);

        case DeleteAppointmentCommand.COMMAND_ALIAS:
            return new DeleteAppointmentCommandParser().parse(arguments);

        case ZoomInCommand.COMMAND_WORD:
            return new ZoomInCommand();

        case ZoomInCommand.COMMAND_ALIAS:
            return new ZoomInCommand();

        case ZoomOutCommand.COMMAND_WORD:
            return new ZoomOutCommand();

        case ZoomOutCommand.COMMAND_ALIAS:
            return new ZoomOutCommand();
```
###### \java\seedu\address\logic\parser\DeleteAppointmentCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteAppointmentCommand object
 */
public class DeleteAppointmentCommandParser implements Parser<DeleteAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAppointmentCommand
     * and returns a DeleteAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_DATE, PREFIX_STARTTIME, PREFIX_ENDTIME, PREFIX_LOCATION);

        if (!arePrefixesPresent(
                argMultimap, PREFIX_NAME, PREFIX_DATE, PREFIX_STARTTIME, PREFIX_ENDTIME, PREFIX_LOCATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            PersonName name = ParserUtil.parsePersonName(argMultimap.getValue(PREFIX_NAME)).get();
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).get();
            StartTime startTime = ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_STARTTIME).get());
            EndTime endTime = ParserUtil.parseEndTime(argMultimap.getValue(PREFIX_ENDTIME).get());
            Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());

            Appointment appointment = new Appointment(name, date, startTime, endTime, location);

            return new DeleteAppointmentCommand(appointment);
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
###### \java\seedu\address\logic\parser\DeleteBeforeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteBeforeCommand object
 */
public class DeleteBeforeCommandParser implements Parser<DeleteBeforeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteBeforeCommand
     * and returns an DeleteBeforeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteBeforeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBeforeCommand.MESSAGE_USAGE));
        }

        try {
            DateAdded inputDate = ParserUtil.parseDateAdded(argMultimap.getValue(PREFIX_DATE)).get();
            Set<Tag> inputTags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            dateFormatter.parse(inputDate.toString()); //check if can parse inputDate, requires review
            return new DeleteBeforeCommand(inputDate, inputTags);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        } catch (java.text.ParseException e) { //cannot parse dateAdded into a Date object
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBeforeCommand.MESSAGE_USAGE));
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
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String date} into a {@code DateAdded}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static DateAdded parseDateAdded(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!DateAdded.isValidDate(trimmedDate)) {
            throw new IllegalValueException(DateAdded.MESSAGE_DATE_CONSTRAINTS);
        }
        return new DateAdded(trimmedDate);
    }

    /**
     * Parses a {@code Optional<String> date} into a {@code Optional<DateAdded>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DateAdded> parseDateAdded(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(parseDateAdded(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code Optional<String> date} into a {@code Optional<Date>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Date> parseDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(parseDate(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String name} into a {@code PersonName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static PersonName parsePersonName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!PersonName.isValidName(trimmedName)) {
            throw new IllegalValueException(PersonName.MESSAGE_NAME_CONSTRAINTS);
        }
        return new PersonName(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<PersonName>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<PersonName> parsePersonName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parsePersonName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String time} into a {@code StartTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code time} is invalid.
     */
    public static StartTime parseStartTime(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!StartTime.isValidTime(trimmedTime)) {
            throw new IllegalValueException(StartTime.MESSAGE_TIME_CONSTRAINTS);
        }
        return new StartTime(trimmedTime);
    }

    /**
     * Parses a {@code Optional<String> time} into a {@code Optional<StartTime>} if {@code time} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<StartTime> parseStartTime(Optional<String> time) throws IllegalValueException {
        requireNonNull(time);
        return time.isPresent() ? Optional.of(parseStartTime(time.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String time} into a {@code EndTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code time} is invalid.
     */
    public static EndTime parseEndTime(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!EndTime.isValidTime(trimmedTime)) {
            throw new IllegalValueException(EndTime.MESSAGE_TIME_CONSTRAINTS);
        }
        return new EndTime(trimmedTime);
    }

    /**
     * Parses a {@code Optional<String> time} into a {@code Optional<EndTime>} if {@code time} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EndTime> parseEndTime(Optional<String> time) throws IllegalValueException {
        requireNonNull(time);
        return time.isPresent() ? Optional.of(parseEndTime(time.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String location} into a {@code Location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code location} is invalid.
     */
    public static Location parseLocation(String location) throws IllegalValueException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Location.isValidLocation(trimmedLocation)) {
            throw new IllegalValueException(Location.MESSAGE_LOCATION_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }

    /**
     * Parses a {@code Optional<String> location} into a {@code Optional<Location>} if {@code location} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Location> parseLocation(Optional<String> location) throws IllegalValueException {
        requireNonNull(location);
        return location.isPresent() ? Optional.of(parseLocation(location.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setAppointments(List<Appointment> appointments) throws DuplicateAppointmentException {
        this.appointments.setAppointments(appointments);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes {@code keys} from this {@code AddressBook}.
     * @throws PersonNotFoundException if any of the {@code keys} are not in this {@code AddressBook}.
     */
    public void removePersons(List<Person> keys) throws PersonNotFoundException {
        persons.removeAll(keys);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    //// appointment-level operations

    /**
     * Adds an appointment to the address book.
     *
     * @throws DuplicateAppointmentException if an equivalent appointment already exists.
     */
    public void addAppointment(Appointment appointment) throws DuplicateAppointmentException {
        appointments.add(appointment);
    }

    /**
     * Removes {@code appointment} from this {@code AddressBook}.
     * @throws AppointmentNotFoundException if the {@code appointment} is not in this {@code AddressBook}.
     */
    public boolean removeAppointment(Appointment appointment) throws AppointmentNotFoundException {
        if (appointments.remove(appointment)) {
            return true;
        } else {
            throw new AppointmentNotFoundException();
        }
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asObservableList();
    }
```
###### \java\seedu\address\model\appointment\Appointment.java
``` java
/**
 * Represents an Appointment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {
    private final PersonName personName;
    private final Date date;
    private final StartTime startTime;
    private final EndTime endTime;
    private final Location location;

    /**
     * Every field must be present and not null.
     */
    public Appointment(PersonName personName, Date date, StartTime startTime, EndTime endTime, Location location) {
        this.personName = personName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public PersonName getName() {
        return personName;
    }

    public Date getDate() {
        return date;
    }

    public StartTime getStartTime() {
        return startTime;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getName().equals(this.getName())
                && otherAppointment.getDate().equals(this.getDate())
                && otherAppointment.getStartTime().equals(this.getStartTime())
                && otherAppointment.getEndTime().equals(this.getEndTime())
                && otherAppointment.getLocation().equals(this.getLocation());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(personName, date, startTime, endTime, location);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
                .append(getDate())
                .append(" Start Time: ")
                .append(getStartTime())
                .append(" End Time: ")
                .append(getEndTime())
                .append(" Location: ")
                .append(getLocation());
        return builder.toString();
    }

    /**
     * Returns a list of Strings which represents all the appointment's attributes
     */
    public List<String> toStringList() {
        final List<String> result = new ArrayList<>();
        result.add(getName().toString());
        result.add(getDate().toString());
        result.add(getStartTime().toString());
        result.add(getEndTime().toString());
        result.add(getLocation().toString());
        return result;

    }
}
```
###### \java\seedu\address\model\appointment\Date.java
``` java
/**
 * Represents an Appointment's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date input should be in the format: dd/MM/yyyy";
    /*
     * The first character of the date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATE_VALIDATION_REGEX = "\\d{2}/\\d{2}/\\d{4}";

    public final String date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date in the format dd/MM/yyyy.
     */
    public static boolean isValidDate(String test) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFormatter.setLenient(false);

        try {
            dateFormatter.parse(test); //attempt to parse date
        } catch (ParseException e) { //if fail return false
            return false;
        }
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
```
###### \java\seedu\address\model\appointment\EndTime.java
``` java
/**
 * Represents an Appointment's ending time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class EndTime extends Time {
    /**
     * Constructs a {@code EndTime}.
     *
     * @param endTime A valid endTime.
     */
    public EndTime(String endTime) {
        super(endTime);
    }
}
```
###### \java\seedu\address\model\appointment\exceptions\AppointmentNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified appointment.
 */
public class AppointmentNotFoundException extends Exception {}
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
###### \java\seedu\address\model\appointment\Location.java
``` java
/**
 * Represents an Appointment's location in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Appointment location can take any values, and it should not be blank";

    /*
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Location}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_LOCATION_CONSTRAINTS);
        this.value = location;
    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && this.value.equals(((Location) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\appointment\PersonName.java
``` java
/**
 * Represents a Person's name the user is having appointment with.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class PersonName {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Person names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public PersonName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_NAME_CONSTRAINTS);
        this.fullName = name;
    }

    /**
     * Returns true if a given string is a valid person's name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonName // instanceof handles nulls
                && this.fullName.equals(((PersonName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
```
###### \java\seedu\address\model\appointment\StartTime.java
``` java
/**
 * Represents an Appointment's starting time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class StartTime extends Time {

    /**
     * Constructs a {@code StartTime}.
     *
     * @param startTime A valid startTime.
     */
    public StartTime(String startTime) {
        super(startTime);
    }
}
```
###### \java\seedu\address\model\appointment\Time.java
``` java
/**
 * Represents a time.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Time input should be in the format: HH:mm (24 hour format)";
    /*
     * The first character of the time must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TIME_VALIDATION_REGEX = "\\d{2}:\\d{2}";

    public final String time;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        this.time = time;
    }

    /**
     * Returns true if a given string is a valid time in the 24 hour format HH:mm.
     */
    public static boolean isValidTime(String test) {

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        timeFormatter.setLenient(false);

        try {
            timeFormatter.parse(test); //attempt to parse time
        } catch (ParseException e) { //if fail return false
            return false;
        }
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return time;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && this.time.equals(((Time) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
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
 */
public class UniqueAppointmentList implements Iterable<Appointment> {

    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent appointment as the given argument.
     */
    public boolean contains(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a appointment to the list.
     *
     * @throws DuplicateAppointmentException if the appointment to add is a duplicate of an existing appointment
     * in the list.
     */
    public void add(Appointment toAdd) throws DuplicateAppointmentException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAppointmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent appointment from the list.
     *
     * @throws AppointmentNotFoundException if no such appointment could be found in the list.
     */
    public boolean remove(Appointment toRemove) throws AppointmentNotFoundException {
        requireNonNull(toRemove);
        final boolean appointmentFoundAndDeleted = internalList.remove(toRemove);
        if (!appointmentFoundAndDeleted) {
            throw new AppointmentNotFoundException();
        }
        return appointmentFoundAndDeleted;
    }

    public void setAppointments(UniqueAppointmentList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setAppointments(List<Appointment> appointments) throws DuplicateAppointmentException {
        requireAllNonNull(appointments);
        final UniqueAppointmentList replacement = new UniqueAppointmentList();
        for (final Appointment appointment : appointments) {
            replacement.add(appointment);
        }
        setAppointments(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Appointment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAppointmentList // instanceof handles nulls
                && this.internalList.equals(((UniqueAppointmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Deletes the given appointment. */
    void deleteAppointment(Appointment appointment) throws AppointmentNotFoundException;

    /** Adds the given appointment */
    void addAppointment(Appointment appointment) throws DuplicateAppointmentException;

    /** Returns an unmodifiable view of the filtered appointment list */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** Raises an event to indicate a new appointment has been added */
    private void indicateAppointmentAdded(Appointment appointment) {
        raise(new NewAppointmentAddedEvent(appointment));
    }

    /** Raises an event to indicate an appointment has been deleted */
    private void indicateAppointmentDeleted(ObservableList<Appointment> appointments) {
        raise(new AppointmentDeletedEvent(appointments));
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void deletePersons(List<Person> targets) throws PersonNotFoundException {
        addressBook.removePersons(targets);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void deleteAppointment(Appointment target) throws AppointmentNotFoundException {
        addressBook.removeAppointment(target);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        indicateAppointmentDeleted(getFilteredAppointmentList());
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addAppointment(Appointment appointment) throws DuplicateAppointmentException {
        addressBook.addAppointment(appointment);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        indicateAppointmentAdded(appointment);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return FXCollections.unmodifiableObservableList(filteredAppointments);
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointments.setPredicate(predicate);
    }
```
###### \java\seedu\address\model\person\DateAdded.java
``` java
/**
 * Represents a Person's date added to the address book.
 * Guarantees: immutable; is valid
 */
public class DateAdded {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date input should be in the format: dd/MM/yyyy";
    /*
     * The first character of the date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATE_VALIDATION_REGEX = "\\d{2}/\\d{2}/\\d{4}";

    public final String dateAdded;

    /**
     * Constructs a {@code DateAdded}.
     *
     * @param dateAdded A valid date.
     */
    public DateAdded(String dateAdded) {
        requireNonNull(dateAdded);
        assert isValidDate(dateAdded); //dateAdded generated by the program should be correct
        this.dateAdded = dateAdded;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return dateAdded;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateAdded // instanceof handles nulls
                && this.dateAdded.equals(((DateAdded) other).dateAdded)); // state check
    }

    @Override
    public int hashCode() {
        return dateAdded.hashCode();
    }

}
```
###### \java\seedu\address\model\person\DateAddedIsBeforeDateInputPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code DateAdded} is before the date input.
 */

public class DateAddedIsBeforeDateInputPredicate implements Predicate<Person> {
    private final String dateInputString;

    public DateAddedIsBeforeDateInputPredicate(String dateInputString) {
        this.dateInputString = dateInputString;
    }

    @Override
    public boolean test(Person person) {
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateAddedString = person.getDateAdded().dateAdded;
            assert DateAdded.isValidDate(dateAddedString);
            Date dateAdded = dateFormatter.parse(dateAddedString);
            Date dateInput = dateFormatter.parse(dateInputString);
            return dateAdded.compareTo(dateInput) <= 0;
        } catch (ParseException e) {
            return false; //need to resolve this part
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DateAddedIsBeforeDateInputPredicate
                && this.dateInputString.equals(((DateAddedIsBeforeDateInputPredicate) other).dateInputString));
    }

}
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Removes all the equivalent persons from the list.
     *
     * @throws PersonNotFoundException if any of such persons could not be found in the list.
     */
    public boolean removeAll(List<Person> toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean personsFoundAndDeleted = internalList.removeAll(toRemove);
        if (!personsFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return personsFoundAndDeleted;
    }
```
###### \java\seedu\address\model\PersonIsAddedBeforeDateInputAndContainsTagsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code UniqueTagList} matches all of the input tags and
 * {@code DateAdded} is before the date input.
 */
public class PersonIsAddedBeforeDateInputAndContainsTagsPredicate implements Predicate<Person> {

    private final Set<Tag> inputTags;
    private final String inputDate;

    public PersonIsAddedBeforeDateInputAndContainsTagsPredicate(Set<Tag> inputTags, String inputDate) {
        this.inputTags = inputTags;
        this.inputDate = inputDate;
    }

    @Override
    public boolean test(Person person) {
        UniqueTagListContainsTagsPredicate containsTagsPredicate =
                new UniqueTagListContainsTagsPredicate(inputTags);
        DateAddedIsBeforeDateInputPredicate isAddedBeforeDateInputPredicate =
                new DateAddedIsBeforeDateInputPredicate(inputDate);
        return containsTagsPredicate.test(person) && isAddedBeforeDateInputPredicate.test(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonIsAddedBeforeDateInputAndContainsTagsPredicate // instanceof handles nulls
                && this.inputTags.equals(((
                        PersonIsAddedBeforeDateInputAndContainsTagsPredicate) other).inputTags) // state check
                && this.inputDate.equals(((
                        PersonIsAddedBeforeDateInputAndContainsTagsPredicate) other).inputDate));
    }
}
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the appointments list.
     * This list will not contain any duplicate appointments.
     */
    ObservableList<Appointment> getAppointmentList();
```
###### \java\seedu\address\model\tag\UniqueTagListContainsTagsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code UniqueTagList} matches all of the input tags.
 */

public class UniqueTagListContainsTagsPredicate  implements Predicate<Person> {
    private final Set<Tag> inputTags;

    public UniqueTagListContainsTagsPredicate(Set<Tag> inputTags) {
        this.inputTags = inputTags;
    }

    @Override
    public boolean test(Person person) {
        UniqueTagList personTags = new UniqueTagList(person.getTags());
        return inputTags.stream()
                .allMatch(personTags::contains);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTagListContainsTagsPredicate // instanceof handles nulls
                && this.inputTags.equals(((UniqueTagListContainsTagsPredicate) other).inputTags)); // state check
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedAppointment.java
``` java
/**
 * JAXB-friendly version of the Appointment.
 */
public class XmlAdaptedAppointment {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String location;


    /**
     * Constructs an XmlAdaptedAppointment.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Constructs an {@code XmlAdaptedAppointment} with the given appointment details.
     */
    public XmlAdaptedAppointment(String name, String date, String startTime, String endTime, String location) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAppointment
     */
    public XmlAdaptedAppointment(Appointment source) {
        name = source.getName().fullName;
        date = source.getDate().date;
        startTime = source.getStartTime().time;
        endTime = source.getEndTime().time;
        location = source.getLocation().value;
    }

    /**
     * Converts this jaxb-friendly adapted appointment object into the model's Appointment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment
     */
    public Appointment toModelType() throws IllegalValueException {
        if (this.name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, PersonName.class.getSimpleName()));
        }
        if (!PersonName.isValidName(this.name)) {
            throw new IllegalValueException(PersonName.MESSAGE_NAME_CONSTRAINTS);
        }
        final PersonName name = new PersonName(this.name);

        if (this.date == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(this.date)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date date = new Date(this.date);

        if (this.startTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, StartTime.class.getSimpleName()));
        }
        if (!StartTime.isValidTime(this.startTime)) {
            throw new IllegalValueException(StartTime.MESSAGE_TIME_CONSTRAINTS);
        }
        final StartTime startTime = new StartTime(this.startTime);

        if (this.endTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, EndTime.class.getSimpleName()));
        }
        if (!EndTime.isValidTime(this.endTime)) {
            throw new IllegalValueException(EndTime.MESSAGE_TIME_CONSTRAINTS);
        }
        final EndTime endTime = new EndTime(this.endTime);

        if (this.location == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(this.location)) {
            throw new IllegalValueException(Location.MESSAGE_LOCATION_CONSTRAINTS);
        }
        final Location location = new Location(this.location);


        return new Appointment(name, date, startTime, endTime, location);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAppointment)) {
            return false;
        }

        XmlAdaptedAppointment otherAppointment = (XmlAdaptedAppointment) other;
        return Objects.equals(name, otherAppointment.name)
                && Objects.equals(date, otherAppointment.date)
                && Objects.equals(startTime, otherAppointment.startTime)
                && Objects.equals(endTime, otherAppointment.endTime)
                && Objects.equals(location, otherAppointment.location);
    }
}
```
###### \java\seedu\address\ui\CalendarPanel.java
``` java
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<CalendarView> {

    private static final String FXML = "CalendarPanel.fxml";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
    private static final String ENTRY_TITLE = "Appt: ";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @javafx.fxml.FXML
    private CalendarView calendarView;
    private Calendar calendar;
    private PageBase pageBase;

    public CalendarPanel(ObservableList<Appointment> appointments) {
        super(FXML);
        initializeCalendar();
        setUpCalendarView();
        loadEntries(appointments);
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
        calendar = new Calendar("Appointments");
    }

    /**
     * Sets up the calendar view
     */
    private void setUpCalendarView() {
        CalendarSource calendarSource = new CalendarSource("My Calendar");
        calendarSource.getCalendars().addAll(calendar);

        calendarView.getCalendarSources().addAll(calendarSource);

        calendarView.setRequestedTime(LocalTime.now());
        calendarView.showMonthPage();
        pageBase = calendarView.getSelectedPage();
    }

    /**
     * Loads {@code appointments} into the calendar
     */
    private void loadEntries(ObservableList<Appointment> appointments) {
        appointments.stream().forEach(this::loadEntry);
    }

    /**
     * Creates an entry with the {@code appointment} details and loads it into the calendar
     */
    private void loadEntry(Appointment appointment) {
        String dateString = appointment.getDate().date;
        String startTimeString = appointment.getStartTime().time;
        String endTimeString = appointment.getEndTime().time;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime startDateTime = LocalDateTime.parse(dateString + " " + startTimeString, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(dateString + " " + endTimeString, formatter);

        Entry entry = new Entry();
        entry.setInterval(startDateTime, endDateTime);
        entry.setLocation(appointment.getLocation().value);
        entry.setTitle(ENTRY_TITLE + appointment.getName() + " " + appointment.getLocation());
        entry.setCalendar(calendar);
    }

    /**
     * Handles the event where a new appointment is added by loading the appointment into the calendar
     * @param event contains the newly added appointment
     */
    @Subscribe
    private void handleNewAppointmentAddedEvent(NewAppointmentAddedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadEntry(event.getAppointmentAdded());
    }

    /**
     * Handles the event where an appointment is deleted by loading the updated appointments list into the calendar
     * @param event contains the updated appointments list
     */
    @Subscribe
    private void handleAppointmentDeletedEvent(AppointmentDeletedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        calendar.clear();
        loadEntries(event.getUpdatedAppointments());
    }

    /**
     * Handles the event where the user is trying to zoom in on the calendar
     */
    @Subscribe
    private void handleZoomInEvent(ZoomInEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        zoomIn();
    }

    /**
     * Zooms in on the calendar if possible
     */
    private void zoomIn() {
        if (pageBase.equals(calendarView.getYearPage())) {
            calendarView.showMonthPage();
        } else if (pageBase.equals(calendarView.getMonthPage())) {
            calendarView.showWeekPage();
        } else if (pageBase.equals(calendarView.getWeekPage())) {
            calendarView.showDayPage();
        }
        pageBase = calendarView.getSelectedPage();
    }

    /**
     * Handles the event where the user is trying to zoom out on the calendar
     */
    @Subscribe
    private void handleZoomOutEvent(ZoomOutEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        zoomOut();
    }

    /**
     * Zooms out on the calendar if possible
     */
    private void zoomOut() {
        if (pageBase.equals(calendarView.getDayPage())) {
            calendarView.showWeekPage();
        } else if (pageBase.equals(calendarView.getWeekPage())) {
            calendarView.showMonthPage();
        } else if (pageBase.equals(calendarView.getMonthPage())) {
            calendarView.showYearPage();
        }
        pageBase = calendarView.getSelectedPage();
    }

    /**
     * Update the current date and time shown in the calendar as a thread in the background
     * Adapted from http://dlsc.com/wp-content/html/calendarfx/manual.html
     */
    private void updateTime() {
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
###### \resources\view\CalendarPanel.fxml
``` fxml
<?import javafx.scene.layout.StackPane?>
<?import com.calendarfx.view.CalendarView?>

<StackPane xmlns:fx="http://javafx.com/fxml/1">
    <CalendarView fx:id="calendarView"/>
</StackPane>
```
