package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.FindResults;

//@@author tanhengyeow
/**
 * Tests that a {@code Person}'s {@code Name} matches the prefix string given.
 */
public class NameContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;
    private final String commandPrefix = "n/";

    public NameContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> FindResults.getInstance().containsPrefixIgnoreCase(
                        person.getName().fullName, prefix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals(((NameContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
