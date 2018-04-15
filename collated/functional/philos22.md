# philos22
###### \java\seedu\address\Calendar.java
``` java

    /**
     * iterate through events and list them out with their respective start times
     * @return a list of upcoming events
     * @throws IOException
     */
    public static String listEvents() throws IOException {

        com.google.api.services.calendar.Calendar service =
                getCalendarService();

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        StringBuilder result = new StringBuilder();

        if (items.size() == 0) {
            result.append("No upcoming events found.");
        } else {
            Integer eventNumber = 1;
            for (Event event : items) {
                String eventId = event.getId();
                addEventIDs(eventId);
                result.append(String.format("[Event %s] \t %s \t [%s] to [%s] \tLocation: %s\n",
                        eventNumber, event.getSummary(), event.getStart().getDateTime(),
                        event.getEnd().getDateTime(), event.getLocation()));
                eventNumber++;
            }
        }
        return result.toString();
    }

    /**
     * Adds event to the calendar - specifying Name, Location, StartTime, EndTime
     * @return success code
     * @throws IOException
     */
    public static String addEvent(String eventName, String eventLocation, DateTime startDateTime,
                                  DateTime endDateTime) throws IOException {

        String successAddedMessage = "Event added successfully";

        com.google.api.services.calendar.Calendar service =
                getCalendarService();

        Event event = new Event()
                .setSummary(eventName)
                .setLocation(eventLocation);

        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("");
        event.setEnd(end);

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());

        return successAddedMessage;
    }

    /**
     * Delete event from the calendar - specifying EventID
     * @return success code
     * @throws IOException
     */
    public static String delEvent(String eventArrayId) throws IOException {

        String successDeletedMessage = "Event successfully deleted.";

        int eventArrayIdInt = Integer.parseInt(eventArrayId) - 1;
        String eventId = eventIDs.get(eventArrayIdInt);

        // Build a new authorized API client service.
        com.google.api.services.calendar.Calendar service = getCalendarService();

        service.events().delete("primary", eventId).execute();

        eventIDs.clear();

        String reList = listEvents(); // to regenerate the EventIDs array

        return successDeletedMessage;
    }

}
```
###### \java\seedu\address\logic\commands\CalendarAddCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.io.IOException;

import com.google.api.client.util.DateTime;

import seedu.address.Calendar;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Adds an event to the Google Calendar.
 */
