# Kyomian
###### \java\seedu\address\logic\commands\EventCommand.java
``` java
/**
 * Adds an event to the desk board
 */
public class EventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the desk board. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_START_DATETIME + " START DATETIME "
            + PREFIX_END_DATETIME + "END DATETIME "
            + PREFIX_LOCATION + "LOCATION "
            + "[" + PREFIX_REMARK + "REMARK]"
            + "[" + PREFIX_TAG + "TAG]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Software Project "
            + PREFIX_START_DATETIME + "01/05/2018 08:00 "
            + PREFIX_END_DATETIME + "01/08/2018 08:00 "
            + PREFIX_LOCATION + "School of Computing "
            + PREFIX_REMARK + "Bring laptop charger "
            + PREFIX_TAG + "Important";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the desk board";

    private final Event toAdd;

    /**
     * Creates a EventCommand to add the specified {@code Event}
     */
    public EventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addActivity(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateActivityException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventCommand // instanceof handles nulls
                && toAdd.equals(((EventCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\RemoveCommand.java
``` java
/**
 * Removes an activity based on its last displayed index in the desk board.
 */
public class RemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rm";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes task/event identified by the index number in the last displayed task/event listing.\n"
            + "Parameters: task/event INDEX (INDEX must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " task 1" + " OR "
            + COMMAND_WORD + " event 1";

    public static final String MESSAGE_REMOVE_TASK_SUCCESS = "Removed task: %1$s";
    public static final String MESSAGE_REMOVE_EVENT_SUCCESS = "Removed event: %1$s";

    private final Index targetIndex;
    private Activity activityToDelete;
    private final String activityOption;

    public RemoveCommand(String activityOption, Index targetIndex) {
        this.activityOption = activityOption;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(activityToDelete);
        try {
            model.deleteActivity(activityToDelete);
        } catch (ActivityNotFoundException pnfe) {
            throw new AssertionError("The target activity cannot be missing");
        }

        if (activityOption.equals("task")) {
            return new CommandResult(String.format(MESSAGE_REMOVE_TASK_SUCCESS, activityToDelete));
        } else {
            return new CommandResult(String.format(MESSAGE_REMOVE_EVENT_SUCCESS, activityToDelete));
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {

        if (activityOption.equals("task")) {
            List<Activity> lastShownTaskList = model.getFilteredTaskList();
            if (targetIndex.getZeroBased() >= lastShownTaskList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
            }
            activityToDelete = lastShownTaskList.get(targetIndex.getZeroBased());
        } else if (activityOption.equals("event")) {
            List<Activity> lastShownEventList = model.getFilteredEventList();
            if (targetIndex.getZeroBased() >= lastShownEventList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
            }
            activityToDelete = lastShownEventList.get(targetIndex.getZeroBased());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveCommand // instanceof handles nulls
                && this.targetIndex.equals(((RemoveCommand) other).targetIndex) // state check
                && Objects.equals(this.activityToDelete, ((RemoveCommand) other).activityToDelete));
    }
}
```
###### \java\seedu\address\logic\commands\TaskCommand.java
``` java
/**
 * Adds a task to the desk board.
 */
public class TaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the desk board. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE_TIME + "DATETIME "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_TAG + "TAG]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Software Engineering Milestone 1 "
            + PREFIX_DATE_TIME + "01/08/2018 17:00 "
            + PREFIX_REMARK + "Enhance major component "
            + PREFIX_TAG + "CS2103T";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the desk board";

    private final Task toAdd;

    /**
     * Creates a TaskCommand to add the specified {@code Task}
     */
    public TaskCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addActivity(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateActivityException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskCommand // instanceof handles nulls
                && toAdd.equals(((TaskCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DATE_TIME = new Prefix("d/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_START_DATETIME = new Prefix("s/");
    public static final Prefix PREFIX_END_DATETIME = new Prefix("e/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");

    // To be deleted soon
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_EMAIL = new Prefix("em/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
}
```
###### \java\seedu\address\logic\parser\DeskBoardParser.java
``` java
/**
 * Parses user input.
 */
public class DeskBoardParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case TaskCommand.COMMAND_WORD:
            return new TaskCommandParser().parse(arguments);

        case CompleteCommand.COMMAND_WORD:
            return new CompleteCommandParser().parse(arguments);

        case EventCommand.COMMAND_WORD:
            return new EventCommandParser().parse(arguments);

        //case EditCommand.COMMAND_WORD:
            //return new EditCommandParser().parse(arguments);

        //case SelectCommand.COMMAND_WORD:
            //return new SelectCommandParser().parse(arguments);

        case RemoveCommand.COMMAND_WORD:
            return new RemoveCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        //case FindCommand.COMMAND_WORD:
            //return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ListCommand.COMMAND_ALIAS:
            return new ListCommandParser().parse(arguments);

        //case HistoryCommand.COMMAND_WORD:
            //return new HistoryCommand();

        //case ExitCommand.COMMAND_WORD:
            //return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommandParser().parse(arguments);

        //case UndoCommand.COMMAND_WORD:
            //return new UndoCommand();

        //case RedoCommand.COMMAND_WORD:
            //return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
```
###### \java\seedu\address\logic\parser\EventCommandParser.java
``` java

/**
 * Parses input arguments and creates a new EventCommand object
 */
public class EventCommandParser implements Parser<EventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EventCommand
     * and returns a EventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START_DATETIME, PREFIX_END_DATETIME,
                        PREFIX_LOCATION, PREFIX_REMARK, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_START_DATETIME, PREFIX_END_DATETIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            DateTime startDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATETIME)).get();
            DateTime endDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_DATETIME)).get();
            Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION)).get();
            Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Event event = new Event(name, startDateTime, endDateTime, location, remark, tagList);

            return new EventCommand(event);
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
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String datetime} into a {@code DateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code datetime} is invalid.
     */

    public static DateTime parseDateTime(String datetime) throws IllegalValueException {
        requireNonNull(datetime);
        String trimmedDateTime = datetime.trim();
        if (!DateTime.isValidDateTime(trimmedDateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        }
        return new DateTime(trimmedDateTime);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<DateTime>} if {@code datetime} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DateTime> parseDateTime(Optional<String> datetime) throws IllegalValueException {
        requireNonNull(datetime);
        return datetime.isPresent() ? Optional.of(parseDateTime(datetime.get())) : Optional.empty();
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
     * Parses a {@code Optional<String> location} into an {@code Optional<Location>} if {@code location} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Location> parseLocation(Optional<String> location) throws IllegalValueException {
        requireNonNull(location);
        return location.isPresent() ? Optional.of(parseLocation(location.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String remark} into a {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws IllegalValueException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemark(trimmedRemark)) {
            throw new IllegalValueException(Remark.MESSAGE_REMARK_CONSTRAINTS);
        }
        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(parseRemark(remark.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

}
```
###### \java\seedu\address\logic\parser\RemoveCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemoveCommand object
 */
public class RemoveCommandParser implements Parser<RemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveCommand
     * and returns a RemoveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveCommand parse(String args) throws ParseException {
        String[] argsParts = args.trim().split(" ");

        if (argsParts.length != 2 || !isValidActivityOption(argsParts[0])) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        }

        try {
            String activityOption = argsParts[0];
            Index index = ParserUtil.parseIndex(argsParts[1]);
            return new RemoveCommand(activityOption, index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        }
    }

    private static boolean isValidActivityOption(String activityOption) {
        return activityOption.equals("task") || activityOption.equals("event");
    }

}
```
###### \java\seedu\address\logic\parser\TaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new TaskCommand object
 */

public class TaskCommandParser implements Parser<TaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TaskCommand
     * and returns a TaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE_TIME, PREFIX_REMARK, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATE_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            DateTime datetime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE_TIME)).get();
            Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Task task = new Task(name, datetime, remark, tagList);

            return new TaskCommand(task);
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
###### \java\seedu\address\model\activity\DateTime.java
``` java
/**
 * Represents an Activity's datetime in the desk board.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    public static final String DEFAULT_DATETIME_FORMAT = "d/M/y H:m";
    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Date and Time should be in the format of " + DEFAULT_DATETIME_FORMAT;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);

    private final LocalDateTime dateTime;
    private final String dateTimeString;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param value A valid datetime.
     */
    public DateTime(String value) {
        requireNonNull(value);
        checkArgument(isValidDateTime(value), MESSAGE_DATETIME_CONSTRAINTS);
        this.dateTime = LocalDateTime.parse(value, formatter);
        this.dateTimeString = value;
    }

    /**
     * Returns true if a given string is a valid datetime.
     */
    public static boolean isValidDateTime(String value) {
        try {
            LocalDateTime.parse(value, formatter);
            return true;
        } catch (DateTimeParseException dtpe) {
            dtpe.printStackTrace();
            return false;
        }
    }

    public LocalDateTime getLocalDateTime() {
        return this.dateTime;
    }

    @Override
    public String toString() {
        return this.dateTimeString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.dateTime.equals(((DateTime) other).dateTime)); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

}
```
###### \java\seedu\address\ui\util\DateTimeUtil.java
``` java
/**
 * Formats DateTime for display in UI
 * Example: 01/08/2018 08:00 is displayed as 1 Aug 2018 08:00 in the UI
 */
public class DateTimeUtil {

    private static final String DISPLAYED_DATETIME_FORMAT = "d MMM y HH:mm";
    private static final DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern(DISPLAYED_DATETIME_FORMAT);

    /**
     * Formats DateTime of task as day, name of month, year, hours and minutes
     */
    public static String getDisplayedDateTime(Task task) throws DateTimeException {
        DateTime dateTime = task.getDateTime();
        String displayedDateTime = displayFormatter.format(dateTime.getLocalDateTime());
        return displayedDateTime;
    }

    /**
     * Formats StartDateTime of event as day, name of month, year, hours and minutes
     */
    public static String getDisplayedStartDateTime(Event event) throws DateTimeException {
        DateTime dateTime = event.getStartDateTime();
        String displayedDateTime = displayFormatter.format(dateTime.getLocalDateTime());
        return displayedDateTime;
    }

    /**
     * Formats EndDateTime of event as day, name of month, year, hours and minutes
     */
    public static String getDisplayedEndDateTime(Event event) throws DateTimeException {
        DateTime dateTime = event.getEndDateTime();
        String displayedDateTime = displayFormatter.format(dateTime.getLocalDateTime());
        return displayedDateTime;
    }
}
```
