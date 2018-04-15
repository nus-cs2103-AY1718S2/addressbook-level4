//@@author Kyholmes-test
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
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
    public void parse_validArgs_returnsAddAppointmentCommand() throws IllegalValueException {
        AddAppointmentCommand expectedCommand = new AddAppointmentCommand(INDEX_FIRST_PERSON,
                ParserUtil.parseDateTime("2/4/2108 1030"));
        assertParseSuccess(parser, "1 2/4/2108 1030", expectedCommand);
        assertParseSuccess(parser, "\n 1 2/4/2108 1030 \n", expectedCommand);
    }
}
