# meerakanani10
###### \java\seedu\address\logic\commands\FilterCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FilterCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        DatePredicate firstPredicate =
                new DatePredicate(Collections.singletonList("2018-03-23"));
        DatePredicate secondPredicate =
                new DatePredicate(Collections.singletonList("2018-03-24"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

```
###### \java\seedu\address\logic\parser\FilterCommandParserTest.java
``` java
/**
 * Filter Command Parser Tests
 */
public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new DatePredicate(Arrays.asList("2018-03-23", "2018-03-24")));
        assertParseSuccess(parser, "2018-03-23 2018-03-24", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 2018-03-23 \n \t 2018-03-24  \t", expectedFilterCommand);
    }

}
```
###### \java\seedu\address\logic\SortAddressesTest.java
``` java
/**
 * Test for the SortAddresses Class
 */
public class SortAddressesTest {

    private Map<String, Double> unsortMap = new HashMap<String, Double>();
    private Map<String, Double> sortMap = new HashMap<String, Double>();

    @Before
    public void setUp() {
        unsortMap.put("27 Prince George's Park", 3.0);
        unsortMap.put("Blk 30 Geylang Street 29", 5.0);
        unsortMap.put("Blk 436 Serangoon Gardens Street 26", 6.0);
        unsortMap.put("Blk 45 Aljunied Street 85", 2.0);

        sortMap.put("Blk 45 Aljunied Street 85", 2.0);
        sortMap.put("27 Prince George's Park", 3.0);
        sortMap.put("Blk 30 Geylang Street 29", 5.0);
        sortMap.put("Blk 436 Serangoon Gardens Street 26", 6.0);
    }

    @Test
    public void execute_sorting() {
        SortAddresses sortAddresses = new SortAddresses();
        Map<String, Double> sorted = new HashMap<>();
        sorted = sortAddresses.sortByComparator(unsortMap);
        sortAddresses.printMap(sorted);

    }

    /**
     *
     * @param sorted
     */
    public void assertCorrectlySorted(Map<String, Double> sorted) {
        Assert.assertTrue(sorted.equals(sortMap));
    }

}
```
###### \java\systemtests\FilterCommandSystemTest.java
``` java
public class FilterCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void filter() {
        /* Case: find multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FilterCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();


        /* Case: filter person where person list is not displaying the person we are finding -> 1 person found */
        command = FilterCommand.COMMAND_WORD + " 2018-03-29";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);

        /* Case: filter multiple persons in address book, 1 keywords -> 2 persons found */
        command = FilterCommand.COMMAND_WORD + " 2018-03-24";
        ModelHelper.setFilteredList(expectedModel, FIONA, ELLE);
        assertCommandSuccess(command, expectedModel);

        /* Case: filter multiple persons in address book 1 non-matching keyword
         * -> 0 persons found
         */
        command = FilterCommand.COMMAND_WORD + " 2017-03-23";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous filter command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous filter command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: filter person in address book, keyword is substring of date -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " 2018";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, date is substring of keyword -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " 03-23";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
