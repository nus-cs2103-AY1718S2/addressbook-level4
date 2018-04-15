package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showCoinAtIndex;
import static seedu.address.testutil.TypicalCoins.getTypicalCoinBook;
import static seedu.address.testutil.TypicalTargets.INDEX_FIRST_COIN;
import static seedu.address.testutil.TypicalTargets.INDEX_SECOND_COIN;
import static seedu.address.testutil.TypicalTargets.INDEX_THIRD_COIN;
import static seedu.address.testutil.TypicalTargets.TARGET_FIRST_COIN;
import static seedu.address.testutil.TypicalTargets.TARGET_SECOND_COIN;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewCommand}.
 */
public class ViewCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCoinBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastCoinIndex = Index.fromOneBased(model.getFilteredCoinList().size());

        assertExecutionSuccess(INDEX_FIRST_COIN);
        assertExecutionSuccess(INDEX_THIRD_COIN);
        assertExecutionSuccess(lastCoinIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredCoinList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_COMMAND_TARGET);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCoinAtIndex(model, INDEX_FIRST_COIN);

        assertExecutionSuccess(INDEX_FIRST_COIN);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showCoinAtIndex(model, INDEX_FIRST_COIN);

        Index outOfBoundsIndex = INDEX_SECOND_COIN;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getCoinBook().getCoinList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_COMMAND_TARGET);
    }

    @Test
    public void equals() {
        ViewCommand selectFirstCommand = new ViewCommand(TARGET_FIRST_COIN);
        ViewCommand selectSecondCommand = new ViewCommand(TARGET_SECOND_COIN);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        ViewCommand selectFirstCommandCopy = new ViewCommand(TARGET_FIRST_COIN);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different coin -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code ViewCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        ViewCommand viewCommand = prepareCommand(index);

        try {
            CommandResult commandResult = viewCommand.execute();
            assertEquals(String.format(ViewCommand.MESSAGE_SELECT_COIN_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code ViewCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        ViewCommand viewCommand = prepareCommand(index);

        try {
            viewCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code ViewCommand} with parameters {@code index}.
     */
    private ViewCommand prepareCommand(Index index) {
        ViewCommand viewCommand = new ViewCommand(new CommandTarget(index));
        viewCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return viewCommand;
    }
}
