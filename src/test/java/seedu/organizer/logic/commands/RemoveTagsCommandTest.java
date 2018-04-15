package seedu.organizer.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.organizer.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.organizer.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.organizer.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;

//@@author natania
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for RemoveTagsCommand.
 */
public class RemoveTagsCommandTest {

    private Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());
    private Set<Tag> tagList;

    @Before
    public void setUp() {
        try {
            model.loginUser(ADMIN_USER);
            ObservableList<Tag> tags = model.getOrganizer().getTagList();
            tagList = new HashSet<>(tags);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        } catch (UserPasswordWrongException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_unfilteredList_success() throws Exception {
        RemoveTagsCommand removeTagsCommand = prepareCommand(tagList);

        String expectedMessage = String.format(RemoveTagsCommand.MESSAGE_REMOVE_TAG_SUCCESS, tagList);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        for (Tag tag : tagList) {
            expectedModel.deleteTag(tag);
        }

        assertCommandSuccess(removeTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        RemoveTagsCommand removeTagsCommand = prepareCommand(tagList);

        String expectedMessage = String.format(RemoveTagsCommand.MESSAGE_REMOVE_TAG_SUCCESS, tagList);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        for (Tag tag : tagList) {
            expectedModel.deleteTag(tag);
        }

        assertCommandSuccess(removeTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_unfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemoveTagsCommand removeTagsCommand = prepareCommand(tagList);
        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);

        // edit -> delete tags
        removeTagsCommand.execute();
        undoRedoStack.push(removeTagsCommand);

        // undo -> reverts organizer back to previous state and filtered task list to show all tasks
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> delete the same tags
        for (Tag tag : tagList) {
            expectedModel.deleteTag(tag);
        }
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final RemoveTagsCommand standardCommand = prepareCommand(tagList);

        // same values -> returns true
        RemoveTagsCommand commandWithSameValues = prepareCommand(tagList);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }

    /**
     * Returns an {@code RemoveTagsCommand} with parameters {@code index} and {@code descriptor}
     */
    private RemoveTagsCommand prepareCommand(Set<Tag> tagList) {
        RemoveTagsCommand removeTagsCommand = new RemoveTagsCommand(tagList);
        removeTagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeTagsCommand;
    }
}
