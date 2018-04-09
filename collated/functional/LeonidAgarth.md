# LeonidAgarth
###### \java\seedu\address\commons\events\ui\CalendarChangedEvent.java
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
###### \java\seedu\address\commons\events\ui\TimetableChangedEvent.java
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
###### \java\seedu\address\logic\commands\AddEventCommand.java
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
###### \java\seedu\address\logic\commands\ChangeTagColorCommand.java
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
     * @param name of the tag to edit
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
        for (Tag tag: allTags) {
            if (tag.name.equals(tagName)) {
                tagToEdit = tag;
                return;
            }
        }
        throw new CommandException(MESSAGE_TAG_NOT_IN_LIST);
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
###### \java\seedu\address\logic\commands\SwitchCommand.java
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
            model.switchView();
            return new CommandResult(MESSAGE_SUCCESS_TIMETABLE);
        }
        model.indicateCalendarChanged();
        model.switchView();
        return new CommandResult(MESSAGE_SUCCESS_CALENDAR);
    }
}
```
###### \java\seedu\address\logic\parser\AddEventCommandParser.java
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
###### \java\seedu\address\logic\parser\AddressBookParser.java
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
```
###### \java\seedu\address\logic\parser\ChangeTagColorCommandParser.java
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
 * @throws ParseException if the user input does not conform the expected format
 */
public class ChangeTagColorCommandParser implements Parser<ChangeTagColorCommand> {

    @Override
    public ChangeTagColorCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim().replaceAll(" +", " ");;
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
###### \java\seedu\address\logic\parser\ParserUtil.java
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
###### \java\seedu\address\model\AddressBook.java
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
###### \java\seedu\address\model\event\Event.java
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
###### \java\seedu\address\model\event\exceptions\DuplicateEventException.java
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
###### \java\seedu\address\model\event\exceptions\EventNotFoundException.java
``` java
package seedu.address.model.event.exceptions;

/**
 * Signals that the operation is unable to find the specified event.
 */
public class EventNotFoundException extends Exception {
}
```
###### \java\seedu\address\model\event\UniqueEventList.java
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
 *
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
     * @throws EventNotFoundException if {@code target} could not be found in the list.
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
###### \java\seedu\address\model\event\WeeklyEvent.java
``` java
package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.Schedule;

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
        this.venue = schedule.getClassNo();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.day = schedule.getDayText();
        this.details = new String[]{};
    }

    public String getDay() {
        return day;
    }

    public ObservableList<String> getDetails() {
        ArrayList<String> temp = new ArrayList<String>(Arrays.asList(details));
        return FXCollections.observableArrayList(temp);
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
###### \java\seedu\address\model\Model.java
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
###### \java\seedu\address\model\ModelManager.java
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
###### \java\seedu\address\model\ModelManager.java
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
###### \java\seedu\address\model\ModelManager.java
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
###### \java\seedu\address\model\tag\Tag.java
``` java
    public static final String MESSAGE_TAG_COLOR_CONSTRAINTS = "Colors available are: "
            + "teal, red, yellow, blue, orange, brown, green, pink, black, grey";
    private static final String[] AVAILABLE_COLORS = new String[] {"teal", "red", "yellow", "blue", "orange", "brown",
        "green", "pink", "black", "grey", "undefined"};

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
###### \java\seedu\address\storage\AddressBookStorage.java
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
###### \java\seedu\address\storage\StorageManager.java
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
###### \java\seedu\address\storage\StorageManager.java
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
###### \java\seedu\address\storage\XmlAdaptedEvent.java
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
###### \java\seedu\address\storage\XmlAddressBookStorage.java
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
###### \java\seedu\address\ui\Calendar.java
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
            if (month != currentYearMonth.getMonthValue()) {
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
###### \java\seedu\address\ui\CalendarDate.java
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
###### \java\seedu\address\ui\MainWindow.java
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
###### \java\seedu\address\ui\Timetable.java
``` java
package seedu.address.ui;

import java.util.ArrayList;
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
                node.setModule("module1hr", mod);
            } else {
                TimetableSlot node = getSlotNode(day, startTime);
                node.setModule("module2hrtop", mod);
                WeeklyEvent blank = new WeeklyEvent(mod.getName(), "", "", "", "", "");
                node = getSlotNode(day, startTime + 100);
                node.setModule("module2hrbottom", blank);
                node.getModule().setText("");
            }
        }
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
###### \java\seedu\address\ui\TimetableSlot.java
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

    public void setModule(String style, WeeklyEvent mod) {
        setStyleClass(style, AVAILABLE_COLORS[Math.abs(mod.getName().hashCode()) % AVAILABLE_COLORS.length]);
        module.setText(mod.getName());
        if (!mod.getDetails().isEmpty() && mod.getDetails().size() >= 2) {
            lectureType.setText(mod.getDetails().get(1));
        }
        venue.setText(mod.getVenue());
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
###### \java\seedu\address\ui\UiManager.java
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
###### \resources\view\Calendar.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="calendarListView" VBox.vgrow="NEVER" HBox.hgrow="NEVER"/>
</VBox>
```
###### \resources\view\CalendarDate.fxml
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
###### \resources\view\DarkTheme.css
``` css
#calendarPlaceholder .yearMonth {
    -fx-font: 25px Tahoma;
    -fx-fill: #95adf0;
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
}

#calendarPlaceholder .module2hrtop {
    -fx-border-radius: 6 6 0 0;
    -fx-border-width: 0;
    -fx-background-radius: 10 10 0 0;
}

#calendarPlaceholder .module2hrbottom {
    -fx-border-radius: 0 0 6 6;
    -fx-border-width: 0 0 3px 0;
    -fx-background-radius: 0 0 10 10;
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
###### \resources\view\Timetable.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
   <ListView fx:id="timetableListView" VBox.vgrow="NEVER" HBox.hgrow="NEVER"/>
</VBox>
```
###### \resources\view\TimetableSlot.fxml
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
