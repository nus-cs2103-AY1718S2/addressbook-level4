//@@author chenchongsong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.ItemDeleteCommand;

public class ItemDeleteCommandParserTest {

    private ItemDeleteCommandParser parser = new ItemDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsItemDeleteCommand() {
        assertParseSuccess(parser, "1 1",
                new ItemDeleteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_ITEM));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 1 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDeleteCommand.MESSAGE_USAGE));
    }
}
