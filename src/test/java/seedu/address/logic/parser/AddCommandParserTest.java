package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CURRENT_POSITION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CURRENT_POSITION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CURRENT_POSITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROFILE_PICTURE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SKILL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PROFILE_PICTURE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PROFILE_PICTURE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CURRENT_POSITION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CURRENT_POSITION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROFILE_PICTURE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.person.AddCommand;
import seedu.address.logic.parser.person.AddCommandParser;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.CurrentPosition;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.skill.Skill;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withCurrentPosition(VALID_CURRENT_POSITION_BOB).withCompany(VALID_COMPANY_BOB)
                .withProfilePicture(VALID_PROFILE_PICTURE_BOB)
                .withSkills(VALID_SKILL_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB
                + SKILL_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB
                + SKILL_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB
                + SKILL_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB
                + SKILL_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB
                + SKILL_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple current positions - last current position accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + CURRENT_POSITION_DESC_AMY + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB
                + SKILL_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple companies - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_AMY + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB
                + SKILL_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple profilePictures - last profilePicture accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_AMY + PROFILE_PICTURE_DESC_BOB
                        + SKILL_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple skills - all accepted
        Person expectedPersonMultipleSkills = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withCurrentPosition(VALID_CURRENT_POSITION_BOB).withCompany(VALID_COMPANY_BOB)
                .withProfilePicture(VALID_PROFILE_PICTURE_BOB).withSkills(VALID_SKILL_FRIEND, VALID_SKILL_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB
                        + SKILL_DESC_HUSBAND + SKILL_DESC_FRIEND, new AddCommand(expectedPersonMultipleSkills));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero skills and no profilePicture
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withCurrentPosition(VALID_CURRENT_POSITION_AMY).withCompany(VALID_COMPANY_AMY)
                .withProfilePicture().withSkills().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + CURRENT_POSITION_DESC_AMY + COMPANY_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB, expectedMessage);

        // missing current position prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + VALID_CURRENT_POSITION_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB, expectedMessage);

        // missing company prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + CURRENT_POSITION_DESC_BOB + VALID_COMPANY_BOB + PROFILE_PICTURE_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_CURRENT_POSITION_BOB + VALID_COMPANY_BOB + VALID_PROFILE_PICTURE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB + SKILL_DESC_HUSBAND
                + SKILL_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB + SKILL_DESC_HUSBAND
                + SKILL_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB + SKILL_DESC_HUSBAND
                + SKILL_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB + SKILL_DESC_HUSBAND
                + SKILL_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid current position
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_CURRENT_POSITION_DESC + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB + SKILL_DESC_HUSBAND
                + SKILL_DESC_FRIEND, CurrentPosition.MESSAGE_CURRENT_POSITION_CONSTRAINTS);

        // invalid company
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + CURRENT_POSITION_DESC_BOB + INVALID_COMPANY_DESC + PROFILE_PICTURE_DESC_BOB + SKILL_DESC_HUSBAND
                + SKILL_DESC_FRIEND, Company.MESSAGE_COMPANY_CONSTRAINTS);

        // invalid profilePicture
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + INVALID_PROFILE_PICTURE_DESC + SKILL_DESC_HUSBAND
                + SKILL_DESC_FRIEND, ProfilePicture.MESSAGE_PROFILEPICTURE_CONSTRAINTS);

        // invalid skill
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB + INVALID_SKILL_DESC
                + VALID_SKILL_FRIEND, Skill.MESSAGE_SKILL_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + CURRENT_POSITION_DESC_BOB + COMPANY_DESC_BOB + PROFILE_PICTURE_DESC_BOB
                + SKILL_DESC_HUSBAND + SKILL_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
