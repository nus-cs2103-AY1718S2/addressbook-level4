package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.PasswordCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the PasswordCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class PasswordCommandParserTest {

    private PasswordCommandParser parser = new PasswordCommandParser();

    @Test
    public void parse_validArgs_returnsParseCommand() {
        assertParseSuccess(parser, "1", new PasswordCommand("1"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PasswordCommand.INVALID_PASSWORD, PasswordCommand.MESSAGE_USAGE));
    }
}
