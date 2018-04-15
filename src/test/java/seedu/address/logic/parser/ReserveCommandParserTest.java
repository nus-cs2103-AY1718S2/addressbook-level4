//@@author chantiongley
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.ReserveCommand;


/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the BorrowCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the BorrowCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ReserveCommandParserTest {

    private ReserveCommandParser parser = new ReserveCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReserveCommand.MESSAGE_USAGE));
    }
}
