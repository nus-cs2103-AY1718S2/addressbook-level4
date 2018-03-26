package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RECORD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RECORD_BOB;
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
import seedu.address.model.patient.Record;
import seedu.address.testutil.PatientBuilder;

public class RecordCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RecordCommand(null, null);
    }

    @Test
    public void execute_addRecordUnfilteredList_success() throws Exception {
        Patient toEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(toEdit).withRecord("date",
                "symptom", "illness", "treatment").build();
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON,
                new Record("date", "symptom", "illness", "treatment"));

        String expectedMessage = String.format(RecordCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(recordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRecordUnfilteredList_success() throws Exception {
        Patient toEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(toEdit).withRecord("", "", "", "").build();
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON, new Record("", "", "", ""));

        String expectedMessage = String.format(RecordCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(recordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Patient patientInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(patientInFilteredList).withRecord("test",
                "test", "test", "test").build();
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON, new Record("test", "test", "test", "test"));

        String expectedMessage = String.format(RecordCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(recordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RecordCommand recordCommand = prepareCommand(outOfBoundIndex, new Record("test", "test", "test", "test"));

        assertCommandFailure(recordCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        RecordCommand recordCommand = prepareCommand(outOfBoundIndex, new Record("test", "test", "test", "test"));

        assertCommandFailure(recordCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Patient patientToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON, new Record("test", "test", "test", "test"));
        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());

        // edit -> first patient edited
        recordCommand.execute();
        Patient editedPatient = recordCommand.getEdited();
        undoRedoStack.push(recordCommand);

        // undo -> reverts addressbook back to previous state and filtered patient list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first patient edited again
        expectedModel.updatePerson(patientToEdit, editedPatient);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RecordCommand recordCommand = prepareCommand(outOfBoundIndex, new Record("test", "test", "test", "test"));

        // execution failed -> recordCommand not pushed into undoRedoStack
        assertCommandFailure(recordCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

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
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON, new Record("test", "test", "test", "test"));
        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Patient patientToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // record -> edits the records of the second patient in unfiltered patient list
        // / first patient in filtered patient list
        recordCommand.execute();
        Patient editedPatient = recordCommand.getEdited();
        undoRedoStack.push(recordCommand);

        // undo -> reverts addressbook back to previous state and filtered patient list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(patientToEdit, editedPatient);
        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), patientToEdit);
        // redo -> edits same second patient in unfiltered patient list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final RecordCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, new Record(VALID_RECORD_AMY));

        // same values -> returns true
        RecordCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, new Record(VALID_RECORD_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RecordCommand(INDEX_SECOND_PERSON, new Record(VALID_RECORD_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RecordCommand(INDEX_FIRST_PERSON, new Record(VALID_RECORD_BOB))));
    }

    /**
     * Returns an {@code RecordCommand} with parameters {@code index} and {@code descriptor}
     */
    private RecordCommand prepareCommand(Index index, Record record) {
        RecordCommand recordCommand = new RecordCommand(index, record);
        recordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return recordCommand;
    }

}
