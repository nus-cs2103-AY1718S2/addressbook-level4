package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalOddEven.EVEN_INDEX;
import static seedu.address.testutil.TypicalOddEven.ODD_INDEX;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.TimeTableEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author yeggasd
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

        assertExecutionSuccess(INDEX_FIRST_PERSON, ODD_INDEX);
        assertExecutionSuccess(INDEX_THIRD_PERSON, ODD_INDEX);
        assertExecutionSuccess(lastPersonIndex, ODD_INDEX);
        assertExecutionSuccess(INDEX_FIRST_PERSON, EVEN_INDEX);
        assertExecutionSuccess(INDEX_THIRD_PERSON, EVEN_INDEX);
        assertExecutionSuccess(lastPersonIndex, EVEN_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, EVEN_INDEX, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        assertExecutionSuccess(INDEX_FIRST_PERSON, EVEN_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, EVEN_INDEX, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        TimeTableCommand timeTableFirstCommand = new TimeTableCommand(INDEX_FIRST_PERSON, EVEN_INDEX);
        TimeTableCommand timeTableSecondCommand = new TimeTableCommand(INDEX_SECOND_PERSON, EVEN_INDEX);
        TimeTableCommand timeTableThirdCommand = new TimeTableCommand(INDEX_FIRST_PERSON, ODD_INDEX);

        // same object -> returns true
        assertTrue(timeTableFirstCommand.equals(timeTableFirstCommand));

        // same values -> returns true
        TimeTableCommand timeTableFirstCommandCopy = new TimeTableCommand(INDEX_FIRST_PERSON, EVEN_INDEX);
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
    private void assertExecutionSuccess(Index index, int oddEven) {
        TimeTableCommand timeTableCommand = prepareCommand(index, oddEven);
        Person target = model.getFilteredPersonList().get(index.getZeroBased());
        ArrayList<ArrayList<ArrayList<String>>> targetList = target.getTimetable().getTimetable();
        try {
            CommandResult commandResult = timeTableCommand.execute();
            assertEquals(String.format(TimeTableCommand.MESSAGE_SELECT_PERSON_SUCCESS, oddEven, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        TimeTableEvent lastEvent = (TimeTableEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(targetList.get(oddEven), lastEvent.getTimeTable());
    }

    /**
     * Executes a {@code TimeTableCommand} with the given {@code index} and {@code oddEvem},
     * and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, int oddEven, String expectedMessage) {
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
    private TimeTableCommand prepareCommand(Index index, int oddEven) {
        TimeTableCommand timeTableCommand = new TimeTableCommand(index, oddEven);
        timeTableCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return timeTableCommand;
    }
}
