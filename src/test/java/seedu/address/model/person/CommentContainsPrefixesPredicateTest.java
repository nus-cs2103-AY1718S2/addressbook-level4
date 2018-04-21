package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author tanhengyeow
public class CommentContainsPrefixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("He is decent");
        List<String> secondPredicateKeywordList = Arrays.asList("He is decent", "She is decent");

        CommentContainsPrefixesPredicate firstPredicate =
                new CommentContainsPrefixesPredicate(firstPredicateKeywordList);
        CommentContainsPrefixesPredicate secondPredicate =
                new CommentContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CommentContainsPrefixesPredicate firstPredicateCopy =
                new CommentContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_commentContainsPrefixes_returnsTrue() {
        // One prefix
        CommentContainsPrefixesPredicate predicate =
                new CommentContainsPrefixesPredicate(Collections.singletonList("He"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Mixed-case prefix
        predicate = new CommentContainsPrefixesPredicate(Arrays.asList("hE"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));
    }

    @Test
    public void test_commentDoesNotContainPrefixes_returnsFalse() {
        // Zero prefix
        CommentContainsPrefixesPredicate predicate =
                new CommentContainsPrefixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Non-matching prefix
        predicate = new CommentContainsPrefixesPredicate(Arrays.asList("She"));
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));
    }
}
