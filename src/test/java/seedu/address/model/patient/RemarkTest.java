//@@author nhs-work
package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemarkTest {

    @Test
    public void isValidRemark() {
        // valid remarks
        assertTrue(Remark.isValidRemark("test"));
        assertTrue(Remark.isValidRemark("Shows up weekly for medication")); // long remark
        assertTrue(Remark.isValidRemark("a")); // one character
    }

    @Test
    public void equals() {
        Remark remark = new Remark("test");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same value -> returns true
        Remark remark2 = new Remark("test");
        assertTrue(remark.equals(remark2));

        // different value -> returns false
        Remark remark3 = new Remark("not test");
        assertFalse(remark.equals(remark3));

        // different objects -> returns false
        assertFalse(remark.equals(362));

        // null -> returns false
        assertFalse(remark.equals(null));
    }
}
