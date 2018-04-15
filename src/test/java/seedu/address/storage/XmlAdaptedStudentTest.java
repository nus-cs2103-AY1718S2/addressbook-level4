package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalStudents.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.programminglanguage.ProgrammingLanguage;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.UniqueKey;
import seedu.address.testutil.Assert;

public class XmlAdaptedStudentTest {
    private static final String INVALID_KEY = "bcdefg";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_SUBJECT = "\t";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_PROFILEPICTUREPATH = "invalid";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_KEY = BENSON.getUniqueKey().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_SUBJECT = BENSON.getProgrammingLanguage().toString();
    private static final String VALID_FAVOURITE = BENSON.getFavourite().toString();
    private static final String VALID_PROFILEPICTUREPATH = BENSON.getProfilePicturePath().toString();
    private static final XmlAdaptedMiscInfo VALID_MISCELLANEOUS = new XmlAdaptedMiscInfo(BENSON.getMiscellaneousInfo());
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    private static final XmlAdaptedDashboard VALID_DASHBOARD = new XmlAdaptedDashboard(BENSON.getDashboard());

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        XmlAdaptedStudent student = new XmlAdaptedStudent(BENSON);
        assertEquals(BENSON, student.toModelType());
    }

    //@@author demitycho
    @Test
    public void toModelType_invalidKey_throwsIllegalValueException() {
        XmlAdaptedStudent student =
                new XmlAdaptedStudent(INVALID_KEY, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, VALID_SUBJECT, VALID_TAGS, VALID_FAVOURITE, VALID_PROFILEPICTUREPATH,
                        VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = UniqueKey.MESSAGE_UNIQUE_KEY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }
    //@@author

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedStudent student =
                new XmlAdaptedStudent(VALID_KEY, INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, VALID_SUBJECT, VALID_TAGS, VALID_FAVOURITE, VALID_PROFILEPICTUREPATH,
                        VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_KEY, null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_SUBJECT, VALID_TAGS, VALID_FAVOURITE, VALID_PROFILEPICTUREPATH,
                VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_KEY, VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_SUBJECT, VALID_TAGS, VALID_FAVOURITE, VALID_PROFILEPICTUREPATH,
                VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_KEY, VALID_NAME, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_SUBJECT, VALID_TAGS, VALID_FAVOURITE, VALID_PROFILEPICTUREPATH,
                VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedStudent student =
                new XmlAdaptedStudent(VALID_KEY, VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_SUBJECT, VALID_TAGS, VALID_FAVOURITE, VALID_PROFILEPICTUREPATH,
                        VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_KEY, VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_SUBJECT, VALID_TAGS, VALID_FAVOURITE, VALID_PROFILEPICTUREPATH,
                VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_KEY, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                INVALID_ADDRESS, VALID_SUBJECT, VALID_TAGS, VALID_FAVOURITE, VALID_PROFILEPICTUREPATH,
                VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_KEY, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                null, VALID_SUBJECT, VALID_TAGS, VALID_FAVOURITE, VALID_PROFILEPICTUREPATH,
                VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidSubject_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_KEY, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, INVALID_SUBJECT, VALID_TAGS, VALID_FAVOURITE, VALID_PROFILEPICTUREPATH,
                VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = ProgrammingLanguage.MESSAGE_PROGRAMMING_LANGUAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_KEY, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_TAGS, VALID_FAVOURITE, VALID_PROFILEPICTUREPATH,
                VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ProgrammingLanguage.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_KEY, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_SUBJECT, invalidTags, VALID_FAVOURITE, VALID_PROFILEPICTUREPATH,
                VALID_DASHBOARD, VALID_MISCELLANEOUS);
        Assert.assertThrows(IllegalValueException.class, student::toModelType);
    }

    //@@author samuelloh
    @Test
    public void toModelType_nullPicturePath_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_KEY, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_TAGS, VALID_FAVOURITE, INVALID_PROFILEPICTUREPATH,
                VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ProgrammingLanguage.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }
}
