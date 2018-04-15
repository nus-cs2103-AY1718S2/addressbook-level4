package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.progresschecker.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_MAJOR_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.MAJOR_DESC_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.MAJOR_DESC_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.progresschecker.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.progresschecker.logic.commands.CommandTestUtil.USERNAME_DESC_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.USERNAME_DESC_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_MAJOR_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_USERNAME_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_USERNAME_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_YEAR_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.VALID_YEAR_BOB;
import static seedu.progresschecker.logic.commands.CommandTestUtil.YEAR_DESC_AMY;
import static seedu.progresschecker.logic.commands.CommandTestUtil.YEAR_DESC_BOB;
import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.progresschecker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.progresschecker.logic.commands.AddCommand;
import seedu.progresschecker.model.person.Email;
import seedu.progresschecker.model.person.GithubUsername;
import seedu.progresschecker.model.person.Major;
import seedu.progresschecker.model.person.Name;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.Phone;
import seedu.progresschecker.model.person.Year;
import seedu.progresschecker.model.tag.Tag;
import seedu.progresschecker.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withUsername(VALID_USERNAME_BOB)
                .withMajor(VALID_MAJOR_BOB).withYear(VALID_YEAR_BOB)
                .withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + USERNAME_DESC_BOB + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + USERNAME_DESC_BOB + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + USERNAME_DESC_BOB + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + USERNAME_DESC_BOB + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        //@@author EdwardKSG
        // multiple usernames - last name accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_AMY
                        + USERNAME_DESC_BOB + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple majors - last major accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_AMY + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple years - last year accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_BOB + YEAR_DESC_AMY + YEAR_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));
        //@@author

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withUsername(VALID_USERNAME_BOB).withMajor(VALID_MAJOR_BOB)
                .withYear(VALID_YEAR_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withUsername(VALID_USERNAME_AMY).withMajor(VALID_MAJOR_AMY)
                .withYear(VALID_YEAR_AMY).withTags().build();

        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + USERNAME_DESC_AMY
                + MAJOR_DESC_AMY + YEAR_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_BOB + YEAR_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_BOB + YEAR_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_BOB + YEAR_DESC_BOB, expectedMessage);

        //@@author EdwardKSG
        // missing username prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_USERNAME_BOB
                + MAJOR_DESC_BOB + YEAR_DESC_BOB, expectedMessage);

        // missing major prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + VALID_MAJOR_BOB + YEAR_DESC_BOB, expectedMessage);

        // missing year prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_BOB + VALID_YEAR_BOB, expectedMessage);
        //@@author

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_USERNAME_BOB
                + VALID_MAJOR_BOB + VALID_YEAR_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + USERNAME_DESC_BOB
                + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        //@@author EdwardKSG
        // invalid username
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_USERNAME_DESC
                + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                GithubUsername.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid major
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + INVALID_MAJOR_DESC + YEAR_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Major.MESSAGE_MAJOR_CONSTRAINTS);

        // invalid year
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_BOB + INVALID_YEAR_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Year.MESSAGE_YEAR_CONSTRAINTS);
        //@@author

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + MAJOR_DESC_BOB + YEAR_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + USERNAME_DESC_BOB
                + INVALID_MAJOR_DESC + YEAR_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + USERNAME_DESC_BOB + MAJOR_DESC_BOB + YEAR_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

}
