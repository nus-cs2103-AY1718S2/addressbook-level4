//@@author kush1509
package seedu.address.model.skill;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.job.Job;
//@@author kush1509
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
        Iterator tagsIterator = job.getSkills().iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(tagsIterator.next());
        while (tagsIterator.hasNext()) {
            sb.append(" " + tagsIterator.next());
        }
        String tagLists = sb.toString()
                .replace("[", "")
                .replace("]", "");
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tagLists, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobSkillContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((JobSkillContainsKeywordsPredicate) other).keywords)); // state check
    }

}
