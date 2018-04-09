// @@author kush1509
package seedu.address.model.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.JobBuilder;

public class LocationContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        LocationContainsKeywordsPredicate firstPredicate =
                new LocationContainsKeywordsPredicate(firstPredicateKeywordList);
        LocationContainsKeywordsPredicate secondPredicate =
                new LocationContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LocationContainsKeywordsPredicate firstPredicateCopy =
                new LocationContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different job -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_positionContainsKeywords_returnsTrue() {
        // One keyword
        LocationContainsKeywordsPredicate predicate =
                new LocationContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new JobBuilder().withLocation("Alice Bob").build()));

        // Multiple keywords
        predicate = new LocationContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new JobBuilder().withLocation("Alice Bob").build()));

        // Only one matching keyword
        predicate = new LocationContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new JobBuilder().withLocation("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new LocationContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new JobBuilder().withLocation("Alice Bob").build()));
    }

    @Test
    public void test_positionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        LocationContainsKeywordsPredicate predicate = new LocationContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new JobBuilder().withLocation("Alice").build()));

        // Non-matching keyword
        predicate = new LocationContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new JobBuilder().withLocation("Alice Bob").build()));

        // Keywords match position, team, skill and number of positions, but does not match location
        predicate = new LocationContainsKeywordsPredicate(
                Arrays.asList("developer", "Hardware", "2", "developer"));
        assertFalse(predicate.test(new JobBuilder().withSkills("developer").withLocation("Engineer")
                .withTeam("Hardware").withLocation("Main Street").withNumberOfPositions("2").build()));
    }
}
