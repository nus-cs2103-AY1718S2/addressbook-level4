# SuxianAlicia
###### \java\seedu\address\commons\events\model\CalendarManagerChangedEvent.java
``` java
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyCalendarManager;

/**
 * Indicates the CalendarManager in the model has changed
 */
public class CalendarManagerChangedEvent extends BaseEvent {

    public final ReadOnlyCalendarManager data;

    public CalendarManagerChangedEvent(ReadOnlyCalendarManager data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of calendar entries " + data.getCalendarEntryList().size();
    }
}
```
###### \java\seedu\address\commons\events\ui\CalendarEntryPanelSelectionChangedEvent.java
``` java
import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.CalendarEntryCard;

/**
 * Represents a selection change in the Calendar Entry List Panel
 */
public class CalendarEntryPanelSelectionChangedEvent extends BaseEvent {

    private final CalendarEntryCard newSelection;

    public CalendarEntryPanelSelectionChangedEvent(CalendarEntryCard newSelection) {
        this.newSelection = newSelection;
    }

    public CalendarEntryCard getNewSelection() {
        return newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ChangeCalendarDateRequestEvent.java
``` java
import java.time.LocalDate;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a request to display given {@code date} in calendar.
 */
public class ChangeCalendarDateRequestEvent extends BaseEvent {

    private final LocalDate date;

    public ChangeCalendarDateRequestEvent(LocalDate date) {
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
###### \java\seedu\address\commons\events\ui\ChangeCalendarPageRequestEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates request to change page that calendar is displaying.
 * A page represents a day, week or month, depending on the current view of the calendar.
 */
public class ChangeCalendarPageRequestEvent extends BaseEvent {

    private final String requestType;

    public ChangeCalendarPageRequestEvent(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ChangeCalendarViewRequestEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates request to display calendar.
 */
public class ChangeCalendarViewRequestEvent extends BaseEvent {

    private final String calendarView;

    public ChangeCalendarViewRequestEvent(String calendarView) {
        this.calendarView = calendarView;
    }

    public String getView() {
        return calendarView;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\DisplayCalendarEntryListEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates request to display calendar entry list.
 */
public class DisplayCalendarEntryListEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\DisplayOrderListEvent.java
``` java
public class DisplayOrderListEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\util\CalendarEntryConversionUtil.java
``` java
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;

import javafx.collections.ObservableList;
import seedu.address.model.entry.CalendarEntry;

/**
 * Provides utilities to convert between {@code Entry} used in CalendarFX and its Model Version, {@code CalendarEntry}.
 */
public class CalendarEntryConversionUtil {

    /**
     * Converts {@code CalendarEntry} to {@code Entry} used in CalendarFX.
     */
    public static Entry<String> convertToEntry(CalendarEntry calEntry) {
        requireNonNull(calEntry);

        Interval entryInterval = new Interval(calEntry.getStartDate().getLocalDate(),
                calEntry.getStartTime().getLocalTime(),
                calEntry.getEndDate().getLocalDate(),
                calEntry.getEndTime().getLocalTime());

        return new Entry<>(calEntry.getEntryTitle().toString(), entryInterval);
    }

    /**
     * Converts given list of calendarEntries to {@code Entry} used in CalendarFX and return list of {@code Entry}.
     */
    public static List<Entry<?>> convertEntireListToEntries(ObservableList<CalendarEntry> calendarEntries) {
        List<Entry<?>> convertedEntries = new ArrayList<>();

        for (CalendarEntry ce: calendarEntries) {
            convertedEntries.add(CalendarEntryConversionUtil.convertToEntry(ce));
        }

        return convertedEntries;
    }
}
```
###### \java\seedu\address\commons\util\DateUtil.java
``` java
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper functions for handling Date related operations.
 * Ensures that strings conform to a given Date Format.
 */
public class DateUtil {
    public static final String DATE_VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}"; // format
    public static final String DATE_VALIDATION_FORMAT = "dd-MM-yyyy"; // legal dates
    public static final String DATE_PATTERN = "dd-MM-yyyy";

    /**
     * Returns true if given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_VALIDATION_FORMAT);
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(test);
        } catch (ParseException e) {
            return false;
        }

        return test.matches(DATE_VALIDATION_REGEX);
    }

    /**
     * Converts given string to a {@code LocalDate}.
     */
    public static LocalDate convertStringToDate(String date) throws DateTimeParseException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate convertedDate = LocalDate.parse(date, format);

        return convertedDate;
    }
}
```
###### \java\seedu\address\commons\util\EntryTimeConstraintsUtil.java
``` java
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;

/**
 * Helper functions for checking StartDate, EndDate, StartTime, EndTime of {@code CalendarEntry}.
 */
public class EntryTimeConstraintsUtil {

    public static final String ENTRY_DURATION_CONSTRAINTS =
            "Entry must last at least 15 minutes if ending in same day."; //Constraint of CalendarFX entries
    public static final String START_AND_END_DATE_CONSTRAINTS = "Start Date cannot be later than End Date.";
    public static final String START_AND_END_TIME_CONSTRAINTS =
            "Start Time cannot be later than End Time if Entry ends on same date.";

    private static final long MINIMAL_DURATION = 15; //Constraint of CalendarFX entries

    /**
     * Returns true if duration between start time and end time is less than 15 minutes.
     * This is a constraint that CalendarFX has. Event duration must last at least 15 minutes.
     */
    private static boolean eventIsShorterThanFifteenMinutes(StartDate startDate, EndDate endDate,
                                                            StartTime startTime, EndTime endTime) {
        requireAllNonNull(startDate, endDate, startTime, endTime);

        LocalDateTime startDateAndTime = LocalDateTime.of(startDate.getLocalDate(), startTime.getLocalTime());
        LocalDateTime endDateAndTime = LocalDateTime.of(endDate.getLocalDate(), endTime.getLocalTime());
        if (Duration.between(startDateAndTime, endDateAndTime).toMinutes() < MINIMAL_DURATION) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if given start time is later than end time.
     * Start time cannot be later than End time if event ends on the same date.
     */
    private static boolean startTimeIsLaterThanEndTime(StartTime startTime, EndTime endTime) {
        requireAllNonNull(startTime, endTime);
        return startTime.getLocalTime().isAfter(endTime.getLocalTime());
    }

    /**
     * Returns true if given start date is later than end date.
     * Start Date cannot be later than End Date as it violates the meaning of the terms.
     */
    private static boolean startDateIsLaterThanEndDate(StartDate startDate, EndDate endDate) {
        requireAllNonNull(startDate, endDate);
        return startDate.getLocalDate().isAfter(endDate.getLocalDate());
    }

    /**
     * Checks 3 constraints:
     * 1. {@code StartDate} must not be after {@code EndDate}.
     * 2. {@code Start Time} must not be after {@code EndTime} if Calendar Entry ends on same Date.
     * 3. Duration of entry cannot be less than 15 minutes.
     */
    public static void checkCalendarEntryTimeConstraints(
           StartDate startDate, EndDate endDate, StartTime startTime, EndTime endTime) throws IllegalValueException {

        if (startDateIsLaterThanEndDate(startDate, endDate)) {
            throw new IllegalValueException(START_AND_END_DATE_CONSTRAINTS);
        }

        if (startDate.toString().equals(endDate.toString()) && startTimeIsLaterThanEndTime(startTime, endTime)) {
            throw new IllegalValueException(START_AND_END_TIME_CONSTRAINTS);
        }

        if (eventIsShorterThanFifteenMinutes(startDate, endDate, startTime, endTime)) {
            throw new IllegalValueException(ENTRY_DURATION_CONSTRAINTS);
        }
    }
}
```
###### \java\seedu\address\commons\util\TimeUtil.java
``` java
import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper functions for handling strings representing Time.
 * Ensures that strings conform to a given Time Format.
 */
public class TimeUtil {
    public static final String TIME_VALIDATION_REGEX = "\\d{2}:\\d{2}"; // format
    public static final String TIME_VALIDATION_FORMAT = "HH:mm"; // legal dates
    public static final String TIME_PATTERN = "HH:mm";

    /**
     * Returns true if given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        requireNonNull(test);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_VALIDATION_FORMAT);
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(test);
        } catch (ParseException e) {
            return false;
        }
        return test.matches(TIME_VALIDATION_REGEX);
    }

    /**
     * Converts given string to a {@code LocalTime}.
     */
    public static LocalTime convertStringToTime(String time) throws DateTimeParseException {
        requireNonNull(time);
        DateTimeFormatter format = DateTimeFormatter.ofPattern(TIME_PATTERN);
        LocalTime convertedTime = LocalTime.parse(time, format);

        return convertedTime;
    }
}
```
###### \java\seedu\address\logic\commands\AddEntryCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeCalendarDateRequestEvent;
import seedu.address.commons.events.ui.DisplayCalendarEntryListEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;

/**
 * Adds a calendar entry to calendar manager.
 */
public class AddEntryCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "entryadd";
    public static final String COMMAND_ALIAS = "ea";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_ENTRY_TITLE + "ENTRY_TITLE "
            + PREFIX_START_DATE + "[START_DATE] "
            + PREFIX_END_DATE + "END_DATE "
            + PREFIX_START_TIME + "[START_TIME] "
            + PREFIX_END_TIME + "END_TIME";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a calendar entry to the calendar"
            + " and displays the calendar with the entry's start date.\n"
            + "Parameters: "
            + PREFIX_ENTRY_TITLE + "ENTRY_TITLE "
            + "[" + PREFIX_START_DATE + "START_DATE] "
            + PREFIX_END_DATE + "END_DATE "
            + "[" + PREFIX_START_TIME + "START_TIME] "
            + PREFIX_END_TIME + "END_TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ENTRY_TITLE + "Meeting with Boss "
            + PREFIX_START_DATE + "05-05-2018 "
            + PREFIX_END_DATE + "05-05-2018 "
            + PREFIX_START_TIME + "10:00 "
            + PREFIX_END_TIME + "12:30";

    public static final String MESSAGE_ADD_ENTRY_SUCCESS = "Added Entry [%1$s]";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the calendar.";

    private final CalendarEntry calEntryToAdd;

    /**
     * Creates an AddEntryCommand to add specified {@code CalendarEntry}.
     */
    public AddEntryCommand(CalendarEntry calEntry) {
        requireNonNull(calEntry);
        this.calEntryToAdd = calEntry;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addCalendarEntry(calEntryToAdd);
            EventsCenter.getInstance().post(new ChangeCalendarDateRequestEvent(calEntryToAdd.getDateToDisplay()));
            EventsCenter.getInstance().post(new DisplayCalendarEntryListEvent());
            return new CommandResult(String.format(MESSAGE_ADD_ENTRY_SUCCESS, calEntryToAdd));
        } catch (DuplicateCalendarEntryException dcee) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEntryCommand // instanceof handles nulls
                && calEntryToAdd.equals(((AddEntryCommand) other).calEntryToAdd));
    }
}
```
###### \java\seedu\address\logic\commands\CalendarJumpCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TARGET_DATE;

import java.time.LocalDate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeCalendarDateRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Displays specified date in Calendar.
 */
public class CalendarJumpCommand extends Command {

    public static final String COMMAND_WORD = "calendarjump";
    public static final String COMMAND_ALIAS = "caljump";

    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_TARGET_DATE + "TARGET_DATE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays given date in Calendar.\n"
            + "Parameters: " + PREFIX_TARGET_DATE + "TARGET_DATE (must be in format DD-MM-YYY)\n"
            + "Example: " + COMMAND_WORD + " 06-06-2018";

    public static final String MESSAGE_CALENDAR_JUMP_SUCCESS = "Displayed Date: %1$s in Calendar.";

    private final LocalDate date;
    private final String dateString;

    /**
     * Creates CalendarJumpCommand with specified {@code LocalDate} to display in Calendar.
     */
    public CalendarJumpCommand(LocalDate date, String dateString) {
        requireAllNonNull(date, dateString);
        this.date = date;
        this.dateString = dateString;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(date);
        EventsCenter.getInstance().post(new ChangeCalendarDateRequestEvent(date));
        return new CommandResult(String.format(MESSAGE_CALENDAR_JUMP_SUCCESS, dateString));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalendarJumpCommand // instanceof handles nulls
                && date.equals(((CalendarJumpCommand) other).date)
                && dateString.equals(((CalendarJumpCommand) other).dateString));
    }
}
```
###### \java\seedu\address\logic\commands\DeleteEntryCommand.java
``` java
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayCalendarEntryListEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.exceptions.CalendarEntryNotFoundException;

/**
 * Deletes a calendar entry identified using it's last displayed index from the address book.
 */
public class DeleteEntryCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "entrydelete";
    public static final String COMMAND_ALIAS = "ed";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the calendar entry identified by the index number used in the entry listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ENTRY_SUCCESS = "Deleted Calendar Entry: %1$s";

    private final Index targetIndex;

    private CalendarEntry entryToDelete;

    public DeleteEntryCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(entryToDelete);
        try {
            model.deleteCalendarEntry(entryToDelete);
        } catch (CalendarEntryNotFoundException cenfe) {
            throw new AssertionError("The target calendar entry cannot be missing");
        }

        EventsCenter.getInstance().post(new DisplayCalendarEntryListEvent());
        return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, entryToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<CalendarEntry> lastShownList = model.getFilteredCalendarEntryList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        entryToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEntryCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteEntryCommand) other).targetIndex) // state check
                && Objects.equals(this.entryToDelete, ((DeleteEntryCommand) other).entryToDelete));
    }
}
```
###### \java\seedu\address\logic\commands\DeleteGroupCommand.java
``` java
import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.exceptions.GroupNotFoundException;

