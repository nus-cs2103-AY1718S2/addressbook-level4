package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalLessons.getTypicalSchedule;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;

//@@author demitycho
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteLessonCommand}.
 */
public class DeleteLessonCommandTest {

    private Model model = new ModelManager(
            getTypicalAddressBook(), new UserPrefs(), getTypicalSchedule());

    @Test
    public void execute_validIndex_success() throws Exception {
        Lesson lessonToDelete = model.getSchedule().getSchedule().get(INDEX_FIRST.getZeroBased());
        DeleteLessonCommand deleteLessonCommand = prepareCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteLessonCommand.MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), getTypicalSchedule());
        expectedModel.deleteLesson(lessonToDelete);

        assertCommandSuccess(deleteLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getSchedule().getSchedule().size() + 1);
        DeleteLessonCommand deleteLessonCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteLessonCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndex_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Lesson lessonToDelete = model.getSchedule().getSchedule().get(INDEX_FIRST.getZeroBased());
        DeleteLessonCommand deleteLessonCommand = prepareCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), getTypicalSchedule());

        // delete -> first student deleted
        deleteLessonCommand.execute();
        undoRedoStack.push(deleteLessonCommand);

        // undo -> reverts addressbook back to previous state and filtered student list to show all studentFs
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first student deleted again
        expectedModel.deleteLesson(lessonToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        DeleteLessonCommand deleteFirstCommand = prepareCommand(INDEX_FIRST);
        DeleteLessonCommand deleteSecondCommand = prepareCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteLessonCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteLessonCommand} with the parameter {@code index}.
     */
    private DeleteLessonCommand prepareCommand(Index index) {
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(index);
        deleteLessonCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteLessonCommand;
    }

}
