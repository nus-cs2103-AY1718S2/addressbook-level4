package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

public class TagDeleteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Tag tagToDelete = new Tag("removeTag");

    @Test
    public void execute_validTagToRemoveEntered_success() throws Exception {
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        List<Tag> tagList = addressBook.getTagList();
        Tag removeTag = tagList.get(0);
        TagDeleteCommand tagDeleteCommand = prepareCommand(removeTag);

        String expectedMessage = String.format(TagDeleteCommand.MESSAGE_DELETE_TAG_SUCCESS, removeTag);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(removeTag);

        assertCommandSuccess(tagDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTagToRemoveEntered_throwsCommandException() {
        TagDeleteCommand tagDeleteCommand = prepareCommand(tagToDelete);
        assertCommandFailure(tagDeleteCommand, model, Messages.MESSAGE_INVALID_TAG_ENTERED);
    }

    @Test
    public void executeUndoRedo_invalidTagToRemoveEntered_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        TagDeleteCommand tagDeleteCommand = prepareCommand(tagToDelete);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(tagDeleteCommand, model, Messages.MESSAGE_INVALID_TAG_ENTERED);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private TagDeleteCommand prepareCommand(Tag removeTag) {
        TagDeleteCommand tagDeleteCommand = new TagDeleteCommand(removeTag);
        tagDeleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagDeleteCommand;
    }

}
