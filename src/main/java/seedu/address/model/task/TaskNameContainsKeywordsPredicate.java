package seedu.address.model.task;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author CYX28
/**
 * Tests that a {@code Task}'s {@code Name} matches any of the keywords given.
 */
public class TaskNameContainsKeywordsPredicate implements Predicate<Task> {

    private final List<String> keywords;

    public TaskNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getTaskName().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskNameContainsKeywordsPredicate
                && this.keywords.equals(((TaskNameContainsKeywordsPredicate) other).keywords));
    }

}
