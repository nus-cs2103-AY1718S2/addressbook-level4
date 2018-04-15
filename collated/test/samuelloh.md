# samuelloh
###### \java\seedu\address\logic\commands\EditMiscCommandTest.java
``` java

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
```
###### \java\seedu\address\logic\commands\EditPictureCommandTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.PROFILEPICTUREPATH_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PROFILEPICTUREPATH_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROFILEPICTUREPATH_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Schedule;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;

public class EditPictureCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());

    @Test
    public void execute_validFilePath_success() throws Exception {
        Student editedPictureStudent = new StudentBuilder(ALICE)
                .withProfilePictureUrl("data/profilePictures/c5daab.png").build();
        //The above is to account for the the saving of profile picture in local data using the unique key of
        //the student.

        ProfilePicturePath profilePicturePath = new ProfilePicturePath(VALID_PROFILEPICTUREPATH_AMY);
        EditPictureCommand editPictureCommand = prepareCommand(INDEX_FIRST, profilePicturePath);

        String expectedMessage = String.format(EditPictureCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedPictureStudent);

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new Schedule());
        expectedModel.updateStudent(model.getFilteredStudentList().get(0), editedPictureStudent);

        assertCommandSuccess(editPictureCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        ProfilePicturePath profilePicturePath = new ProfilePicturePath(VALID_PROFILEPICTUREPATH_AMY);
        EditPictureCommand editPictureCommand = prepareCommand(outOfBoundIndex, profilePicturePath);

        assertCommandFailure(editPictureCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
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

        EditPictureCommand editPictureCommand = prepareCommand(outOfBoundIndex,
                new ProfilePicturePath(PROFILEPICTUREPATH_DESC_AMY));

        assertCommandFailure(editPictureCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }


    @Test
    public void equals() throws Exception {
        final EditPictureCommand standardCommand = prepareCommand(INDEX_FIRST,
                new ProfilePicturePath(PROFILEPICTUREPATH_DESC_AMY));


        // same values -> returns true
        ProfilePicturePath profilePicturePath = new ProfilePicturePath(PROFILEPICTUREPATH_DESC_AMY);
        EditPictureCommand commandWithSameValues = prepareCommand(INDEX_FIRST, profilePicturePath);

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preProcessCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPictureCommand(INDEX_SECOND,
                new ProfilePicturePath(PROFILEPICTUREPATH_DESC_AMY))));

        // different profile picture path -> returns false
        assertFalse(standardCommand.equals(new EditPictureCommand(INDEX_FIRST,
                new ProfilePicturePath(PROFILEPICTUREPATH_DESC_BOB))));
    }

    /**
     * Returns an {@code EditPictureCommand} with parameters {@code index} and {@code profilePicturePath}
     */
    private EditPictureCommand prepareCommand(Index index, ProfilePicturePath profilePicturePath) {
        EditPictureCommand editPictureCommand = new EditPictureCommand(index, profilePicturePath);
        editPictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editPictureCommand;
    }

}
```
###### \java\seedu\address\logic\commands\MoreInfoCommandTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.exceptions.StorageFileMissingException.STORAGE_FILE_MISSING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.io.File;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Schedule;
import seedu.address.model.UserPrefs;

public class MoreInfoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());

    /**
     * NOTE: This test will succeed when there is no data around initially and hence no data folder.
     * If this test is failing at the moment, please delete any data in the local folder and try again.
     */
    @Test
    public void execute_storageFileMissing_failure() {
        MoreInfoCommand moreInfoCommand = prepareCommand(INDEX_FIRST);
        String expectedMessage = STORAGE_FILE_MISSING;
        assertCommandFailure(moreInfoCommand, model , expectedMessage);
    }

    @Test
    public void execute_storageFileMissing_success() throws Exception {
        MoreInfoCommand moreInfoCommand = prepareCommand(INDEX_FIRST);
        String expectedMessage = MoreInfoCommand.MESSAGE_MOREINFO_STUDENT_SUCCESS.substring(0, 40)
                + "Alice Pauline";


        String pathOfDataFileToCreate = "data/addressBook.xml";
        File dataFileToCreate = new File(pathOfDataFileToCreate);
        Boolean fileCreated = false;
        if (!dataFileToCreate.exists()) {
            new File("data").mkdir();
            dataFileToCreate.createNewFile();
            fileCreated = true;
        }

        assertCommandSuccess(moreInfoCommand, model , expectedMessage, model);

        if (fileCreated == true) {
            File parent = dataFileToCreate.getParentFile();
            dataFileToCreate.delete();
            parent.delete();

        }

    }

    @Test
    public void equals() throws Exception {
        final MoreInfoCommand standardCommand = prepareCommand(INDEX_FIRST);

        // same values -> returns true
        Index sameIndex = INDEX_FIRST;
        MoreInfoCommand commandWithSameValues = prepareCommand(sameIndex);

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preProcessStudent();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new MoreInfoCommand(INDEX_SECOND)));

    }
    /**
     * Returns a {@code MoreInfo} with parameters {@code index}
     */
    private MoreInfoCommand prepareCommand(Index index) {
        MoreInfoCommand moreInfoCommand = new MoreInfoCommand(index);
        moreInfoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return moreInfoCommand;
    }
}

```
###### \java\seedu\address\logic\parser\EditMiscCommandParserTest.java
``` java

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGIES_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGIES_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ALLERGIES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOKNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOKPHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARKS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NOKNAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOKNAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NOKPHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOKPHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGIES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGIES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKPHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKPHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditMiscCommand;
import seedu.address.model.student.miscellaneousinfo.Allergies;
import seedu.address.model.student.miscellaneousinfo.NextOfKinName;
import seedu.address.model.student.miscellaneousinfo.NextOfKinPhone;
import seedu.address.model.student.miscellaneousinfo.Remarks;
import seedu.address.testutil.EditMiscDescriptorBuilder;


public class EditMiscCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMiscCommand.MESSAGE_USAGE);

    private EditMiscCommandParser parser = new EditMiscCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ALLERGIES_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditMiscCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ALLERGIES_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ALLERGIES_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ALLERGIES_DESC,
                Allergies.MESSAGE_ALLERGIES_CONSTRAINTS); // invalid allergies
        assertParseFailure(parser, "1" + INVALID_NOKNAME_DESC,
                NextOfKinName.MESSAGE_NEXTOFKINNAME_CONSTRAINTS); // invalid next-of-kin name
        assertParseFailure(parser, "1" + INVALID_NOKPHONE_DESC,
                NextOfKinPhone.MESSAGE_NEXTOFKINPHONE_CONSTRAINTS); // invalid next-of-kin phone
        assertParseFailure(parser, "1" + INVALID_REMARKS_DESC,
                Remarks.MESSAGE_REMARKS_CONSTRAINTS); // invalid remarks

        // invalid allergies followed by valid remarks
        assertParseFailure(parser, "1" + INVALID_ALLERGIES_DESC + NOKNAME_DESC_AMY,
                Allergies.MESSAGE_ALLERGIES_CONSTRAINTS);

        // valid allergies followed by invalid allergies. The test case for invalid allergies followed by
        // valid allergies is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + ALLERGIES_DESC_AMY + INVALID_ALLERGIES_DESC,
                Allergies.MESSAGE_ALLERGIES_CONSTRAINTS);


        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_ALLERGIES_DESC + INVALID_NOKNAME_DESC
                + REMARKS_DESC_AMY, Allergies.MESSAGE_ALLERGIES_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + ALLERGIES_DESC_AMY + NOKNAME_DESC_AMY
                + NOKPHONE_DESC_AMY + REMARKS_DESC_AMY;

        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_AMY).withNextOfKinName(VALID_NOKNAME_AMY)
                .withNextOfKinPhone(VALID_NOKPHONE_AMY).withRemarks(VALID_REMARKS_AMY).build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + ALLERGIES_DESC_AMY + NOKNAME_DESC_AMY;

        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_AMY).withNextOfKinName(VALID_NOKNAME_AMY).build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // allergies
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + ALLERGIES_DESC_AMY;
        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_AMY).build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // next-of-kin name
        userInput = targetIndex.getOneBased() + NOKNAME_DESC_AMY;
        descriptor = new EditMiscDescriptorBuilder().withNextOfKinName(VALID_NOKNAME_AMY).build();
        expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // next-of-kin phone
        userInput = targetIndex.getOneBased() + NOKPHONE_DESC_AMY;
        descriptor = new EditMiscDescriptorBuilder().withNextOfKinPhone(VALID_NOKPHONE_AMY).build();
        expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remarks
        userInput = targetIndex.getOneBased() + REMARKS_DESC_AMY;
        descriptor = new EditMiscDescriptorBuilder().withRemarks(VALID_REMARKS_AMY).build();
        expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased()  + ALLERGIES_DESC_AMY + NOKNAME_DESC_AMY + NOKPHONE_DESC_AMY
                + REMARKS_DESC_AMY + ALLERGIES_DESC_AMY + ALLERGIES_DESC_BOB + NOKNAME_DESC_BOB + NOKNAME_DESC_BOB
                + NOKPHONE_DESC_BOB + REMARKS_DESC_BOB;

        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_BOB).withNextOfKinName(VALID_NOKNAME_BOB)
                .withNextOfKinPhone(VALID_NOKPHONE_BOB).withRemarks(VALID_REMARKS_BOB)
                .build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_ALLERGIES_DESC + ALLERGIES_DESC_BOB;
        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_BOB).build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + NOKNAME_DESC_BOB + INVALID_ALLERGIES_DESC + NOKPHONE_DESC_BOB
                + ALLERGIES_DESC_BOB;
        descriptor = new EditMiscDescriptorBuilder().withNextOfKinName(VALID_NOKNAME_BOB)
                .withAllergies(VALID_ALLERGIES_BOB)
                .withNextOfKinPhone(VALID_NOKPHONE_BOB).build();
        expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }




}
```
###### \java\seedu\address\logic\parser\EditPictureCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROFILEPICTUREPATH_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PROFILEPICTUREPATH_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROFILEPICTUREPATH_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PICTURE_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.EditPictureCommand;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;

