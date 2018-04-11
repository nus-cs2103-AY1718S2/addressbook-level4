package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.StreamCommand;

//@@author TeyXinHui
public class StreamCommandParserTest {

    private StreamCommandParser parser = new StreamCommandParser();

    @Test
    public void parse_validInput_success() {
        assertParseSuccess(parser, " 1 1", new StreamCommand(INDEX_FIRST_PERSON, 1));
    }

    @Test
    public void parse_emptyArgs_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StreamCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_failure() {
        //invalid Index input
        assertParseFailure(parser, " A 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StreamCommand.MESSAGE_USAGE));

        //invalid type input
        assertParseFailure(parser, " 1 A",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StreamCommand.MESSAGE_USAGE));

        //insufficient input
        assertParseFailure(parser, " 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StreamCommand.MESSAGE_USAGE));

        //too much unnecessary input
        assertParseFailure(parser, " 1 1 2 3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StreamCommand.MESSAGE_USAGE));
    }
}
//@@author
