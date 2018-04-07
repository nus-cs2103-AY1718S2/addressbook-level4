package seedu.organizer.model.task.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.organizer.commons.util.StringUtil;
import seedu.organizer.model.task.Task;

//@@author guekling
/**
 * Tests that a {@code Task}'s {@code Description} matches any of the keywords given.
 */
public class DescriptionContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public DescriptionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((DescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }
}
//@@author