public class EditPictureCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPictureCommand.MESSAGE_USAGE);

    private EditPictureCommandParser parser = new EditPictureCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PROFILEPICTUREPATH_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, PREFIX_INDEX + "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_invalidIndex_failure() {
        //missing index prefix
        assertParseFailure(parser, "1" + PROFILEPICTUREPATH_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // negative index
        assertParseFailure(parser, PREFIX_INDEX + "-5" + PROFILEPICTUREPATH_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, PREFIX_INDEX +  "0" + PROFILEPICTUREPATH_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, PREFIX_INDEX + "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, PREFIX_INDEX + "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser,  " " + PREFIX_INDEX + "1 " +  PREFIX_PICTURE_PATH
                        + INVALID_PROFILEPICTUREPATH_DESC, ProfilePicturePath.MESSAGE_PICTURE_CONSTRAINTS);
    }


}
```
###### \java\seedu\address\logic\parser\MoreInfoCommandParserTest.java
``` java

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.MoreInfoCommand;

public class MoreInfoCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoreInfoCommand.MESSAGE_USAGE);

    private MoreInfoCommandParser parser = new MoreInfoCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsMoreInfoCommand() {
        assertParseSuccess(parser, "1", new MoreInfoCommand(INDEX_FIRST));
    }
}
```
###### \java\seedu\address\model\student\ProgrammingLanguageTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.programminglanguage.ProgrammingLanguage;
import seedu.address.testutil.Assert;

public class ProgrammingLanguageTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ProgrammingLanguage(null));
    }

    @Test
    public void constructor_invalidProgrammingLanguage_throwsIllegalArgumentException() {
        String invalidProgrammingLanguage = "\t";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ProgrammingLanguage(invalidProgrammingLanguage));
    }

    @Test
    public void isValidProgrammingLanguage() {
        // null ProgrammingLanguage
        Assert.assertThrows(NullPointerException.class, () -> new ProgrammingLanguage(null));

        // invalid ProgrammingLanguage
        assertFalse(ProgrammingLanguage.isValidProgrammingLanguage("\t")); // tab an invisible character
        assertFalse(ProgrammingLanguage.isValidProgrammingLanguage("\n")); // new line an invisible charater
        assertFalse(ProgrammingLanguage.isValidProgrammingLanguage("\r")); // carriage return invisible character
        assertFalse(ProgrammingLanguage.isValidProgrammingLanguage("\f")); // form feed an invisible character

        // valid ProgrammingLanguage
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("java"));
        // alphabets only
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("12345"));
        // numbers only
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("angular js 8"));
        // alphanumeric characters
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("C"));
        // with capital letters
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("javascript version 10 hardcore"));
        // long ProgrammingLanguages
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("C++"));
        // with special characters
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("#"));
        // special characters only
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedStudentTest.java
``` java
    @Test
    public void toModelType_nullPicturePath_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_KEY, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_TAGS, VALID_FAVOURITE, INVALID_PROFILEPICTUREPATH,
                VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ProgrammingLanguage.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }
}
```
###### \java\seedu\address\storage\XmlRequiredIndexStorageTest.java
``` java
public class XmlRequiredIndexStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void saveRequiredIndexStorage_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        updateData(1, null);
    }

    @Test
    public void saveRequiredIndexStorage_invalidFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        updateData(1, "invalid");
    }

    @Test
    public void saveRequiredIndexStorage_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempRequiredIndexStorage.xml";
        FileUtil.createIfMissing(new File(filePath));
        RequiredStudentIndex original = new RequiredStudentIndex(1);
        XmlRequiredIndexStorage xmlRequiredIndexStorage = new XmlRequiredIndexStorage(filePath);

        //save in new file and read back
        xmlRequiredIndexStorage.updateData(1, filePath);
        RequiredStudentIndex readBack = xmlRequiredIndexStorage.getData(filePath);
        assertEquals(original.getRequiredStudentIndex(), readBack.getRequiredStudentIndex());
    }

}
```
###### \java\seedu\address\testutil\EditMiscDescriptorBuilder.java
``` java

