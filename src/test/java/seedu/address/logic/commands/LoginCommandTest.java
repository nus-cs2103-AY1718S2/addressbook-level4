package seedu.address.logic.commands;

import static seedu.address.logic.commands.LoginCommand.TEST_PASSWORD;
import static seedu.address.logic.commands.LoginCommand.TEST_USERNAME;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class LoginCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_displaysUsernameAndPassword() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("Username: " + TEST_USERNAME + ", Password: " + TEST_PASSWORD);

        LoginCommand command = new LoginCommand(TEST_USERNAME, TEST_PASSWORD);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }
}
