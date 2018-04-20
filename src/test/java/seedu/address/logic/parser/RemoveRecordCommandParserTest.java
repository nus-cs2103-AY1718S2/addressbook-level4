//@@author nhs-work
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveRecordCommand;

public class RemoveRecordCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveRecordCommand.MESSAGE_USAGE);

    private RemoveRecordCommandParser parser = new RemoveRecordCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no patient index specified
        assertParseFailure(parser, PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PREFIX_INDEX + "1 ", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 abc/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        final StringBuilder builder = new StringBuilder();
        builder.append(targetIndex.getOneBased())
                .append(" " + PREFIX_INDEX + "1 ");

        RemoveRecordCommand expectedCommand = new RemoveRecordCommand(targetIndex, Index.fromZeroBased(0));

        assertParseSuccess(parser, builder.toString(), expectedCommand);
    }
}
