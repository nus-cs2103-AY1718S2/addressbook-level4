package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;

//@@author Caijun7
public class ExportCommandParserTest {
    private static final String TEST_PASSWORD = "test";

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validOneArgs_returnsExportCommand() {
        assertParseSuccess(parser, "validString", new ExportCommand("validString", TEST_PASSWORD));
    }

    @Test
    public void parse_validTwoArgs_returnsExportCommand() {
        assertParseSuccess(parser, "validString test", new ExportCommand("validString", TEST_PASSWORD));
    }

    @Test
    public void parse_invalidThreeArg_throwsParseException() {
        assertParseFailure(parser, "invalidString is invalid", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidZeroArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }
}
