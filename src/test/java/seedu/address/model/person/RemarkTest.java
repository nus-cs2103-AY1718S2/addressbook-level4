package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author tiny767
public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Like fishing");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        // different types -> returns false
        assertFalse(remark.equals(1));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different remark -> returns false
        Remark differentRemark = new Remark("Like walking");
        assertFalse(remark.equals(differentRemark));
    }
}
