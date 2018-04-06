package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.appointment.DateTimeCommand;
import seedu.address.logic.parser.appointment.DateTimeCommandParser;

//@@author trafalgarandre
public class DateTimeCommandParserTest {

    private DateTimeCommandParser parser = new DateTimeCommandParser();

    @Test
    public void parse_validArgs_returnsDateTimeCommand() throws IllegalValueException {
        assertParseSuccess(parser, "2018-04-01 12:00",
                new DateTimeCommand(ParserUtil.parseDateTime("2018-04-01 12:00")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "2018-04-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateTimeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "01-04-2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateTimeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateTimeCommand.MESSAGE_USAGE));
    }
}
