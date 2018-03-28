package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Phone} matches the prefix string given.
 */
public class PhoneContainsPrefixesPredicate implements Predicate<Person> {
    private final List<String> prefixKeywords;

    public PhoneContainsPrefixesPredicate(List<String> prefixKeywords) {
        this.prefixKeywords = prefixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return prefixKeywords.stream()
                .anyMatch(prefix -> StringUtil.containsPrefixIgnoreCase(
                        person.getPhone().value, prefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsPrefixesPredicate // instanceof handles nulls
                && this.prefixKeywords.equals(((PhoneContainsPrefixesPredicate) other).prefixKeywords)); // state check
    }

}
