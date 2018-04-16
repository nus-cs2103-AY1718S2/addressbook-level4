package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalAppointmentEntires.MEET_JAMES;

import org.junit.Test;
//@@author yuxiangSg
public class XmlAdaptedAppointmentEntryTest {

    @Test
    public void toModelType_validAppointDetails_returnsAppointment() throws Exception {
        XmlAdptedAppointmentEntry entry = new XmlAdptedAppointmentEntry(MEET_JAMES);
        assertEquals(MEET_JAMES, entry.toModelType());
    }
}
