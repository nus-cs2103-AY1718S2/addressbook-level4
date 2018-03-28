package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.INFORMATION_A;
import static seedu.address.testutil.TypicalGroups.GROUP_A;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.testutil.GroupUtil;

public class AddGroupCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void addGroup() throws Exception {
        /* ------------------------ Perform addGroup operations on the shown unfiltered list ---------------------- */

        /* Case: add a group to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Group addGroup = GROUP_A;
        String command = "   " + AddGroupCommand.COMMAND_WORD + "  " + INFORMATION_A + " ";
        assertCommandSuccess(command, addGroup);

        /* Case: invalid keyword -> rejected */
        command = "addsGroup " + GroupUtil.getGroupDetails(addGroup);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: add a duplicate group -> rejected */
        command = GroupUtil.getAddGroupCommand(GROUP_A);
        assertCommandFailure(command, AddGroupCommand.MESSAGE_DUPLICATE_GROUP);
    }

    /**
     * Executes the {@code AddGroupCommand} that adds {@code addGroup} to the model and asserts that the,<br>
     * 1. Command node displays an empty string.<br>
     * 2. Command node has the default style class.<br>
     * 3. Result display node displays the success message of executing {@code AddGroupCommand} with the details of
     * {@code addGroup}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code addGroup}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Group addGroup) {
        assertCommandSuccess(GroupUtil.getAddGroupCommand(addGroup), addGroup);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Group)}. Executes {@code command}
     * instead.
     * @see AddGroupCommandSystemTest#assertCommandSuccess(Group)
     */
    private void assertCommandSuccess(String command, Group addGroup) {
        Model expectedModel = getModel();
        try {
            expectedModel.addGroup(addGroup);
        } catch (DuplicateGroupException dpt) {
            throw new IllegalArgumentException("addGroup already exists in the model.");
        }
        String expectedResultMessage = String.format(AddGroupCommand.MESSAGE_SUCCESS, addGroup);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Group)} except asserts that
     * the,<br>
     * 1. Result display node displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code GroupListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddGroupCommandSystemTest#assertCommandSuccess(String, Group)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command node displays {@code command}.<br>
     * 2. Command node has the error style class.<br>
     * 3. Result display node displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
