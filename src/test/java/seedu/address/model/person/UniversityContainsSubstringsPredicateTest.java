package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author tanhengyeow
public class UniversityContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("NUS");
        List<String> secondPredicateKeywordList = Arrays.asList("NUS", "NTU");

        UniversityContainsSubstringsPredicate firstPredicate =
                new UniversityContainsSubstringsPredicate(firstPredicateKeywordList);
        UniversityContainsSubstringsPredicate secondPredicate =
                new UniversityContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UniversityContainsSubstringsPredicate firstPredicateCopy =
                new UniversityContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_universityContainsSubstrings_returnsTrue() {
        // One substring
        UniversityContainsSubstringsPredicate predicate =
                new UniversityContainsSubstringsPredicate(Collections.singletonList("e NU"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));

        // Mixed-case substring
        predicate = new UniversityContainsSubstringsPredicate(Arrays.asList("LE nu"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));
    }

    @Test
    public void test_universityDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        UniversityContainsSubstringsPredicate predicate =
                new UniversityContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withUniversity("NUS").build()));

        // Non-matching substring
        predicate = new UniversityContainsSubstringsPredicate(Arrays.asList("sm"));
        assertFalse(predicate.test(new PersonBuilder().withUniversity("NUS").build()));
    }
}
