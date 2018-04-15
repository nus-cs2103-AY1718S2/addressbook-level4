package seedu.flashy.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.flashy.testutil.TypicalCardBank.getTypicalCardBank;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_THIRD_CARD;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.flashy.commons.core.Messages;
import seedu.flashy.commons.core.index.Index;
import seedu.flashy.commons.events.ui.JumpToCardRequestEvent;
import seedu.flashy.logic.CommandHistory;
import seedu.flashy.logic.UndoRedoStack;
import seedu.flashy.logic.commands.exceptions.CommandException;
import seedu.flashy.model.Model;
import seedu.flashy.model.ModelManager;
import seedu.flashy.model.UserPrefs;
import seedu.flashy.model.tag.Name;
import seedu.flashy.model.tag.Tag;
import seedu.flashy.ui.testutil.EventsCollectorRule;

//@@author yong-jie
public class SelectCardCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardBank(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastCardIndex = Index.fromOneBased(model.getFilteredCardList().size());

        assertExecutionSuccess(INDEX_FIRST_CARD);
        assertExecutionSuccess(INDEX_THIRD_CARD);
        assertExecutionSuccess(lastCardIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        assertExecutionSuccess(INDEX_FIRST_CARD);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        model.filterCardsByTag(new Tag(new Name("Hi")));

        Index outOfBoundsIndex = INDEX_SECOND_CARD;
        // ensures that outOfBoundIndex is still in bounds of the list of cards
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getCardBank().getCardList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_CARD);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_CARD);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_CARD);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different card -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCardCommand} with the given {@code index}, and checks that {@code JumpToCardRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCardCommand selectCardCommand = prepareCommand(index);

        try {
            CommandResult commandResult = selectCardCommand.execute();
            assertEquals(String.format(SelectCardCommand.MESSAGE_SELECT_CARD_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToCardRequestEvent lastEvent = (JumpToCardRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCardCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCardCommand selectCardCommand = prepareCommand(index);

        try {
            selectCardCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectCardCommand} with parameters {@code index}.
     */
    private SelectCardCommand prepareCommand(Index index) {
        SelectCardCommand selectCardCommand = new SelectCardCommand(index);
        selectCardCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return selectCardCommand;
    }
}
