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
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UpdatePasswordCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private String username = "Admin";
    private String password = "ad123";


    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Account());
        LoginCommand command = new LoginCommand(username, password);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }


    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new UpdatePasswordCommand(null, "123");
        new UpdatePasswordCommand(password, null);
    }

    @Test
    public void execute_updateSuccessful() throws Exception {
        CommandResult commandResult = getUpdatePasswordCommand(password, "as12", model).execute();
        assertEquals(String.format(UpdatePasswordCommand.MESSAGE_SUCCESS, password),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidPassword_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PASSWORD);
        UpdatePasswordCommand command = getUpdatePasswordCommand("asd", "4de546", model);
        command.execute();
    }

    public UpdatePasswordCommand getUpdatePasswordCommand(String oldPassword, String newPassword, Model model) {
        UpdatePasswordCommand command = new UpdatePasswordCommand(oldPassword, newPassword);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
