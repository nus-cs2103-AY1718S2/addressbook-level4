# WJY-norainu
###### \java\seedu\address\commons\events\ui\ChangeCalendarRequestEvent.java
``` java
/**
 * Indicates a request to show another celebrity's calendar
 */
public class ChangeCalendarRequestEvent extends BaseEvent {

    public final CelebCalendar celebCalendar;

    public ChangeCalendarRequestEvent(CelebCalendar celebCalendar) {
        this.celebCalendar = celebCalendar;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ChangeCalendarViewPageRequestEvent.java
``` java
/**
 * Indicates a request to change to another calendar view page
 */
public class ChangeCalendarViewPageRequestEvent extends BaseEvent {

    public final String calendarViewPage;

    public ChangeCalendarViewPageRequestEvent(String calendarViewPage) {
        this.calendarViewPage = calendarViewPage;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowCalendarBasedOnDateEvent.java
``` java
/**
 * Event to be raised when switched back from appointment list view to calendar.
 */
public class ShowCalendarBasedOnDateEvent extends BaseEvent {
    private LocalDate date;

    public ShowCalendarBasedOnDateEvent(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowCalendarEvent.java
``` java
/**
 * Event to be raised when switched back from appointment list view to calendar.
 */
public class ShowCalendarEvent extends BaseEvent {
    public ShowCalendarEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowCombinedCalendarViewRequestEvent.java
``` java
/**
 * Indicates a request to change to another calendar view page
 */
public class ShowCombinedCalendarViewRequestEvent extends BaseEvent {

    public ShowCombinedCalendarViewRequestEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\calendar\DeleteAppointmentCommand.java
``` java
/**
 * Deletes an appointment identified using its last displayed index from the calendar.
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "deleteAppointment";
    public static final String COMMAND_ALIAS = "da";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an appointment. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";


    public static final String MESSAGE_SUCCESS = "Deleted Appointment: %1$s";
    public static final String MESSAGE_APPOINTMENT_LIST_BECOMES_EMPTY = "\nAppointment list becomes empty, "
            + "Switching back to calendar view by day\n"
            + "Currently showing %1$s calendar";

    private final Index targetIndex;

    private Appointment apptToDelete;

    public DeleteAppointmentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {
        if (!model.getIsListingAppointments()) {
            throw new CommandException(Messages.MESSAGE_MUST_SHOW_LIST_OF_APPOINTMENTS);
        }

        try {
            apptToDelete = model.deleteAppointment(targetIndex.getZeroBased());
        } catch (IndexOutOfBoundsException iobe) {
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        List<Appointment> currentAppointmentList = model.getCurrentlyDisplayedAppointments();
        // if the list becomes empty, switch back to combined calendar day view
        if (currentAppointmentList.isEmpty()) {
            model.setIsListingAppointments(false);
            return changeToCalendarWithDayView();
        }

        EventsCenter.getInstance().post(new ShowAppointmentListEvent(currentAppointmentList));
        return new CommandResult(String.format(MESSAGE_SUCCESS, apptToDelete.getTitle()));
    }

    /**
     * sets the calendar panel to calendar
     * @return CommandResult with the corresponding message
     */
    private CommandResult changeToCalendarWithDayView() {
        model.setCelebCalendarViewPage(DAY_VIEW_PAGE);
        EventsCenter.getInstance().post(new ChangeCalendarViewPageRequestEvent(DAY_VIEW_PAGE));
        EventsCenter.getInstance().post(new ShowCalendarEvent());

        Celebrity currentCalendarOwner = model.getCurrentCelebCalendarOwner();
        if (currentCalendarOwner == null) {
            return new CommandResult(
                    String.format(MESSAGE_SUCCESS, apptToDelete.getTitle())
                            + String.format(MESSAGE_APPOINTMENT_LIST_BECOMES_EMPTY,
                            "combined"));
        } else {
            return new CommandResult(
                    String.format(MESSAGE_SUCCESS, apptToDelete.getTitle())
                            + String.format(MESSAGE_APPOINTMENT_LIST_BECOMES_EMPTY,
                            currentCalendarOwner.getName().toString() + "'s"));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteAppointmentCommand
                && Objects.equals(this.apptToDelete, ((DeleteAppointmentCommand) other).apptToDelete)
                && this.targetIndex.equals(((DeleteAppointmentCommand) other).targetIndex));
    }
}
```
###### \java\seedu\address\logic\commands\calendar\ViewCalendarByCommand.java
``` java
/**
 * Switches the calendar view to another view specified by the user.
 */
public class ViewCalendarByCommand extends Command {

    public static final String COMMAND_WORD = "viewCalendarBy";
    public static final String COMMAND_ALIAS = "vcb";
    public static final String[] VALID_ARGUMENT = {DAY_VIEW_PAGE, WEEK_VIEW_PAGE, MONTH_VIEW_PAGE};

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches to the calendar view specified.\n"
            + "Parameter: VIEW (must be one of "
            + Arrays.toString(VALID_ARGUMENT)
            + ". Parameter is not case-sensitive)\n"
            + "Example: " + COMMAND_WORD + " week";

    public static final String MESSAGE_NO_CHANGE_IN_CALENDARVIEW = "Calender is already in %1$s view";
    public static final String MESSAGE_SUCCESS = "Switched to view calendar by %1$s";

    private final String calendarViewPage;

    public ViewCalendarByCommand(String calendarViewPage) {
        this.calendarViewPage = calendarViewPage;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (calendarViewPage.contentEquals(model.getCurrentCelebCalendarViewPage())
                && !model.getIsListingAppointments()) {
            throw new CommandException(String.format(MESSAGE_NO_CHANGE_IN_CALENDARVIEW, calendarViewPage));
        }

        model.setCelebCalendarViewPage(calendarViewPage);
        EventsCenter.getInstance().post(new ChangeCalendarViewPageRequestEvent(calendarViewPage));

        // if it's switching from appointment list view to calendar
        if (model.getIsListingAppointments()) {
            EventsCenter.getInstance().post(new ShowCalendarEvent());
            model.setIsListingAppointments(false);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, calendarViewPage));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCalendarByCommand // instanceof handles nulls
                && this.calendarViewPage.equals(((ViewCalendarByCommand) other).calendarViewPage)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\calendar\ViewCalendarCommand.java
``` java
/**
 * Display the calendar of the celebrity specified by the user.
 */
public class ViewCalendarCommand extends Command {

    public static final String COMMAND_WORD = "viewCalendar";
    public static final String COMMAND_ALIAS = "vc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the calendar of the celebrity"
            + " identified by the index number used in the last person listing.\n"
            + "Parameter: INDEX (must be a positive integer and the person at the index must be a celebrity)\n"
            + "Example: " + COMMAND_WORD + " 6";

    public static final String MESSAGE_NO_CHANGE_IN_CALENDAR = "The calendar shown currently is already %1$s's";
    public static final String MESSAGE_SUCCESS = "Switched to show %1$s's calendar";
    public static final String MESSAGE_NOT_CELEBRITY = "The person at the given index is not a celebrity and has "
            + "no calendar to show";

    private final Index targetIndex;

    public ViewCalendarCommand(Index index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToShowCalendar = lastShownList.get(targetIndex.getZeroBased());
        // person at the given index is not a celebrity
        if (!personToShowCalendar.hasTag(CELEBRITY_TAG)) {
            throw new CommandException(MESSAGE_NOT_CELEBRITY);
        }

        Celebrity celebrityToShowCalendar = (Celebrity) personToShowCalendar;
        // the celebrity's calendar is currently being shown
        if (celebrityToShowCalendar.equals(model.getCurrentCelebCalendarOwner())
                && !model.getIsListingAppointments()) {
            throw new CommandException(String.format(MESSAGE_NO_CHANGE_IN_CALENDAR,
                    celebrityToShowCalendar.getName().toString()));
        }

        model.setCelebCalendarOwner(celebrityToShowCalendar);
        EventsCenter.getInstance().post(new ChangeCalendarRequestEvent(celebrityToShowCalendar.getCelebCalendar()));
        // if it's switching from appointment list view to calendar
        if (model.getIsListingAppointments()) {
            EventsCenter.getInstance().post(new ShowCalendarEvent());
            model.setIsListingAppointments(false);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, celebrityToShowCalendar.getName().toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCalendarCommand // instanceof handles nulls
                && this.targetIndex.equals(((ViewCalendarCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\calendar\ViewCombinedCalendarCommand.java
``` java
/**
 * Display the calendar of the celebrity specified by the user.
 */
public class ViewCombinedCalendarCommand extends Command {

    public static final String COMMAND_WORD = "viewCombinedCalendar";
    public static final String COMMAND_ALIAS = "vcc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays a combined view of calendars "
            + "of all celebrities.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_ALREADY_IN_COMBINED_VIEW = "The current calendar is already in combined view";
    public static final String MESSAGE_SUCCESS = "Switched to show combined calendar";

    public ViewCombinedCalendarCommand() {}

    @Override
    public CommandResult execute() throws CommandException {
        // view combined calendar when it's already in combined calendar
        if (model.getCurrentCelebCalendarOwner() == null
                && !model.getIsListingAppointments()) {
            throw new CommandException(MESSAGE_ALREADY_IN_COMBINED_VIEW);
        }

        model.setCelebCalendarOwner(null);
        EventsCenter.getInstance().post(new ShowCombinedCalendarViewRequestEvent());

        // if it's switching from appointment list view to calendar
        if (model.getIsListingAppointments()) {
            EventsCenter.getInstance().post(new ShowCalendarEvent());
            model.setIsListingAppointments(false);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\calendar\ViewDateCommand.java
``` java
/**
 * Display the calendar based on the date specified by the user.
 */
public class ViewDateCommand extends Command {

    public static final String COMMAND_WORD = "viewDate";
    public static final String COMMAND_ALIAS = "vd";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM[-yyyy]");

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the calendar which bases on the specified "
            + "date.\n"
            + "Parameter: [DATE] (goes back to current date when no parameter is given.)"
            + "DATE should be in DD-MM-YYYY or DD-MM format, including the dash."
            + "When latter is entered, YYYY will take the current year.\n"
            + "Example: " + COMMAND_WORD + " 23-04";

    public static final String MESSAGE_INVALID_DATE = "The date entered is invalid";
    public static final String MESSAGE_NO_CHANGE_IN_BASE_DATE = "The current calendar is already based on %1$s";
    public static final String MESSAGE_SUCCESS = "Switched to view calendar based on %1$s";

    private final LocalDate date;

    public ViewDateCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public CommandResult execute() throws CommandException {
        // view a date that is already the base date
        if (model.getBaseDate().equals(date)
                && !model.getIsListingAppointments()) {
            throw new CommandException(String.format(MESSAGE_NO_CHANGE_IN_BASE_DATE,
                    date.format(FORMATTER)));
        }

        model.setBaseDate(date);
        EventsCenter.getInstance().post(new ShowCalendarBasedOnDateEvent(date));
        model.setCelebCalendarViewPage(DAY_VIEW_PAGE);
        EventsCenter.getInstance().post(new ChangeCalendarViewPageRequestEvent(DAY_VIEW_PAGE));

        // if it's switching from appointment list view to calendar
        if (model.getIsListingAppointments()) {
            EventsCenter.getInstance().post(new ShowCalendarEvent());
            model.setIsListingAppointments(false);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, date.format(FORMATTER)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewDateCommand // instanceof handles nulls
                && Objects.equals(date, ((ViewDateCommand) other).date));
    }
}
```
###### \java\seedu\address\logic\commands\RemoveTagCommand.java
``` java
/**
 * Removes a tag from every person who has it in the address book
 * and shows the number of people affected by the operation.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removeTag";
    public static final String COMMAND_ALIAS = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a tag from every person who has it in the list and shows the number of people affected "
            + "by this operation.\n"
            + "Parameters: TAG (must be a non-empty string)\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_CANNOT_REMOVE_CELEBRITY_TAG = "Cannot remove celebrity tag.";
    public static final String MESSAGE_TAG_NOT_FOUND = "The tag %1$s does not exist and thus cannot be removed.";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Removed tag %1$s and %2$s person(s) affected.";

    public final Tag tagToRemove;

    public RemoveTagCommand(Tag tagToRemove) {
        requireNonNull(tagToRemove);
        this.tagToRemove = tagToRemove;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(tagToRemove);

        if (tagToRemove.equals(CELEBRITY_TAG)) {
            throw new CommandException(MESSAGE_CANNOT_REMOVE_CELEBRITY_TAG);
        }

        int numberOfAffectedPersons = 0;
        try {
            numberOfAffectedPersons = model.removeTag(tagToRemove);
        } catch (TagNotFoundException tnfe) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagToRemove.toString()));
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(String.format(
                MESSAGE_DELETE_TAG_SUCCESS,
                tagToRemove.toString(),
                numberOfAffectedPersons));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.tagToRemove.equals(((RemoveTagCommand) other).tagToRemove));
    }
}
```
###### \java\seedu\address\logic\parser\calendar\DeleteAppointmentCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteAppointmentCommand object
 */
public class DeleteAppointmentCommandParser implements Parser<DeleteAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAppointmentCommand
     * and returns an DeleteAppointmentCommand object for execution
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteAppointmentCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteAppointmentCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\calendar\ListAppointmentCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ListAppointmentCommand object
 */
public class ListAppointmentCommandParser implements Parser<ListAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListAppointmentCommand
     * and returns a ListAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListAppointmentCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            return new ListAppointmentCommand();
        }

        String trimmedArgs = args.trim();
        String[] arguments = trimmedArgs.split(" ");
        //there should be two elements, one for start date, the other for end date.
        if (arguments.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListAppointmentCommand.MESSAGE_USAGE));
        }
        //check if start and end dates follow the format
        try {
            FORMATTER.parse(arguments[0]);
            FORMATTER.parse(arguments[1]);
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListAppointmentCommand.MESSAGE_USAGE));
        }

