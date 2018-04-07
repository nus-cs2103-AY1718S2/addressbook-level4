package seedu.organizer.model.task.predicates;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.organizer.commons.util.StringUtil;
import seedu.organizer.model.task.Task;

//@@author guekling
/**
 * Tests that a {@code Task}'s {@code Name}, {@code Description} and {@code Deadline} matches any of the keywords given.
 */
public class MultipleFieldsContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> nameKeywords;
    private final List<String> descriptionKeywords;
    private final List<String> deadlineKeywords;
    private final List<String> keywords;


    public MultipleFieldsContainsKeywordsPredicate(List<String> keywords) {
        this.nameKeywords = keywords;
        this.descriptionKeywords = keywords;
        this.deadlineKeywords = keywords;

        this.keywords = concatKeywords();
    }

    /**
     * Concatenate the list of keywords from {@code Name}, {@code Description} and {@code Deadline}.
     *
     * @return A list of concatenated String containing the keywords from {@code Name}, {@code Description} and
     * {@code Deadline}.
     */
    private List<String> concatKeywords() {
        Stream<String> nameDescriptionStreams = Stream.concat(nameKeywords.stream(), descriptionKeywords.stream());
        List<String> concatenatedLists = Stream.concat(nameDescriptionStreams, deadlineKeywords.stream()).collect
                (Collectors.toList());
        return concatenatedLists;
    }

    @Override
    public boolean test(Task task) {
        return nameKeywords.stream().anyMatch(nameKeyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName,
                nameKeyword))
                || descriptionKeywords.stream().anyMatch(descriptionKeyword -> StringUtil.containsWordIgnoreCase(
                    task.getDescription().value, descriptionKeyword))
                || deadlineKeywords.stream().anyMatch(deadlineKeyword -> StringUtil.containsWordIgnoreCase(
                task.getDeadline().toString(), deadlineKeyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MultipleFieldsContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((MultipleFieldsContainsKeywordsPredicate) other).keywords)); // state check
    }
}
