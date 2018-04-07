package seedu.organizer.model.task.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.organizer.testutil.TaskBuilder;

//@@author guekling
public class DescriptionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("CS2101");
        List<String> secondPredicateKeywordList = Arrays.asList("CS2101", "CS2103");

        DescriptionContainsKeywordsPredicate firstPredicate = new DescriptionContainsKeywordsPredicate(
            firstPredicateKeywordList);
        DescriptionContainsKeywordsPredicate secondPredicate = new DescriptionContainsKeywordsPredicate(
            secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DescriptionContainsKeywordsPredicate firstPredicateCopy = new DescriptionContainsKeywordsPredicate(
            firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different task -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        DescriptionContainsKeywordsPredicate predicate = new DescriptionContainsKeywordsPredicate(
            Collections.singletonList("CS2103T"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("Study for CS2103T Exam").build()));

        // Multiple keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("CS2101", "CS2103"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("CS2101 CS2103").build()));

        // Only one matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("CS2101", "ES2660"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("CS2101 ES2660").build()));

        // Mixed-case keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("cs2101", "eS2660"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("CS2101 ES2660").build()));
    }

    @Test
    public void test_descriptionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DescriptionContainsKeywordsPredicate predicate = new DescriptionContainsKeywordsPredicate(
            Collections.emptyList());
        assertFalse(predicate.test(new TaskBuilder().withDescription("CS2101").build()));

        // Non-matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("CS2103"));
        assertFalse(predicate.test(new TaskBuilder().withDescription("CS2101 CS2103T").build()));

        // Keywords match name, priority, deadline, but does not match description
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Study", "2", "2018-11-11"));
        assertFalse(predicate.test(new TaskBuilder().withName("Study").withPriority("2")
            .withDeadline("2018-11-11").withDescription("Chapter 1").build()));
    }
}
