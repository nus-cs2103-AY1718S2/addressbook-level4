package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ConvertCommand;

public class ConvertCommandParserTest {

    private ConvertCommandParser parser = new ConvertCommandParser();

    @Test
    public void parse_validArgs_returnsConvertCommand() {
        assertParseSuccess(parser, "1", new ConvertCommand(1));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
    }
}
