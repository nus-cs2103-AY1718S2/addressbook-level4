package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_THEME_LIGHTT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_THEME_PINK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THEME_DARK1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THEME_DARK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THEME_LIGHT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THEME_LIGHT2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.theme.Theme.MESSAGE_THEME_CONSTRAINTS;
import static seedu.address.testutil.TypicalThemes.DARK;
import static seedu.address.testutil.TypicalThemes.LIGHT;

import org.junit.Test;

import javafx.scene.effect.Light;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.model.theme.Theme;

public class ChangeThemeCommandParserTest {
    private ChangeThemeCommandParser parser = new ChangeThemeCommandParser();

    @Test
    public void parse_invalidUsage() {
        //empty
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeThemeCommand.MESSAGE_USAGE));

        //more than 1 argument
        assertParseFailure(parser, "light dark", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeThemeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "dark blue", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeThemeCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_invalidTheme() {

        //themes do not exist
        assertParseFailure(parser, INVALID_THEME_PINK, Theme.MESSAGE_THEME_CONSTRAINTS);
        assertParseFailure(parser, INVALID_THEME_LIGHTT, Theme.MESSAGE_THEME_CONSTRAINTS);
    }

    @Test
    public void parse_validTheme_caseInsensitive() {
        //LIGHT
        assertParseSuccess(parser, "LIGHT", new ChangeThemeCommand(LIGHT));

        //LIghT
        assertParseSuccess(parser, "LIghT", new ChangeThemeCommand(LIGHT));

        //DaRk
        assertParseSuccess(parser, "DaRk", new ChangeThemeCommand(DARK));

        //DARk
        assertParseSuccess(parser, "DARk", new ChangeThemeCommand(DARK));
    }
}
