package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author jordancjq-reused
public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Test Remark");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same values -> returns true
        Remark sameRemark = new Remark(remark.value);
        assertTrue(remark.equals(sameRemark));

        // different types -> return false
        assertFalse(remark.equals(new Phone("999")));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different remark -> returns false
        Remark differentRemark = new Remark("Another Remark");
        assertFalse(remark.equals(differentRemark));

    }
}
