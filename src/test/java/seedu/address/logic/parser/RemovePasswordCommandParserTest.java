package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.RemovePasswordCommand;

//@@author yeggasd
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the RemovePasswordCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the RemovePasswordCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RemovePasswordCommandParserTest {

    private RemovePasswordCommandParser parser = new RemovePasswordCommandParser();

    @Test
    public void parse_validArgs_returnsParseCommand() {
        Command command = parser.parse("");
        assertTrue(command instanceof RemovePasswordCommand);
    }
}
