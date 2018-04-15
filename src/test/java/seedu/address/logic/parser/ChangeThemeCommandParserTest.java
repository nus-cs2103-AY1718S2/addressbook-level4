//@@author amad-person
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.theme.Theme.DARK_THEME_KEYWORD;
import static seedu.address.model.theme.Theme.LIGHT_THEME_KEYWORD;

import org.junit.Test;

import seedu.address.logic.commands.ChangeThemeCommand;

public class ChangeThemeCommandParserTest {

    private final ChangeThemeCommandParser parser = new ChangeThemeCommandParser();

    @Test
    public void parse_validArgs_returnsChangeThemeCommand() {
        assertParseSuccess(parser, "light", new ChangeThemeCommand(LIGHT_THEME_KEYWORD));

        assertParseSuccess(parser, "dark", new ChangeThemeCommand(DARK_THEME_KEYWORD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeThemeCommand.MESSAGE_USAGE));
    }
}