/**
 * Deletes a group specified by user from address book.
 */
public class DeleteGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "groupdelete";
    public static final String COMMAND_ALIAS = "gd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified group from all persons in address book.\n"
            + "Parameters: GROUP_NAME (must be alphanumeric)\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted GROUP: %1$s";
    public static final String MESSAGE_GROUP_NOT_FOUND = "Group does not exist in address book.";

    private Group groupToDelete;

    public DeleteGroupCommand(Group targetGroup) {
        this.groupToDelete = targetGroup;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(groupToDelete);
        try {
            model.deleteGroup(groupToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, groupToDelete));
        } catch (GroupNotFoundException e) {
            throw new CommandException(MESSAGE_GROUP_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && Objects.equals(this.groupToDelete, ((DeleteGroupCommand) other).groupToDelete)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\DeletePreferenceCommand.java
``` java
import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Preference;
import seedu.address.model.tag.exceptions.PreferenceNotFoundException;

/**
 * Deletes a preference specified by user from address book.
 */
public class DeletePreferenceCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "prefdelete";
    public static final String COMMAND_ALIAS = "pd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified preference from all persons in address book.\n"
            + "Parameters: PREFERENCE_NAME (must be alphanumeric)\n"
            + "Example: " + COMMAND_WORD + " computers";

    public static final String MESSAGE_DELETE_PREFERENCE_SUCCESS = "Deleted PREFERENCE: %1$s";
    public static final String MESSAGE_PREFERENCE_NOT_FOUND = "Preference does not exist in address book.";

    private Preference prefToDelete;

    public DeletePreferenceCommand(Preference targetPref) {
        this.prefToDelete = targetPref;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(prefToDelete);
        try {
            model.deletePreference(prefToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PREFERENCE_SUCCESS, prefToDelete));
        } catch (PreferenceNotFoundException e) {
            throw new CommandException(MESSAGE_PREFERENCE_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePreferenceCommand // instanceof handles nulls
                && Objects.equals(this.prefToDelete, ((DeletePreferenceCommand) other).prefToDelete)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FindGroupCommand.java
``` java
import seedu.address.model.person.GroupsContainKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose groups contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindGroupCommand extends Command {

    public static final String COMMAND_WORD = "groupfind";
    public static final String COMMAND_ALIAS = "gf";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "KEYWORD "
            + "[MORE KEYWORDS]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose groups contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends colleagues neighbours";

    private final GroupsContainKeywordsPredicate groupsContainKeywordsPredicate;

    public FindGroupCommand(GroupsContainKeywordsPredicate predicate) {
        this.groupsContainKeywordsPredicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(groupsContainKeywordsPredicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindGroupCommand // instanceof handles nulls
                && this.groupsContainKeywordsPredicate.equals
                (((FindGroupCommand) other).groupsContainKeywordsPredicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FindPreferenceCommand.java
``` java
import seedu.address.model.person.PreferencesContainKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose preferences contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPreferenceCommand extends Command {

    public static final String COMMAND_WORD = "preffind";
    public static final String COMMAND_ALIAS = "pf";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "KEYWORD "
            + "[MORE KEYWORDS]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose preferences contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " shoes computers videoGames";

    private final PreferencesContainKeywordsPredicate preferencesContainKeywordsPredicate;

    public FindPreferenceCommand(PreferencesContainKeywordsPredicate predicate) {
        this.preferencesContainKeywordsPredicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(preferencesContainKeywordsPredicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPreferenceCommand // instanceof handles nulls
                && this.preferencesContainKeywordsPredicate.equals
                (((FindPreferenceCommand) other).preferencesContainKeywordsPredicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ListCalendarEntryCommand.java
``` java
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayCalendarEntryListEvent;
import seedu.address.model.Model;

/**
 * List and display all calendar entries in the address book to the user.
 */
public class ListCalendarEntryCommand extends Command {

    public static final String COMMAND_WORD = "entrylist";
    public static final String COMMAND_ALIAS = "el";

    public static final String MESSAGE_SUCCESS = "Listed all calendar entries";

    @Override
    public CommandResult execute() {
        model.updateFilteredCalendarEventList(Model.PREDICATE_SHOW_ALL_CALENDAR_ENTRIES);
        EventsCenter.getInstance().post(new DisplayCalendarEntryListEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\ListOrderCommand.java
``` java
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayOrderListEvent;

/**
 * List and display all orders in the address book to the user.
 */
public class ListOrderCommand extends Command {
    public static final String COMMAND_WORD = "orderlist";
    public static final String COMMAND_ALIAS = "ol";

    public static final String MESSAGE_SUCCESS = "Listed all orders";


    @Override
    public CommandResult execute() {
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        EventsCenter.getInstance().post(new DisplayOrderListEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\ViewBackCommand.java
``` java
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_BACK;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Switches current page of Calendar to previous page.
 * Depending on the current viewing format of Calendar, the previous page can be the previous day, previous week,
 * or previous month of the current displayed date.
 * This command will display the calendar if it is not displayed when command is executed.
 */
public class ViewBackCommand extends Command {

    public static final String COMMAND_WORD = "calendarback";
    public static final String COMMAND_ALIAS = "calback";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays previous page of current displayed date in calendar.\n"
            + "Depending on the current viewing format of Calendar, the previous page can be the previous day,"
            + " previous week, or previous month of the current displayed date.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_VIEW_CALENDAR_BACK_SUCCESS = "Displayed previous page in Calendar.";

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ChangeCalendarPageRequestEvent(REQUEST_BACK));
        return new CommandResult(MESSAGE_VIEW_CALENDAR_BACK_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewBackCommand); // instanceof handles nulls
    }
}
```
###### \java\seedu\address\logic\commands\ViewCalendarCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.ui.util.CalendarFxUtil.DAY_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.MONTH_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.WEEK_VIEW;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeCalendarViewRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Displays Calendar in ContactSails in 3 possible viewing formats, Day, Week or Month.
 */
public class ViewCalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";
    public static final String COMMAND_ALIAS = "cal";

    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "[VIEW_FORMAT]";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays Calendar in a specified viewing format.\n"
            + "Parameters: [VIEW_FORMAT] (must be either \"day\", \"week\" or \"month\" without captions)\n"
            + "If no parameters are given or given parameter does not follow the accepted keywords,"
            + " calendar will display in Day-View.\n"
            + "Example: " + COMMAND_WORD + " day";

    public static final String MESSAGE_SHOW_CALENDAR_SUCCESS = "Display Calendar in %1$s-View.";

    private final String view;

    public ViewCalendarCommand(String view) {
        requireNonNull(view);
        String trimmedView = view.trim();

        if (trimmedView.equalsIgnoreCase(MONTH_VIEW)) {
            this.view = MONTH_VIEW;
        } else if (trimmedView.equalsIgnoreCase(WEEK_VIEW)) {
            this.view = WEEK_VIEW;
        } else { //If view is equal to DAY_VIEW, is empty or does not match any of the accepted keywords
            this.view = DAY_VIEW;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(view);
        EventsCenter.getInstance().post(new ChangeCalendarViewRequestEvent(view));
        return new CommandResult(String.format(MESSAGE_SHOW_CALENDAR_SUCCESS, view));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCalendarCommand
                && this.view.equals(((ViewCalendarCommand) other).view)); // instanceof handles nulls
    }

}
```
###### \java\seedu\address\logic\commands\ViewNextCommand.java
``` java
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_NEXT;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Switches current page of Calendar to next page.
 * Depending on the current viewing format of Calendar, the next page can be the next day, next week
 * or next month of the current displayed date.
 * This command will display the calendar if it is not displayed when command is executed.
 */
public class ViewNextCommand extends Command {

    public static final String COMMAND_WORD = "calendarnext";
    public static final String COMMAND_ALIAS = "calnext";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays next page of current displayed date in calendar.\n"
            + "Depending on the current viewing format of Calendar, the next page can be the next day,"
            + " next week or next month of the current displayed date.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_VIEW_CALENDAR_NEXT_SUCCESS = "Displayed next page in Calendar.";

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ChangeCalendarPageRequestEvent(REQUEST_NEXT));
        return new CommandResult(MESSAGE_VIEW_CALENDAR_NEXT_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewNextCommand); // instanceof handles nulls
    }
}
```
###### \java\seedu\address\logic\commands\ViewTodayCommand.java
``` java
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_TODAY;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Switches currently displayed date in Calendar to today's date.
 * This command will display the calendar if it is not displayed when command is executed.
 */
public class ViewTodayCommand extends Command {

    public static final String COMMAND_WORD = "calendartoday";
    public static final String COMMAND_ALIAS = "caltoday";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays today's date in calendar.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_VIEW_CALENDAR_TODAY_SUCCESS = "Displayed Today in Calendar.";

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ChangeCalendarPageRequestEvent(REQUEST_TODAY));
        return new CommandResult(MESSAGE_VIEW_CALENDAR_TODAY_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewTodayCommand); // instanceof handles nulls
    }
}
```
###### \java\seedu\address\logic\parser\AddEntryCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.EntryTimeConstraintsUtil;
import seedu.address.logic.commands.AddEntryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.EntryTitle;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;

/**
 * Parses input arguments and creates a new AddEntryCommand object
 */
public class AddEntryCommandParser implements Parser<AddEntryCommand> {

    public static final String STANDARD_START_TIME = "00:00"; //Start Time of event if StartTime not given

    /**
     * Parses the given {@code String} of arguments in the context of the AddEntryCommand
     * and returns an AddEntryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddEntryCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_ENTRY_TITLE, PREFIX_START_DATE, PREFIX_END_DATE,
                        PREFIX_START_TIME, PREFIX_END_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_ENTRY_TITLE, PREFIX_END_DATE, PREFIX_END_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEntryCommand.MESSAGE_USAGE));
        }

        try {
            EntryTitle entryTitle = ParserUtil.parseEventTitle(argMultimap.getValue(PREFIX_ENTRY_TITLE)).get();
            EndDate endDate = ParserUtil.parseEndDate(argMultimap.getValue(PREFIX_END_DATE)).get();
            StartDate startDate;

            // If no Start Date is given, Start Date will be the same date as End Date
            if (!argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
                startDate = ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_END_DATE)).get();
            } else {
                startDate = ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_START_DATE)).get();
            }

            EndTime endTime = ParserUtil.parseEndTime(argMultimap.getValue(PREFIX_END_TIME)).get();
            StartTime startTime;

            // If no Start Time is given, Start Time will be 00:00
            if (!argMultimap.getValue(PREFIX_START_TIME).isPresent()) {
                startTime = ParserUtil.parseStartTime(STANDARD_START_TIME);
            } else {
                startTime = ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_START_TIME)).get();
            }

            EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints(startDate, endDate, startTime, endTime);

            CalendarEntry calendarEntry = new CalendarEntry(entryTitle, startDate, endDate, startTime, endTime);
            return new AddEntryCommand(calendarEntry);

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
###### \java\seedu\address\logic\parser\CalendarJumpCommandParser.java
``` java

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TARGET_DATE;

import java.time.LocalDate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CalendarJumpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CalendarJumpCommand object
 */
public class CalendarJumpCommandParser implements Parser<CalendarJumpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CalendarJumpCommand
     * and returns an CalendarJumpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CalendarJumpCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_TARGET_DATE);

        if (!isPrefixPresent(argMultimap, PREFIX_TARGET_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarJumpCommand.MESSAGE_USAGE));
        }

        try {
            String dateString = argMultimap.getValue(PREFIX_TARGET_DATE).get();
            LocalDate date = ParserUtil.parseTargetDate(dateString);
            return new CalendarJumpCommand(date, dateString);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    private boolean isPrefixPresent(ArgumentMultimap argMultimap, Prefix prefixTargetDate) {
        return argMultimap.getValue(prefixTargetDate).isPresent();
    }
}
```
###### \java\seedu\address\logic\parser\DeleteEntryCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteEntryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteEntryCommand object
 */
public class DeleteEntryCommandParser implements Parser<DeleteEntryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEntryCommand
     * and returns an DeleteEntryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteEntryCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteEntryCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEntryCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\DeleteGroupCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Group;

/**
 * Parses input arguments and creates a new DeleteGroupCommand object.
 */
public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGroupCommand
     * and returns an DeleteGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteGroupCommand parse(String userInput) throws ParseException {
        try {
            Group group = ParserUtil.parseGroup(userInput);
            return new DeleteGroupCommand(group);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\DeletePreferenceCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeletePreferenceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Preference;

/**
 * Parses given arguments and creates a new DeletePreferenceCommand object.
 */
public class DeletePreferenceCommandParser implements Parser<DeletePreferenceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePreferenceCommand
     * and returns an DeletePreferenceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePreferenceCommand parse(String userInput) throws ParseException {
        try {
            Preference preference = ParserUtil.parsePreference(userInput);
            return new DeletePreferenceCommand(preference);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePreferenceCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\EditEntryCommandParser.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditEntryCommand;
import seedu.address.logic.commands.EditEntryCommand.EditEntryDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditEntryCommand object.
 */
public class EditEntryCommandParser implements Parser<EditEntryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEntryCommand
     * and returns an EditEntryCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public EditEntryCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_ENTRY_TITLE, PREFIX_START_DATE, PREFIX_END_DATE,
                        PREFIX_START_TIME, PREFIX_END_TIME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEntryCommand.MESSAGE_USAGE));
        }

        EditEntryDescriptor editEntryDescriptor = new EditEntryDescriptor();

        try {
            ParserUtil.parseEventTitle(argMultimap.getValue(PREFIX_ENTRY_TITLE))
                    .ifPresent(editEntryDescriptor::setEntryTitle);
            ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_START_DATE))
                    .ifPresent(editEntryDescriptor::setStartDate);
            ParserUtil.parseEndDate(argMultimap.getValue(PREFIX_END_DATE))
                    .ifPresent(editEntryDescriptor::setEndDate);
            ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_START_TIME))
                    .ifPresent(editEntryDescriptor::setStartTime);
            ParserUtil.parseEndTime(argMultimap.getValue(PREFIX_END_TIME))
                    .ifPresent(editEntryDescriptor::setEndTime);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editEntryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEntryCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEntryCommand(index, editEntryDescriptor);
    }
}
```
###### \java\seedu\address\logic\parser\FindGroupCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.GroupsContainKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindGroupCommand object
 */
public class FindGroupCommandParser implements Parser<FindGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindGroupCommand
     * and returns an FindGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindGroupCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindGroupCommand.MESSAGE_USAGE));
        }

        String[] groupKeywords = trimmedArgs.split("\\s+");

        return new FindGroupCommand(new GroupsContainKeywordsPredicate(Arrays.asList(groupKeywords)));
    }
}
```
###### \java\seedu\address\logic\parser\FindPreferenceCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindPreferenceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PreferencesContainKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPreferenceCommand object
 */
public class FindPreferenceCommandParser implements Parser<FindPreferenceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPreferenceCommand
     * and returns an FindPreferenceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPreferenceCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPreferenceCommand.MESSAGE_USAGE));
        }

        String[] preferenceKeywords = trimmedArgs.split("\\s+");

        return new FindPreferenceCommand(new PreferencesContainKeywordsPredicate(Arrays.asList(preferenceKeywords)));
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes group from all persons who has the group
     * @throws GroupNotFoundException if the {@code toRemove} is not in this {@code AddressBook}.
     */
    public void removeGroup(Group toRemove) throws GroupNotFoundException {
        if (groupTags.contains(toRemove)) {
            persons.removeGroupFromAllPersons(toRemove);
            groupTags.remove(toRemove);
        } else {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Removes preference from all persons who has the preference
     * @throws PreferenceNotFoundException if the {@code toRemove} is not in this {@code AddressBook}.
     */
    public void removePreference(Preference toRemove) throws PreferenceNotFoundException {
        if (prefTags.contains(toRemove)) {
            persons.removePrefFromAllPersons(toRemove);
            prefTags.remove(toRemove);
        } else {
            throw new PreferenceNotFoundException();
        }
    }

    /**
     * Solution below adapted from
     * https://github.com/se-edu/addressbook-level4/pull/790/commits/48ba8e95de5d7eae883504d40e6795c857dae3c2
     * Removes unused groups in groupTags.
     */
    private void removeUnusedGroups() {
        ObservableList<Person> list = persons.getInternalList();
        UniqueGroupList newList = new UniqueGroupList();

        for (Person p: list) {
            newList.mergeFrom(new UniqueGroupList(p.getGroupTags()));
        }
        setGroupTags(newList.toSet());
    }

    /**
     * Solution below adapted from
     * https://github.com/se-edu/addressbook-level4/pull/790/commits/48ba8e95de5d7eae883504d40e6795c857dae3c2
     * Removes unused preferences in prefTags.
     */
    private void removeUnusedPreferences() {
        ObservableList<Person> list = persons.getInternalList();
        UniquePreferenceList newList = new UniquePreferenceList();

        for (Person p: list) {
            newList.mergeFrom(new UniquePreferenceList(p.getPreferenceTags()));
        }
        setPreferenceTags(newList.toSet());
    }
```
###### \java\seedu\address\model\CalendarManager.java
``` java
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.calendarfx.model.Calendar;

import javafx.collections.ObservableList;
import seedu.address.commons.util.CalendarEntryConversionUtil;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.UniqueCalendarEntryList;
import seedu.address.model.entry.exceptions.CalendarEntryNotFoundException;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;

/**
 * Manages {@code Calendar} as defined in CalendarFX and {@code UniqueCalendarEntryList},
 * which contains {@code CalendarEntry} to be added to {@code Calendar}.
 */
public class CalendarManager implements ReadOnlyCalendarManager {
    private final Calendar calendar;
    private final UniqueCalendarEntryList calendarEntryList;

    public CalendarManager() {
        calendarEntryList = new UniqueCalendarEntryList();
        calendar = new Calendar();
        calendar.setReadOnly(true);
        calendar.setStyle(Calendar.Style.STYLE1);
    }

    public CalendarManager(ReadOnlyCalendarManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code CalendarManager} with {@code newData}.
     * Updates the Calendar with calendar entries in {@code calEntries}.
     */
    public void resetData(ReadOnlyCalendarManager newData) {
        requireNonNull(newData);

        List<CalendarEntry> calEntries = new ArrayList<>(newData.getCalendarEntryList());

        try {
            setCalendarEntries(calEntries);
        } catch (DuplicateCalendarEntryException dcee) {
            throw new AssertionError("Calendar Manager should not have duplicate calendar entries.");
        }
        updateCalendar();
    }

    /**
     * Updates Calendar with entries converted from {@code calendarEntryList}.
     */
    private void updateCalendar() {
        calendar.clear();
        calendar.addEntries(
                CalendarEntryConversionUtil.convertEntireListToEntries(calendarEntryList.asObservableList()));
    }

    /**
     * Sets {@code calendarEntryList} to match the given list of calendar entries.
     */
    private void setCalendarEntries(List<CalendarEntry> calEntries)
            throws DuplicateCalendarEntryException {
        calendarEntryList.setCalEntryList(calEntries);
    }

    @Override
    public ObservableList<CalendarEntry> getCalendarEntryList() {
        return calendarEntryList.asObservableList();
    }

    public Calendar getCalendar() {
        return calendar;
    }

    // Managing CalendarEntries operations

    /**
     * Adds a calendar entries to list of calendar entries in calendar manager.
     * @throws DuplicateCalendarEntryException
     * if there exist an equivalent calendar entry in calendar manager.
     */
    public void addCalendarEntry(CalendarEntry toAdd) throws DuplicateCalendarEntryException {
        calendarEntryList.add(toAdd);
        updateCalendar();
    }

    /**
     * Removes an existing calendar entry in list of calendar entries and from the calendar itself.
     * @throws CalendarEntryNotFoundException
     * if given calendar entry does not exist in list of calendar entry
     */
    public void deleteCalendarEntry(CalendarEntry entryToDelete) throws CalendarEntryNotFoundException {
        if (!calendarEntryList.remove(entryToDelete)) {
            throw new CalendarEntryNotFoundException();
        } else {
            updateCalendar();
        }
    }

    /**
     * Replaces the given calendar entry {@code target} in the list with {@code editedEntry}.
     * Updates the Calendar to show the new result.
     */
    public void updateCalendarEntry(CalendarEntry entryToEdit, CalendarEntry editedEntry)
            throws DuplicateCalendarEntryException, CalendarEntryNotFoundException {
        requireNonNull(editedEntry);
        calendarEntryList.setCalendarEntry(entryToEdit, editedEntry);
        updateCalendar();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalendarManager // instanceof handles nulls
                && this.calendarEntryList.equals(((CalendarManager) other).calendarEntryList));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(calendar, calendarEntryList);
    }
}
```
###### \java\seedu\address\model\event\CalendarEntry.java
``` java
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a Calendar Event in address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CalendarEntry {

    private final EntryTitle entryTitle;
    private final StartDate startDate;
    private final EndDate endDate;
    private final StartTime startTime;
    private final EndTime endTime;

    /**
     * Every field must be present, and not null.
     */
    public CalendarEntry(EntryTitle entryTitle, StartDate startDate, EndDate endDate,
                         StartTime startTime, EndTime endTime) {
        requireAllNonNull(entryTitle, startDate, endDate, startTime, endTime);
        this.entryTitle = entryTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public EntryTitle getEntryTitle() {
        return entryTitle;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public EndDate getEndDate() {
        return endDate;
    }

    public StartTime getStartTime() {
        return startTime;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    public LocalDate getDateToDisplay() {
        return startDate.getLocalDate();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CalendarEntry)) {
            return false;
        }

        CalendarEntry otherCalEvent = (CalendarEntry) other;
        return otherCalEvent.getEntryTitle().equals(this.getEntryTitle())
                && otherCalEvent.getStartDate().equals(this.getStartDate())
                && otherCalEvent.getEndDate().equals(this.getEndDate())
                && otherCalEvent.getStartTime().equals(this.getStartTime())
                && otherCalEvent.getEndTime().equals(this.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryTitle, startDate, endDate, startTime, endTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEntryTitle())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" End Date: ")
                .append(getEndDate())
                .append(" Start Time: ")
                .append(getStartTime())
                .append(" End Time: ")
                .append(getEndTime());
        return builder.toString();
    }
}
```
###### \java\seedu\address\model\event\EndDate.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.convertStringToDate;
import static seedu.address.commons.util.DateUtil.isValidDate;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents Ending Date of a {@code CalendarEntry}.
 * Guarantees: immutable; is valid as declared in {@link seedu.address.commons.util.DateUtil#isValidDate(String)}
 */
public class EndDate {

    public static final String MESSAGE_END_DATE_CONSTRAINTS =
            "End Date should be DD-MM-YYYY, and it should not be blank";

    private final String endDateString;
    private final LocalDate endDate;

    /**
     * Constructs {@code EndDate}.
     *
     * @param endDate Valid end date.
     */
    public EndDate(String endDate) {
        requireNonNull(endDate);
        checkArgument(isValidDate(endDate), MESSAGE_END_DATE_CONSTRAINTS);
        try {
            this.endDate = convertStringToDate(endDate);
            this.endDateString = endDate;
        } catch (DateTimeParseException dtpe) {
            throw new AssertionError("Given End date should be valid for conversion.");
        }
    }

    public LocalDate getLocalDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return endDateString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDate // instanceof handles nulls
                && this.endDate.equals(((EndDate) other).endDate)); // state check
    }

    @Override
    public int hashCode() {
        return endDate.hashCode();
    }
}
```
###### \java\seedu\address\model\event\EndTime.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.TimeUtil.convertStringToTime;
import static seedu.address.commons.util.TimeUtil.isValidTime;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Represents ending Time of a {@code CalendarEntry}.
 * Guarantees: immutable; is valid as declared in {@link seedu.address.commons.util.TimeUtil#isValidTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_END_TIME_CONSTRAINTS =
            "End Time should be HH:mm (24Hour Format), and it should not be blank";

    private final String endTimeString;
    private final LocalTime endTime;

    /**
     * Constructs {@code EndTime}.
     * @param endTime Valid end time.
     */
    public EndTime (String endTime) {
        requireNonNull(endTime);
        checkArgument(isValidTime(endTime), MESSAGE_END_TIME_CONSTRAINTS);
        try {
            this.endTime = convertStringToTime(endTime);
            this.endTimeString = endTime;
        } catch (DateTimeParseException dtpe) {
            throw new AssertionError("Given End time should be valid for conversion.");
        }
    }

    public LocalTime getLocalTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return endTimeString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.endTime.equals(((EndTime) other).endTime)); // state check
    }

    @Override
    public int hashCode() {
        return endTime.hashCode();
    }
}
```
###### \java\seedu\address\model\event\EntryTitle.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Title of a {@code CalendarEntry} in Event list of Address Book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEntryTitle(String)}
 */
