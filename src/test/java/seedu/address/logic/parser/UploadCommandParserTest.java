package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.UploadCommand;

//@@author Caijun7
public class UploadCommandParserTest {
    private static final String TEST_PASSWORD = "test";

    private UploadCommandParser parser = new UploadCommandParser();

    @Test
    public void parse_validOneArgs_returnsUploadCommand() {
        assertParseSuccess(parser, "validString", new UploadCommand("validString", TEST_PASSWORD));
    }

    @Test
    public void parse_validTwoArgs_returnsUploadCommand() {
        assertParseSuccess(parser, "validString test", new UploadCommand("validString", TEST_PASSWORD));
    }

    @Test
    public void parse_invalidThreeArg_throwsParseException() {
        assertParseFailure(parser, "invalidString is invalid", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UploadCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidZeroArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadCommand.MESSAGE_USAGE));
    }
}
