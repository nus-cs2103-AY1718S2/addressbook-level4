package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.model.CalendarEvent.EventEndDate.INVALID_END_DATE_MESSAGE;
import static seedu.address.model.CalendarEvent.EventEndTime.INVALID_END_TIME_MESSAGE;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CalendarEvent.CalendarEvent;
import seedu.address.model.CalendarEvent.EventEndDate;
import seedu.address.model.CalendarEvent.EventEndTime;
import seedu.address.model.CalendarEvent.EventName;
import seedu.address.model.CalendarEvent.EventStartDate;
import seedu.address.model.CalendarEvent.EventStartTime;
import seedu.address.testutil.EventBuilder;

//@@author wayneong95

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        CalendarEvent expectedEvent = new EventBuilder().withEventName(VALID_EVENT_NAME)
                .withStartDate(VALID_START_DATE)
                .withStartTime(VALID_START_TIME)
                .withEndDate(VALID_END_DATE)
                .withEndTime(VALID_END_TIME)
                .build();
        try {
            Command actual = parser.parse(PREAMBLE_WHITESPACE + EVENT_NAME_DESC + EVENT_START_DATE_DESC
                    + EVENT_START_TIME_DESC + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC);
            Command expected = new AddEventCommand(expectedEvent);
            expected.equals(actual);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_invalidEndDate_failure() {
        String expectedMessage = String.format(INVALID_END_DATE_MESSAGE);
        //end date earlier than start date
        assertParseFailure(parser, EVENT_NAME_DESC + " sd/2018-05-20" + EVENT_START_TIME_DESC
                + " ed/2018-05-19" + EVENT_END_TIME_DESC, expectedMessage);
    }

    @Test
    public void parse_invalidEndTime_failure() {
        String expectedMessage = String.format(INVALID_END_TIME_MESSAGE);
        //end time earlier than start time
        assertParseFailure(parser, EVENT_NAME_DESC + EVENT_START_DATE_DESC + " st/11:30"
                + EVENT_END_DATE_DESC + " et/10:30", expectedMessage);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing eventName prefix
        assertParseFailure(parser, VALID_EVENT_NAME + EVENT_START_DATE_DESC + EVENT_START_TIME_DESC
                        + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC, expectedMessage);

        // missing eventStartDate prefix
        assertParseFailure(parser, EVENT_NAME_DESC + VALID_START_DATE + EVENT_START_TIME_DESC
                + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC, expectedMessage);

        // missing eventStartTime prefix
        assertParseFailure(parser, EVENT_NAME_DESC + EVENT_START_DATE_DESC + VALID_START_TIME
                + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC, expectedMessage);

        // missing eventEndDate prefix
        assertParseFailure(parser, EVENT_NAME_DESC + EVENT_START_DATE_DESC + EVENT_START_TIME_DESC
                + VALID_END_DATE + EVENT_END_TIME_DESC, expectedMessage);

        // missing eventEndTime prefix
        assertParseFailure(parser, EVENT_NAME_DESC + EVENT_START_DATE_DESC + EVENT_START_TIME_DESC
                + EVENT_END_DATE_DESC + VALID_END_TIME, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EVENT_NAME + VALID_START_DATE + VALID_START_TIME
                + VALID_END_DATE + VALID_END_TIME, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid eventName
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + EVENT_START_DATE_DESC
                + EVENT_START_TIME_DESC + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC,
                EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        // invalid eventStartDate
        assertParseFailure(parser, EVENT_NAME_DESC + INVALID_START_DATE_DESC
                        + EVENT_START_TIME_DESC + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC,
                EventStartDate.MESSAGE_START_DATE_CONSTRAINTS);

        // invalid eventStartTime
        assertParseFailure(parser, EVENT_NAME_DESC + EVENT_START_DATE_DESC
                        + INVALID_START_TIME_DESC + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC,
                EventStartTime.MESSAGE_START_TIME_CONSTRAINTS);

        // invalid eventEndDate
        assertParseFailure(parser, EVENT_NAME_DESC + EVENT_START_DATE_DESC
                        + EVENT_START_TIME_DESC + INVALID_END_DATE_DESC + EVENT_END_TIME_DESC,
                EventEndDate.MESSAGE_END_DATE_CONSTRAINTS);

        // invalid eventEndTime
        assertParseFailure(parser, EVENT_NAME_DESC + EVENT_START_DATE_DESC
                        + EVENT_START_TIME_DESC + EVENT_END_DATE_DESC + INVALID_END_TIME_DESC,
                EventEndTime.MESSAGE_END_TIME_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + INVALID_START_DATE_DESC
                        + EVENT_START_TIME_DESC + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC,
                EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_NAME_DESC + EVENT_START_DATE_DESC
                        + EVENT_START_TIME_DESC + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
