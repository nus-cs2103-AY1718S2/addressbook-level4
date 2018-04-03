package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalOddEven.ODD_INDEX;

import org.junit.Test;

import seedu.address.logic.commands.TimeTableCommand;

//@@author yeggasd
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the TimeTableCommand code. For example, inputs "1 odd" and "1 abc" take the
 * same path through the TimeTableCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class TimeTableCommandParserTest {

    private TimeTableCommandParser parser = new TimeTableCommandParser();

    @Test
    public void parse_validArgs_returnsTimeTableCommand() {
        assertParseSuccess(parser, "1 odd", new TimeTableCommand(INDEX_FIRST_PERSON, ODD_INDEX));
    }

    @Test
    public void parse_invalidNumArgs_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimeTableCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1odd", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TimeTableCommand.MESSAGE_USAGE));
    }
}
