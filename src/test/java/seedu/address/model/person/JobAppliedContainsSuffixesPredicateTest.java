package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class JobAppliedContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Software Engineer");
        List<String> secondPredicateKeywordList = Arrays.asList("Front-end Developer", "Data Analyst");

        JobAppliedContainsSuffixesPredicate firstPredicate =
                new JobAppliedContainsSuffixesPredicate(firstPredicateKeywordList);
        JobAppliedContainsSuffixesPredicate secondPredicate =
                new JobAppliedContainsSuffixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        JobAppliedContainsSuffixesPredicate firstPredicateCopy =
                new JobAppliedContainsSuffixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_jobAppliedContainsSuffixes_returnsTrue() {
        // One suffix
        JobAppliedContainsSuffixesPredicate predicate =
                new JobAppliedContainsSuffixesPredicate(Collections.singletonList("eer"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Mixed-case suffix
        predicate = new JobAppliedContainsSuffixesPredicate(Arrays.asList("EEr"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));
    }

    @Test
    public void test_jobAppliedDoesNotContainSuffixes_returnsFalse() {
        // Zero suffix
        JobAppliedContainsSuffixesPredicate predicate =
                new JobAppliedContainsSuffixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Non-matching suffix
        predicate = new JobAppliedContainsSuffixesPredicate(Arrays.asList("loper"));
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));
    }
}
