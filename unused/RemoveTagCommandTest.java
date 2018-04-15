package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TAG;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

//@@author alexawangzi
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code RemoveTagCommand}.
 */
public class RemoveTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_removeValidTag_success() throws Exception {
        Tag tagToDelete = new Tag("English");
        RemoveTagCommand removeTagCommand = prepareCommand(tagToDelete);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(tagToDelete);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeInvalidTag_failure() throws Exception {
        Tag tagToDelete = new Tag("nonExistingTag");
        RemoveTagCommand removeTagCommand = prepareCommand(tagToDelete);
        String expectedMessage = String.format(MESSAGE_INVALID_TAG);
        assertCommandFailure(removeTagCommand, model, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validTag_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Tag tagToDelete = new Tag("English");
        RemoveTagCommand removeTagCommand = prepareCommand(tagToDelete);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first tag deleted
        removeTagCommand.execute();
        undoRedoStack.push(removeTagCommand);

        // undo -> reverts addressbook back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same tag deleted again
        expectedModel.deleteTag(tagToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidTag_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Tag tagToDelete = new Tag("nonExistingTag");
        RemoveTagCommand removeTagCommand = prepareCommand(tagToDelete);
        String expectedMessage = String.format(MESSAGE_INVALID_TAG);

        // execution failed -> removeTagCommand not pushed into undoRedoStack
        assertCommandFailure(removeTagCommand, model, expectedMessage);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private RemoveTagCommand prepareCommand(Tag tag) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(tag);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeTagCommand;
    }

}
