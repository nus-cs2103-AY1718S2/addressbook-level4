//@@author Jason1im
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_USER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_USER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.UpdateUsernameCommand;

public class UpdateUsernameParserTest {
    private UpdateUsernameCommandParser parser = new UpdateUsernameCommandParser();
    private String newUsername = "John";

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_USER,
                new UpdateUsernameCommand(VALID_USERNAME_USER));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateUsernameCommand.MESSAGE_USAGE);

        //Missing prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_USERNAME_USER, expectedMessage);

        // Missing input
        assertParseFailure(parser, PREAMBLE_WHITESPACE + PREFIX_USERNAME, expectedMessage);
    }
}
