# x3tsunayh
###### \java\seedu\address\commons\events\model\EventBookChangedEvent.java
``` java

/**
 * Indicates the EventBook in the model has changed
 */
public class EventBookChangedEvent extends BaseEvent {

    public final ReadOnlyEventBook data;

    public EventBookChangedEvent(ReadOnlyEventBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of events " + data.getEventList().size();
    }
}
```
###### \java\seedu\address\commons\events\ui\EventPanelSelectionChangedEvent.java
``` java

/**
 * Represents a selection change in the Event List Panel
 */
public class EventPanelSelectionChangedEvent extends BaseEvent {

    private final EventCard newSelection;

    public EventPanelSelectionChangedEvent(EventCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public EventCard getNewSelection() {
        return newSelection;
    }

}
```
###### \java\seedu\address\commons\events\ui\GoogleContactNameEvent.java
``` java

/**
 * An event requesting to google for a contact's name.
 */
public class GoogleContactNameEvent extends BaseEvent {

    private Person person;

    public GoogleContactNameEvent (Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Person getPerson() {
        return this.person;
    }

}
```
###### \java\seedu\address\commons\events\ui\JumpToCalendarRequestEvent.java
``` java

/**
 * An event requesting to jump to a specified month and year on the calendar.
 */
public class JumpToCalendarRequestEvent extends BaseEvent {

    private YearMonth yearMonth;

    public JumpToCalendarRequestEvent (YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

}
```
###### \java\seedu\address\commons\util\FileUtil.java
``` java

    /**
     * Checks if a String in {@code filePath} is a valid XML file
     */
    public static boolean isValidXmlFile(String filePath) {
        return filePath.toLowerCase().matches(REGEX_XML_FILE);
    }

    /**
     * Checks if a String in {@code filePath} is a valid CSV file
     */
    public static boolean isValidCsvFile(String filePath) {
        return filePath.toLowerCase().matches(REGEX_CSV_FILE);
    }

```
###### \java\seedu\address\logic\commands\AddEventCommand.java
``` java

/**
 * Adds an event to event book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the event book. "
            + "Parameters: "
            + PREFIX_EVENT_TITLE + "NAME "
            + PREFIX_EVENT_DESCRIPTION + "DESCRIPTION "
            + PREFIX_EVENT_LOCATION + "LOCATION "
            + PREFIX_EVENT_DATETIME + "DATETIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_TITLE + "Movie Outing "
            + PREFIX_EVENT_DESCRIPTION + "Watching Black Panther "
            + PREFIX_EVENT_LOCATION + "Suntec City GV "
            + PREFIX_EVENT_DATETIME + "2018-04-22 1630";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event book";

    private final ReadOnlyEvent toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code ReadOnlyEvent}
     */
    public AddEventCommand(ReadOnlyEvent event)  {
        requireNonNull(event);
        toAdd = new Event(event);
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addEvent(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateEventException dee) {
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
###### \java\seedu\address\logic\commands\ClearEventsCommand.java
``` java

/**
 * Clears the event list.
 */
public class ClearEventsCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clearevents";
    public static final String MESSAGE_SUCCESS = "Event list has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new EventBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\DeleteEventCommand.java
``` java

/**
 * Deletes a event identified using its last displayed index from the latest displayed event list.
 */
public class DeleteEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes event identified by the index number used in the last event listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";

    private final Index targetIndex;

    public DeleteEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        ReadOnlyEvent eventToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteEvent(eventToDelete);
        } catch (Error e) {
            assert false : "Event cannot be empty.";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteEventCommand) other).targetIndex));
    }
}
```
###### \java\seedu\address\logic\commands\ExportCommand.java
``` java

/**
 * Exports the address book to a user-defined location {@code filePath}
 */
public class ExportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_ALIAS = "exp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports current data into defined file path. "
            + "Parameters: FILEPATH (must end with an extension of .xml or .csv)\n"
            + "Example: " + COMMAND_WORD + " "
            + " C:\\Users\\John Doe\\Documents\\addressbook.xml\n";

    public static final String MESSAGE_EXPORT_SUCCESS = "Addressbook data exported to: %1$s";
    public static final String MESSAGE_NOT_XML_CSV_FILE = "Filepath does not lead to an XML/CSV file.";
    public static final String MESSAGE_ERROR = "Addressbook data not exported successfully.";
    public static final String MESSAGE_EXISTING_XML_CSV = "XML/CSV file name already exists. Choose a different name.";

    private Storage storage;
    private final String filePath;

    /**
     * Creates an ExportCommand to add the specified {@code String}
     */
    public ExportCommand(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @param storage
     */
    @Override
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            if (FileUtil.isValidCsvFile(filePath)) {
                storage.exportAddressBookCsv(model.getAddressBook(), filePath);
            } else {
                storage.exportAddressBook(model.getAddressBook(), filePath);
            }
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR);
        } catch (InvalidFileException e) {
            throw new CommandException(MESSAGE_NOT_XML_CSV_FILE);
        } catch (ExistingFileException e) {
            throw new CommandException(MESSAGE_EXISTING_XML_CSV);
        }
        return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && filePath.equals(((ExportCommand) other).filePath));
    }
}

```
###### \java\seedu\address\logic\commands\FindEventCommand.java
``` java

/**
 * Finds and lists all events in event book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive, and only one field can be searched.
 */
public class FindEventCommand extends Command {

    public static final String COMMAND_WORD = "findevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all events whose title or description contain any "
            + "of the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: et/KEYWORD [MORE_KEYWORDS]... for title OR ed/KEYWORD [MORE_KEYWORDS]... for description\n"
            + "Example: " + COMMAND_WORD + " et/movie date party";

    private final TitleContainsKeywordsPredicate predicate;

    public FindEventCommand(TitleContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredEventList(predicate);
        return new CommandResult(getMessageForEventListShownSummary(model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindEventCommand // instanceof handles nulls
                && this.predicate.equals(((FindEventCommand) other).predicate));
    }
}
```
###### \java\seedu\address\logic\commands\GoogleCommand.java
``` java

/**
 * Selects and google searches a person's name identified using the last displayed index from the address book.
 */
public class GoogleCommand extends Command {

    public static final String COMMAND_WORD = "google";
    public static final String COMMAND_ALIAS = "g";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Googles the person's name identified by the index number used in the latest person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_GOOGLE_PERSON_SUCCESS = "Google Searched Person: %1$s";

    private final Index targetIndex;

    public GoogleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new GoogleContactNameEvent(lastShownList.get(targetIndex.getZeroBased())));
        return new CommandResult(String.format(MESSAGE_GOOGLE_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoogleCommand // instanceof handles nulls
                && this.targetIndex.equals(((GoogleCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\JumpToCommand.java
``` java

/**
 * Jumps to user-defined month and year on the calendar
 */
public class JumpToCommand extends Command {
    public static final String COMMAND_WORD = "jumpto";
    public static final String COMMAND_ALIAS = "jt";
    public static final String JUMP_TO_MESSAGE = "Jumped to: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Jumps to a specified year (between 1900 to 2300) and month (between 01 to 12) on the calendar.\n"
            + "Parameters: yyyy-mm\n"
            + "Example: " + COMMAND_WORD + " 2018-02";

