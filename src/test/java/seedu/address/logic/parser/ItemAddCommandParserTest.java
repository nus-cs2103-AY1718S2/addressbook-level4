//@@author chenchongsong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.ItemAddCommand;

public class ItemAddCommandParserTest {

    public static final String VALID_ITEM_NAME = "Taxi Fare";
    public static final String VALID_ITEM_VALUE = "10.23";
    public static final String INVALID_ITEM_NAME = "Taxi*&(Fare)";
    public static final String INVALID_ITEM_VALUE = "10k";


    private ItemAddCommandParser parser = new ItemAddCommandParser();

    @Test
    public void parse_validArgs_returnsItemAddCommand() {
        assertParseSuccess(parser, "1 " + PREFIX_NAME + VALID_ITEM_NAME + " " + PREFIX_MONEY + VALID_ITEM_VALUE,
                new ItemAddCommand(INDEX_FIRST_PERSON, VALID_ITEM_NAME, VALID_ITEM_VALUE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        // 0 as targetIndex
        assertParseFailure(parser, "0 " + PREFIX_NAME + VALID_ITEM_NAME + " " + PREFIX_MONEY + VALID_ITEM_VALUE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemAddCommand.MESSAGE_USAGE));

        // no prefix "n/"
        assertParseFailure(parser, "1 " + VALID_ITEM_NAME + " " + PREFIX_MONEY + VALID_ITEM_VALUE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemAddCommand.MESSAGE_USAGE));

        // no prefix "m/"
        assertParseFailure(parser, "1 " + PREFIX_NAME + VALID_ITEM_NAME + " " + VALID_ITEM_VALUE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemAddCommand.MESSAGE_USAGE));

        // invalid item name
        assertParseFailure(parser, "1 " + PREFIX_NAME + INVALID_ITEM_NAME + " " + PREFIX_MONEY + VALID_ITEM_VALUE,
                ItemAddCommand.MESSAGE_INVALID_ARGUMENT);

        // invalid item value
        assertParseFailure(parser, "1 " + PREFIX_NAME + VALID_ITEM_NAME + " " + PREFIX_MONEY + INVALID_ITEM_VALUE,
                ItemAddCommand.MESSAGE_INVALID_ARGUMENT);
    }
}
