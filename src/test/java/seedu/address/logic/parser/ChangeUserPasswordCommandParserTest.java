package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.LOGIN_INVALID_DESC_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.LOGIN_INVALID_DESC_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.LOGIN_VALID_DESC_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.LOGIN_VALID_DESC_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.NEWPASS_INVALID_DESC_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.NEWPASS_VALID_DESC_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOGIN_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOGIN_USERNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ChangeUserPasswordCommand;
import seedu.address.model.login.Password;
import seedu.address.model.login.Username;
//@@author kaisertanqr
public class ChangeUserPasswordCommandParserTest {

    private ChangeUserPasswordCommandParser parser = new ChangeUserPasswordCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        ChangeUserPasswordCommand validChangeUserCommand =
                new ChangeUserPasswordCommand(new Username(VALID_LOGIN_USERNAME), new Password(VALID_LOGIN_PASSWORD),
                new Password(VALID_LOGIN_PASSWORD + "AA"));

        //successful input
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD
                        + NEWPASS_VALID_DESC_PASSWORD, validChangeUserCommand);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD
                        + NEWPASS_VALID_DESC_PASSWORD, validChangeUserCommand);

        // multiple usernames - last username accepted
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_USERNAME
                + LOGIN_VALID_DESC_PASSWORD  + NEWPASS_VALID_DESC_PASSWORD, validChangeUserCommand);

        // multiple usernames with valid last username - last username accepted
        assertParseSuccess(parser,  LOGIN_INVALID_DESC_USERNAME + LOGIN_VALID_DESC_USERNAME
                + LOGIN_VALID_DESC_PASSWORD  + NEWPASS_VALID_DESC_PASSWORD, validChangeUserCommand);

        // multiple passwords with valid last password - last password accepted
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_INVALID_DESC_PASSWORD
                + LOGIN_VALID_DESC_PASSWORD + NEWPASS_VALID_DESC_PASSWORD, validChangeUserCommand);

        // multiple new passwords with valid last password - last password accepted
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_INVALID_DESC_PASSWORD
                + LOGIN_VALID_DESC_PASSWORD + NEWPASS_INVALID_DESC_PASSWORD
                + NEWPASS_VALID_DESC_PASSWORD, validChangeUserCommand);

    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeUserPasswordCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, LOGIN_VALID_DESC_PASSWORD, expectedMessage);

        // missing password prefix
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME, expectedMessage);

        // missing new password prefix
        assertParseFailure(parser, NEWPASS_VALID_DESC_PASSWORD, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid username
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + "@@ " + LOGIN_VALID_DESC_PASSWORD
                        + NEWPASS_INVALID_DESC_PASSWORD, Username.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD + "@@ "
                + NEWPASS_VALID_DESC_PASSWORD, Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD
                + NEWPASS_VALID_DESC_PASSWORD + "@@", Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + "@@ " + LOGIN_VALID_DESC_PASSWORD + "@@ "
                        + NEWPASS_INVALID_DESC_PASSWORD, Username.MESSAGE_USERNAME_CONSTRAINTS);

        // three invalid values, only first invalid value reported
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + "@@ " + LOGIN_VALID_DESC_PASSWORD + "@@ "
                + NEWPASS_INVALID_DESC_PASSWORD + "@@", Username.MESSAGE_USERNAME_CONSTRAINTS);

        // non-empty preamble
        System.out.println(PREAMBLE_NON_EMPTY + LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD
                + NEWPASS_VALID_DESC_PASSWORD);
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD
                        + NEWPASS_VALID_DESC_PASSWORD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeUserPasswordCommand.MESSAGE_USAGE));

    }
}
