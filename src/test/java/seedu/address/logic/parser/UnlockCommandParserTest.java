package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
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
    public void parse_invalidArgs() {
        // no agrs provided command
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlockCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsLockCommand() {
        // agrs provided command
        assertParseSuccess(parser, " 1234", new UnlockCommand("1234"));
    }
}
