package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowWeightLogEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author wenhao53-unused
// Feature is working but was unable to solve the many tests that were failing.
public class WeightLogCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        WeightLogCommand weightLogCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(weightLogCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final WeightLogCommand standardCommand = new WeightLogCommand(INDEX_FIRST_PERSON);
        final WeightLogCommand commandWithSameIndex = new WeightLogCommand(INDEX_FIRST_PERSON);
        final WeightLogCommand commandWithDifferentIndex = new WeightLogCommand(INDEX_SECOND_PERSON);


        // same object -> Returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different object, same type with same values -> return true
        assertTrue(standardCommand.equals(commandWithSameIndex));

        // same type but different index -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void checkIfEventCollected() throws CommandException {
        WeightLogCommand weightLogCommand = prepareCommand(INDEX_FIRST_PERSON);
        weightLogCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowWeightLogEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    /**
     * Executes a {@code WeightLogCommand} with the given {@code index}, and checks that {@code ShowWeightLogEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        WeightLogCommand weightLogCommand = prepareCommand(index);

        try {
            CommandResult commandResult = weightLogCommand.execute();
            assertEquals(String.format(WeightLogCommand.MESSAGE_WEIGHT_LOG_SUCCESS,
                    model.getFilteredPersonList().get(index.getZeroBased()).getName().fullName,
                    model.getFilteredPersonList().get(index.getZeroBased()).getAddress().value),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ShowWeightLogEvent lastEvent = (ShowWeightLogEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(model.getFilteredPersonList().get(index.getZeroBased()), lastEvent.person);
    }

    /**
     * Returns an {@code WeightLogCommand} with parameters {@code index}}
     */
    private WeightLogCommand prepareCommand(Index index) {
        WeightLogCommand weightLogCommand = new WeightLogCommand(index);
        weightLogCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return weightLogCommand;
    }
}
