# LeonidAgarth
###### \main\java\seedu\address\commons\events\ui\CalendarChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Person List Panel
 */
public class CalendarChangedEvent extends BaseEvent {

    public CalendarChangedEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \main\java\seedu\address\commons\events\ui\TimetableChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.WeeklyEvent;

/**
 * Represents a selection change in the Person List Panel
 */
public class TimetableChangedEvent extends BaseEvent {

    public final ObservableList<WeeklyEvent> timetable;

    public TimetableChangedEvent() {
        timetable = FXCollections.observableArrayList();
    }

    public TimetableChangedEvent(ObservableList<WeeklyEvent> newTimetable) {
        this.timetable = newTimetable;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \main\java\seedu\address\logic\commands\AddEventCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addEvent";
    public static final String COMMAND_ALIAS = "ae";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_VENUE + "VENUE "
            + PREFIX_DATE + "DATE "
            + PREFIX_START_TIME + "STARTTIME "
            + PREFIX_END_TIME + "ENDTIME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "F1 Race "
            + PREFIX_VENUE + "Marina Bay Street Circuit "
            + PREFIX_DATE + "19/07/2017 "
            + PREFIX_START_TIME + "1000 "
            + PREFIX_END_TIME + "1300\n"
            + "Note: DATE must be in the format of DD/MM/YYYY\n"
            + "      TIME must be in the format of HHmm\n";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";
    public static final String MESSAGE_END_BEFORE_START = "The event's ENDTIME must be after STARTTIME";

    private final Event toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addEvent(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateEventException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
```
###### \main\java\seedu\address\logic\commands\ChangeTagColorCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagNotFoundException;

/**
 * Edits the details of an existing person in the address book.
 */
public class ChangeTagColorCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "changeTagColor";
    public static final String COMMAND_ALIAS = "color";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the current color of the tag specified by name"
            + "\nParameters: TAGNAME (must be an existing tag) COLOR\n"
            + "Example: " + COMMAND_WORD + " friends red\n"
            + "Available colors are: teal, red, yellow, blue, orange, brown, green, pink, black, grey";

    public static final String MESSAGE_EDIT_TAG_SUCCESS = "Tag %1$s's color changed to %2$s";
    public static final String MESSAGE_TAG_NOT_IN_LIST = "The tag specified is not associated with any person";

    private final String tagName;
    private final String tagColor;

    private Tag tagToEdit;
    private Tag editedTag;

    /**
     * @param name  of the tag to edit
     * @param color to change the tag into
     */
    public ChangeTagColorCommand(String name, String color) {
        requireNonNull(name);
        requireNonNull(color);

        this.tagName = name;
        this.tagColor = color;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateTag(tagToEdit, editedTag);
        } catch (TagNotFoundException tnfe) {
            throw new CommandException(MESSAGE_TAG_NOT_IN_LIST);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_TAG_SUCCESS, tagName, tagColor));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        editedTag = new Tag(tagName, tagColor);
        List<Tag> allTags = model.getAddressBook().getTagList();
        for (Tag tag : allTags) {
            if (tag.name.equals(tagName)) {
                tagToEdit = tag;
                return;
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        if (!(other instanceof ChangeTagColorCommand)) {
            return false;
        }

        ChangeTagColorCommand e = (ChangeTagColorCommand) other;
        return tagName.equals(e.tagName)
                && tagColor.equals(e.tagColor);
    }
}
```
###### \main\java\seedu\address\logic\commands\ScheduleGroupCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.TimetableChangedEvent;
import seedu.address.database.DatabaseManager;
import seedu.address.database.module.Module;
import seedu.address.database.module.Schedule;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.WeeklyEvent;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Display the common free time slots of members in a group
 */
public class ScheduleGroupCommand extends Command {

    public static final String COMMAND_WORD = "scheduleGroup";
    public static final String COMMAND_ALIAS = "sG";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Display the common free time slots of members in a group.\n"
            + "Parameters: GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " CS2103T";

    public static final String MESSAGE_SUCCESS = "Common free time slots are displayed for group %1$s";
    public static final String MESSAGE_GROUP_NOT_FOUND = "There is no group named %1$s.";

    private final Group toShow;
    private final ArrayList<WeeklyEvent> occupied;
    private final ArrayList<WeeklyEvent> free;

    /**
     * Creates an ScheduleGroupCommand to schedule the specified {@code Group}
     */
    public ScheduleGroupCommand(Group group) {
        requireNonNull(group);
        toShow = group;
        occupied = new ArrayList<>();
        free = new ArrayList<>();
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        Group groupToShow = new Group(toShow.getInformation());
        boolean groupNotFound = true;
        for (Group group : model.getFilteredGroupList()) {
            if (toShow.getInformation().equals(group.getInformation())) {
                groupToShow = group;
                groupNotFound = false;
                break;
            }
        }
        if (groupNotFound) {
            throw new CommandException(String.format(MESSAGE_GROUP_NOT_FOUND, toShow.getInformation()));
        }
        fillTimeSlots(groupToShow);
        generateFreeTimeSlots();
        EventsCenter.getInstance().post(new TimetableChangedEvent(FXCollections.observableArrayList(free)));
        return new CommandResult(String.format(MESSAGE_SUCCESS, groupToShow.getInformation()));
    }

    /**
     * Populate the {@code occupied} list to include all modules from all members from {@code groupToShow}
     */
    private void fillTimeSlots(Group groupToShow) {
        for (Person member : groupToShow.getPersonList()) {
            ArrayList<WeeklyEvent> moduleList = DatabaseManager.getInstance().parseEvents(member.getTimeTableLink());
            occupied.addAll(moduleList);
        }
    }

    /**
     * Generate all common free time slots according to the {@code occupied} list and store it in {@code free}
     */
    private void generateFreeTimeSlots() {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (String day : daysOfWeek) {
            for (int s = 800; s < 1800; s += 100) {
                Module mod = new Module("Free", "", null);
                Schedule sch = new Schedule("", "", "", day, "" + s, "" + (s + 100), "");
                WeeklyEvent freeTimeSlot = new WeeklyEvent(mod, sch);
                if (!moduleClash(freeTimeSlot)) {
                    free.add(freeTimeSlot);
                }
            }
        }
    }

    /**
     * @return true if the {@code timeSlot} clashes with any mod in {@code occupied}
     */
    private boolean moduleClash(WeeklyEvent timeSlot) {
        for (WeeklyEvent mod : occupied) {
            if (mod.clash(timeSlot)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleGroupCommand // instanceof handles nulls
                && toShow.equals(((ScheduleGroupCommand) other).toShow));
    }
}
```
###### \main\java\seedu\address\logic\commands\SwitchCommand.java
``` java
package seedu.address.logic.commands;

/**
 * Switches between Calendar view and Timetable view
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";
    public static final String COMMAND_ALIAS = "sw";

    public static final String MESSAGE_SUCCESS_CALENDAR = "View is switched to calendar";
    public static final String MESSAGE_SUCCESS_TIMETABLE = "View is switched to timetable";


    @Override
    public CommandResult execute() {
        if (model.calendarIsViewed()) {
            model.indicateTimetableChanged();
            return new CommandResult(MESSAGE_SUCCESS_TIMETABLE);
        }
        model.indicateCalendarChanged();
        return new CommandResult(MESSAGE_SUCCESS_CALENDAR);
    }
}
```
###### \main\java\seedu\address\logic\parser\AddEventCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_VENUE, PREFIX_DATE,
                        PREFIX_START_TIME, PREFIX_END_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_VENUE, PREFIX_DATE,
                PREFIX_START_TIME, PREFIX_END_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        try {
            String name = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_NAME).get());
            String venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get());
            String date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            String startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
            String endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME).get());

            if (Integer.parseInt(startTime) > Integer.parseInt(endTime)) {
                throw new ParseException(AddEventCommand.MESSAGE_END_BEFORE_START);
            }

            Event event = new Event(name, venue, date, startTime, endTime);

            return new AddEventCommand(event);
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
###### \main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ChangeTagColorCommand.COMMAND_WORD:
        case ChangeTagColorCommand.COMMAND_ALIAS:
            return new ChangeTagColorCommandParser().parse(arguments);

        case AddEventCommand.COMMAND_WORD:
        case AddEventCommand.COMMAND_ALIAS:
            return new AddEventCommandParser().parse(arguments);

        case SwitchCommand.COMMAND_WORD:
        case SwitchCommand.COMMAND_ALIAS:
            return new SwitchCommand();

        case ScheduleGroupCommand.COMMAND_WORD:
        case ScheduleGroupCommand.COMMAND_ALIAS:
            return new ScheduleGroupCommandParser().parse(arguments);
```
###### \main\java\seedu\address\logic\parser\ChangeTagColorCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses the given {@code String} of arguments in the context of the ChangeTagColorCommand
 * and returns an ChangeTagColorCommand object for execution.
 *
 * @throws ParseException if the user input does not conform the expected format
 */
public class ChangeTagColorCommandParser implements Parser<ChangeTagColorCommand> {

    @Override
    public ChangeTagColorCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim().replaceAll(" +", " ");
        String[] args = trimmedInput.split(" ");
        if (args.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeTagColorCommand.MESSAGE_USAGE));
        }
        try {
            Tag tag = ParserUtil.parseTag(args[0]);
            String color = ParserUtil.parseColor((args[1]));
            return new ChangeTagColorCommand(tag.name, color);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \main\java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code phone} is invalid.
     */
    public static String parseColor(String color) throws IllegalValueException {
        requireNonNull(color);
        if (Tag.isValidTagColor(color)) {
            return color;
        } else {
            throw new IllegalValueException(Tag.MESSAGE_TAG_COLOR_CONSTRAINTS);
        }
    }


    /**
     * Parses a {@code String name} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static String parseEventName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Event.isValidName(trimmedName)) {
            throw new IllegalValueException(Event.MESSAGE_NAME_CONSTRAINTS);
        }
        return trimmedName;
    }

    /**
     * Parses a {@code String venue} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code venue} is invalid.
     */
    public static String parseVenue(String venue) throws IllegalValueException {
        requireNonNull(venue);
        String trimmedVenue = venue.trim();
        if (!Event.isValidName(trimmedVenue)) {
            throw new IllegalValueException(Event.MESSAGE_VENUE_CONSTRAINTS);
        }
        return trimmedVenue;
    }

    /**
     * Parses a {@code String date} into a {@code String}.
     * Date must follow DD/MM/YYYY format
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static String parseDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Event.isValidDate(trimmedDate)) {
            throw new IllegalValueException(Event.MESSAGE_DATE_CONSTRAINTS);
        }
        return trimmedDate;
    }

    /**
     * Parses a {@code String time} into a {@code String}.
     * Time must follow HHmm format
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static String parseTime(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Event.isValidTime(trimmedTime)) {
            throw new IllegalValueException(Event.MESSAGE_TIME_CONSTRAINTS);
        }
        return trimmedTime;
    }
}
```
###### \main\java\seedu\address\logic\parser\ScheduleGroupCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ScheduleGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;

/**
 * Parses input arguments and creates a new ScheduleGroupCommand object
 */
public class ScheduleGroupCommandParser implements Parser<ScheduleGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleGroupCommand
     * and returns an ScheduleGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleGroupCommand parse(String args) throws ParseException {
        try {
            Information information = ParserUtil.parseInformation(args);
            Group group = new Group(information);
            return new ScheduleGroupCommand(group);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleGroupCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \main\java\seedu\address\model\AddressBook.java
``` java
    /**
     * Replaces the old {@code target} tag with the new {@code editedTag}
     */
    public void editTag(Tag target, Tag editedTag) throws TagNotFoundException {
        Set<Tag> editedTagList = tags.toSet();
        if (editedTagList.contains(target)) {
            editedTagList.remove(target);
            editedTagList.add(editedTag);
            tags.setTags(editedTagList);
        } else {
            throw new TagNotFoundException();
        }
        for (Person p : persons) {
            replaceTagInPerson(target, editedTag, p);
        }
    }

    /**
     * Replaces the old {@code target} tag of a {@code person} with the new {@code editedTag}
     */
    private void replaceTagInPerson(Tag target, Tag editedTag, Person person) {
        Set<Tag> tagList = new HashSet<>(person.getTags());

        //Terminate if tag is not is tagList
        if (!tagList.remove(target)) {
            return;
        }
        tagList.add(editedTag);
        Person updatedPerson = new Person(person.getName(), person.getPhone(),
                person.getEmail(), person.getAddress(), person.getTimeTableLink(), person.getDetail(), tagList);

        try {
            updatePerson(person, updatedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Modifying a person's tags only should not result in a duplicate. "
                    + "See Person#equals(Object).");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Modifying a person's tags only should not result in "
                    + "a PersonNotFoundException. See Person#equals(Object).");
        }
    }
```
###### \main\java\seedu\address\model\event\Event.java
``` java
package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an event related to a person
 */
public class Event {
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Event names should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String MESSAGE_VENUE_CONSTRAINTS =
            "Venues should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String MESSAGE_DATE_CONSTRAINTS = "DATE must be a valid date in the format of DD/MM/YYYY";
    public static final String MESSAGE_TIME_CONSTRAINTS = "TIME must be a valid time in the format of HHmm";

    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String DATE_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/"
            + "|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9"
            + "]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\"
            + "d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public static final String TIME_VALIDATION_REGEX = "^(0[0-9]|1[0-9]|2[0-4])[0-5][0-9]";

    protected String name;
    protected String venue;
    protected String date;
    protected String startTime;
    protected String endTime;

    /**
     * Default constructor, creating a blank Event.
     */
    public Event() {
        this("blank", "blank", "19/07/2017", "0000", "2359");
    }

    /**
     * Every field must be present and not null
     */
    public Event(String name, String venue, String date, String start, String end) {
        requireAllNonNull(name, start, end);
        this.name = name;
        this.venue = venue;
        this.date = date;
        this.startTime = start;
        this.endTime = end;
    }

    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(this.getName())
                && otherEvent.getVenue().equals(this.getVenue())
                && otherEvent.getDate().equals(this.getDate())
                && otherEvent.getStartTime().equals(this.getStartTime())
                && otherEvent.getEndTime().equals(this.getEndTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, venue, date, startTime, endTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Venue: ")
                .append(getVenue())
                .append(" Date: ")
                .append(getDate())
                .append(" Start time: ")
                .append(getStartTime())
                .append(" End time: ")
                .append(getEndTime());
        return builder.toString();
    }

    public String getName() {
        return name;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
```
###### \main\java\seedu\address\model\event\exceptions\DuplicateEventException.java
``` java
package seedu.address.model.event.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Event objects.
 */
public class DuplicateEventException extends DuplicateDataException {
    public DuplicateEventException() {
        super("Operation would result in duplicate events");
    }
}
```
###### \main\java\seedu\address\model\event\exceptions\EventNotFoundException.java
``` java
package seedu.address.model.event.exceptions;

/**
 * Signals that the operation is unable to find the specified event.
 */
public class EventNotFoundException extends Exception {
}
```
###### \main\java\seedu\address\model\event\UniqueEventList.java
``` java
package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;

/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Event#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a event to the list.
     *
     * @throws DuplicateEventException if the event to add is a duplicate of an existing event in the list.
     */
    public void add(Event toAdd) throws DuplicateEventException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the event {@code target} in the list with {@code editedEvent}.
     *
     * @throws DuplicateEventException if the replacement is equivalent to another existing event in the list.
     * @throws EventNotFoundException  if {@code target} could not be found in the list.
     */
    public void setEvent(Event target, Event editedEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireNonNull(editedEvent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        if (!target.equals(editedEvent) && internalList.contains(editedEvent)) {
            throw new DuplicateEventException();
        }

        internalList.set(index, editedEvent);
    }

    /**
     * Removes the equivalent event from the list.
     *
     * @throws EventNotFoundException if no such event could be found in the list.
     */
    public boolean removeEvent(Event toRemove) throws EventNotFoundException {
        requireNonNull(toRemove);
        final boolean eventFoundAndDeleted = internalList.remove(toRemove);
        if (!eventFoundAndDeleted) {
            throw new EventNotFoundException();
        }
        return eventFoundAndDeleted;
    }

    public void setEvents(UniqueEventList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setEvents(List<Event> events) throws DuplicateEventException {
        requireAllNonNull(events);
        final UniqueEventList replacement = new UniqueEventList();
        for (final Event event : events) {
            replacement.add(event);
        }
        setEvents(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEventList // instanceof handles nulls
                && this.internalList.equals(((UniqueEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \main\java\seedu\address\model\event\WeeklyEvent.java
``` java
package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.database.module.Module;
import seedu.address.database.module.Schedule;

/**
 * Events, such as lectures, tutorial slots, to appear in timetable
 */
public class WeeklyEvent extends Event {
    private String day;
    private String[] details;

    public WeeklyEvent(String name, String venue, String start, String end, String... details) {
        super(name, venue, "NA", start, end);
        this.day = details[0];          //Placeholder command
        this.details = details;
    }

    public WeeklyEvent(Module mod, Schedule schedule) {
        requireAllNonNull(mod, schedule);
        this.name = mod.getModuleCode();
        this.venue = schedule.getVenue();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.day = schedule.getDayText();
        this.details = new String[]{schedule.getLessonType() + ' ' + schedule.getClassNo(), mod.getModuleTitle()};
    }

    public String getDay() {
        return day;
    }

    public ObservableList<String> getDetails() {
        ArrayList<String> temp = new ArrayList<String>(Arrays.asList(details));
        return FXCollections.observableArrayList(temp);
    }

    /**
     * @return true if {@code this} clashes with the {@code mod}, false otherwise
     */
    public boolean clash(WeeklyEvent mod) {
        return clash(mod.getDay(), mod.getStartTime(), mod.getEndTime());
    }

    /**
     * @return true if {@code this} is on {@code dayOfWeek},
     * around the time from {@code start} to {@code end}, false otherwise
     */
    public boolean clash(String dayOfWeek, String start, String end) {
        if (!day.equals(dayOfWeek)) {
            return false;
        }
        if (Integer.parseInt(start) >= Integer.parseInt(endTime)) {
            return false;
        }
        return Integer.parseInt(end) > Integer.parseInt(startTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof WeeklyEvent)) {
            return false;
        }

        WeeklyEvent otherEvent = (WeeklyEvent) other;
        return otherEvent.getName().equals(this.getName())
                && otherEvent.getVenue().equals(this.getVenue())
                && otherEvent.getDate().equals(this.getDate())
                && otherEvent.getStartTime().equals(this.getStartTime())
                && otherEvent.getEndTime().equals(this.getEndTime())
                && otherEvent.getDay().equals(this.getDay())
                && otherEvent.getDetails().equals(this.getDetails());
    }
}
```
###### \main\java\seedu\address\model\Model.java
``` java
    /** Checks whether application is in Calendar or Timetable view */
    boolean calendarIsViewed();

    /** Switches between Calendar and Timetable view */
    void switchView();

    /** Raises an event to indicate the calendar has changed */
    void indicateCalendarChanged();

    /** Raises an event to indicate the timetable has changed */
    void indicateTimetableChanged();
}
```
###### \main\java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void indicateCalendarChanged() {
        raise(new CalendarChangedEvent());
    }

    @Override
    public void indicateTimetableChanged() {
        raise(new TimetableChangedEvent());
    }

```
###### \main\java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addEvent(Event event) throws DuplicateEventException {
        addressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateAddressBookChanged();
    }

    @Override
    public boolean calendarIsViewed() {
        return inCalendarView;
    }

    @Override
    public void switchView() {
        inCalendarView = !inCalendarView;
    }
```
###### \main\java\seedu\address\model\ModelManager.java
``` java
    //=========== Filtered Event List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }
```
###### \main\java\seedu\address\model\ModelManager.java
``` java
    @Subscribe
    private void handleCalendarChangedEvent(CalendarChangedEvent event) {
        inCalendarView = true;
    }

    @Subscribe
    private void handleTimetableChangedEvent(TimetableChangedEvent event) {
        inCalendarView = false;
    }
}
```
###### \main\java\seedu\address\model\tag\Tag.java
``` java
    public static final String MESSAGE_TAG_COLOR_CONSTRAINTS = "Colors available are: aqua, black, blue, brown, gold, "
            + "green, grey, lime, magenta, navy, orange, pink, purple, red, teal, yellow, white";
    public static final String[] AVAILABLE_COLORS = new String[] {"teal", "red", "yellow", "blue", "orange", "brown",
        "green", "pink", "black", "grey", "purple", "lime", "magenta", "navy", "aqua", "gold", "white", "undefined"};

