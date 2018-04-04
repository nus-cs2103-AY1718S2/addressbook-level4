package seedu.organizer.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import org.junit.Before;
import org.junit.Test;

import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.model.user.UserWithQuestionAnswer;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;

//@@author dominickenn
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for AddQuestionAnswerCommand.
 */
public class AddQuestionAnswerCommandTest {

    private Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());
    private String username = "admin";
    private String password = "admin";
    private String question = "are cats cool?";
    private String answer = "of course!";

    @Before
    public void setUp() {
        try {
            model.loginUser(ADMIN_USER);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_success() throws Exception {
        UserWithQuestionAnswer editedUser = new UserWithQuestionAnswer(username, password, question, answer);
        AddQuestionAnswerCommand command = prepareCommand(question, answer);

        String expectedMessage = String.format(
                AddQuestionAnswerCommand.MESSAGE_ADD_QUESTION_ANSWER_SUCCESS, editedUser);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        expectedModel.addQuestionAnswerToUser(ADMIN_USER, editedUser);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final AddQuestionAnswerCommand standardCommand = prepareCommand(question, answer);

        // same values -> returns true
        AddQuestionAnswerCommand commandWithSameValues = prepareCommand(question, answer);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different question -> returns false
        assertFalse(standardCommand.equals(new AddQuestionAnswerCommand("different", answer)));

        // different answer -> returns false
        assertFalse(standardCommand.equals(new AddQuestionAnswerCommand(question, "different")));
    }

    /**
     * Returns an {@code AddQuestionAnswerCommand} with parameters {@code question} and {@code answer}
     */
    private AddQuestionAnswerCommand prepareCommand(String question, String answer) {
        AddQuestionAnswerCommand command = new AddQuestionAnswerCommand(question, answer);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
