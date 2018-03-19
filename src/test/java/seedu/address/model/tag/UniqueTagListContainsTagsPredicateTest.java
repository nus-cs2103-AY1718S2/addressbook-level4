package seedu.address.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalTags.TAG_SET_FRIEND;
import static seedu.address.testutil.TypicalTags.TAG_SET_HUSBAND;
import static seedu.address.testutil.TypicalTags.TAG_SET_OWES_MONEY_FRIEND;

import org.junit.Test;

public class UniqueTagListContainsTagsPredicateTest {
    @Test
    public void equals() {

        UniqueTagListContainsTagsPredicate firstPredicate = new
                UniqueTagListContainsTagsPredicate(TAG_SET_FRIEND);
        UniqueTagListContainsTagsPredicate secondPredicate = new
                UniqueTagListContainsTagsPredicate(TAG_SET_OWES_MONEY_FRIEND);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UniqueTagListContainsTagsPredicate firstPredicateCopy = new
                UniqueTagListContainsTagsPredicate(TAG_SET_FRIEND);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different input date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_uniqueTagListContainsTags_returnsTrue() {
        UniqueTagListContainsTagsPredicate predicate = new
                UniqueTagListContainsTagsPredicate(TAG_SET_HUSBAND);
        assertTrue(predicate.test(BOB));
    }

    @Test
    public void test_uniqueTagListDoesNotContainTags_returnsFalse() {
        UniqueTagListContainsTagsPredicate predicate = new
                UniqueTagListContainsTagsPredicate(TAG_SET_OWES_MONEY_FRIEND);
        assertFalse(predicate.test(AMY));
    }
}
