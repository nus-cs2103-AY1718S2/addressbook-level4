package seedu.address.commons.event;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPT;
import static seedu.address.testutil.TypicalPersonsAndAppointments.getTypicalAddressBook;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.events.model.AppointmentDeletedEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;

//@@author jlks96
public class AppointmentDeletedEventTest {

    @Test
    public void getAppointmentDeleted_validAppointment_success() throws AppointmentNotFoundException {
        AddressBook addressBook = getTypicalAddressBook();
        addressBook.removeAppointment(addressBook.getAppointmentList().get(INDEX_FIRST_APPT.getZeroBased()));
        ObservableList<Appointment> appointments = addressBook.getAppointmentList();
        AppointmentDeletedEvent event = new AppointmentDeletedEvent(appointments);
        assert(event.getUpdatedAppointments().equals(appointments));
    }
}
