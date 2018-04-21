package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.FindResults;

//@@author tanhengyeow
/**
 * Tests that a {@code Person}'s {@code University} matches the prefix string given.
 */
public class UniversityContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;
    private final String commandPrefix = "u/";

    public UniversityContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> FindResults.getInstance().containsPrefixIgnoreCase(
                        person.getUniversity().value, prefix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniversityContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals((
                (UniversityContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
