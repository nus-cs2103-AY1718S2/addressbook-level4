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
import seedu.address.model.person.Company;
import seedu.address.model.person.CurrentPosition;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicture;
import seedu.address.testutil.Assert;

public class XmlAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_CURRENT_POSITION = "";
    private static final String INVALID_COMPANY = "G@@gle";
    private static final String INVALID_PROFILE_PICTURE = "Rachel.jpx";
    private static final String INVALID_TAG = "#friend";
    private static final String NON_EXIST_PROFILE_PICTURE = "src/test/data/images/Rachel.jpeg";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_CURRENT_POSITION = BENSON.getCurrentPosition().toString();
    private static final String VALID_COMPANY = BENSON.getCompany().toString();
    private static final String VALID_PROFILE_PICTURE = BENSON.getProfilePicture().toString();
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
                new XmlAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_CURRENT_POSITION,
                        VALID_COMPANY, VALID_PROFILE_PICTURE, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_CURRENT_POSITION,
                        VALID_COMPANY, VALID_PROFILE_PICTURE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_CURRENT_POSITION,
                        VALID_COMPANY, VALID_PROFILE_PICTURE, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_CURRENT_POSITION,
                        VALID_COMPANY, VALID_PROFILE_PICTURE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_CURRENT_POSITION,
                        VALID_COMPANY, VALID_PROFILE_PICTURE, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_CURRENT_POSITION,
                        VALID_COMPANY, VALID_PROFILE_PICTURE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_CURRENT_POSITION,
                        VALID_COMPANY, VALID_PROFILE_PICTURE, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_CURRENT_POSITION,
                        VALID_COMPANY, VALID_PROFILE_PICTURE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidCurrentPosition_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_CURRENT_POSITION,
                        VALID_COMPANY, VALID_PROFILE_PICTURE, VALID_TAGS);
        String expectedMessage = CurrentPosition.MESSAGE_CURRENT_POSITION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullCurrentPosition_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null,
                        VALID_COMPANY, VALID_PROFILE_PICTURE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CurrentPosition.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_CURRENT_POSITION,
                        INVALID_COMPANY, VALID_PROFILE_PICTURE, VALID_TAGS);
        String expectedMessage = Company.MESSAGE_COMPANY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullCompany_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_CURRENT_POSITION, null, VALID_PROFILE_PICTURE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidProfilePicture_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_CURRENT_POSITION,
                        VALID_COMPANY, INVALID_PROFILE_PICTURE, VALID_TAGS);
        String expectedMessage = ProfilePicture.MESSAGE_PROFILEPICTURE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nonExistProfilePicture_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_CURRENT_POSITION,
                        VALID_COMPANY, NON_EXIST_PROFILE_PICTURE, VALID_TAGS);
        String expectedMessage = ProfilePicture.MESSAGE_PROFILEPICTURE_NOT_EXISTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_CURRENT_POSITION,
                        VALID_COMPANY, VALID_PROFILE_PICTURE, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
