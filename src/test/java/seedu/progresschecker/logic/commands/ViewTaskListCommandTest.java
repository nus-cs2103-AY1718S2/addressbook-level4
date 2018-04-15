package seedu.progresschecker.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.progresschecker.logic.commands.AddDefaultTasksCommand.DEFAULT_LIST_TITLE;
import static seedu.progresschecker.logic.commands.ViewTaskListCommand.COMPULSORY_STR;
import static seedu.progresschecker.logic.commands.ViewTaskListCommand.MESSAGE_SUCCESS;
import static seedu.progresschecker.logic.commands.ViewTaskListCommand.SUBMISSION_STR;
import static seedu.progresschecker.testutil.TypicalTaskArgs.ASTERISK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.COM_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.FIRST_WEEK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.LAST_WEEK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.SUB_INT;

import org.junit.Test;

//@@author EdwardKSG
/**
 * Contains assertion tests for {@code ViewTaskListCommand}. This command is not undoable.
 */
public class ViewTaskListCommandTest {

    @Test
    public void execute_commandEquals() throws Exception {
        ViewTaskListCommand viewTaskListCommand = new ViewTaskListCommand(ASTERISK_INT);
        ViewTaskListCommand viewTaskListCommand2 = new ViewTaskListCommand(FIRST_WEEK_INT);

        // same object -> execution successful
        assertTrue(viewTaskListCommand.equals(viewTaskListCommand));

        // different object -> execution failed
        assertFalse(viewTaskListCommand.equals(viewTaskListCommand2));
    }

    @Test
    public void execute_validArgUnfilteredList_success() throws Exception {
        ViewTaskListCommand viewAll = new ViewTaskListCommand(ASTERISK_INT);

        String expected = String.format(MESSAGE_SUCCESS, DEFAULT_LIST_TITLE);
        String actual = viewAll.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validArgFirstWeekFilteredList_success() throws Exception {
        ViewTaskListCommand viewFirst = new ViewTaskListCommand(FIRST_WEEK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                DEFAULT_LIST_TITLE + "  Week: " + FIRST_WEEK_INT);
        String actual = viewFirst.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validArgLastWeekFilteredList_success() throws Exception {
        ViewTaskListCommand viewFirst = new ViewTaskListCommand(LAST_WEEK_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                DEFAULT_LIST_TITLE + "  Week: " + LAST_WEEK_INT);
        String actual = viewFirst.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validArgCompulsoryFilteredList_success() throws Exception {
        ViewTaskListCommand viewFirst = new ViewTaskListCommand(COM_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                DEFAULT_LIST_TITLE + COMPULSORY_STR);
        String actual = viewFirst.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_validArgSubmissionFilteredList_success() throws Exception {
        ViewTaskListCommand viewFirst = new ViewTaskListCommand(SUB_INT);

        String expected = String.format(MESSAGE_SUCCESS,
                DEFAULT_LIST_TITLE + SUBMISSION_STR);
        String actual = viewFirst.execute().feedbackToUser;
        assertEquals(expected, actual);
    }

}
