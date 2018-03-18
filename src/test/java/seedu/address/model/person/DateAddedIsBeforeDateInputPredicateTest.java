package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.Test;

public class DateAddedIsBeforeDateInputPredicateTest {
    @Test
    public void equals() {

        DateAddedIsBeforeDateInputPredicate firstPredicate = new
                DateAddedIsBeforeDateInputPredicate("03/03/2018");
        DateAddedIsBeforeDateInputPredicate secondPredicate = new
                DateAddedIsBeforeDateInputPredicate("01/01/2018");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DateAddedIsBeforeDateInputPredicate firstPredicateCopy = new
                DateAddedIsBeforeDateInputPredicate("03/03/2018");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different input date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateAddedIsBeforeDateInput_returnsTrue() {
        DateAddedIsBeforeDateInputPredicate predicate = new
                DateAddedIsBeforeDateInputPredicate("03/03/2018");
        assertTrue(predicate.test(AMY));
    }

    @Test
    public void test_dateAddedIsAfterDateInput_returnsFalse() {
        DateAddedIsBeforeDateInputPredicate predicate = new
                DateAddedIsBeforeDateInputPredicate("01/01/2018");
        assertFalse(predicate.test(AMY));
    }
}
