package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConvertCommandTest {
    @Test
    public void equals() {
        ConvertCommand convertFirstCommand = new ConvertCommand(1);
        ConvertCommand convertSecondCommand = new ConvertCommand(2);

        // same object -> returns true
        assertTrue(convertFirstCommand.equals(convertFirstCommand));

        // same values -> returns true
        ConvertCommand convertFirstCommandCopy = new ConvertCommand(1);
        assertTrue(convertFirstCommand.equals(convertFirstCommandCopy));

        // different types -> returns false
        assertFalse(convertFirstCommand.equals(1));

        // null -> returns false
        assertFalse(convertFirstCommand.equals(null));

        // different value -> returns false
        assertFalse(convertFirstCommand.equals(convertSecondCommand));
    }

    @Test
    public void execute_doubleArgument_validInput() {
        String expectedMessage = String.format(ConvertCommand.MESSAGE_COMPLETE, 1.00, 0.76);
        ConvertCommand command = new ConvertCommand(1);
        assertCommandResult(command, expectedMessage);
    }

    /**
     * Asserts that the result message from the execution of {@code ConvertCommand} equals to {@code expectedMessage}
     */
    private void assertCommandResult(ConvertCommand convertCommand, String expectedMessage) {
        assertEquals(expectedMessage, convertCommand.execute().feedbackToUser);
    }
}
