package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.organizer.logic.commands.LoginCommand;
import seedu.organizer.model.user.User;

//@@author dominickenn
public class LoginCommandParserTest {
    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        User expectedUser = new User("bob", "bob");

        assertParseSuccess(parser, " u/bob p/bob", new LoginCommand(expectedUser));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, " bob p/b0b", expectedMessage);

        // missing password prefix
        assertParseFailure(parser, " u/bob b0b", expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, " bob b0b", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser, " u/b@b p/bob", User.MESSAGE_USER_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, " u/bob p/b@b", User.MESSAGE_USER_CONSTRAINTS);
    }
}
