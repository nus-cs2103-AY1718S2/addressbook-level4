package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.exceptions.CommandException;

public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_throwsCommandException() throws Exception {
        LoginCommand command = new LoginCommand();

        thrown.expect(CommandException.class);
        thrown.expectMessage("Test");

        command.execute();
    }
}