    public final String name;
    public final String color;

    /**
     * Constructs a {@code Tag}.
     *
     * @param name A valid tag name.
     */
    public Tag(String name) {
        requireNonNull(name);
        checkArgument(isValidTagName(name), MESSAGE_TAG_CONSTRAINTS);
        this.name = name;
        this.color = "undefined";
    }

    public Tag(String name, String color) {
        requireNonNull(name);
        checkArgument(isValidTagName(name), MESSAGE_TAG_CONSTRAINTS);
        checkArgument(isValidTagColor(color), MESSAGE_TAG_COLOR_CONSTRAINTS);
        this.name = name;
        this.color = color;
    }

    /**
     * Returns true if a given string is a available tag color
     */
    public static boolean isValidTagColor(String color) {
        String trimmedColor = color.trim().toLowerCase();
        for (String s : AVAILABLE_COLORS) {
            if (s.equals(trimmedColor)) {
                return true;
            }
        }
        return false;
    }

```
###### \main\java\seedu\address\storage\AddressBookStorage.java
``` java
    /**
     * Returns AddressBook data that has been backed up as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readAddressBookBackup() throws DataConversionException, IOException;
    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyAddressBook> readAddressBookBackup(String filePath) throws DataConversionException, IOException;

    /**
     * Backup the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
    /**
     * @see #backupAddressBook(ReadOnlyAddressBook)
     */
    void backupAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException;

```
###### \main\java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public Optional<ReadOnlyAddressBook> readAddressBookBackup() throws DataConversionException, IOException {
        return readAddressBookBackup(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBookBackup(String filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from backup: " + filePath);
        return addressBookStorage.readAddressBookBackup(filePath);
    }

```
###### \main\java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        addressBookStorage.backupAddressBook(addressBook);
    }

    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        addressBookStorage.backupAddressBook(addressBook, filePath);
    }

