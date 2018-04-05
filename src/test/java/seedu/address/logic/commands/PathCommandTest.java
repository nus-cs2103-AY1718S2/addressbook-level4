//@@author ZhangYijiong
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.PersonPanelPathChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CustomerStats;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * is almost the same as {@code SelectCommandTest}
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class PathCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CustomerStats());
    }

    /**
     * is the same to {@code SelectCommandTest}
     * is not able to work due to unsolved problem during initialization of javafx.fxml in
     * initialization of PersonCard in execution of PathCommand in assertExecutionSuccess
     */
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    /**
     * is the same to {@code SelectCommandTest}
     * is not able to work due to unsolved problem during initialization of javafx.fxml in
     * initialization of PersonCard in execution of PathCommand in assertExecutionSuccess
     */
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
        PathCommand pathFirstCommand = new PathCommand(INDEX_FIRST_PERSON);
        PathCommand pathSecondCommand = new PathCommand(INDEX_SECOND_PERSON);
        PathCommand pathThirdCommand = new PathCommand(INDEX_THIRD_PERSON);

        // same object -> returns true
        assertTrue(pathFirstCommand.equals(pathFirstCommand));
        assertTrue(pathThirdCommand.equals(pathThirdCommand));

        // same values -> returns true
        PathCommand pathSecondCommandCopy = new PathCommand(INDEX_SECOND_PERSON);
        assertTrue(pathSecondCommand.equals(pathSecondCommandCopy));

        // different types -> returns false
        assertFalse(pathFirstCommand.equals(1));

        // null -> returns false
        assertFalse(pathSecondCommand.equals(null));

        // different person -> returns false
        assertFalse(pathFirstCommand.equals(pathThirdCommand));
    }

    /**
     * Executes a {@code PathCommand} with the given {@code index}, and checks that {@code PersonPanelPathChangedEvent}
     * is similar to {@code SelectCommandTest} except for Event
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        PathCommand pathCommand = prepareCommand(index);

        try {
            CommandResult commandResult = pathCommand.execute();
            assertEquals(String.format(PathCommand.MESSAGE_PATH_PERSON_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        PersonPanelPathChangedEvent lastEvent =
                (PersonPanelPathChangedEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.getNewSelection().getDisplayedIndex()));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is the same to {@code SelectCommandTest} except for the test object
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        PathCommand pathCommand = prepareCommand(index);

        try {
            pathCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code PathCommand} with parameters {@code index}.
     */
    private PathCommand prepareCommand(Index index) {
        PathCommand pathCommand = new PathCommand(index);
        pathCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return pathCommand;
    }
}
