package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Address} matches the substring given.
 */
public class AddressContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;

    public AddressContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> StringUtil.containsSubstringIgnoreCase(
                        person.getAddress().value, substring));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                        (AddressContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
