//@@author ZhangYijiong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.TagOrderCommand;

public class TagOrderCommandParserTest {

    private TagOrderCommandParser parser = new TagOrderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, "1 friend",
                new TagOrderCommand(INDEX_FIRST_PERSON, VALID_TAG_FRIEND));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagOrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "djfal;kjafld;",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagOrderCommand.MESSAGE_USAGE));
    }

}
