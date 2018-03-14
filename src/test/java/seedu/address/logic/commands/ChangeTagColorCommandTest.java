package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_RED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for ChangeTagColorCommand.
 */
public class ChangeTagColorCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_correctFields_success() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        ChangeTagColorCommand command = prepareCommand(VALID_TAG_FRIEND, VALID_TAG_COLOR_RED);

        String expectedMessage =
                String.format(ChangeTagColorCommand.MESSAGE_EDIT_TAG_SUCCESS, VALID_TAG_FRIEND, VALID_TAG_COLOR_RED);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Tag oldTag = new Tag(VALID_TAG_FRIEND);
        Tag newTag = new Tag(VALID_TAG_FRIEND, VALID_TAG_COLOR_RED);
        expectedModel.updateTag(oldTag, newTag);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagNameNotInList_failure() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        ChangeTagColorCommand command = prepareCommand(VALID_TAG_HUSBAND, VALID_TAG_COLOR_RED);

        assertCommandFailure(command, model, ChangeTagColorCommand.MESSAGE_TAG_NOT_IN_LIST);
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private ChangeTagColorCommand prepareCommand(String name, String color) {
        ChangeTagColorCommand command = new ChangeTagColorCommand(name, color);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
