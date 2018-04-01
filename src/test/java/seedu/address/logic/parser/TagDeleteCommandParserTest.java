package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.TagDeleteCommand;
import seedu.address.model.tag.Tag;

public class TagDeleteCommandParserTest {

    private TagDeleteCommandParser parser = new TagDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsTagDeleteCommand() {
        assertParseSuccess(parser, "removeTag", new TagDeleteCommand(new Tag("removeTag")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagDeleteCommand.MESSAGE_USAGE));
    }
}
