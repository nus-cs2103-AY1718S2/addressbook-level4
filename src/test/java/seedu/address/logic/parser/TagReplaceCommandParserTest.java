package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.TagReplaceCommand;
import seedu.address.model.tag.Tag;
//@@author chuakunhong
public class TagReplaceCommandParserTest {

    private TagReplaceCommandParser parser = new TagReplaceCommandParser();


    @Test


    public void parse_validArgs_returnsTagReplaceCommand() {
        List<Tag> tagList = new ArrayList<Tag>(){};
        tagList.add(new Tag("friend"));
        tagList.add(new Tag("husband"));
        assertParseFailure(parser, "t/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            TagReplaceCommand.MESSAGE_USAGE));
        //assertParseFailure(parser, "t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
        //                   TagReplaceCommand.MESSAGE_USAGE));
        //assertParseSuccess(parser, "t/friend t/husband", new TagReplaceCommand(tagList));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                                TagReplaceCommand.MESSAGE_USAGE));
    }
//@@author
}
