//@@author kush1509
package seedu.address.logic.parser.job;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.job.JobMatchCommand;

/**
 * Test scope: similar to {@code SelectCommandParserTest}.
 * @see seedu.address.logic.parser.SelectCommandParserTest
 */
public class JobMatchCommandParserTest {

    private JobMatchCommandParser parser = new JobMatchCommandParser();

    @Test
    public void parse_validArgs_returnsJobMatchCommand() {
        assertParseSuccess(parser, "1", new JobMatchCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobMatchCommand.MESSAGE_USAGE));
    }
}
