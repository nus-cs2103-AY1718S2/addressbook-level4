package seedu.organizer.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;
import static seedu.organizer.logic.commands.AnswerCommand.MESSAGE_USER_DOES_NOT_EXIST;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;

import org.junit.Assert;
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

//@@author dominickenn
/**
 * Contains unit tests for AnswerCommand.
 */
public class AnswerCommandTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private Model model;
    private AnswerCommand answerCommand;

    @Before
    public void setUp() {
        model = new ModelManager();
        try {
            model.addUser(ADMIN_USER);
        } catch (DuplicateUserException du) {
            throw new AssertionError("There should not be any duplicate users");
        }
        answerCommand = new AnswerCommand("admin", "answer");
    }

    @Test
    public void execute_existingUser_noQuestion() throws Exception {
        assertCommandSuccess(answerCommand,
                String.format(AnswerCommand.MESSAGE_NO_QUESTION, "admin"));
    }

    @Test
    public void execute_existingUser_success() throws Exception {
        UserWithQuestionAnswer editedUser = new UserWithQuestionAnswer(
                "admin",
                "admin",
                "question?",
                "answer");
        model.addQuestionAnswerToUser(ADMIN_USER, editedUser);
        assertCommandSuccess(answerCommand,
                String.format(AnswerCommand.MESSAGE_SUCCESS, "admin"));
    }

    @Test
    public void execute_nonExistingUser_noSuchUserFound() {
        answerCommand = new AnswerCommand("noSuchUser", "answer");
        assertCommandFailure(answerCommand, MESSAGE_USER_DOES_NOT_EXIST);
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     *
     * @throws CommandException If an error occurs during command execution.
     */
    protected void assertCommandSuccess(AnswerCommand command, String expectedMessage)
            throws CommandException {
        answerCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - Exception is thrown
     */
    protected void assertCommandFailure(AnswerCommand command, String expectedMessage) {
        answerCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }
}

