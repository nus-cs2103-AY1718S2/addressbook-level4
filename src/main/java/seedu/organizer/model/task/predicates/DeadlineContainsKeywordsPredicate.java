package seedu.organizer.model.task.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.organizer.commons.util.StringUtil;
import seedu.organizer.model.task.Task;

//@@author guekling
/**
 * Tests that a {@code Task}'s {@code Deadline} matches any of the keywords given. Keywords given should in the
 * format of YYYY-MM-DD.
 */
public class DeadlineContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public DeadlineContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getDeadline().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeadlineContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((DeadlineContainsKeywordsPredicate) other).keywords)); // state check
    }
}
