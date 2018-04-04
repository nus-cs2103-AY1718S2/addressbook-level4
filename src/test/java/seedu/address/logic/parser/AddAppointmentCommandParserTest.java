package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

public class AddAppointmentCommandParserTest {
    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAddAppointmentCommand() {
        AddAppointmentCommand expectedCommand = new AddAppointmentCommand(new NameContainsKeywordsPredicate(
                Arrays.asList("Alice", "2/4/2018", "1030")), "2/4/2018", "1030");
        assertParseSuccess(parser, "Alice 2/4/2018 1030", expectedCommand);
        assertParseSuccess(parser, "\n Alice 2/4/2018 1030 \n", expectedCommand);
    }
}
