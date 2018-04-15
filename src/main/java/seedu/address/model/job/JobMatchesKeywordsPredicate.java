package seedu.address.model.job;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author ChengSashankh

/**
 * Tests that a {@code Job}'s {@code JobTitle} or {@code Location} or {@code Tags} matches any of the
 * keywords given.
 */
public class JobMatchesKeywordsPredicate implements Predicate<Job> {
    private final List<String> keywords;

    public JobMatchesKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Job job) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(job.getJobTitle().fullTitle, keyword)
                        || StringUtil.containsWordIgnoreCase(job.getLocation().toString(), keyword)
                        || job.getTags().stream()
                        .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobMatchesKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((JobMatchesKeywordsPredicate) other).keywords)); // state check
    }

}
//@@author
