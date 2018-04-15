package seedu.progresschecker.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.progresschecker.testutil.TypicalTabTypes.TYPE_EXERCISE;
import static seedu.progresschecker.testutil.TypicalTabTypes.TYPE_TASK;

import org.junit.Test;

//@@author iNekox3
/**
 * Contains assertion tests for {@code ViewCommand}.
 */
public class ViewCommandTest {
    @Test
    public void equals() {
        ViewCommand viewFirstCommand = new ViewCommand(TYPE_TASK, -1, false);
        ViewCommand viewSecondCommand = new ViewCommand(TYPE_EXERCISE, 11, true);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(TYPE_TASK, -1, false);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different type -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }
}
