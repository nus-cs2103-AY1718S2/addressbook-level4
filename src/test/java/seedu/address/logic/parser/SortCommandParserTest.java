package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UnlockCommand;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

/**
 * Test scope: To test SortCommandParser.
 * @see DeleteCommandParserTest
 */
public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void no_arguments_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
