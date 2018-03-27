package seedu.address.model.appointment;

import org.junit.Test;

import seedu.address.model.calendar.AppointmentEntry;
import seedu.address.testutil.Assert;

public class AppointmentEntryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AppointmentEntry(null));
    }
}
