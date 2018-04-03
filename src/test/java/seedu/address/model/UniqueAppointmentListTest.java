package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;

public class UniqueAppointmentListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UniqueAppointmentList listToTest = new UniqueAppointmentList();

    @Test
    public void execute_addAppointment_addSuccessful() throws Exception {
        UniqueAppointmentList anotherList = new UniqueAppointmentList();
        Appointment appointment = new Appointment("3/4/2018 1130");
        listToTest.add(appointment);
        anotherList.add(appointment);
        assertEquals(anotherList, listToTest);
    }

    @Test
    public void execute_removeAppointment_removeSuccessful() throws Exception {
        UniqueAppointmentList anotherList = new UniqueAppointmentList();
        Appointment appointment = new Appointment("3/4/2018 1130");
        listToTest.add(appointment);
        anotherList.add(appointment);
        listToTest.remove(ParserUtil.parseIndex("1"));
        anotherList.remove(ParserUtil.parseIndex("1"));
        assertEquals(listToTest, anotherList);
    }

    @Test
    public void execute_duplicateAppointment_throwDUplicateException() throws Exception {
        Appointment appointment = new Appointment("3/4/2018 1130");
        listToTest.add(appointment);
        thrown.expect(UniqueAppointmentList.DuplicatedAppointmentException.class);
        listToTest.add(appointment);
    }

    @Test
    public void execute_setAppointmentList_setSucessful() {
        Set<Appointment> appointments = new HashSet<>(Arrays.asList(new Appointment("3/4/2018 1130")));
        UniqueAppointmentList anotherList = new UniqueAppointmentList();
        listToTest.setAppointment(appointments);
        anotherList.setAppointment(appointments);
        assertEquals(listToTest, anotherList);
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueAppointmentList uniqueAppointmentList = new UniqueAppointmentList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueAppointmentList.asObservableList().remove(0);
    }
}
