package seedu.address.commons.event;

import org.junit.Test;

import seedu.address.commons.events.ui.AppointmentDeletedEvent;
import seedu.address.model.appointment.Appointment;
import seedu.address.testutil.AppointmentBuilder;

public class AppointmentDeletedEventTest {

    @Test
    public void getAppointmentDeleted_validAppointment_success() {
        Appointment appointment = new AppointmentBuilder().build();
        AppointmentDeletedEvent event = new AppointmentDeletedEvent(appointment);
        assert(event.getAppointmentDeleted().equals(appointment));
    }
}
