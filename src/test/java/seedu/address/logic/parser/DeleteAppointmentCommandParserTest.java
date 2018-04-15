package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ENDTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PERSONNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PERSONNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STARTTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERSONNAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.EndTime;
import seedu.address.model.appointment.Location;
import seedu.address.model.appointment.PersonName;
import seedu.address.model.appointment.StartTime;
import seedu.address.testutil.AppointmentBuilder;

//@@author jlks96
public class DeleteAppointmentCommandParserTest {

    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Appointment appointmentToDelete = new AppointmentBuilder().withPersonName(VALID_PERSONNAME)
                .withDate(VALID_DATE).withStartTime(VALID_STARTTIME).withEndTime(VALID_ENDTIME)
                .withLocation(VALID_LOCATION).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC
                + ENDTIME_DESC + LOCATION_DESC, new DeleteAppointmentCommand(appointmentToDelete));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_PERSONNAME + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, PERSONNAME_DESC + VALID_DATE + STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                expectedMessage);

        // missing start time prefix
        assertParseFailure(parser, PERSONNAME_DESC + DATE_DESC + VALID_STARTTIME + ENDTIME_DESC + LOCATION_DESC,
                expectedMessage);

        // missing end time prefix
        assertParseFailure(parser, PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + VALID_ENDTIME + LOCATION_DESC,
                expectedMessage);

        // missing location prefix
        assertParseFailure(parser, PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + VALID_LOCATION,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_PERSONNAME + VALID_DATE + VALID_STARTTIME + VALID_ENDTIME + VALID_LOCATION,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid person name
        assertParseFailure(parser,
                INVALID_PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                PersonName.MESSAGE_NAME_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser,
                PERSONNAME_DESC + INVALID_DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                Date.MESSAGE_DATE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser,
                PERSONNAME_DESC + DATE_DESC + INVALID_STARTTIME_DESC + ENDTIME_DESC + LOCATION_DESC,
                StartTime.MESSAGE_TIME_CONSTRAINTS);

        // invalid end time
        assertParseFailure(parser,
                PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + INVALID_ENDTIME_DESC + LOCATION_DESC,
                EndTime.MESSAGE_TIME_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser,
                PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + INVALID_LOCATION_DESC,
                Location.MESSAGE_LOCATION_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
                INVALID_PERSONNAME_DESC + DATE_DESC + STARTTIME_DESC + ENDTIME_DESC + INVALID_LOCATION_DESC,
                PersonName.MESSAGE_NAME_CONSTRAINTS);
    }
}
