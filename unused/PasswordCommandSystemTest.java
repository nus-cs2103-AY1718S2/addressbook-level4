package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SET;

import org.junit.Test;

import seedu.address.logic.commands.PasswordCommand;

//@@author limzk1994
public class PasswordCommandSystemTest extends AddressBookSystemTest {

    private static String oldPassword = "old";
    private static String newPassword = "new";

    @Test
    public void pass() {
        // Set Password success
        String command = PasswordCommand.COMMAND_WORD + " " + PREFIX_SET + oldPassword;
        assertCommandSuccess(command, PasswordCommand.MESSAGE_SUCCESS);

        // Set Password fail because password already present
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_SET + oldPassword;
        assertCommandFailure(command, PasswordCommand.MESSAGE_PASSWORD_EXISTS);

        // Change Password success
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_CHANGE + newPassword;
        assertCommandSuccess(command, PasswordCommand.MESSAGE_PASSWORD_CHANGE);

        // Remove Password success
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_REMOVE + newPassword;
        assertCommandSuccess(command, PasswordCommand.MESSAGE_PASSWORD_REMOVE);

        // Remove Password fail because no password to clear
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_REMOVE + newPassword;
        assertCommandFailure(command, PasswordCommand.MESSAGE_NO_PASSWORD_EXISTS);

        // Change Password fail because no password to change
        command = PasswordCommand.COMMAND_WORD + " " + PREFIX_CHANGE + newPassword;
        assertCommandFailure(command, PasswordCommand.MESSAGE_NO_PASSWORD_EXISTS);

    }
    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     *
     */
    private void assertCommandFailure (String command, String expectedResultMessage) {
        executeCommand(command);
        assertEquals(command, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());

        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     *
     */
    private void assertCommandSuccess(String command, String expectedResultMessage) {

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, getModel());

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }
}
