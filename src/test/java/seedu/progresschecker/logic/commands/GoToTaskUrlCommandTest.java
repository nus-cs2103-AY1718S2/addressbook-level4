package seedu.progresschecker.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.progresschecker.logic.commands.GoToTaskUrlCommand.MESSAGE_SUCCESS;
import static seedu.progresschecker.model.task.TaskUtil.INDEX_OUT_OF_BOUND;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INDEX_FIRST_TASK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INDEX_LAST_TASK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.OUT_OF_BOUND_TASK_INDEX_INT;

import org.junit.Test;

//@@author EdwardKSG
/**
 * Contains assertion tests for {@code GoToTaskUrlCommand}. This command is not undoable.
 */
public class GoToTaskUrlCommandTest {

    @Test
    public void execute_commandEquals() throws Exception {
        GoToTaskUrlCommand goToTaskUrlCommand = new GoToTaskUrlCommand(INDEX_FIRST_TASK_INT);
        GoToTaskUrlCommand goToTaskUrlCommand2 = new GoToTaskUrlCommand(INDEX_LAST_TASK_INT);

        // same object -> execution successful
        assertTrue(goToTaskUrlCommand.equals(goToTaskUrlCommand));

        // different object -> execution failed
        assertFalse(goToTaskUrlCommand.equals(goToTaskUrlCommand2));
    }

    @Test
    public void execute_validIndexFirst_success() throws Exception {
        GoToTaskUrlCommand gotoFirst = new GoToTaskUrlCommand(INDEX_FIRST_TASK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                INDEX_FIRST_TASK_INT + ". LO[W6.5][Submission]");
        String actual = gotoFirst.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validIndexLast_success() throws Exception {
        GoToTaskUrlCommand gotoLast = new GoToTaskUrlCommand(INDEX_LAST_TASK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                INDEX_LAST_TASK_INT + ". LO[W3.10][Compulsory][Submission]");
        String actual = gotoLast.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validIndexLastTwice_success() throws Exception {
        GoToTaskUrlCommand gotoTwice = new GoToTaskUrlCommand(INDEX_LAST_TASK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                INDEX_LAST_TASK_INT + ". LO[W3.10][Compulsory][Submission]");
        String actual = gotoTwice.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    // the case of negative/zero/non-integer are tested in the command parser test.

    @Test
    public void execute_invalidIndexZero_success() throws Exception {
        GoToTaskUrlCommand gotoOutOfBound = new GoToTaskUrlCommand(OUT_OF_BOUND_TASK_INDEX_INT);

        String expected = String.format(INDEX_OUT_OF_BOUND);
        String actual = gotoOutOfBound.execute().feedbackToUser;
        assertEquals(expected, actual);
    }
}
