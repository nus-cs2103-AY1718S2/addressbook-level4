# nhs-work
###### \java\seedu\address\logic\commands\RecordCommandTest.java
``` java
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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.record.RecordManager;
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
        Patient editedPatient = new PatientBuilder(toEdit).withRecordList("01/04/2018 s/ i/ t/").build();
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON, 0,
                new Record("01/04/2018", "", "", ""));

        String expectedMessage = String.format(RecordCommand.MESSAGE_ADD_RECORD_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(recordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRecordUnfilteredList_success() throws Exception {
        Patient toEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(toEdit).withRecordList("01/04/2018 s/ i/ t/").build();
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON, 0, new Record("01/04/2018", "", "", ""));

        String expectedMessage = String.format(RecordCommand.MESSAGE_ADD_RECORD_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(recordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Patient patientInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(patientInFilteredList)
                .withRecordList("01/04/2018 s/test i/test t/test").build();
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON, 0,
                new Record("01/04/2018", "test", "test", "test"));

        String expectedMessage = String.format(RecordCommand.MESSAGE_ADD_RECORD_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(recordCommand, model, expectedMessage, expectedModel);
        RecordCommand undoCommand = prepareCommand(INDEX_FIRST_PERSON, 0,
                new Record(new SimpleDateFormat("dd/MM/yyyy").format(new Date()),
                "", "", ""));
        undoCommand.execute();
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RecordCommand recordCommand = prepareCommand(outOfBoundIndex, 0);

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

        RecordCommand recordCommand = prepareCommand(outOfBoundIndex, 0);

        assertCommandFailure(recordCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Patient patientToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON, 0,
                new Record("01/04/2018", "test", "test", "test"));
        RecordCommand toUndoCommand = prepareCommand(INDEX_FIRST_PERSON, 0,
                new Record(new SimpleDateFormat("dd/MM/yyyy").format(new Date()),
                "", "", ""));
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

        toUndoCommand.execute();
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RecordCommand recordCommand = prepareCommand(outOfBoundIndex, 0);

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
        RecordCommand recordCommand = prepareCommand(INDEX_FIRST_PERSON, 0, new Record("01/04/2018", "b", "b", "b"));
        RecordCommand toUndoCommand = prepareCommand(INDEX_SECOND_PERSON, 0,
                new Record(new SimpleDateFormat("dd/MM/yyyy").format(new Date()),
                "", "", ""));
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
        toUndoCommand.execute();
    }

    @Test
    public void equals() {
        final RecordCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, 1);

        // same values -> returns true
        RecordCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, 1);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different patient index -> returns false
        assertFalse(standardCommand.equals(new RecordCommand(INDEX_SECOND_PERSON, Index.fromOneBased(1))));

        // different record index -> returns false
        assertFalse(standardCommand.equals(new RecordCommand(INDEX_FIRST_PERSON, Index.fromOneBased(5))));
    }

    /**
     * Returns an {@code RecordCommand} with parameters {@code index} and {@code descriptor}
     */
    private RecordCommand prepareCommand(Index patientIndex, int recordIndex) {
        RecordCommand recordCommand = new RecordCommand(patientIndex, Index.fromZeroBased(recordIndex));
        recordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return recordCommand;
    }

    /**
     * Returns an {@code RecordCommand} with parameters {@code index} and {@code descriptor}
     */
    private RecordCommand prepareCommand(Index patientIndex, int recordIndex, Record record) {
        RecordCommand recordCommand = new RecordCommand(patientIndex, Index.fromZeroBased(recordIndex), true);
        RecordManager.authenticate(record.getDate(), record.getSymptom(), record.getIllness(), record.getTreatment());
        recordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return recordCommand;
    }

}
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
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
import seedu.address.model.patient.Remark;
import seedu.address.testutil.PatientBuilder;

public class RemarkCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndexAndRemark_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemarkCommand(null, null);
    }

    @Test
    public void execute_addRemarkUnfilteredList_success() throws Exception {
        Patient toEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(toEdit).withRemark("test").build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark("test"));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemarkUnfilteredList_success() throws Exception {
        Patient toEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(toEdit).withRemark("").build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark(""));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_REMOVE_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Patient patientInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(patientInFilteredList).withRemark("test").build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark("test"));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, new Remark("test"));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit remarks of filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getImdb().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, new Remark("test"));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Patient patientToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark("test"));
        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());

        // edit -> first patient edited
        remarkCommand.execute();
        Patient editedPatient = remarkCommand.getEdited();
        undoRedoStack.push(remarkCommand);

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
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, new Remark("test"));

        // execution failed -> remarkCommand not pushed into undoRedoStack
        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

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
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark("test"));
        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Patient patientToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // remark -> edits the remarks of the second patient in unfiltered patient list
        // / first patient in filtered patient list
        remarkCommand.execute();
        Patient editedPatient = remarkCommand.getEdited();
        undoRedoStack.push(remarkCommand);

        // undo -> reverts addressbook back to previous state and filtered patient list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(patientToEdit, editedPatient);
        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), patientToEdit);
        // redo -> edits same second patient in unfiltered patient list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // same values -> returns true
        RemarkCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code descriptor}
     */
    private RemarkCommand prepareCommand(Index index, Remark remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, remark);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }

}
```
###### \java\seedu\address\logic\commands\RemoveRecordCommandTest.java
``` java
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
```
###### \java\seedu\address\logic\parser\RecordCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RecordCommand;

