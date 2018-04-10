//@@author jas5469
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.testutil.GroupBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddGroupCommand}.
 */
public class AddGroupCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newGroup_success() throws Exception {
        Group validGroup = new GroupBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addGroup(validGroup);

        assertCommandSuccess(prepareCommand(validGroup, model), model,
                String.format(AddGroupCommand.MESSAGE_SUCCESS, validGroup), expectedModel);
    }

    @Test
    public void execute_duplicateGroup_throwsCommandException() {
        Group groupInList = model.getAddressBook().getGroupList().get(0);
        assertCommandFailure(prepareCommand(groupInList, model), model, AddGroupCommand.MESSAGE_DUPLICATE_GROUP);
    }
    /**
     * Generates a new {@code AddGroupCommand} which upon execution, adds {@code group} into the {@code model}.
     */
    private AddGroupCommand prepareCommand(Group group, Model model) {
        AddGroupCommand command = new AddGroupCommand(group);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
