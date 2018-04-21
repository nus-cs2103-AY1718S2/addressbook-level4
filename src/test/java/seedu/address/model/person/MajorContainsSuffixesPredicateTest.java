package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author tanhengyeow
public class MajorContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Computer");
        List<String> secondPredicateKeywordList = Arrays.asList("Computer", "Science");

        MajorContainsSuffixesPredicate firstPredicate =
                new MajorContainsSuffixesPredicate(firstPredicateKeywordList);
        MajorContainsSuffixesPredicate secondPredicate =
                new MajorContainsSuffixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MajorContainsSuffixesPredicate firstPredicateCopy =
                new MajorContainsSuffixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_majorContainsSuffixes_returnsTrue() {
        // One suffix
        MajorContainsSuffixesPredicate predicate =
                new MajorContainsSuffixesPredicate(Collections.singletonList("ence"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Mixed-case suffix
        predicate = new MajorContainsSuffixesPredicate(Arrays.asList("EnCe"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }

    @Test
    public void test_majorDoesNotContainSuffixes_returnsFalse() {
        // Zero suffix
        MajorContainsSuffixesPredicate predicate = new MajorContainsSuffixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Non-matching suffix
        predicate = new MajorContainsSuffixesPredicate(Arrays.asList("curity"));
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }
}
