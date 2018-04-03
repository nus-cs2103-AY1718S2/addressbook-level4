package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.Test;

import seedu.address.logic.commands.appointment.DateCommand;
import seedu.address.logic.parser.appointment.DateCommandParser;

//@@author trafalgarandre
public class DateCommandParserTest {

    private DateCommandParser parser = new DateCommandParser();

    @Test
    public void parse_validArgs_returnsDateCommand() {
        assertParseSuccess(parser, "", new DateCommand(null));
        assertParseSuccess(parser, "2018-04-01", new DateCommand(LocalDate.parse("2018-04-01")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "2018-04-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "01-04-2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE));
    }
}