```
###### \main\java\seedu\address\storage\XmlAdaptedEvent.java
``` java
package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String venue;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    /**
     * Constructs an {@code XmlAdaptedEvent} with the given event details.
     */
    public XmlAdaptedEvent(String name, String venue, String date, String startTime, String endTime) {
        this.name = name;
        this.venue = venue;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(Event source) {
        name = source.getName();
        venue = source.getVenue();
        date = source.getDate();
        startTime = source.getStartTime();
        endTime = source.getEndTime();
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name"));
        }
        if (!Event.isValidName(this.name)) {
            throw new IllegalValueException(Event.MESSAGE_NAME_CONSTRAINTS);
        }
        if (this.venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Venue"));
        }
        if (!Event.isValidName(this.venue)) {
            throw new IllegalValueException(Event.MESSAGE_VENUE_CONSTRAINTS);
        }
        if (this.date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date"));
        }
        if (!Event.isValidDate(this.date)) {
            throw new IllegalValueException(Event.MESSAGE_DATE_CONSTRAINTS);
        }
        if (this.startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "StartTime"));
        }
        if (!Event.isValidTime(this.startTime)) {
            throw new IllegalValueException(Event.MESSAGE_TIME_CONSTRAINTS);
        }
        if (this.endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "EndTime"));
        }
        if (!Event.isValidTime(this.endTime)) {
            throw new IllegalValueException(Event.MESSAGE_TIME_CONSTRAINTS);
        }

        return new Event(name, venue, date, startTime, endTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedEvent)) {
            return false;
        }

        XmlAdaptedEvent otherEvent = (XmlAdaptedEvent) other;
        return Objects.equals(name, otherEvent.name)
                && Objects.equals(venue, otherEvent.venue)
                && Objects.equals(date, otherEvent.date)
                && Objects.equals(startTime, otherEvent.startTime)
                && Objects.equals(endTime, otherEvent.endTime);
    }
}
```
###### \main\java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public Optional<ReadOnlyAddressBook> readAddressBookBackup() throws DataConversionException, IOException {
        return readAddressBook(filePath + ".backup");
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBookBackup(String filePath) throws DataConversionException,
                                                                                       IOException {
        if (Objects.isNull(filePath)) {
            throw new NullPointerException();
        } else {
            return readAddressBook(filePath + ".backup");
        }
    }

    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        if (Objects.isNull(filePath)) {
            throw new NullPointerException();
        } else {
            saveAddressBook(addressBook, filePath + ".backup");
        }
    }

    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        if (Objects.isNull(filePath)) {
            throw new NullPointerException();
        } else {
            saveAddressBook(addressBook, filePath + ".backup");
        }
    }

```
###### \main\java\seedu\address\ui\Calendar.java
``` java
package seedu.address.ui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;

/**
 * The Calendar of the App.
 * <p>
 * Adapted from javafx-calendar by SirGoose3432
 * URL: https://github.com/SirGoose3432/javafx-calendar
 */
public class Calendar extends UiPart<Region> {

    private static final String FXML = "Calendar.fxml";

    private ArrayList<CalendarDate> allCalendarDays = new ArrayList<>(42);
    private VBox calendarView;
    private Text calendarHeader;
    private YearMonth currentYearMonth;
    private ObservableList<Event> events;

    @FXML
    private ListView<CalendarDate> calendarListView;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public Calendar(ObservableList<Event> eventList) {
        this(YearMonth.now(), eventList);
    }

    public Calendar(YearMonth yearMonth, ObservableList<Event> eventList) {
        super(FXML);
        currentYearMonth = yearMonth;
        events = eventList;
        initCalendar();

        registerAsAnEventHandler(this);
    }

    /**
     * Create the calendarView for the calendar
     */
    private void initCalendar() {
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(1400, 600);
        calendar.setMaxWidth(1400);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                CalendarDate apn = new CalendarDate();
                calendar.add(apn.getBox(), j, i);
                allCalendarDays.add(apn);
            }
        }
        // Days of the week
        Text[] dayNames = new Text[]{new Text("Mon"), new Text("Tue"), new Text("Wed"),
            new Text("Thu"), new Text("Fri"), new Text("Sat"), new Text("Sun")};
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        dayLabels.setMaxWidth(1120);
        dayLabels.setGridLinesVisible(true);
        int col = 0;
        for (Text txt : dayNames) {
            txt.getStyleClass().add("dayName");
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 20);
            AnchorPane.setLeftAnchor(txt, 5.0);
            AnchorPane.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            if (txt.getText().equals("Sun") || txt.getText().equals("Sat")) {
                ap.getStyleClass().add("weekend");
            } else {
                ap.getStyleClass().add("weekday");
            }
            dayLabels.add(ap, col++, 0);
        }
        // Create calendarHeader and navigation
        calendarHeader = new Text();
        calendarHeader.getStyleClass().add("yearMonth");
        Button previousMonth = new Button("<<");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">>");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, new Text("\t"), calendarHeader, new Text("\t"), nextMonth);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        fillCalendar(currentYearMonth);
        showEvents();
        // Create the calendar calendarView
        calendarView = new VBox(titleBar, dayLabels, calendar);
    }

    /**
     * Set the visible days on the calendar to the appropriate {@code yearMonth}
     */
    private void fillCalendar(YearMonth yearMonth) {
        // Get the month and year to display
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        //Roll the day back to MONDAY in order to fill up the whole calendar
        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Fill the calendar
        for (CalendarDate date : allCalendarDays) {
            date.setDate(calendarDate, yearMonth);
            calendarDate = calendarDate.plusDays(1);
            date.setEventText("");
        }
        // Change the header of the calendar
        calendarHeader.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Show all events in eventList onto Calendar
     */
    private void showEvents() {
        for (Event e : events) {
            String[] dayMonthYear = e.getDate().split("/");
            int day = Integer.parseInt(dayMonthYear[0]);
            int month = Integer.parseInt(dayMonthYear[1]);
            int year = Integer.parseInt(dayMonthYear[2]);
            if (month != currentYearMonth.getMonthValue() || year != currentYearMonth.getYear()) {
                continue;
            }
            LocalDate date = LocalDate.of(year, month, day);
            CalendarDate node = getDateNode(date);
            node.setEventText(e.getName());
        }
    }

    /**
     * Move back 1 month, then refill the calendar.
     */
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        fillCalendar(currentYearMonth);
        showEvents();
    }

    /**
     * Move forward 1 month, then refill the calendar.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        fillCalendar(currentYearMonth);
        showEvents();
    }

    /**
     * Return the Calendar to view in application
     */
    public VBox getCalendarView() {
        return calendarView;
    }

    public CalendarDate getDateNode(LocalDate date) {
        LocalDate firstDay = LocalDate.of(currentYearMonth.getYear(), currentYearMonth.getMonthValue(), 1);
        int firstDayIndex = firstDay.getDayOfWeek().getValue() - 1;
        int gap = date.getDayOfMonth() - firstDay.getDayOfMonth();
        return allCalendarDays.get(firstDayIndex + gap);
    }
}
```
###### \main\java\seedu\address\ui\CalendarDate.java
``` java
package seedu.address.ui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.model.event.Event;

/**
 * Node to display each cell/day of the calendar
 */
public class CalendarDate extends UiPart<Region> {

    private static final String FXML = "CalendarDate.fxml";

    public final LocalDate date;

    @FXML
    private GridPane box;
    @FXML
    private VBox node;
    @FXML
    private Text day;
    @FXML
    private Text eventText;

    /**
     * Create a calendar date for today on the calendar
     */
    public CalendarDate() {
        super(FXML);
        this.date = LocalDate.now();
        initStyle();
    }

    /**
     * Create a calendar date for an event on the calendar
     */
    public CalendarDate(Event event) {
        super(FXML);
        this.date = LocalDate.parse(event.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        initStyle();
        eventText.setText(event.getName());
    }

    /**
     * Initialize the style of the date node
     */
    private void initStyle() {
        //node = new VBox();
        node.setPrefSize(200, 50);
        day.setText("" + date.getDayOfMonth());
        setEventText("");
    }

    /**
     * Set the date of this node to the specified {@code date}
     */
    public void setDate(LocalDate date, YearMonth currentYearMonth) {
        day.setText("" + date.getDayOfMonth());
        if (date.equals(LocalDate.now())) {
            setStyleClass(node, "weekend");
        } else {
            setStyleClass(node, "date");
        }
        if (date.getMonth().equals(currentYearMonth.getMonth())) {
            setStyleClass(day, "thisMonthDate");
        } else {
            setStyleClass(day, "notThisMonthDate");
        }
    }

    private void setStyleClass(Node node, String... styles) {
        ObservableList<String> styleClass = node.getStyleClass();
        styleClass.clear();
        for (String style : styles) {
            styleClass.add(style);
        }
    }

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public GridPane getBox() {
        return box;
    }

    public VBox getNode() {
        return node;
    }

    public Text getDay() {
        return day;
    }

    public Text getEventText() {
        return eventText;
    }

    public void setEventText(String text) {
        if (text.length() > 1) {
            eventText.setText("- " + text);
        } else {
            eventText.setText(text);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CalendarDate)) {
            return false;
        }

        // state check
        CalendarDate theOther = (CalendarDate) other;
        return date.equals(theOther.date) && eventText.getText().equals(theOther.getEventText().getText());
    }
}
```
###### \main\java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Clears the old calendar and display an updated one
     */
    void redisplayCalendar() {
        calendarPlaceholder.getChildren().clear();
        calendar = new Calendar(logic.getFilteredEventList());
        calendarPlaceholder.getChildren().add(calendar.getCalendarView());
    }

    /**
     * Clears the old timetable and display an updated one
     */
    void redisplayTimetable(ObservableList<WeeklyEvent> modules) {
        calendarPlaceholder.getChildren().clear();
        timetable = new Timetable(modules);
        calendarPlaceholder.getChildren().add(timetable.getTimetableView());
    }

```
###### \main\java\seedu\address\ui\Timetable.java
``` java
package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.WeeklyEvent;

/**
 * The weekly Timetable of the App.
 */
public class Timetable extends UiPart<Region> {

    private static final String FXML = "Timetable.fxml";
    private static final int MAX_WIDTH = 1100;

    private ArrayList<TimetableSlot> allTimetableSlots = new ArrayList<>(72);
    private VBox timetableView;
    private Text timetableHeader;
    private ObservableList<WeeklyEvent> events;

    @FXML
    private ListView<TimetableSlot> timetableListView;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public Timetable() {
        this(FXCollections.observableArrayList());
    }

    public Timetable(ObservableList<WeeklyEvent> eventList) {
        super(FXML);
        events = eventList;
        initTimetable();

        registerAsAnEventHandler(this);
    }

    /**
     * Create the timetableView for the timetable
     */
    private void initTimetable() {
        // Create the timetable grid pane
        GridPane timetable = new GridPane();
        timetable.setPrefSize(MAX_WIDTH, 1000);
        timetable.setMaxWidth(MAX_WIDTH);
        timetable.setGridLinesVisible(false);
        // Create rows and columns with anchor panes for the timetable
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                TimetableSlot apn = new TimetableSlot();
                timetable.add(apn.getBox(), j, i);
                allTimetableSlots.add(apn);
            }
        }
        // Slots of the week
        Text[] dayNames = new Text[]{new Text(""), new Text("Mon"), new Text("Tue"),
            new Text("Wed"), new Text("Thu"), new Text("Fri")};
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        dayLabels.setMaxWidth(MAX_WIDTH);
        dayLabels.setGridLinesVisible(false);
        int col = 0;
        for (Text txt : dayNames) {
            txt.getStyleClass().add("dayName");
            HBox box = new HBox(txt);
            box.setPrefSize(200, 20);
            box.setMaxWidth(MAX_WIDTH);
            box.setAlignment(Pos.BASELINE_CENTER);
            if (col == 0) {
                box.getStyleClass().add("timecell");
            } else {
                box.getStyleClass().add("weekday");
            }
            dayLabels.add(box, col++, 0);
        }
        // Create timetableHeader and navigation
        timetableHeader = new Text();
        timetableHeader.setText("Timetable");
        timetableHeader.getStyleClass().add("yearMonth");
        HBox titleBar = new HBox(timetableHeader);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate timetable with the appropriate day numbers
        clearTimetable();
        showSlots();
        // Create the timetable timetableView
        timetableView = new VBox(titleBar, dayLabels, timetable);
    }

    /**
     * Clear the timetable to blank
     */
    private void clearTimetable() {
        // Fill the timetable
        boolean white = true;
        for (TimetableSlot slot : allTimetableSlots) {
            if (white) {
                slot.initStyle("blank1");
            } else {
                slot.initStyle("blank2");
            }
            white = !white;
        }
        for (int hour = 800; hour <= 1800; hour += 100) {
            TimetableSlot node = getSlotNode("time", hour);
            node.getNode().setPadding(new Insets(-7, 15, 5, 5));
            node.getNode().setAlignment(Pos.TOP_RIGHT);
            node.getBox().setMinWidth(50);
            if (hour < 1000) {
                node.setText("0" + hour + "");
            } else {
                node.setText(hour + "");
            }
        }
    }

    /**
     * Show all events in eventList onto Timetable
     */
    private void showSlots() {
        HashMap<Integer, String> usedColor = new HashMap<>();
        for (WeeklyEvent mod : events) {
            String day = mod.getDay();
            int startTime = Integer.parseInt(mod.getStartTime());
            int endTime = Integer.parseInt(mod.getEndTime());
            //Right now, app doesn't support modules starting from 6pm onwards
            if (startTime > 1700) {
                continue;
            }
            if (endTime - startTime <= 100) {
                TimetableSlot node = getSlotNode(day, startTime);
                int color = setUnusedColor(usedColor, node, mod, "module1hr");
            } else {
                WeeklyEvent blank = new WeeklyEvent(mod.getName(), "", "", "", "", "");
                TimetableSlot node = getSlotNode(day, startTime);
                int color = setUnusedColor(usedColor, node, blank, "module2hrtop");

                node = getSlotNode(day, startTime + 100);
                node.setModule("module2hrbottom", mod);
                node.getModule().setText("");
                node.setColor(color);
            }
        }
    }

    /**
     * Ensure that every {@code mod} displayed on the timetable has a unique color
     *
     * @param used usedColor HashMap to determine which color has been used
     * @param node the node on the timetable to display the mod on
     * @param modStyle the style of the mod
     * @return
     */
    private int setUnusedColor(HashMap<Integer, String> used, TimetableSlot node, WeeklyEvent mod, String modStyle) {
        int color = node.setModule(modStyle, mod);
        if (used.containsValue(mod.getName())) {
            for (Integer k : used.keySet()) {
                if (used.get(k).equals(mod.getName())) {
                    color = k;
                    node.setColor(color);
                    return color;
                }
            }
        }
        if (!used.containsKey(color)) {
            used.put(color, mod.getName());
            return color;
        }
        String module = used.get(color);
        if (mod.getName().equals(module)) {
            return color;
        }
        while (used.containsKey(color)) {
            color = node.randomizeColor(modStyle);
        }
        used.put(color, mod.getName());
        return color;
    }

    /**
     * Return the Timetable to view in application
     */
    public VBox getTimetableView() {
        return timetableView;
    }

    /**
     * Return the list of all visible day in the current month
     */
    public ArrayList<TimetableSlot> getAllTimetableSlots() {
        return allTimetableSlots;
    }

    /**
     * Set all currently visible days to {@code allTimetableSlots}
     */
    public void setAllTimetableSlots(ArrayList<TimetableSlot> allTimetableSlots) {
        this.allTimetableSlots = allTimetableSlots;
    }

    public TimetableSlot getSlotNode(String day, int hour) {
        int column = 0;
        switch (day) {
        case "Monday":
            column = 1;
            break;
        case "Tuesday":
            column = 2;
            break;
        case "Wednesday":
            column = 3;
            break;
        case "Thursday":
            column = 4;
            break;
        case "Friday":
            column = 5;
            break;
        default:
        }
        int row = hour / 100 - 8;
        return allTimetableSlots.get(row * 6 + column);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Timetable)) {
            return false;
        }

        // state check
        Timetable theOther = (Timetable) other;
        return allTimetableSlots.equals(theOther.getAllTimetableSlots());
    }
}
```
###### \main\java\seedu\address\ui\TimetableSlot.java
``` java
package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.model.event.WeeklyEvent;

