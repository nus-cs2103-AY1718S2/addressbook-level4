//@@author Kyholmes-test
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPatientQueueCommand;

public class AddPatientQueueCommandParserTest {
    private AddPatientQueueCommandParser parser = new AddPatientQueueCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPatientQueueCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsString_throwsParseException() {
        assertParseFailure(parser, "alice", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPatientQueueCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsNegativeIndex_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPatientQueueCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAddPatientQueueCommand() throws IllegalValueException {
        AddPatientQueueCommand expectedCommand = new AddPatientQueueCommand(ParserUtil.parseIndex("1"));
        assertParseSuccess(parser, "1", expectedCommand);

        assertParseSuccess(parser, "\n 1 \n", expectedCommand);
    }
}
