//@@author cxingkai
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.index.Index;

public class PrintCommandTest {
    @Test
    public void equals() {
        Index testIndex = Index.fromOneBased(1);
        PrintCommand printCommand = new PrintCommand(testIndex);
        PrintCommand printCommandCopy = new PrintCommand(testIndex);

        assertTrue(printCommand.equals(printCommandCopy));
    }
}