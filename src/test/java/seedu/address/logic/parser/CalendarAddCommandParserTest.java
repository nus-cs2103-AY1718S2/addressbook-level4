//@@author philos22
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATETIME_FORMAT;
import static seedu.address.logic.commands.CalendarAddCommand.COMMAND_WORD;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_END_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_START;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.CalendarAddCommand;

/**
 * Testing for CalendarAddCommand
 */
public class CalendarAddCommandParserTest {

    private CalendarAddCommandParser parser = new CalendarAddCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CalendarAddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsCalendarAddCommand() {
        // no leading and trailing whitespaces
        String eventName = "CalendarTest";
        String location = "CalendarLocation";
        String startTime = "2019-01-01 12:00:00";
        String endTime = "2019-01-01 13:00:00";
        CalendarAddCommand expectedCalendarAddCommand =
                new CalendarAddCommand(eventName, location, ParserUtil.parseDateTime(startTime),
                        ParserUtil.parseDateTime(endTime));

        assertParseSuccess(parser,
                "calAdd event/CalendarTest loc/CalendarLocation start/2019-01-01 12:00:00 end/2019-01-01 13:00:00",
                expectedCalendarAddCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                "calAdd \n event/CalendarTest \t \n loc/CalendarLocation \t start/2019-01-01 12:00:00 \n "
                        + "end/2019-01-01 13:00:00", expectedCalendarAddCommand);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarAddCommand.MESSAGE_USAGE);

        //missing event prefix
        assertParseFailure(parser, COMMAND_WORD + " " + VALID_EVENT_NAME + EVENT_LOCATION_DESC
                + EVENT_START_DESC + EVENT_END_DESC, expectedMessage);

        //missing location prefix
        assertParseFailure(parser, COMMAND_WORD + " " + EVENT_NAME_DESC + VALID_EVENT_LOCATION + EVENT_START_DESC
                 + EVENT_END_DESC, expectedMessage);

        //missing startDateTime prefix
        assertParseFailure(parser, COMMAND_WORD + " " + EVENT_NAME_DESC + EVENT_LOCATION_DESC + VALID_EVENT_START
                + EVENT_END_DESC, expectedMessage);

        //missing endDateTime prefix
        assertParseFailure(parser, COMMAND_WORD + " " + EVENT_NAME_DESC + EVENT_LOCATION_DESC + EVENT_START_DESC
                + VALID_EVENT_END, expectedMessage);
    }

    @Test
    public void parse_invalidArgs_failure() {
        //invalid date format - start and end dates are the same scenario
        assertParseFailure(parser, COMMAND_WORD + " " + EVENT_NAME_DESC + EVENT_LOCATION_DESC + INVALID_EVENT_START
                + EVENT_END_DESC, MESSAGE_INVALID_DATETIME_FORMAT);
    }

}
