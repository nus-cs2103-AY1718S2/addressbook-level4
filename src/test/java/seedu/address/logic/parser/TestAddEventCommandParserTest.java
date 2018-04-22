package seedu.address.logic.parser;
//@@author crizyli
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ENDTIME;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_STARTTIME;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_ENDTIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_STARTTIME;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_ENDTIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_STARTTIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.TestAddEventCommand;

//@@author crizyli
public class TestAddEventCommandParserTest {

    private TestAddEventCommandParser parser = new TestAddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        final String expectedTitle = "Test Event";
        final String expectedLocation = "NUS";
        final String expectedStarttime = "2018-05-15T10:00:00";
        final String expectedEndtime = "2018-05-15T12:00:00";
        final String expectedDescription = "A test event.";

        assertParseSuccess(parser, "1" + EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION, new TestAddEventCommand(INDEX_FIRST_PERSON, expectedTitle,
                expectedLocation, expectedStarttime, expectedEndtime, expectedDescription));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "1" + EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION, new TestAddEventCommand(INDEX_FIRST_PERSON, expectedTitle,
                expectedLocation, expectedStarttime, expectedEndtime, expectedDescription));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestAddEventCommand.MESSAGE_USAGE);

        //missing person index
        assertParseFailure(parser, VALID_EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION, expectedMessage);

        //missing title prefix
        assertParseFailure(parser, "1" + VALID_EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION, expectedMessage);

        //missing location prefix
        assertParseFailure(parser, "1" + EVENT_TITLE + VALID_EVENT_LOCATION + EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION, expectedMessage);

        //missing starttime prefix
        assertParseFailure(parser, "1" + EVENT_TITLE + EVENT_LOCATION + VALID_EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION, expectedMessage);

        //missing endtime prefix
        assertParseFailure(parser, "1" + EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + VALID_EVENT_ENDTIME + EVENT_DESCRIPTION, expectedMessage);

        //missing description prefix
        assertParseFailure(parser, "1" + EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + EVENT_ENDTIME + VALID_EVENT_DESCRIPTION, expectedMessage);

        //all prefix missing
        assertParseFailure(parser, "1" + VALID_EVENT_TITLE + VALID_EVENT_LOCATION + VALID_EVENT_STARTTIME
                + VALID_EVENT_ENDTIME + VALID_EVENT_DESCRIPTION, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid start time
        assertParseFailure(parser, "1" + EVENT_TITLE + EVENT_LOCATION + INVALID_EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION, "Invalid date/time format: " + "2018-04-09T08:00");

        // invalid end time
        assertParseFailure(parser, "1" + EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + INVALID_EVENT_ENDTIME + EVENT_DESCRIPTION, "Invalid date/time format: " + "2018-04-09T10");

        //non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "1" + EVENT_TITLE + EVENT_LOCATION + EVENT_STARTTIME
                + EVENT_ENDTIME + EVENT_DESCRIPTION,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestAddEventCommand.MESSAGE_USAGE));
    }
}
