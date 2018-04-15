//@@author Jason1im
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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

public class UpdateUsernameCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private String username = "Admin";
    private String password = "ad123";


    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                new Account(username, password));
        LoginCommand command = new LoginCommand(username, password);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }


    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new UpdateUsernameCommand(null);
    }

    @Test
    public void execute_updateSuccessful() throws Exception {
        CommandResult commandResult = getUpdateUsernameCommand("John", model).execute();
        assertEquals(String.format(UpdateUsernameCommand.MESSAGE_SUCCESS, "John"),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_badUsername_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("Bad username. " + Account.MESSAGE_USERNAME_CONSTRAINTS);
        UpdateUsernameCommand command = getUpdateUsernameCommand(".a.", model);
        command.execute();
    }

    public UpdateUsernameCommand getUpdateUsernameCommand(String newUsername, Model model) {
        UpdateUsernameCommand command = new UpdateUsernameCommand(newUsername);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
