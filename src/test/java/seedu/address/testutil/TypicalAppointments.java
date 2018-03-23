package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.appointment.Appointment;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment ALLY = new AppointmentBuilder().withOwner(TypicalPersons.ALICE)
            .withRemark("Requires Home Visit").withDateTime("2018-12-31 12:30")
            .withTags("checkup").build();
    public static final Appointment BENNY = new AppointmentBuilder().withOwner(TypicalPersons.BENSON)
            .withRemark("May require isolation").withDateTime("2018-12-31 14:30")
            .withTags("surgery").build();

    private TypicalAppointments() {} // prevents instantiation

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(ALLY, BENNY));
    }
}