public class RecordCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordCommand.MESSAGE_USAGE);

    private RecordCommandParser parser = new RecordCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no patient index specified
        assertParseFailure(parser, PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 abc/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        final StringBuilder builder = new StringBuilder();
        builder.append(targetIndex.getOneBased())
                .append(" " + PREFIX_INDEX + "1 ");

        RecordCommand expectedCommand = new RecordCommand(targetIndex, Index.fromZeroBased(0));

        assertParseSuccess(parser, builder.toString(), expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.patient.Remark;

public class RemarkCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " r/Is friendly";

        RemarkCommand expectedCommand = new RemarkCommand(targetIndex, new Remark("Is friendly"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\RemoveRecordCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveRecordCommand;

public class RemoveRecordCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveRecordCommand.MESSAGE_USAGE);

    private RemoveRecordCommandParser parser = new RemoveRecordCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no patient index specified
        assertParseFailure(parser, PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 abc/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        final StringBuilder builder = new StringBuilder();
        builder.append(targetIndex.getOneBased())
                .append(" " + PREFIX_INDEX + "1 ");

        RemoveRecordCommand expectedCommand = new RemoveRecordCommand(targetIndex, Index.fromZeroBased(0));

        assertParseSuccess(parser, builder.toString(), expectedCommand);
    }
}
```
###### \java\seedu\address\model\patient\RecordListTest.java
``` java
package seedu.address.model.patient;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;

public class RecordListTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Record(null, null, null, null));
    }

    @Test
    public void isValidRecordList() throws ParseException {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> RecordList.isValidRecordList(null));

        ArrayList<Record> temp = new ArrayList<Record>();

        // valid recordLists
        temp.add(new Record("01/04/2018", "", "", ""));
        assertTrue(RecordList.isValidRecordList(new RecordList(temp))); // empty string
        temp.remove(0);
        temp.add(new Record("01/04/2018", " ", " ", " "));
        assertTrue(RecordList.isValidRecordList(new RecordList(temp))); // spaces only
        temp.remove(0);
        temp.add(new Record("01/04/2018", "b", "c", "d"));
        assertTrue(RecordList.isValidRecordList(new RecordList(temp))); // one character
        try {
            assertTrue(RecordList.isValidRecordList(new RecordList("01/04/2018 s/a i/b t/c"))); // one character
        } catch (ParseException pe) {
            throw pe;
        }

        // invalid recordList
        temp.remove(0);
        Assert.assertThrows(IllegalArgumentException.class, () -> new RecordList("9th March 2017 s/ i/ t/"));
    }
}
```
###### \java\seedu\address\model\patient\RecordTest.java
``` java
package seedu.address.model.patient;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RecordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Record(null, null, null, null));
    }

    @Test
    public void isValidRecord() {
        // null record
        Assert.assertThrows(NullPointerException.class, () -> Record.isValidRecord(null));

        // invalid records
        Assert.assertThrows(IllegalArgumentException.class, () -> Record.isValidRecord(new Record("", "", "", "")));
        Assert.assertThrows(IllegalArgumentException.class, () -> Record.isValidRecord(new Record(" ", " ", " ", " ")));
        Assert.assertThrows(IllegalArgumentException.class, () -> Record.isValidRecord(
                new Record("5th March 2016", " ", " ", " ")));

        // valid records
        assertTrue(Record.isValidRecord(new Record("01/04/2018", "High temperature", "Fever", "Antibiotics")));
        assertTrue(Record.isValidRecord(new Record("11/11/1991", "b", "c", "d"))); // one character
    }
}
```
###### \java\seedu\address\model\patient\RemarkTest.java
``` java
package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemarkTest {

    @Test
    public void isValidRemark() {
        // valid remarks
        assertTrue(Remark.isValidRemark("test"));
        assertTrue(Remark.isValidRemark("Shows up weekly for medication")); // long remark
        assertTrue(Remark.isValidRemark("a")); // one character
    }

    @Test
    public void equals() {
        Remark remark = new Remark("test");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same value -> returns true
        Remark remark2 = new Remark("test");
        assertTrue(remark.equals(remark2));

        // different value -> returns false
        Remark remark3 = new Remark("not test");
        assertFalse(remark.equals(remark3));

        // different objects -> returns false
        assertFalse(remark.equals(362));

        // null -> returns false
        assertFalse(remark.equals(null));
    }
}
```
