package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.ADESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.AddConditionCommand.EditPersonDescriptor;
import seedu.address.model.Imdb;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.AEditPersonDescriptorBuilder;
import seedu.address.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and
 * unit tests for AddConditionCommand.
 */
public class AddConditionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Patient editedPatient = new PatientBuilder().build();
        EditPersonDescriptor descriptor = new AEditPersonDescriptorBuilder(editedPatient).build();
        AddConditionCommand addConditionCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AddConditionCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPatient);

        assertCommandSuccess(addConditionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Patient firstPatient = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new AEditPersonDescriptorBuilder(firstPatient).build();
        AddConditionCommand addConditionCommand = prepareCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(addConditionCommand, model, AddConditionCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit patient in filtered list into a duplicate in address book
        Patient patientInList = model.getImdb().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        AddConditionCommand addConditionCommand = prepareCommand(INDEX_FIRST_PERSON,
                new AEditPersonDescriptorBuilder(patientInList).build());

        assertCommandFailure(addConditionCommand, model, AddConditionCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new AEditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        AddConditionCommand addConditionCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(addConditionCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getImdb().getPersonList().size());

        AddConditionCommand addConditionCommand = prepareCommand(outOfBoundIndex,
                new AEditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(addConditionCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Patient editedPatient = new PatientBuilder().build();
        Patient patientToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new AEditPersonDescriptorBuilder(editedPatient).build();
        AddConditionCommand addConditionCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());

        // edit -> first patient edited
        addConditionCommand.execute();
        undoRedoStack.push(addConditionCommand);

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
        EditPersonDescriptor descriptor = new AEditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        AddConditionCommand addConditionCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> addConditionCommand not pushed into undoRedoStack
        assertCommandFailure(addConditionCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

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
        Patient editedPatient = new PatientBuilder().build();
        EditPersonDescriptor descriptor = new AEditPersonDescriptorBuilder(editedPatient).build();
        AddConditionCommand addConditionCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new Imdb(model.getImdb()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Patient patientToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // edit -> edits second patient in unfiltered patient list / first patient in filtered patient list
        addConditionCommand.execute();
        undoRedoStack.push(addConditionCommand);

        // undo -> reverts addressbook back to previous state and filtered patient list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(patientToEdit, editedPatient);
        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), patientToEdit);
        // redo -> edits same second patient in unfiltered patient list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final AddConditionCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, ADESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(ADESC_AMY);
        AddConditionCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddConditionCommand(INDEX_SECOND_PERSON, ADESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AddConditionCommand(INDEX_FIRST_PERSON, ADESC_BOB)));
    }

    /**
     * Returns an {@code AddConditionCommand} with parameters {@code index} and {@code descriptor}
     */
    private AddConditionCommand prepareCommand(Index index, EditPersonDescriptor descriptor) {
        AddConditionCommand addConditionCommand = new AddConditionCommand(index, descriptor);
        addConditionCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addConditionCommand;
    }
}
