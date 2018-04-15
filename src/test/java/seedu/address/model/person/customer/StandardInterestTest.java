package seedu.address.model.person.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

//@@author jonleeyz
public class StandardInterestTest {
    @Test
    public void testToString_success() {
        assertEquals("9.71", new StandardInterest(9.71).toString());
    }

    @Test
    public void testHashcode_symmetric() {
        StandardInterest standardInterestA = new StandardInterest();
        StandardInterest standardInterestB = new StandardInterest();
        StandardInterest standardInterestC = new StandardInterest(9.71);
        StandardInterest standardInterestD = new StandardInterest(9.71);

        assertEquals(standardInterestA.hashCode(), standardInterestB.hashCode());
        assertEquals(standardInterestC.hashCode(), standardInterestD.hashCode());
        assertNotEquals(standardInterestA.hashCode(), standardInterestC.hashCode());
        assertNotEquals(standardInterestA.hashCode(), standardInterestD.hashCode());
        assertNotEquals(standardInterestB.hashCode(), standardInterestC.hashCode());
        assertNotEquals(standardInterestB.hashCode(), standardInterestD.hashCode());
    }
}
//@@author
