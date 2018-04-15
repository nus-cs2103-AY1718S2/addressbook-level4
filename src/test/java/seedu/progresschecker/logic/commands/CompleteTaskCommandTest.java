package seedu.progresschecker.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.progresschecker.logic.commands.CompleteTaskCommand.MESSAGE_NO_ACTION;
import static seedu.progresschecker.logic.commands.CompleteTaskCommand.MESSAGE_SUCCESS;
import static seedu.progresschecker.model.task.TaskUtil.INDEX_OUT_OF_BOUND;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INDEX_FIRST_TASK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INDEX_LAST_TASK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.OUT_OF_BOUND_TASK_INDEX_INT;

import org.junit.Test;

//@@author EdwardKSG
/**
 * Contains assertion tests for {@code CompleteTaskCommand}. This command is not undoable.
 */
public class CompleteTaskCommandTest {

    @Test
    public void execute_commandEquals() throws Exception {
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK_INT);
        CompleteTaskCommand completeTaskCommand2 = new CompleteTaskCommand(INDEX_LAST_TASK_INT);

        // same object -> execution successful
        assertTrue(completeTaskCommand.equals(completeTaskCommand));

        // different object -> execution failed
        assertFalse(completeTaskCommand.equals(completeTaskCommand2));
    }

    @Test
    public void execute_validIndexFirst_success() throws Exception {
        CompleteTaskCommand completeFirst = new CompleteTaskCommand(INDEX_FIRST_TASK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                INDEX_FIRST_TASK_INT + ". LO[W6.5][Submission]");
        String actual = completeFirst.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validIndexLast_success() throws Exception {
        CompleteTaskCommand completeLast = new CompleteTaskCommand(INDEX_LAST_TASK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                INDEX_LAST_TASK_INT + ". LO[W3.10][Compulsory][Submission]");
        String actual = completeLast.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validIndexLastTwice_success() throws Exception {
        CompleteTaskCommand completeTwice = new CompleteTaskCommand(INDEX_LAST_TASK_INT);

        String expected = String.format(MESSAGE_NO_ACTION,
                INDEX_LAST_TASK_INT + ". LO[W3.10][Compulsory][Submission]");
        String actual = completeTwice.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    // the case of negative/zero/non-integer are tested in the command parser test.

    @Test
    public void execute_invalidIndexZero_success() throws Exception {
        CompleteTaskCommand completeOutOfBound = new CompleteTaskCommand(OUT_OF_BOUND_TASK_INDEX_INT);

        String expected = String.format(INDEX_OUT_OF_BOUND);
        String actual = completeOutOfBound.execute().feedbackToUser;
        assertEquals(expected, actual);
    }
}

