package seedu.address.logic.parser.job;

import static org.junit.Assert.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.job.JobDeleteCommand;

public class JobDeleteCommandParserTest {

    private JobDeleteCommandParser parser = new JobDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsJobDeleteCommand() {
        assertParseSuccess(parser, "1", new JobDeleteCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobDeleteCommand.MESSAGE_USAGE));
    }

}