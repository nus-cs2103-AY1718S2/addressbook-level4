package seedu.address.model.appointment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author wynonaK
public class UniqueAppointmentListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueAppointmentList uniquePersonList = new UniqueAppointmentList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asObservableList().remove(0);
    }
}
