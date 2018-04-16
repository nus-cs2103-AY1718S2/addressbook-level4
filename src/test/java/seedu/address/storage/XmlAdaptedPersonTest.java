package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.testutil.Assert;

public class XmlAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final Double INVALID_INCOME = -20323.3;
    private static final Integer INVALID_AGE = 1203;

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final Double VALID_INCOME = BENSON.getIncome().value;
    private static final Double VALID_ACTUALSPENDING = BENSON.getActualSpending().value;
    private static final Double VALID_EXPECTEDSPENDING = BENSON.getExpectedSpending().value;
    private static final Integer VALID_AGE = BENSON.getAge().value;
    private static final String VALID_BEG_DATE = "10/10/2018";
    private static final String VALID_EXP_DATE = "25/10/2018";
    private static final Double VALID_PRICE = 100.0;
    private static final List<String> VALID_ISSUES = new ArrayList<>();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_INCOME, VALID_ACTUALSPENDING, VALID_EXPECTEDSPENDING, VALID_AGE, VALID_BEG_DATE,
                        VALID_EXP_DATE, VALID_PRICE, VALID_ISSUES);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(null, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_INCOME, VALID_ACTUALSPENDING, VALID_EXPECTEDSPENDING,
                VALID_AGE, VALID_BEG_DATE,
                VALID_EXP_DATE, VALID_PRICE, VALID_ISSUES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, VALID_TAGS, VALID_INCOME, VALID_ACTUALSPENDING, VALID_EXPECTEDSPENDING,
                        VALID_AGE, VALID_BEG_DATE, VALID_EXP_DATE, VALID_PRICE, VALID_ISSUES);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_INCOME, VALID_ACTUALSPENDING, VALID_EXPECTEDSPENDING, VALID_AGE,
                VALID_BEG_DATE, VALID_EXP_DATE, VALID_PRICE, VALID_ISSUES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                        VALID_ADDRESS, VALID_TAGS, VALID_INCOME, VALID_ACTUALSPENDING, VALID_EXPECTEDSPENDING,
                        VALID_AGE, VALID_BEG_DATE, VALID_EXP_DATE, VALID_PRICE, VALID_ISSUES);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_TAGS, VALID_INCOME, VALID_ACTUALSPENDING, VALID_EXPECTEDSPENDING, VALID_AGE,
                VALID_BEG_DATE, VALID_EXP_DATE, VALID_PRICE, VALID_ISSUES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                        INVALID_ADDRESS, VALID_TAGS, VALID_INCOME, VALID_ACTUALSPENDING, VALID_EXPECTEDSPENDING,
                        VALID_AGE, VALID_BEG_DATE, VALID_EXP_DATE, VALID_PRICE, VALID_ISSUES);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                null, VALID_TAGS, VALID_INCOME, VALID_ACTUALSPENDING, VALID_EXPECTEDSPENDING, VALID_AGE,
                VALID_BEG_DATE, VALID_EXP_DATE, VALID_PRICE, VALID_ISSUES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, invalidTags, VALID_INCOME, VALID_ACTUALSPENDING, VALID_EXPECTEDSPENDING,
                        VALID_AGE, VALID_BEG_DATE, VALID_EXP_DATE, VALID_PRICE, VALID_ISSUES);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
