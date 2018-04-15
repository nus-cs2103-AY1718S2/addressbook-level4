package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ThemeCommand;

//@@author tiny767
public class ThemeCommandParserTest {
    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "dark", new ThemeCommand("dark"));

        //valid theme name (since it's case insensitive
        assertParseSuccess(parser, "LIGHT", new ThemeCommand("LIGHT"));
        assertParseSuccess(parser, "mOrning", new ThemeCommand("mOrning"));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "afternoon",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "dark abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
    }
}
