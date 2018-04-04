package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_TIME_END_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_TIME_START_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_MEET_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME_END;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME_START;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_JOHN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.model.calendar.AppointmentEntry;
import seedu.address.testutil.TypicalAppointmentEntires;
//@@author yuxiangSg
public class AddAppointmentParserTest {
    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AppointmentEntry expectedEntry = TypicalAppointmentEntires.MEET_JOHN;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_MEET_JOHN + START_DATE_DESC
                + END_DATE_DESC, new AddAppointmentCommand(expectedEntry));
    }

    @Test
    public void parse_fieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, START_DATE_DESC + END_DATE_DESC, expectedMessage);

        // missing start interval prefix
        assertParseFailure(parser, TITLE_DESC_MEET_JOHN + END_DATE_DESC, expectedMessage);

        // missing end interval prefix
        assertParseFailure(parser, TITLE_DESC_MEET_JOHN + START_DATE_DESC,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_JOHN + VALID_DATE_TIME_START + VALID_DATE_TIME_END,
                expectedMessage);
    }

    @Test
    public void parse_invalidDateFormat_failure() {

        assertParseFailure(parser, TITLE_DESC_MEET_JOHN + INVALID_DATE_TIME_START_DESC
                + INVALID_DATE_TIME_END_DESC, AppointmentEntry.MESSAGE_DATE_TIME_CONSTRAINTS);
    }
}