import seedu.address.logic.commands.EditMiscCommand.EditMiscDescriptor;
import seedu.address.model.student.Student;
import seedu.address.model.student.miscellaneousinfo.Allergies;
import seedu.address.model.student.miscellaneousinfo.NextOfKinName;
import seedu.address.model.student.miscellaneousinfo.NextOfKinPhone;
import seedu.address.model.student.miscellaneousinfo.Remarks;

/**
 * A utility class to help with building an EditMiscDescriptor object
 */
public class EditMiscDescriptorBuilder {

    private EditMiscDescriptor descriptor;

    public EditMiscDescriptorBuilder() {
        descriptor = new EditMiscDescriptor();
    }

    public EditMiscDescriptorBuilder(EditMiscDescriptor editMiscDescriptor) {
        this.descriptor = new EditMiscDescriptor(editMiscDescriptor);
    }

    /**
     * Returns an {@code EditMiscDescriptor} object with fields containing {@code student}'s miscellaneous details
     */
    public EditMiscDescriptorBuilder(Student student) {
        this.descriptor = new EditMiscDescriptor();
        this.descriptor.setAllergies(student.getMiscellaneousInfo().getAllergies());
        this.descriptor.setNextOfKinName(student.getMiscellaneousInfo().getNextOfKinName());
        this.descriptor.setNextOfKinPhone(student.getMiscellaneousInfo().getNextOfKinPhone());
        this.descriptor.setRemarks(student.getMiscellaneousInfo().getRemarks());
    }

