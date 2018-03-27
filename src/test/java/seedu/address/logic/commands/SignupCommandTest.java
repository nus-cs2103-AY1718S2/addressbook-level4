package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SignupCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }


    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SignupCommand(null, "123");
        new SignupCommand("John", null);
    }

    @Test
    public void execute_accountRegistered_addSuccessful() throws Exception {
        String inputUsername = "John";
        String inputPassword = "123";

        CommandResult commandResult = getSignupCommand(inputUsername, inputPassword, model).execute();

        assertEquals(SignupCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(Arrays.asList(new Account(inputUsername, inputPassword)),
                model.getAccountsManager().getAccountList());
    }

    @Test
    public void execute_duplicateAccount_throwsCommandException() throws Exception {
        String inputUsername = "John";
        String inputPassword = "123";

        thrown.expect(CommandException.class);

        //thrown.expectMessage(SignupCommand.MESSAGE_FAILURE);
        thrown.expectMessage(String.format(SignupCommand.MESSAGE_FAILURE,
                                            SignupCommand.MESSAGE_DUPLICATE_USERNAME));

        getSignupCommand(inputUsername, inputPassword, model).execute();
        getSignupCommand(inputUsername, inputPassword, model).execute();
    }

    @Test
    public void equals() {
        String inputUsername1 = "John";
        String inputPassword1 = "123";
        String inputUsername2 = "Jane";
        String inputPassword2 = "456";

        SignupCommand signupCommand1 = new SignupCommand(inputUsername1, inputPassword1);
        SignupCommand signupCommand2 = new SignupCommand(inputUsername2, inputPassword2);

        // same object -> returns true
        assertTrue(signupCommand1.equals(signupCommand1));

        // same values -> returns true
        SignupCommand signupCommand1Copy = new SignupCommand(inputUsername1, inputPassword1);
        assertTrue(signupCommand1.equals(signupCommand1Copy));

        // different types -> returns false
        assertFalse(signupCommand1.equals(1));

        // null -> returns false
        assertFalse(signupCommand1.equals(null));

        // different person -> returns false
        assertFalse(signupCommand1.equals(signupCommand2));
    }

    /**
     * Generates a new SignupCommand with the details of the given person.
     */
    private SignupCommand getSignupCommand(String username, String password,  Model model) throws Exception {
        SignupCommand command = new SignupCommand(username, password);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
