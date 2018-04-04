package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author tanhengyeow
public class MajorContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Computer");
        List<String> secondPredicateKeywordList = Arrays.asList("Computer", "Science");

        MajorContainsSubstringsPredicate firstPredicate =
                new MajorContainsSubstringsPredicate(firstPredicateKeywordList);
        MajorContainsSubstringsPredicate secondPredicate =
                new MajorContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MajorContainsSubstringsPredicate firstPredicateCopy =
                new MajorContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_majorContainsSubstrings_returnsTrue() {
        // One substring
        MajorContainsSubstringsPredicate predicate =
                new MajorContainsSubstringsPredicate(Collections.singletonList("ter Sci"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Mixed-case substring
        predicate = new MajorContainsSubstringsPredicate(Arrays.asList("TeR sCi"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }

    @Test
    public void test_majorDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        MajorContainsSubstringsPredicate predicate = new MajorContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Non-matching substring
        predicate = new MajorContainsSubstringsPredicate(Arrays.asList("mation Secu"));
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }
}
