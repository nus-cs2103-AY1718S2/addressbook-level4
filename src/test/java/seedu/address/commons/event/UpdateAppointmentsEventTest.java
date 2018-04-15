package seedu.address.commons.event;

import static seedu.address.testutil.TypicalPersonsAndAppointments.getTypicalAddressBook;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.events.logic.UpdateAppointmentsEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;

//@@author jlks96
public class UpdateAppointmentsEventTest {

    @Test
    public void getAppointmentAdded_validAppointment_success() {
        AddressBook addressBook = getTypicalAddressBook();
        ObservableList<Appointment> appointments = addressBook.getAppointmentList();
        UpdateAppointmentsEvent event = new UpdateAppointmentsEvent(appointments);
        assert(event.getUpdatedAppointments().equals(appointments));
    }

    @Test
    public void toString_comparedWithClassName_success() {
        AddressBook addressBook = getTypicalAddressBook();
        ObservableList<Appointment> appointments = addressBook.getAppointmentList();
        UpdateAppointmentsEvent event = new UpdateAppointmentsEvent(appointments);
        assert(event.toString().equals("UpdateAppointmentsEvent"));
    }
}
