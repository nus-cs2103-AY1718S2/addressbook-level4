//@@author Eldon-Chung
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.TokenType.PREFIXAMOUNT;
import static seedu.address.logic.parser.TokenType.PREFIXNAME;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandTarget;

public class BuyCommandParserTest {
    private static final String INDEX_AS_STRING = "1";
    private static final String INT_AS_STRING = "50";
    private static final String FLOAT_AS_STRING = "50.01";
    private static final String INVALID_VALUE_AS_STRING = "asd";

    private BuyCommandParser parser = new BuyCommandParser();

    private BuyCommand constructBuyCommand(String indexAsString, String valueAsString) throws IllegalValueException {
        return new BuyCommand(new CommandTarget(ParserUtil.parseIndex(indexAsString)),
                ParserUtil.parseDouble(valueAsString));
    }

    /**
     * Appends strings together with a space in between each of them.
     */
    private String buildCommandString(String... strings) {
        StringBuilder commandStringBuilder = new StringBuilder();
        for (String str : strings) {
            commandStringBuilder.append(String.format(" %s", str));
        }
        return commandStringBuilder.toString().trim();
    }

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String commandString = buildCommandString(INDEX_AS_STRING, PREFIXAMOUNT.toString(), INT_AS_STRING);
        Command command = constructBuyCommand(INDEX_AS_STRING, INT_AS_STRING);
        assertParseSuccess(parser, commandString, command);
        commandString = buildCommandString(INDEX_AS_STRING, PREFIXAMOUNT.toString(), FLOAT_AS_STRING);
        command = constructBuyCommand(INDEX_AS_STRING, FLOAT_AS_STRING);
        assertParseSuccess(parser, commandString, command);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE);

        //missing amount prefix
        String commandString = buildCommandString(INDEX_AS_STRING, INT_AS_STRING);
        assertParseFailure(parser, commandString, expectedMessage);

        //missing actual amount after prefix
        commandString = buildCommandString(INDEX_AS_STRING, PREFIXAMOUNT.toString());
        assertParseFailure(parser, commandString, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE);

        // invalid prefix
        String commandString = buildCommandString(INDEX_AS_STRING, PREFIXNAME.toString(), INDEX_AS_STRING);
        assertParseFailure(parser, commandString, expectedMessage);

        // invalid value
        commandString = buildCommandString(INDEX_AS_STRING, PREFIXNAME.toString(), INVALID_VALUE_AS_STRING);
        assertParseFailure(parser, commandString, expectedMessage);

        // empty preamble
        commandString = buildCommandString(PREFIXNAME.toString(), INT_AS_STRING);
        assertParseFailure(parser, commandString, expectedMessage);
    }
}
