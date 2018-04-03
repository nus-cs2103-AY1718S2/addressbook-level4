package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author tanhengyeow
public class CommentContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("He is decent");
        List<String> secondPredicateKeywordList = Arrays.asList("He is decent", "She is decent");

        CommentContainsSubstringsPredicate firstPredicate =
                new CommentContainsSubstringsPredicate(firstPredicateKeywordList);
        CommentContainsSubstringsPredicate secondPredicate =
                new CommentContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CommentContainsSubstringsPredicate firstPredicateCopy =
                new CommentContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_commentContainsSubstrings_returnsTrue() {
        // One substring
        CommentContainsSubstringsPredicate predicate =
                new CommentContainsSubstringsPredicate(Collections.singletonList("dec"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Mixed-case substring
        predicate = new CommentContainsSubstringsPredicate(Arrays.asList("s DecE"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));
    }

    @Test
    public void test_commentDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        CommentContainsSubstringsPredicate predicate =
                new CommentContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Non-matching substring
        predicate = new CommentContainsSubstringsPredicate(Arrays.asList("test"));
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));
    }
}
