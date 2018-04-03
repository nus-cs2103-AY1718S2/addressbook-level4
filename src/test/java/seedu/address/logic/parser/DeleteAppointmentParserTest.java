package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_TIME_DESC_INTERVIEW1;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_TIME_DESC_INTERVIEW2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_TIME_DESC_INTERVIEW1;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_TIME_DESC_INTERVIEW2;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_INTERVIEW1;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_INTERVIEW2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_TIME_INTERVIEW2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_TIME_INTERVIEW2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_INTERVIEW2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.appointment.DeleteAppointmentCommand;
import seedu.address.logic.parser.appointment.DeleteAppointmentCommandParser;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.EndDateTime;
import seedu.address.model.appointment.StartDateTime;
import seedu.address.model.appointment.Title;
import seedu.address.testutil.AppointmentBuilder;

//@@author trafalgarandre
public class DeleteAppointmentParserTest {
    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Appointment expectedAppointment = new AppointmentBuilder().withTitle(VALID_TITLE_INTERVIEW2)
                .withStartDateTime(VALID_START_DATE_TIME_INTERVIEW2).withEndDateTime(VALID_END_DATE_TIME_INTERVIEW2)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_INTERVIEW2
                        + START_DATE_TIME_DESC_INTERVIEW2 + END_DATE_TIME_DESC_INTERVIEW2,
                new DeleteAppointmentCommand(expectedAppointment));

        // multiple title - last title accepted
        assertParseSuccess(parser, TITLE_DESC_INTERVIEW1 + TITLE_DESC_INTERVIEW2
                        + START_DATE_TIME_DESC_INTERVIEW2 + END_DATE_TIME_DESC_INTERVIEW2,
                new DeleteAppointmentCommand(expectedAppointment));

        // multiple start date time - last start date time accepted
        assertParseSuccess(parser, TITLE_DESC_INTERVIEW2 + START_DATE_TIME_DESC_INTERVIEW1
                        + START_DATE_TIME_DESC_INTERVIEW2 + END_DATE_TIME_DESC_INTERVIEW2,
                new DeleteAppointmentCommand(expectedAppointment));

        // multiple end date time - last end date time accepted
        assertParseSuccess(parser, TITLE_DESC_INTERVIEW2 + START_DATE_TIME_DESC_INTERVIEW2
                        + END_DATE_TIME_DESC_INTERVIEW1 + END_DATE_TIME_DESC_INTERVIEW2,
                new DeleteAppointmentCommand(expectedAppointment));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_TITLE_INTERVIEW2 + START_DATE_TIME_DESC_INTERVIEW2
                + END_DATE_TIME_DESC_INTERVIEW2, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, TITLE_DESC_INTERVIEW2 + VALID_START_DATE_TIME_INTERVIEW2
                + END_DATE_TIME_DESC_INTERVIEW2, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, TITLE_DESC_INTERVIEW2 + START_DATE_TIME_DESC_INTERVIEW2
                + VALID_END_DATE_TIME_INTERVIEW2, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_INTERVIEW2 + VALID_START_DATE_TIME_INTERVIEW2
                + VALID_END_DATE_TIME_INTERVIEW2, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + START_DATE_TIME_DESC_INTERVIEW2
                + END_DATE_TIME_DESC_INTERVIEW2, Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid start date time
        assertParseFailure(parser, TITLE_DESC_INTERVIEW2 + INVALID_START_DATE_TIME
                + END_DATE_TIME_DESC_INTERVIEW2, StartDateTime.MESSAGE_START_DATE_TIME_CONSTRAINTS);

        // invalid end date time
        assertParseFailure(parser, TITLE_DESC_INTERVIEW2 + START_DATE_TIME_DESC_INTERVIEW2
                + INVALID_END_DATE_TIME, EndDateTime.MESSAGE_END_DATE_TIME_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + START_DATE_TIME_DESC_INTERVIEW2
                + END_DATE_TIME_DESC_INTERVIEW2, Title.MESSAGE_TITLE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TITLE_DESC_INTERVIEW2
                        + START_DATE_TIME_DESC_INTERVIEW2 + END_DATE_TIME_DESC_INTERVIEW2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE));
    }
}
