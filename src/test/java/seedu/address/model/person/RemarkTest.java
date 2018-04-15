package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author sherlynng
public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void checkRemarkToString() {
        assertTrue(new Remark("Friendly and patient.").toString() == "Friendly and patient.");
    }

    @Test
    public void checkRemarkEquality() {
        //test remark against non-remark type
        assertFalse(new Remark("Friendly and patient.").equals(null));
        assertFalse(new Remark("Friendly and patient.").equals(new Address("Friendly and patient.")));

        //test correctly returns equal if remark string is the same
        assertTrue(new Remark("Friendly and patient.").equals(new Remark("Friendly and patient.")));
    }

    @Test
    public void checkRemarkHashCode() {
        Remark remark = new Remark("");
        assertTrue(remark.hashCode() == remark.value.hashCode());
        remark = new Remark("Friendly and patient.");
        assertTrue(remark.hashCode() == remark.value.hashCode());
        remark = new Remark("Late and impatient tutor.");
        assertTrue(remark.hashCode() == remark.value.hashCode());
    }
}
