//@@author Articho28

package seedu.address.logic.parser;

import static org.testng.Assert.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.SearchTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Tests that the SeachTagCommandParser manipulates the input properly.
 */
public class SearchTagCommandParserTest {

    public static final String TAG_NAME_1 = "friends";
    public static final String TAG_NAME_2 = "colleagues";

    private SearchTagCommandParser searchTagCommandParser = new SearchTagCommandParser();

    /**
     * Test for single input.
     * @throws ParseException
     */
    @Test
    public void validArgsSingleTagInputIsEqual() throws ParseException {
        Set<Tag> tagsToFind = new HashSet<>();
        tagsToFind.add(new Tag(TAG_NAME_1));
        SearchTagCommand searchTagCommand = searchTagCommandParser.parse( " " + PREFIX_TAG + TAG_NAME_1);
        assertEquals(searchTagCommand.getTagsToFind(), tagsToFind);
    }

    /**
     * Tests for multiple input.
     * @throws ParseException
     */
    @Test
    public void validArgsMultipleTagsAreEqual() throws ParseException {
        Set<Tag> multipleTagsToFind = new HashSet<>();
        multipleTagsToFind.add(new Tag(TAG_NAME_1));
        multipleTagsToFind.add(new Tag(TAG_NAME_2));
        SearchTagCommand searchTagCommand = searchTagCommandParser.parse(" "
                + PREFIX_TAG + TAG_NAME_1
                + " "
                + PREFIX_TAG + TAG_NAME_2);
        assertEquals(searchTagCommand.getTagsToFind(), multipleTagsToFind);

    }
    /**
     * Throws exception when no tags are provided.
     */
    @Test
    public void emptyTagsArgs() {
        assertParseFailure(searchTagCommandParser, "a",
                String.format(SearchTagCommandParser.MESSAGE_INVALID_COMMAND_NO_TAGS, SearchTagCommand.MESSAGE_USAGE));
    }
}
