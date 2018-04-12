package systemtests;

import static seedu.address.logic.commands.LoginCommand.MESSAGE_LOGIN_ALREADY;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_LOGIN_FAILURE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.testutil.TypicalUsers.DEFAULT_USER;
import static seedu.address.testutil.TypicalUsers.SLAP;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.model.Model;
import seedu.address.ui.CommandBox;

//@@author kaisertanqr

public class LoginCommandSystemTest extends AddressBookSystemTestWithLogin {

    @Test
    public void login_normal_successful() {
        /*
         * Case: login using the correct credentials
         */
        String command = LoginCommand.COMMAND_WORD + " " + PREFIX_USERNAME + "u " + PREFIX_PASSWORD
                + "p ";
        Model expectedModel = getModel();
        ModelHelper.setUsersList(expectedModel, DEFAULT_USER, SLAP);
        assertCommandSuccess(command, expectedModel);

    }

    @Test
    public void login_incorrectFormat_failure() {
        /*
         * Case: login using the correct credentials with trailing whitespace
        */
        String command = "   " + LoginCommand.COMMAND_WORD + " " + PREFIX_USERNAME + "u " + PREFIX_PASSWORD
                + "p ";
        Model expectedModel = getModel();
        ModelHelper.setUsersList(expectedModel, DEFAULT_USER, SLAP);
        assertCommandFailure(command, CommandBox.MESSAGE_HAVE_NOT_LOGGED_IN);
    }

    @Test
    public void login_incorrectCredentials_failure() {
        /*
         * Case: login using the incorrect credentials
         */
        String command = LoginCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "l " + PREFIX_PASSWORD
                + "p ";
        Model expectedModel = getModel();
        ModelHelper.setUsersList(expectedModel, DEFAULT_USER, SLAP);
        assertCommandFailure(command, MESSAGE_LOGIN_FAILURE);
    }

    @Test
    public void login_multipleAttempts_failure() {
        /*
         * Case: login using the correct credentials
         */
        String command = LoginCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "u " + PREFIX_PASSWORD
                + "p ";
        Model expectedModel = getModel();
        ModelHelper.setUsersList(expectedModel, DEFAULT_USER, SLAP);
        assertCommandSuccess(command, expectedModel);
        /*
         * Case: Login again with the same credentials
         */
        command = LoginCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "u " + PREFIX_PASSWORD
                + "p ";
        assertCommandFailureNoClearCommandBox(command, MESSAGE_LOGIN_ALREADY);

    }



    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = LoginCommand.MESSAGE_LOGIN_SUCCESS;
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }
    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertStatusBarUnchanged();
    }
    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailureNoClearCommandBox(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertStatusBarUnchanged();
    }


}
