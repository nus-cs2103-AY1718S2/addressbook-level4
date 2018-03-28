package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TODO;

import org.junit.Test;

import seedu.address.logic.commands.UnCheckToDoCommand;

/**
 * As we are only doing white-node testing, our test cases do not cover path variations
 * outside of the UnCheckToDoCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the UnCheckToDoCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class UnCheckToDoCommandParserTest {

    private UnCheckToDoCommandParser parser = new UnCheckToDoCommandParser();

    @Test
    public void parse_validArgs_returnsUnCheckToDoCommand() {
        assertParseSuccess(parser, "1", new UnCheckToDoCommand(INDEX_FIRST_TODO));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(
                parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnCheckToDoCommand.MESSAGE_USAGE));
    }
}
