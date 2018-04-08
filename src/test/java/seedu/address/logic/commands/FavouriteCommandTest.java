package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
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
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

//@@author yapni
/**
 * Contains integration tests (interaction with the Model) for {@code FavouriteCommand}.
 */
public class FavouriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Student targetStudent = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        Student favouritedTargetStudent = new StudentBuilder(targetStudent).withFavourite(true).build();
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_FIRST);

        String expectedMessage = String.format(FavouriteCommand.MESSAGE_SUCCESS, favouritedTargetStudent);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Schedule());
        expectedModel.updateStudent(targetStudent, favouritedTargetStudent);

        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        FavouriteCommand favouriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    private FavouriteCommand prepareCommand(Index index) {
        FavouriteCommand favouriteCommand = new FavouriteCommand(index);
        favouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favouriteCommand;
    }
}
