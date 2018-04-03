package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.FindResults;

/**
 * Tests that a {@code Person}'s {@code Name} matches the substring given.
 */
public class NameContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;
    private final String commandPrefix = "n/";

    public NameContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> FindResults.getInstance().containsSubstringIgnoreCase(
                        person.getName().fullName, substring, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                        (NameContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
