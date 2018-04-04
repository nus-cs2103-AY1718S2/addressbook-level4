//@@author nicholasangcx
package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.recipe.logic.commands.AccessTokenCommand;

public class AccessTokenCommandParserTest {

    private static final String DUMMY_ACCESS_CODE = "valid_access_code";
    private AccessTokenCommandParser parser = new AccessTokenCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AccessTokenCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsUploadCommand() {
        // code taken from an actual dropbox authorization process
        AccessTokenCommand expectedAccessTokenCommand =
                new AccessTokenCommand(DUMMY_ACCESS_CODE);
        assertParseSuccess(parser, "valid_access_code", expectedAccessTokenCommand);

        // trim leading and trailing whitespaces
        assertParseSuccess(parser, "  valid_access_code  ", expectedAccessTokenCommand);
    }
}
//@@author
