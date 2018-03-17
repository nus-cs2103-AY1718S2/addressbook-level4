package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalGroups.BUDDIES;
import static seedu.address.testutil.TypicalGroups.COLLEAGUES;
import static seedu.address.testutil.TypicalGroups.FRIENDS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;


public class GroupsContainKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Arrays.asList(COLLEAGUES.tagName, BUDDIES.tagName);
        List<String> secondPredicateKeywordList = Collections.singletonList(FRIENDS.tagName);

        GroupsContainKeywordsPredicate firstPredicate = new GroupsContainKeywordsPredicate(firstPredicateKeywordList);
        GroupsContainKeywordsPredicate secondPredicate = new GroupsContainKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GroupsContainKeywordsPredicate firstPredicateCopy =
                new GroupsContainKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_groupsContainsKeywords_returnsTrue() {
        GroupsContainKeywordsPredicate predicate;
        // One keyword
        predicate = new GroupsContainKeywordsPredicate(Collections.singletonList(COLLEAGUES.tagName));
        assertTrue(predicate.test(new PersonBuilder().withGroups(COLLEAGUES.tagName).build()));

        // Multiple keywords
        predicate = new GroupsContainKeywordsPredicate(Arrays.asList(COLLEAGUES.tagName, BUDDIES.tagName));
        assertTrue(predicate.test(new PersonBuilder().withGroups(COLLEAGUES.tagName, BUDDIES.tagName).build()));

        // Only one matching keyword
        predicate = new GroupsContainKeywordsPredicate(Arrays.asList(FRIENDS.tagName, BUDDIES.tagName));
        assertTrue(predicate.test(new PersonBuilder().withGroups(COLLEAGUES.tagName, BUDDIES.tagName).build()));

        // Mixed-case keywords
        predicate = new GroupsContainKeywordsPredicate(Arrays.asList("COlleAGUES", "BUDDIes"));
        assertTrue(predicate.test(new PersonBuilder().withGroups(COLLEAGUES.tagName, BUDDIES.tagName).build()));
    }

    @Test
    public void test_groupsDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GroupsContainKeywordsPredicate predicate =
                new GroupsContainKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withGroups(BUDDIES.tagName).build()));

        // Non-matching keyword
        predicate = new GroupsContainKeywordsPredicate(Collections.singletonList(COLLEAGUES.tagName));
        assertFalse(predicate.test(new PersonBuilder().withGroups(BUDDIES.tagName, FRIENDS.tagName).build()));
    }
}
