package systemtests;

import static seedu.address.logic.commands.CreateUserCommand.MESSAGE_DUPLICATE_USER;
import static seedu.address.logic.commands.CreateUserCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.testutil.TypicalUsers.DEFAULT_USER;
import static seedu.address.testutil.TypicalUsers.SLAP;

import java.io.File;

import org.junit.Test;

import seedu.address.logic.commands.CreateUserCommand;
import seedu.address.model.Model;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.testutil.TestUtil;

public class CreateUserCommandSystemTest extends AddressBookSystemTestWithLogin {


    private File file = new File(TestUtil.getFilePathInSandboxFolder("sampleUsers.xml"));

    @Test
    public void createUser_successful() {
        file.delete();
        Model model = getModel();
        ModelHelper.setUsersList(model, DEFAULT_USER, SLAP);

        /* Case: add a user to an empty userdatabase
         * -> added
         */
        User toAdd = new User(new Username("lana"), new Password("pass"));
        String command = CreateUserCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "lana  " + PREFIX_PASSWORD
                + "pass";
        assertCommandSuccess(command, toAdd);
        file.delete();
    }

    @Test
    public void createUser_multipleSameUser_failure() {
        file.delete();
        Model model = getModel();
        ModelHelper.setUsersList(model, DEFAULT_USER, SLAP);

        /* Case: add a user to an empty userdatabase
         * -> added
         */
        User toAdd = new User(new Username("lana"), new Password("pass"));
        String command = CreateUserCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "lana  " + PREFIX_PASSWORD
                + "pass";
        assertCommandSuccess(command, toAdd);

        /* Case: add a same user to an empty userdatabase
         * -> added
         */
        command = CreateUserCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "lana  " + PREFIX_PASSWORD
                + "pass";
        assertCommandFailureNoClearCommandBox(command, MESSAGE_DUPLICATE_USER);

        /* Case: add a same user to an empty userdatabase (Upper case characters)
         * -> added
         */
        command = CreateUserCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "LaNa  " + PREFIX_PASSWORD
                + "pass";
        assertCommandFailureNoClearCommandBox(command, MESSAGE_DUPLICATE_USER);
        file.delete();
    }



    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     */
    private void assertCommandSuccess(String command, User toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addUser(toAdd);
        } catch (DuplicateUserException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(String.format(MESSAGE_SUCCESS, toAdd.getUsername().toString()),
                toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }
    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see CreateUserCommandSystemTest#assertCommandSuccess(String, User)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
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
    }

}