public class CalendarAddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "calAdd";
    public static final String COMMAND_ALIAS = "calA";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the calendar. \n"
            + "Parameters: "
            + PREFIX_EVENT + "EVENT NAME "
            + PREFIX_LOCATION + "EVENT LOCATION "
            + PREFIX_START + "EVENT_START_TIME "
            + PREFIX_END + "EVENT_END_TIME \n"
            + "Example: \n" + COMMAND_WORD + " "
            + PREFIX_EVENT + "Prison Security Sweep "
            + PREFIX_LOCATION + "NUS Prison "
            + PREFIX_START + "2020-04-01 15:00:00 "
            + PREFIX_END + "2020-04-01 17:00:00";

    private final String calEventName;
    private final String calEventLocation;
    private final DateTime calStartDateTime;
    private final DateTime calEndDateTime;
    private final String toAdd;


    /**
     * Creates an CalendarAddCommand to add the specified {@code Event}
     */
    public CalendarAddCommand(String eventName, String eventLocation, DateTime startDateTime, DateTime endDateTime) {
        requireNonNull(eventName);
        requireNonNull(eventLocation);
        requireNonNull(startDateTime);
        requireNonNull(endDateTime);
        calEventName = eventName;
        calEventLocation = eventLocation;
        calStartDateTime = startDateTime;
        calEndDateTime = endDateTime;
        toAdd = calEventName + " " + calEventLocation + " " + calStartDateTime.toString() + " "
                + calEndDateTime.toString();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            String successMessage = new Calendar().addEvent(calEventName, calEventLocation, calStartDateTime,
                    calEndDateTime);
            return new CommandResult(String.format(successMessage, toAdd));
        } catch (IOException e) {
            throw new CommandException(e.toString());
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalendarAddCommand // instanceof handles nulls
                && toAdd.equals(((CalendarAddCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\CalendarCommand.java
``` java
package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.Calendar;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";
    public static final String COMMAND_ALIAS = "cal";
    public static final String ERROR_MESSAGE = "calendar execution failed";
    public static final int MIN_SECURITY_LEVEL = 1;

    @Override
    public CommandResult execute() {
        try {
            String messagesuccess = new Calendar().listEvents();
            return new CommandResult(messagesuccess);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommandResult(ERROR_MESSAGE);
    }

    @Override
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }

}
```
###### \java\seedu\address\logic\commands\CalendarDeleteCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.Calendar;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Deletes an event from the Google Calendar.
 */
public class CalendarDeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "calDel";
    public static final String COMMAND_ALIAS = "calD";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an event from the calendar. \n"
            + "Parameter: EVENT NUMBER (from the event list in Calendar command)"
            + "Example: \n" + COMMAND_WORD + " 2\n"
            + "Deletes the second event listed in cal command";

    private final String toDel;

    /**
     * Creates an CalendarAddCommand to add the specified {@code Event}
     */
    public CalendarDeleteCommand(String eventId) {
        requireNonNull(eventId);
        toDel = eventId;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            String successMessage = new Calendar().delEvent(toDel);
            return new CommandResult(String.format(successMessage, toDel));
        } catch (IOException e) {
            throw new CommandException(e.toString());
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalendarDeleteCommand // instanceof handles nulls
                && toDel.equals(((CalendarDeleteCommand) other).toDel));
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names or tags contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/NAME_KEYWORDS t/TAG_KEYWORDS...\n"
            + "Example: " + COMMAND_WORD + " n/alice t/tag1";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }

}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case CalendarCommand.COMMAND_WORD:
        case CalendarCommand.COMMAND_ALIAS:
            return new CalendarCommand();

        case CalendarAddCommand.COMMAND_WORD:
        case CalendarAddCommand.COMMAND_ALIAS:
            return new CalendarAddCommandParser().parse(arguments);

        case CalendarDeleteCommand.COMMAND_WORD:
        case CalendarDeleteCommand.COMMAND_ALIAS:
            return new CalendarDeleteCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\CalendarAddCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import com.google.api.client.util.DateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CalendarAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new CalendarAddCommandParser object
 */
public class CalendarAddCommandParser implements Parser<CalendarAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CalendarAddCommand
     * and returns the message of whether execution was successful or not.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CalendarAddCommand parse(String args) throws ParseException, DateTimeParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT, PREFIX_LOCATION, PREFIX_START, PREFIX_END);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT, PREFIX_LOCATION, PREFIX_START, PREFIX_END)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarAddCommand.MESSAGE_USAGE));
        }

        try {
            String eventName = ParserUtil.parseName(argMultimap.getValue(PREFIX_EVENT)).get().toString();
            String eventLocation = ParserUtil.parseName(argMultimap.getValue(PREFIX_LOCATION)).get().toString();
            DateTime startDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START).toString());
            DateTime endDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END).toString());
            return new CalendarAddCommand(eventName, eventLocation, startDateTime, endDateTime);

        } catch (IllegalValueException | DateTimeParseException e) {
            throw new ParseException(e.getMessage(), e);
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
###### \java\seedu\address\logic\parser\CalendarDeleteCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.CalendarDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new CalendarDeleteCommandParser object
 */
public class CalendarDeleteCommandParser implements Parser<CalendarDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CalendarDeleteCommand
     * and returns the message of whether execution was successful or not.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CalendarDeleteCommand parse(String args) throws ParseException {
        try {
            int intCheck = Integer.parseInt(args);
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CalendarDeleteCommand.MESSAGE_USAGE));
        }

        return new CalendarDeleteCommand(args.trim());
    }
}
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_EVENT = new Prefix("event/");
    public static final Prefix PREFIX_LOCATION = new Prefix("loc/");
    public static final Prefix PREFIX_START = new Prefix("start/");
    public static final Prefix PREFIX_END = new Prefix("end/");
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        if (!(arePrefixesPresent(argMultimap, PREFIX_NAME) || arePrefixesPresent(argMultimap, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty())) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if ((arePrefixesPresent(argMultimap, PREFIX_NAME)) && (arePrefixesPresent(argMultimap, PREFIX_TAG))) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            String[] tagKeywords = argMultimap.getValue(PREFIX_TAG).get().split("\\s+");
            return new FindCommand(new ContainsKeywordsPredicate(Arrays.asList(nameKeywords),
                    Arrays.asList(tagKeywords)));
        } else if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        } else if (arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            String[] tagKeywords = argMultimap.getValue(PREFIX_TAG).get().split("\\s+");
            return new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
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
     * Parses a {@code Optional<String> DateTime} if present.
     */
    public static DateTime parseDateTime(String dateTime) throws DateTimeParseException {

        String theDateTime = dateTime.replaceAll("[\\[\\]]", "").replaceAll("Optional", "");

        try {
            TemporalAccessor ta = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse(theDateTime);
            String strDateTime = LocalDateTime.from(ta).toString() + ":00+08:00";
            return new DateTime(strDateTime);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException(MESSAGE_INVALID_DATETIME_FORMAT, theDateTime, 0, e);
        }
    }

}
```
###### \java\seedu\address\model\person\ContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s fields matches any of the keywords given.
 */
public class ContainsKeywordsPredicate implements Predicate<Person> {
    private final Predicate<Person> namePredicate;
    private final Predicate<Person> tagPredicate;

    public ContainsKeywordsPredicate(List<String> nameKeywords, List<String> tagKeywords) {
        this.namePredicate = new NameContainsKeywordsPredicate(nameKeywords);
        this.tagPredicate = new TagContainsKeywordsPredicate(tagKeywords);
    }


    @Override
    public boolean test(Person person) {
        return namePredicate.test(person) && tagPredicate.test(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ContainsKeywordsPredicate // Used to handle Nulls
                && this.namePredicate.equals(((ContainsKeywordsPredicate) other).namePredicate)
                && this.tagPredicate.equals(((ContainsKeywordsPredicate) other).tagPredicate)); // state check
    }

}
```
###### \java\seedu\address\model\person\TagContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        // Making a string of all tags
        if (person.getTags().size() == 0) {
            return false;
        }
        Iterator tagIteration = person.getTags().iterator();
        StringBuilder strBuild = new StringBuilder();
        strBuild.append(tagIteration.next());
        while (tagIteration.hasNext()) {
            strBuild.append(" " + tagIteration.next());
        }
        String tagList = strBuild.toString().replace("[", "").replace("]", "");

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tagList, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
