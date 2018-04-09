package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.ENTRY_TITLE_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.ENTRY_TITLE_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENTRY_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_LATER_THAN_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_LATER_THAN_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_LESS_THAN_FIFTEEN_MINUTES_FROM_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEET_BOSS;
import static seedu.address.logic.parser.AddEntryCommandParser.STANDARD_START_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.util.EntryTimeConstraintsUtil;
import seedu.address.logic.commands.AddEntryCommand;
import seedu.address.model.event.CalendarEntry;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.EntryTitle;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;
import seedu.address.testutil.CalendarEntryBuilder;

//@@author SuxianAlicia
public class AddEntryCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEntryCommand.MESSAGE_USAGE);

    private AddEntryCommandParser parser = new AddEntryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        CalendarEntry expectedCalEvent = new CalendarEntryBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS)
                .withStartDate(VALID_START_DATE_MEET_BOSS)
                .withEndDate(VALID_END_DATE_MEET_BOSS)
                .withStartTime(VALID_START_TIME_MEET_BOSS)
                .withEndTime(VALID_END_TIME_MEET_BOSS).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                        + START_DATE_DESC_MEET_BOSS + ENTRY_TITLE_DESC_MEET_BOSS
                        + END_DATE_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));

        // multiple event title strings - last event title string accepted
        assertParseSuccess(parser, ENTRY_TITLE_DESC_GET_STOCKS + START_DATE_DESC_MEET_BOSS
                        + ENTRY_TITLE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));

        // multiple start date strings - last start date string accepted
        assertParseSuccess(parser, START_DATE_DESC_GET_STOCKS + START_DATE_DESC_MEET_BOSS
                        + ENTRY_TITLE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));

        // multiple end date strings - last end date string accepted
        assertParseSuccess(parser, END_DATE_DESC_GET_STOCKS + START_DATE_DESC_MEET_BOSS
                        + ENTRY_TITLE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));

        // multiple start time strings - last start time string accepted
        assertParseSuccess(parser, START_TIME_DESC_GET_STOCKS + START_DATE_DESC_MEET_BOSS
                        + ENTRY_TITLE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));

        // multiple end time strings - last end time string accepted
        assertParseSuccess(parser, END_TIME_DESC_GET_STOCKS + START_DATE_DESC_MEET_BOSS
                        + ENTRY_TITLE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // No start Date - Start Date should match End Date
        CalendarEntry expectedCalEvent = new CalendarEntryBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS)
                .withStartDate(VALID_END_DATE_MEET_BOSS)
                .withEndDate(VALID_END_DATE_MEET_BOSS)
                .withStartTime(VALID_START_TIME_MEET_BOSS)
                .withEndTime(VALID_END_TIME_MEET_BOSS).build();


        assertParseSuccess(parser, ENTRY_TITLE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));

        // No Start Time - Start Time equals to 00:00
        expectedCalEvent = new CalendarEntryBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS)
                .withStartDate(VALID_START_DATE_MEET_BOSS)
                .withEndDate(VALID_END_DATE_MEET_BOSS)
                .withStartTime(STANDARD_START_TIME)
                .withEndTime(VALID_END_TIME_MEET_BOSS).build();


        assertParseSuccess(parser, ENTRY_TITLE_DESC_MEET_BOSS + START_DATE_DESC_MEET_BOSS
                        + END_DATE_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));

        // No Start Date and No Start Time
        expectedCalEvent = new CalendarEntryBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS)
                .withStartDate(VALID_END_DATE_MEET_BOSS)
                .withEndDate(VALID_END_DATE_MEET_BOSS)
                .withStartTime(STANDARD_START_TIME)
                .withEndTime(VALID_END_TIME_MEET_BOSS).build();


        assertParseSuccess(parser, ENTRY_TITLE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // Missing Event Title prefix
        assertParseFailure(parser,  VALID_ENTRY_TITLE_MEET_BOSS
                        + START_DATE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + END_TIME_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS,
                MESSAGE_INVALID_FORMAT);

        // Missing End Date prefix
        assertParseFailure(parser,  ENTRY_TITLE_DESC_MEET_BOSS
                        + START_DATE_DESC_MEET_BOSS + VALID_END_DATE_MEET_BOSS
                        + END_TIME_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS,
                MESSAGE_INVALID_FORMAT);

        // Missing End Time prefix
        assertParseFailure(parser,  ENTRY_TITLE_DESC_MEET_BOSS
                        + START_DATE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + VALID_END_TIME_MEET_BOSS + START_TIME_DESC_MEET_BOSS,
                MESSAGE_INVALID_FORMAT);
        // All prefixes missing
        assertParseFailure(parser,  VALID_ENTRY_TITLE_MEET_BOSS
                        + VALID_START_DATE_MEET_BOSS + VALID_END_DATE_MEET_BOSS
                        + VALID_END_TIME_MEET_BOSS + VALID_START_TIME_MEET_BOSS,
                MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid Event Title
        assertParseFailure(parser,  INVALID_ENTRY_TITLE_DESC
                        + START_DATE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + END_TIME_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS,
                EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS);

        // Invalid Start Date
        assertParseFailure(parser,  ENTRY_TITLE_DESC_MEET_BOSS
                        + INVALID_START_DATE_DESC + END_DATE_DESC_MEET_BOSS
                        + END_TIME_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS,
                StartDate.MESSAGE_START_DATE_CONSTRAINTS);

        // Invalid End Date
        assertParseFailure(parser,  ENTRY_TITLE_DESC_MEET_BOSS
                        + START_DATE_DESC_MEET_BOSS + INVALID_END_DATE_DESC
                        + END_TIME_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS,
                EndDate.MESSAGE_END_DATE_CONSTRAINTS);

        // Invalid Start Time
        assertParseFailure(parser,  ENTRY_TITLE_DESC_MEET_BOSS
                        + START_DATE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + END_TIME_DESC_MEET_BOSS + INVALID_START_TIME_DESC,
                StartTime.MESSAGE_START_TIME_CONSTRAINTS);

        // Invalid End Time
        assertParseFailure(parser,  ENTRY_TITLE_DESC_MEET_BOSS
                        + START_DATE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + INVALID_END_TIME_DESC + START_TIME_DESC_MEET_BOSS,
                EndTime.MESSAGE_END_TIME_CONSTRAINTS);

        // Start Date later than End Date
        assertParseFailure(parser, ENTRY_TITLE_DESC_MEET_BOSS + INVALID_START_DATE_LATER_THAN_END_DATE_DESC
                        + END_DATE_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                EntryTimeConstraintsUtil.START_AND_END_DATE_CONSTRAINTS);

        // Start Time later than End Time for same Start Date and End Date
        assertParseFailure(parser, ENTRY_TITLE_DESC_MEET_BOSS + START_DATE_DESC_MEET_BOSS
                        + END_DATE_DESC_MEET_BOSS + INVALID_START_TIME_LATER_THAN_END_TIME_DESC
                        + END_TIME_DESC_MEET_BOSS,
                EntryTimeConstraintsUtil.START_AND_END_TIME_CONSTRAINTS);

        // Start Time less than 15 minutes from End Time for same Start Date and End Date
        assertParseFailure(parser, ENTRY_TITLE_DESC_MEET_BOSS + START_DATE_DESC_MEET_BOSS
                        + END_DATE_DESC_MEET_BOSS + INVALID_START_TIME_LESS_THAN_FIFTEEN_MINUTES_FROM_END_TIME_DESC
                        + END_TIME_DESC_MEET_BOSS,
                EntryTimeConstraintsUtil.ENTRY_DURATION_CONSTRAINTS);
    }
}
