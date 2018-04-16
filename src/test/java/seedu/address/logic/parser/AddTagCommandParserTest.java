package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.AddTagCommand;
import seedu.address.model.tag.Tag;

//@@author dezhanglee
public class AddTagCommandParserTest {

    private static final String VALID_TAG_1 = "NUS";
    private static final String VALID_TAG_2 = "CS2103";
    private static final ArrayList<String> toAdd = new ArrayList<String>(Arrays.asList(VALID_TAG_1, VALID_TAG_2));

    public static final String TAG_DESC_CS2103 = " " + PREFIX_TAG + VALID_TAG_1;
    public static final String TAG_DESC_NUS = " " + PREFIX_TAG + VALID_TAG_2;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE);

    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TAG_1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_TAG_1, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_TAG_2, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); //invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC + VALID_TAG_1, Tag.MESSAGE_TAG_CONSTRAINTS); //invalid tag
    }

    @Test
    public void parse_validValue_success() throws Exception {
        Set<Tag> tags = ParserUtil.parseTags(toAdd);
        AddTagCommand expectedCommand = new AddTagCommand(INDEX_FIRST_PERSON, tags);
        assertParseSuccess(parser, "1 " + TAG_DESC_NUS + " " + TAG_DESC_CS2103, expectedCommand);
    }
}
