package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.Username;
import seedu.address.testutil.AccountBuilder;

//@@author shadow2496
public class LoginCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Account account = new AccountBuilder().withUsername(VALID_USERNAME_BOB)
                .withPassword(VALID_PASSWORD_BOB).build();
        LoginCommand expectedCommand = new LoginCommand(account);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_BOB + PASSWORD_DESC_BOB,
                expectedCommand);

        // multiple usernames - last username accepted
        assertParseSuccess(parser, USERNAME_DESC_AMY + USERNAME_DESC_BOB + PASSWORD_DESC_BOB,
                expectedCommand);

        // multiple passwords - last password accepted
        assertParseSuccess(parser, USERNAME_DESC_BOB + PASSWORD_DESC_AMY + PASSWORD_DESC_BOB,
                expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing username prefix
        assertParseFailure(parser, VALID_USERNAME_BOB + PASSWORD_DESC_BOB, MESSAGE_INVALID_FORMAT);

        // missing password prefix
        assertParseFailure(parser, USERNAME_DESC_BOB + VALID_PASSWORD_BOB, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser, INVALID_USERNAME_DESC + PASSWORD_DESC_BOB,
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, USERNAME_DESC_BOB + INVALID_PASSWORD_DESC,
                Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_USERNAME_DESC + INVALID_PASSWORD_DESC,
                Username.MESSAGE_USERNAME_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + USERNAME_DESC_BOB + PASSWORD_DESC_BOB,
                MESSAGE_INVALID_FORMAT);
    }
}
