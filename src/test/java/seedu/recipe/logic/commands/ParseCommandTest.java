//@@author kokonguyen191
package seedu.recipe.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.recipe.logic.commands.ParseCommand.MESSAGE_SUCCESS;

import org.junit.Test;

public class ParseCommandTest {
    @Test
    public void execute_nothing_throwsAssertionError() {
        CommandResult result = new ParseCommand().execute();
        assertEquals(MESSAGE_SUCCESS, result.feedbackToUser);
    }
}
