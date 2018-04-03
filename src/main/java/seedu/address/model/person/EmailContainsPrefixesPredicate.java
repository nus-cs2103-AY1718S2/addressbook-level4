package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.FindResults;

/**
 * Tests that a {@code Person}'s {@code Email} matches the prefix string given.
 */
public class EmailContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;
    private final String commandPrefix = "e/";

    public EmailContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> FindResults.getInstance().containsPrefixIgnoreCase(
                        person.getEmail().value, prefix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals(((EmailContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