public class EntryTitle {
    public static final String MESSAGE_ENTRY_TITLE_CONSTRAINTS =
            "Event title should only contain alphanumeric characters and spaces"
                    + "and it should not be blank";

    public static final String ENTRY_TITLE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String entryTitle;

    /**
     * Constructs {@code EntryTitle}.
     *
     * @param entryTitle Valid event title.
     */
    public EntryTitle(String entryTitle) {
        requireNonNull(entryTitle);
        checkArgument(isValidEntryTitle(entryTitle), MESSAGE_ENTRY_TITLE_CONSTRAINTS);
        this.entryTitle = entryTitle;
    }

    /**
     * Returns true if a given string is a valid event title.
     */
    public static boolean isValidEntryTitle(String test) {
        return test.matches(ENTRY_TITLE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return entryTitle;
    }

    /**
     * entryTitle matching is non case-sensitive
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryTitle // instanceof handles nulls
                && this.entryTitle.equalsIgnoreCase(((EntryTitle) other).entryTitle)); // state check
    }

    @Override
    public int hashCode() {
        return entryTitle.hashCode();
    }
}
```
###### \java\seedu\address\model\event\exceptions\CalendarEntryNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified CalendarEntry.
 */
public class CalendarEntryNotFoundException extends Exception {
}
```
###### \java\seedu\address\model\event\exceptions\DuplicateCalendarEntryException.java
``` java
import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that an operation would have violated the 'no duplicates' property of the {@code UniqueCalendarEntryList}.
 */
public class DuplicateCalendarEntryException extends DuplicateDataException {

    public DuplicateCalendarEntryException() {
        super("Operation would result in duplicate events");
    }
}
```
###### \java\seedu\address\model\event\StartDate.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.convertStringToDate;
import static seedu.address.commons.util.DateUtil.isValidDate;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents Starting Date of a {@code CalendarEntry}.
 * Guarantees: immutable; is valid as declared in {@link seedu.address.commons.util.DateUtil#isValidDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_START_DATE_CONSTRAINTS =
            "Start Date should be DD-MM-YYYY, and it should not be blank";

    private final String startDateString;
    private final LocalDate startDate;

    /**
     * Constructs {@code StartDate}.
     *
     * @param startDate Valid start date.
     */
    public StartDate(String startDate) {
        requireNonNull(startDate);
        checkArgument(isValidDate(startDate), MESSAGE_START_DATE_CONSTRAINTS);
        try {
            this.startDate = convertStringToDate(startDate);
            this.startDateString = startDate;
        } catch (DateTimeParseException dtpe) {
            throw new AssertionError("Given Start date should be valid for conversion.");
        }
    }

    public LocalDate getLocalDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return startDateString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.startDate.equals(((StartDate) other).startDate)); // state check
    }

    @Override
    public int hashCode() {
        return startDate.hashCode();
    }
}
```
###### \java\seedu\address\model\event\StartTime.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.TimeUtil.convertStringToTime;
import static seedu.address.commons.util.TimeUtil.isValidTime;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Represents starting Time of a {@code CalendarEntry}.
 * Guarantees: immutable; is valid as declared in {@link seedu.address.commons.util.TimeUtil#isValidTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_START_TIME_CONSTRAINTS =
            "Start Time should be HH:mm (24Hour Format), and it should not be blank";

    private final String startTimeString;
    private final LocalTime startTime;

    /**
     * Constructs {@code StartTime}.
     *
     * @param startTime Valid start time.
     */
    public StartTime (String startTime) {
        requireNonNull(startTime);
        checkArgument(isValidTime(startTime), MESSAGE_START_TIME_CONSTRAINTS);

        try {
            this.startTime = convertStringToTime(startTime);
            this.startTimeString = startTime;
        } catch (DateTimeParseException dtpe) {
            throw new AssertionError("Given Start time should be valid for conversion.");
        }
    }

    public LocalTime getLocalTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return startTimeString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && this.startTime.equals(((StartTime) other).startTime)); // state check
    }

