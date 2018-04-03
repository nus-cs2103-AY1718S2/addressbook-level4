package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;

//@@author trafalgarandre
/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {
    public static final Appointment BIRTHDAY = new AppointmentBuilder()
            .withTitle("Birthday").withStartDateTime("2018-03-26 00:00").withEndDateTime("2018-03-26 23:59").build();
    public static final Appointment MEETING = new AppointmentBuilder()
            .withTitle("Meeting").withStartDateTime("2018-04-20 10:00").withEndDateTime("2018-04-20 12:00").build();

    private TypicalAppointments() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical appointments.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Appointment appointment : getTypicalAppointments()) {
            try {
                ab.addAppointment(appointment);
            } catch (DuplicateAppointmentException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(BIRTHDAY, MEETING));
    }
}
