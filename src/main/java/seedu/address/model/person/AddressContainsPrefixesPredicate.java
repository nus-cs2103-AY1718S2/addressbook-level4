package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Address} matches the prefix string given.
 */
public class AddressContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;

    public AddressContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> StringUtil.containsPrefixIgnoreCase(
                        person.getAddress().value, prefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals((
                        (AddressContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
