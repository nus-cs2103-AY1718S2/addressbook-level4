package seedu.address.logic.parser;
//@@author crizyli
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.UnlockCommand;

/**
 * Test scope: similar to {@code UnlockCommandParserTest}.
 * @see UnlockCommandParserTest
 */
public class UnlockCommandParserTest {

    private UnlockCommandParser parser = new UnlockCommandParser();

    @Test
    public void parse_validArgs_returnsLockCommand() {
        assertParseSuccess(parser, "   ", new UnlockCommand());
    }
}
