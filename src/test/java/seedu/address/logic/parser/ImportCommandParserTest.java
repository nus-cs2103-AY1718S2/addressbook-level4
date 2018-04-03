package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ImportCommand;

//@@author Caijun7
public class ImportCommandParserTest {
    private static final String TEST_PASSWORD = "test";

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validOneArgs_returnsImportCommand() {
        assertParseSuccess(parser, "validString", new ImportCommand("validString", TEST_PASSWORD));
    }

    @Test
    public void parse_validTwoArgs_returnsImportCommand() {
        assertParseSuccess(parser, "validString test", new ImportCommand("validString", TEST_PASSWORD));
    }

    @Test
    public void parse_invalidThreeArg_throwsParseException() {
        assertParseFailure(parser, "invalidString is invalid", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidZeroArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}
