//@@author Kyholmes-test
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
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
        thrown.expectMessage(RemovePatientQueueCommand.MESSAGE_QUEUE_EMPTY);
        removeEmptyQueueCommand.execute();
    }

    @Test
    public void execute_patientExist_removeSuccessful() throws CommandException, IllegalValueException,
            PatientNotFoundException {
        RemovePatientQueueCommand command = prepareCommand();
        CommandResult commandResult = command.execute();
        assertEquals(String.format(RemovePatientQueueCommand.MESSAGE_REMOVE_SUCCESS,
                TypicalPatients.ALICE.getName().fullName), commandResult.feedbackToUser);
    }

    @Test
    public void execute_patientExist_removeByIndexSuccessful() throws CommandException,
            IllegalValueException, PatientNotFoundException {
        RemovePatientQueueCommand command = prepareCommandMorePatient("2");
        CommandResult commandResult = command.execute();
        assertEquals(String.format(RemovePatientQueueCommand.MESSAGE_REMOVE_SUCCESS,
                TypicalPatients.BENSON.getName().fullName), commandResult.feedbackToUser);
    }

    @Test
    public void execute_emptyQueueRemoveByIndex_throwsCommandException() throws IllegalValueException, CommandException {
        RemovePatientQueueCommand command = prepareCommandEmptyQueueIndex("2");
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemovePatientQueueCommand.MESSAGE_QUEUE_EMPTY);
        command.execute();
    }

    @Test
    public void execute_patientNotExistInQueue_throwsCommandException() throws CommandException,
            IllegalValueException, PatientNotFoundException {
        RemovePatientQueueCommand command = prepareCommandMorePatientNotExist("2");
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemovePatientQueueCommand.MESSAGE_PERSON_NOT_FOUND_QUEUE);
        command.execute();
    }

    private RemovePatientQueueCommand prepareEmptyQueueCommand() {
        RemovePatientQueueCommand command = new RemovePatientQueueCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    private RemovePatientQueueCommand prepareCommandEmptyQueueIndex(String userInput) throws IllegalValueException {
        RemovePatientQueueCommand command = new RemovePatientQueueCommand(ParserUtil.parseIndex(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code RemovePatientQueueCommand}.
     */
    private RemovePatientQueueCommand prepareCommand() throws IllegalValueException, PatientNotFoundException {
        model.addPatientToQueue(ParserUtil.parseIndex("1"));
        RemovePatientQueueCommand command = new RemovePatientQueueCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code RemovePatientQueueCommand}.
     */
    private RemovePatientQueueCommand prepareCommandMorePatient(String userInput) throws IllegalValueException,
            PatientNotFoundException {
        model.addPatientToQueue(ParserUtil.parseIndex("1"));
        model.addPatientToQueue(ParserUtil.parseIndex("2"));
        model.addPatientToQueue(ParserUtil.parseIndex("3"));
        RemovePatientQueueCommand command = new RemovePatientQueueCommand(ParserUtil.parseIndex(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code RemovePatientQueueCommand}.
     */
    private RemovePatientQueueCommand prepareCommandMorePatientNotExist(String userInput) throws IllegalValueException,
            PatientNotFoundException {
        model.addPatientToQueue(ParserUtil.parseIndex("1"));
        model.addPatientToQueue(ParserUtil.parseIndex("3"));
        RemovePatientQueueCommand command = new RemovePatientQueueCommand(ParserUtil.parseIndex(userInput));
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