        LocalDate startDate = convertFormattedStringToLocalDate(arguments[0]);
        LocalDate endDate = convertFormattedStringToLocalDate(arguments[1]);
        if (startDate.isAfter(endDate)) {
            throw new ParseException(MESSAGE_INVALID_DATE_RANGE);
        }

        return new ListAppointmentCommand(startDate, endDate);
    }

    /**
     * Converts a correctly-formatted string to a LocalDate object.
     * The input is assumed to be correctly-formmtted.
     * @param s
     * @return LocalDate
     */
    private LocalDate convertFormattedStringToLocalDate(String s) throws ParseException {
        int[] time = Arrays.stream(s.split("-")).mapToInt(Integer::parseInt).toArray();
        LocalDate date;
        try {
            if (time.length == 2) {
                date = LocalDate.of(LocalDate.now().getYear(), time[1], time[0]);
            } else if (time.length == 3) {
                date = LocalDate.of(time[2], time[1], time[0]);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListAppointmentCommand.MESSAGE_USAGE));
            }
        } catch (Exception e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
        return date;
    }
}
```
###### \java\seedu\address\logic\parser\calendar\ViewCalendarByCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ViewCalendarByCommand object
 */
public class ViewCalendarByCommandParser implements Parser<ViewCalendarByCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCalendarByCommand
     * and returns a ViewCalendarByCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCalendarByCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] arguments = trimmedArgs.split("\\s+");
        if (!isValidArgument(arguments)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCalendarByCommand.MESSAGE_USAGE));
        }

        return new ViewCalendarByCommand(arguments[0].toLowerCase());
    }

    /**
     * Takes in {@code String[]} of arguments
     * @returns true if the argument is valid, ie: only 1 argument, and argument is one of {day, week, month, year}
     */
    private boolean isValidArgument(String[] arguments) {
        if (arguments.length != 1) {
            return false;
        }
        String argument = arguments[0];
        for (String validArgument: VALID_ARGUMENT) {
            if (validArgument.equalsIgnoreCase(argument)) {
                return true;
            }
        }
        return false;
    }
}
```
###### \java\seedu\address\logic\parser\calendar\ViewCalendarCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ViewCalendarCommand object
 */
