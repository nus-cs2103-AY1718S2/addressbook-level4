package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.YearMonth;

import org.junit.Test;

import seedu.address.logic.commands.appointment.MonthCommand;
import seedu.address.logic.parser.appointment.MonthCommandParser;

//@@author trafalgarandre
public class MonthCommandParserTest {

    private MonthCommandParser parser = new MonthCommandParser();

    @Test
    public void parse_validArgs_returnsMonthCommand() {
        assertParseSuccess(parser, "", new MonthCommand(null));
        assertParseSuccess(parser, "2018-04", new MonthCommand(YearMonth.parse("2018-04")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "2018-4",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MonthCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "04-2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MonthCommand.MESSAGE_USAGE));
    }
}
