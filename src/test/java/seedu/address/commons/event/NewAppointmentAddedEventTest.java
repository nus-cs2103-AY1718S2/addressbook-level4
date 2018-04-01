package seedu.address.commons.event;

import org.junit.Test;

import seedu.address.commons.events.model.NewAppointmentAddedEvent;
import seedu.address.model.appointment.Appointment;
import seedu.address.testutil.AppointmentBuilder;

//@@author jlks96
public class NewAppointmentAddedEventTest {

    @Test
    public void getAppointmentAdded_validAppointment_success() {
        Appointment appointment = new AppointmentBuilder().build();
        NewAppointmentAddedEvent event = new NewAppointmentAddedEvent(appointment);
        assert(event.getAppointmentAdded().equals(appointment));
    }
}
