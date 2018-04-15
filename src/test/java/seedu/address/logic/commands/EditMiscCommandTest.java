package seedu.address.logic.commands;

//@@author samuelloh

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MISC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MISC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGIES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.CARL;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditMiscCommand.EditMiscDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Schedule;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.testutil.EditMiscDescriptorBuilder;
import seedu.address.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditMiscCommand.
 */
public class EditMiscCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Student editedMiscStudent = new StudentBuilder(ALICE).withAllergies(VALID_ALLERGIES_BOB)
                .withNextOfKinName("Bob").withNextOfKinPhone("12345678").withRemarks(VALID_REMARKS_BOB).build();
        EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder(editedMiscStudent).build();
        EditMiscCommand editMiscCommand = prepareCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditMiscCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedMiscStudent);

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new Schedule());
        expectedModel.updateStudent(model.getFilteredStudentList().get(0), editedMiscStudent);

        assertCommandSuccess(editMiscCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastStudent = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastStudent.getZeroBased());

        StudentBuilder studentInList = new StudentBuilder(lastStudent);
        Student editedMiscStudent = studentInList.withAllergies(VALID_ALLERGIES_BOB)
                .withRemarks(VALID_REMARKS_BOB).build();

        EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder().withAllergies(VALID_ALLERGIES_BOB)
                .withRemarks(VALID_REMARKS_BOB).build();
        EditMiscCommand editMiscCommand = prepareCommand(indexLastStudent, descriptor);

        String expectedMessage = String.format(EditMiscCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedMiscStudent);

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new Schedule());
        expectedModel.updateStudent(lastStudent, editedMiscStudent);

        assertCommandSuccess(editMiscCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditMiscCommand editMiscCommand = prepareCommand(INDEX_FIRST, new EditMiscDescriptor());
        Student editedMiscStudent = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditMiscCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedMiscStudent);

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new Schedule());

        assertCommandSuccess(editMiscCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateStudentUnfilteredList_failure() throws Exception {
        Student firstStudent = new StudentBuilder(ALICE).withAllergies(VALID_ALLERGIES_BOB)
                .withRemarks(VALID_REMARKS_BOB).build();
        model.updateStudent(model.getFilteredStudentList().get(INDEX_SECOND.getZeroBased()), firstStudent);
        // To modify the 2nd student to be exactly the same as the
        // 1st student, except for the students miscellaneous info.
        EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder(firstStudent).build();
        EditMiscCommand editMiscCommand = prepareCommand(INDEX_FIRST, descriptor);

        assertCommandFailure(editMiscCommand, model, EditMiscCommand.MESSAGE_DUPLICATE_STUDENT);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder().withAllergies(VALID_ALLERGIES_BOB).build();
        EditMiscCommand editMiscCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editMiscCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Edit the misc info of a student in the filtered list where index is larger
     * than size of filtered list, but smaller than size of address book
     */
    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        EditMiscCommand editMiscCommand = prepareCommand(outOfBoundIndex,
                new EditMiscDescriptorBuilder().withAllergies(VALID_ALLERGIES_BOB).build());

        assertCommandFailure(editMiscCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Student editedStudent = new StudentBuilder(ALICE).withAllergies(VALID_ALLERGIES_BOB).build();
        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder(editedStudent).build();
        EditMiscCommand editMiscCommand = prepareCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new Schedule());

        // edit -> first student edited
        editMiscCommand.execute();
        undoRedoStack.push(editMiscCommand);

        // undo -> reverts addressbook back to previous state and filtered student list to show all students
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first student edited again
        expectedModel.updateStudent(studentToEdit, editedStudent);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder().withAllergies(VALID_ALLERGIES_BOB).build();
        EditMiscCommand editMiscCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editCommand not pushed into undoRedoStack
        assertCommandFailure(editMiscCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits the miscellaneous {@code Student} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited student in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the student object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameStudentEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Student editedStudent = new StudentBuilder(CARL).withAllergies(VALID_ALLERGIES_BOB).build();
        EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder(editedStudent).build();
        EditMiscCommand editMiscCommand = prepareCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new Schedule());

        showStudentAtIndex(model, INDEX_THIRD);
        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        // edit -> edits third student in unfiltered student list / first student in filtered student list
        editMiscCommand.execute();
        undoRedoStack.push(editMiscCommand);

        // undo -> reverts addressbook back to previous state and filtered student list to show all students
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updateStudent(studentToEdit, editedStudent);
        assertNotEquals(model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased()), studentToEdit);
        // redo -> edits same second student in unfiltered student list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final EditMiscCommand standardCommand = prepareCommand(INDEX_FIRST, DESC_MISC_AMY);

        // same values -> returns true
        EditMiscDescriptor copyDescriptor = new EditMiscDescriptor(DESC_MISC_AMY);
        EditMiscCommand commandWithSameValues = prepareCommand(INDEX_FIRST, copyDescriptor);

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
        assertFalse(standardCommand.equals(new EditMiscCommand(INDEX_SECOND, DESC_MISC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditMiscCommand(INDEX_FIRST, DESC_MISC_BOB)));
    }


    /**
     * Returns an {@code EditMiscCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditMiscCommand prepareCommand(Index index, EditMiscDescriptor descriptor) {
        EditMiscCommand editMiscCommand = new EditMiscCommand(index, descriptor);
        editMiscCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editMiscCommand;
    }

}
