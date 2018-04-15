//@@author chenchongsong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.model.tag.Tag;

public class RemoveTagCommandParserTest {

    public static final String TAG_NAME_1 = "friends";
    public static final String TAG_NAME_2 = "owesMoney";

    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_validArgsSingleTag_returnsRemoveTagCommand() {
        Set<Tag> tagsToRemoved = new HashSet<>();
        tagsToRemoved.add(new Tag(TAG_NAME_1));

        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        editPersonDescriptor.setTags(tagsToRemoved);

        assertParseSuccess(parser, "1 " + PREFIX_TAG + TAG_NAME_1,
                new RemoveTagCommand(INDEX_FIRST_PERSON, editPersonDescriptor));
    }

    @Test
    public void parse_validArgsMultipleTags_returnsRemoveTagCommand() {
        Set<Tag> tagsToRemoved = new HashSet<>();
        tagsToRemoved.add(new Tag(TAG_NAME_1));
        tagsToRemoved.add(new Tag(TAG_NAME_2));

        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        editPersonDescriptor.setTags(tagsToRemoved);

        assertParseSuccess(parser, "1 " + PREFIX_TAG + TAG_NAME_1 + " " + PREFIX_TAG + TAG_NAME_2,
                new RemoveTagCommand(INDEX_FIRST_PERSON, editPersonDescriptor));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no tags
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));

        // no index
        assertParseFailure(parser, "t/friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));

        // 0 index
        assertParseFailure(parser, "0 t/friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }
}
