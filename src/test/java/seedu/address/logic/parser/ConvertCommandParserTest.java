package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ConvertCommand;
//@@author jill858
public class ConvertCommandParserTest {

    private ConvertCommandParser parser = new ConvertCommandParser();

    @Test
    public void parse_validArgs_returnsConvertCommand() {
        //standard arguement
        assertParseSuccess(parser, "1 SGD USD", new ConvertCommand("SGD", "USD", 1));

        //retrieving base rate
        assertParseSuccess(parser, "SGD USD", new ConvertCommand("SGD", "USD", 1));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCurrencyCode_throwsParseException() {
        //invalid currency code
        assertParseFailure(parser, "SGD US",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "SG USD",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "SG US",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));

        //invalid number of parameter
        assertParseFailure(parser, "SGD",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "5 SGD",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));

        //invalid value
        assertParseFailure(parser, "SGD SGD USD",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "SGD 5 USD",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "SGD SGD 5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "5 5 5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
    }
}
