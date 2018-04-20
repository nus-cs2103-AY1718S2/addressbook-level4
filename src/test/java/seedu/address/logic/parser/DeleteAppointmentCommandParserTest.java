//@@author Kyholmes-test
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteAppointmentCommand;

public class DeleteAppointmentCommandParserTest {
    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_empty_throwParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTime_throwParseException() {
        assertParseFailure(parser, "1 14/3/2018", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingDate_throwParseException() {
        assertParseFailure(parser, "1 1200", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwParseException() {
        assertParseFailure(parser, "14/3/2018 1200", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwParseException() {
        assertParseFailure(parser, "alex 14/3/2018 1200", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDate_throwParseException() {
        assertParseFailure(parser, "1 14/three/2018 1200", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTime_throwParseException() {
        assertParseFailure(parser, "1 14/3/2018 12pm", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_dateTimeArgSwap_throwParseException() {
        assertParseFailure(parser, "1 1200 14/3/2018", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnDeleteAppointmentCommand() throws IllegalValueException {
        DeleteAppointmentCommand expectedCommand = new DeleteAppointmentCommand(
                ParserUtil.parseIndex("1"), ParserUtil.parseDateTime("14/3/2018 1200"));
        assertParseSuccess(parser, "1 14/3/2018 1200", expectedCommand);
        assertParseSuccess(parser, "\n 1 14/3/2018 1200 \n", expectedCommand);
    }
}
