package seedu.address.model.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.JobBuilder;

public class PositionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PositionContainsKeywordsPredicate firstPredicate = new PositionContainsKeywordsPredicate(firstPredicateKeywordList);
        PositionContainsKeywordsPredicate secondPredicate = new PositionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PositionContainsKeywordsPredicate firstPredicateCopy = new PositionContainsKeywordsPredicate(firstPredicateKeywordList);
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
        PositionContainsKeywordsPredicate predicate = new PositionContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new JobBuilder().withPosition("Alice Bob").build()));

        // Multiple keywords
        predicate = new PositionContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new JobBuilder().withPosition("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PositionContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new JobBuilder().withPosition("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PositionContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new JobBuilder().withPosition("Alice Bob").build()));
    }

    @Test
    public void test_positionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PositionContainsKeywordsPredicate predicate = new PositionContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new JobBuilder().withPosition("Alice").build()));

        // Non-matching keyword
        predicate = new PositionContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new JobBuilder().withPosition("Alice Bob").build()));

        // Keywords match skill, team, location and number of positions, but does not match position
        predicate = new PositionContainsKeywordsPredicate(
                Arrays.asList("developer", "Hardware", "2", "Main", "Street"));
        assertFalse(predicate.test(new JobBuilder().withSkills("developer").withPosition("Engineer")
                .withTeam("Hardware").withLocation("Main Street").withNumberOfPositions("2").build()));
    }
}
