package seedu.flashy.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.flashy.logic.commands.CommandTestUtil.showTagAtIndex;
import static seedu.flashy.testutil.TypicalCardBank.getTypicalCardBank;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_FIRST_TAG;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_SECOND_TAG;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_THIRD_TAG;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.flashy.commons.core.Messages;
import seedu.flashy.commons.core.index.Index;
import seedu.flashy.commons.events.ui.JumpToTagRequestEvent;
import seedu.flashy.logic.CommandHistory;
import seedu.flashy.logic.UndoRedoStack;
import seedu.flashy.logic.commands.exceptions.CommandException;
import seedu.flashy.model.Model;
import seedu.flashy.model.ModelManager;
import seedu.flashy.model.UserPrefs;
import seedu.flashy.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardBank(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastTagIndex = Index.fromOneBased(model.getFilteredTagList().size());

        assertExecutionSuccess(INDEX_FIRST_TAG);
        assertExecutionSuccess(INDEX_THIRD_TAG);
        assertExecutionSuccess(lastTagIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTagList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTagAtIndex(model, INDEX_FIRST_TAG);

        assertExecutionSuccess(INDEX_FIRST_TAG);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showTagAtIndex(model, INDEX_FIRST_TAG);

        Index outOfBoundsIndex = INDEX_SECOND_TAG;
        // ensures that outOfBoundIndex is still in bounds of flashy book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getCardBank().getTagList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_TAG);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_TAG);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_TAG);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different tag -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToTagRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = prepareCommand(index);

        try {
            CommandResult commandResult = selectCommand.execute();
            assertEquals(String.format(SelectCommand.MESSAGE_SELECT_TAG_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToTagRequestEvent lastEvent = (JumpToTagRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = prepareCommand(index);

        try {
            selectCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectCommand} with parameters {@code index}.
     */
    private SelectCommand prepareCommand(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        selectCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return selectCommand;
    }
}
