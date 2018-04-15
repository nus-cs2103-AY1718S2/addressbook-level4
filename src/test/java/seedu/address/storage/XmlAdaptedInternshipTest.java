package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedInternship.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalInternships.ENGINEERING2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.internship.Address;
import seedu.address.model.internship.Email;
import seedu.address.model.internship.Industry;
import seedu.address.model.internship.Name;
import seedu.address.model.internship.Region;
import seedu.address.model.internship.Role;
import seedu.address.model.internship.Salary;
import seedu.address.testutil.Assert;

public class XmlAdaptedInternshipTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_SALARY = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_INDUSTRY = " ";
    private static final String INVALID_REGION = " Geylang";
    private static final String INVALID_ROLE = " ";
    private static final String INVALID_TAG = " ";

    private static final String VALID_NAME = ENGINEERING2.getName().toString();
    private static final String VALID_SALARY = ENGINEERING2.getSalary().toString();
    private static final String VALID_EMAIL = ENGINEERING2.getEmail().toString();
    private static final String VALID_ADDRESS = ENGINEERING2.getAddress().toString();
    private static final String VALID_INDUSTRY = ENGINEERING2.getIndustry().toString();
    private static final String VALID_REGION = ENGINEERING2.getRegion().toString();
    private static final String VALID_ROLE = ENGINEERING2.getRole().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = ENGINEERING2.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validInternshipDetails_returnsInternship() throws Exception {
        XmlAdaptedInternship internship = new XmlAdaptedInternship(ENGINEERING2);
        assertEquals(ENGINEERING2, internship.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(INVALID_NAME, VALID_SALARY, VALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        VALID_REGION, VALID_ROLE, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(null, VALID_SALARY, VALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        VALID_REGION, VALID_ROLE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidSalary_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, INVALID_SALARY, VALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        VALID_REGION, VALID_ROLE, VALID_TAGS);
        String expectedMessage = Salary.MESSAGE_SALARY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullSalary_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        VALID_REGION, VALID_ROLE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Salary.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, INVALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        VALID_REGION, VALID_ROLE, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, null, VALID_ADDRESS, VALID_INDUSTRY,
                        VALID_REGION, VALID_ROLE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, VALID_EMAIL, INVALID_ADDRESS, VALID_INDUSTRY,
                        VALID_REGION, VALID_ROLE, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, VALID_EMAIL, null, VALID_INDUSTRY,
                        VALID_REGION, VALID_ROLE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidIndustry_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, VALID_EMAIL, VALID_ADDRESS, INVALID_INDUSTRY,
                        VALID_REGION, VALID_ROLE, VALID_TAGS);
        String expectedMessage = Industry.MESSAGE_INDUSTRY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullIndustry_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, VALID_EMAIL, VALID_ADDRESS, null,
                        VALID_REGION, VALID_ROLE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Industry.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, VALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        INVALID_REGION, VALID_ROLE, VALID_TAGS);
        String expectedMessage = Region.MESSAGE_REGION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, VALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        null, VALID_ROLE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Region.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, VALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        VALID_REGION, INVALID_ROLE, VALID_TAGS);
        String expectedMessage = Role.MESSAGE_ROLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, VALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        VALID_REGION, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, VALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        VALID_REGION, VALID_ROLE, invalidTags);
        Assert.assertThrows(IllegalValueException.class, internship::toModelType);
    }

}
