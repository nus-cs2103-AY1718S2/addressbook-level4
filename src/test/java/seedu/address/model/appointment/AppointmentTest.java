//@@author Kyholmes-test
package seedu.address.model.appointment;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AppointmentTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Appointment(null));
    }

    @Test
    public void test_getAppointmentDateTimeString() {
        String date = "3/4/2017";
        String time = "2217";
        String dateTimeString = "3/4/2017 2217";

        Appointment toTestAppt = new Appointment(dateTimeString);

        assertEquals(toTestAppt.getAppointmentDateTimeString(), date + " " + time);
    }
}
