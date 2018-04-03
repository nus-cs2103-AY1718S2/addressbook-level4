# trafalgarandre
###### /java/seedu/address/model/appointment/Title.java
``` java
/**
 * Represents an Appointment's title in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Title {

    public static final String MESSAGE_TITLE_CONSTRAINTS =
            "Appointment Title should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TITLE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String title;

    /**
     * Constructs a {@code Title}.
     *
     * @param title A valid title.
     */
    public Title(String title) {
        requireNonNull(title);
        checkArgument(isValidTitle(title), MESSAGE_TITLE_CONSTRAINTS);
        this.title = title;
    }

    /**
     * Returns true if a given string is a valid appointment title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(TITLE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && this.title.equals(((Title) other).title)); // state check
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

}
```
###### /java/seedu/address/model/appointment/UniqueAppointmentList.java
``` java
/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Appointment#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueAppointmentList implements Iterable<Appointment> {

    private final List<Appointment> internalList = new ArrayList<Appointment>();

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
     * @throws DuplicateAppointmentException
     * if the appointment to add is a duplicate of an existing appointment in the list.
     */
    public void add(Appointment toAdd) throws DuplicateAppointmentException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAppointmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Appointment {@code target} in the list with {@code editedAppointment}.
     *
     * @throws DuplicateAppointmentException
     * if the replacement is equivalent to another existing appointment in the list.
     * @throws AppointmentNotFoundException if {@code target} could not be found in the list.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment)
            throws DuplicateAppointmentException, AppointmentNotFoundException {
        requireNonNull(editedAppointment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }

        if (!target.equals(editedAppointment) && internalList.contains(editedAppointment)) {
            throw new DuplicateAppointmentException();
        }

        internalList.set(index, editedAppointment);
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
        this.internalList.addAll(replacement.internalList);
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
    public List<Appointment> asList() {
        return internalList;
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
###### /java/seedu/address/model/appointment/exceptions/DuplicateAppointmentException.java
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
###### /java/seedu/address/model/appointment/exceptions/AppointmentNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified appointment.
 */
public class AppointmentNotFoundException extends Exception {
}
```
###### /java/seedu/address/model/appointment/StartDateTime.java
``` java
/**
 * Represents start date time of an appointment in the address book.
 * Guarantees: is valid as declared in {@link #isValidStartDateTime(String)} }
 */
public class StartDateTime {
    public static final String MESSAGE_START_DATE_TIME_CONSTRAINTS =
            "Start date time should be a valid local date time";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String START_DATE_TIME_VALIDATION_REGEX = "^\\d{4}-\\d{2}-\\d{2}[ ]\\d{2}:\\d{2}$";

    public final String startDateTime;

    /**
     * Constructs a {@code Name}.
     *
     * @param startDateTime A valid startDateTime.
     */
    public StartDateTime(String startDateTime) {
        requireNonNull(startDateTime);
        checkArgument(isValidStartDateTime(startDateTime), MESSAGE_START_DATE_TIME_CONSTRAINTS);
        this.startDateTime = startDateTime;
    }

    /**
     * Returns true if a given string is a valid startDateTime.
     */
    public static boolean isValidStartDateTime(String test) {
        return test.matches(START_DATE_TIME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return startDateTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDateTime // instanceof handles nulls
                && this.startDateTime.equals(((StartDateTime) other).startDateTime)); // state check
    }

    @Override
    public int hashCode() {
        return startDateTime.hashCode();
    }

}
```
###### /java/seedu/address/model/appointment/EndDateTime.java
``` java
/**
 * Represents end date time of an appointment in the address book.
 * Guarantees: is valid as declared in {@link #isValidEndDateTime(String)} }
 */
public class EndDateTime {
    public static final String MESSAGE_END_DATE_TIME_CONSTRAINTS = "Start date time should be a valid local date time";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String END_DATE_TIME_VALIDATION_REGEX = "^\\d{4}-\\d{2}-\\d{2}[ ]\\d{2}:\\d{2}$";

    public final String endDateTime;

    /**
     * Constructs a {@code Name}.
     *
     * @param endDateTime A valid endDateTime.
     */
    public EndDateTime(String endDateTime) {
        requireNonNull(endDateTime);
        checkArgument(isValidEndDateTime(endDateTime), MESSAGE_END_DATE_TIME_CONSTRAINTS);
        this.endDateTime = endDateTime;
    }

    /**
     * Returns true if a given string is a valid endDateTime.
     */
    public static boolean isValidEndDateTime(String test) {
        return test.matches(END_DATE_TIME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return endDateTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDateTime // instanceof handles nulls
                && this.endDateTime.equals(((EndDateTime) other).endDateTime)); // state check
    }

    @Override
    public int hashCode() {
        return endDateTime.hashCode();
    }

}
```
###### /java/seedu/address/model/appointment/Appointment.java
``` java
/**
 * Represents a Appointment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    private final Title title;
    private final StartDateTime startDateTime;
    private final EndDateTime endDateTime;

    /**
     * Every field must be present and not null.
     */
    public Appointment(Title title, StartDateTime startDateTime, EndDateTime endDateTime) {
        requireAllNonNull(title, startDateTime, endDateTime);
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Title getTitle() {
        return title;
    }

    public StartDateTime getStartDateTime() {
        return startDateTime;
    }

    public EndDateTime getEndDateTime() {
        return endDateTime;
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
        return otherAppointment.getTitle().equals(this.getTitle())
                && otherAppointment.getStartDateTime().equals(this.getStartDateTime())
                && otherAppointment.getEndDateTime().equals(this.getEndDateTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, startDateTime, endDateTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Start Date Time: ")
                .append(getStartDateTime())
                .append(" End Date Time: ")
                .append(getEndDateTime());
        return builder.toString();
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void addAppointment(Appointment appointment) throws DuplicateAppointmentException {
        addressBook.addAppointment(appointment);
        indicateAddressBookChanged();
        indicateCalendarChanged();
    }

    @Override
    public synchronized void deleteAppointment(Appointment target) throws AppointmentNotFoundException {
        addressBook.removeAppointment(target);
        indicateAddressBookChanged();
        indicateCalendarChanged();
    }

    @Override
    public void updateAppointment(Appointment target, Appointment editedAppointment)
            throws DuplicateAppointmentException, AppointmentNotFoundException {
        requireAllNonNull(target, editedAppointment);
        addressBook.updateAppointment(target, editedAppointment);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Adds an appointment to the address book.
     *
     *
     * @throws DuplicateAppointmentException if an equivalent appointment already exists.
     */
    public void addAppointment(Appointment a) throws DuplicateAppointmentException {
        appointments.add(a);
    }

    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppointment}.
     * @throws DuplicateAppointmentException if updating the appointment's details causes the appointment to be
     * equivalent to another existing appointment in the list.
     * @throws AppointmentNotFoundException if {@code target} could not be found in the list.
     *
     */
    public void updateAppointment(Appointment target, Appointment editedAppointment)
            throws DuplicateAppointmentException, AppointmentNotFoundException {
        requireNonNull(editedAppointment);

        appointments.setAppointment(target, editedAppointment);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws AppointmentNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeAppointment(Appointment key) throws AppointmentNotFoundException {
        if (appointments.remove(key)) {
            return true;
        } else {
            throw new AppointmentNotFoundException();
        }
    }


    //// job-level operations

    /**
     * Adds a job to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicateJobException if an equivalent job already exists.
     */
    public void addJob(Job job) throws DuplicateJobException {
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        jobs.add(job);
    }

    /// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags"
                + appointments.asList().size() + " appointments";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public List<Appointment> getAppointmentList() {
        return appointments.asList();
    }

    public ObservableList<Job> getJobList() {
        return jobs.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags))
                && this.appointments.equals(((AddressBook) other).appointments)
                && this.jobs.equals(((AddressBook) other).jobs);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags, appointments, jobs);
    }
}
```
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
            "Profile picture does not exist. Please give another profile picture";

    // alphanumeric and special characters
    public static final String PROFILE_PICTURE_VALIDATION_REGEX = "^$|([^\\s]+(\\.(?i)(jpeg|jpg|png|gif|bmp))$)";
    public static final String DEFAULT_IMG_URL = "file:src/test/data/images/default.png";
    public static final String PROFILE_PICTURE_FOLDER =
            "./src/main/resources/ProfilePictures/";

    public final String filePath;
    public final String url;

    /**
     * Constructs an {@code Email}.
     *
     * @param profilePicture A valid image path.
     */
    public ProfilePicture(String... profilePicture) {
        if (profilePicture.length != 0 && profilePicture[0] != null) {
            checkArgument(isValidProfilePicture(profilePicture[0]), MESSAGE_PROFILEPICTURE_CONSTRAINTS);
            checkArgument(hasValidProfilePicture(profilePicture[0]), MESSAGE_PROFILEPICTURE_NOT_EXISTS);
            if (profilePicture[0].length() > 37
                    && profilePicture[0].substring(0, 37).equals("./src/main/resources/ProfilePictures/")) {
                this.filePath = profilePicture[0];
            } else {
                this.filePath = copyImageToProfilePictureFolder(profilePicture[0]);
            }
            this.url = "file:".concat(this.filePath.substring(2));
        } else {
            this.url = DEFAULT_IMG_URL;
            this.filePath = DEFAULT_IMG_URL.replace("file:", "./");
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
        return new Image(url);
    }

    @Override
    public String toString() {
        return filePath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePicture // instanceof handles nulls
                && this.filePath.equals(((ProfilePicture) other).filePath)); // state check
    }

    @Override
    public int hashCode() {
        return filePath.hashCode();
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
            Date date = new Date();
            destPath = PROFILE_PICTURE_FOLDER.concat(
                    date.toString().replace(":", "").replace(" ", "").concat(
                            ".").concat(fileExtension));
            File dest = new File(destPath);
            Files.copy(source.toPath(), dest.toPath());
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
###### /java/seedu/address/storage/XmlAdaptedAppointment.java
``` java
/**
 * JAXB-friendly version of the Appointment.
 */
public class XmlAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String startDateTime;
    @XmlElement(required = true)
    private String endDateTime;

    /**
     * Constructs an XmlAdaptedAppointment.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Constructs an {@code XmlAdaptedAppointment} with the given appointment details.
     */
    public XmlAdaptedAppointment(String title, String startDateTime, String endDateTime) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAppointment
     */
    public XmlAdaptedAppointment(Appointment source) {
        title = source.getTitle().title;
        startDateTime = source.getStartDateTime().startDateTime;
        endDateTime = source.getEndDateTime().endDateTime;
    }

    /**
     * Converts this jaxb-friendly adapted appointment object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment
     */
    public Appointment toModelType() throws IllegalValueException {
        if (this.title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(this.title)) {
            throw new IllegalValueException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        final Title title = new Title(this.title);

        if (this.startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartDateTime.class.getSimpleName()));
        }
        if (!StartDateTime.isValidStartDateTime(this.startDateTime)) {
            throw new IllegalValueException(StartDateTime.MESSAGE_START_DATE_TIME_CONSTRAINTS);
        }
        final StartDateTime startDateTime = new StartDateTime(this.startDateTime);

        if (this.endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EndDateTime.class.getSimpleName()));
        }
        if (!EndDateTime.isValidEndDateTime(this.endDateTime)) {
            throw new IllegalValueException(EndDateTime.MESSAGE_END_DATE_TIME_CONSTRAINTS);
        }
        final EndDateTime endDateTime = new EndDateTime(this.endDateTime);

        return new Appointment(title, startDateTime, endDateTime);
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
        return Objects.equals(title, otherAppointment.title)
                && Objects.equals(startDateTime, otherAppointment.startDateTime)
                && Objects.equals(endDateTime, otherAppointment.endDateTime);
    }
}
```
###### /java/seedu/address/logic/parser/appointment/DeleteAppointmentCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteAppointmentCommand object
 */
public class DeleteAppointmentCommandParser implements Parser<DeleteAppointmentCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAppointmentCommand
     * and returns an DeleteAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_TITLE, PREFIX_START_DATE_TIME, PREFIX_END_DATE_TIME);
        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_START_DATE_TIME, PREFIX_END_DATE_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAppointmentCommand.MESSAGE_USAGE));
        }
        try {
            Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE)).get();
            StartDateTime startDateTime =
                    ParserUtil.parseStartDateTime(argMultimap.getValue(PREFIX_START_DATE_TIME)).get();
            EndDateTime endDateTime = ParserUtil.parseEndDateTime(argMultimap.getValue(PREFIX_END_DATE_TIME)).get();

            Appointment appointment = new Appointment(title, startDateTime, endDateTime);

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
###### /java/seedu/address/logic/parser/appointment/YearCommandParser.java
``` java
/**
 * Parses input arguments and creates a new YearCommand object
 */
public class YearCommandParser implements Parser<YearCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the YearCommand
     * and returns an YearCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public YearCommand parse(String args) throws ParseException {
        try {
            Year year = ParserUtil.parseYear(args);
            return new YearCommand(year);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, YearCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/appointment/WeekCommandParser.java
``` java
/**
 * Parses input arguments and creates a new WeekCommand object
 */
