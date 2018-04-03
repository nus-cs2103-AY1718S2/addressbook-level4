package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.FindResults;

/**
 * Tests that a {@code Person}'s {@code JobApplied} matches the prefix string given.
 */
public class JobAppliedContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;
    private final String commandPrefix = "j/";

    public JobAppliedContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> FindResults.getInstance().containsPrefixIgnoreCase(
                        person.getJobApplied().value, prefix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobAppliedContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals((
                (JobAppliedContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
