package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersonsAndAppointments.BENSON_APPT;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.EndTime;
import seedu.address.model.appointment.Location;
import seedu.address.model.appointment.PersonName;
import seedu.address.model.appointment.StartTime;
import seedu.address.testutil.Assert;

//@@author jlks96
public class XmlAdaptedAppointmentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DATE = "12/34";
    private static final String INVALID_START_TIME = "26:80";
    private static final String INVALID_END_TIME = "0000";
    private static final String INVALID_LOCATION = " ";

    private static final String VALID_NAME = BENSON_APPT.getName().toString();
    private static final String VALID_DATE = BENSON_APPT.getDate().toString();
    private static final String VALID_START_TIME = BENSON_APPT.getStartTime().toString();
    private static final String VALID_END_TIME = BENSON_APPT.getEndTime().toString();
    private static final String VALID_LOCATION = BENSON_APPT.getLocation().toString();


    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(BENSON_APPT);
        assertEquals(BENSON_APPT, appointment.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(INVALID_NAME, VALID_DATE, VALID_START_TIME, VALID_END_TIME, VALID_LOCATION);
        String expectedMessage = PersonName.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(null, VALID_DATE, VALID_START_TIME, VALID_END_TIME, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PersonName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, INVALID_DATE, VALID_START_TIME, VALID_END_TIME, VALID_LOCATION);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, null, VALID_START_TIME, VALID_END_TIME, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, VALID_DATE, INVALID_START_TIME, VALID_END_TIME, VALID_LOCATION);
        String expectedMessage = StartTime.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, VALID_DATE, null, VALID_END_TIME, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, VALID_DATE, VALID_START_TIME, INVALID_END_TIME, VALID_LOCATION);
        String expectedMessage = EndTime.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, VALID_DATE, VALID_START_TIME, null, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, VALID_DATE, VALID_START_TIME, VALID_END_TIME, INVALID_LOCATION);
        String expectedMessage = Location.MESSAGE_LOCATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment =
                new XmlAdaptedAppointment(VALID_NAME, VALID_DATE, VALID_START_TIME, VALID_END_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

}
