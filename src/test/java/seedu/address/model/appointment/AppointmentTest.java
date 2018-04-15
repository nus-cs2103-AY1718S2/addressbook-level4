package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.appointment.Appointment.MESSAGE_TIMES_CONSTRAINTS;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author jlks96
public class AppointmentTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final PersonName name = new PersonName("ALICE");
    private final Date date = new Date("01/01/2017");
    private final StartTime startTime = new StartTime("12:30");
    private final EndTime endTime = new EndTime("13:30");
    private final EndTime invalidEndTime = new EndTime("11:30");
    private final Location location = new Location("Gold Park Mall");
    private final Appointment appointment = new Appointment(name, date, startTime, endTime, location);

    @Test
    public void isEqual_sameAppointment_success() {
        assertTrue(new Appointment(name, date, startTime, endTime, location).equals(appointment));
    }

    @Test
    public void isEqual_compareNull_failure() {
        assertFalse(new Appointment(name, date, startTime, endTime, location).equals(null));
    }

    @Test
    public void getters_validAppointment_success() {
        assertTrue(appointment.getName().equals(name));
        assertTrue(appointment.getStartTime().equals(startTime));
        assertTrue(appointment.getEndTime().equals(endTime));
        assertTrue(appointment.getDate().equals(date));
        assertTrue(appointment.getLocation().equals(location));
    }

    @Test
    public void areValidTimes_invalidTimes_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_TIMES_CONSTRAINTS);
        new Appointment(name, date, startTime, invalidEndTime, location);
    }

    @Test
    public void toString_validAppointment_success() {
        assertTrue(appointment.toString().equals(name + " Date: " + date + " Start Time: " + startTime
                + " End Time: " + endTime + " Location: " + location));
    }

    @Test
    public void toStringList_validAppointment_success() {
        final List<String> expectedStringList = new ArrayList<>();
        expectedStringList.add(name.toString());
        expectedStringList.add(date.toString());
        expectedStringList.add(startTime.toString());
        expectedStringList.add(endTime.toString());
        expectedStringList.add(location.toString());
        assertTrue(appointment.toStringList().equals(expectedStringList));
    }

}
