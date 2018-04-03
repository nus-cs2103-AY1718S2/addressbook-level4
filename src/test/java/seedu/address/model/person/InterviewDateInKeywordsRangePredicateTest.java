package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import seedu.address.logic.parser.FilterRange;
import seedu.address.model.util.InterviewDateUtil;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

public class InterviewDateInKeywordsRangePredicateTest {
    //@@author mhq199657
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new InterviewDateInKeywordsRangePredicate(null));
    }

    @Test
    public void equals() {
        String firstRangeLower = "20180404";
        String firstRangeHigher = "20180411";
        String secondRangeExact = "20180505";
        FilterRange<InterviewDate> firstPredicateFilterRange = new FilterRange<InterviewDate>(
                new InterviewDate(InterviewDateUtil.formLowerInterviewDateTime(firstRangeLower)),
                new InterviewDate(InterviewDateUtil.formHigherInterviewDateTime(firstRangeHigher)));
        FilterRange<InterviewDate> secondPredicateFilterRange = new FilterRange<InterviewDate>(
                new InterviewDate(InterviewDateUtil.formLowerInterviewDateTime(secondRangeExact)),
                new InterviewDate(InterviewDateUtil.formHigherInterviewDateTime(secondRangeExact)));

        InterviewDateInKeywordsRangePredicate firstPredicate =
                new InterviewDateInKeywordsRangePredicate(firstPredicateFilterRange);
        InterviewDateInKeywordsRangePredicate secondPredicate =
                new InterviewDateInKeywordsRangePredicate(secondPredicateFilterRange);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        InterviewDateInKeywordsRangePredicate firstPredicateCopy =
                new InterviewDateInKeywordsRangePredicate(firstPredicateFilterRange);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_interviewDateInKeywordsRange_returnsTrue() {
        // ranged keyword
        InterviewDateInKeywordsRangePredicate predicate =
                new InterviewDateInKeywordsRangePredicate(
                        new FilterRange<InterviewDate>(
                                new InterviewDate(InterviewDateUtil.formLowerInterviewDateTime("20180404")),
                                new InterviewDate(InterviewDateUtil.formHigherInterviewDateTime("20180406"))));
        assertTrue(predicate.test(new PersonBuilder()
                .withInterviewDate(LocalDateTime.of(2018, Month.APRIL, 6, 14, 0, 0)).build()));
        // single keyword
        predicate =
                new InterviewDateInKeywordsRangePredicate(
                        new FilterRange<InterviewDate>(
                                new InterviewDate(InterviewDateUtil.formLowerInterviewDateTime("20180404")),
                                new InterviewDate(InterviewDateUtil.formHigherInterviewDateTime("20180404"))));
        assertTrue(predicate.test(new PersonBuilder()
                .withInterviewDate(LocalDateTime.of(2018, Month.APRIL, 4, 23, 59, 59)).build()));
    }

    @Test
    public void test_interviewDateNotInKeywordsRange_returnsFalse() {
        // Non-matching keyword for range predicate
        InterviewDateInKeywordsRangePredicate predicate =
                new InterviewDateInKeywordsRangePredicate(
                        new FilterRange<InterviewDate>(
                                new InterviewDate(InterviewDateUtil.formLowerInterviewDateTime("20180404")),
                                new InterviewDate(InterviewDateUtil.formHigherInterviewDateTime("20180406"))));
        assertFalse(predicate.test(new PersonBuilder()
                .withInterviewDate(LocalDateTime.of(2018, Month.APRIL, 3, 23, 59, 59)).build()));

        // Not-in-range keyword for ranged predicate
        predicate =
                new InterviewDateInKeywordsRangePredicate(
                        new FilterRange<InterviewDate>(
                                new InterviewDate(InterviewDateUtil.formLowerInterviewDateTime("20180404")),
                                new InterviewDate(InterviewDateUtil.formHigherInterviewDateTime("20180404"))));
        assertFalse(predicate.test(new PersonBuilder()
                .withInterviewDate(LocalDateTime.of(2018, Month.APRIL, 5, 0, 0, 0)).build()));
    }
}