    /**
     * Sets the {@code Allergies} of the {@code EditMiscDescriptor} that is being built
     * @param allergies
     */
    public EditMiscDescriptorBuilder withAllergies(String allergies) {
        this.descriptor.setAllergies(new Allergies(allergies));
        return this;
    }
    /**
     * Sets the {@code NextOfKinName} of the {@code EditMiscDescriptor} that is being built
     * @param nextOfKinName
     * */
    public EditMiscDescriptorBuilder withNextOfKinName(String nextOfKinName) {
        this.descriptor.setNextOfKinName(new NextOfKinName(nextOfKinName));
        return this;
    }
    /**
     * Sets the {@code NextOfKinPhone} of the {@code EditMiscDescriptor} that is being built
     * @param nextOfKinPhone
     */
    public EditMiscDescriptorBuilder withNextOfKinPhone(String nextOfKinPhone) {
        this.descriptor.setNextOfKinPhone(new NextOfKinPhone(nextOfKinPhone));
        return this;
    }
    /**
     * Sets the {@code Remarks} of the {@code EditMiscDescriptor} that is being built
     * @param remarks
     */
    public EditMiscDescriptorBuilder withRemarks(String remarks) {
        this.descriptor.setRemarks(new Remarks(remarks));
        return this;
    }


    public EditMiscDescriptor build() {
        return descriptor;
    }

}
//@author
```
###### \java\seedu\address\testutil\StudentBuilder.java
``` java
    /**
     * Sets the {@code programminglanguage} of the {@code Student} that we are building.
     */
    public StudentBuilder withProgrammingLanguage(String progLang) {
        this.programmingLanguage = new ProgrammingLanguage(progLang);
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withAllergies(String allergies) {
        this.miscellaneousInfo = new MiscellaneousInfo(new Allergies(allergies), miscellaneousInfo.getNextOfKinName(),
                miscellaneousInfo.getNextOfKinPhone(), miscellaneousInfo.getRemarks());
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withNextOfKinName(String nextOfKinName) {
        this.miscellaneousInfo = new MiscellaneousInfo(miscellaneousInfo.getAllergies(),
                new NextOfKinName(nextOfKinName), miscellaneousInfo.getNextOfKinPhone(),
                miscellaneousInfo.getRemarks());
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withNextOfKinPhone(String nextOfKinPhone) {
        this.miscellaneousInfo = new MiscellaneousInfo(miscellaneousInfo.getAllergies(),
                miscellaneousInfo.getNextOfKinName(), new NextOfKinPhone(nextOfKinPhone),
                miscellaneousInfo.getRemarks());
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withRemarks(String remarks) {
        this.miscellaneousInfo = new MiscellaneousInfo(miscellaneousInfo.getAllergies(),
                miscellaneousInfo.getNextOfKinName(), miscellaneousInfo.getNextOfKinPhone(), new Remarks(remarks));
        return this;
    }
```
