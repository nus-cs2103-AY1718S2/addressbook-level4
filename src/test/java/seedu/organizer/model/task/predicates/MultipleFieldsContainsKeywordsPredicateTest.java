package seedu.organizer.model.task.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.organizer.testutil.TaskBuilder;

//@@author guekling
public class MultipleFieldsContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MultipleFieldsContainsKeywordsPredicate firstPredicate = new MultipleFieldsContainsKeywordsPredicate(
            firstPredicateKeywordList);
        MultipleFieldsContainsKeywordsPredicate secondPredicate = new MultipleFieldsContainsKeywordsPredicate(
            secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MultipleFieldsContainsKeywordsPredicate firstPredicateCopy = new MultipleFieldsContainsKeywordsPredicate(
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
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        MultipleFieldsContainsKeywordsPredicate predicate = new MultipleFieldsContainsKeywordsPredicate(Collections
                .singletonList("Study"));
        assertTrue(predicate.test(new TaskBuilder().withName("Study Exam").build()));

        // Multiple keywords
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("Study", "Exam"));
        assertTrue(predicate.test(new TaskBuilder().withName("Study Exam").build()));

        // Only one matching keyword
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("Exam", "Grocery"));
        assertTrue(predicate.test(new TaskBuilder().withName("Study Grocery").build()));

        // Mixed-case keywords
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("sTuDy", "eXAM"));
        assertTrue(predicate.test(new TaskBuilder().withName("Study Exam").build()));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        MultipleFieldsContainsKeywordsPredicate predicate = new MultipleFieldsContainsKeywordsPredicate(
                Collections.singletonList("CS2103T"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("Study for CS2103T Exam").build()));

        // Multiple keywords
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("CS2101", "CS2103"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("CS2101 CS2103").build()));

        // Only one matching keyword
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("CS2101", "ES2660"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("CS2101 ES2660").build()));

        // Mixed-case keywords
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("cs2101", "eS2660"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("CS2101 ES2660").build()));
    }

    @Test
    public void test_deadlineContainsKeywords_returnsTrue() {
        // One keyword
        MultipleFieldsContainsKeywordsPredicate predicate = new MultipleFieldsContainsKeywordsPredicate(
            Collections.singletonList("2018-03-17"));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2018-03-17").build()));

        // Multiple keywords
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("2018-09-09", "2019-01-01"));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2018-09-09").build()));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2019-01-01").build()));

        // Only one matching keyword
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("2018-09-09", "2019-01-01"));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2018-09-09").build()));
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2018-03-17").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MultipleFieldsContainsKeywordsPredicate predicate = new MultipleFieldsContainsKeywordsPredicate(Collections
                .emptyList());
        assertFalse(predicate.test(new TaskBuilder().withName("Study").build()));

        // Non-matching keyword
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("Grocery"));
        assertFalse(predicate.test(new TaskBuilder().withName("Study Exam").build()));

        // Keywords match priority, but does not match name, description and deadline
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("2"));
        assertFalse(predicate.test(new TaskBuilder().withName("Study").withPriority("2")
                .withDeadline("2018-11-11").withDescription("Chapter 1").build()));
    }

    @Test
    public void test_descriptionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MultipleFieldsContainsKeywordsPredicate predicate = new MultipleFieldsContainsKeywordsPredicate(
            Collections.emptyList());
        assertFalse(predicate.test(new TaskBuilder().withDescription("CS2101").build()));

        // Non-matching keyword
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("CS2103"));
        assertFalse(predicate.test(new TaskBuilder().withDescription("CS2101 CS2103T").build()));

        // Keywords match priority, but does not match name, description and deadline
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("2"));
        assertFalse(predicate.test(new TaskBuilder().withName("Study").withPriority("2")
                .withDeadline("2018-11-11").withDescription("Chapter 1").build()));
    }

    @Test
    public void test_deadlineDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MultipleFieldsContainsKeywordsPredicate predicate = new MultipleFieldsContainsKeywordsPredicate(
            Collections.emptyList());
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2018-03-17").build()));

        // Non-matching keyword
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("2018-03-17"));
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2018-09-09").build()));
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2019-01-01").build()));

        // Keywords match priority, but does not match name, description and deadline
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("2"));
        assertFalse(predicate.test(new TaskBuilder().withName("Study").withPriority("2")
                .withDeadline("2018-11-11").withDescription("Chapter 1").build()));
    }

}
