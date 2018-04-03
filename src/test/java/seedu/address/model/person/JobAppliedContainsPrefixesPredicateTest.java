package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class JobAppliedContainsPrefixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Software Engineer");
        List<String> secondPredicateKeywordList = Arrays.asList("Front-end Developer", "Data Analyst");

        JobAppliedContainsPrefixesPredicate firstPredicate =
                new JobAppliedContainsPrefixesPredicate(firstPredicateKeywordList);
        JobAppliedContainsPrefixesPredicate secondPredicate =
                new JobAppliedContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        JobAppliedContainsPrefixesPredicate firstPredicateCopy =
                new JobAppliedContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_jobAppliedContainsPrefixes_returnsTrue() {
        // One prefix
        JobAppliedContainsPrefixesPredicate predicate =
                new JobAppliedContainsPrefixesPredicate(Collections.singletonList("Soft"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Mixed-case prefix
        predicate = new JobAppliedContainsPrefixesPredicate(Arrays.asList("sOfT"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));
    }

    @Test
    public void test_jobAppliedDoesNotContainPrefixes_returnsFalse() {
        // Zero prefix
        JobAppliedContainsPrefixesPredicate predicate =
                new JobAppliedContainsPrefixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Non-matching prefix
        predicate = new JobAppliedContainsPrefixesPredicate(Arrays.asList("Data"));
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));
    }
}
