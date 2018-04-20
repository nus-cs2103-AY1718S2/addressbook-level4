//@@author Kyholmes-test
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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

public class AddPatientQueueCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullObject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPatientQueueCommand(null);
    }

    @Test
    public void equals() throws IllegalValueException {
        Index firstIndex = ParserUtil.parseIndex(INDEX_FIRST_PERSON.getOneBased() + "");
        Index secondIndex = ParserUtil.parseIndex(INDEX_SECOND_PERSON.getOneBased() + "");

        AddPatientQueueCommand addQueueFirstCommand = new AddPatientQueueCommand(firstIndex);
        AddPatientQueueCommand addQueueSecondCommand = new AddPatientQueueCommand(secondIndex);

        // same object -> returns true
        assertTrue(addQueueFirstCommand.equals(addQueueFirstCommand));

        // same values -> returns true
        AddPatientQueueCommand addQueueFirstCommandCopy = new AddPatientQueueCommand(firstIndex);
        assertTrue(addQueueFirstCommand.equals(addQueueFirstCommandCopy));

        // different types -> returns false
        assertFalse(addQueueFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addQueueFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(addQueueFirstCommand.equals(addQueueSecondCommand));
    }

    @Test
    public void execute_validIndexUnfilteredList_addSuccessful() throws Exception {
        AddPatientQueueCommand command = prepareCommand(INDEX_SECOND_PERSON.getOneBased() + "");
        CommandResult commandResult = command.execute();
        assertEquals(String.format(AddPatientQueueCommand.MESSAGE_SUCCESS, TypicalPatients.BENSON.getName()),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        String outOfBoundIndexString = model.getFilteredPersonList().size() + 1 + "";
        AddPatientQueueCommand command = prepareCommand(outOfBoundIndexString);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexUnfilteredListDuplicatePatient_throwsCommandException() throws Exception {
        prepareForDuplicatePatient();
        AddPatientQueueCommand duplicateCommand = prepareCommand(INDEX_SECOND_PERSON.getOneBased() + "");
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddPatientQueueCommand.MESSAGE_DUPLICATE_PERSON);
        duplicateCommand.execute();
    }

    @Test
    public void execute_validIndexFilteredList_addSuccessful() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Patient patientToAddQueue = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddPatientQueueCommand command = prepareCommand(INDEX_FIRST_PERSON.getOneBased() + "");
        String expectedMessage = String.format(AddPatientQueueCommand.MESSAGE_SUCCESS,
                patientToAddQueue.getName().fullName);
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());
        expectedModel.addPatientToQueue(INDEX_FIRST_PERSON);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getImdb().getPersonList().size());
        AddPatientQueueCommand command = prepareCommand(INDEX_SECOND_PERSON.getOneBased() + "");
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        AddPatientQueueCommand command = prepareCommand(INDEX_FIRST_PERSON.getOneBased() + "");
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());

        //addPatientQueue -> first patient added into queue
        command.execute();
        undoRedoStack.push(command);

        //undo -> reverts imdb back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        //redo -> same first patient added into queue
        expectedModel.addPatientToQueue(INDEX_FIRST_PERSON);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        String outOfBoundIndexString = model.getFilteredPersonList().size() + 1 + "";
        AddPatientQueueCommand command = prepareCommand(outOfBoundIndexString);

        //execute failed -> addPatientQueueCommand not pushed into undoRedoStack
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        //no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Adds a patient from a filtered list by its index (after filtering).
     * 2. Undo the add patient into queue operation.
     * 3. Verify that the index of the previously added patient in the unfiltered list is different from the index at
     * the filtered list.
     * 4. Redo the add patient into queue operation. This ensures {@code RedoCommand} adds the patient into queue
     * regardless of indexing.
     */
    @Test
    public void executeUdoRedo_validIndexFilteredList_samePersonAdded() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        AddPatientQueueCommand command = prepareCommand(INDEX_FIRST_PERSON.getOneBased() + "");
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Patient patientToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        //addPatientQueue -> adds second patient in unfiltered patient list into queue /
        //first patient in filtered patient list
        command.execute();
        undoRedoStack.push(command);

        //undo -> reverts imdb back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.addPatientToQueue(INDEX_FIRST_PERSON);
        assertNotEquals(patientToAdd, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));

        //redo -> adds same second patient in unfiltered patient list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Parses {@code userInput} into a {@code AddPatientQueueCommand}.
     */
    private AddPatientQueueCommand prepareCommand(String userInput) throws IllegalValueException {
        AddPatientQueueCommand command =
                new AddPatientQueueCommand(ParserUtil.parseIndex(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
    private void prepareForDuplicatePatient() throws Exception {
        AddPatientQueueCommand duplicateCommand = prepareCommand(INDEX_SECOND_PERSON.getOneBased() + "");
        duplicateCommand.execute();
    }
}
