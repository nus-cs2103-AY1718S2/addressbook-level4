package seedu.address.model.job;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Job}'s {@code Position} matches any of the keywords given.
 */
public class PositionContainsKeywordsPredicate implements Predicate<Job> {
    private final List<String> keywords;

    public PositionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Job job) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(job.getPosition().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PositionContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PositionContainsKeywordsPredicate) other).keywords)); // state check
    }

}
