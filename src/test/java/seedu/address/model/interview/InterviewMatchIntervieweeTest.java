//@@author deeheenguyen
package seedu.address.model.interview;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.InterviewBuilder;

public class InterviewMatchIntervieweeTest {

    @Test
    public void equals() {
        String firstPredicateKeywordList = "first";
        String secondPredicateKeywordList = "second";

        InterviewMatchInterviewee firstPredicate;
        firstPredicate = new InterviewMatchInterviewee(firstPredicateKeywordList);
        InterviewMatchInterviewee secondPredicate;
        secondPredicate = new InterviewMatchInterviewee(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        InterviewMatchInterviewee firstPredicateCopy;
        firstPredicateCopy = new InterviewMatchInterviewee(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchInterview_returnsTrue() {
        // One keyword
        InterviewMatchInterviewee predicate;
        predicate = new InterviewMatchInterviewee("John");
        assertTrue(predicate.test(new InterviewBuilder().withInterviewee("John").build()));
    }

    @Test
    public void test_matchDoesNotContainKeywords_returnsFalse() {

        InterviewMatchInterviewee predicate;
        predicate = new InterviewMatchInterviewee("Alice");
        assertFalse(predicate.test(new InterviewBuilder().withInterviewee("John").build()));
    }
}
