package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.FindResults;

/**
 * Tests that a {@code Person}'s {@code JobApplied} matches the suffix string given.
 */
public class JobAppliedContainsSuffixesPredicate implements Predicate<Person> {
    private final List<String> suffixKeywords;
    private final String commandPrefix = "j/";

    public JobAppliedContainsSuffixesPredicate(List<String> suffixKeywords) {
        this.suffixKeywords = suffixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return suffixKeywords.stream()
                .anyMatch(suffix -> FindResults.getInstance().containsSuffixIgnoreCase(
                        person.getJobApplied().value, suffix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobAppliedContainsSuffixesPredicate // instanceof handles nulls
                && this.suffixKeywords.equals((
                (JobAppliedContainsSuffixesPredicate) other).suffixKeywords)); // state check
    }

}
