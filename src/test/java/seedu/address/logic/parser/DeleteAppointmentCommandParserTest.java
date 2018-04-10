//@@author Kyholmes-test
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

public class DeleteAppointmentCommandParserTest {
    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_empty_throwParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnDeleteAppointmentCommand() throws IllegalValueException {
        DeleteAppointmentCommand expectedCommand = new DeleteAppointmentCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "1")), ParserUtil.parseIndex("1"));
        assertParseSuccess(parser, "Alice 1", expectedCommand);
        assertParseSuccess(parser, "\n Alice 1 \n", expectedCommand);
    }
}
