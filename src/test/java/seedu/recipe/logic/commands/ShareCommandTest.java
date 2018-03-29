package seedu.recipe.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.recipe.logic.commands.CommandTestUtil.showRecipeAtIndex;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_THIRD_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.commons.events.ui.ShareRecipeEvent;
import seedu.recipe.logic.CommandHistory;
import seedu.recipe.logic.UndoRedoStack;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ShareCommand}.
 */
public class ShareCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastRecipeIndex = Index.fromOneBased(model.getFilteredRecipeList().size());

        assertExecutionSuccess(INDEX_FIRST_RECIPE);
        assertExecutionSuccess(INDEX_THIRD_RECIPE);
        assertExecutionSuccess(lastRecipeIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        assertExecutionSuccess(INDEX_FIRST_RECIPE);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        Index outOfBoundsIndex = INDEX_SECOND_RECIPE;
        // ensures that outOfBoundIndex is still in bounds of recipe book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getRecipeBook().getRecipeList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ShareCommand shareFirstCommand = new ShareCommand(INDEX_FIRST_RECIPE);
        ShareCommand shareSecondCommand = new ShareCommand(INDEX_SECOND_RECIPE);

        // same object -> returns true
        assertTrue(shareFirstCommand.equals(shareFirstCommand));

        // same values -> returns true
        ShareCommand shareFirstCommandCopy = new ShareCommand(INDEX_FIRST_RECIPE);
        assertTrue(shareFirstCommand.equals(shareFirstCommandCopy));

        // different types -> returns false
        assertFalse(shareFirstCommand.equals(1));

        // null -> returns false
        assertFalse(shareFirstCommand.equals(null));

        // different recipe -> returns false
        assertFalse(shareFirstCommand.equals(shareSecondCommand));
    }

    /**
     * Executes a {@code ShareCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        ShareCommand shareCommand = prepareCommand(index);

        try {
            CommandResult commandResult = shareCommand.execute();
            assertEquals(String.format(ShareCommand.MESSAGE_SHARE_RECIPE_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ShareRecipeEvent lastEvent = (ShareRecipeEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code ShareCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        ShareCommand shareCommand = prepareCommand(index);

        try {
            shareCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code ShareCommand} with parameters {@code index}.
     */
    private ShareCommand prepareCommand(Index index) {
        ShareCommand shareCommand = new ShareCommand(index);
        shareCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return shareCommand;
    }
}
