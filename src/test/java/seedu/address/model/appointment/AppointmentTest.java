package seedu.address.model.appointment;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AppointmentTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Appointment(null));
    }
}
