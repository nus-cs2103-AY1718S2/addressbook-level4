package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.FindResults;

/**
 * Tests that a {@code Person}'s {@code JobApplied} matches the substring given.
 */
public class JobAppliedContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;
    private final String commandPrefix = "j/";

    public JobAppliedContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> FindResults.getInstance().containsSubstringIgnoreCase(
                        person.getJobApplied().value, substring, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobAppliedContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                (JobAppliedContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
