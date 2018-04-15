package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.progresschecker.testutil.TypicalIndexes.INDEX_ISSUE;

import org.junit.Test;

import seedu.progresschecker.logic.commands.ReopenIssueCommand;

//@@author adityaa1998
public class ReopenIssueCommandParserTest {

    private ReopenIssueCommandParser parser = new ReopenIssueCommandParser();

    @Test
    public void parse_validArgs_returnsReopenIssueCommand() {
        assertParseSuccess(parser, "1", new ReopenIssueCommand(INDEX_ISSUE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ReopenIssueCommand.MESSAGE_USAGE));
    }
}
