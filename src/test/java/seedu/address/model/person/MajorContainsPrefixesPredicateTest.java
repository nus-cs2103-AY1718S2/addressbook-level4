package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author tanhengyeow
public class MajorContainsPrefixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Computer");
        List<String> secondPredicateKeywordList = Arrays.asList("Computer", "Science");

        MajorContainsPrefixesPredicate firstPredicate =
                new MajorContainsPrefixesPredicate(firstPredicateKeywordList);
        MajorContainsPrefixesPredicate secondPredicate =
                new MajorContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MajorContainsPrefixesPredicate firstPredicateCopy =
                new MajorContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_majorContainsPrefixes_returnsTrue() {
        // One prefix
        MajorContainsPrefixesPredicate predicate =
                new MajorContainsPrefixesPredicate(Collections.singletonList("Com"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Mixed-case prefix
        predicate = new MajorContainsPrefixesPredicate(Arrays.asList("CoM"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }

    @Test
    public void test_majorDoesNotContainPrefixes_returnsFalse() {
        // Zero prefix
        MajorContainsPrefixesPredicate predicate = new MajorContainsPrefixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Non-matching prefix
        predicate = new MajorContainsPrefixesPredicate(Arrays.asList("Info"));
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }
}