    private final YearMonth yearMonth;

    /**
     * Creates a JumpToCommand to jump to specified year and month of the calendar
     */
    public JumpToCommand(YearMonth yearMonthValue)  {
        yearMonth = yearMonthValue;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new JumpToCalendarRequestEvent(yearMonth));
        return new CommandResult(String.format(JUMP_TO_MESSAGE, yearMonth.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JumpToCommand // instanceof handles nulls
                && yearMonth.equals(((JumpToCommand) other).yearMonth));
    }
}
```
###### \java\seedu\address\logic\commands\ListAllEventsCommand.java
``` java

/**
 * Lists all events in the event book to the user.
 */
public class ListAllEventsCommand extends Command {
    public static final String COMMAND_WORD = "listallevents";
    public static final String COMMAND_WORD_TWO = "listevents";
    public static final String COMMAND_WORD_THREE = "allevents";

    @Override
    public CommandResult execute() {
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult("All "
                + getMessageForEventListShownSummary(model.getFilteredEventList().size()));
    }
}
```
###### \java\seedu\address\logic\commands\SortEventCommand.java
``` java

/**
 * Sorts the event list according to the specified parameter
 */
public class SortEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sortevent";
    public static final String COMMAND_WORD_TWO = "sortevents";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Order the Event Book based on a specified parameter.\n"
            + "Parameters:  TITLE, LOCATION, DATETIME\n"
            + "Example: " + COMMAND_WORD + " TITLE";

    public static final String MESSAGE_SORT_SUCCESS = "Event Book has been sorted by ";
    public static final String MESSAGE_SORT_WRONG_PARAMETER =
            "The parameter can only contain Title, Location or Datetime";

    private String sortParameter;

    public SortEventCommand(String sortParameter) {
        this.sortParameter = sortParameter;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    public String getSortParameter() {
        return sortParameter;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        try {
            model.sortEventList(sortParameter);

        } catch (CommandException e) {
            return new CommandResult(MESSAGE_SORT_WRONG_PARAMETER);
        }
        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS + sortParameter));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortEventCommand // instanceof handles nulls
                && this.sortParameter.equals(((SortEventCommand) other).sortParameter)); // state check
    }

}
```
###### \java\seedu\address\logic\commands\SwitchTabCommand.java
``` java

/**
 * switch between the Events and Tasks list
 */
public class SwitchTabCommand extends Command {

    public static final String COMMAND_WORD = "switchtab";
    public static final String COMMAND_WORD_TWO = "switch";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches between Event and Task UI List Tab";
    public static final String MESSAGE_SUCCESS = "Switched Tabs!";

    private static final int EVENTS_TAB = 0;
    private static final int TASKS_TAB = 1;

    private final TabPane tabPane;

    public SwitchTabCommand(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    @Override
    public CommandResult execute() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        int selectedIndex = selectionModel.getSelectedIndex();
        if (selectedIndex == EVENTS_TAB) {
            selectedIndex = TASKS_TAB;
        } else {
            selectedIndex = EVENTS_TAB;
        }
        selectionModel.select(selectedIndex);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public void setCalendarView(CalendarView calendarView) {
        this.calendarViewStateParser = new CalendarViewStateParser(this.userPrefs, this.model, calendarView);
    }

    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return model.getFilteredEventList();
    }

    @Override
    public void setTabPane(TabPane tabPane) {
        addressBookParser.setTabPane(tabPane);
    }
}
```
###### \java\seedu\address\logic\parser\AddEventCommandParser.java
``` java

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddEventCommand parse(String args) throws IllegalValueException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_EVENT_TITLE, PREFIX_EVENT_DESCRIPTION, PREFIX_EVENT_LOCATION, PREFIX_EVENT_DATETIME);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_EVENT_TITLE, PREFIX_EVENT_DESCRIPTION, PREFIX_EVENT_LOCATION, PREFIX_EVENT_DATETIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        String title = argMultimap.getValue(PREFIX_EVENT_TITLE).get();
        String description = argMultimap.getValue(PREFIX_EVENT_DESCRIPTION).get();
        String location = argMultimap.getValue(PREFIX_EVENT_LOCATION).get();
        Datetime datetime = ParserUtil.parseDatetime(argMultimap.getValue(PREFIX_EVENT_DATETIME)).get();

        ReadOnlyEvent event = new Event(title, description, location, datetime);

        return new AddEventCommand(event);
    }
}
```
###### \java\seedu\address\logic\parser\CalendarViewStateParser.java
``` java

/**
* Parses user input to update calendar UI state.
*/
public class CalendarViewStateParser {

    private static UserPrefs userPrefs;
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private final Model model;
    private CalendarView calendarView;

    public CalendarViewStateParser(UserPrefs userPrefs, Model model, CalendarView calendarView) {
        this.userPrefs = userPrefs;
        this.model = model;
        this.calendarView = calendarView;
    }

    /**
     * update the state of the calendar UI object with reference to the user input
     *
     * @param userInput
     * @throws ParseException
     */
    public void updateViewState(String userInput) throws ParseException {

        //Checks whether CalendarView is null
        requireNonNull(calendarView);

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");

        if (commandWord.equals(AddEventCommand.COMMAND_WORD)
                || commandWord.equals(DeleteEventCommand.COMMAND_WORD)
                || commandWord.equals(ClearCommand.COMMAND_WORD)
                || commandWord.equals(ClearCommand.COMMAND_ALIAS)
                || commandWord.equals(UndoCommand.COMMAND_WORD)
                || commandWord.equals(UndoCommand.COMMAND_ALIAS)
                || commandWord.equals(RedoCommand.COMMAND_WORD)
                || commandWord.equals(RedoCommand.COMMAND_ALIAS)
                || commandWord.equals(ClearEventsCommand.COMMAND_WORD)) {
            CalendarViewUpdate.updateViewState(calendarView);
        } else if (commandWord.equals(FindEventCommand.COMMAND_WORD)) {
            CalendarViewUpdate.updateFindState(calendarView, model);
        }
    }
}
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_EVENT_TITLE = new Prefix("et/");
    public static final Prefix PREFIX_EVENT_DESCRIPTION = new Prefix("ed/");
    public static final Prefix PREFIX_EVENT_LOCATION = new Prefix("el/");
    public static final Prefix PREFIX_EVENT_DATETIME = new Prefix("edt/");

```
###### \java\seedu\address\logic\parser\DeleteEventCommandParser.java
``` java

/**
 * Parses input arguments and creates a new DeleteEventCommand object
 */
public class DeleteEventCommandParser implements Parser<DeleteEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEventCommand
     * and returns an DeleteEventCommand object for execution.
     *
     * @throws ParseException if the user input is not of acceptable format
     */
    @Override
    public DeleteEventCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteEventCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\FindEventCommandParser.java
