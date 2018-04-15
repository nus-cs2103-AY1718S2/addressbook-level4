package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.pair.PairHash;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.person.Rate;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Subject;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

public class XmlAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PRICE = "-50";
    private static final String INVALID_LEVEL = "kindergarden";
    private static final String INVALID_SUBJECT = "fake news";
    private static final String INVALID_ROLE = "nottutor";
    private static final String INVALID_STATUS = "very matched";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_SUBJECT = BENSON.getSubject().toString();
    private static final String VALID_LEVEL = BENSON.getLevel().toString();
    private static final String VALID_STATUS = BENSON.getStatus().toString();
    private static final String VALID_ROLE = BENSON.getRole().toString();
    private static final String VALID_PRICE = BENSON.getPrice().toString();

    private static final List<XmlAdaptedPairHash> VALID_PAIRHASH = Collections.singletonList(
            new XmlAdaptedPairHash(PairHash.DEFAULT_PAIR_HASH));

    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final String VALID_RATE = BENSON.getRate().toString();
    private static final String VALID_RATECOUNT = Integer.toString(BENSON.getRate().getCount());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        Person targetPerson = new PersonBuilder().withPairhash(new PairHash(1234)).build();
        XmlAdaptedPerson person = new XmlAdaptedPerson(targetPerson);
        assertEquals(targetPerson, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, VALID_ROLE,
                        VALID_TAGS, VALID_REMARK, VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, VALID_ROLE, VALID_TAGS,
                VALID_REMARK, VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, VALID_ROLE,
                        VALID_TAGS, VALID_REMARK, VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, VALID_ROLE, VALID_TAGS,
                VALID_REMARK, VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, VALID_ROLE,
                        VALID_TAGS, VALID_REMARK, VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, VALID_ROLE, VALID_TAGS,
                VALID_REMARK, VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, VALID_ROLE,
                        VALID_TAGS, VALID_REMARK, VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, VALID_ROLE, VALID_TAGS,
                VALID_REMARK, VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, VALID_ROLE,
                        invalidTags, VALID_REMARK, VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    //@@author aussiroth
    @Test
    public void toModelType_invalidLevel_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_PRICE, VALID_SUBJECT, INVALID_LEVEL, VALID_STATUS, VALID_ROLE,
                        VALID_TAGS, VALID_REMARK, VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = Level.MESSAGE_LEVEL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullLevel_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_PRICE, VALID_SUBJECT, null, VALID_STATUS, VALID_ROLE, VALID_TAGS, VALID_REMARK,
                VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Level.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSubject_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_PRICE, INVALID_SUBJECT, VALID_LEVEL, VALID_STATUS, VALID_ROLE,
                        VALID_TAGS, VALID_REMARK, VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = Subject.MESSAGE_SUBJECT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_PRICE, null, VALID_LEVEL, VALID_STATUS, VALID_ROLE, VALID_TAGS, VALID_REMARK,
                VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_PRICE, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, VALID_ROLE,
                        VALID_TAGS, VALID_REMARK, VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, VALID_ROLE, VALID_TAGS, VALID_REMARK,
                        VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, INVALID_STATUS, VALID_ROLE,
                        VALID_TAGS, VALID_REMARK, VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = Status.MESSAGE_STATUS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, null, VALID_TAGS, VALID_REMARK,
                VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, INVALID_ROLE,
                        VALID_TAGS, VALID_REMARK, VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = Role.MESSAGE_ROLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, null, VALID_ROLE, VALID_TAGS, VALID_REMARK,
                VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    //@@author
    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, VALID_ROLE, VALID_TAGS, null,
                VALID_RATE, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullRate_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_PRICE, VALID_SUBJECT, VALID_LEVEL, VALID_STATUS, VALID_ROLE, VALID_TAGS,
                VALID_REMARK, null, VALID_RATECOUNT, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Rate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void testXmlAdaptedPersonEquality() {
        XmlAdaptedPerson alice = new XmlAdaptedPerson(ALICE);
        XmlAdaptedPerson copy = new XmlAdaptedPerson(ALICE);
        assertTrue(alice.equals(alice));
        assertTrue(alice.equals(copy)); //check equality if values are equal
        assertFalse(alice.equals(ALICE)); //check not equal if type is different
    }

}
