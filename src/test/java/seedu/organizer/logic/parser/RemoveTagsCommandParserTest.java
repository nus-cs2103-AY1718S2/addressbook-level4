package seedu.organizer.logic.parser;

import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_FRIENDS;
import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.organizer.logic.commands.RemoveTagsCommand;
import seedu.organizer.model.tag.Tag;

//@@author natania-d
public class RemoveTagsCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private RemoveTagsCommandParser parser = new RemoveTagsCommandParser();

    @Test
    public void parse_noTagGiven_success() {
        // no field specified
        Set<Tag> emptyTagList = new HashSet<>();
        RemoveTagsCommand expectedCommand = new RemoveTagsCommand(emptyTagList);
        assertParseSuccess(parser, "", expectedCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, TAG_DESC_FRIENDS + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser,
                TAG_EMPTY + TAG_DESC_FRIENDS + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_validValue_failure() {
        Set<Tag> tagList = new HashSet<>();
        tagList.add(new Tag(VALID_TAG_FRIENDS));
        tagList.add(new Tag(VALID_TAG_HUSBAND));
        RemoveTagsCommand expectedCommand = new RemoveTagsCommand(tagList);
        assertParseSuccess(parser, TAG_DESC_FRIENDS + TAG_DESC_HUSBAND, expectedCommand);
    }
}
