package seedu.address.model.person.Customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import seedu.address.model.person.customer.LateInterest;

//@@author jonleeyz
public class LateInterestTest {
    @Test
    public void testToString_success() {
        assertEquals("9.71", new LateInterest(9.71).toString());
    }

    @Test
    public void testHashcode_symmetric() {
        LateInterest lateInterestA = new LateInterest();
        LateInterest lateInterestB = new LateInterest();
        LateInterest lateInterestC = new LateInterest(9.71);
        LateInterest lateInterestD = new LateInterest(9.71);

        assertEquals(lateInterestA.hashCode(), lateInterestB.hashCode());
        assertEquals(lateInterestC.hashCode(), lateInterestD.hashCode());
        assertNotEquals(lateInterestA.hashCode(), lateInterestC.hashCode());
        assertNotEquals(lateInterestA.hashCode(), lateInterestD.hashCode());
        assertNotEquals(lateInterestB.hashCode(), lateInterestC.hashCode());
        assertNotEquals(lateInterestB.hashCode(), lateInterestD.hashCode());
    }
}
//@@author
