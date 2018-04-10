package seedu.address.logic.parser;
//@@author crizyli
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LockCommand;

/**
 * Test scope: similar to {@code UnlockCommandParserTest}.
 * @see UnlockCommandParserTest
 */
public class LockCommandParserTest {

    private LockCommandParser parser = new LockCommandParser();

    @Test
    public void parse_invalidArgs() {
        assertParseFailure(parser, " 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LockCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsLockCommand() {
        assertParseSuccess(parser, "   ", new LockCommand());
    }
}