/**
 * Node to display each slot of the Timetable
 */
public class TimetableSlot extends UiPart<Region> {

    private static final String FXML = "TimetableSlot.fxml";

    private static final String[] AVAILABLE_COLORS = new String[] {"red", "orange", "yellow", "blue", "teal",
        "green", "purple", "pink", "brown"};

    @FXML
    private GridPane box;
    @FXML
    private VBox node;
    @FXML
    private Text module;
    @FXML
    private Text lectureType;
    @FXML
    private Text venue;

    /**
     * Create a anchor pane node containing all of the {@code children}.
     * The Slot of the node is not set in the constructor
     */
    public TimetableSlot() {
        super(FXML);
        initStyle("blank1");
    }

    /**
     * Initialize the style of the Slot node
     */
    public void initStyle(String style) {
        node.setPrefSize(250, 60);
        setStyleClass(style);
        module.setText("");
        lectureType.setText("");
        venue.setText("");
    }

    public void setStyleClass(String... styles) {
        ObservableList<String> styleClass = node.getStyleClass();
        styleClass.clear();
        for (String style : styles) {
            styleClass.add(style);
        }
    }

    public GridPane getBox() {
        return box;
    }

    public VBox getNode() {
        return node;
    }

    public Text getModule() {
        return module;
    }

    /**
     * Set the node to contain the {@code mod}
     * with a specified {@code style} and a random color
     *
     * @return index of the color chosen
     */
    public int setModule(String style, WeeklyEvent mod) {
        int colorIndex = Math.abs(mod.getName().hashCode()) % AVAILABLE_COLORS.length;
        setStyleClass(style, AVAILABLE_COLORS[colorIndex]);
        module.setText(mod.getName());
        if (!mod.getDetails().isEmpty() && mod.getDetails().size() >= 2) {
            lectureType.setText(mod.getDetails().get(0));
        }
        venue.setText(mod.getVenue());
        return colorIndex;
    }

    /**
     * Randomly change the color of the node while keeping the {@code style}
     *
     * @return index of the color randomized
     */
    public int randomizeColor(String style) {
        int randomColor = (int) Math.floor(Math.random() * AVAILABLE_COLORS.length);
        setStyleClass(style, AVAILABLE_COLORS[randomColor]);
        return randomColor;
    }

    /**
     * Set the node to have a color specified at {@code colorIndex}
     */
    public void setColor(int colorIndex) {
        node.getStyleClass().set(1, AVAILABLE_COLORS[colorIndex]);
    }


    public void setText(String text) {
        setStyleClass("time");
        module.setText(text);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TimetableSlot)) {
            return false;
        }

        // state check
        TimetableSlot theOther = (TimetableSlot) other;
        return node.equals(theOther.node);
    }
}
```
###### \main\java\seedu\address\ui\UiManager.java
``` java
    @Subscribe
    private void handleCalendarChangedEvent(CalendarChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.redisplayCalendar();
    }

    @Subscribe
    private void handleTimetableChangedEvent(TimetableChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.redisplayTimetable(event.timetable);
    }
}
```
###### \main\resources\view\Calendar.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="calendarListView" VBox.vgrow="NEVER" HBox.hgrow="NEVER"/>
</VBox>
```
###### \main\resources\view\CalendarDate.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Text?>
<AnchorPane fx:id="top" maxHeight="-Infinity" maxWidth="200.0" minHeight="-Infinity" minWidth="-Infinity" prefHeight="200.0" prefWidth="200.0" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
   <GridPane fx:id="box" maxWidth="200.0" HBox.hgrow="NEVER">
      <columnConstraints>
         <ColumnConstraints minWidth="10" prefWidth="160" />
      </columnConstraints>
      <VBox fx:id="node" alignment="CENTER_LEFT" minHeight="80" GridPane.columnIndex="0">
         <padding>
            <Insets bottom="5" left="5" right="5" top="-20" />
         </padding>
         <Text fx:id="day" styleClass="thisMonthDate" text="\$day" />
         <Text fx:id="eventText" text="\$event1">
            <VBox.margin>
               <Insets left="10.0" />
            </VBox.margin></Text>
      </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
   </GridPane>
</AnchorPane>
```
###### \main\resources\view\DarkTheme.css
``` css
#calendarPlaceholder .yearMonth {
    -fx-font: 25px Tahoma;
    -fx-fill: #DEF0EE;
}

#calendarPlaceholder .button {
    -fx-padding: 5 10 5 10;
    -fx-margin: 20;
    -fx-border-color: #7194cc;
    -fx-border-width: 2;
    -fx-border-radius: 5;
    -fx-background-radius: 5;
    -fx-background-color: #ddeeff;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 15pt;
    -fx-font-weight: bold;
    -fx-text-fill: #95adf0;
    -fx-background-insets: 2, 0, 1, 2;
}

#calendarPlaceholder .button:hover {
    -fx-background-color: #ace;
}

#calendarPlaceholder .button:pressed, .button:default:hover:pressed {
    -fx-border-color: #58b;
    -fx-background-color: #7ad;
    -fx-text-fill: #fff;
}

#calendarPlaceholder .button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

#calendarPlaceholder .button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

#calendarPlaceholder .button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

#calendarPlaceholder .weekday {
    -fx-background-color: #e2f6ff;
    -fx-border-width: 1px;
    -fx-border-color: #7194cc;
}

#calendarPlaceholder .weekend {
    -fx-background-color: #b6c4da;
    -fx-border-width: 1px;
    -fx-border-color: #7194cc;
}

#calendarPlaceholder .dayName {
    -fx-font: 13px Tahoma;
    -fx-fill: black;
    -fx-stroke: black;
    -fx-stroke-width: 1;
}

#calendarPlaceholder .date {
    -fx-background-color: #e3e9f4;
    -fx-border-width: 1px;
    -fx-border-color: #7194cc;
}

#calendarPlaceholder .today {
    -fx-background-color: #b6c4da;
}

#calendarPlaceholder .thisMonthDate {
    -fx-font: 13px Tahoma;
    -fx-fill: black;
    -fx-stroke: black;
    -fx-stroke-width: 0.5;
}

#calendarPlaceholder .notThisMonthDate {
    -fx-font: 13px Tahoma;
    -fx-fill: #888;
    -fx-stroke: #888;
    -fx-stroke-width: 0.5;
}

#calendarPlaceholder .timecell {
    -fx-border-width: 0;
    -fx-pref-width: 100;
    -fx-min-width: 50;
}

#calendarPlaceholder .time {
    -fx-border-width: 0;
    -fx-pref-width: 150;
}

#calendarPlaceholder .blank1 {
    -fx-border-width: 1px 0;
    -fx-border-color: #666;
    -fx-background-color: #ccc;
}

#calendarPlaceholder .blank2 {
    -fx-border-width: 1px 0;
    -fx-border-color: #666;
    -fx-background-color: #999;
}

#calendarPlaceholder .module1hr {
    -fx-border-radius: 6;
    -fx-border-width: 0 0 3px 0;
    -fx-background-radius: 10;
    -fx-padding: 0 0 0 5;
}

#calendarPlaceholder .module2hrtop {
    -fx-border-radius: 6 6 0 0;
    -fx-border-width: 0;
    -fx-background-radius: 10 10 0 0;
    -fx-padding: 50 0 0 5;
}

#calendarPlaceholder .module2hrbottom {
    -fx-border-radius: 0 0 6 6;
    -fx-border-width: 0 0 3px 0;
    -fx-background-radius: 0 0 10 10;
    -fx-padding: -50 0 0 5;
}

#calendarPlaceholder .red {
    -fx-background-color: #f27770;
    -fx-border-color: #e91a1f;
}

#calendarPlaceholder .orange {
    -fx-background-color: #f99157;
    -fx-border-color: #e25608;
}

#calendarPlaceholder .yellow {
    -fx-background-color: #fc6;
    -fx-border-color: #fa0;
}

#calendarPlaceholder .blue {
    -fx-background-color: #69c;
    -fx-border-color: #369;
}

#calendarPlaceholder .teal {
    -fx-background-color: #6aa;
    -fx-border-color: #399;
}

#calendarPlaceholder .green {
    -fx-background-color: #9c9;
    -fx-border-color: #5a5;
}

#calendarPlaceholder .brown {
    -fx-background-color: #d27b53;
    -fx-border-color: #974b28;
}

#calendarPlaceholder .purple {
    -fx-background-color: #c9c;
    -fx-border-color: #a5a;
}

#calendarPlaceholder .pink {
    -fx-background-color: #fad;
    -fx-border-color: #c7a;
}
```
###### \main\resources\view\Timetable.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
   <ListView fx:id="timetableListView" VBox.vgrow="NEVER" HBox.hgrow="NEVER"/>
</VBox>
```
###### \main\resources\view\TimetableSlot.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Text?>
<AnchorPane fx:id="top" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
   <GridPane fx:id="box" maxWidth="250.0" HBox.hgrow="NEVER">
      <columnConstraints>
         <ColumnConstraints minWidth="10" />
      </columnConstraints>
      <VBox fx:id="node" alignment="CENTER_LEFT" minHeight="50" GridPane.columnIndex="0">
         <padding>
            <Insets bottom="5" left="5" right="5" top="5" />
         </padding>
         <Text fx:id="module" styleClass="thisMonthDate"/>
         <Text fx:id="lectureType"/>
         <Text fx:id="venue"/>
      </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
   </GridPane>
