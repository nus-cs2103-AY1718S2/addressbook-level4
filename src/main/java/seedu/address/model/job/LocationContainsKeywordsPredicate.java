//@@author kush1509
package seedu.address.model.job;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Job}'s {@code Location} matches any of the keywords given.
 */
public class LocationContainsKeywordsPredicate implements Predicate<Job> {
    private final List<String> keywords;

    public LocationContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Job job) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(job.getLocation().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((LocationContainsKeywordsPredicate) other).keywords)); // state check
    }

}
