package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_USER;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_USER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.UpdatePasswordCommand;

public class UpdatePasswordParserTest {
    private UpdatePasswordCommandParser parser = new UpdatePasswordCommandParser();
    private String newPassword = "ang123";

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PASSWORD_DESC_USER
                + " " + PREFIX_NEW_PASSWORD + newPassword,
                new UpdatePasswordCommand(VALID_PASSWORD_USER, newPassword));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdatePasswordCommand.MESSAGE_USAGE);

        //Missing prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + PASSWORD_DESC_USER
                + newPassword, expectedMessage);

        // Missing input
        assertParseFailure(parser, PREAMBLE_WHITESPACE + PASSWORD_DESC_USER
                + PREFIX_NEW_PASSWORD, expectedMessage);

        //
    }
}
