package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON;
import static seedu.address.testutil.TypicalJournalEntries.getTypicalJournal;
import static seedu.address.testutil.TypicalPersons.getTypicalPerson;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowTimetableRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author marlenekoh
/**
 * Contains integration tests (interaction with the Model) for {@code ShowTimetableCommand}.
 */
public class ShowTimetableCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model withPartnerModel;
    private Model noPartnerModel;

    @Before
    public void setUp() {
        withPartnerModel = new ModelManager(getTypicalPerson(), getTypicalJournal(), new UserPrefs());
        noPartnerModel = new ModelManager(null, getTypicalJournal(), new UserPrefs());
    }

    @Test
    public void equals() {
        ShowTimetableCommand showTimetableCommand = new ShowTimetableCommand();

        // different types -> returns false
        assertFalse(showTimetableCommand.equals(1));

        // null -> returns false
        assertFalse(showTimetableCommand.equals(null));
    }

    @Test
    public void execute_success() throws IllegalArgumentException {
        ShowTimetableCommand showTimetableCommand = prepareCommand(withPartnerModel);

        try {
            CommandResult commandResult = showTimetableCommand.execute();
            assertEquals(ShowTimetableCommand.MESSAGE_SELECT_PERSON_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowTimetableRequestEvent);
    }

    @Test
    public void execute_failure() throws IllegalArgumentException {
        ShowTimetableCommand showTimetableCommand = prepareCommand(noPartnerModel);

        try {
            showTimetableCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(MESSAGE_INVALID_PERSON, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code ShowTimetableCommand} with new CommandHistory and new UndoRedoStack.
     */
    private ShowTimetableCommand prepareCommand(Model myModel) {
        ShowTimetableCommand showTimetableCommand = new ShowTimetableCommand();
        showTimetableCommand.setData(myModel, new CommandHistory(), new UndoRedoStack());
        return showTimetableCommand;
    }
}
