package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteRatingCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteRatingCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteRatingCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteRatingCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRatingCommand.MESSAGE_USAGE);

    private DeleteRatingCommandParser parser = new DeleteRatingCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteRatingCommand() {
        assertParseSuccess(parser, "1", new DeleteRatingCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);
    }
}
