package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.model.tag.Tag;

/**
 * Tests for the parsing of input arguments and creating a new ChangeTagColorCommand object
 */
public class ChangeTagColorCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeTagColorCommand.MESSAGE_USAGE);

    private ChangeTagColorCommandParser parser = new ChangeTagColorCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no color specified
        assertParseFailure(parser, VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // unsupported color specified
        assertParseFailure(parser, VALID_TAG_FRIEND + INVALID_TAG_COLOR,
                Tag.MESSAGE_TAG_COLOR_CONSTRAINTS);

        // invalid tag name
        assertParseFailure(parser, INVALID_TAG_DESC + " " + VALID_TAG_COLOR_RED,
                Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_validValue_success() {
        // unsupported color specified
        assertParseSuccess(parser, VALID_TAG_FRIEND + " " + VALID_TAG_COLOR_RED,
                new ChangeTagColorCommand(VALID_TAG_FRIEND, VALID_TAG_COLOR_RED));
    }
}