public class ViewCalendarCommandParser implements Parser<ViewCalendarCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCalendarCommand
     * and returns a ViewCalendarCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCalendarCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewCalendarCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCalendarCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\calendar\ViewDateCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ViewDateCommand object
 */
public class ViewDateCommandParser implements Parser<ViewDateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewDateCommand
     * and returns a ViewDateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewDateCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            return new ViewDateCommand(LocalDate.now());
        }

        String trimmedArgs = args.trim();
        try {
            FORMATTER.parse(trimmedArgs);
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewDateCommand.MESSAGE_USAGE));
        }

        int[] time = Arrays.stream(args.trim().split("-")).mapToInt(Integer::parseInt).toArray();
        LocalDate date;
        try {
            if (time.length == 2) {
                date = LocalDate.of(LocalDate.now().getYear(), time[1], time[0]);
            } else if (time.length == 3) {
                date = LocalDate.of(time[2], time[1], time[0]);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewDateCommand.MESSAGE_USAGE));
            }
        } catch (Exception e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
        return new ViewDateCommand(date);
    }
}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns a RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        //check if there is an input for tag
        if (args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }
        //check if args is a valid tag name
        try {
            Tag targetTag = ParserUtil.parseTag(args);
            return new RemoveTagCommand(targetTag);
        } catch (IllegalValueException ive) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Saves the newData of {@code AddressBook} in an empty {@code AddressBook}.
     */
    public void savePreviousAddressBookData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        List<Celebrity> filteredCelebrityList = filterCelebrities(syncedPersonList);
        // make celebrity's celeb calendar point to the copy of original ones
        List<Celebrity> syncedCelebrityList = syncCelebCalendar(filteredCelebrityList, newData.getCelebritiesList());

        try {
            setPersons(syncedPersonList);
            setCelebrities(syncedCelebrityList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        }
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     * Used when user undo/redo
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        List<Celebrity> filteredCelebrityList = filterCelebrities(syncedPersonList);
        // make celebrity's celeb calendar point to the copy of original ones
        List<Celebrity> syncedCelebrityList = syncCelebCalendar(filteredCelebrityList, newData.getCelebritiesList());

        if (isUndoingRemovalOfCelebrity(getCelebritiesList(), syncedCelebrityList)) {
            Celebrity copiedCelebrityForRemovedCelebrity =
                    findCelebrityRemoved(getCelebritiesList(), syncedCelebrityList);
            copiedCelebrityForRemovedCelebrity
                    .setCelebCalendar(new CelebCalendar(copiedCelebrityForRemovedCelebrity.getName().fullName));
        }

        try {
            setPersons(syncedPersonList);
            setCelebrities(syncedCelebrityList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        }
    }

    /**
     * Returns true if the user is undoing removal of a celebrity
     * @return true or false
     */
    private boolean isUndoingRemovalOfCelebrity(
            List<Celebrity> currentCelebrities, List<Celebrity> previousCelebrities) {
        return currentCelebrities.size() < previousCelebrities.size();
    }

    /**
     * Returns the copiedCelebrity of {@code Celebrity} removed in previous command
     * @return copiedCelebrity
     */
    private Celebrity findCelebrityRemoved(
            List<Celebrity> currentCelebrities, List<Celebrity> previousCelebrities) {
        for (Celebrity copiedCelebrity: previousCelebrities) {
            boolean inCurrentCelebrities = false;
            for (Celebrity celebrity: currentCelebrities) {
                if (copiedCelebrity.isCopyOf(celebrity)) {
                    inCurrentCelebrities = true;
                }
            }
            // this is the celebrity removed, set its celeb calendar to be new empty
            if (!inCurrentCelebrities) {
                return copiedCelebrity;
            }
        }
        return null;
    }

    /**
     * Counts the number of {@code person}s with a given {@code tag}
     */
    public int countPersonsWithTag(Tag tag) {
        int count = 0;
        for (Person person: persons) {
            if (person.hasTag(tag)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Removes {@code tag} from all persons in this {@code AddressBook}.
     * @return the number of {@code person}s with this {@code tag} removed.
     */
    public int removeTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException, TagNotFoundException {
        boolean tagExists = false;
        for (Tag existingTag: tags) {
            if (existingTag.equals(tag)) {
                tagExists = true;
            }
        }
        if (!tagExists) {
            throw new TagNotFoundException();
        }

        int count = 0;
        for (Person person: persons) {
            if (person.hasTag(tag)) {
                //get the new tag set with the specified tag removed
                Set<Tag> oldTags = person.getTags();
                Set<Tag> newTags = new HashSet<>();
                for (Tag tagToKeep: oldTags) {
                    if (tagToKeep.equals(tag)) {
                        continue;
                    }
                    newTags.add(tagToKeep);
                }

                //create a new person with the specified tag removed to replace the person
                EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
                editPersonDescriptor.setTags(newTags);
                Person editedPerson = createEditedPerson(person, editPersonDescriptor);
                Person syncedEditedPerson = syncWithMasterTagList(editedPerson);
                persons.setPerson(person, syncedEditedPerson);
                removeUnusedTags();

                count++;
            }
        }
        return count;
    }

    //// person-level operations

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Change pointers to celebCalendar of copied celebrity to the original celebCalendar
     * @return modified celebrities
     */
    private List<Celebrity> syncCelebCalendar(List<Celebrity> celebrities, List<Celebrity> previousCelebrities) {
        for (Celebrity celebrity: celebrities) {
            for (Celebrity previousCelebrity: previousCelebrities) {
                if (celebrity.isCopyOf(previousCelebrity)) {
                    celebrity.setCelebCalendar(previousCelebrity.getCelebCalendar());
                }
            }
        }
        return celebrities;
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        resetCelebCalendars();
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public int countPersonsWithTag(Tag tag) {
        return addressBook.countPersonsWithTag(tag);
    }

    @Override
    public int removeTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException, TagNotFoundException {
        int numPersonsAffected = addressBook.removeTag(tag);
        indicateAddressBookChanged();
        return numPersonsAffected;
    }

    @Override
    public void setCelebCalendarViewPage(String newCurrentCelebCalendarViewPage) {
        currentCelebCalendarViewPage = newCurrentCelebCalendarViewPage;
    }

    @Override
    public void setCelebCalendarOwner(Celebrity celerity) {
        this.currentCelebCalendarOwner = celerity;
    }

    @Override
    public boolean getIsListingAppointments() {
        return this.isListingAppointments;
    }

    @Override
    public void setIsListingAppointments(boolean isListingAppointments) {
        this.isListingAppointments = isListingAppointments;
    }

    //=========== Celeb Calendar Accessors ===================================================================

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public String getCurrentCelebCalendarViewPage() {
        return currentCelebCalendarViewPage;
    }

    @Override
    public Celebrity getCurrentCelebCalendarOwner() {
        return currentCelebCalendarOwner;
    }

    @Override
    public List<Appointment> getCurrentlyDisplayedAppointments() {
        return this.currentlyDisplayedAppointments;
    }

    @Override
    public List<Appointment> getStoredAppointmentList() {
        return storageCalendar.getAllAppointments();
    }

    @Override
    public void setCurrentlyDisplayedAppointments(List<Appointment> appointments) {
        this.currentlyDisplayedAppointments = appointments;
    }

    @Override
    public LocalDate getBaseDate() {
        return this.baseDate;
    }

    @Override
    public  void setBaseDate(LocalDate date) {
        this.baseDate = date;
    }

```
###### \java\seedu\address\ui\CalendarPanel.java
``` java
    /** Hide all buttons in the calendar */
    private void hideButtons() {
        celebCalendarView.setShowSearchField(false);
        celebCalendarView.setShowSourceTrayButton(false);
        celebCalendarView.setShowAddCalendarButton(false);
        celebCalendarView.setShowPrintButton(false);
        celebCalendarView.setShowPageToolBarControls(false);
        celebCalendarView.setShowPageSwitcher(false);
        celebCalendarView.setShowToolBar(false);
        celebCalendarView.setLayout(DateControl.Layout.SWIMLANE);
    }

    public CalendarView getCalendarView() {
        return celebCalendarView;
    }

    /**
     * Method to handle the event for changing calendar view. Changes to either day,
     * week, month or year view.
     * @param event
     */
    @Subscribe
    private void handleChangeCalendarViewPageRequestEvent(ChangeCalendarViewPageRequestEvent event) {
        celebCalendarView.getCalendarSources().clear();
        celebCalendarView.getCalendarSources().add(celebCalendarSource);
        String calendarViewPage = event.calendarViewPage;

        Platform.runLater(() -> {
            switch (calendarViewPage) {

            case DAY_VIEW_PAGE:
                celebCalendarView.getDayPage().setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
                celebCalendarView.showDayPage();
                break;
            case WEEK_VIEW_PAGE:
                celebCalendarView.showWeekPage();
                break;
            case MONTH_VIEW_PAGE:
                celebCalendarView.showMonthPage();
                break;

            default:
                try {
                    throw new ParseException(MESSAGE_UNKNOWN_CALENDAR_VIEW);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /** Shows the calendar of the specified {@code celebrity} */
    private void showCalendarOf(CelebCalendar celebCalendarToShow) {
        Platform.runLater(() -> {
            for (Calendar calendar: celebCalendarSource.getCalendars()) {
                if (calendar != celebCalendarToShow) {
                    celebCalendarView.getSourceView().setCalendarVisibility(calendar, false);
                } else {
                    celebCalendarView.getSourceView().setCalendarVisibility(calendar, true);
                }
            }
        });
    }

    @Subscribe
    private void handleChangeCalendarRequestEvent(ChangeCalendarRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showCalendarOf(event.celebCalendar);
    }

    /** Shows a combined calendar that contains {@code appointment}s for all {@code celebrity}s */
    private void showAllCalendars() {
        Platform.runLater(() -> {
            for (Calendar calendar: celebCalendarSource.getCalendars()) {
                celebCalendarView.getSourceView().setCalendarVisibility(calendar, true);
            }
        });
    }

    @Subscribe
    private void handleShowCombinedCalendarViewRequestEvent(ShowCombinedCalendarViewRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showAllCalendars();
    }

    private void showDate(LocalDate date) {
        Platform.runLater(() -> {
            celebCalendarView.setDate(date);
        });
    }

    /** Shows calendar with the specified date as its base */
    @Subscribe
    private void handleShowCalendarBasedOnDateEvent(ShowCalendarBasedOnDateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showDate(event.getDate());
    }
}
```
