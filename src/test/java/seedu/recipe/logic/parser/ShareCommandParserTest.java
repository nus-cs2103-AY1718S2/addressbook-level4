package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;

import org.junit.Test;

import seedu.recipe.logic.commands.ShareCommand;

public class ShareCommandParserTest {
    private ShareCommandParser parser = new ShareCommandParser();

    @Test
    public void parse_validArgs_returnsShareCommand() {
        assertParseSuccess(parser, "1", new ShareCommand(INDEX_FIRST_RECIPE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShareCommand.MESSAGE_USAGE));
    }
}
