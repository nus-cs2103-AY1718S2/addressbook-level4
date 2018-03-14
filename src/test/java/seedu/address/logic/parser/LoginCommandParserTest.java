package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.LoginCommand.TEST_PASSWORD;
import static seedu.address.logic.commands.LoginCommand.TEST_USERNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;

public class LoginCommandParserTest {
    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, TEST_USERNAME, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        assertParseFailure(parser, TEST_USERNAME + " " + TEST_PASSWORD + " " + TEST_USERNAME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsLoginCommand() {
        // no leading and trailing whitespaces
        LoginCommand expectedLoginCommand = new LoginCommand(TEST_USERNAME, TEST_PASSWORD);
        assertParseSuccess(parser, TEST_USERNAME + " " + TEST_PASSWORD, expectedLoginCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                " \n " + TEST_USERNAME + " \n \t " + TEST_PASSWORD + "  \t", expectedLoginCommand);
    }
}
