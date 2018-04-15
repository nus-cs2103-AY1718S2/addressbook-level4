package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.storage.XmlAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalAppointments.BIRTHDAY;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.EndDateTime;
import seedu.address.model.appointment.StartDateTime;
import seedu.address.model.appointment.Title;
import seedu.address.testutil.Assert;

//@@author trafalgarandre
public class XmlAdaptedAppointmentTest {
    private static final String INVALID_TITLE = "";
    private static final String INVALID_START_DATE_TIME = "2018-20-03";
    private static final String INVALID_END_DATE_TIME = "20-03-2018 08:00";

    private static final String VALID_TITLE = BIRTHDAY.getTitle().toString();
    private static final String VALID_START_DATE_TIME = BIRTHDAY.getStartDateTime().toString();
    private static final String VALID_END_DATE_TIME = BIRTHDAY.getEndDateTime().toString();

    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(BIRTHDAY);
        assertEquals(BIRTHDAY, appointment.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(INVALID_TITLE, VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = Title.MESSAGE_TITLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(null, VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidStartDateTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_TITLE, INVALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = StartDateTime.MESSAGE_START_DATE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appoinment =
                new XmlAdaptedAppointment(VALID_TITLE, null, VALID_END_DATE_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartDateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appoinment::toModelType);
    }

    @Test
    public void toModelType_invalidEndDateTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_TITLE, VALID_START_DATE_TIME, INVALID_END_DATE_TIME);
        String expectedMessage = EndDateTime.MESSAGE_END_DATE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_TITLE, VALID_START_DATE_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndDateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }
}