public class WeekCommandParser implements Parser<WeekCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the WeekCommand
     * and returns an WeekCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WeekCommand parse(String args) throws ParseException {
        try {
            Year year = ParserUtil.parseYearOfWeek(args);
            int week = ParserUtil.parseWeek(args);
            return new WeekCommand(year, week);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, WeekCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/appointment/AddAppointmentCommandParser.java
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
###### /java/seedu/address/logic/parser/appointment/DateCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DateCommand object
 */
public class DateCommandParser implements Parser<DateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DateCommand
     * and returns an DateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DateCommand parse(String args) throws ParseException {
        try {
            LocalDate date = ParserUtil.parseDate(args);
            return new DateCommand(date);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/appointment/MonthCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MonthCommand object
 */
public class MonthCommandParser implements Parser<MonthCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MonthCommand
     * and returns an MonthCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MonthCommand parse(String args) throws ParseException {
        try {
            YearMonth yearMonth = ParserUtil.parseYearMonth(args);
            return new MonthCommand(yearMonth);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MonthCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> numberOfPositions} into an {@code Optional<String>}
     * if {@code numberOfPositions} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<NumberOfPositions> parseNumberOfPositions(Optional<String> numberOfPositions)
            throws IllegalValueException {
        requireNonNull(numberOfPositions);
        return numberOfPositions.isPresent() ? Optional.of(parseNumberOfPositions(numberOfPositions.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String yearMonth} into a {@code yearMonth}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code yearMonth} is invalid.
     */
    public static YearMonth parseYearMonth(String yearMonth) throws IllegalValueException {
        String trimmedYearMonth = yearMonth.trim();
        if (!trimmedYearMonth.matches(YEAR_MONTH_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_YEAR_MONTH_CONSTRAINTS);
        }
        if (trimmedYearMonth.length() == 0) {
            return null;
        } else {
            return YearMonth.parse(trimmedYearMonth);
        }
    }

    /**
     * Parses a {@code String date} into a {@code date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws IllegalValueException {
        String trimmedDate = date.trim();
        if (!trimmedDate.matches(DATE_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        if (trimmedDate.length() == 0) {
            return null;
        } else {
            return LocalDate.parse(trimmedDate);
        }
    }

    /**
     * Parses a {@code String date} into a {@code year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws IllegalValueException {
        String trimmedYear = year.trim();
        if (!trimmedYear.matches(YEAR_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_YEAR_CONSTRAINTS);
        }
        if (trimmedYear.length() == 0) {
            return null;
        } else {
            return Year.parse(trimmedYear);
        }
    }

    /**
     * Parses a {@code String args} into a {@code year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code args} is invalid.
     */
    public static Year parseYearOfWeek(String args) throws IllegalValueException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.matches(WEEK_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_WEEK_CONSTRAINTS);
        }
        if (trimmedArgs.length() == 0) {
            return null;
        } else {
            return Year.parse(trimmedArgs.substring(0, 4));
        }
    }

    /**
     * Parses a {@code String args} into a {@code week}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code args} is invalid.
     */
    public static int parseWeek(String args) throws IllegalValueException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.matches(WEEK_VALIDATION_REGEX)) {
            throw new IllegalValueException(MESSAGE_YEAR_CONSTRAINTS);
        }
        if (trimmedArgs.length() == 0) {
            return 0;
        } else {
            return Integer.parseInt(trimmedArgs.substring(5));
        }
    }
}
```
###### /java/seedu/address/logic/commands/appointment/AddAppointmentCommand.java
``` java
/**
 * Add appointment to calendar of addressbook
 */
public class AddAppointmentCommand extends Command {
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
    public CommandResult execute() throws CommandException {
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
###### /java/seedu/address/logic/commands/appointment/CalendarCommand.java
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
###### /java/seedu/address/logic/commands/appointment/DeleteAppointmentCommand.java
``` java
/**
 * delete appointment from calendar of addressbook
 */
public class DeleteAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "delapp";

    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_TITLE + " "
            + PREFIX_START_DATE_TIME + " "
            + PREFIX_END_DATE_TIME;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an appointment to calendar. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_START_DATE_TIME + "START DATE TIME "
            + PREFIX_END_DATE_TIME + "END DATE TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Birthday "
            + PREFIX_START_DATE_TIME + "2018-03-26 12:00 "
            + PREFIX_END_DATE_TIME + "2018-03-26 12:30 ";

    public static final String MESSAGE_SUCCESS = "Appointment deleted: %1$s";
    public static final String MESSAGE_NOT_FOUND_APPOINTMENT =
            "This appointment does not exist in the calendar or has already been deleted";

    private final Appointment toDelete;

    /**
     * Creates an DeleteAppointmentCommand to add the specified {@code Appointment}
     */
    public DeleteAppointmentCommand(Appointment appointment) {
        requireNonNull(appointment);
        toDelete = appointment;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.deleteAppointment(toDelete);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
        } catch (AppointmentNotFoundException e) {
            throw new CommandException(MESSAGE_NOT_FOUND_APPOINTMENT);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAppointmentCommand // instanceof handles nulls
                && toDelete.equals(((DeleteAppointmentCommand) other).toDelete));
    }
}
```
###### /java/seedu/address/logic/commands/appointment/MonthCommand.java
``` java
/**
 * Change view of calendar to specific month.
 */
public class MonthCommand extends Command {
    public static final String COMMAND_WORD = "month";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View month. "
            + "Parameters: MONTH (optional, but must be in format YYYY-MM if have)\n"
            + "Example: " + COMMAND_WORD + " 2018-03";

    public static final String MESSAGE_SUCCESS = "View month: %1$s";
    public static final String YEAR_MONTH_VALIDATION_REGEX = "^$|^\\d{4}-\\d{2}";
    public static final String MESSAGE_YEAR_MONTH_CONSTRAINTS = "Month needs to be null or in format YYYY-MM";

    private final YearMonth yearMonth;

    /**
     * Creates an MonthCommand to view the specified {@code yearMonth} or current if null
     */
    public MonthCommand(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowMonthRequestEvent(yearMonth));

        return new CommandResult(String.format(MESSAGE_SUCCESS, yearMonth));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MonthCommand // instanceof handles nulls
                && ((yearMonth == null && ((MonthCommand) other).yearMonth == null)
                || (yearMonth != null && ((MonthCommand) other).yearMonth != null
                && yearMonth.equals(((MonthCommand) other).yearMonth))));
    }
}
```
###### /java/seedu/address/logic/commands/appointment/YearCommand.java
``` java
/**
 * Change view of calendar to specific year.
 */
public class YearCommand extends Command {
    public static final String COMMAND_WORD = "year";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View year. "
            + "Parameters: year (optional, but must be in format YYYY if have)\n"
            + "Example: " + COMMAND_WORD + " 2018";

    public static final String MESSAGE_SUCCESS = "View year: %1$s";
    public static final String YEAR_VALIDATION_REGEX = "^$|^\\d{4}";
    public static final String MESSAGE_YEAR_CONSTRAINTS = "Year needs to be null or in format YYYY";

    private final Year year;

    /**
     * Creates an YearCommand to view the specified {@code yearMonth} or current if null
     */
    public YearCommand(Year year) {
        this.year = year;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowYearRequestEvent(year));

        return new CommandResult(String.format(MESSAGE_SUCCESS, year));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof YearCommand // instanceof handles nulls
                && ((year == null && ((YearCommand) other).year == null)
                || (year != null && ((YearCommand) other).year != null && year.equals(((YearCommand) other).year))));
    }
}
```
###### /java/seedu/address/logic/commands/appointment/WeekCommand.java
``` java
/**
 * Change view of calendar to specific week.
 */
public class WeekCommand extends Command {
    public static final String COMMAND_WORD = "week";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View week. "
            + "Parameters: YEAR WEEK (optional, but must be in format YYYY WW if have)\n"
            + "Example: " + COMMAND_WORD + " 2018";

    public static final String MESSAGE_SUCCESS = "View week: %1$s";
    public static final String WEEK_VALIDATION_REGEX = "^$|^\\d{4}\\s\\d{2}";
    public static final String MESSAGE_WEEK_CONSTRAINTS = "Week needs to be null or in format YYYY DD";

    private final Year year;
    private final int week;

    /**
     * Creates an WeekCommand to view the specified {@code week, year} or current if null
     */
    public WeekCommand(Year year, int week) {
        this.year = year;
        this.week = week;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowWeekRequestEvent(year, week));
        return new CommandResult(String.format(MESSAGE_SUCCESS, week + " " + year));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WeekCommand // instanceof handles nulls
                && ((year == null && ((WeekCommand) other).year == null)
                || (year != null && ((WeekCommand) other).year != null && year.equals(((WeekCommand) other).year))
                && week == (((WeekCommand) other).week)));
    }
}
```
###### /java/seedu/address/logic/commands/appointment/DateCommand.java
``` java
/**
 * Change view of calendar to specific date.
 */
public class DateCommand extends Command {
    public static final String COMMAND_WORD = "date";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View date. "
            + "Parameters: DATE (optional, but must be in format YYYY-MM-DD if have)\n"
            + "Example: " + COMMAND_WORD + " 2018-03-26";

    public static final String MESSAGE_SUCCESS = "View date: %1$s";
    public static final String DATE_VALIDATION_REGEX = "^$|^\\d{4}-\\d{2}-\\d{2}";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date needs to be null or in format YYYY-MM-DD";

    private final LocalDate date;

    /**
     * Creates an DateCommand to view the specified {@code Date} or current if null
     */
    public DateCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowDateRequestEvent(date));

        return new CommandResult(String.format(MESSAGE_SUCCESS, date));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateCommand // instanceof handles nulls
                && ((date == null && ((DateCommand) other).date == null)
                || (date != null && ((DateCommand) other).date != null && date.equals(((DateCommand) other).date))));

    }
}
```
###### /java/seedu/address/ui/DetailsPanel.java
``` java
    /**
     * Adds the CalendarView to the DetailsPanel
     */
    public void addCalendarPanel(List<Appointment> appointmentList) {
        CalendarPanel calendarPanel = new CalendarPanel(appointmentList);
        calendar.setContent(calendarPanel.getRoot());
    }

    /**
     * Adds the ContactDetailsPanel to the DetailsPanel
     */
    public void addContactDetailsDisplayPanel() {
        contactDetailsDisplay = new ContactDetailsDisplay();
        profile.setContent(contactDetailsDisplay.getRoot());
    }

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

    @Subscribe
    private void handleShowDateRequestEvent(ShowDateRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.targetDate == null) {
            calendarView.showDayPage();
        } else {
            calendarView.showDate(event.targetDate);
        }
    }

    @Subscribe
    private void handleShowWeekRequestEvent(ShowWeekRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.targetYear == null) {
            calendarView.showWeekPage();
        } else {
            calendarView.showWeek(event.targetYear, event.targetWeek);
        }
    }

    @Subscribe
    private void handleShowMonthRequestEvent(ShowMonthRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.targetYearMonth == null) {
            calendarView.showMonthPage();
        } else {
            calendarView.showYearMonth(event.targetYearMonth);
        }
    }

    @Subscribe
    private void handleShowYearRequestEvent(ShowYearRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.targetYear == null) {
            calendarView.showYearPage();
        } else {
            calendarView.showYear(event.targetYear);
        }
    }
}
```
###### /java/seedu/address/commons/events/ui/ShowYearRequestEvent.java
``` java
/**
 * An event requesting to change view of Calendar to YearPage.
 */
public class ShowYearRequestEvent extends BaseEvent {
    public final Year targetYear;

    public ShowYearRequestEvent(Year year) {
        this.targetYear = year;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/ReloadCalendarEvent.java
``` java
/**
 * An event request the Calendar to reload.
 */
public class ReloadCalendarEvent extends BaseEvent {

    public final List<Appointment> appointments;

    public ReloadCalendarEvent(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/ShowMonthRequestEvent.java
``` java
/**
 * An event requesting to change view of Calendar to MonthPage.
 */
public class ShowMonthRequestEvent extends BaseEvent {
    public final YearMonth targetYearMonth;

    public ShowMonthRequestEvent(YearMonth yearMonth) {
        this.targetYearMonth = yearMonth;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/ShowDateRequestEvent.java
``` java
/**
 * An event requesting to change view of Calendar to DatePage.
 */
public class ShowDateRequestEvent extends BaseEvent {
    public final LocalDate targetDate;

    public ShowDateRequestEvent(LocalDate date) {
        this.targetDate = date;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/ShowWeekRequestEvent.java
``` java
/**
 * An event requesting to change view of Calendar to WeekPage.
 */
public class ShowWeekRequestEvent extends BaseEvent {
    public final Year targetYear;
    public final int targetWeek;

    public ShowWeekRequestEvent(Year year, int week) {
        this.targetYear = year;
        this.targetWeek = week;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/core/appointment/TypicalCalendar.java
``` java
/**
 * A utility class containing a list of calendars's component objects to be used in tests.
 */
public class TypicalCalendar {
    public static final LocalDate FIRST_DATE =  LocalDate.parse("2018-01-04");
    public static final LocalDate SECOND_DATE =  LocalDate.parse("2018-03-08");
    public static final Year FIRST_YEAR = Year.parse("2017");
    public static final Year SECOND_YEAR = Year.parse("2018");
    public static final int FIRST_WEEK = Integer.parseInt("05");
    public static final int SECOND_WEEK = Integer.parseInt("30");
    public static final YearMonth FIRST_YEAR_MONTH = YearMonth.parse("2018-03");
    public static final YearMonth SECOND_YEAR_MONTH = YearMonth.parse("2018-04");
}
```
###### /resources/view/CalendarPanel.fxml
``` fxml
<StackPane xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/9">
    <CalendarView fx:id="calendarView" />
</StackPane>
```
