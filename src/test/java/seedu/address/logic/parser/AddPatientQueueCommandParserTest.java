package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.AddPatientQueueCommand;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

public class AddPatientQueueCommandParserTest {
    private AddPatientQueueCommandParser parser = new AddPatientQueueCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPatientQueueCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAddPatientQueueCommand() {
        AddPatientQueueCommand expectedCommand = new AddPatientQueueCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        assertParseSuccess(parser, "Alice", expectedCommand);

        assertParseSuccess(parser, "\n Alice \n", expectedCommand);
    }
}
