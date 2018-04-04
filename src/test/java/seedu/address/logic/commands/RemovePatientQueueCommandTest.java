//@@author Kai Yong
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;
import seedu.address.testutil.TypicalPatients;

public class RemovePatientQueueCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_emptyQueue_throwsCommandException() throws CommandException {
        RemovePatientQueueCommand removeEmptyQueueCommand = prepareEmptyQueueCommand();
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemovePatientQueueCommand.MESSAGE_PERSON_NOT_FOUND_QUEUE);
        removeEmptyQueueCommand.execute();
    }

    @Test
    public void execute_patientExist_removeSuccessful() throws CommandException, DuplicatePatientException,
            PatientNotFoundException {
        RemovePatientQueueCommand command = prepareCommand();
        CommandResult commandResult = command.execute();
        assertEquals(String.format(RemovePatientQueueCommand.MESSAGE_REMOVE_SUCCESS,
                TypicalPatients.FIONA.getName().toString()), commandResult.feedbackToUser);
    }

    private RemovePatientQueueCommand prepareEmptyQueueCommand() {
        RemovePatientQueueCommand command = new RemovePatientQueueCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
    /**
     * Parses {@code userInput} into a {@code RemovePatientQueueCommand}.
     */
    private RemovePatientQueueCommand prepareCommand() throws DuplicatePatientException, PatientNotFoundException {
        model.addPatientToQueue(new NameContainsKeywordsPredicate(Arrays.asList("Fiona")));
        RemovePatientQueueCommand command = new RemovePatientQueueCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    @Test
    public void equals() throws Exception {
        RemovePatientQueueCommand removePatientQueueFirstCommand = new RemovePatientQueueCommand();

        //same object -> returns true
        assertTrue(removePatientQueueFirstCommand.equals(removePatientQueueFirstCommand));

        //different types -> return false
        assertFalse(removePatientQueueFirstCommand.equals(1));

        //null -> returns false
        assertFalse(removePatientQueueFirstCommand.equals(null));
    }
}