</AnchorPane>
```
###### \test\java\seedu\address\logic\commands\AddEventCommandTest.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagNotFoundException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;
import seedu.address.testutil.EventBuilder;

public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = getAddEventCommandForEvent(validEvent, modelStub).execute();

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateEventException();
        Event validEvent = new EventBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEventCommand.MESSAGE_DUPLICATE_EVENT);

        getAddEventCommandForEvent(validEvent, modelStub).execute();
    }

    @Test
    public void equals() {
        Event f1Race = new EventBuilder().build();
        Event iLight = new Event("iLight", "Marina Bay", "01/04/2018", "1930", "2359");
        AddEventCommand addF1Command = new AddEventCommand(f1Race);
        AddEventCommand addILightCommand = new AddEventCommand(iLight);

        // same object -> returns true
        assertTrue(addF1Command.equals(addF1Command));

        // same values -> returns true
        AddEventCommand addF1CommandCopy = new AddEventCommand(f1Race);
        assertTrue(addF1Command.equals(addF1CommandCopy));

        // different types -> returns false
        assertFalse(addF1Command.equals(1));

        // null -> returns false
        assertFalse(addF1Command.equals(null));

        // different event -> returns false
        assertFalse(addF1Command.equals(addILightCommand));
    }

    /**
     * Generates a new AddEventCommand with the details of the given event.
     */
    private AddEventCommand getAddEventCommandForEvent(Event event, Model model) {
        AddEventCommand command = new AddEventCommand(event);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) throws DuplicateEventException {
            fail("This method should not be called.");
        }

        @Override
        public void addToDo(ToDo todo) throws DuplicateToDoException {
            fail("This method should not be called.");
        }

        @Override
        public void addGroup(Group group) throws DuplicateGroupException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTag(Tag target, Tag editedTag) throws TagNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void removeTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData
        ) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteToDo(ToDo target) throws ToDoNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteGroup(Group target) throws GroupNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void updateToDo(ToDo target, ToDo editedToDo) throws DuplicateToDoException, ToDoNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateGroup(Group target, Group editedGroup) throws DuplicateGroupException,
                GroupNotFoundException {
            fail("This method should not be called.");
        }


        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ToDo> getFilteredToDoList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredToDoList(Predicate<ToDo> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void indicateCalendarChanged() {
            fail("This method should not be called.");
        }

        @Override
        public void indicateTimetableChanged() {
            fail("This method should not be called.");
        }

        @Override
        public boolean calendarIsViewed() {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void switchView() {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateEventException when trying to add a event.
     */
    private class ModelStubThrowingDuplicateEventException extends ModelStub {
        @Override
        public void addEvent(Event event) throws DuplicateEventException {
            throw new DuplicateEventException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public void addEvent(Event event) throws DuplicateEventException {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### \test\java\seedu\address\logic\commands\ChangeTagColorCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for ChangeTagColorCommand.
 */
public class ChangeTagColorCommandTest {
    private Model model;

    @Test
    public void execute_correctFields_success() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person editedPerson = new PersonBuilder().build();
        ChangeTagColorCommand command = prepareCommand(VALID_TAG_FRIEND, VALID_TAG_COLOR_RED);

        String expectedMessage =
                String.format(ChangeTagColorCommand.MESSAGE_EDIT_TAG_SUCCESS, VALID_TAG_FRIEND, VALID_TAG_COLOR_RED);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Tag oldTag = new Tag(VALID_TAG_FRIEND);
        Tag newTag = new Tag(VALID_TAG_FRIEND, VALID_TAG_COLOR_RED);
        expectedModel.updateTag(oldTag, newTag);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagNameNotInList_failure() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person editedPerson = new PersonBuilder().build();
        ChangeTagColorCommand command = prepareCommand(VALID_TAG_HUSBAND, VALID_TAG_COLOR_RED);

        assertCommandFailure(command, model, ChangeTagColorCommand.MESSAGE_TAG_NOT_IN_LIST);
    }

    @Test
    public void equals_test() throws Exception {
        ChangeTagColorCommand command1 = prepareCommand(VALID_TAG_HUSBAND, VALID_TAG_COLOR_RED);
        ChangeTagColorCommand command2 = prepareCommand(VALID_TAG_FRIEND, VALID_TAG_COLOR_RED);
        ChangeTagColorCommand command3 = prepareCommand(VALID_TAG_FRIEND, VALID_TAG_COLOR_RED);

        assertEquals(command1, command1);
        assertEquals(command2, command3);
        assertNotEquals(command3, 1);
        assertNotEquals(command1, command2);
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private ChangeTagColorCommand prepareCommand(String name, String color) {
        ChangeTagColorCommand command = new ChangeTagColorCommand(name, color);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \test\java\seedu\address\logic\commands\ScheduleGroupCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalGroups.GROUP_A;
import static seedu.address.testutil.TypicalGroups.GROUP_C;
import static seedu.address.testutil.TypicalGroups.GROUP_G;
import static seedu.address.testutil.TypicalGroups.GROUP_H;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.events.ui.TimetableChangedEvent;
import seedu.address.database.module.Module;
import seedu.address.database.module.Schedule;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.WeeklyEvent;
import seedu.address.model.group.Group;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ScheduleGroupCommand}.
 */
public class ScheduleGroupCommandTest {

    private ExpectedException thrown = ExpectedException.none();

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validGroup_success() {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        ArrayList<WeeklyEvent> freeA = new ArrayList<>();
        Module mod = new Module("Free", "", null);
        for (String day : daysOfWeek) {
            for (int s = 800; s < 1800; s += 100) {
                Schedule sch = new Schedule("", "", "", day, "" + s, "" + (s + 100), "");
                freeA.add(new WeeklyEvent(mod, sch));
            }
        }
        assertExecutionSuccess(GROUP_A, freeA);

        ArrayList<WeeklyEvent> freeH = new ArrayList<>();
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Monday", "800", "900", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Monday", "1600", "1700", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Monday", "1700", "1800", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Tuesday", "1400", "1500", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Tuesday", "1500", "1600", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "800", "900", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "1200", "1300", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "1300", "1400", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "1600", "1700", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "1700", "1800", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "800", "900", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "900", "1000", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "1300", "1400", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "1400", "1500", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "1500", "1600", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Friday", "1200", "1300", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Friday", "1300", "1400", "")));
        assertExecutionSuccess(GROUP_H, freeH);
    }

    @Test
    public void execute_groupNotFound_failure() {
        assertExecutionFailure(GROUP_C,
                String.format(ScheduleGroupCommand.MESSAGE_GROUP_NOT_FOUND, GROUP_C.getInformation()));
        assertExecutionFailure(GROUP_G,
                String.format(ScheduleGroupCommand.MESSAGE_GROUP_NOT_FOUND, GROUP_G.getInformation()));
    }

    @Test
    public void equals() {
        ScheduleGroupCommand groupACommand = new ScheduleGroupCommand(GROUP_A);
        ScheduleGroupCommand groupHCommand = new ScheduleGroupCommand(GROUP_H);

        // same object -> returns true
        assertTrue(groupACommand.equals(groupACommand));

        // same values -> returns true
        ScheduleGroupCommand selectFirstCommandCopy = new ScheduleGroupCommand(GROUP_A);
        assertTrue(groupACommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(groupACommand.equals(1));

        // null -> returns false
        assertFalse(groupACommand.equals(null));

        // different person -> returns false
        assertFalse(groupACommand.equals(groupHCommand));
    }

    /**
     * Executes a {@code ScheduleGroupCommand} with the given {@code index},
     * and checks that {@code JumpToListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Group group, ArrayList<WeeklyEvent> freeSlots) {
        ScheduleGroupCommand scheduleGroupCommand = prepareCommand(group);

        try {
            CommandResult commandResult = scheduleGroupCommand.execute();
            assertEquals(String.format(ScheduleGroupCommand.MESSAGE_SUCCESS, group.getInformation()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        TimetableChangedEvent lastEvent = (TimetableChangedEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertTrue(lastEvent != null);
        assertTrue(lastEvent.timetable.equals(freeSlots));
    }

    /**
     * Executes a {@code ScheduleGroupCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Group group, String expectedMessage) {
        ScheduleGroupCommand scheduleGroupCommand = prepareCommand(group);

        try {
            scheduleGroupCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
            assertEquals(expectedMessage, ce.getMessage());
        }
    }

    /**
     * Returns a {@code ScheduleGroupCommand} with parameters {@code index}.
     */
    private ScheduleGroupCommand prepareCommand(Group group) {
        ScheduleGroupCommand scheduleGroupCommand = new ScheduleGroupCommand(group);
        scheduleGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return scheduleGroupCommand;
    }
}
```
###### \test\java\seedu\address\logic\commands\SwitchCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SwitchCommand.
 */
public class SwitchCommandTest {
    private Model model;
    private Model expectedModel;
    private SwitchCommand switchCommand;

    @Test
    public void execute_calendarToTimetable_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.switchView();

        switchCommand = new SwitchCommand();
        switchCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(switchCommand, model, SwitchCommand.MESSAGE_SUCCESS_TIMETABLE, expectedModel);
    }

    @Test
    public void execute_timetableToCalendar_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.switchView();
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        switchCommand = new SwitchCommand();
        switchCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(switchCommand, expectedModel, SwitchCommand.MESSAGE_SUCCESS_CALENDAR, model);
    }
}
```
###### \test\java\seedu\address\logic\parser\AddEventCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_END_TIME_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_TIME_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_TIME_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_VENUE_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_VENUE_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_NDP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder().withName(VALID_EVENT_NAME_NDP).withVenue(VALID_EVENT_VENUE_NDP)
                .withDate(VALID_EVENT_DATE_NDP).withStartTime(VALID_EVENT_START_TIME_NDP)
                .withEndTime(VALID_EVENT_END_TIME_NDP).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple names - last name accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_F1 + EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple venues - last venue accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_F1 + EVENT_VENUE_DESC_NDP
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple dates - last date accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_F1
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple start times - last time accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_F1 + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple tags - all accepted
        Event expectedEventMultipleTags = new EventBuilder().withName(VALID_EVENT_NAME_NDP)
                .withVenue(VALID_EVENT_VENUE_NDP).withDate(VALID_EVENT_DATE_NDP)
                .withStartTime(VALID_EVENT_START_TIME_NDP).withEndTime(VALID_EVENT_END_TIME_NDP).build();
        assertParseSuccess(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEventMultipleTags));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_EVENT_NAME_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP, expectedMessage);

        // missing venue prefix
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + VALID_EVENT_VENUE_NDP + EVENT_DATE_DESC_NDP
                + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + VALID_EVENT_DATE_NDP
                + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP, expectedMessage);

        // missing start time prefix
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                + VALID_EVENT_START_TIME_NDP + EVENT_END_TIME_DESC_NDP, expectedMessage);

        // missing end time prefix
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                + EVENT_START_TIME_DESC_NDP + VALID_EVENT_END_TIME_NDP, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EVENT_NAME_NDP + VALID_EVENT_VENUE_NDP + VALID_EVENT_DATE_NDP
                + VALID_EVENT_START_TIME_NDP + VALID_EVENT_END_TIME_NDP, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                Event.MESSAGE_NAME_CONSTRAINTS);

        // invalid venue
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + INVALID_EVENT_VENUE_DESC + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                Event.MESSAGE_VENUE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + INVALID_EVENT_DATE_DESC
                        + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                Event.MESSAGE_DATE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + INVALID_EVENT_START_TIME_DESC + EVENT_END_TIME_DESC_NDP,
                Event.MESSAGE_TIME_CONSTRAINTS);

        // invalid end time
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_NDP + INVALID_EVENT_END_TIME_DESC,
                Event.MESSAGE_TIME_CONSTRAINTS);

        // endTime before Start
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + " " + PREFIX_END_TIME + "0000" + EVENT_START_TIME_DESC_NDP,
                AddEventCommand.MESSAGE_END_BEFORE_START);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                + INVALID_EVENT_START_TIME_DESC + EVENT_END_TIME_DESC_NDP, Event.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
```
###### \test\java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_addEvent() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(EventUtil.getAddEventCommand(event));
        assertEquals(new AddEventCommand(event), command);
    }

    @Test
    public void parseCommand_addEventAlias() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(AddEventCommand.COMMAND_ALIAS + " "
                + EventUtil.getEventDetails(event));
        assertEquals(new AddEventCommand(event), command);
    }

    @Test
    public void parseCommand_changeTagColor() throws Exception {
        String tagName = "friends";
        String tagColor = "red";
        ChangeTagColorCommand command = (ChangeTagColorCommand) parser.parseCommand(ChangeTagColorCommand.COMMAND_WORD
                + " " + tagName + " " + tagColor);
        assertEquals(new ChangeTagColorCommand(tagName, tagColor), command);
    }

    @Test
    public void parseCommand_changeTagColorAlias() throws Exception {
        String tagName = "friends";
        String tagColor = "red";
        ChangeTagColorCommand command = (ChangeTagColorCommand) parser.parseCommand(ChangeTagColorCommand.COMMAND_ALIAS
                + " " + tagName + " " + tagColor);
        assertEquals(new ChangeTagColorCommand(tagName, tagColor), command);
    }

    @Test
    public void parseCommand_switch() throws Exception {
        assertTrue(parser.parseCommand(SwitchCommand.COMMAND_WORD) instanceof SwitchCommand);
        assertTrue(parser.parseCommand(SwitchCommand.COMMAND_WORD + " view") instanceof SwitchCommand);
    }

    @Test
    public void parseCommand_switchAlias() throws Exception {
        assertTrue(parser.parseCommand(SwitchCommand.COMMAND_ALIAS) instanceof SwitchCommand);
        assertTrue(parser.parseCommand(SwitchCommand.COMMAND_ALIAS + " view") instanceof SwitchCommand);
    }

```
###### \test\java\seedu\address\logic\parser\ChangeTagColorCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.model.tag.Tag;

/**
 * Tests for the parsing of input arguments and creating a new ChangeTagColorCommand object
 */
public class ChangeTagColorCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeTagColorCommand.MESSAGE_USAGE);

    private ChangeTagColorCommandParser parser = new ChangeTagColorCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no color specified
        assertParseFailure(parser, VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // unsupported color specified
        assertParseFailure(parser, VALID_TAG_FRIEND + INVALID_TAG_COLOR,
                Tag.MESSAGE_TAG_COLOR_CONSTRAINTS);

        // invalid tag name
        assertParseFailure(parser, INVALID_TAG_DESC + " " + VALID_TAG_COLOR_RED,
                Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_validValue_success() {
        // unsupported color specified
        assertParseSuccess(parser, VALID_TAG_FRIEND + " " + VALID_TAG_COLOR_RED,
                new ChangeTagColorCommand(VALID_TAG_FRIEND, VALID_TAG_COLOR_RED));
    }
}
```
###### \test\java\seedu\address\logic\parser\ScheduleGroupCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INFORMATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFORMATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ScheduleGroupCommand;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;

