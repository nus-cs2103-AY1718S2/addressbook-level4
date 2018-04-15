package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddCommand.MESSAGE_USAGE;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CASE_INSENSITIVE_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CASE_INSENSITIVE_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CASE_INSENSITIVE_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CASE_INSENSITIVE_LEVEL_SHORTCUT_UPPER_SEC;
import static seedu.address.logic.commands.CommandTestUtil.CASE_INSENSITIVE_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CASE_INSENSITIVE_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CASE_INSENSITIVE_ROLE_SHORTCUT_TUTOR;
import static seedu.address.logic.commands.CommandTestUtil.CASE_INSENSITIVE_SUBJECT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CASE_INSENSITIVE_SUBJECT_SHORTCUT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEVEL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_LOWER_SEC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_UPPER_SEC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_LOWER_PRI;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_LOWER_SEC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_SHORTCUT_LOWER_PRI;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_SHORTCUT_LOWER_SEC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_SHORTCUT_UPPER_PRI;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_SHORTCUT_UPPER_SEC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_UPPER_PRI;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_UPPER_SEC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_SHORTCUT_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_SHORTCUT_TUTOR;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_TUTOR;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_UNMATCHED;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_UNMATCHED;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_CHEMISTRY;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_SHORTCUT_CHEMISTRY;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_SHORTCUT_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_SHORTCUT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_SHORTCUT_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {

    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));


        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(SUBJECT_CHINESE)
                .withPrice(VALID_PRICE_BOB).withLevel(LEVEL_UPPER_SEC).withRole(ROLE_TUTOR).withStatus(STATUS_UNMATCHED)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_CHINESE + PRICE_DESC_BOB + LEVEL_DESC_UPPER_SEC + ROLE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_fieldsMissing_failure() {
        //no name
        assertParseFailure(parser, PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + PRICE_DESC_AMY + SUBJECT_DESC_AMY + LEVEL_DESC_LOWER_SEC + STATUS_DESC_UNMATCHED,
                MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE);
    }

    //@@author aussiroth
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        //Only name is compulsory now
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE, MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_ENGLISH + LEVEL_DESC_LOWER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_optionalFieldMissing_success() {
        //All non-name fields are optional
        //phone
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone("")
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //email
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail("").withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //address
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress("").withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + SUBJECT_DESC_BOB + LEVEL_DESC_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //subject
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject("")
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + LEVEL_DESC_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //level
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel("").withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        //price
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withPrice("")
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //role
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole("").withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB
                + PRICE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //all missing but name
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone("")
                .withEmail("").withAddress("").withSubject("")
                .withLevel("").withPrice("")
                .withRole("").build();
        assertParseSuccess(parser, NAME_DESC_BOB, new AddCommand(expectedPerson));

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_CHINESE + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + INVALID_PRICE_DESC
                + ROLE_DESC_BOB + TAG_DESC_FRIEND, Price.MESSAGE_PRICE_CONSTRAINTS);

        // invalid subject
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_SUBJECT_DESC + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_FRIEND, Subject.MESSAGE_SUBJECT_CONSTRAINTS);

        // invalid level
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_BOB + INVALID_LEVEL_DESC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB
                + ROLE_DESC_BOB + TAG_DESC_FRIEND, Level.MESSAGE_LEVEL_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SUBJECT_DESC_CHINESE + LEVEL_DESC_UPPER_SEC + PRICE_DESC_BOB
                + ROLE_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);


        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                + SUBJECT_DESC_CHINESE + INVALID_LEVEL_DESC + STATUS_DESC_UNMATCHED + PRICE_DESC_BOB + ROLE_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_CHINESE + LEVEL_DESC_UPPER_SEC + STATUS_DESC_UNMATCHED
                        + ROLE_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE, MESSAGE_USAGE));
    }

    //@@author
    @Test
    public void parse_fieldsCaseInsensitive_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();

        Person expectedPersonWithMixedCaseName = new PersonBuilder().withName("bOb cHoO")
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withSubject(VALID_SUBJECT_BOB).withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB)
                .withPrice(VALID_PRICE_BOB).withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();

        Person expectedPersonWithMixedCaseEmail = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail("bOb@eXaMpLe.CoM").withAddress(VALID_ADDRESS_BOB)
                .withSubject(VALID_SUBJECT_BOB).withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB)
                .withPrice(VALID_PRICE_BOB).withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();

        // name case insensitive - accepted
        assertParseSuccess(parser, CASE_INSENSITIVE_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPersonWithMixedCaseName));

        // email case insensitive - accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + CASE_INSENSITIVE_EMAIL_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPersonWithMixedCaseEmail));

        // address case insensitive - accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + CASE_INSENSITIVE_ADDRESS_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));


        // subject case insensitive - accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + CASE_INSENSITIVE_SUBJECT_BOB + LEVEL_DESC_UPPER_SEC
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // level case insensitive - accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + CASE_INSENSITIVE_LEVEL_BOB
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // role case insensitive - accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC
                + PRICE_DESC_BOB + CASE_INSENSITIVE_ROLE_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_fieldsShortcut_success() {

        // subject short cut (eng) accepted
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(SUBJECT_ENGLISH)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_SHORTCUT_ENGLISH + LEVEL_DESC_UPPER_SEC
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));


        // subject short cut (chi) accepted
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(SUBJECT_CHINESE)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_SHORTCUT_CHINESE + LEVEL_DESC_UPPER_SEC
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // subject short cut (chem) accepted
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(SUBJECT_CHEMISTRY)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_SHORTCUT_CHEMISTRY + LEVEL_DESC_UPPER_SEC
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // subject short cut (phy) accepted
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(SUBJECT_PHYSICS)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_SHORTCUT_PHYSICS + LEVEL_DESC_UPPER_SEC
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // level short cut (us) accepted
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_SHORTCUT_UPPER_SEC
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // level short cut (ls) accepted
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(LEVEL_LOWER_SEC).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_SHORTCUT_LOWER_SEC
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // level short cut (up) accepted
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(LEVEL_UPPER_PRI).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_SHORTCUT_UPPER_PRI
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // level short cut (lp) accepted
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(LEVEL_LOWER_PRI).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_SHORTCUT_LOWER_PRI
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // role short cut (t) accepted
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withPrice(VALID_PRICE_BOB).withRole(ROLE_TUTOR)
                .withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC
                + PRICE_DESC_BOB + ROLE_SHORTCUT_TUTOR
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // role short cut (s) accepted
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(ROLE_STUDENT).withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC
                + PRICE_DESC_BOB + ROLE_SHORTCUT_STUDENT
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // status short cut (um) accepted
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB).withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // status short cut (m) accepted
        expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_BOB)
                .withLevel(VALID_LEVEL_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB)
                .withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SUBJECT_DESC_BOB + LEVEL_DESC_UPPER_SEC
                + PRICE_DESC_BOB + ROLE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_fieldsShortcutAndCaseInsensitive_success() {

        // using shortcuts in cae insenstive fashion - accepted
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSubject(SUBJECT_ENGLISH)
                .withLevel(VALID_LEVEL_BOB).withStatus(VALID_STATUS_BOB).withPrice(VALID_PRICE_BOB)
                .withRole(VALID_ROLE_BOB)
                .withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB  + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + CASE_INSENSITIVE_SUBJECT_SHORTCUT_ENGLISH
                + CASE_INSENSITIVE_LEVEL_SHORTCUT_UPPER_SEC
                + PRICE_DESC_BOB + CASE_INSENSITIVE_ROLE_SHORTCUT_TUTOR
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

    }

}
