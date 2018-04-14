package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.progresschecker.testutil.TypicalIndexes.INDEX_ISSUE;

import org.junit.Test;

import seedu.progresschecker.logic.commands.CloseIssueCommand;

public class CloseIssueCommandParserTest {

    private CloseIssueCommandParser parser = new CloseIssueCommandParser();

    @Test
    public void parse_validArgs_returnsCloseIssueCommand() {
        assertParseSuccess(parser, "125", new CloseIssueCommand(INDEX_ISSUE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseIssueCommand.MESSAGE_USAGE));
    }
}
