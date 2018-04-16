package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_JERSEY_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.JERSEY_NUMBER_DESC_17;
import static seedu.address.logic.commands.CommandTestUtil.JERSEY_NUMBER_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_MIDFILED;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_STRIKER;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_0;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JERSEY_NUMBER_17;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JERSEY_NUMBER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_MIDFIELD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_STRIKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_0;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.UNSPECIFIED_FIELD;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.JerseyNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Rating;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND)
                .withRating(VALID_RATING_0).withPosition(VALID_POSITION_STRIKER)
                .withJerseyNumber(VALID_JERSEY_NUMBER_2).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        //multiple rating - last rating accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RATING_DESC_1 + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        //multiple position - last position accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RATING_DESC_0 + POSITION_DESC_MIDFILED + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_2
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        //multiple jersey number - last jersey number accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RATING_DESC_0 + POSITION_DESC_STRIKER + JERSEY_NUMBER_DESC_17 + JERSEY_NUMBER_DESC_2
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .withRating(VALID_RATING_0).withPosition(VALID_POSITION_STRIKER)
                .withJerseyNumber(VALID_JERSEY_NUMBER_2).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RATING_DESC_0 + POSITION_DESC_MIDFILED + JERSEY_NUMBER_DESC_2
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags()
                .withRating(VALID_RATING_1).withPosition(VALID_POSITION_MIDFIELD)
                .withJerseyNumber(VALID_JERSEY_NUMBER_17).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + RATING_DESC_1 + POSITION_DESC_MIDFILED + JERSEY_NUMBER_DESC_17,
                new AddCommand(expectedPerson));

        // missing all optional fields
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(UNSPECIFIED_FIELD)
                .withEmail(VALID_EMAIL_AMY).withAddress(UNSPECIFIED_FIELD).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY,
                new AddCommand(expectedPerson));

        // missing address
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(UNSPECIFIED_FIELD).withRating(VALID_RATING_1)
                .withPosition(VALID_POSITION_MIDFIELD)
                .withJerseyNumber(VALID_JERSEY_NUMBER_17).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY + PHONE_DESC_AMY
                        + RATING_DESC_1 + POSITION_DESC_MIDFILED + JERSEY_NUMBER_DESC_17,
                new AddCommand((expectedPerson)));

        // missing phone
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(UNSPECIFIED_FIELD)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withRating(VALID_RATING_1).withPosition(VALID_POSITION_MIDFIELD)
                .withJerseyNumber(VALID_JERSEY_NUMBER_17).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + RATING_DESC_1 + POSITION_DESC_MIDFILED + JERSEY_NUMBER_DESC_17,
                new AddCommand((expectedPerson)));

        // missing rating
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(UNSPECIFIED_FIELD)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withPosition(VALID_POSITION_MIDFIELD)
                .withJerseyNumber(VALID_JERSEY_NUMBER_17).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + POSITION_DESC_MIDFILED + JERSEY_NUMBER_DESC_17,
                new AddCommand((expectedPerson)));

        // missing position
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(UNSPECIFIED_FIELD)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withRating(VALID_RATING_1)
                .withJerseyNumber(VALID_JERSEY_NUMBER_17).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + RATING_DESC_1 + JERSEY_NUMBER_DESC_17,
                new AddCommand((expectedPerson)));

        // missing jersey number
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(UNSPECIFIED_FIELD)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withRating(VALID_RATING_1).withPosition(VALID_POSITION_MIDFIELD)
                .withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + RATING_DESC_1 + POSITION_DESC_MIDFILED,
                new AddCommand((expectedPerson)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RATING_DESC_1 + POSITION_DESC_MIDFILED + JERSEY_NUMBER_DESC_17
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RATING_DESC_1 + POSITION_DESC_MIDFILED + JERSEY_NUMBER_DESC_17
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + RATING_DESC_1 + POSITION_DESC_MIDFILED + JERSEY_NUMBER_DESC_17
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + RATING_DESC_1 + POSITION_DESC_MIDFILED + JERSEY_NUMBER_DESC_17
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RATING_DESC_1 + POSITION_DESC_MIDFILED + JERSEY_NUMBER_DESC_17
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid rating
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_RATING_DESC + POSITION_DESC_MIDFILED + JERSEY_NUMBER_DESC_17
                 + VALID_TAG_FRIEND, Rating.MESSAGE_RATING_CONSTRAINTS);

        // invalid position
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RATING_DESC_1 + INVALID_POSITION_DESC + JERSEY_NUMBER_DESC_17
                + VALID_TAG_FRIEND, Position.MESSAGE_POSITION_CONSTRAINTS);

        // invalid jersey number
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RATING_DESC_1 + POSITION_DESC_MIDFILED + INVALID_JERSEY_NUMBER_DESC
                + VALID_TAG_FRIEND, JerseyNumber.MESSAGE_JERSEY_NUMBER_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + RATING_DESC_1 + POSITION_DESC_MIDFILED + JERSEY_NUMBER_DESC_17 + INVALID_ADDRESS_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + RATING_DESC_1 + POSITION_DESC_MIDFILED + JERSEY_NUMBER_DESC_17
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
