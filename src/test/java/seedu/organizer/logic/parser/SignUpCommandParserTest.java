package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.commons.core.Messages.MESSAGE_REPEATED_SAME_PREFIXES;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PASSWORD_BOBBY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PASSWORD_JOSHUA;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_USERNAME_BOBBY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_USERNAME_JOSHUA;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.organizer.logic.commands.SignUpCommand;
import seedu.organizer.model.user.User;

//@@author dominickenn
public class SignUpCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignUpCommand.MESSAGE_USAGE);
    private static final String MESSAGE_MULTIPLE_SAME_PREFIXES =
            String.format(MESSAGE_REPEATED_SAME_PREFIXES, SignUpCommand.MESSAGE_USAGE);

    private SignUpCommandParser parser = new SignUpCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        User expectedUser = new User("bobby", "bobby");

        assertParseSuccess(parser, " u/bobby p/bobby", new SignUpCommand(expectedUser));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing username prefix
        assertParseFailure(parser, " bobby p/b0bby", MESSAGE_INVALID_FORMAT);

        // missing password prefix
        assertParseFailure(parser, " u/bobby b0bby", MESSAGE_INVALID_FORMAT);

        // all prefixes missing
        assertParseFailure(parser, " bobby b0bby", MESSAGE_INVALID_FORMAT);

        // missing username
        assertParseFailure(parser, "u/ p/b0bby", MESSAGE_INVALID_FORMAT);

        // missing password
        assertParseFailure(parser, "u/bobby p/ ", MESSAGE_INVALID_FORMAT);

        // missing fields
        assertParseFailure(parser, "u/ p/ ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username : contains special character
        assertParseFailure(parser, " u/b@bby p/bobby", User.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password : contains special character
        assertParseFailure(parser, " u/bobby p/b@bby", User.MESSAGE_PASSWORD_CONSTRAINTS);

        // invalid username : length < 5
        assertParseFailure(parser, " u/bobb p/bobby", User.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password : length < 5
        assertParseFailure(parser, " u/bobby p/bobb", User.MESSAGE_PASSWORD_CONSTRAINTS);

        // invalid username : blank
        assertParseFailure(parser, " u/ p/bobby", User.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password : blank
        assertParseFailure(parser, " u/bobby p/ ", User.MESSAGE_PASSWORD_CONSTRAINTS);
    }

    //@@author guekling
    @Test
    public void parse_multipleSamePrefixes_failure() {
        // repeated username prefix
        assertParseFailure(parser, " " + PREFIX_USERNAME + VALID_USERNAME_JOSHUA + " " + PREFIX_USERNAME
            + VALID_USERNAME_BOBBY + " " + PREFIX_PASSWORD + VALID_PASSWORD_BOBBY, MESSAGE_MULTIPLE_SAME_PREFIXES);

        // repeated password prefix
        assertParseFailure(parser, " " + PREFIX_USERNAME + VALID_USERNAME_JOSHUA + " " + PREFIX_PASSWORD
            + VALID_PASSWORD_JOSHUA + " " + PREFIX_PASSWORD + VALID_PASSWORD_BOBBY, MESSAGE_MULTIPLE_SAME_PREFIXES);
        assertParseFailure(parser, " u/steven p/12345 p/12345",  MESSAGE_MULTIPLE_SAME_PREFIXES);
    }
    //@@author
}
