package seedu.organizer.model.task;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.organizer.commons.util.StringUtil;

//@@author guekling
/**
 * Tests that a {@code Task}'s {@code Name} and {@code Description} matches any of the keywords given.
 */
public class MultipleFieldsContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> nameKeywords;
    private final List<String> descriptionKeywords;
    private final List<String> keywords;


    public MultipleFieldsContainsKeywordsPredicate(List<String> keywords) {
        this.nameKeywords = keywords;
        this.descriptionKeywords = keywords;
        this.keywords = Stream.concat(nameKeywords.stream(), descriptionKeywords.stream()).collect(Collectors.toList
                ());
    }

    @Override
    public boolean test(Task task) {
        return nameKeywords.stream().anyMatch(nameKeyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName,
                nameKeyword))
                || descriptionKeywords.stream().anyMatch(descriptionKeyword -> StringUtil.containsWordIgnoreCase(
                    task.getDescription().value, descriptionKeyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MultipleFieldsContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((MultipleFieldsContainsKeywordsPredicate) other).keywords)); // state check
    }
}
