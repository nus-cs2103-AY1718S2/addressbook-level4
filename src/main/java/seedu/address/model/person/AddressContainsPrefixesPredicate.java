package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.FindResults;

//@@author tanhengyeow
/**
 * Tests that a {@code Person}'s {@code Address} matches the prefix string given.
 */
public class AddressContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;
    private final String commandPrefix = "a/";

    public AddressContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> FindResults.getInstance().containsPrefixIgnoreCase(
                        person.getAddress().value, prefix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals((
                        (AddressContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
