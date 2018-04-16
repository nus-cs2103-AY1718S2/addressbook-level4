package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author jill858
public class ConvertCommandTest {
    @Test
    public void equals() {
        ConvertCommand convertFirstCommand = new ConvertCommand("SGD", "USD", 0.76);
        ConvertCommand convertSecondCommand = new ConvertCommand("USD", "SGD", 1.31);

        // same object -> returns true
        assertTrue(convertFirstCommand.equals(convertFirstCommand));

        // same values -> returns true
        ConvertCommand convertFirstCommandCopy = new ConvertCommand("SGD", "USD", 0.76);
        assertTrue(convertFirstCommand.equals(convertFirstCommandCopy));

        // different types -> returns false
        assertFalse(convertFirstCommand.equals(1));

        // null -> returns false
        assertFalse(convertFirstCommand.equals(null));

        // different value -> returns false
        assertFalse(convertFirstCommand.equals(convertSecondCommand));
    }

    @Test
    public void execute_convertCurrency_validOutput() {
        double baseRate = 1.00;

        //convert 1 SGD to USD
        String expectedMessage = String.format(
                ConvertCommand.MESSAGE_COMPLETE, "SGD", baseRate, "USD", 0.76, "13-Apr-2018");
        ConvertCommand command = new ConvertCommand("SGD", "USD", 1.00);
        assertCommandResult(command, expectedMessage);

        //convert 1 SGD to TWD
        expectedMessage = String.format(
                ConvertCommand.MESSAGE_COMPLETE, "SGD", baseRate, "TWD", 22.285, "13-Apr-2018");
        command = new ConvertCommand("SGD", "TWD", 1.00);
        assertCommandResult(command, expectedMessage);

    }

    /**
     * Asserts that the result message from the execution of {@code ConvertCommand} equals to {@code expectedMessage}
     */
    private void assertCommandResult(ConvertCommand convertCommand, String expectedMessage) {
        assertEquals(expectedMessage, convertCommand.execute().feedbackToUser);
    }
}
