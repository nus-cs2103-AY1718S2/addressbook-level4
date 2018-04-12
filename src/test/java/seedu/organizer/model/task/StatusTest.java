package seedu.organizer.model.task;

//@@author agus
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StatusTest {

    @Test
    public void comparator_allCombination() {
        Status sTrue1 = new Status(true);
        Status sTrue2 = new Status(true);
        Status sFalse2 = new Status(false);
        Status sFalse1 = new Status(false);

        assertTrue(sTrue1.equals(sTrue1));
        assertTrue(sTrue1.equals(sTrue2));
        assertFalse(sTrue1.equals(sFalse2));

        assertTrue(sFalse2.equals(sFalse2));
        assertTrue(sFalse2.equals(sFalse1));
        assertFalse(sFalse2.equals(sTrue1));
    }

    public void getInverse_allCombination() {
        Status sTrue = new Status(true);
        Status sFalse = new Status(false);

        assertEquals(sTrue.getInverse(), sFalse);
        assertEquals(sFalse.getInverse(), sTrue);
    }
}
