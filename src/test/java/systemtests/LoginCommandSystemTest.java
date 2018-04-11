package systemtests;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.testutil.TypicalUsers.DEFAULT_USER;
import static seedu.address.testutil.TypicalUsers.SLAP;

import org.junit.Test;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.model.Model;
import seedu.address.model.login.Password;
import seedu.address.model.login.Username;

public class LoginCommandSystemTest extends AddressBookSystemTest{

    @Test
    public void login(){
        /*
         * Case: login using the correct credentials,  command with leading spaces and trailing spaces
         */
        String command = LoginCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "user " + PREFIX_PASSWORD
                + "pass ";
        Model expectedModel = getLoggedOutModel();
        ModelHelper.setUsersList(expectedModel, DEFAULT_USER, SLAP);
        assertCommandSuccess(command, expectedModel);

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

    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    public class LoginAttempt {
        private Username username;
        private Password password;


        LoginAttempt(Username username, Password password){
            this.username = username;
            this.password = password;
        }

        public Username getUsername(){
            return username;
        }

        public Password getPassword(){
            return password;
        }
    }

}
