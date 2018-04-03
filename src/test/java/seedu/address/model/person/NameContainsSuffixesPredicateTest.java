package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsSuffixesPredicate firstPredicate =
                new NameContainsSuffixesPredicate(firstPredicateKeywordList);
        NameContainsSuffixesPredicate secondPredicate =
                new NameContainsSuffixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsSuffixesPredicate firstPredicateCopy =
                new NameContainsSuffixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsSuffixes_returnsTrue() {
        // One suffix
        NameContainsSuffixesPredicate predicate =
                new NameContainsSuffixesPredicate(Collections.singletonList("ob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case suffix
        predicate = new NameContainsSuffixesPredicate(Arrays.asList("Ob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainSuffixes_returnsFalse() {
        // Zero suffix
        NameContainsSuffixesPredicate predicate = new NameContainsSuffixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching suffix
        predicate = new NameContainsSuffixesPredicate(Arrays.asList("les"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }
}