/**
 * Tests for the parsing of input arguments and creating a new ScheduleGroupCommand object
 */
public class ScheduleGroupCommandParserTest {

    private ScheduleGroupCommandParser parser = new ScheduleGroupCommandParser();

    @Test
    public void parse_validArgs_returnsScheduleGroupCommand() {
        Group group = new Group(new Information(VALID_INFORMATION));
        assertParseSuccess(parser, VALID_INFORMATION, new ScheduleGroupCommand(group));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_INFORMATION,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleGroupCommand.MESSAGE_USAGE));
    }
}
```
###### \test\java\seedu\address\model\event\EventTest.java
``` java
package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_NDP;

import org.junit.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.EventBuilder;

public class EventTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Event(null, null, null, null, null));
    }

    @Test
    public void isValidEventName_null_throwsNullPointerException() {
        // null event name
        Assert.assertThrows(NullPointerException.class, () -> Event.isValidName(null));
    }

    @Test
    public void isValidName() {
        assertTrue(Event.isValidName(VALID_EVENT_NAME_F1));
        assertTrue(Event.isValidName(VALID_EVENT_NAME_NDP));
        assertFalse(Event.isValidName(INVALID_EVENT_NAME_DESC));
    }

    @Test
    public void isValidDate() {
        assertTrue(Event.isValidDate(VALID_EVENT_DATE_F1));
        assertTrue(Event.isValidDate(VALID_EVENT_DATE_NDP));
        assertFalse(Event.isValidDate(INVALID_EVENT_DATE_DESC));
    }

    @Test
    public void isValidTime() {
        assertTrue(Event.isValidTime(VALID_EVENT_START_TIME_F1));
        assertTrue(Event.isValidTime(VALID_EVENT_START_TIME_NDP));
        assertTrue(Event.isValidTime(VALID_EVENT_END_TIME_F1));
        assertTrue(Event.isValidTime(VALID_EVENT_END_TIME_NDP));
        assertFalse(Event.isValidTime(INVALID_EVENT_START_TIME_DESC));
        assertFalse(Event.isValidTime(INVALID_EVENT_END_TIME_DESC));
    }

    @Test
    public void equals() {
        Event f1Race1 = new EventBuilder().build();
        Event f1Race2 = new EventBuilder().withName(VALID_EVENT_NAME_F1).withDate(VALID_EVENT_DATE_F1)
                .withStartTime(VALID_EVENT_START_TIME_F1).withEndTime(VALID_EVENT_END_TIME_F1).build();

        assertTrue(f1Race1.equals(f1Race1));
        assertFalse(f1Race1.equals(1));
        assertTrue(f1Race1.equals(f1Race2));
        assertFalse(f1Race1.equals(new Event()));
    }

    @Test
    public void toString_test() {
        Event f1Race1 = new EventBuilder().build();
        Event f1Race2 = new EventBuilder().withName(VALID_EVENT_NAME_F1).withDate(VALID_EVENT_DATE_F1)
                .withStartTime(VALID_EVENT_START_TIME_F1).withEndTime(VALID_EVENT_END_TIME_F1).build();

        assertTrue(f1Race1.toString().equals(f1Race1.toString()));
        assertTrue(f1Race1.toString().equals(f1Race2.toString()));
        assertFalse(f1Race1.toString().equals(new Event().toString()));
    }
}
```
###### \test\java\seedu\address\model\event\WeeklyEventTest.java
``` java
package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_NDP;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

import seedu.address.database.module.Module;
import seedu.address.database.module.Schedule;

public class WeeklyEventTest {

    private WeeklyEvent event1 = new WeeklyEvent("CS2101", "COM1", "1500", "1600", "WEDNESDAY");
    private WeeklyEvent event2 = new WeeklyEvent(new Module("CS2103", "Software Engineer"), new Schedule());
    private WeeklyEvent event3 = new WeeklyEvent(new Module("CS2103", "Software Engineer"), new Schedule());
    private WeeklyEvent event4 = new WeeklyEvent("CS2103T", "I3", "1500", "1700", "WEDNESDAY");
    private WeeklyEvent event5 = new WeeklyEvent("CS2102", "COM2", "2000", "2100", "WEDNESDAY");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WeeklyEvent(null, null, null, null, (String[]) null));
        assertThrows(NullPointerException.class, () -> new WeeklyEvent(null, null));
    }

    @Test
    public void isValidWeeklyEventName_null_throwsNullPointerException() {
        // null event name
        assertThrows(NullPointerException.class, () -> WeeklyEvent.isValidName(null));
    }

    @Test
    public void isValidName() {
        assertTrue(WeeklyEvent.isValidName(VALID_EVENT_NAME_F1));
        assertTrue(WeeklyEvent.isValidName(VALID_EVENT_NAME_NDP));
        assertFalse(WeeklyEvent.isValidName(INVALID_EVENT_NAME_DESC));
    }

    @Test
    public void isValidDate() {
        assertTrue(WeeklyEvent.isValidDate(VALID_EVENT_DATE_F1));
        assertTrue(WeeklyEvent.isValidDate(VALID_EVENT_DATE_NDP));
        assertFalse(WeeklyEvent.isValidDate(INVALID_EVENT_DATE_DESC));
    }

    @Test
    public void isValidTime() {
        assertTrue(WeeklyEvent.isValidTime(VALID_EVENT_START_TIME_F1));
        assertTrue(WeeklyEvent.isValidTime(VALID_EVENT_START_TIME_NDP));
        assertTrue(WeeklyEvent.isValidTime(VALID_EVENT_END_TIME_F1));
        assertTrue(WeeklyEvent.isValidTime(VALID_EVENT_END_TIME_NDP));
        assertFalse(WeeklyEvent.isValidTime(INVALID_EVENT_START_TIME_DESC));
        assertFalse(WeeklyEvent.isValidTime(INVALID_EVENT_END_TIME_DESC));
    }

    @Test
    public void equals_test() {
        assertTrue(event1.equals(event1));
        assertTrue(event2.equals(event3));
        assertFalse(event1.equals(1));
        assertFalse(event1.equals(event2));
    }

    @Test
    public void clash() {
        assertTrue(event1.clash(event1));
        assertTrue(event1.clash(event4));
        assertTrue(event2.clash(event3));
        assertFalse(event1.clash(event2));
        assertFalse(event1.clash(event5));
        assertFalse(event3.clash(event5));
    }

    @Test
    public void toString_test() {
        assertTrue(event1.toString().equals(event1.toString()));
        assertFalse(event1.toString().equals(event2.toString()));
    }
}
```
###### \test\java\seedu\address\model\group\GroupTest.java
``` java
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Group groupA = new GroupBuilder().withInformation("Group A").build();
    private Group groupA2 = new GroupBuilder().withInformation("Group A").build();
    private Group groupB = new GroupBuilder().withInformation("Group B").build();

    @Test
    public void hashCodeAndString_test() {
        assertEquals(groupA.hashCode(), groupA.hashCode());
        assertEquals(groupA.hashCode(), groupA2.hashCode());
        assertNotEquals(groupA.hashCode(), groupB.hashCode());

        assertEquals(groupA.toString(), groupA.toString());
        assertEquals(groupA.toString(), groupA2.toString());
        assertNotEquals(groupA.toString(), groupB.toString());
    }

    @Test
    public void addPerson() throws Exception {
        groupA.addPerson(ALICE);
        assertNotEquals(groupA, groupA2);
        groupA2.addPerson(ALICE);
        assertEquals(groupA, groupA2);

        groupA.addPerson(BENSON);
        groupA2.addPerson(CARL);
        assertNotEquals(groupA, groupA2);
        groupA.addPerson(CARL);
        groupA2.addPerson(BENSON);
        assertEquals(groupA, groupA2);
    }

    @Test
    public void addPerson_duplicatePerson_throwsException() throws Exception {
        groupA.addPerson(ALICE);
        groupA.addPerson(BENSON);
        groupA.addPerson(CARL);
        thrown.expect(DuplicatePersonException.class);
        groupA.addPerson(BENSON);
    }

    @Test
    public void removePerson() throws Exception {
        groupA.addPerson(ALICE);
        groupA.addPerson(BENSON);
        groupA.addPerson(CARL);
        groupA2.addPerson(ALICE);
        groupA2.addPerson(BENSON);
        groupA2.addPerson(CARL);

        groupA.removePerson(ALICE);
        assertNotEquals(groupA, groupA2);
        groupA2.removePerson(ALICE);
        assertEquals(groupA, groupA2);

        groupA.removePerson(BENSON);
        groupA2.removePerson(CARL);
        assertNotEquals(groupA, groupA2);
        groupA.removePerson(CARL);
        groupA2.removePerson(BENSON);
        assertEquals(groupA, groupA2);
    }

    @Test
    public void removePerson_personNotFound_throwsException() throws Exception {
        groupA.addPerson(ALICE);
        groupA.addPerson(CARL);
        thrown.expect(PersonNotFoundException.class);
        groupA.removePerson(BENSON);
    }
}
```
###### \test\java\seedu\address\model\person\AddressTest.java
``` java
    @Test
    public void hashCode_variousTest() {
        Address address1 = new Address("Blk 456, Den Road, #01-355");
        Address address2 = new Address("Blk 456, Den Road, #01-355");
        Address address3 = new Address("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA");

        assertEquals(address1.hashCode(), address1.hashCode());
        assertEquals(address1.hashCode(), address2.hashCode());
        assertNotEquals(address2.hashCode(), address3.hashCode());
    }
}
```
###### \test\java\seedu\address\model\person\DetailTest.java
``` java
    @Test
    public void hashCode_variousTest() {
        Detail detail1 = new Detail("Likes tennis");
        Detail detail2 = new Detail("Likes tennis");
        Detail detail3 = new Detail("Has 3 dogs");

        assertEquals(detail1.hashCode(), detail1.hashCode());
        assertEquals(detail1.hashCode(), detail2.hashCode());
        assertNotEquals(detail2.hashCode(), detail3.hashCode());
    }
}
```
###### \test\java\seedu\address\model\person\EmailTest.java
``` java
    @Test
    public void hashCode_variousTest() {
        Email email1 = new Email("PeterJack_1190@example.com");
        Email email2 = new Email("PeterJack_1190@example.com");
        Email email3 = new Email("a@bc");

        assertEquals(email1.hashCode(), email1.hashCode());
        assertEquals(email1.hashCode(), email2.hashCode());
        assertNotEquals(email2.hashCode(), email3.hashCode());
    }
}
```
###### \test\java\seedu\address\model\person\NameTest.java
``` java
    @Test
    public void hashCode_variousTest() {
        Name name1 = new Name("Peter Jack");
        Name name2 = new Name("Peter Jack");
        Name name3 = new Name("Capital Tan");

        assertEquals(name1.hashCode(), name1.hashCode());
        assertEquals(name1.hashCode(), name2.hashCode());
        assertNotEquals(name2.hashCode(), name3.hashCode());
    }
}
```
###### \test\java\seedu\address\model\person\PhoneTest.java
``` java
    @Test
    public void hashCode_variousTest() {
        Phone phone1 = new Phone("93121534");
        Phone phone2 = new Phone("93121534");
        Phone phone3 = new Phone("124293874203154");

        assertEquals(phone1.hashCode(), phone1.hashCode());
        assertEquals(phone1.hashCode(), phone2.hashCode());
        assertNotEquals(phone2.hashCode(), phone3.hashCode());
    }
}
```
###### \test\java\seedu\address\model\person\TimeTableLinkTest.java
``` java
    @Test
    public void hashCode_variousTest() {
        TimeTableLink timeTableLink1 = new TimeTableLink("http://modsn.us/MYwiD");
        TimeTableLink timeTableLink2 = new TimeTableLink("http://modsn.us/MYwiD");
        TimeTableLink timeTableLink3 = new TimeTableLink("http://modsn.us/FumdA");

        assertEquals(timeTableLink1.hashCode(), timeTableLink1.hashCode());
        assertEquals(timeTableLink1.hashCode(), timeTableLink2.hashCode());
        assertNotEquals(timeTableLink2.hashCode(), timeTableLink3.hashCode());
    }
}
```
###### \test\java\seedu\address\model\UniqueEventListTest.java
``` java
package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_NDP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.testutil.EventBuilder;

