package seedu.address.model.person.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

//@@author jonleeyz
public class MoneyBorrowedTest {
    @Test
    public void testToString_success() {
        assertEquals("9.71", new MoneyBorrowed(9.71).toString());
    }

    @Test
    public void testHashcode_symmetric() {
        MoneyBorrowed moneyBorrowedA = new MoneyBorrowed();
        MoneyBorrowed moneyBorrowedB = new MoneyBorrowed();
        MoneyBorrowed moneyBorrowedC = new MoneyBorrowed(9.71);
        MoneyBorrowed moneyBorrowedD = new MoneyBorrowed(9.71);

        assertEquals(moneyBorrowedA.hashCode(), moneyBorrowedB.hashCode());
        assertEquals(moneyBorrowedC.hashCode(), moneyBorrowedD.hashCode());
        assertNotEquals(moneyBorrowedA.hashCode(), moneyBorrowedC.hashCode());
        assertNotEquals(moneyBorrowedA.hashCode(), moneyBorrowedD.hashCode());
        assertNotEquals(moneyBorrowedB.hashCode(), moneyBorrowedC.hashCode());
        assertNotEquals(moneyBorrowedB.hashCode(), moneyBorrowedD.hashCode());
    }
}
//@@author
