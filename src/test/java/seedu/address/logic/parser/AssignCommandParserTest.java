//@@author melvintzw
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;

public class AssignCommandParserTest {

    private AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_validArgsAndOneCustomer_returnsAssignCommand() {
        assertParseSuccess(parser, "1 c: 2", new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(2)));
    }

    @Test
    public void parse_validArgsAndTwoCustomers_returnsAssignCommand() {
        assertParseSuccess(parser, "1 c: 2 3", new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(2),
                Index.fromOneBased(3)));
    }

    @Test
    public void parse_alphabet_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AssignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertParseFailure(parser, "-1 c: 2 3", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AssignCommand.MESSAGE_USAGE));
    }
}
