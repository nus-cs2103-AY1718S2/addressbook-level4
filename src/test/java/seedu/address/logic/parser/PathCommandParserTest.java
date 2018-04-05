//@@author ZhangYijiong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.PathCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest} and to{@code SelectCommandParserTest}}.
 * @see DeleteCommandParserTest
 */
public class PathCommandParserTest {
    private PathCommandParser parser = new PathCommandParser();

    @Test
    public void parse_validArgs_returnsPathCommand() {
        assertParseSuccess(parser, "3", new PathCommand(INDEX_THIRD_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "ABC", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PathCommand.MESSAGE_USAGE));
    }
}
