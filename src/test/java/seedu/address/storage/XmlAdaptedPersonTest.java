package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.testutil.Assert;

public class XmlAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_NRIC = "+651234";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final String VALID_CCA = BENSON.getCca().value;
    private static final String VALID_CCA_POS = BENSON.getCca().pos;
    private static final String VALID_INJURIES_HISTORY = BENSON.getInjuriesHistory().toString();
    private static final String VALID_NEXT_OF_KIN = BENSON.getNextOfKin().toString();
    private static final String VALID_NAME_OF_KIN = BENSON.getNextOfKin().fullName;
    private static final String VALID_PHONE_OF_KIN = BENSON.getNextOfKin().phone;
    private static final String VALID_EMAIL_OF_KIN = BENSON.getNextOfKin().email;
    private static final String VALID_RELATIONSHIP_OF_KIN = BENSON.getNextOfKin().remark;

    /*private static final List<XmlAdaptedSubject> VALID_SUBJECTS = BENSON.getSubjects().stream()
            .map(XmlAdaptedSubject::new)
            .collect(Collectors.toList());*/

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(INVALID_NAME, VALID_NRIC, VALID_TAGS, null, VALID_REMARK,
                                    VALID_CCA, VALID_INJURIES_HISTORY, VALID_NEXT_OF_KIN);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(null, VALID_NRIC, VALID_TAGS, null, VALID_REMARK,
                                                        VALID_CCA, VALID_INJURIES_HISTORY, VALID_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, INVALID_NRIC, VALID_TAGS, null, VALID_REMARK,
                                    VALID_CCA, VALID_INJURIES_HISTORY, VALID_NAME);
        String expectedMessage = Nric.MESSAGE_NRIC_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, null, VALID_TAGS, null, VALID_REMARK,
                                                        VALID_CCA, VALID_INJURIES_HISTORY, VALID_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_NRIC, invalidTags, null, VALID_REMARK, VALID_CCA,
                                        VALID_INJURIES_HISTORY, VALID_NAME);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
