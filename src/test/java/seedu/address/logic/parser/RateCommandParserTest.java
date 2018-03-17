package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.RateCommand;

public class RateCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE);

    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_oneIntegerArg_failure() {
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_nonIntegerArg_failure() {
        assertParseFailure(parser, "a b", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 b", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "b 1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_indexZeroOrLess_failure() {
        assertParseFailure(parser, "0 5", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "-1 5", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_ratingOutOfBound_failure() {
        assertParseFailure(parser, "1 6", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 0", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 -1", MESSAGE_INVALID_FORMAT);
    }
}
