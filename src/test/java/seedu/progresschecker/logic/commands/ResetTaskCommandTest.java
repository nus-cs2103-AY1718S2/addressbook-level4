package seedu.progresschecker.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.progresschecker.logic.commands.ResetTaskCommand.MESSAGE_NO_ACTION;
import static seedu.progresschecker.logic.commands.ResetTaskCommand.MESSAGE_SUCCESS;
import static seedu.progresschecker.model.task.TaskUtil.INDEX_OUT_OF_BOUND;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INDEX_FIRST_TASK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INDEX_LAST_TASK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.OUT_OF_BOUND_TASK_INDEX_INT;

import org.junit.Test;

//@@author EdwardKSG
/**
 * Contains assertion tests for {@code ResetTaskCommand}. This command is not undoable.
 */
public class ResetTaskCommandTest {

    @Test
    public void execute_commandEquals() throws Exception {
        ResetTaskCommand resetTaskCommand = new ResetTaskCommand(INDEX_FIRST_TASK_INT);
        ResetTaskCommand resetTaskCommand2 = new ResetTaskCommand(INDEX_LAST_TASK_INT);

        // same object -> execution successful
        assertTrue(resetTaskCommand.equals(resetTaskCommand));

        // different object -> execution failed
        assertFalse(resetTaskCommand.equals(resetTaskCommand2));
    }

    @Test
    public void execute_validIndexFirst_success() throws Exception {
        ResetTaskCommand resetFirst = new ResetTaskCommand(INDEX_FIRST_TASK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                INDEX_FIRST_TASK_INT + ". LO[W6.5][Submission]");
        String actual = resetFirst.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validIndexLast_success() throws Exception {
        ResetTaskCommand resetLast = new ResetTaskCommand(INDEX_LAST_TASK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                INDEX_LAST_TASK_INT + ". LO[W3.10][Compulsory][Submission]");
        String actual = resetLast.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validIndexLastTwice_success() throws Exception {
        ResetTaskCommand resetTwice = new ResetTaskCommand(INDEX_LAST_TASK_INT);

        String expected = String.format(MESSAGE_NO_ACTION,
                INDEX_LAST_TASK_INT + ". LO[W3.10][Compulsory][Submission]");
        String actual = resetTwice.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    // the case of negative/zero/non-integer are tested in the command parser test.

    @Test
    public void execute_invalidIndexZero_success() throws Exception {
        ResetTaskCommand resetOutOfBound = new ResetTaskCommand(OUT_OF_BOUND_TASK_INDEX_INT);

        String expected = String.format(INDEX_OUT_OF_BOUND);
        String actual = resetOutOfBound.execute().feedbackToUser;
        assertEquals(expected, actual);
    }
}
