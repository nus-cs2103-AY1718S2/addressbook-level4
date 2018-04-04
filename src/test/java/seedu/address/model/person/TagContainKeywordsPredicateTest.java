package seedu.address.model.person;

//@@author jas5469

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainKeywordsPredicate firstPredicate = new TagContainKeywordsPredicate(firstPredicateKeywordList);
        TagContainKeywordsPredicate secondPredicate = new TagContainKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainKeywordsPredicate firstPredicateCopy = new TagContainKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainKeywords_returnsTrue() {
        // One keyword
        TagContainKeywordsPredicate predicate = new TagContainKeywordsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Only one matching keyword
        predicate = new TagContainKeywordsPredicate(Arrays.asList("friends", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainKeywordsPredicate predicate = new TagContainKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Non-matching keyword
        predicate = new TagContainKeywordsPredicate(Arrays.asList("friends"));
        assertFalse(predicate.test(new PersonBuilder().withTags("oweMoney").build()));

        // Mixed-case keywords
        predicate = new TagContainKeywordsPredicate(Arrays.asList("FrieNds"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

    }
}
