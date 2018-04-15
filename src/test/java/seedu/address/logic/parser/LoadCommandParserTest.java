//@@author ZhangYijiong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.LoadCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest} and to{@code SelectCommandParserTest}}.
 * @see DeleteCommandParserTest
 */
public class LoadCommandParserTest {
    private LoadCommandParser parser = new LoadCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
    }
}
