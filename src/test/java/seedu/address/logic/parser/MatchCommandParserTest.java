package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.MatchCommand;
import seedu.address.model.person.Category;

//@@author Zhu-Jiahui
public class MatchCommandParserTest {

    private MatchCommandParser parser = new MatchCommandParser();

    @Test
    public void parse_validArgs_returnsMatchCommand() {
        assertParseSuccess(parser, "1 c/s",
                new MatchCommand(INDEX_FIRST_PERSON, new Category("s")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Missing index
        assertParseFailure(parser, "c/s",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        // Missing category
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
    }
}