public class UniqueEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asObservableList().remove(0);
    }

    @Test
    public void equals_sameList_true() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        assertEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList2.add(new EventBuilder().build());
        assertEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList2.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        assertEquals(uniqueEventList1, uniqueEventList2);
    }

    @Test
    public void equals_differentList_false() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        uniqueEventList2.add(new EventBuilder().build());
        assertNotEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        assertNotEquals(uniqueEventList1, uniqueEventList2);
    }

    @Test
    public void hashCode_sameList_sameResult() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        assertEquals(uniqueEventList1.hashCode(), uniqueEventList2.hashCode());

        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList2.add(new EventBuilder().build());
        assertEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList2.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        assertEquals(uniqueEventList1, uniqueEventList2);
    }

    @Test
    public void hashCode_differentList_differentResult() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        uniqueEventList2.add(new EventBuilder().build());
        assertNotEquals(uniqueEventList1.hashCode(), uniqueEventList2.hashCode());

        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        assertNotEquals(uniqueEventList1.hashCode(), uniqueEventList2.hashCode());
    }

    @Test
    public void duplicateEvent() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
    }

    @Test
    public void setEvent_editedEvent_success() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();
        Event ndp = new EventBuilder().withName(VALID_EVENT_NAME_NDP).build();
        Event f1 = new EventBuilder().build();
        uniqueEventList.add(ndp);
        uniqueEventList.setEvent(ndp, f1);
        uniqueEventList2.add(f1);
        assertEquals(uniqueEventList, uniqueEventList2);
    }

    @Test
    public void setEvent_wrongEvent_throwsEventNotFoundException() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event ndp = new EventBuilder().withName(VALID_EVENT_NAME_NDP).build();
        Event f1 = new EventBuilder().build();
        uniqueEventList.add(ndp);
        thrown.expect(EventNotFoundException.class);
        uniqueEventList.setEvent(f1, ndp);
    }

    @Test
    public void setEvent_duplicateEvent_throwsDuplicateEventException() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event ndp = new EventBuilder().withName(VALID_EVENT_NAME_NDP).build();
        Event f1 = new EventBuilder().build();
        uniqueEventList.add(ndp);
        uniqueEventList.add(f1);
        thrown.expect(DuplicateEventException.class);
        uniqueEventList.setEvent(f1, ndp);
    }

    @Test
    public void removeEvent_wrongEvent_throwsEventNotFoundException() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event ndp = new EventBuilder().withName(VALID_EVENT_NAME_NDP).build();
        uniqueEventList.add(ndp);
        thrown.expect(EventNotFoundException.class);
        uniqueEventList.removeEvent(new EventBuilder().build());
    }

    @Test
    public void removeEvent_correctEvent_success() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(new EventBuilder().build());
        uniqueEventList.removeEvent(new EventBuilder().build());
        assertEquals(uniqueEventList, new UniqueEventList());
    }

    @Test
    public void setEvents_correctParameters_success() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();

        uniqueEventList2.add(new EventBuilder().build());
        uniqueEventList1.setEvents(uniqueEventList2);
        assertEquals(uniqueEventList1, uniqueEventList2);

        uniqueEventList2.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList2.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        uniqueEventList1.setEvents(uniqueEventList2);
        assertEquals(uniqueEventList1, uniqueEventList2);

        List<Event> events = new ArrayList<Event>();
        events.add(new EventBuilder().build());
        events.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        events.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());
        uniqueEventList1.setEvents(events);
        assertEquals(uniqueEventList1, uniqueEventList2);
    }

    @Test
    public void setEvents_null_throwsNullPointerException() throws Exception {
        UniqueEventList uniqueEventList = new UniqueEventList();
        thrown.expect(NullPointerException.class);
        uniqueEventList.setEvents((List<Event>) null);
    }

    @Test
    public void iterator() throws Exception {
        UniqueEventList uniqueEventList1 = new UniqueEventList();
        UniqueEventList uniqueEventList2 = new UniqueEventList();

        uniqueEventList1.add(new EventBuilder().build());
        uniqueEventList1.add(new EventBuilder().withName(VALID_EVENT_NAME_NDP).build());
        uniqueEventList1.add(new EventBuilder().withVenue(VALID_EVENT_VENUE_NDP).build());

        Iterator<Event> iter = uniqueEventList1.iterator();
        while (iter.hasNext()) {
            uniqueEventList2.add(iter.next());
        }

        assertEquals(uniqueEventList1, uniqueEventList2);
    }
}
```
###### \test\java\seedu\address\model\UniqueTagListTest.java
``` java
    @Test
    public void equals_sameList_true() throws Exception {
        UniqueTagList uniqueTagList1 = new UniqueTagList();
        UniqueTagList uniqueTagList2 = new UniqueTagList();
        assertEquals(uniqueTagList1, uniqueTagList2);

        uniqueTagList1.add(new Tag(VALID_TAG_COLOR_RED));
        uniqueTagList2.add(new Tag(VALID_TAG_COLOR_RED));
        assertEquals(uniqueTagList1, uniqueTagList2);

        uniqueTagList1.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList1.add(new Tag(VALID_TAG_HUSBAND));
        uniqueTagList2.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList2.add(new Tag(VALID_TAG_HUSBAND));
        assertEquals(uniqueTagList1, uniqueTagList2);
    }

    @Test
    public void equals_differentList_false() throws Exception {
        UniqueTagList uniqueTagList1 = new UniqueTagList();
        UniqueTagList uniqueTagList2 = new UniqueTagList();
        uniqueTagList2.add(new Tag(VALID_TAG_COLOR_RED));
        assertNotEquals(uniqueTagList1, uniqueTagList2);

        uniqueTagList1.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList1.add(new Tag(VALID_TAG_COLOR_RED));
        uniqueTagList2.add(new Tag(VALID_TAG_FRIEND));
        assertNotEquals(uniqueTagList1, uniqueTagList2);
    }

    @Test
    public void hashCode_sameList_sameResult() throws Exception {
        UniqueTagList uniqueTagList1 = new UniqueTagList();
        UniqueTagList uniqueTagList2 = new UniqueTagList();
        assertEquals(uniqueTagList1.hashCode(), uniqueTagList2.hashCode());

        uniqueTagList1.add(new Tag(VALID_TAG_COLOR_RED));
        uniqueTagList2.add(new Tag(VALID_TAG_COLOR_RED));
        assertEquals(uniqueTagList1, uniqueTagList2);

        uniqueTagList1.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList1.add(new Tag(VALID_TAG_HUSBAND));
        uniqueTagList2.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList2.add(new Tag(VALID_TAG_HUSBAND));
        assertEquals(uniqueTagList1, uniqueTagList2);
    }

    @Test
    public void hashCode_differentList_differentResult() throws Exception {
        UniqueTagList uniqueTagList1 = new UniqueTagList();
        UniqueTagList uniqueTagList2 = new UniqueTagList();
        uniqueTagList2.add(new Tag(VALID_TAG_COLOR_RED));
        assertNotEquals(uniqueTagList1.hashCode(), uniqueTagList2.hashCode());

        uniqueTagList1.add(new Tag(VALID_TAG_FRIEND));
        uniqueTagList1.add(new Tag(VALID_TAG_COLOR_RED));
        uniqueTagList2.add(new Tag(VALID_TAG_FRIEND));
        assertNotEquals(uniqueTagList1.hashCode(), uniqueTagList2.hashCode());
    }

    @Test
    public void duplicateTag() throws Exception {
        UniqueTagList uniqueTagList = new UniqueTagList();
        uniqueTagList.add(new Tag(VALID_TAG_FRIEND));
        thrown.expect(UniqueTagList.DuplicateTagException.class);
        uniqueTagList.add(new Tag(VALID_TAG_FRIEND));
    }
}
```
###### \test\java\seedu\address\model\UniqueToDoListTest.java
``` java
    @Test
    public void equals_sameList_true() throws Exception {
        UniqueToDoList uniqueToDoList1 = new UniqueToDoList();
        UniqueToDoList uniqueToDoList2 = new UniqueToDoList();
        assertEquals(uniqueToDoList1, uniqueToDoList2);

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_E)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_E)));
        assertEquals(uniqueToDoList1, uniqueToDoList2);

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList1.add(new ToDo(new Content(VALID_CONTENT)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList2.add(new ToDo(new Content(VALID_CONTENT)));
        assertEquals(uniqueToDoList1, uniqueToDoList2);
    }

    @Test
    public void equals_differentList_false() throws Exception {
        UniqueToDoList uniqueToDoList1 = new UniqueToDoList();
        UniqueToDoList uniqueToDoList2 = new UniqueToDoList();
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_E)));
        assertNotEquals(uniqueToDoList1, uniqueToDoList2);

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList1.add(new ToDo(new Content(CONTENT_E)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_B)));
        assertNotEquals(uniqueToDoList1, uniqueToDoList2);
    }

    @Test
    public void hashCode_sameList_sameResult() throws Exception {
        UniqueToDoList uniqueToDoList1 = new UniqueToDoList();
        UniqueToDoList uniqueToDoList2 = new UniqueToDoList();
        assertEquals(uniqueToDoList1.hashCode(), uniqueToDoList2.hashCode());

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_E)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_E)));
        assertEquals(uniqueToDoList1, uniqueToDoList2);

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList1.add(new ToDo(new Content(VALID_CONTENT)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList2.add(new ToDo(new Content(VALID_CONTENT)));
        assertEquals(uniqueToDoList1, uniqueToDoList2);
    }

    @Test
    public void hashCode_differentList_differentResult() throws Exception {
        UniqueToDoList uniqueToDoList1 = new UniqueToDoList();
        UniqueToDoList uniqueToDoList2 = new UniqueToDoList();
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_E)));
        assertNotEquals(uniqueToDoList1.hashCode(), uniqueToDoList2.hashCode());

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList1.add(new ToDo(new Content(CONTENT_E)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_B)));
        assertNotEquals(uniqueToDoList1.hashCode(), uniqueToDoList2.hashCode());
    }
}
```
###### \test\java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void backupAddressBook_typicalAddressBook() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.backupAddressBook(original);
        ReadOnlyAddressBook backedUp = storageManager.readAddressBookBackup().get();
        assertEquals(original, new AddressBook(backedUp));
    }

    @Test
    public void backupAddressBook_withFilePath_typicalAddressBook() throws Exception {
        AddressBook original = getTypicalAddressBook();
        String filePath = storageManager.getAddressBookFilePath();
        storageManager.backupAddressBook(original, filePath);
        ReadOnlyAddressBook backedUp = storageManager.readAddressBookBackup(filePath).get();
        assertEquals(original, new AddressBook(backedUp));
    }

```
###### \test\java\seedu\address\storage\XmlAdaptedEventTest.java
``` java
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_F1;
import static seedu.address.storage.XmlAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.testutil.Assert;
import seedu.address.testutil.EventBuilder;

public class XmlAdaptedEventTest {
    private static final String INVALID_NAME = "Something?!";
    private static final String INVALID_VENUE = "Some&where";
    private static final String INVALID_DATE = "30/02/2000";
    private static final String INVALID_START_TIME = "2369";
    private static final String INVALID_END_TIME = "23:59";

    private static final String VALID_NAME = VALID_EVENT_NAME_F1;
    private static final String VALID_VENUE = VALID_EVENT_VENUE_F1;
    private static final String VALID_DATE = VALID_EVENT_DATE_F1;
    private static final String VALID_START_TIME = VALID_EVENT_START_TIME_F1;
    private static final String VALID_END_TIME = VALID_EVENT_END_TIME_F1;

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(new EventBuilder().build());
        assertEquals(new EventBuilder().build(), event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(INVALID_NAME, VALID_VENUE, VALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Event.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(null, VALID_VENUE, VALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidVenue_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, INVALID_VENUE, VALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Event.MESSAGE_VENUE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullVenue_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, null, VALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Venue");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, INVALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Event.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, null, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, VALID_DATE, INVALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Event.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, VALID_DATE, null, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "StartTime");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, VALID_DATE, VALID_START_TIME, INVALID_END_TIME);
        String expectedMessage = Event.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_VENUE, VALID_DATE, VALID_START_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "EndTime");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void equals_test() {
        XmlAdaptedEvent event1 = new XmlAdaptedEvent(new EventBuilder().build());
        XmlAdaptedEvent event2 = new XmlAdaptedEvent(new EventBuilder().withName("Different").build());
        assertEquals(event1, event1);
        assertEquals(event1, new XmlAdaptedEvent(new EventBuilder().build()));
        assertNotEquals(event1, 1);
        assertNotEquals(event1, event2);
    }
}
```
###### \test\java\seedu\address\storage\XmlAdaptedPersonTest.java
``` java
    @Test
    public void xmlAdaptedTagEqual() {
        XmlAdaptedTag tag1 = new XmlAdaptedTag("friends");
        XmlAdaptedTag tag2 = new XmlAdaptedTag("friends");
        Tag tag3 = new Tag("friends");
        XmlAdaptedTag tag4 = new XmlAdaptedTag("husband");

        assertEquals(tag1, tag2);
        assertNotEquals(tag1, tag3);
        assertNotEquals(tag2, tag4);
    }
}
```
###### \test\java\seedu\address\storage\XmlAdaptedTagTest.java
``` java
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.storage.XmlAdaptedTag.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class XmlAdaptedTagTest {
    private static final String INVALID_NAME = "Something?!";
    private static final String INVALID_COLOR = "rainbow";

    private static final String VALID_NAME = VALID_TAG_FRIEND;
    private static final String VALID_COLOR = VALID_TAG_COLOR_RED;

    @Test
    public void toModelType_validTagDetails_returnsTag() throws Exception {
        XmlAdaptedTag tag = new XmlAdaptedTag(new Tag(VALID_NAME, VALID_COLOR));
        assertEquals(new Tag(VALID_NAME, VALID_COLOR), tag.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedTag tag = new XmlAdaptedTag(INVALID_NAME);
        String expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedTag tag = new XmlAdaptedTag(null, VALID_COLOR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_invalidColor_throwsIllegalValueException() {
        XmlAdaptedTag tag =
                new XmlAdaptedTag(VALID_NAME, INVALID_COLOR);
        String expectedMessage = Tag.MESSAGE_TAG_COLOR_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void toModelType_nullColor_throwsIllegalValueException() {
        XmlAdaptedTag tag =
                new XmlAdaptedTag(VALID_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Color");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, tag::toModelType);
    }

    @Test
    public void equals_test() {
        XmlAdaptedTag tag1 = new XmlAdaptedTag(VALID_TAG_FRIEND);
        XmlAdaptedTag tag2 = new XmlAdaptedTag(VALID_TAG_HUSBAND, VALID_COLOR);
        assertEquals(tag1, tag1);
        assertEquals(tag1, new XmlAdaptedTag(VALID_TAG_FRIEND));
        assertNotEquals(tag1, 1);
        assertNotEquals(tag1, tag2);
    }
}
```
###### \test\java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    private java.util.Optional<ReadOnlyAddressBook> readAddressBookBackup(String filePath) throws Exception {
        return new XmlAddressBookStorage(filePath).readAddressBookBackup(addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void readAddressBookBackup_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBookBackup(null);
    }

    @Test
    public void readAddressBookBackup_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBookBackup("NonExistentFile.xml.backup").isPresent());
    }

