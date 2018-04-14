package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.progresschecker.logic.commands.ListIssuesCommand;

public class ListIssuesCommandParserTest {

    private ListIssuesCommandParser parser = new ListIssuesCommandParser();

    @Test
    public void parse_validArgs_returnsListIssuesCommand() {
        assertParseSuccess(parser, "OPEN", new ListIssuesCommand("OPEN"));
        assertParseSuccess(parser, "CLOSE", new ListIssuesCommand("CLOSE"));
    }
}
