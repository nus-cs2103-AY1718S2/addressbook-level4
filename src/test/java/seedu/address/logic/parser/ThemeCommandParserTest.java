package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Theme;
import seedu.address.logic.commands.ThemeCommand;

public class ThemeCommandParserTest {

    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void parse_validArgs_returnsThemeCommand() {
        assertParseSuccess(parser, "white", new ThemeCommand(Theme.WHITE));
        assertParseSuccess(parser, "light", new ThemeCommand(Theme.LIGHT));
        assertParseSuccess(parser, "dark", new ThemeCommand(Theme.DARK));

        // theme names should be case insensitive
        assertParseSuccess(parser, "whITE", new ThemeCommand(Theme.WHITE));
        assertParseSuccess(parser, "LIGht", new ThemeCommand(Theme.LIGHT));
        assertParseSuccess(parser, "Dark", new ThemeCommand(Theme.DARK));

        // leading and ending spaces should be ignored
        assertParseSuccess(parser, "   white   ", new ThemeCommand(Theme.WHITE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "12345", String.format(ThemeCommand.MESSAGE_INVALID_THEME, "12345"));
        assertParseFailure(parser, "a", String.format(ThemeCommand.MESSAGE_INVALID_THEME, "a"));
        assertParseFailure(parser, "whitee", String.format(ThemeCommand.MESSAGE_INVALID_THEME, "whitee"));
    }
    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
    }
}
