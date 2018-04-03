# ncaminh
###### \java\seedu\address\logic\commands\DistanceCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code DistanceCommand}.
 */
public class DistanceCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Index lastPersonIndex = Index.fromOneBased(lastShownList.size());

        Person firstPerson = lastShownList.get(0);
        Person secondPerson = lastShownList.get(1);
        Person lastPerson = lastShownList.get(lastPersonIndex.getZeroBased());

        assertOnePersonExecutionSuccess(firstPerson, INDEX_FIRST_PERSON);
        assertOnePersonExecutionSuccess(secondPerson, INDEX_SECOND_PERSON);
        assertOnePersonExecutionSuccess(lastPerson, lastPersonIndex);

        assertTwoPersonExecutionSuccess(firstPerson, INDEX_FIRST_PERSON, secondPerson, INDEX_SECOND_PERSON);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        List<Person> lastShownList = model.getFilteredPersonList();

        assertOnePersonExecutionSuccess(lastShownList.get(0), INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_PERSON);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_PERSON);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code DistanceCommand} with the given {@code person and index}
     */
    private void assertOnePersonExecutionSuccess(Person person, Index index) {
        DistanceCommand distanceCommand = prepareOnePersonCommand(index);

        try {
            CommandResult commandResult = distanceCommand.execute();
            String address = person.getAddress().toString();
            String headQuarterAddress = "Kent Ridge MRT";
            GetDistance route = new GetDistance();
            Double distance = route.getDistance(headQuarterAddress, address);

            JumpToListRequestEvent lastEvent =
                    (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
            assertEquals(String.format(DistanceCommand.MESSAGE_DISTANCE_FROM_HQ_SUCCESS, distance),
                    commandResult.feedbackToUser);
        } catch (Exception ce) {
            System.out.println(ce.getMessage());
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes a {@code DistanceCommand} with the given {@code persons and indexes}
     */
    private void assertTwoPersonExecutionSuccess(Person personAtOrigin, Index originIndex,
                                                 Person personAtDestination, Index destinationIndex) {
        DistanceCommand distanceCommand = prepareTwoPersonsCommand(originIndex, destinationIndex);

        try {
            CommandResult commandResult = distanceCommand.execute();
            String addressOrigin = personAtOrigin.getAddress().toString();
            String addressDestination = personAtDestination.getAddress().toString();
            String nameOrigin = personAtOrigin.getName().fullName;
            String nameDestination = personAtDestination.getName().fullName;
            GetDistance route = new GetDistance();
            Double distance = route.getDistance(addressOrigin, addressDestination);
            List<String> addressesList = new ArrayList<>();
            addressesList.add(addressOrigin);
            addressesList.add(addressDestination);

            ShowRouteFromOneToAnotherEvent lastEvent =
                    (ShowRouteFromOneToAnotherEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(addressesList, lastEvent.sortedList);
            assertEquals(String.format(DistanceCommand.MESSAGE_DISTANCE_FROM_PERSON_SUCCESS,
                    nameOrigin, nameDestination, distance),
                    commandResult.feedbackToUser);
        } catch (Exception ce) {
            System.out.println(ce.getMessage());
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes a {@code DistanceCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        DistanceCommand distanceCommand = prepareOnePersonCommand(index);

        try {
            distanceCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code DistanceCommand} with one parameter {@code index}.
     */
    private DistanceCommand prepareOnePersonCommand(Index index) {
        DistanceCommand distanceCommand = new DistanceCommand(index);
        distanceCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return distanceCommand;
    }

    /**
     * Returns a {@code DistanceCommand} with two parameters {@code index}.
     */
    private DistanceCommand prepareTwoPersonsCommand(Index originIndex, Index destinationIndex) {
        DistanceCommand distanceCommand = new DistanceCommand(originIndex, destinationIndex);
        distanceCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return distanceCommand;
    }
}
```
###### \java\seedu\address\logic\commands\FilterCommandTest.java
``` java
    @Test
    public void execute_noPersonMatchesDate() {
        DatePredicate datePredicate =
                new DatePredicate(Collections.singletonList("2019-03-23"));
        FilterCommand filterCommand = prepareFilterCommand(datePredicate);

        try {
            CommandResult actualCommandResult = filterCommand.execute();
            CommandResult expectedCommandResult =
                    new CommandResult(getMessageForPersonListShownSummary(0));
            assertEquals(actualCommandResult.feedbackToUser, expectedCommandResult.feedbackToUser);

        } catch (CommandException | IOException ce) {
            ce.printStackTrace();
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_allAddressesCannotBeFound() {
        DatePredicate datePredicate =
                new DatePredicate(Collections.singletonList("2018-03-25"));
        FilterCommand filterCommand = prepareFilterCommand(datePredicate);
        model.updateFilteredPersonList(datePredicate);
        int numberOfPersonsListed = model.getFilteredPersonList().size();

        try {
            CommandResult actualCommandResult = filterCommand.execute();
            String shown = getMessageForPersonListShownSummary(numberOfPersonsListed)
                    + "\nAll the addresses on "
                    + model.getFilteredPersonList().get(0).getDate().toString()
                    + " cannot be found.";
            CommandResult expectedCommandResult =
                    new CommandResult(shown);
            assertEquals(actualCommandResult.feedbackToUser, expectedCommandResult.feedbackToUser);

        } catch (CommandException | IOException ce) {
            ce.printStackTrace();
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_someAddressesCannotBeFound() {
        DatePredicate datePredicate =
                new DatePredicate(Collections.singletonList("2018-03-23"));
        FilterCommand filterCommand = prepareFilterCommand(datePredicate);
        model.updateFilteredPersonList(datePredicate);
        int numberOfPersonsListed = model.getFilteredPersonList().size();

        try {
            CommandResult actualCommandResult = filterCommand.execute();
            String shown = getMessageForPersonListShownSummary(numberOfPersonsListed)
                    + "\nAt least one address on "
                    + model.getFilteredPersonList().get(0).getDate().toString()
                    + " cannot be found.";
            CommandResult expectedCommandResult =
                    new CommandResult(shown);
            assertEquals(actualCommandResult.feedbackToUser, expectedCommandResult.feedbackToUser);

        } catch (CommandException | IOException ce) {
            ce.printStackTrace();
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_allAddressesCanBeFound() {
        DatePredicate datePredicate =
                new DatePredicate(Collections.singletonList("2018-03-28"));
        FilterCommand filterCommand = prepareFilterCommand(datePredicate);
        model.updateFilteredPersonList(datePredicate);
        int numberOfPersonsListed = model.getFilteredPersonList().size();

        try {
            CommandResult actualCommandResult = filterCommand.execute();
            CommandResult expectedCommandResult =
                    new CommandResult(getMessageForPersonListShownSummary(numberOfPersonsListed));
            assertEquals(actualCommandResult.feedbackToUser, expectedCommandResult.feedbackToUser);

        } catch (CommandException | IOException ce) {
            ce.printStackTrace();
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    private FilterCommand prepareFilterCommand(DatePredicate datePredicate) {
        FilterCommand filterCommand = new FilterCommand(datePredicate);
        filterCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return filterCommand;
    }
}
```
###### \java\seedu\address\logic\parser\DistanceCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class DistanceCommandParserTest {

    private DistanceCommandParser parser = new DistanceCommandParser();

    @Test
    public void parse_validArgs_returnsDistanceCommand() {
        assertParseSuccess(parser, "1", new DistanceCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DistanceCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\ui\BrowserPanelTest.java
``` java
        String addressValue = ALICE.getAddress().value;
        String addressWithoutUnit = addressValue.substring(0, addressValue.indexOf('#') - 2);
        URL expectedPersonUrl = new URL(BrowserPanel.SEARCH_PAGE_URL
                + addressWithoutUnit.replaceAll(" ", "%20") + "?dg=dbrw&newdg=1");
```
