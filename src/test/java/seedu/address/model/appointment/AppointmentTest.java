package seedu.address.model.appointment;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author trafalgarandre
public class AppointmentTest {

    @Test
    public void constructor_invalidAppointment_throwsIllegalArgumentException() {
        Title validTitle = new Title("Meeting");
        StartDateTime largerSdt = new StartDateTime("2018-03-26 13:00");
        EndDateTime smallerEdt = new EndDateTime("2018-03-26 12:30"); // edt < sdt
        Assert.assertThrows(IllegalArgumentException.class, () -> new Appointment(validTitle, largerSdt, smallerEdt));
    }
}
