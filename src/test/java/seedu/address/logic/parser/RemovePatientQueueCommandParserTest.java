//@@author Kyholmes-test
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.RemovePatientQueueCommand;

public class RemovePatientQueueCommandParserTest {
    private RemovePatientQueueCommandParser parser = new RemovePatientQueueCommandParser();

    @Test
    public void parse_validArgs_returnsRemovePatientQueueCommand() {
        assertParseSuccess(parser, "1", new RemovePatientQueueCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgsNegativeValue_throwsParserException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemovePatientQueueCommand.MESSAGE_USAGE_INDEX));
    }

    @Test
    public void parse_invalidArgsAlphaValue_throwsParserException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemovePatientQueueCommand.MESSAGE_USAGE_INDEX));
    }
}