``` java

/**
 * Parses input arguments and creates a new FindEventCommand object
 */
public class FindEventCommandParser implements Parser<FindEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindEventCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindEventCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty() || trimmedArgs.length() < 3) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }
        if (trimmedArgs.substring(0, 3).equals("et/")) {
            TitleContainsKeywordsPredicate.setPredicateType("et");
        } else if (trimmedArgs.substring(0, 3).equals("ed/")) {
            TitleContainsKeywordsPredicate.setPredicateType("ed");
        } else if (trimmedArgs.length() > 3 && trimmedArgs.substring(0, 4).equals("edt/")) {
            TitleContainsKeywordsPredicate.setPredicateType("edt");
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.substring(0, 3).equals("edt")) {
            trimmedArgs = trimmedArgs.substring(4).trim();
        } else {
            trimmedArgs = trimmedArgs.substring(3).trim();
        }

        if (trimmedArgs.equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        String[] titleKeywords = trimmedArgs.split("\\s+");

        return new FindEventCommand(new TitleContainsKeywordsPredicate(Arrays.asList(titleKeywords)));
    }
}
```
###### \java\seedu\address\logic\parser\GoogleCommandParser.java
``` java

/**
 * Parses input arguments and creates a new GoogleCommand object
 */
public class GoogleCommandParser implements Parser<GoogleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GoogleCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new GoogleCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoogleCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\JumpToCommandParser.java
``` java

/**
 * Parses input arguments and creates a new JumpToCommand object
 */
