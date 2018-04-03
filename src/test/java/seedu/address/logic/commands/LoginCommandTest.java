//@@author Jason1im
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private String username = "John";
    private String password = "123";


    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SignupCommand command = new SignupCommand(username, password);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }


    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null, password);
        new LoginCommand(username, null);
    }

    @Test
    public void execute_loginSuccessful() throws Exception {
        CommandResult commandResult = getLoginCommand(username, password, model).execute();
        assertEquals(String.format(LoginCommand.MESSAGE_SUCCESS, username),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidUsername_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_USERNAME);
        LoginCommand command = getLoginCommand("Jane", password, model);
        command.execute();
    }

    @Test
    public void execute_invalidPassword_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PASSWORD);
        LoginCommand command = getLoginCommand(username, "43546", model);
        command.execute();
    }

    @Test
    public void execute_multipleLogin_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_MULTIPLE_LOGIN);
        LoginCommand command = getLoginCommand(username, password, model);
        command.execute();
        command.execute();
    }

    public LoginCommand getLoginCommand(String username, String password, Model model) {
        LoginCommand command = new LoginCommand(username, password);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
