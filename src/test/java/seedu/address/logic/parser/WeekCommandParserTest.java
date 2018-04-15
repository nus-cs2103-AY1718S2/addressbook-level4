package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.Year;

import org.junit.Test;

import seedu.address.logic.commands.appointment.WeekCommand;
import seedu.address.logic.parser.appointment.WeekCommandParser;

//@@author trafalgarandre
public class WeekCommandParserTest {

    private WeekCommandParser parser = new WeekCommandParser();

    @Test
    public void parse_validArgs_returnsDateCommand() {
        assertParseSuccess(parser, "", new WeekCommand(null, 0));
        assertParseSuccess(parser, "2018 03", new WeekCommand(Year.parse("2018"), 3));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, WeekCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "04",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, WeekCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "2018-04",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, WeekCommand.MESSAGE_USAGE));
    }
}
