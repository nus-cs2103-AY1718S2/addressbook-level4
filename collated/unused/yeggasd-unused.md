# yeggasd-unused
###### \TimeTableCommand.java
``` java
/**
 * Retrieves the timetable of a person identified using it's last displayed index from the address book.
 */
public class TimeTableCommand extends Command {
    public static final String COMMAND_WORD = "timetable";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds timetable of a person identified  \n"
            + "Parameters: INDEX (must be a positive integer) ODD/EVEN\n"
            + "Example: " + COMMAND_WORD + " 1 Odd";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "%1$s Week Timetable of selected Person: %2$s";

    private final Index targetIndex;
    private final String oddEven;
    private Person personToShow;
    /**
     * Creates a Timetable to retrieve the timetable of the given index
     */
    public TimeTableCommand(Index targetIndex, String oddEven) {
        requireNonNull(targetIndex);
        requireNonNull(oddEven);
        this.targetIndex = targetIndex;
        this.oddEven = oddEven;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        preprocess();

        Timetable timeTable = personToShow.getTimetable();
        int oddEvenIndex = StringUtil.getOddEven(oddEven);
        ArrayList<ArrayList<String>> personTimeTable = timeTable.getTimetable().get(oddEvenIndex);
        ObservableList<ArrayList<String>> timeTableList = FXCollections.observableArrayList(personTimeTable);
        EventsCenter.getInstance().post(new TimeTableEvent(timeTableList));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, StringUtil.capitalize(oddEven),
                personToShow.getName()));
    }

    /**
     * Preprocess the required data for execution.
     * @throws CommandException when index out of bound
     */
    protected void preprocess() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        personToShow = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeTableCommand // instanceof handles nulls
                && this.targetIndex.equals(((TimeTableCommand) other).targetIndex) // state check
                && this.oddEven.equalsIgnoreCase(((TimeTableCommand) other).oddEven)
                && Objects.equals(this.personToShow, ((TimeTableCommand) other).personToShow));

    }

    @Override
    public String toString() {
        return targetIndex.toString() + " " + oddEven + " " + personToShow;
    }
}
```
###### \TimeTableCommandParser.java
``` java
/**
 * Parses input arguments and creates a new TimeTableCommand object
 */
public class TimeTableCommandParser implements Parser<TimeTableCommand> {
    private static final String SPLIT_TOKEN = " ";
    private static final int PERSON_INDEX = 0;
    private static final int ODD_EVEN_INDEX = 1;
    /**
     * Parses the given {@code String} of arguments in the context of the TimeTableCommand
     * and returns an TimeTableCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TimeTableCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            String[] splitArgs = trimmedArgs.split(SPLIT_TOKEN);
            if (splitArgs.length != 2) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimeTableCommand.MESSAGE_USAGE));
            }
            Index index = ParserUtil.parseIndex(splitArgs[PERSON_INDEX]);
            String oddEven = ParserUtil.parseOddEven(splitArgs[ODD_EVEN_INDEX]);
            return new TimeTableCommand(index, oddEven);

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimeTableCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \TimeTableCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the TimeTableCommand code. For example, inputs "1 odd" and "1 abc" take the
 * same path through the TimeTableCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class TimeTableCommandParserTest {

    private TimeTableCommandParser parser = new TimeTableCommandParser();

    @Test
    public void parse_validArgs_returnsTimeTableCommand() {
        assertParseSuccess(parser, "1 Odd", new TimeTableCommand(INDEX_FIRST_PERSON, ODD));
    }

    @Test
    public void parse_invalidNumArgs_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimeTableCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1odd", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TimeTableCommand.MESSAGE_USAGE));
    }
}
```
###### \TimeTableCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code TimeTableCommand}.
 */
public class TimeTableCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON, ODD);
        assertExecutionSuccess(INDEX_THIRD_PERSON, ODD);
        assertExecutionSuccess(lastPersonIndex, ODD);
        assertExecutionSuccess(INDEX_FIRST_PERSON, EVEN);
        assertExecutionSuccess(INDEX_THIRD_PERSON, EVEN);
        assertExecutionSuccess(lastPersonIndex, EVEN);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, EVEN, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        assertExecutionSuccess(INDEX_FIRST_PERSON, EVEN);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, EVEN, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        TimeTableCommand timeTableFirstCommand = new TimeTableCommand(INDEX_FIRST_PERSON, EVEN);
        TimeTableCommand timeTableSecondCommand = new TimeTableCommand(INDEX_SECOND_PERSON, EVEN);
        TimeTableCommand timeTableThirdCommand = new TimeTableCommand(INDEX_FIRST_PERSON, ODD);

        // same object -> returns true
        assertTrue(timeTableFirstCommand.equals(timeTableFirstCommand));

        // same values -> returns true
        TimeTableCommand timeTableFirstCommandCopy = new TimeTableCommand(INDEX_FIRST_PERSON, EVEN);
        assertTrue(timeTableFirstCommand.equals(timeTableFirstCommandCopy));

        // different types -> returns false
        assertFalse(timeTableFirstCommand.equals(1));

        // null -> returns false
        assertFalse(timeTableFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(timeTableFirstCommand.equals(timeTableSecondCommand));

        // different OddEven -> returns false
        assertFalse(timeTableFirstCommand.equals(timeTableThirdCommand));
    }

    /**
     * Executes a {@code TimeTableCommand} with the given {@code index}  and {@code oddEvem},
     * and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index and oddEven.
     */
    private void assertExecutionSuccess(Index index, String oddEven) {
        TimeTableCommand timeTableCommand = prepareCommand(index, oddEven);
        Person target = model.getFilteredPersonList().get(index.getZeroBased());
        ArrayList<ArrayList<ArrayList<String>>> targetList = target.getTimetable().getTimetable();
        int oddEvenIndex = StringUtil.getOddEven(oddEven);
        try {
            CommandResult commandResult = timeTableCommand.execute();
            assertEquals(String.format(TimeTableCommand.MESSAGE_SELECT_PERSON_SUCCESS, oddEven, target.getName()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        TimeTableEvent lastEvent = (TimeTableEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(targetList.get(oddEvenIndex), lastEvent.getTimeTable());
    }

    /**
     * Executes a {@code TimeTableCommand} with the given {@code index} and {@code oddEvem},
     * and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String oddEven, String expectedMessage) {
        TimeTableCommand timeTableCommand = prepareCommand(index, oddEven);

        try {
            timeTableCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectCommand} with parameters {@code index}.
     */
    private TimeTableCommand prepareCommand(Index index, String oddEven) {
        TimeTableCommand timeTableCommand = new TimeTableCommand(index, oddEven);
        timeTableCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return timeTableCommand;
    }
}
```
