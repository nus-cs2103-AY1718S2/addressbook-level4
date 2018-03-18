package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class UnfavouriteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Student targetStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student unfavouriteTargetStudent = new StudentBuilder(targetStudent).withFavourite(false).build();
        UnfavouriteCommand unfavouriteCommand = prepareCommand(INDEX_FIRST_STUDENT);

        String expectedMessage = String.format(UnfavouriteCommand.MESSAGE_SUCCESS, unfavouriteTargetStudent);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateStudent(targetStudent, unfavouriteTargetStudent);

        assertCommandSuccess(unfavouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        UnfavouriteCommand unfavouriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unfavouriteCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    private UnfavouriteCommand prepareCommand(Index index) {
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(index);
        unfavouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unfavouriteCommand;
    }
}
