package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalTags.TAG_SET_FRIEND;
import static seedu.address.testutil.TypicalTags.TAG_SET_HUSBAND;

import org.junit.Test;

public class PersonIsAddedBeforeDateInputAndContainsTagsPredicateTest {
    @Test
    public void equals() {

        PersonIsAddedBeforeDateInputAndContainsTagsPredicate firstPredicate = new
                PersonIsAddedBeforeDateInputAndContainsTagsPredicate(TAG_SET_FRIEND, "01/01/2018");
        PersonIsAddedBeforeDateInputAndContainsTagsPredicate secondPredicate = new
                PersonIsAddedBeforeDateInputAndContainsTagsPredicate(TAG_SET_HUSBAND, "03/03/2018");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonIsAddedBeforeDateInputAndContainsTagsPredicate firstPredicateCopy = new
                PersonIsAddedBeforeDateInputAndContainsTagsPredicate(TAG_SET_FRIEND, "01/01/2018");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different input date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personIsAddedBeforeDateInputAndContainsTags_returnsTrue() {
        PersonIsAddedBeforeDateInputAndContainsTagsPredicate predicate = new
                PersonIsAddedBeforeDateInputAndContainsTagsPredicate(TAG_SET_HUSBAND, "03/03/2018");
        assertTrue(predicate.test(BOB));
    }

    @Test
    public void test_personDoesNotContainTags_returnsFalse() {
        PersonIsAddedBeforeDateInputAndContainsTagsPredicate predicate = new
                PersonIsAddedBeforeDateInputAndContainsTagsPredicate(TAG_SET_FRIEND, "03/03/2018");
        assertFalse(predicate.test(BOB));
    }

    @Test
    public void test_personIsNotAddedBeforeDateInput_returnsFalse() {
        PersonIsAddedBeforeDateInputAndContainsTagsPredicate predicate = new
                PersonIsAddedBeforeDateInputAndContainsTagsPredicate(TAG_SET_HUSBAND, "01/01/2018");
        assertFalse(predicate.test(BOB));
    }
}
