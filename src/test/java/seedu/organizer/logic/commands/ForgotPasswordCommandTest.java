package seedu.organizer.logic.commands;

//@@author dominickenn

import static junit.framework.TestCase.assertEquals;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.user.UserWithQuestionAnswer;
import seedu.organizer.model.user.exceptions.DuplicateUserException;

/**
 * Contains unit tests for ForgotPasswordCommand.
 */
public class ForgotPasswordCommandTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private Model model;
    private ForgotPasswordCommand forgotPasswordCommand;

    @Before
    public void setUp() {
        model = new ModelManager();
        try {
            model.addUser(ADMIN_USER);
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        }
        forgotPasswordCommand = new ForgotPasswordCommand("admin");
    }

    @Test
    public void execute_existingUser_noQuestion() throws Exception {
        assertCommandSuccess(forgotPasswordCommand,
                String.format(ForgotPasswordCommand.MESSAGE_NO_QUESTION, "admin"));
    }

    @Test
    public void execute_existingUser_question() throws Exception {
        UserWithQuestionAnswer editedUser = new UserWithQuestionAnswer(
                "admin",
                "admin",
                "question?",
                "answer");
        model.addQuestionAnswerToUser(ADMIN_USER, editedUser);
        assertCommandSuccess(forgotPasswordCommand,
                String.format(ForgotPasswordCommand.MESSAGE_SUCCESS, "question?"));
    }

    @Test
    public void execute_nonexistingUser_noSuchUserFound() {
        forgotPasswordCommand = new ForgotPasswordCommand("noSuchUser");
        assertCommandFailure(forgotPasswordCommand);
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     *
     * @throws CommandException If an error occurs during command execution.
     */
    protected void assertCommandSuccess(ForgotPasswordCommand command, String expectedMessage)
            throws CommandException {
        forgotPasswordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - Exception is thrown
     */
    protected void assertCommandFailure(ForgotPasswordCommand command) {
        forgotPasswordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        exception.expect(AssertionError.class);
        command.execute();
    }
}

