package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ACTIVITYLEVEL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ACTIVITYLEVEL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HEIGHT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HEIGHT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ACTIVITYLEVEL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HEIGHT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEIGHT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITYLEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITYLEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEIGHT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEIGHT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.ActivityLevel;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Height;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Weight;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withHeight(VALID_HEIGHT_BOB)
                .withWeight(VALID_WEIGHT_BOB)
                .withGender(VALID_GENDER_BOB)
                .withAge(VALID_AGE_BOB)
                .withActivityLevel(VALID_ACTIVITYLEVEL_BOB)
                .withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB
                        + ACTIVITYLEVEL_DESC_BOB
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB
                        + ACTIVITYLEVEL_DESC_BOB
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB
                        + ACTIVITYLEVEL_DESC_BOB
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB
                        + ACTIVITYLEVEL_DESC_BOB
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB
                        + ACTIVITYLEVEL_DESC_BOB
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withHeight(VALID_HEIGHT_BOB)
                .withWeight(VALID_WEIGHT_BOB)
                .withGender(VALID_GENDER_BOB)
                .withAge(VALID_AGE_BOB)
                .withActivityLevel(VALID_ACTIVITYLEVEL_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withHeight(VALID_HEIGHT_AMY)
                .withWeight(VALID_WEIGHT_AMY).withGender(VALID_GENDER_AMY).withAge(VALID_AGE_AMY)
                .withActivityLevel(VALID_ACTIVITYLEVEL_AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + HEIGHT_DESC_AMY + WEIGHT_DESC_AMY
                + GENDER_DESC_AMY + AGE_DESC_AMY + ACTIVITYLEVEL_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                        + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                        + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB,
                expectedMessage);

        // missing height prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + VALID_HEIGHT_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB,
                expectedMessage);

        // missing weight prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + HEIGHT_DESC_BOB + VALID_WEIGHT_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB,
                expectedMessage);

        // missing gender prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + WEIGHT_DESC_BOB + VALID_GENDER_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB,
                expectedMessage);

        // missing age prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + WEIGHT_DESC_BOB  + GENDER_DESC_BOB + VALID_AGE_BOB + ACTIVITYLEVEL_DESC_BOB,
                expectedMessage);

        // missing activityLevel prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + WEIGHT_DESC_BOB  + GENDER_DESC_BOB + AGE_DESC_BOB + VALID_ACTIVITYLEVEL_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                        + VALID_HEIGHT_BOB + VALID_WEIGHT_BOB + VALID_GENDER_BOB + VALID_AGE_BOB
                        + VALID_ACTIVITYLEVEL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + HEIGHT_DESC_BOB  + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid height
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_HEIGHT_DESC + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Height.MESSAGE_HEIGHT_CONSTRAINTS);

        // invalid weight
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + HEIGHT_DESC_BOB + INVALID_WEIGHT_DESC + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Weight.MESSAGE_WEIGHT_CONSTRAINTS);

        // invalid Gender
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + INVALID_GENDER_DESC + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Gender.MESSAGE_GENDER_CONSTRAINTS);

        // invalid Age
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + INVALID_AGE_DESC + ACTIVITYLEVEL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Age.MESSAGE_AGE_CONSTRAINTS);

        // invalid ActivityLevel
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + INVALID_ACTIVITYLEVEL_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, ActivityLevel.MESSAGE_ACTIVITYLEVEL_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + GENDER_DESC_BOB + AGE_DESC_BOB + ACTIVITYLEVEL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
