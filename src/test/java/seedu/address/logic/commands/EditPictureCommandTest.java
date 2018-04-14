package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.PROFILEPICTUREPATH_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PROFILEPICTUREPATH_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROFILEPICTUREPATH_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Schedule;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;

public class EditPictureCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());




    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        ProfilePicturePath profilePicturePath = new ProfilePicturePath(VALID_PROFILEPICTUREPATH_AMY);
        EditPictureCommand editPictureCommand = prepareCommand(outOfBoundIndex, profilePicturePath);

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
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPictureCommand(INDEX_SECOND,
                new ProfilePicturePath(PROFILEPICTUREPATH_DESC_AMY))));

        // different descriptor -> returns false
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
