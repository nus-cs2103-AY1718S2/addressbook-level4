//@@author Kyholmes-test
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
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
    public void execute_patientExist_removeSuccessful() throws CommandException, IllegalValueException {
        RemovePatientQueueCommand command = prepareCommand();
        CommandResult commandResult = command.execute();
        assertEquals(String.format(RemovePatientQueueCommand.MESSAGE_REMOVE_SUCCESS,
                TypicalPatients.ALICE.getName().fullName), commandResult.feedbackToUser);
    }

    @Test
    public void execute_patientExist_removeByIndexSuccessful() throws CommandException,
            IllegalValueException {
        RemovePatientQueueCommand command = prepareCommandMorePatient(INDEX_SECOND_PERSON.getOneBased() + "");
        CommandResult commandResult = command.execute();
        assertEquals(String.format(RemovePatientQueueCommand.MESSAGE_REMOVE_SUCCESS,
                TypicalPatients.BENSON.getName().fullName), commandResult.feedbackToUser);
    }

    @Test
    public void execute_emptyQueueRemoveByIndex_throwsCommandException() throws IllegalValueException,
            CommandException {
        RemovePatientQueueCommand command = prepareCommandEmptyQueueIndex(INDEX_SECOND_PERSON.getOneBased()
                + "");
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemovePatientQueueCommand.MESSAGE_QUEUE_EMPTY);
        command.execute();
    }

    @Test
    public void execute_patientNotExistInQueue_throwsCommandException() throws CommandException,
            IllegalValueException {
        RemovePatientQueueCommand command = prepareCommandMorePatientNotExist(INDEX_SECOND_PERSON.getOneBased()
                + "");
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemovePatientQueueCommand.MESSAGE_PERSON_NOT_FOUND_QUEUE);
        command.execute();
    }

    @Test
    public void execute_removeByInvalidIndex_throwsCommandException() throws Exception {
        String outOfBoundIndexString = model.getFilteredPersonList().size() + 1 + "";
        RemovePatientQueueCommand command = prepareCommandMorePatient(outOfBoundIndexString);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_removeByValidIndexFilteredList_success() throws Exception {
        Patient patientToRemove = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        RemovePatientQueueCommand command = prepareCommandMorePatient(INDEX_SECOND_PERSON.getOneBased() + "");
        String expectedMessage = String.format(RemovePatientQueueCommand.MESSAGE_REMOVE_SUCCESS,
                patientToRemove.getName().fullName);
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());
        expectedModel.removePatientFromQueueByIndex(INDEX_SECOND_PERSON);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_removeWithoutIndex_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemovePatientQueueCommand command = prepareCommand();
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());

        //remove -> first patient removed from queue
        command.execute();
        undoRedoStack.push(command);

        //undo -> reverts imdb back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        //redo -> same first patient removed again
        expectedModel.removePatientFromQueue();
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_removeWithIndex_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemovePatientQueueCommand command = prepareCommandMorePatient(INDEX_SECOND_PERSON.getOneBased() + "");
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());

        //remove -> second patient removed
        command.execute();
        undoRedoStack.push(command);

        //undo -> reverts imdb back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        //redo -> same second patient deleted again
        expectedModel.removePatientFromQueueByIndex(INDEX_SECOND_PERSON);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_removeWithValidIndexFilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemovePatientQueueCommand command = prepareCommandMorePatient(INDEX_THIRD_PERSON.getOneBased() + "");
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        //removePatientQueue -> remove third patient in unfiltered patient from queue
        command.execute();
        undoRedoStack.push(command);

        //undo -> reverts imdb back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        //redo -> remove same third patient in unfiltered patient list from queue
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_emptyQueueRemoveWithoutIndex_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemovePatientQueueCommand command = prepareEmptyQueueCommand();

        //execution failed -> remove command not pushed into undoRedoStack
        assertCommandFailure(command, model, RemovePatientQueueCommand.MESSAGE_QUEUE_EMPTY);

        //no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_removeWithInvalidIndex_failure() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        String outOfBoundIndexString = model.getFilteredPersonList().size() + 1 + "";
        RemovePatientQueueCommand command = prepareCommandMorePatient(outOfBoundIndexString);

        //execution failed -> command not pushed into undoRedoStack
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        //no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_removeWithValidIndexPatientNotInQueue_failure() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemovePatientQueueCommand command = prepareCommandMorePatientNotExist(INDEX_SECOND_PERSON.getOneBased()
                + "");

        //execution failed -> command not pushed into undoRedoStack
        assertCommandFailure(command, model, RemovePatientQueueCommand.MESSAGE_PERSON_NOT_FOUND_QUEUE);

        //no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
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
    private RemovePatientQueueCommand prepareCommand() throws IllegalValueException {
        model.addPatientToQueue(INDEX_FIRST_PERSON);
        RemovePatientQueueCommand command = new RemovePatientQueueCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code RemovePatientQueueCommand}.
     */
    private RemovePatientQueueCommand prepareCommandMorePatient(String userInput) throws IllegalValueException {
        model.addPatientToQueue(INDEX_FIRST_PERSON);
        model.addPatientToQueue(INDEX_SECOND_PERSON);
        model.addPatientToQueue(INDEX_THIRD_PERSON);
        RemovePatientQueueCommand command = new RemovePatientQueueCommand(ParserUtil.parseIndex(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code RemovePatientQueueCommand}.
     */
    private RemovePatientQueueCommand prepareCommandMorePatientNotExist(String userInput) throws IllegalValueException {
        model.addPatientToQueue(INDEX_FIRST_PERSON);
        model.addPatientToQueue(INDEX_THIRD_PERSON);
        RemovePatientQueueCommand command = new RemovePatientQueueCommand(ParserUtil.parseIndex(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    @Test
    public void equals() {
        RemovePatientQueueCommand removePatientQueueFirstCommand = new RemovePatientQueueCommand();

        //same object -> returns true
        assertTrue(removePatientQueueFirstCommand.equals(removePatientQueueFirstCommand));

        //different types -> return false
        assertFalse(removePatientQueueFirstCommand.equals(1));

        //null -> returns false
        assertFalse(removePatientQueueFirstCommand.equals(null));
    }
}
