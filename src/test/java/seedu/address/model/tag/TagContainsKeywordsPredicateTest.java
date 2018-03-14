package seedu.address.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("developer"));
        assertTrue(predicate.test(new PersonBuilder().withTags("developer", "geek").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("developer", "geek"));
        assertTrue(predicate.test(new PersonBuilder().withTags("developer", "geek").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("developer", "accountant"));
        assertTrue(predicate.test(new PersonBuilder().withTags("accountant", "manager").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("deVeloper", "acCounTant"));
        assertTrue(predicate.test(new PersonBuilder().withTags("developer", "accountant").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("developer").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("projectmanager"));
        assertFalse(predicate.test(new PersonBuilder().withTags("developer", "designer").build()));

        // Keywords match name, phone, email and address, but does not match tag
        predicate = new TagContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@company.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withTags("developer").withPhone("12345")
                .withEmail("alice@company.com").withAddress("Main Street").withName("Alice").build()));
    }
}
