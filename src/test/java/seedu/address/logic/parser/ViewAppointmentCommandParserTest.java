//@@author Kyholmes-test
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.ViewAppointmentCommand;

public class ViewAppointmentCommandParserTest {

    private ViewAppointmentCommandParser parser = new ViewAppointmentCommandParser();

    @Test
    public void parse_invalidArgsString_throwsParseException() {
        assertParseFailure(parser, "alice", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAppointmentCommand.MESSAGE_USAGE_PATIENT_WITH_INDEX));
    }

    @Test
    public void parse_invalidArgsNegativeIndex_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAppointmentCommand.MESSAGE_USAGE_PATIENT_WITH_INDEX));
    }

    @Test
    public void parse_validArgs_returnsViewAppointmentCommand() {
        ViewAppointmentCommand expectedCommand = new ViewAppointmentCommand(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", expectedCommand);
        assertParseSuccess(parser, "\n 1 \n", expectedCommand);
    }
}
