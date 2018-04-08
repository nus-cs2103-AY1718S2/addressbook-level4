package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SetPasswordCommand;


/**
 * Test scope: similar to {@code SetPasswordCommandParserTest}.
 * @see SetPasswordCommandParserTest
 */
public class SetPasswordCommandParserTest {

    private SetPasswordCommandParser parser = new SetPasswordCommandParser();

    @Test
    public void parse_invalidArgs() {
        // no agrs provided command
        assertParseFailure(parser, " 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetPasswordCommand.MESSAGE_USAGE));

        //only old password provided
        assertParseFailure(parser, " qqq  aaa", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetPasswordCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSetPasswordCommand() {
        assertParseSuccess(parser, " ",
                new SetPasswordCommand());
    }
}
