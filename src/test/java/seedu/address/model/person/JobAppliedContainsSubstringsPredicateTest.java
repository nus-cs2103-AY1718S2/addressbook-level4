package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class JobAppliedContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Software Engineer");
        List<String> secondPredicateKeywordList = Arrays.asList("Front-end Developer", "Data Analyst");

        JobAppliedContainsSubstringsPredicate firstPredicate =
                new JobAppliedContainsSubstringsPredicate(firstPredicateKeywordList);
        JobAppliedContainsSubstringsPredicate secondPredicate =
                new JobAppliedContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        JobAppliedContainsSubstringsPredicate firstPredicateCopy =
                new JobAppliedContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_jobAppliedContainsSubstrings_returnsTrue() {
        // One substring
        JobAppliedContainsSubstringsPredicate predicate =
                new JobAppliedContainsSubstringsPredicate(Collections.singletonList("are Engin"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Mixed-case substring
        predicate = new JobAppliedContainsSubstringsPredicate(Arrays.asList("ArE EnGiNe"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));
    }

    @Test
    public void test_jobAppliedDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        JobAppliedContainsSubstringsPredicate predicate =
                new JobAppliedContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Non-matching substring
        predicate = new JobAppliedContainsSubstringsPredicate(Arrays.asList("are Dev"));
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));
   }
}
