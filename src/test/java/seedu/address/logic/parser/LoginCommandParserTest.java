//@@author Jason1im
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_USER;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_USER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_USER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_USER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;

public class LoginCommandParserTest {

    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_USER
                + PASSWORD_DESC_USER, new LoginCommand("John", "1234"));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

        // Missing username prefix.
        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_USERNAME_USER
                + PASSWORD_DESC_USER, expectedMessage);

        assertParseFailure(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_USER
                + VALID_PASSWORD_USER, expectedMessage);

        // Missing username
        assertParseFailure(parser, PREAMBLE_WHITESPACE + PREFIX_USERNAME + PASSWORD_DESC_USER, expectedMessage);

        // Missing password
        assertParseFailure(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_USER, expectedMessage);
    }

}
