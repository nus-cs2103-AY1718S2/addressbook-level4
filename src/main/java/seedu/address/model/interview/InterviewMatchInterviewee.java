//@@author deeheenguyen
package seedu.address.model.interview;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code skills or address} matches any of the keywords given.
 */

public class InterviewMatchInterviewee implements Predicate<Interview> {
    private final String keyword;

    public InterviewMatchInterviewee(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Interview interview) {
        return StringUtil.containsWordIgnoreCase(interview.getInterviewee().fullName, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InterviewMatchInterviewee // instanceof handles nulls
                && this.keyword.equals(((InterviewMatchInterviewee) other).keyword)); // state check
    }

}
//author@@
