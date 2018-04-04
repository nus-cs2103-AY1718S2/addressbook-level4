package seedu.organizer.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.organizer.logic.commands.AnswerCommand;
import seedu.organizer.logic.commands.ExitCommand;
import seedu.organizer.logic.commands.ForgotPasswordCommand;
import seedu.organizer.logic.commands.LoginCommand;
import seedu.organizer.logic.commands.SignUpCommand;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.user.User;

//@@author dominickenn
/**
 * Performs OrganizerParser tests when no user is logged in
 */
public class OrganizerParserNotLoggedInTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final OrganizerParser parser = new OrganizerParser();

    private Model model = new ModelManager();
    private User user = ADMIN_USER;
    private String username = "admin";

    @Test
    public void parseCommand_signUp() throws Exception {
        SignUpCommand command = (SignUpCommand) parser.parseCommand(
                SignUpCommand.COMMAND_WORD + " "
                        + PREFIX_USERNAME + "admin "
                        + PREFIX_PASSWORD + "admin");
        assertEquals(new SignUpCommand(user), command);

        SignUpCommand commandAlias = (SignUpCommand) parser.parseCommand(
                SignUpCommand.COMMAND_ALIAS + " "
                        + PREFIX_USERNAME + "admin "
                        + PREFIX_PASSWORD + "admin");
        assertEquals(new SignUpCommand(user), commandAlias);
    }

    @Test
    public void parseCommand_login() throws Exception {
        LoginCommand command = (LoginCommand) parser.parseCommand(
                LoginCommand.COMMAND_WORD + " "
                        + PREFIX_USERNAME + "admin "
                        + PREFIX_PASSWORD + "admin");
        assertEquals(new LoginCommand(user), command);

        LoginCommand commandAlias = (LoginCommand) parser.parseCommand(
                LoginCommand.COMMAND_ALIAS + " "
                        + PREFIX_USERNAME + "admin "
                        + PREFIX_PASSWORD + "admin");
        assertEquals(new LoginCommand(user), commandAlias);
    }

    @Test
    public void parseCommand_forgotPassword() throws Exception {
        ForgotPasswordCommand command = (ForgotPasswordCommand) parser.parseCommand(
                ForgotPasswordCommand.COMMAND_WORD + " "
                        + PREFIX_USERNAME + username);
        assertEquals(new ForgotPasswordCommand(username), command);

        ForgotPasswordCommand commandAlias = (ForgotPasswordCommand) parser.parseCommand(
                ForgotPasswordCommand.COMMAND_ALIAS + " "
                        + PREFIX_USERNAME + username);
        assertEquals(new ForgotPasswordCommand(username), commandAlias);
    }

    @Test
    public void parseCommand_answer() throws Exception {
        String answer = "answer";
        AnswerCommand command = (AnswerCommand) parser.parseCommand(
                AnswerCommand.COMMAND_WORD + " "
                        + PREFIX_USERNAME + username + " "
                        + PREFIX_ANSWER + answer);
        assertEquals(new AnswerCommand(username, answer), command);

        AnswerCommand commandAlias = (AnswerCommand) parser.parseCommand(
                AnswerCommand.COMMAND_ALIAS + " "
                        + PREFIX_USERNAME + username + " "
                        + PREFIX_ANSWER + answer);
        assertEquals(new AnswerCommand(username, answer), commandAlias);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }
}