    @Override
    public int hashCode() {
        return startTime.hashCode();
    }
}
```
###### \java\seedu\address\model\event\UniqueCalendarEntryList.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.entry.exceptions.CalendarEntryNotFoundException;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;

/**
 * A list of {@code CalendarEntry} that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see CalendarEntry#equals(Object)
 */
public class UniqueCalendarEntryList implements Iterable<CalendarEntry> {

    private final ObservableList<CalendarEntry> internalList = FXCollections.observableArrayList();

    /**
     * Replaces the CalendarEntries in internal list with those in the argument calendar entry list.
     */
    public void setCalEntryList(List<CalendarEntry> calendarEntries) throws DuplicateCalendarEntryException {
        requireAllNonNull(calendarEntries);
        final UniqueCalendarEntryList replacement = new UniqueCalendarEntryList();
        for (CalendarEntry ce: calendarEntries) {
            replacement.add(ce);
        }
        setCalendarEntries(replacement);
    }

    public void setCalendarEntries(UniqueCalendarEntryList replacement) {
        internalList.setAll(replacement.internalList);
    }

    /**
     * Returns true if the list contains an equivalent {@code CalendarEntry} as the given argument.
     */
    public boolean contains(CalendarEntry toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an CalendarEntry to the list.
     *
     * @throws DuplicateCalendarEntryException if the CalendarEntry to add
     * is a duplicate of an existing CalendarEntry in the list.
     */
    public void add(CalendarEntry toAdd) throws DuplicateCalendarEntryException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCalendarEntryException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes CalendarEntry from list if it exists.
     */
    public boolean remove(CalendarEntry toRemove) {
        requireNonNull(toRemove);
        if (contains(toRemove)) {
            internalList.remove(toRemove);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Replaces the calendar entry {@code target} in the list with {@code editedEntry}.
     *
     * @throws DuplicateCalendarEntryException if the replacement is equivalent to another existing entry in the list.
     * @throws CalendarEntryNotFoundException if {@code target} could not be found in the list.
     */
    public void setCalendarEntry(CalendarEntry entryToEdit, CalendarEntry editedEntry)
            throws DuplicateCalendarEntryException, CalendarEntryNotFoundException {

        requireNonNull(editedEntry);

        int index = internalList.indexOf(entryToEdit);
        if (index == -1) {
            throw new CalendarEntryNotFoundException();
        }

        if (!entryToEdit.equals(editedEntry) && internalList.contains(editedEntry)) {
            throw new DuplicateCalendarEntryException();
        }

        internalList.set(index, editedEntry);
    }

    @Override
    public Iterator<CalendarEntry> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CalendarEntry> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueCalendarEntryList // instanceof handles nulls
                && this.internalList.equals(((UniqueCalendarEntryList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueCalendarEntryList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Adds event to list of calendar events.
     */
    void addCalendarEntry(CalendarEntry toAdd) throws DuplicateCalendarEntryException;

    /**
     * Deletes given calendar entry from calendar.
     */
    void deleteCalendarEntry(CalendarEntry entryToDelete) throws CalendarEntryNotFoundException;

    /**
     * Replaces the given calendar entry {@code target} with {@code editedEntry}.
     *
     * @throws DuplicateCalendarEntryException if updating the entry's details causes the entry to be equivalent to
     *      another existing entry in the list.
     * @throws CalendarEntryNotFoundException if {@code target} could not be found in the list.
     */
    void updateCalendarEntry(CalendarEntry entryToEdit, CalendarEntry editedEntry)
            throws DuplicateCalendarEntryException, CalendarEntryNotFoundException;

    /** Returns an unmodifiable view of the filtered calendar entry list */
    ObservableList<CalendarEntry> getFilteredCalendarEntryList();

    /** Returns Calendar stored in Model. */
    Calendar getCalendar();

    /** Returns the CalendarManager */
    ReadOnlyCalendarManager getCalendarManager();
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ReadOnlyCalendarManager getCalendarManager() {
        return calendarManager;
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** Raises an event to indicate calendar manager has changed */
    private void indicateCalendarManagerChanged() {
        raise(new CalendarManagerChangedEvent(calendarManager));
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void deleteGroup(Group targetGroup) throws GroupNotFoundException {
        addressBook.removeGroup(targetGroup);
        indicateAddressBookChanged();
    }

    @Override
    public void deletePreference(Preference targetPreference) throws PreferenceNotFoundException {
        addressBook.removePreference(targetPreference);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void addCalendarEntry(CalendarEntry toAdd) throws DuplicateCalendarEntryException {
        calendarManager.addCalendarEntry(toAdd);
        updateFilteredCalendarEventList(PREDICATE_SHOW_ALL_CALENDAR_ENTRIES);
        indicateCalendarManagerChanged();
    }

    @Override
    public void deleteCalendarEntry(CalendarEntry entryToDelete) throws CalendarEntryNotFoundException {
        calendarManager.deleteCalendarEntry(entryToDelete);
        updateFilteredCalendarEventList(PREDICATE_SHOW_ALL_CALENDAR_ENTRIES);
        indicateCalendarManagerChanged();
    }

    @Override
    public void updateCalendarEntry(CalendarEntry entryToEdit, CalendarEntry editedEntry)
            throws DuplicateCalendarEntryException, CalendarEntryNotFoundException {
        requireAllNonNull(entryToEdit, editedEntry);
        calendarManager.updateCalendarEntry(entryToEdit, editedEntry);
        indicateCalendarManagerChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ObservableList<CalendarEntry> getFilteredCalendarEntryList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredCalendarEventList(Predicate<CalendarEntry> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public Calendar getCalendar() {
        return calendarManager.getCalendar();
    }
```
###### \java\seedu\address\model\person\GroupsContainKeywordsPredicate.java
``` java
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person} 's {@code Group}s' names matches any of the keywords given.
 */
public class GroupsContainKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public GroupsContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> personGroupsMatchesKeyword(person, keyword));
    }

    /**
     * Checks if person contains group with group tag names matching given keyword.
     * Matching is case-insensitive.
     */
    private boolean personGroupsMatchesKeyword(Person person, String keyword) {
        Set<String> groupNames = person.getGroupTags().stream().map(group -> group.tagName).collect(Collectors.toSet());
        for (String groupName: groupNames) {
            if (StringUtil.containsWordIgnoreCase(groupName, keyword)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupsContainKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((GroupsContainKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\person\PreferencesContainKeywordsPredicate.java
``` java
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person} 's {@code Preference}s' names matches any of the keywords given.
 */