public class JumpToCommandParser implements Parser<JumpToCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the JumpToCommand
     * and returns an JumpToCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public JumpToCommand parse(String args) throws ParseException {
        try {
            YearMonth yearMonth = ParserUtil.parseYearmonth(args);
            return new JumpToCommand(yearMonth);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, JumpToCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java

    /**
     * Parses a {@code Optional<String> datetime} into an {@code Optional<Datetime>} if {@code datetime} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Datetime> parseDatetime(Optional<String> datetime) throws IllegalValueException {
        requireNonNull(datetime);
        return datetime.isPresent() ? Optional.of(new Datetime(datetime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String yearmonth} into a {@code YearMonth} if {@code yearmonth} is present.
     */
    public static YearMonth parseYearmonth(String yearmonth) throws IllegalValueException {
        requireNonNull(yearmonth);
        if (yearmonth.length() != JUMPTO_VALID_ARGS_LENGTH) {
            throw new IllegalValueException(MESSAGE_USAGE);
        }

        int year = Integer.parseInt(yearmonth.substring(1, 5));
        String separator = yearmonth.substring(5, 6);
        int month = Integer.parseInt(yearmonth.substring(6));

        if (!separator.equals("-") || 1900 > year || year > 2300 || 1 > month || month > 12) {
            throw new IllegalValueException(MESSAGE_USAGE);
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth;
    }
}
```
###### \java\seedu\address\logic\parser\SortEventCommandParser.java
``` java

/**
 * Parses input arguments and creates a new SortEventCommand object
 */
public class SortEventCommandParser implements Parser<SortEventCommand> {

    /**
     * Parses the given {@code String} of arguments
     * and returns a SortEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortEventCommand.MESSAGE_USAGE));
        }
        String upperCaseParameter = trimmedArgs.toUpperCase();
        return new SortEventCommand(upperCaseParameter);
    }
}
```
###### \java\seedu\address\model\event\Datetime.java
``` java

/**
 * Represents an Event's Datetime in the event book.
 * Ensures valid Datetime input and aids future implementations involving NLP, etc.
 */
public class Datetime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Event datetime should be in the format: yyyy-mm-dd hhmm\n"
            + "Datetime values should also be logical (eg. hhmm should be between 0000 to 2359)";

    private static final int VALID_DATETIME_LENGTH = 15;

    public final String value;

    /**
     * Validates given datetime.
     *
     * @throws IllegalValueException if given datetime string is invalid.
     */
    public Datetime(String datetime) throws IllegalValueException {
        requireNonNull(datetime);
        String trimmedDatetime = datetime.trim();

        if (!isValidDatetime(trimmedDatetime)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.value = trimmedDatetime;
    }

    /**
     * Returns true if a given string is a valid event datetime.
     */
    public static boolean isValidDatetime(String datetime) {
        Boolean validTime = false;
        Boolean validDate = false;
        Boolean validFormat = false;

        if (datetime.length() != VALID_DATETIME_LENGTH) {
            return false;
        }

        try {
            int year = Integer.parseInt(datetime.substring(0, 4));
            int month = Integer.parseInt(datetime.substring(5, 7));
            int day = Integer.parseInt(datetime.substring(8, 10));
            int hour = Integer.parseInt(datetime.substring(11, 13));
            int min = Integer.parseInt(datetime.substring(13, 15));

            String firstSeperator = datetime.substring(4, 5);
            String secondSeperator = datetime.substring(7, 8);
            String thirdSeperator = datetime.substring(10, 11);

            //Format Validation
            if (firstSeperator.equals("-") & secondSeperator.equals("-") & thirdSeperator.equals(" ")) {
                validFormat = true;
            }

            //Time Validation
            if (0 <= hour && hour <= 23) {
                if (0 <= min && min <= 59) {
                    validTime = true;
                }
            }

            //Date Validation
            if (day >= 1) {
                // For months with 30 days.
                if ((month == 4
                        || month == 6
                        || month == 9
                        || month == 11)
                        && day <= 30) {
                    validDate = true;
                }
                // For months with 31 days.
                if ((month == 1
                        || month == 3
                        || month == 5
                        || month == 7
                        || month == 8
                        || month == 10
                        || month == 12)
                        && day <= 31) {
                    validDate = true;
                }
                // For February.
                if (month == 2) {
                    if (day <= 28) {
                        validDate = true;
                    } else if (day == 29) {
                        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                            validDate = true;
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return validTime && validDate && validFormat;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Datetime
                && this.value.equals(((Datetime) other).value));
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\address\model\event\Event.java
``` java

/**
 * Represents an Event in the event book.
 * Makes sure all fields are filled and not null.
 */
public class Event implements ReadOnlyEvent {

    private ObjectProperty<String> title;
    private ObjectProperty<String> description;
    private ObjectProperty<String> location;
    private ObjectProperty<Datetime> datetime;

    /**
     * Every field must be present and not null.
     */
    public Event(String title, String description, String location, Datetime datetime) {
        requireAllNonNull(title, description, location, datetime);
        this.title = new SimpleObjectProperty<>(title);
        this.description = new SimpleObjectProperty<>(description);
        this.location = new SimpleObjectProperty<>(location);
        this.datetime = new SimpleObjectProperty<>(datetime);
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     * This prevents the original version from being changed unknowingly.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getTitle(), source.getDescription(), source.getLocation(), source.getDatetime());
    }

    @Override
    public ObjectProperty<String> titleProperty() {
        return title;
    }

    @Override
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(requireNonNull(title));
    }

    @Override
    public ObjectProperty<String> descriptionProperty() {
        return description;
    }

    @Override
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(requireNonNull(description));
    }

    @Override
    public ObjectProperty<String> locationProperty() {
        return location;
    }

    @Override
    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(requireNonNull(location));
    }

    @Override
    public ObjectProperty<Datetime> datetimeProperty() {
        return datetime;
    }

    @Override
    public Datetime getDatetime() {
        return datetime.get();
    }

    public void setDatetime(Datetime datetime) {
        this.datetime.set(requireNonNull(datetime));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent
                && this.isSameStateAs((ReadOnlyEvent) other));
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, location, datetime);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
```
###### \java\seedu\address\model\event\exceptions\DuplicateEventException.java
``` java

/**
 * Signals that the operation will result in duplicate Event objects.
 */
public class DuplicateEventException extends DuplicateDataException {
    public DuplicateEventException() {
        super("Event already exists in the event list!");
    }
}
```
###### \java\seedu\address\model\event\ReadOnlyEvent.java
``` java

/**
 * Interface for Events related to Calendar View feature.
 */
public interface ReadOnlyEvent {
    ObjectProperty<String> titleProperty();

    String getTitle();

    ObjectProperty<String> descriptionProperty();

    String getDescription();

    ObjectProperty<String> locationProperty();

    String getLocation();

    ObjectProperty<Datetime> datetimeProperty();

    Datetime getDatetime();

    /**
     * Checks if @param other is of the same state as this.
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null
                && other.getTitle().equals(this.getTitle())
                && other.getDescription().equals(this.getDescription())
                && other.getLocation().equals(this.getLocation())
                && other.getDatetime().equals(this.getDatetime()));
    }

    /**
     * Formats the event as text, showing all event details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Description: ")
                .append(getDescription())
                .append(" Location: ")
                .append(getLocation())
                .append(" Datetime: ")
                .append(getDatetime().value);
        return builder.toString();
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    default String getEventDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_EVENT_TITLE + getTitle() + " ");
        sb.append(PREFIX_EVENT_DESCRIPTION + getDescription() + " ");
        sb.append(PREFIX_EVENT_LOCATION + getLocation() + " ");
        sb.append(PREFIX_EVENT_DATETIME + getDatetime().value + " ");

        return sb.toString();
    }

}
```
###### \java\seedu\address\model\event\ReadOnlyEventBook.java
``` java

/**
 * A read-only immutable interface for a event in the eventbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEventBook {

    ObjectProperty<String> titleProperty();

    String getTitle();

    ObjectProperty<String> descriptionProperty();

    String getDescription();

    ObjectProperty<String> locationProperty();

    String getLocation();

    ObjectProperty<String> datetimeProperty();

    String getDatetime();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEventBook other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.getDescription().equals(this.getDescription())
                && other.getLocation().equals(this.getLocation())
                && other.getDatetime().equals(this.getDatetime()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Description: ")
                .append(getDescription())
                .append(" Location: ")
                .append(getLocation())
                .append(" Datetime: ")
                .append(getDatetime());
        return builder.toString();
    }

    List<? extends ReadOnlyEvent> getEventList();
}
```
###### \java\seedu\address\model\event\TitleContainsKeywordsPredicate.java
``` java

/**
 * Tests that a {@code ReadOnlyEvent}'s {@code Title} matches any of the keywords given.
 */
public class TitleContainsKeywordsPredicate implements Predicate<ReadOnlyEvent> {
    private static String predicateType = "et";
    private final List<String> keywords;

    public TitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public static void setPredicateType(String predicateType) {
        TitleContainsKeywordsPredicate.predicateType = predicateType;
    }

    @Override
    public boolean test(ReadOnlyEvent event) {
        if (predicateType.equals("et")) {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getTitle(), keyword));
        }

        if (predicateType.equals("edt")) {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getDatetime().value, keyword));
        }

        if (predicateType.equals("ed")) {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getDescription(), keyword));
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TitleContainsKeywordsPredicate
                && this.keywords.equals(((TitleContainsKeywordsPredicate) other).keywords));
    }
}
```
###### \java\seedu\address\model\event\UniqueEventList.java
``` java

/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();

    private final ObservableList<ReadOnlyEvent> mappedList = EasyBind.map(internalList, (event) -> event);

    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(ReadOnlyEvent toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an event to the list.
     */
    public void add(ReadOnlyEvent toAdd) throws DuplicateEventException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(new Event(toAdd));
    }

    /**
     * Removes the equivalent event from the list.
     *
     * @throws CommandException if no such event could be found in the list.
     */
    public boolean remove(ReadOnlyEvent toRemove) throws CommandException {
        requireNonNull(toRemove);
        final boolean eventFoundAndDeleted = internalList.remove(toRemove);
        if (!eventFoundAndDeleted) {
            throw new CommandException("");
        }
        return eventFoundAndDeleted;
    }

    public void setEvents(UniqueEventList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setEvents(List<? extends ReadOnlyEvent> events) throws DuplicateEventException {
        final UniqueEventList replacement = new UniqueEventList();
        for (final ReadOnlyEvent event : events) {
            replacement.add(new Event(event));
        }
        setEvents(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyEvent> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    /**
     * Sorts the list.
     */
    public void sortBy(String parameter) throws CommandException {
        requireNonNull(parameter);
        Comparator<Event> orderByTitle = (Event a, Event b) -> a.getTitle().toString()
                .compareToIgnoreCase(b.getTitle().toString());
        Comparator<Event> orderByLocation = (Event a, Event b) -> a.getLocation().toString()
                .compareToIgnoreCase(b.getLocation().toString());
        Comparator<Event> orderByDatetime = (Event a, Event b) -> {

            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hhmm");
            try {
                Date datetime1 = formatDate.parse(a.getDatetime().value);
                Date datetime2 = formatDate.parse(b.getDatetime().value);

                return datetime2.compareTo(datetime1);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;

        };

        switch (parameter) {
        case "TITLE":
            internalList.sort(orderByTitle);
            break;

        case "LOCATION":
            internalList.sort(orderByLocation);
            break;

        case "DATETIME":
            internalList.sort(orderByDatetime);
            break;

        default:
            throw new CommandException("");
        }


    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEventList
                && this.internalList.equals(((UniqueEventList) other).internalList));
    }
}
```
###### \java\seedu\address\model\EventBook.java
``` java

/**
 * Wraps all data at the event-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class EventBook implements ReadOnlyEventBook {

    private final UniqueEventList events;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        events = new UniqueEventList();
    }

    public EventBook() {}

    /**
     * Creates an EventBook using the Events in the {@code toBeCopied}
     */
    public EventBook(ReadOnlyEventBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setEvents(List<? extends ReadOnlyEvent> events) {
        try {
            this.events.setEvents(events);
        } catch (DuplicateEventException dee) {
            throw new AssertionError("Eventbooks should not have duplicate events");
        }
    }

    /**
     * Resets the existing data of this {@code EventBook} with {@code newData}.
     */
    public void resetData(ReadOnlyEventBook newData) {
        requireNonNull(newData);
        try {
            this.events.setEvents(newData.getEventList());
        } catch (DuplicateEventException dee) {
            throw new AssertionError("Eventbooks should not have duplicated events");
        }
    }

    /**
     * Adds an event to the event book.
     *
     * @throws CommandException if an equivalent event already exists.
     */
    public void addEvent(ReadOnlyEvent e) throws CommandException, DuplicateEventException {
        Event newEvent = new Event(e);
        events.add(newEvent);
    }

    /**
     * Removes {@code key} from this {@code EventBook}.
     */
    public boolean removeEvent(ReadOnlyEvent key) throws CommandException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new CommandException("");
        }
    }

    /**
     * Sorts list of all events in the event Book based on the specified parameter.
     */
    public void sortBy(String parameter) throws CommandException {
        events.sortBy(parameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(events);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventBook // instanceof handles nulls
                && this.events.equals(((EventBook) other).events));
    }

    @Override
    public String toString() {
        return events.asObservableList().size() + " events";
    }

    @Override
    public ObjectProperty<String> titleProperty() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public ObjectProperty<String> descriptionProperty() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public ObjectProperty<String> locationProperty() {
        return null;
    }

    @Override
    public String getLocation() {
        return null;
    }

    @Override
    public ObjectProperty<String> datetimeProperty() {
        return null;
    }

    @Override
    public String getDatetime() {
        return null;
    }

    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        return events.asObservableList();
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void resetData(ReadOnlyEventBook newData) {
        eventBook.resetData(newData);
        indicateEventBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java

    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public void addEvent(ReadOnlyEvent eventToAdd) throws CommandException, DuplicateEventException {
        eventBook.addEvent(eventToAdd);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateEventBookChanged();
    }

    private void indicateEventBookChanged() {
        raise(new EventBookChangedEvent(eventBook));
    }

    @Override
    public void deleteEvent(ReadOnlyEvent eventToDelete) throws CommandException {
        eventBook.removeEvent(eventToDelete);
        indicateEventBookChanged();
    }

    @Override
    public void sortEventList(String parameter) throws CommandException {
        eventBook.sortBy(parameter);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateEventBookChanged();
    }

    @Override
    public ReadOnlyEventBook getEventBook() {
        return eventBook;
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return FXCollections.unmodifiableObservableList(filteredTasks);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
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
                && taskBook.equals(other.taskBook)
                && filteredPersons.equals(other.filteredPersons)
                && filteredTasks.equals(other.filteredTasks);
    }

}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java

    public static Event[] getSampleEvents() throws IllegalValueException {
        return new Event[]{
            new Event("Company Charity Event", "Annual fund-raising event",
                "VivoCity", new Datetime("2017-10-31 0930")),
            new Event("Launch Marketing Campaign", "New products to be released",
                "-", new Datetime("2018-03-14 1000")),
            new Event("Class Reunion", "Catchup session with old classmates",
                "Tampines Hub", new Datetime("2018-04-15 1745")),
            new Event("CS2103 Demo Presentation", "Flight to London",
                "COM1-B103", new Datetime("2018-04-19 1500")),
            new Event("Marketing Department Meeting", "Monthly Meeting",
                "Company Conference Room 2", new Datetime("2018-04-20 1315")),
            new Event("Flight to London", "-",
                "Changi Airport", new Datetime("2018-05-12 2250")),
            new Event("Company Tour", "Checking out company operations in London",
                "ABC Company (London)", new Datetime("2018-05-14 0930")),
            new Event("Overseas Meeting", "Discussion w/ London representatives",
                "ABC Conference Hall 1", new Datetime("2018-05-18 1300")),
            new Event("Flight back to Singapore", "-",
                "Heathrow Airport", new Datetime("2018-05-21 1420")),
            new Event("Musical Concert", "Friend's performance",
                "NUS UCC", new Datetime("2018-05-23 1900"))
        };
    }

    public static ReadOnlyEventBook getSampleEventBook() {
        try {
            EventBook sampleEb = new EventBook();
            for (Event sampleEvent : getSampleEvents()) {
                sampleEb.addEvent(sampleEvent);
            }
            return sampleEb;
        } catch (CommandException e) {
            throw new AssertionError("sample data cannot contain duplicate events ", e);
        } catch (IllegalValueException e) {
            throw new AssertionError("Invalid input given!");
        }
    }

```
###### \java\seedu\address\storage\CsvFileStorage.java
``` java

/**
 * Stores addressbook data in a CSV file
 */
public class CsvFileStorage {

    // This string forms the header for the Google CSV format.
    public static final String GOOGLE_CSV_FORMAT = "Name,Given Name,Additional Name,Family Name,Yomi Name,"
            + "Given Name Yomi,Additional Name Yomi,Family Name Yomi,Name Prefix,Name Suffix,Initials,Nickname,"
            + "Short Name,Maiden Name,Birthday,Gender,Location,Billing Information,Directory Server,Mileage,"
            + "Occupation,Hobby,Sensitivity,Priority,Subject,Notes,Group Membership,E-mail 1 - Type,E-mail 1 - Value,"
            + "Phone 1 - Type,Phone 1 - Value,Phone 2 - Type,Phone 2 - Value,Organization 1 - Type,"
            + "Organization 1 - Name,Organization 1 - Yomi Name,Organization 1 - Title,Organization 1 - Department,"
            + "Organization 1 - Symbol,Organization 1 - Location,Organization 1 - Job Description";

    /**
     * Saves the given addressbook data to the specified file.
     */
    public static void saveDataToFile(ReadOnlyAddressBook addressBook, String filePath) throws IOException {

        PrintWriter pw = new PrintWriter(new File(filePath));
        StringBuilder sb = new StringBuilder();

        sb.append(GOOGLE_CSV_FORMAT);
        sb.append('\n');
        for (Person person : addressBook.getPersonList()) {
            sb.append(person.getName());
            sb.append(",,,,,,,,,,,,,,,,,,,,,,,,,,");
            sb.append("* My Contacts");
            sb.append(",,");
            sb.append(person.getEmail());
            sb.append(",,");
            sb.append(person.getPhone());
            sb.append(",,,,,,,,,,");
            sb.append('\n');
        }
        pw.write(sb.toString());
        pw.close();
    }

}
```
###### \java\seedu\address\storage\EventBookStorage.java
``` java

/**
 * Represents a storage for {@link seedu.address.model.EventBook}.
 */
public interface EventBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getEventBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyEventBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException, IOException, JAXBException;

    /**
     * @see #getEventBookFilePath()
     */
    Optional<ReadOnlyEventBook> readEventBook(String filePath)
            throws DataConversionException, IOException, JAXBException;

    /**
     * Saves the given {@link ReadOnlyEventBook} to the storage.
     *
     * @param eventBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEventBook(ReadOnlyEventBook eventBook) throws IOException;

    /**
     * @see #saveEventBook(ReadOnlyEventBook)
     */
    void saveEventBook(ReadOnlyEventBook eventBook, String filePath) throws IOException;

    /**
     * @see #saveEventBook(ReadOnlyEventBook)
     */
    void backupEventBook(ReadOnlyEventBook eventBook) throws IOException;

    /**
     * @see #exportEventBook()
     */
    void exportEventBook() throws ParserConfigurationException, IOException, TransformerException;

}
```
###### \java\seedu\address\storage\XmlEventBookStorage.java
``` java

/**
 * A class to access TunedIn EventBook data stored as an xml file on the hard disk.
 */
public class XmlEventBookStorage implements EventBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlEventBookStorage.class);

    private String filePath;
    private String exportedPath;
    private String header;

    // default constructor
    public XmlEventBookStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getEventBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook() throws IOException, JAXBException, DataConversionException {
        return readEventBook(filePath);
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook(String filePath)
            throws FileNotFoundException, JAXBException, DataConversionException {
        requireNonNull(filePath);

        File eventBookFile = new File(filePath);

        if (!eventBookFile.exists()) {
            logger.info("EventBook file " + eventBookFile + " not found");
            return Optional.empty();
        }

        XmlSerializableEventBook eventBookOptional = XmlFileStorage.loadEventDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(eventBookOptional.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + eventBookOptional + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        } catch (CommandException e) {
            logger.info("Invalid command in " + eventBookOptional + ": " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook) throws IOException {
        saveEventBook(eventBook, filePath);
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook, String filePath) throws IOException {
        requireNonNull(eventBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableEventBook(eventBook));
    }

    @Override
    public void backupEventBook(ReadOnlyEventBook eventBook) throws IOException {
        saveEventBook(eventBook, filePath + "-backup");
    }

    @Override
    public void exportEventBook() throws ParserConfigurationException, IOException {
        //TODO BY V2.0
    }
}
```
###### \java\seedu\address\storage\XmlSerializableEventBook.java
``` java

/**
 * An Immutable EventBook that is serializable to XML format
 */
@XmlRootElement(name = "eventbook")
public class XmlSerializableEventBook {

    private static final Logger logger = LogsCenter.getLogger(XmlEventBookStorage.class);

    @XmlElement
    private List<XmlAdaptedEvent> events;

    /**
     * Creates an empty XmlSerializableEventBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableEventBook() {
        events = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableEventBook(ReadOnlyEventBook src) {
        this();
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this eventbook into the model's {@code EventBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code XmlAdaptedEvent}.
     */
    public EventBook toModelType() throws IllegalValueException, CommandException {
        EventBook eventBook = new EventBook();
        for (XmlAdaptedEvent e : events) {
            eventBook.addEvent(e.toModelType());
        }
        return eventBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableEventBook)) {
            return false;
        }

        XmlSerializableEventBook otherAb = (XmlSerializableEventBook) other;
        return events.equals(otherAb.events);
    }
}
```
###### \java\seedu\address\ui\AnchorPaneNode.java
``` java

/**
 * Creates anchor pane to store additional data.
 */
public class AnchorPaneNode extends AnchorPane {
    // Date associated with this pane
    private LocalDate date;
    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */

    public AnchorPaneNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked
        //this.setOnMouseClicked(e -> System.out.println("This pane's date is: " + date));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java

/**
 * The Browser Panel of the App.
 * The default page is Google's home page, but can also be used to
 * google for contacts selected by the Select Command.
 */
public class BrowserPanel extends UiPart<Stage> {

    public static final String GOOGLE_URL = "https://www.google.com.sg/";
    public static final String SEARCH_PAGE_URL =
            "https://www.google.com.sg/search?q=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    /**
     * Creates a new Google Search page.
     *
     * @param root Stage to use as the root of the GoogleSearch.
     */
    public BrowserPanel(Stage root, Person person) {
        super(FXML, root);
        if (person != null) {
            this.loadPersonPage(person);
        } else {
            this.loadPage(GOOGLE_URL);
        }
        registerAsAnEventHandler(this);
    }

    public BrowserPanel(Person person) {
        this(new Stage(), person);
    }

    private void loadPersonPage(Person person) {
        loadPage(SEARCH_PAGE_URL + person.getName().fullName);
    }

    public void loadPage(String url) {
        browser.getEngine().load(url);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    /**
     * Shows the GoogleSearch window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing Google Search page.");
        getRoot().show();
    }
}
```
###### \java\seedu\address\ui\CalendarView.java
``` java

/**
 * Creates a Calendar View
 */
public class CalendarView {
    private ArrayList<AnchorPaneNode> calendarMonth = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth defaultYearMonth;
    private YearMonth currentYearMonth;
    private ObservableList<ReadOnlyEvent> eventList;
    private Logic logic;
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    /**
     * Provides layout for the calendar month with anchor panes.
     */
    public CalendarView(Logic logic, ObservableList<ReadOnlyEvent> eventList, YearMonth yearMonth) {
        this.logic = logic;
        this.eventList = eventList;
        defaultYearMonth = yearMonth;
        currentYearMonth = yearMonth;

        // Creates the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(450, 300);

        // Create rows and columns of anchor panes for calendar
        calendarMonthSetup(calendar);

        // Days of the Week
        Text[] days = new Text[]{new Text("SUNDAY"), new Text("MONDAY"), new Text("TUESDAY"),
            new Text("WEDNESDAY"), new Text("THURSDAY"), new Text("FRIDAY"), new Text("SATURDAY")};
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(450);
        Integer col = 0;

        for (Text day : days) {
            day.setFill(Color.WHITE);
            day.setFont(new Font("Serif", 13));
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            AnchorPane.setBottomAnchor(day, 5.0);
            ap.getChildren().add(day);
            dayLabels.add(ap, col++, 0);
        }

        // Creates a title for the calendar
        calendarTitle = new Text();
        calendarTitle.setFill(Color.WHITE);
        calendarTitle.setFont(new Font("Serif", 16));

        // Buttons to navigate through months
        Button previousMonth = new Button("< Previous");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button("Next >");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        HBox.setMargin(calendarTitle, new Insets(0, 10, 0, 10));
        titleBar.setAlignment(Pos.BASELINE_CENTER);

        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth, null);

        // Displaying the current date and time
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        Button calendarDateTime = new Button("Today's date is " + dateTimeFormat.format(LocalDate.now()));
        calendarDateTime.setStyle("-fx-border-color: transparent; "
                + "-fx-background-color: transparent; -fx-font-size: 18");
        calendarDateTime.setOnAction(e -> originalYearMonth());
        HBox calendarDtBar = new HBox(calendarDateTime);
        calendarDtBar.setAlignment(Pos.BASELINE_CENTER);

        // Displaying a welcome message
        Text welcomeMessage = new Text("Welcome to BizConnect Journal!");
        welcomeMessage.setFill(Color.WHITE);
        welcomeMessage.setFont(new Font("Impact", 24));
        HBox welcomeMessageBar = new HBox(welcomeMessage);
        welcomeMessageBar.setAlignment(Pos.BASELINE_CENTER);

        // Creates the calendar view
        view = new VBox(calendarDtBar, titleBar, dayLabels, calendar);
        VBox.setMargin(calendarDtBar, new Insets(0, 0, 5, 0));
        VBox.setMargin(titleBar, new Insets(0, 0, 5, 0));

        EventsCenter.getInstance().registerHandler(this);
    }

    /**
     * Sets the calendar days according to the intended month and year
     * @param targetIndex for finding specific event(s)
     * @param yearMonth for desired year and month of the calendar view
     */
    public void populateCalendar(YearMonth yearMonth, Index targetIndex) {
        // Gets the current date as reference
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);

        // Sets first day to be a Sunday
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }

        // Fills up calendar with day numbers
        for (AnchorPaneNode ap : calendarMonth) {
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }

            String dayValue = String.valueOf(calendarDate.getDayOfMonth());
            String monthValue = String.valueOf(calendarDate.getMonthValue());
            String yearValue = String.valueOf(calendarDate.getYear());

            boolean eventExist = false;

            if (targetIndex == null) {
                eventExist = eventList.stream()
                        .anyMatch(e -> checkEventDay(e, dayValue)
                                && checkEventMonth(e, monthValue)
                                && checkEventYear(e, yearValue));
            } else {
                ReadOnlyEvent e = eventList.get(targetIndex.getZeroBased());

                if (checkEventDay(e, dayValue)
                        && checkEventMonth(e, monthValue)
                        && checkEventYear(e, yearValue)) {
                    eventExist = true;
                }
            }

            Text dateNumber = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            // Days from a different month shows up as a different colour
            if (calendarDate.getMonthValue() != yearMonth.getMonthValue()) {
                dateNumber.setFill(Color.DARKGREY);
            } else {
                dateNumber.setFill(Color.WHITE);
            }
            ap.setDate(calendarDate);
            ap.setTopAnchor(dateNumber, 5.0);
            ap.setLeftAnchor(dateNumber, 5.0);

            if (eventExist) {
                ap.setOnMouseClicked(ev -> {
                    String commandText = FindEventCommand.getCommandWord()
                            + " " + PREFIX_EVENT_DATETIME + getFormatDate(yearValue, monthValue, dayValue);
                    try {
                        CommandResult commandResult = logic.execute(commandText);
                        logger.info("Command Result: " + commandResult.feedbackToUser);

                    } catch (CommandException | IllegalValueException e) {
                        logger.info("Invalid Command: " + commandText);
                    }
                });
                ap.setStyle("-fx-background-color: #2e5577;");
            } else {
                ap.setStyle("-fx-background-color: #3d719d;");
            }

            ap.getChildren().add(dateNumber);
            calendarDate = calendarDate.plusDays(1);
        }

        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Provides layout for the calendar month with anchor panes.
     */
    private void calendarMonthSetup(GridPane calendar) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.getStyleClass().add("anchor");
                ap.setPrefSize(100, 100);
                calendar.add(ap, j, i);
                calendarMonth.add(ap);
            }
        }
    }

    // to look at the previous month
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth, null);
    }

    // to look at the next month
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth, null);
    }

    // to jump back to the current year and month easily
    private void originalYearMonth() {
        currentYearMonth = defaultYearMonth;
        populateCalendar(currentYearMonth, null);
    }

    public YearMonth getCurrentYearMonth() {
        return currentYearMonth;
    }

    public void setCurrentYearMonth(YearMonth currentYearMonth) {
        this.currentYearMonth = currentYearMonth;
    }

    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return calendarMonth;
    }

    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.calendarMonth = allCalendarDays;
    }

    /**
     * Check whether the event Day matches the input dayValue
     * @param event
     * @param dayValue
     * @return
     */
    private boolean checkEventDay(ReadOnlyEvent event, String dayValue) {
        if (dayValue.length() == 1) {
            return event.getDatetime().value.substring(8, 10).equals("0" + dayValue);
        } else {
            return event.getDatetime().value.substring(8, 10).equals(dayValue);
        }
    }

    /**
     * Check whether the event Day matches the input monthValue
     * @param event
     * @param monthValue
     * @return
     */
    private boolean checkEventMonth(ReadOnlyEvent event, String monthValue) {
        if (monthValue.length() == 1) {
            return event.getDatetime().value.substring(5, 7).equals("0" + monthValue);
        } else {
            return event.getDatetime().value.substring(5, 7).equals(monthValue);
        }
    }

    /**
     * Check whether the event Day matches the input yearValue
     * @param event
     * @param yearValue
     * @return
     */
    private boolean checkEventYear(ReadOnlyEvent event, String yearValue) {
        return event.getDatetime().value.substring(0, 4).equals(yearValue);
    }

    private String getFormatDate(String year, String month, String day) {
        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month.length() == 1) {
            month = "0" + month;
        }
        return year + "-" + month + "-" + day;
    }

    @Subscribe
    private void handleJumpToCalendarRequestEvent(JumpToCalendarRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        System.out.println("test");
        setCurrentYearMonth(event.getYearMonth());
        populateCalendar(event.getYearMonth(), null);
    }

}
```
###### \java\seedu\address\ui\CalendarViewPanel.java
``` java

/**
 * Panel containing the calendar.
 */
public class CalendarViewPanel extends UiPart<Region> {
    private static final String FXML = "CalendarView.fxml";

    @FXML
    private Pane calendarPanel;

    private CalendarView calendarView;
    private Logic logic;

    public CalendarViewPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        setConnections();
    }

    private void setConnections() {
        calendarView = new CalendarView(logic, logic.getFilteredEventList(), YearMonth.now());
        calendarPanel.getChildren().add(calendarView.getView());
    }

    public CalendarView getCalendarPane() {
        return calendarView;
    }
}
```
###### \java\seedu\address\ui\CalendarViewUpdate.java
``` java

/**
 * Methods to update Calendar View
 */
public class CalendarViewUpdate {

    /**
     * Updates view state of Calendar UI
     * for methods like add, delete, edit, etc.
     *
     * @param calendarView
     */
    public static void updateViewState(CalendarView calendarView) {
        calendarView.setCurrentYearMonth(YearMonth.now());
        calendarView.populateCalendar(calendarView.getCurrentYearMonth(), null);
    }

    /**
     * Updates view state of Calendar UI
     * for the Find Event Command.
     *
     * @param calendarView
     * @param model
     */
    public static void updateFindState(CalendarView calendarView, Model model) {
        List<ReadOnlyEvent> events = model.getFilteredEventList();
        if (events.size() != 0) {
            String findYearMonth = events.get(0).getDatetime().value.substring(0, 7);
            // If every event in the filtered list is on the same day, Calendar View jumps to that day.
            boolean changeSelectedYearMonth = events.stream()
                    .allMatch(e -> e.getDatetime().value.substring(0, 7).equals(findYearMonth));

            if (changeSelectedYearMonth) {
                calendarView.setCurrentYearMonth(YearMonth.parse(findYearMonth,
                        DateTimeFormatter.ofPattern("yyyy-MM")));
                calendarView.populateCalendar(calendarView.getCurrentYearMonth(), null);
            }
        }
    }
}
```
###### \java\seedu\address\ui\EventCard.java
``` java

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyEvent event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label eventLocation;
    @FXML
    private Label datetime;
    @FXML
    private HBox eventLabelPane;
    @FXML
    private Label eventLabel;
    @FXML
    private ImageView eventImage;

    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ". ");
        this.event = event;
        bindListeners(event);
        setEventStatusStyle(event.getDatetime().value);
    }

    /**
     * Set the style for the event status label pane
     * @param maxWidth maximum width of the pane
     * @param colorCode color code of the border of the pane
     */
    private void setEventPaneStyle(int maxWidth, String colorCode) {
        eventLabelPane.setMaxWidth(maxWidth);
        eventLabelPane.setStyle("-fx-border-color: " + colorCode + ";");
    }

    /**
     * Set the text to be displayed at event date label
     * @param eventStatus text to be displayed
     */
    private void setEventLabelText(String eventStatus) {
        eventLabel.setText(eventStatus);
    }

    /**
     * Sets the event field style to alert users
     * (1) past events: grey color display
     * (2) happening today: red color display
     * (3) happening next 3 days: yellow color display
     * (4) beyond next 3 days: green color display
     */
    private void setEventStatusStyle(String eventDate) {
        int remainingDays = DateUtil.getDayCountBetweenTwoDates(DateUtil.getTodayDate(),
                DateUtil.getParsedDateTime(eventDate));

        if (remainingDays < 0) { // overdue tasks
            setEventPaneStyle(200, "#C5CDE2");
            setEventLabelText("  PAST EVENT");
        } else if (remainingDays == 0) {
            setEventPaneStyle(200, "#FF0000");
            setEventLabelText("  HAPPENING TODAY!");
        } else if (remainingDays < 3) {
            setEventPaneStyle(200, "#FFC000");
            setEventLabelText("  HAPPENING SOON");
        } else {
            setEventPaneStyle(200, "#00FF00");
            setEventLabelText("  NOT ANYTIME SOON");
        }
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Event} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyEvent event) {
        title.textProperty().bind(Bindings.convert(event.titleProperty()));
        description.textProperty().bind(Bindings.convert(event.descriptionProperty()));
        eventLocation.textProperty().bind(Bindings.convert(event.locationProperty()));
        datetime.textProperty().bind(Bindings.convert(event.datetimeProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }
}
```
###### \java\seedu\address\ui\EventListPanel.java
``` java

/**
 * Panel containing the list of events.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    @FXML
    private ListView<EventCard> eventListView;

    public EventListPanel(ObservableList<ReadOnlyEvent> eventList) {
        super(FXML);
        setConnections(eventList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyEvent> eventList) {
        ObservableList<EventCard> mappedList = EasyBind.map(
                eventList, (event) -> new EventCard(event, eventList.indexOf(event) + 1));
        eventListView.setItems(mappedList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new EventPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<EventCard> {

        @Override
        protected void updateItem(EventCard event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(event.getRoot());
            }
        }
    }
}
```
###### \java\seedu\address\ui\LinkedInWindow.java
``` java

/**
 * Controller for a LinkedIn page
 */
public class LinkedInWindow extends UiPart<Stage> {

    public static final String LINKEDIN_URL = "https://www.linkedin.com/";

    private static final Logger logger = LogsCenter.getLogger(LinkedInWindow.class);
    private static final String FXML = "LinkedInWindow.fxml";

    @FXML
    private WebView browser;

    /**
     * Creates a new LinkedIn page.
     *
     * @param root Stage to use as the root of the LinkedIn.
     */
    public LinkedInWindow(Stage root) {
        super(FXML, root);
        browser.getEngine().load(LINKEDIN_URL);
    }

    /**
     * Creates a new LinkedInWindow.
     */
    public LinkedInWindow() {
        this(new Stage());
    }

    /**
     * Shows the LinkedIn window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing the LinkedIn page.");
        getRoot().show();
    }
}
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    @Subscribe
    public void handleEventBookChangedEvent(EventBookChangedEvent ebce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(ebce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
    }

    @Subscribe
    public void handleTaskBookChangedEvent(TaskBookChangedEvent tbce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(tbce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
    }
}
```
###### \resources\view\BrowserPanel.fxml
``` fxml
<!-- TODO: set a more appropriate initial size -->
<fx:root type="javafx.stage.Stage" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1"
         title="GoogleSearch" maximized="true">
  <icons>
    <Image url="@/images/chrome_icon.png" />
  </icons>
  <scene>
    <Scene>
      <WebView fx:id="browser"/>
    </Scene>
  </scene>
</fx:root>
```
###### \resources\view\CalendarView.fxml
``` fxml
<Pane fx:id="calendarPanel" maxHeight="-Infinity"
      maxWidth="-Infinity" minHeight="-Infinity"
      minWidth="-Infinity" prefHeight="550.0"
      prefWidth="550.0" xmlns="http://javafx.com/javafx/8.0.60"
      xmlns:fx="http://javafx.com/fxml/1"
/>
```
###### \resources\view\DarkTheme.css
``` css
#statusPane {
    -fx-padding: 0 35 0 0;
    -fx-spacing: 2;
}

#eventLabelPane {
    -fx-padding: 0 10 0 0;
    -fx-border-width: 1.5;
    -fx-border-radius: 10 10 10 10;
    -fx-spacing: 100;
}

```
###### \resources\view\EventListCard.fxml
``` fxml
<HBox xmlns:fx="http://javafx.com/fxml/1" id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150"/>
    </columnConstraints>
    <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
      <padding>
        <Insets top="5" right="5" bottom="5" left="15"/>
      </padding>
      <HBox alignment="CENTER_LEFT">
        <Label fx:id="id" styleClass="cell_big_label">
          <minWidth>
            <!-- Ensures that the label text is never truncated -->
            <Region fx:constant="USE_PREF_SIZE"/>
          </minWidth>
        </Label>
        <Label fx:id="title" text="\$title" styleClass="cell_big_label"/>
      </HBox>
      <Label fx:id="description" styleClass="cell_small_label" text="\$description"/>
      <Label fx:id="eventLocation" styleClass="cell_small_label" text="\$eventLocation"/>
      <Label fx:id="datetime" styleClass="cell_small_label" text="\$datetime"/>
    </VBox>
    <VBox alignment="CENTER_RIGHT" minHeight="105" GridPane.columnIndex="1">
      <padding>
        <Insets top="25" right="0" bottom="0" left="0" />
      </padding>
        <HBox fx:id="eventLabelPane" alignment="CENTER_RIGHT">
          <Label fx:id="eventLabel" styleClass="cell_small_label" text="\$eventLabel" />
        </HBox>
    </VBox>
  </GridPane>
</HBox>
```
###### \resources\view\EventListPanel.fxml
``` fxml
<VBox xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/8">
  <ListView fx:id="eventListView" VBox.vgrow="ALWAYS"/>
</VBox>
```
###### \resources\view\LinkedInWindow.fxml
``` fxml
<!-- TODO: set a more appropriate initial size -->
<fx:root type="javafx.stage.Stage" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1"
         title="LinkedIn" maximized="true">
  <icons>
    <Image url="@/images/linkedin_icon.png" />
  </icons>
  <scene>
    <Scene>
      <WebView fx:id="browser"/>
    </Scene>
  </scene>
</fx:root>
```
###### \resources\view\MainWindow.fxml
``` fxml
          <VBox fx:id="eventTaskView" minWidth="380" prefWidth="380" SplitPane.resizableWithParent="true">
            <padding>
              <Insets top="10" right="10" bottom="10" left="10"/>
            </padding>
            <TabPane fx:id="tabPane" VBox.vgrow="ALWAYS" tabClosingPolicy="UNAVAILABLE">
              <tabs>
                <Tab fx:id="eventTab" text="Events">
                  <StackPane fx:id="eventListPanelPlaceholder" VBox.vgrow="ALWAYS"/>
                </Tab>
                <Tab fx:id="taskTab" text="Tasks">
                  <StackPane fx:id="taskListPanelPlaceholder" VBox.vgrow="ALWAYS"/>
                </Tab>
              </tabs>
            </TabPane>
          </VBox>
        </SplitPane>

        <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
      </VBox>
    </Scene>
  </scene>
</fx:root>
```
