package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.Year;

import org.junit.Test;

import seedu.address.logic.commands.appointment.YearCommand;
import seedu.address.logic.parser.appointment.YearCommandParser;

//@@author trafalgarandre
public class YearCommandParserTest {

    private YearCommandParser parser = new YearCommandParser();

    @Test
    public void parse_validArgs_returnsDateCommand() {
        assertParseSuccess(parser, "", new YearCommand(null));
        assertParseSuccess(parser, "2018", new YearCommand(Year.parse("2018")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "2018-04-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, YearCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, YearCommand.MESSAGE_USAGE));
    }
}