public class PreferencesContainKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public PreferencesContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> personGroupsMatchesKeyword(person, keyword));
    }

    /**
     * Checks if person contains preferences with  preference tag names matching given keyword.
     * Matching is case-insensitive.
     */
    private boolean personGroupsMatchesKeyword(Person person, String keyword) {
        Set<String> prefNames = person.getPreferenceTags().stream().map(pref -> pref.tagName)
                .collect(Collectors.toSet());

        for (String prefName: prefNames) {
            if (StringUtil.containsWordIgnoreCase(prefName, keyword)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PreferencesContainKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PreferencesContainKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\ReadOnlyCalendarManager.java
``` java
import javafx.collections.ObservableList;
import seedu.address.model.entry.CalendarEntry;

/**
 * Unmodifiable view of an calendar manager.
 */
public interface ReadOnlyCalendarManager {

    /**
     * Returns an unmodifiable view of the calendar entry list.
     * This list will not contain any duplicate calendar entries.
     */
    ObservableList<CalendarEntry> getCalendarEntryList();
}
```
###### \java\seedu\address\model\tag\Group.java
``` java
/**
 * Represents a Group in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)} in parent class.
 */
public class Group extends Tag {

    public Group(String groupTagName) {
        super(groupTagName);
    }
}
```
###### \java\seedu\address\model\tag\Preference.java
``` java
/**
 * Represents a Preference in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)} in parent class.
 */
public class Preference extends Tag {

    public Preference(String preferenceTagName) {
        super(preferenceTagName);
    }
}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    /**
     * Returns a {@code CalendarManager} with no {@code CalendarEntry} in it.
     */
    public static ReadOnlyCalendarManager getSampleCalendarManager() {
        CalendarManager sampleCm = new CalendarManager();
        return sampleCm;
    }
```
###### \java\seedu\address\storage\CalendarManagerStorage.java
``` java
import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCalendarManager;

/**
 * Represents a storage for {@link seedu.address.model.CalendarManager}.
 */
public interface CalendarManagerStorage {
    /**
     * Returns the file path of the data file.
     */
    String getCalendarManagerFilePath();

    /**
     * Returns CalendarManager data as a {@link ReadOnlyCalendarManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCalendarManager> readCalendarManager() throws DataConversionException, IOException;

    /**
     * @see #getCalendarManagerFilePath()
     */
    Optional<ReadOnlyCalendarManager> readCalendarManager(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCalendarManager} to the storage.
     * @param calendarManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCalendarManager(ReadOnlyCalendarManager calendarManager) throws IOException;

    /**
     * @see #saveCalendarManager(ReadOnlyCalendarManager)
     */
    void saveCalendarManager(ReadOnlyCalendarManager calendarManager, String filePath) throws IOException;

}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public String getCalendarManagerFilePath() {
        return calendarManagerStorage.getCalendarManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyCalendarManager> readCalendarManager() throws DataConversionException, IOException {
        return readCalendarManager(calendarManagerStorage.getCalendarManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyCalendarManager> readCalendarManager(String filePath)
            throws DataConversionException, IOException {

        logger.fine("Attempting to read calendar data from file: " + filePath);
        return calendarManagerStorage.readCalendarManager(filePath);
    }

    @Override
    public void saveCalendarManager(ReadOnlyCalendarManager calendarManager) throws IOException {
        saveCalendarManager(calendarManager, calendarManagerStorage.getCalendarManagerFilePath());
    }

    @Override
    public void saveCalendarManager(ReadOnlyCalendarManager calendarManager, String filePath) throws IOException {
        logger.fine("Attempting to write to calendar data file: " + filePath);
        calendarManagerStorage.saveCalendarManager(calendarManager, filePath);
    }
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    @Subscribe
    public void handleCalendarManagerChangedEvent(CalendarManagerChangedEvent event) {
        logger.info(LogsCenter
                .getEventHandlingLogMessage(event, "Local calendar data changed, saving to file"));

        try {
            saveCalendarManager(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
```
###### \java\seedu\address\storage\XmlAdaptedCalendarEntry.java
``` java
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.EntryTimeConstraintsUtil;
import seedu.address.commons.util.TimeUtil;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.EntryTitle;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;

/**
 * JAXB-friendly version of a CalendarEntry.
 */
public class XmlAdaptedCalendarEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CalendarEntry's %s field is missing!";

    @XmlElement
    private String entryTitle;
    @XmlElement
    private String startDate;
    @XmlElement
    private String endDate;
    @XmlElement
    private String startTime;
    @XmlElement
    private String endTime;

    /**
     * Constructs an XmlAdaptedCalendarEntry.
     */
    public XmlAdaptedCalendarEntry() {}

    /**
     * Constructs an {@code XmlAdaptedCalendarEntry} with the given calendar event details.
     */
    public XmlAdaptedCalendarEntry(String entryTitle, String startDate, String endDate,
                                   String startTime, String endTime) {
        this.entryTitle = entryTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given Order into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCalendarEntry
     */
    public XmlAdaptedCalendarEntry(CalendarEntry source) {
        entryTitle = source.getEntryTitle().toString();
        startDate = source.getStartDate().toString();
        endDate = source.getEndDate().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
    }

    /**
     * Converts the jaxb-friendly adapted calendar event object into the model's CalendarEntry object.
     *
     * @throws IllegalValueException if any data constraints are violated in the adapted calendar event's fields.
     */
    public CalendarEntry toModelType() throws IllegalValueException {
        if (this.entryTitle == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EntryTitle.class.getSimpleName()));
        }
        if (!EntryTitle.isValidEntryTitle(this.entryTitle)) {
            throw new IllegalValueException(EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS);
        }
        final EntryTitle entryTitle = new EntryTitle(this.entryTitle);

        if (this.startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartDate.class.getSimpleName()));
        }

        if (!DateUtil.isValidDate(this.startDate)) {
            throw new IllegalValueException(StartDate.MESSAGE_START_DATE_CONSTRAINTS);
        }
        final StartDate startDate = new StartDate(this.startDate);

        if (this.endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EndDate.class.getSimpleName()));
        }

        if (!DateUtil.isValidDate(this.endDate)) {
            throw new IllegalValueException(EndDate.MESSAGE_END_DATE_CONSTRAINTS);
        }
        final EndDate endDate = new EndDate(this.endDate);

        if (this.startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartTime.class.getSimpleName()));
        }

        if (!TimeUtil.isValidTime(this.startTime)) {
            throw new IllegalValueException(StartTime.MESSAGE_START_TIME_CONSTRAINTS);
        }
        final StartTime startTime = new StartTime(this.startTime);

        if (this.endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EndTime.class.getSimpleName()));
        }

        if (!TimeUtil.isValidTime(this.endTime)) {
            throw new IllegalValueException(EndTime.MESSAGE_END_TIME_CONSTRAINTS);
        }

        final EndTime endTime = new EndTime(this.endTime);

        EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints(startDate, endDate, startTime, endTime);

        return new CalendarEntry(entryTitle, startDate, endDate, startTime, endTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCalendarEntry)) {
            return false;
        }

        XmlAdaptedCalendarEntry otherCalEvent = (XmlAdaptedCalendarEntry) other;
        return Objects.equals(entryTitle, otherCalEvent.entryTitle)
                && Objects.equals(startDate, otherCalEvent.startDate)
                && Objects.equals(endDate, otherCalEvent.endDate)
                && Objects.equals(startTime, otherCalEvent.startTime)
                && Objects.equals(endTime, otherCalEvent.endTime);
    }
}
```
###### \java\seedu\address\storage\XmlCalendarManagerStorage.java
``` java
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyCalendarManager;

/**
 * A class to access CalendarManager data stored as an xml file on the hard disk.
 */
public class XmlCalendarManagerStorage implements CalendarManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String filePath;

    public XmlCalendarManagerStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getCalendarManagerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCalendarManager> readCalendarManager() throws DataConversionException, IOException {
        return readCalendarManager(filePath);
    }

    /**
     * Similar to {@link #readCalendarManager()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCalendarManager> readCalendarManager(String filePath)
            throws DataConversionException, IOException {
        requireNonNull(filePath);

        File calendarManagerFile = new File(filePath);

        if (!calendarManagerFile.exists()) {
            logger.info("CalendarManager file "  + calendarManagerFile + " not found");
            return Optional.empty();
        }

        XmlSerializableCalendarManager xmlCalManager =
                XmlFileStorage.loadCalendarManagerDataFromSaveFile(new File(filePath));

        try {
            return Optional.of(xmlCalManager.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + calendarManagerFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCalendarManager(ReadOnlyCalendarManager calendarManager) throws IOException {
        saveCalendarManager(calendarManager, filePath);
    }

    @Override
    public void saveCalendarManager(ReadOnlyCalendarManager calendarManager, String filePath) throws IOException {
        requireNonNull(calendarManager);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveCalendarManagerDataToFile(file, new XmlSerializableCalendarManager(calendarManager));
    }
}
```
###### \java\seedu\address\storage\XmlFileStorage.java
``` java
    public static void saveCalendarManagerDataToFile(File file, XmlSerializableCalendarManager calendarManager)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, calendarManager);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns calendar manager data in the file or an empty address book
     */
    public static XmlSerializableCalendarManager loadCalendarManagerDataFromSaveFile(File file)
            throws DataConversionException, FileNotFoundException {

        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableCalendarManager.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
```
###### \java\seedu\address\storage\XmlSerializableCalendarManager.java
``` java
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CalendarManager;
import seedu.address.model.ReadOnlyCalendarManager;

/**
 * An Immutable CalendarManager that is serializable to XML format
 */
@XmlRootElement(name = "calendarmanager")
public class XmlSerializableCalendarManager {
    @XmlElement
    private List<XmlAdaptedCalendarEntry> calendarEntries;

    /**
     * Creates an empty XmlSerializableCalendarManager.
     */
    public XmlSerializableCalendarManager() {
        calendarEntries = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableCalendarManager(ReadOnlyCalendarManager src) {
        this();
        calendarEntries.addAll(src.getCalendarEntryList().stream().map(XmlAdaptedCalendarEntry::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this calendarManager into the model's {@code CalendarManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedCalendarEntry}.
     */
    public CalendarManager toModelType() throws IllegalValueException {
        CalendarManager calendarManager = new CalendarManager();
        for (XmlAdaptedCalendarEntry entry: calendarEntries) {
            calendarManager.addCalendarEntry(entry.toModelType());
        }

        return calendarManager;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableCalendarManager)) {
            return false;
        }

        XmlSerializableCalendarManager otherCm = (XmlSerializableCalendarManager) other;
        return calendarEntries.equals(otherCm.calendarEntries);
    }
}
```
###### \java\seedu\address\ui\CalendarEntryCard.java
``` java
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.entry.CalendarEntry;

/**
 * An UI component that displays information of a {@code CalendarEntry}.
 */
public class CalendarEntryCard extends UiPart<Region> {

    private static final String FXML = "CalendarEntryCard.fxml";

    public final CalendarEntry calendarEntry;

    @FXML
    private HBox cardPane;

    @FXML
    private Label entryTitle;

    @FXML
    private Label id;

    @FXML
    private Label startDate;

    @FXML
    private Label endDate;

    @FXML
    private Label timeDuration;


    public CalendarEntryCard(CalendarEntry calendarEntry, int displayedIndex) {
        super(FXML);
        this.calendarEntry = calendarEntry;
        id.setText(displayedIndex + ". ");
        entryTitle.setText(calendarEntry.getEntryTitle().toString());
        startDate.setText("From: " + calendarEntry.getStartDate().toString());
        endDate.setText("To: " + calendarEntry.getEndDate().toString());
        timeDuration.setText("Between " + calendarEntry.getStartTime().toString()
                + " and " + calendarEntry.getEndTime().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CalendarEntryCard)) {
            return false;
        }

        // state check
        CalendarEntryCard card = (CalendarEntryCard) other;
        return id.getText().equals(card.id.getText())
                && calendarEntry.equals(card.calendarEntry);
    }
}
```
###### \java\seedu\address\ui\CalendarEntryListPanel.java
``` java
import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CalendarEntryPanelSelectionChangedEvent;
import seedu.address.model.entry.CalendarEntry;

/**
 * Panel containing calendar entries present in calendar.
 */
public class CalendarEntryListPanel extends UiPart<Region> {

    private static final String FXML = "CalendarEntryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarEntryListPanel.class);

    @FXML
    private ListView<CalendarEntryCard> calendarEntryCardListView;

    public CalendarEntryListPanel(ObservableList<CalendarEntry> calendarEntries) {
        super(FXML);
        setConnections(calendarEntries);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<CalendarEntry> calendarEntryList) {
        ObservableList<CalendarEntryCard> mappedList = EasyBind.map(calendarEntryList, (calendarEntry) ->
                        new CalendarEntryCard(calendarEntry, calendarEntryList.indexOf(calendarEntry) + 1));
        calendarEntryCardListView.setItems(mappedList);
        calendarEntryCardListView.setCellFactory(listView -> new CalendarEntryListPanel.CalendarEntryListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        calendarEntryCardListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in calendar entry list panel changed to : '" + newValue + "'");
                        raise(new CalendarEntryPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CalendarEntryCard}.
     */
    class CalendarEntryListViewCell extends ListCell<CalendarEntryCard> {

        @Override
        protected void updateItem(CalendarEntryCard calendarEntry, boolean empty) {
            super.updateItem(calendarEntry, empty);

            if (empty || calendarEntry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(calendarEntry.getRoot());
            }
        }
    }

}
```
###### \java\seedu\address\ui\CalendarPanel.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.ui.util.CalendarFxUtil.DAY_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.MONTH_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_BACK;
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_NEXT;
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_TODAY;
import static seedu.address.ui.util.CalendarFxUtil.WEEK_VIEW;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeCalendarDateRequestEvent;
import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.commons.events.ui.ChangeCalendarViewRequestEvent;
import seedu.address.ui.util.CalendarFxUtil;

/**
 * Calendar Panel displaying calendar.
 * ContactSails implements CalendarFX to display Calendar.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private final CalendarView calendarView;
    private final CalendarSource calendarSource;

    @FXML
    private StackPane calendarPanelHolder;

    public CalendarPanel(Calendar calendar) {
        super(FXML);
        calendarView = CalendarFxUtil.returnModifiedCalendarView();
        calendarSource = new CalendarSource();

        initialiseCalendar(calendar);
        createTimeThread();
    }

    /**
     * Adapted from CalendarFX developer manual QuickStart section.
     * http://dlsc.com/wp-content/html/calendarfx/manual.html#_quick_start
     */
    private void createTimeThread() {
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
            };
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }

    /**
     * Sets up CalendarFX.
     */
    public void initialiseCalendar(Calendar calendar) {
        calendarSource.getCalendars().addAll(calendar);
        calendarView.getCalendarSources().setAll(calendarSource);
        calendarPanelHolder.getChildren().setAll(calendarView);
    }

    /**
     * Handles Request to display Calendar in specific viewing format.
     */
    public void handleChangeCalendarViewRequestEvent(ChangeCalendarViewRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        String view = event.getView();
        requireNonNull(view);

        if (view.equalsIgnoreCase(MONTH_VIEW)) {
            calendarView.showMonthPage();
        } else if (view.equalsIgnoreCase(DAY_VIEW)) {
            calendarView.showDayPage();
        } else if (view.equalsIgnoreCase(WEEK_VIEW)) {
            calendarView.showWeekPage();
        }
    }

    /**
     * Handles request to change the current page of the Calendar to the page in {@code event}.
     */
    public void handleChangeCalendarPageRequestEvent(ChangeCalendarPageRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        String request = event.getRequestType();
        requireNonNull(request);

        if (request.equals(REQUEST_TODAY)) {
            calendarView.getSelectedPage().goToday();
        } else if (request.equals(REQUEST_BACK)) {
            calendarView.getSelectedPage().goBack();
        } else if (request.equals(REQUEST_NEXT)) {
            calendarView.getSelectedPage().goForward();
        }
    }

    /**
     * Handles request to change the current date of the Calendar to the date in {@code event}.
     */
    public void handleChangeCalendarDateRequestEvent(ChangeCalendarDateRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        requireNonNull(event.getDate());
        calendarView.setDate(event.getDate());
    }
}
```
###### \java\seedu\address\ui\CenterPanel.java
``` java
import com.calendarfx.model.Calendar;
import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.events.ui.ChangeCalendarDateRequestEvent;
import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.commons.events.ui.ChangeCalendarViewRequestEvent;
import seedu.address.commons.events.ui.DisplayPersonPanelRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ResetPersonPanelRequestEvent;

/**
 * The Center Panel of the App that can switch between {@code Person Panel} and {@code Calendar Panel}.
 * Center Panel subscribes to Events meant for Person Panel and Calendar Panel
 * in order to handle the switching between the displays.
 */
public class CenterPanel extends UiPart<Region> {

    private static final String FXML = "CenterPanel.fxml";

    private CalendarPanel calendarPanel;

    private PersonPanel personPanel;

    @FXML
    private StackPane centerPlaceholder;

    public CenterPanel(Calendar calendar) {
        super(FXML);

        personPanel = new PersonPanel();
        calendarPanel = new CalendarPanel(calendar);

        displayPersonPanel();
        registerAsAnEventHandler(this);
    }

    /**
     * Displays the Person Panel if it is not shown on CenterPanel.
     */
    public void displayPersonPanel() {
        if (!centerPlaceholder.getChildren().contains(personPanel.getRoot())) {
            centerPlaceholder.getChildren().clear();
            centerPlaceholder.getChildren().add(personPanel.getRoot());
        }
    }

    /**
     * Displays the Calendar Panel if it is not shown on CenterPanel.
     */
    public void displayCalendarPanel() {
        if (!centerPlaceholder.getChildren().contains(calendarPanel.getRoot())) {
            centerPlaceholder.getChildren().clear();
            centerPlaceholder.getChildren().add(calendarPanel.getRoot());
        }
    }

    @Subscribe
    private void handleChangeCalendarViewRequestEvent(ChangeCalendarViewRequestEvent event) {
        calendarPanel.handleChangeCalendarViewRequestEvent(event);
        displayCalendarPanel();
    }

    @Subscribe
    public void handleChangeCalendarPageRequestEvent(ChangeCalendarPageRequestEvent event) {
        calendarPanel.handleChangeCalendarPageRequestEvent(event);
        displayCalendarPanel();
    }

    @Subscribe
    private void handleChangeCalendarDateRequestEvent(ChangeCalendarDateRequestEvent event) {
        calendarPanel.handleChangeCalendarDateRequestEvent(event);
        displayCalendarPanel();
    }

    @Subscribe
    private void handleDisplayPersonPanelRequestEvent(DisplayPersonPanelRequestEvent event) {
        displayPersonPanel();
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        personPanel.handlePersonPanelSelectionChangedEvent(event);
        displayPersonPanel();
    }

    @Subscribe
    private void handleResetPersonPanelRequestEvent(ResetPersonPanelRequestEvent event) {
        personPanel.handleResetPersonPanelRequestEvent(event);
        displayPersonPanel();
    }
}
```
###### \java\seedu\address\ui\util\CalendarFxUtil.java
``` java
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.page.DayPage;

/**
 * Contains helper methods and list of constants related to handling CalendarFx display.
 * Contains methods to initialise Calendar such that unused functions are not displayed, and mouse events are not
 * listened to.
 */
public class CalendarFxUtil {

    public static final String MONTH_VIEW = "Month";
    public static final String DAY_VIEW = "Day";
    public static final String WEEK_VIEW = "Week";

    public static final String REQUEST_TODAY = "Today";
    public static final String REQUEST_BACK = "Back";
    public static final String REQUEST_NEXT = "Next";

    /**
     * Returns modified CalendarView such that unnecessary buttons and features are not shown.
     */
    public static CalendarView returnModifiedCalendarView() {
        CalendarView calendarView = new CalendarView();

        calendarView.setShowPageToolBarControls(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSourceTray(false);
        calendarView.setShowSourceTrayButton(false);
        calendarView.setShowPageSwitcher(false);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowToolBar(false);

        calendarView.getDayPage().setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);

        //calendarView.getDayPage().setDisable(true);
        return calendarView;
    }
}
```
###### \resources\view\CalendarPanel.fxml
``` fxml

<?import javafx.scene.layout.StackPane?>

<StackPane fx:id="calendarPanelHolder" minHeight="400.0" minWidth="640.0" prefHeight="400.0" prefWidth="640.0" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1" />
```
###### \resources\view\CenterPanel.fxml
``` fxml

<?import javafx.scene.layout.StackPane?>


<StackPane prefHeight="400.0" prefWidth="640.0" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <StackPane fx:id="centerPlaceholder" prefHeight="400.0" prefWidth="640.0" />
   </children>
</StackPane>
```
