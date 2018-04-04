package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.organizer.logic.commands.ForgotPasswordCommand;

//@@author dominickenn
public class ForgotPasswordCommandParserTest {
    private ForgotPasswordCommandParser parser = new ForgotPasswordCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String expectedUsername = "admin";

        assertParseSuccess(parser, " u/admin", new ForgotPasswordCommand(expectedUsername));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ForgotPasswordCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, " admin", expectedMessage);
    }
}
