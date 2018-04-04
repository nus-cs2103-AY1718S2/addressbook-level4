package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
//@@author chuakunhong
public class TagReplaceCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Tag tagToDelete = new Tag("removeTag");
    private Tag tagToBePlace = new Tag("replaceTag");

    @Test
    public void execute_validTagToReplaceEntered_success() throws Exception {
        List<Tag> tagList = model.getAddressBook().getTagList();
        TagReplaceCommand tagReplaceCommand = prepareCommand(tagList);

        String expectedMessage = String.format(TagReplaceCommand.MESSAGE_REPLACE_TAG_SUCCESS, tagList.get(0),
                                                tagList.get(1));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.replaceTag(tagList);

        assertCommandSuccess(tagReplaceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTagToRemoveEntered_throwsCommandException() throws IOException {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tagToDelete);
        tagList.add(tagToBePlace);
        TagReplaceCommand tagReplaceCommand = prepareCommand(tagList);
        assertCommandFailure(tagReplaceCommand, model, Messages.MESSAGE_INVALID_TAG_ENTERED);
    }

    @Test
    public void executeUndoRedo_invalidTagToReplaceEntered_failure() throws IOException {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tagToDelete);
        tagList.add(tagToBePlace);
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        TagReplaceCommand tagReplaceCommand = prepareCommand(tagList);

        // execution failed -> replaceCommand not pushed into undoRedoStack
        assertCommandFailure(tagReplaceCommand, model, Messages.MESSAGE_INVALID_TAG_ENTERED);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private TagReplaceCommand prepareCommand(List<Tag> tagList) {
        TagReplaceCommand tagReplaceCommand = new TagReplaceCommand(tagList);
        tagReplaceCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagReplaceCommand;
    }

}
//@@author