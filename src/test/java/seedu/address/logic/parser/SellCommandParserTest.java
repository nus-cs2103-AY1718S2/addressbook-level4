package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.TokenType.PREFIX_AMOUNT;
import static seedu.address.logic.parser.TokenType.PREFIX_NAME;
import static seedu.address.testutil.TestUtil.DECIMAL_STRING;
import static seedu.address.testutil.TestUtil.NUM_STRING;
import static seedu.address.testutil.TestUtil.STRING_ONE_STRING;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandTarget;
import seedu.address.logic.commands.SellCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Eldon-Chung
public class SellCommandParserTest {
    private static final String INDEX_AS_STRING = "1";
    private static final String INVALID_INDEX_STRING = "0";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SellCommandParser parser = new SellCommandParser();

    private SellCommand constructSellCommand(String indexAsString, String valueAsString) throws IllegalValueException {
        return new SellCommand(new CommandTarget(ParserUtil.parseIndex(indexAsString)),
                ParserUtil.parseAmount(valueAsString));
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
        String commandString = buildCommandString(INDEX_AS_STRING, PREFIX_AMOUNT.toString(), NUM_STRING);
        Command command = constructSellCommand(INDEX_AS_STRING, NUM_STRING);
        System.out.println(parser.parse(commandString));
        System.out.println(command);
        assertParseSuccess(parser, commandString, command);
        commandString = buildCommandString(INDEX_AS_STRING, PREFIX_AMOUNT.toString(), DECIMAL_STRING);
        command = constructSellCommand(INDEX_AS_STRING, DECIMAL_STRING);
        assertParseSuccess(parser, commandString, command);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE);

        //missing amount prefix
        String commandString = buildCommandString(INDEX_AS_STRING, NUM_STRING);
        assertParseFailure(parser, commandString, expectedMessage);

        //missing actual amount after prefix
        commandString = buildCommandString(INDEX_AS_STRING, PREFIX_AMOUNT.toString());
        assertParseFailure(parser, commandString, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE);

        // invalid prefix
        String commandString = buildCommandString(INDEX_AS_STRING, PREFIX_NAME.toString(), INDEX_AS_STRING);
        assertParseFailure(parser, commandString, expectedMessage);

        // invalid value
        commandString = buildCommandString(INDEX_AS_STRING, PREFIX_NAME.toString(), STRING_ONE_STRING);
        assertParseFailure(parser, commandString, expectedMessage);

        // empty preamble
        commandString = buildCommandString(PREFIX_NAME.toString(), NUM_STRING);
        assertParseFailure(parser, commandString, expectedMessage);
    }

    @Test
    public void parse_zeroIndex_throwsIndexOutOfBoundsException() throws Exception {
        thrown.expect(ParseException.class);
        String commandString = buildCommandString(INVALID_INDEX_STRING, PREFIX_NAME.toString(), NUM_STRING);
        parser.parse(commandString);
    }
}
