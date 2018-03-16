package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_JOHNNY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_TIMMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_OWNER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.OWNER_DESC_JOHNNY;
import static seedu.address.logic.commands.CommandTestUtil.OWNER_DESC_TIMMY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_JOHNNY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_TIMMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHECKUP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_VACCINATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_JOHNNY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OWNER_JOHNNY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_JOHNNY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHECKUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_VACCINATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.parser.AddAppointmentCommandParser;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Remark;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AppointmentBuilder;

public class AddAppointmentCommandParserTest {
    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Appointment expectedAppointment = new AppointmentBuilder().withOwner(VALID_OWNER_JOHNNY).withRemark(VALID_REMARK_JOHNNY)
                .withDateTime(VALID_DATE_JOHNNY).withTags(VALID_TAG_CHECKUP).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + OWNER_DESC_JOHNNY + REMARK_DESC_JOHNNY
                + DATE_DESC_JOHNNY + TAG_DESC_CHECKUP, new AddAppointmentCommand(expectedAppointment));

        // multiple owners - last owner accepted
        assertParseSuccess(parser,  OWNER_DESC_TIMMY + OWNER_DESC_JOHNNY + REMARK_DESC_JOHNNY
                + DATE_DESC_JOHNNY + TAG_DESC_CHECKUP, new AddAppointmentCommand(expectedAppointment));

        // multiple remarks - last remark accepted
        assertParseSuccess(parser,  OWNER_DESC_JOHNNY + REMARK_DESC_TIMMY + REMARK_DESC_JOHNNY
                + DATE_DESC_JOHNNY + TAG_DESC_CHECKUP, new AddAppointmentCommand(expectedAppointment));

        // multiple dates - last date accepted
        assertParseSuccess(parser, OWNER_DESC_JOHNNY + REMARK_DESC_JOHNNY + DATE_DESC_TIMMY
                + DATE_DESC_JOHNNY + TAG_DESC_CHECKUP, new AddAppointmentCommand(expectedAppointment));

        // multiple tags - all accepted
        Appointment expectedAppointmentMultipleTags = new AppointmentBuilder().withOwner(VALID_OWNER_JOHNNY)
                .withRemark(VALID_REMARK_JOHNNY)
                .withDateTime(VALID_DATE_JOHNNY).withTags(VALID_TAG_CHECKUP, VALID_TAG_VACCINATION).build();
        assertParseSuccess(parser, OWNER_DESC_JOHNNY + REMARK_DESC_JOHNNY + DATE_DESC_JOHNNY
                + TAG_DESC_CHECKUP + TAG_DESC_VACCINATION, new AddAppointmentCommand(expectedAppointmentMultipleTags));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

        // missing owner prefix
        assertParseFailure(parser, VALID_OWNER_JOHNNY + REMARK_DESC_JOHNNY
                        + DATE_DESC_JOHNNY + TAG_DESC_CHECKUP, expectedMessage);

        // missing remark prefix
        assertParseFailure(parser,  OWNER_DESC_JOHNNY + VALID_REMARK_JOHNNY
                + DATE_DESC_JOHNNY + TAG_DESC_CHECKUP, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, VALID_OWNER_JOHNNY + REMARK_DESC_JOHNNY
                + DATE_DESC_JOHNNY + TAG_DESC_CHECKUP, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_OWNER_JOHNNY + VALID_REMARK_JOHNNY
                + VALID_DATE_JOHNNY + VALID_TAG_CHECKUP, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid owner
        assertParseFailure(parser, INVALID_OWNER_DESC + REMARK_DESC_JOHNNY
                + DATE_DESC_JOHNNY + TAG_DESC_CHECKUP, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, OWNER_DESC_JOHNNY + INVALID_REMARK_DESC + DATE_DESC_JOHNNY
                + TAG_DESC_CHECKUP, Remark.MESSAGE_REMARK_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, OWNER_DESC_JOHNNY + REMARK_DESC_JOHNNY + INVALID_DATE_DESC
                + TAG_DESC_CHECKUP, "Please follow the format of yyyy-MM-dd HH:mm");

        // invalid tag
        assertParseFailure(parser, OWNER_DESC_JOHNNY + REMARK_DESC_JOHNNY + DATE_DESC_JOHNNY
                + INVALID_TAG_DESC + VALID_TAG_CHECKUP, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_OWNER_DESC + INVALID_REMARK_DESC + DATE_DESC_JOHNNY
                + TAG_DESC_CHECKUP, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + OWNER_DESC_JOHNNY + REMARK_DESC_JOHNNY
                + DATE_DESC_JOHNNY + TAG_DESC_CHECKUP,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
    }
}
