//@@author LeonidAgarth
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_END_TIME_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_TIME_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_TIME_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_VENUE_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_VENUE_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_NDP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder().withName(VALID_EVENT_NAME_NDP).withVenue(VALID_EVENT_VENUE_NDP)
                .withDate(VALID_EVENT_DATE_NDP).withStartTime(VALID_EVENT_START_TIME_NDP)
                .withEndTime(VALID_EVENT_END_TIME_NDP).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple names - last name accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_F1 + EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple venues - last venue accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_F1 + EVENT_VENUE_DESC_NDP
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple dates - last date accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_F1
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple start times - last time accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_F1 + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEvent));

        // multiple tags - all accepted
        Event expectedEventMultipleTags = new EventBuilder().withName(VALID_EVENT_NAME_NDP)
                .withVenue(VALID_EVENT_VENUE_NDP).withDate(VALID_EVENT_DATE_NDP)
                .withStartTime(VALID_EVENT_START_TIME_NDP).withEndTime(VALID_EVENT_END_TIME_NDP).build();
        assertParseSuccess(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                new AddEventCommand(expectedEventMultipleTags));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_EVENT_NAME_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP, expectedMessage);

        // missing venue prefix
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + VALID_EVENT_VENUE_NDP + EVENT_DATE_DESC_NDP
                + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + VALID_EVENT_DATE_NDP
                + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP, expectedMessage);

        // missing start time prefix
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                + VALID_EVENT_START_TIME_NDP + EVENT_END_TIME_DESC_NDP, expectedMessage);

        // missing end time prefix
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                + EVENT_START_TIME_DESC_NDP + VALID_EVENT_END_TIME_NDP, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EVENT_NAME_NDP + VALID_EVENT_VENUE_NDP + VALID_EVENT_DATE_NDP
                + VALID_EVENT_START_TIME_NDP + VALID_EVENT_END_TIME_NDP, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                Event.MESSAGE_NAME_CONSTRAINTS);

        // invalid venue
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + INVALID_EVENT_VENUE_DESC + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                Event.MESSAGE_VENUE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + INVALID_EVENT_DATE_DESC
                        + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                Event.MESSAGE_DATE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + INVALID_EVENT_START_TIME_DESC + EVENT_END_TIME_DESC_NDP,
                Event.MESSAGE_TIME_CONSTRAINTS);

        // invalid link
        assertParseFailure(parser, EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                        + EVENT_START_TIME_DESC_NDP + INVALID_EVENT_END_TIME_DESC,
                Event.MESSAGE_TIME_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_NDP
                + INVALID_EVENT_START_TIME_DESC + EVENT_END_TIME_DESC_NDP, Event.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_NDP
                        + EVENT_DATE_DESC_NDP + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_NDP,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
