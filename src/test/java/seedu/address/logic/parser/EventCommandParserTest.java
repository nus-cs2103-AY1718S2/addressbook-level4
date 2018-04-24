package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.logictestutil.EventTestConstants.AFTEREND_START_DATETIME_CCA;
import static seedu.address.logic.logictestutil.EventTestConstants.AFTEREND_START_DATETIME_DESC_CCA;
import static seedu.address.logic.logictestutil.EventTestConstants.END_DATETIME_DESC_CCA;
import static seedu.address.logic.logictestutil.EventTestConstants.INVALID_EVENT_END_DATETIME_DESC;
import static seedu.address.logic.logictestutil.EventTestConstants.INVALID_EVENT_LOCATION_DESC;
import static seedu.address.logic.logictestutil.EventTestConstants.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.logictestutil.EventTestConstants.INVALID_EVENT_REMARK_DESC;
import static seedu.address.logic.logictestutil.EventTestConstants.INVALID_EVENT_START_DATETIME_DESC;
import static seedu.address.logic.logictestutil.EventTestConstants.INVALID_EVENT_TAG_DESC;
import static seedu.address.logic.logictestutil.EventTestConstants.LOCATION_DESC_CCA;
import static seedu.address.logic.logictestutil.EventTestConstants.NAME_DESC_CAMP;
import static seedu.address.logic.logictestutil.EventTestConstants.NAME_DESC_CCA;
import static seedu.address.logic.logictestutil.EventTestConstants.REMARK_DESC_CAMP;
import static seedu.address.logic.logictestutil.EventTestConstants.REMARK_DESC_CCA;
import static seedu.address.logic.logictestutil.EventTestConstants.START_DATETIME_DESC_CCA;
import static seedu.address.logic.logictestutil.EventTestConstants.TAG_DESC_CCA;
import static seedu.address.logic.logictestutil.EventTestConstants.TAG_DESC_IMPORTANT;
import static seedu.address.logic.logictestutil.EventTestConstants.VALID_END_DATETIME_CCA;
import static seedu.address.logic.logictestutil.EventTestConstants.VALID_LOCATION_CCA;
import static seedu.address.logic.logictestutil.EventTestConstants.VALID_NAME_CCA;
import static seedu.address.logic.logictestutil.EventTestConstants.VALID_REMARK_CCA;
import static seedu.address.logic.logictestutil.EventTestConstants.VALID_START_DATETIME_CCA;
import static seedu.address.logic.logictestutil.EventTestConstants.VALID_TAG_CCA;
import static seedu.address.logic.logictestutil.EventTestConstants.VALID_TAG_IMPORTANT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.EventCommandParser.MESSAGE_INVALID_TIME_RANGE;

import org.junit.Test;

import seedu.address.logic.commands.EventCommand;
import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Location;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EventBuilder;

