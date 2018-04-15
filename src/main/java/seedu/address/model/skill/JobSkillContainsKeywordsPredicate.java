//@@author kush1509
package seedu.address.model.skill;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.job.Job;

/**
 * Tests that a {@code Job}'s {@code Skill} matches any of the keywords given.
 */
public class JobSkillContainsKeywordsPredicate implements Predicate<Job> {
    private final List<String> keywords;

    public JobSkillContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Job job) {
        return SkillUtil.match(keywords, job.getSkills());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobSkillContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((JobSkillContainsKeywordsPredicate) other).keywords)); // state check
    }

}
