package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author tanhengyeow
public class UniversityContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("NUS");
        List<String> secondPredicateKeywordList = Arrays.asList("NUS", "NTU");

        UniversityContainsSuffixesPredicate firstPredicate =
                new UniversityContainsSuffixesPredicate(firstPredicateKeywordList);
        UniversityContainsSuffixesPredicate secondPredicate =
                new UniversityContainsSuffixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UniversityContainsSuffixesPredicate firstPredicateCopy =
                new UniversityContainsSuffixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_universityContainsSuffixes_returnsTrue() {
        // One suffix
        UniversityContainsSuffixesPredicate predicate =
                new UniversityContainsSuffixesPredicate(Collections.singletonList("US"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));

        // Mixed-case substring
        predicate = new UniversityContainsSuffixesPredicate(Arrays.asList("uS"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));
    }

    @Test
    public void test_universityDoesNotContainSuffixes_returnsFalse() {
        // Zero suffix
        UniversityContainsSuffixesPredicate predicate =
                new UniversityContainsSuffixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withUniversity("NUS").build()));

        // Non-matching suffix
        predicate = new UniversityContainsSuffixesPredicate(Arrays.asList("yale"));
        assertFalse(predicate.test(new PersonBuilder().withUniversity("NUS").build()));
    }
}