//@@author Kyomian
public class EventCommandParserTest {
    private EventCommandParser parser = new EventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder().withName(VALID_NAME_CCA).withStartDateTime(VALID_START_DATETIME_CCA)
                .withEndDateTime(VALID_END_DATETIME_CCA).withLocation(VALID_LOCATION_CCA).withRemark(VALID_REMARK_CCA)
                .withTags(VALID_TAG_CCA).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_CCA + START_DATETIME_DESC_CCA
                + END_DATETIME_DESC_CCA + LOCATION_DESC_CCA + REMARK_DESC_CCA + TAG_DESC_CCA,
                new EventCommand(expectedEvent));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_CAMP + NAME_DESC_CCA + START_DATETIME_DESC_CCA
                        + END_DATETIME_DESC_CCA + LOCATION_DESC_CCA + REMARK_DESC_CCA + TAG_DESC_CCA,
                new EventCommand(expectedEvent));


        // multiple remarks - last remark accepted
        assertParseSuccess(parser, NAME_DESC_CCA + START_DATETIME_DESC_CCA
                        + END_DATETIME_DESC_CCA + LOCATION_DESC_CCA
                        + REMARK_DESC_CAMP + REMARK_DESC_CCA + TAG_DESC_CCA,
                new EventCommand(expectedEvent));

        // multiple tags - all accepted
        Event expectedEventMultipleTags = new EventBuilder().withName(VALID_NAME_CCA)
                .withStartDateTime(VALID_START_DATETIME_CCA).withEndDateTime(VALID_END_DATETIME_CCA)
                .withLocation(VALID_LOCATION_CCA).withRemark(VALID_REMARK_CCA)
                .withTags(VALID_TAG_CCA, VALID_TAG_IMPORTANT).build();
        assertParseSuccess(parser, NAME_DESC_CCA + START_DATETIME_DESC_CCA
                        + END_DATETIME_DESC_CCA + LOCATION_DESC_CCA
                        + REMARK_DESC_CCA + TAG_DESC_CCA + TAG_DESC_IMPORTANT,
                new EventCommand(expectedEventMultipleTags));
    }

    @Test
    public void parse_optionalLocationMissing_success() {
        Event expectedEvent = new EventBuilder().withName(VALID_NAME_CCA).withStartDateTime(VALID_START_DATETIME_CCA)
                .withEndDateTime(VALID_END_DATETIME_CCA).withLocation().withRemark(VALID_REMARK_CCA)
                .withTags(VALID_TAG_CCA).build();
        assertParseSuccess(parser, NAME_DESC_CCA + START_DATETIME_DESC_CCA + END_DATETIME_DESC_CCA
                        + REMARK_DESC_CCA + TAG_DESC_CCA,
                new EventCommand(expectedEvent));
    }

    @Test
    public void parse_optionalRemarkMissing_success() {
        Event expectedEvent = new EventBuilder().withName(VALID_NAME_CCA).withStartDateTime(VALID_START_DATETIME_CCA)
                .withEndDateTime(VALID_END_DATETIME_CCA).withLocation(VALID_LOCATION_CCA).withRemark()
                .withTags(VALID_TAG_CCA).build();
        assertParseSuccess(parser, NAME_DESC_CCA + START_DATETIME_DESC_CCA + END_DATETIME_DESC_CCA
                        + LOCATION_DESC_CCA + TAG_DESC_CCA,
                new EventCommand(expectedEvent));
    }

    @Test
    public void parse_optionalTagsMissing_success() {
        Event expectedEvent = new EventBuilder().withName(VALID_NAME_CCA).withStartDateTime(VALID_START_DATETIME_CCA)
                .withEndDateTime(VALID_END_DATETIME_CCA).withLocation(VALID_LOCATION_CCA).withRemark(VALID_REMARK_CCA)
                .withTags().build();
        assertParseSuccess(parser, NAME_DESC_CCA + START_DATETIME_DESC_CCA + END_DATETIME_DESC_CCA
                + LOCATION_DESC_CCA + REMARK_DESC_CCA, new EventCommand(expectedEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_CCA + START_DATETIME_DESC_CCA + END_DATETIME_DESC_CCA
                + LOCATION_DESC_CCA + REMARK_DESC_CCA, expectedMessage);

        // missing start datetime prefix
        assertParseFailure(parser, NAME_DESC_CCA + VALID_START_DATETIME_CCA + END_DATETIME_DESC_CCA
                + LOCATION_DESC_CCA + REMARK_DESC_CCA, expectedMessage);

        // missing end datetime prefix
        assertParseFailure(parser, NAME_DESC_CCA + START_DATETIME_DESC_CCA + VALID_END_DATETIME_CCA
                + LOCATION_DESC_CCA + REMARK_DESC_CCA, expectedMessage);
    }

    @Test
    public void parse_startDateTimeAfterEndDateTime_failure() {
        Event expectedEvent = new EventBuilder().withName(VALID_NAME_CCA)
                .withStartDateTime(AFTEREND_START_DATETIME_CCA)
                .withEndDateTime(VALID_END_DATETIME_CCA)
                .withLocation(VALID_LOCATION_CCA).withRemark(VALID_REMARK_CCA)
                .withTags(VALID_TAG_CCA).build();

        assertParseFailure(parser, NAME_DESC_CCA + AFTEREND_START_DATETIME_DESC_CCA + END_DATETIME_DESC_CCA
                + LOCATION_DESC_CCA + REMARK_DESC_CCA + TAG_DESC_CCA, MESSAGE_INVALID_TIME_RANGE);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + START_DATETIME_DESC_CCA
                + END_DATETIME_DESC_CCA + LOCATION_DESC_CCA + REMARK_DESC_CCA + TAG_DESC_CCA,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid start datetime
        assertParseFailure(parser, NAME_DESC_CCA + INVALID_EVENT_START_DATETIME_DESC
                        + END_DATETIME_DESC_CCA + LOCATION_DESC_CCA + REMARK_DESC_CCA + TAG_DESC_CCA,
                DateTime.MESSAGE_DATETIME_CONSTRAINTS);

        // invalid end datetime
        assertParseFailure(parser, NAME_DESC_CCA + START_DATETIME_DESC_CCA
                        + INVALID_EVENT_END_DATETIME_DESC + LOCATION_DESC_CCA + REMARK_DESC_CCA + TAG_DESC_CCA,
                DateTime.MESSAGE_DATETIME_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, NAME_DESC_CCA + START_DATETIME_DESC_CCA
                + END_DATETIME_DESC_CCA + INVALID_EVENT_LOCATION_DESC + REMARK_DESC_CCA + TAG_DESC_CCA,
                Location.MESSAGE_LOCATION_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, NAME_DESC_CCA + START_DATETIME_DESC_CCA
                + END_DATETIME_DESC_CCA + LOCATION_DESC_CCA + INVALID_EVENT_REMARK_DESC + TAG_DESC_CCA,
                Remark.MESSAGE_REMARK_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_CCA + START_DATETIME_DESC_CCA
                + END_DATETIME_DESC_CCA + LOCATION_DESC_CCA + REMARK_DESC_CCA + INVALID_EVENT_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + INVALID_EVENT_START_DATETIME_DESC
                        + END_DATETIME_DESC_CCA + LOCATION_DESC_CCA + REMARK_DESC_CCA + TAG_DESC_CCA,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_CCA + START_DATETIME_DESC_CCA
                        + END_DATETIME_DESC_CCA + LOCATION_DESC_CCA + REMARK_DESC_CCA + TAG_DESC_CCA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventCommand.MESSAGE_USAGE));
    }

}
