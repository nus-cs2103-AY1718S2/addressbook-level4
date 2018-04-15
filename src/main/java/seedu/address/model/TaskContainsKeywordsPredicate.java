package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;


/**
 * Tests that a {@code Task}'s {@code description} matches any of the keywords given.
 */
//@@author a-shakra
public class TaskContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public TaskContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TaskContainsKeywordsPredicate) other).keywords)); // state check
    }

}
