package seedu.organizer.logic.parser;

//@@author aguss787
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.organizer.logic.commands.ToggleCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 *
 * @see DeleteCommandParserTest
 */
public class ToggleCommandParserTest {

    private ToggleCommandParser parser = new ToggleCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new ToggleCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ToggleCommand.MESSAGE_USAGE));
    }
}