```
###### \test\java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    @Test
    public void readAndBackupAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        AddressBook original = getTypicalAddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        //Backup in new file and read back
        xmlAddressBookStorage.backupAddressBook(original, filePath);
        ReadOnlyAddressBook readBack = xmlAddressBookStorage.readAddressBookBackup(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        original.addToDo(TODO_D);
        original.addGroup(GROUP_D);
        xmlAddressBookStorage.backupAddressBook(original, filePath);
        readBack = xmlAddressBookStorage.readAddressBookBackup(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        //Back and read without specifying file path
        original.addPerson(IDA);
        original.addToDo(TODO_E);
        original.addGroup(GROUP_E);
        xmlAddressBookStorage.backupAddressBook(original); //file path not specified
        readBack = xmlAddressBookStorage.readAddressBookBackup().get(); //file path not specified
        assertEquals(original, new AddressBook(readBack));
    }

    @Test
    public void backupAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        backupAddressBook(null, "SomeFile.xml");
    }

    /**
     * Backs up {@code addressBook} at the specified {@code filePath}.
     */
    private void backupAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new XmlAddressBookStorage(filePath).backupAddressBook(addressBook);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void backupAddressBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        backupAddressBook(new AddressBook(), null);
    }
}
```
###### \test\java\seedu\address\testutil\EventBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.model.event.Event;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "F1 Race";
    public static final String DEFAULT_VENUE = "Marina Bay Street Circuit";
    public static final String DEFAULT_DATE = "19/07/2018";
    public static final String DEFAULT_START_TIME = "1000";
    public static final String DEFAULT_END_TIME = "2100";

    private String name;
    private String venue;
    private String date;
    private String startTime;
    private String endTime;

    public EventBuilder() {
        name = DEFAULT_NAME;
        venue = DEFAULT_VENUE;
        date = DEFAULT_DATE;
        startTime = DEFAULT_START_TIME;
        endTime = DEFAULT_END_TIME;
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        venue = eventToCopy.getVenue();
        date = eventToCopy.getDate();
        startTime = eventToCopy.getStartTime();
        endTime = eventToCopy.getEndTime();
    }

    /**
     * Sets the {@code String name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code String venue} of the {@code Event} that we are building.
     */
    public EventBuilder withVenue(String venue) {
        this.venue = venue;
        return this;
    }

    /**
     * Sets the {@code String date} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code String startTime} of the {@code Event} that we are building.
     */
    public EventBuilder withStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Sets the {@code String endTime} of the {@code Event} that we are building.
     */
    public EventBuilder withEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public Event build() {
        return new Event(name, venue, date, startTime, endTime);
    }

}
```
###### \test\java\seedu\address\testutil\EventUtil.java
``` java
package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Event;

/**
 * A utility class for Event.
 */
public class EventUtil {

    /**
     * Returns an add command string for adding the {@code event}.
     */
    public static String getAddEventCommand(Event event) {
        return AddEventCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + event.getName() + " ");
        sb.append(PREFIX_VENUE + event.getVenue() + " ");
        sb.append(PREFIX_DATE + event.getDate() + " ");
        sb.append(PREFIX_START_TIME + event.getStartTime() + " ");
        sb.append(PREFIX_END_TIME + event.getEndTime() + " ");
        return sb.toString();
    }
}
```
###### \test\java\seedu\address\testutil\TypicalEvents.java
``` java
package seedu.address.testutil;

import static seedu.address.testutil.TypicalToDos.getTypicalToDos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event F1RACE = new EventBuilder().build();
    public static final Event GSS = new EventBuilder().withName("Great Singapore Sale").withVenue("Orchard")
            .withDate("09/06/2018").withStartTime("0900").withEndTime("2300").build();
    public static final Event HARIRAYA = new EventBuilder().withName("Hari Raya Haji").withVenue("Singapore")
            .withDate("22/08/2018").withStartTime("0000").withEndTime("2359").build();
    public static final Event ILIGHT = new EventBuilder().withName("iLight").withVenue("Marina Bay")
            .withDate("01/04/2018").withStartTime("1930").withEndTime("2359").build();
    public static final Event NDP = new EventBuilder().withName("National Day Parade").withVenue("Promenade")
            .withDate("09/08/2018").withStartTime("1700").withEndTime("1900").build();

    private TypicalEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : TypicalPersons.getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        for (Event event : getTypicalEvents()) {
            try {
                ab.addEvent(event);
            } catch (DuplicateEventException e) {
                throw new AssertionError("not possible");
            }
        }
        for (ToDo todo : getTypicalToDos()) {
            try {
                ab.addToDo(todo);
            } catch (DuplicateToDoException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(F1RACE, GSS, HARIRAYA, ILIGHT, NDP));
    }
}
```
###### \test\java\seedu\address\ui\CalendarDateTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertDateDisplaysEvent;

import org.junit.Test;

import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

public class CalendarDateTest extends GuiUnitTest {

    @Test
    public void display() {
        Event event = new EventBuilder().build();
        CalendarDate date = new CalendarDate(event);
        uiPartRule.setUiPart(date);
        assertCardDisplay(date, event);
    }

    @Test
    public void equals() {
        Event event = new EventBuilder().build();
        CalendarDate date = new CalendarDate(event);

        // same to-do, same index -> returns true
        CalendarDate copy = new CalendarDate(event);
        assertEquals(date, copy);

        // same object -> returns true
        assertEquals(date, date);

        // null -> returns false
        assertNotEquals(date, null);

        // different types -> returns false
        assertNotEquals(date, 0);

        // different to-do, same index -> returns false
        Event differentEvent = new EventBuilder().withDate("21/12/2012").build();
        assertNotEquals(date, new CalendarDate(differentEvent));
    }

    /**
     * Asserts that {@code toDoCard} displays the details of {@code expectedEvent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(CalendarDate date, Event expectedEvent) {
        guiRobot.pauseForHuman();
        // verify to-do details are displayed correctly
        assertDateDisplaysEvent(expectedEvent, date);
    }
}
```
###### \test\java\seedu\address\ui\CalendarTest.java
``` java
package seedu.address.ui;

import org.junit.Test;

public class CalendarTest extends GuiUnitTest {

    @Test
    public void equals() {
        /*Calendar calendar = new Calendar(null);
        assertEquals(calendar, new Calendar(null));*/
    }
}
```
###### \test\java\systemtests\ChangeTagColorCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_BROWN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagNotFoundException;

public class ChangeTagColorCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void changeTagColor() throws Exception {
        Model model = getModel();

        /* ---------- Performing change tag color operation while an unfiltered list is being shown ------------- */

        /* Case: change tag color fields, command with leading spaces, trailing spaces and multiple spaces
         * between each field -> changed
         */
        String command = " " + ChangeTagColorCommand.COMMAND_WORD + "  " + VALID_TAG_FRIEND
                + "  " + VALID_TAG_COLOR_BROWN + " ";
        Tag changedTag = new Tag(VALID_TAG_FRIEND, VALID_TAG_COLOR_BROWN);
        assertCommandSuccess(command, changedTag);

        /* Case: undo changeTagColoring the last tag in the list -> last tag restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo changeTagColoring the last tag in the list -> last tag changeTagColored again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateTag(new Tag(VALID_TAG_FRIEND), changedTag);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: changeTagColor a tag with new values same as existing values -> changeTagColored */
        command = ChangeTagColorCommand.COMMAND_WORD + " " + VALID_TAG_FRIEND + " " + VALID_TAG_COLOR_BROWN;
        assertCommandSuccess(command, changedTag);

        /* Case: tag specified not in list -> rejected
         */
        command = ChangeTagColorCommand.COMMAND_WORD + " " + VALID_TAG_COLOR_RED + " " + VALID_TAG_COLOR_BROWN;
        assertCommandFailure(command, ChangeTagColorCommand.MESSAGE_TAG_NOT_IN_LIST);

        /* Case: color specified is not supported by application -> rejected
         */
        command = ChangeTagColorCommand.COMMAND_WORD + " " + VALID_TAG_FRIEND + " " + INVALID_TAG_COLOR;
        assertCommandFailure(command, Tag.MESSAGE_TAG_COLOR_CONSTRAINTS);
    }

    /**
     * Performs the verification: <br>
     * 1. Asserts that result display node displays the success message of executing {@code ChangeTagColorCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the tag being updated to values
     * specified in {@code changeTagColoredTag}.<br>
     */
    private void assertCommandSuccess(String command, Tag changedTag) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateTag(new Tag(VALID_TAG_FRIEND), changedTag);
            expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (TagNotFoundException tnfe) {
            throw new IllegalArgumentException("Tag isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(ChangeTagColorCommand.MESSAGE_EDIT_TAG_SUCCESS, changedTag.name, changedTag.color));
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command node displays an empty string.<br>
     * 2. Asserts that the result display node displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command node has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command node displays {@code command}.<br>
     * 2. Asserts that result display node displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command node has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \test\java\systemtests\SwitchCommandSystemTest.java
``` java
package systemtests;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.logic.commands.SwitchCommand;
import seedu.address.model.Model;

public class SwitchCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void switchView() {
        final Model calendarModel = getModel();
        final Model timetableModel = getModel();
        timetableModel.switchView();

        /* Case: Application in Calendar view -> switched to Timetable view
         */
        assertCommandSuccess("   " + SwitchCommand.COMMAND_WORD + " view   ", calendarModel, timetableModel);

        assertCommandSuccess("   " + SwitchCommand.COMMAND_WORD + " view   ", timetableModel, calendarModel);
    }

    private void assertCommandSuccess(String command, Model model, Model expectedModel) {
        executeCommand(command);
        assertViewChanged(model, expectedModel);
    }

    private void assertCommandFailure(String command, Model model, Model expectedModel) {
        executeCommand(command);
        assertViewDidNotChange(model, expectedModel);
    }

    private void assertViewChanged(Model model, Model expectedModel) {
        Assert.assertEquals(model.calendarIsViewed(), expectedModel.calendarIsViewed());
    }

    private void assertViewDidNotChange(Model model, Model expectedModel) {
        Assert.assertNotEquals(model.calendarIsViewed(), expectedModel.calendarIsViewed());
    }
}
```
