package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author cambioforma
public class AppointmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Birthday(null));
    }

    @Test
    public void constructor_invalidAppointment_throwsIllegalArgumentException() {
        String invalidAppointment = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Appointment(invalidAppointment));
    }

    @Test
    public void isValidAppointment() {
        // invalid appointment dates
        assertFalse(Appointment.isValidAppointment(" ")); // spaces only
        assertFalse(Appointment.isValidAppointment("20052018")); // without dashes
        assertFalse(Appointment.isValidAppointment("appointment")); // non-numeric
        assertFalse(Appointment.isValidAppointment("2018-09-12")); // yyyy-MM-dd format
        assertFalse(Appointment.isValidAppointment("19- 12-2018")); // spaces within date
        assertFalse(Appointment.isValidAppointment("00-12-2018")); // invalid day
        assertFalse(Appointment.isValidAppointment("19-13-2018")); // invalid month
        assertFalse(Appointment.isValidAppointment("19-12-0000")); // invalid year

        // valid appointment dates
        assertTrue(Appointment.isValidAppointment(null)); // null
        assertTrue(Appointment.isValidAppointment("")); // empty string
        assertTrue(Appointment.isValidAppointment("01-01-2018"));
        assertTrue(Appointment.isValidAppointment("30-12-1990"));
        assertTrue(Appointment.isValidAppointment("29-02-2016")); //leap year
    }
}
