package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Imdb;
import seedu.address.model.appointment.Appointment;

/**
 * A utility class containing a list of {@code Appointment} objects to be userd in tests.
 */
public class TypicalAppointments {

    public static final Appointment ALICE = new AppointmentBuilder().withPatientName("Alice Pauline")
            .withDateTime("2018-04-28 9:30").build();
    public static final Appointment BENSON = new AppointmentBuilder().withPatientName("Benson Meier")
            .withDateTime("2018-03-28 17:00").build();
    public static final Appointment CARL = new AppointmentBuilder().withPatientName("Carl Kurz")
            .withDateTime("2018-04-18 11:00").build();
    public static final Appointment DANIEL = new AppointmentBuilder().withPatientName("Daniel Meier")
            .withDateTime("2018-03-21 19:30").build();
    public static final Appointment ELLE = new AppointmentBuilder().withPatientName("Elle Meyer")
            .withDateTime("2018-04-01 20:00").build();
    public static final Appointment FIONA = new AppointmentBuilder().withPatientName("Fiona Kunz")
            .withDateTime("2018-03-18 9:30").build();
    public static final Appointment GEORGE = new AppointmentBuilder().withPatientName("George Best")
            .withDateTime("2018-04-28 14:00").build();

    private TypicalAppointments() {}

    /**
     * Returns an {@code Imdb} with all the typical appointments.
     */
    public static Imdb getTypicalAddressBook() {
        Imdb ab = new Imdb();

        return ab;
    }

    public static List<Appointment> getTypicalAppointment() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
