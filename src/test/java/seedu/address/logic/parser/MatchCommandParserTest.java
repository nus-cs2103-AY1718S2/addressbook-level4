package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.MatchCommand;

//@@author alexawangzi
public class MatchCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE);

    private MatchCommandParser parser = new MatchCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // missing one index
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index in first
        assertParseFailure(parser, "-5 1", MESSAGE_INVALID_FORMAT);

        // zero index in first
        assertParseFailure(parser, "0 1", MESSAGE_INVALID_FORMAT);

        //second index is negative, first positive
        assertParseFailure(parser, "1 -1", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }
}
