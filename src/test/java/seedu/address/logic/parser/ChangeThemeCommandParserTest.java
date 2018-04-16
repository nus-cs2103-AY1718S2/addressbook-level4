package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ChangeThemeCommand;

/** @@author Codee */
public class ChangeThemeCommandParserTest {

    private ChangeThemeCommandParser parser = new ChangeThemeCommandParser();
    private String[] listThemes = { "Light", "Dark" };

    @Test
    public void parse_validArgs_returnsThemeCommand() {
        for (int i = 0; i < 2; i++) {
            assertParseSuccess(parser, listThemes[i], new ChangeThemeCommand(listThemes[i]));
        }
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Empty Argument
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
    }
}
