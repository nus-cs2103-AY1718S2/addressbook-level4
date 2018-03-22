package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalAppointments.ALLY;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Remark;
import seedu.address.model.person.Name;
import seedu.address.testutil.Assert;

public class XmlAdaptedAppointmentTest {
    private static final String INVALID_OWNER = "R@chel";
    private static final String INVALID_REMARK = " ";
    private static final String INVALID_DATETIME = "MAAAY 2018 8PM";
    private static final String INVALID_TYPE = "#checkup";

    private static final String VALID_OWNER = ALLY.getOwner().toString();
    private static final String VALID_REMARK = ALLY.getRemark().toString();
    private static final String VALID_DATETIME = ALLY.getFormattedLocalDateTime();
    private static final List<XmlAdaptedTag> VALID_TYPE = ALLY.getType().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(ALLY);
        assertEquals(ALLY, appointment.toModelType());
    }

    @Test
    public void toModelType_invalidOwner_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(INVALID_OWNER, VALID_REMARK, VALID_DATETIME, VALID_TYPE);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(null, VALID_REMARK, VALID_DATETIME, VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidRemark_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_OWNER, INVALID_REMARK, VALID_DATETIME, VALID_TYPE);
        String expectedMessage = Remark.MESSAGE_REMARK_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_OWNER, null, VALID_DATETIME, VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_OWNER, VALID_REMARK, INVALID_DATETIME, VALID_TYPE);
        String expectedMessage = "Please follow the format of yyyy-MM-dd HH:mm";
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(VALID_OWNER, VALID_REMARK,
                null, VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TYPE);
        invalidTags.add(new XmlAdaptedTag(INVALID_TYPE));
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_OWNER, VALID_REMARK, VALID_DATETIME, invalidTags);
        Assert.assertThrows(IllegalValueException.class, appointment::toModelType);
    }

}
