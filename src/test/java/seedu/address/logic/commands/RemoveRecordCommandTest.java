//@@author nhs-work
package seedu.address.logic.commands;

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
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Imdb;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.PatientBuilder;

public class RemoveRecordCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemoveRecordCommand(null, null);
    }

    @Test
    public void execute_deleteRecordUnfilteredList_success() throws Exception {
        Patient toEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(toEdit).withRecordList("01/04/2018 s/test i/test t/test").build();

        String expectedMessage = String.format(RemoveRecordCommand.MESSAGE_REMOVE_RECORD_SUCCESS, toEdit);

        Model updatedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        updatedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        RemoveRecordCommand removeRecordCommand = prepareCommand(INDEX_FIRST_PERSON, 0, updatedModel);

        assertCommandSuccess(removeRecordCommand, updatedModel, expectedMessage, model);
    }

    @Test
    public void execute_filteredList_success() throws Exception {

        Patient patientInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(patientInFilteredList)
                .withRecordList("01/04/2018 s/test i/test t/test").build();

        String expectedMessage = String
                .format(RemoveRecordCommand.MESSAGE_REMOVE_RECORD_SUCCESS, patientInFilteredList);

        Model updatedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        updatedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        showPersonAtIndex(updatedModel, INDEX_FIRST_PERSON);

        RemoveRecordCommand removeRecordCommand = prepareCommand(INDEX_FIRST_PERSON, 0, updatedModel);

        assertCommandSuccess(removeRecordCommand, updatedModel, expectedMessage, model);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemoveRecordCommand removeRecordCommand = prepareCommand(outOfBoundIndex, 0, model);

        assertCommandFailure(removeRecordCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit records of filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getImdb().getPersonList().size());

        RemoveRecordCommand removeRecordCommand = prepareCommand(outOfBoundIndex, 0, model);

        assertCommandFailure(removeRecordCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();

        Patient oldPatient = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient currentPatient = new PatientBuilder(oldPatient)
                .withRecordList("01/04/2018 s/test i/test t/test").build();

        Model currentModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        currentModel.updatePerson(model.getFilteredPersonList().get(0), currentPatient);

        Model updatedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        updatedModel.updatePerson(model.getFilteredPersonList().get(0), currentPatient);

        RemoveRecordCommand removeRecordCommand = prepareCommand(INDEX_FIRST_PERSON, 0, currentModel);

        UndoCommand undoCommand = prepareUndoCommand(currentModel, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(currentModel, undoRedoStack);


        // edit -> first patient edited
        removeRecordCommand.execute();
        undoRedoStack.push(removeRecordCommand);

        // undo -> reverts addressbook back to previous state and filtered patient list to show all persons
        assertCommandSuccess(undoCommand, currentModel, UndoCommand.MESSAGE_SUCCESS, updatedModel);

        // redo -> same first patient edited again
        assertCommandSuccess(redoCommand, currentModel, RedoCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemoveRecordCommand removeRecordCommand = prepareCommand(outOfBoundIndex, 0, model);

        // execution failed -> removeRecordCommand not pushed into undoRedoStack
        assertCommandFailure(removeRecordCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Patient} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited patient in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the patient object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();

        Patient oldPatient = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Patient currentPatient = new PatientBuilder(oldPatient)
                .withRecordList("01/04/2018 s/test i/test t/test").build();

        Model currentModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        currentModel.updatePerson(model.getFilteredPersonList().get(1), currentPatient);

        Model updatedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        updatedModel.updatePerson(model.getFilteredPersonList().get(1), currentPatient);

        UndoCommand undoCommand = prepareUndoCommand(currentModel, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(currentModel, undoRedoStack);

        RemoveRecordCommand removeRecordCommand = prepareCommand(INDEX_FIRST_PERSON, 0, currentModel);

        showPersonAtIndex(currentModel, INDEX_SECOND_PERSON);
        Patient patientToEdit = currentModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // record -> edits the records of the second patient in unfiltered patient list
        // / first patient in filtered patient list
        removeRecordCommand.execute();
        undoRedoStack.push(removeRecordCommand);

        // undo -> reverts addressbook back to previous state and filtered patient list to show all persons
        assertCommandSuccess(undoCommand, currentModel, UndoCommand.MESSAGE_SUCCESS, updatedModel);

        assertNotEquals(currentModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), patientToEdit);
        // redo -> edits same second patient in unfiltered patient list
        assertCommandSuccess(redoCommand, currentModel, RedoCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void equals() {
        final RemoveRecordCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, 1, model);

        // same values -> returns true
        RemoveRecordCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, 1, model);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different patient index -> returns false
        assertFalse(standardCommand.equals(new RemoveRecordCommand(INDEX_SECOND_PERSON, Index.fromOneBased(1))));

        // different record index -> returns false
        assertFalse(standardCommand.equals(new RemoveRecordCommand(INDEX_FIRST_PERSON, Index.fromOneBased(5))));
    }


    /**
     * Returns an {@code RecordCommand} with parameters {@code index} and {@code descriptor}
     */
    private RemoveRecordCommand prepareCommand(Index patientIndex, int recordIndex, Model model) {
        RemoveRecordCommand removeRecordCommand = new RemoveRecordCommand(
                patientIndex, Index.fromZeroBased(recordIndex));
        removeRecordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeRecordCommand;
    }

}
