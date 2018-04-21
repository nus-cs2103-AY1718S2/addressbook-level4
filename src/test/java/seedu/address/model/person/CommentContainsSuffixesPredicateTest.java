package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author tanhengyeow
public class CommentContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("He is decent");
        List<String> secondPredicateKeywordList = Arrays.asList("He is decent", "She is decent");

        CommentContainsSuffixesPredicate firstPredicate =
                new CommentContainsSuffixesPredicate(firstPredicateKeywordList);
        CommentContainsSuffixesPredicate secondPredicate =
                new CommentContainsSuffixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CommentContainsSuffixesPredicate firstPredicateCopy =
                new CommentContainsSuffixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_commentContainsSuffixes_returnsTrue() {
        // One suffix
        CommentContainsSuffixesPredicate predicate =
                new CommentContainsSuffixesPredicate(Collections.singletonList("ent"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Mixed-case suffix
        predicate = new CommentContainsSuffixesPredicate(Arrays.asList("CeNt"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));
    }

    @Test
    public void test_commentDoesNotContainSuffixes_returnsFalse() {
        // Zero suffix
        CommentContainsSuffixesPredicate predicate =
                new CommentContainsSuffixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Non-matching suffix
        predicate = new CommentContainsSuffixesPredicate(Arrays.asList("alright"));
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));
    }
}
