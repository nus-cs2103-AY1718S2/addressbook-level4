package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches the suffix string given.
 */
public class NameContainsSuffixesPredicate implements Predicate<Person> {
    private final List<String> suffixKeywords;

    public NameContainsSuffixesPredicate(List<String> suffixKeywords) {
        this.suffixKeywords = suffixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return suffixKeywords.stream()
                .anyMatch(suffix -> StringUtil.containsSuffixIgnoreCase(
                        person.getName().fullName, suffix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsSuffixesPredicate // instanceof handles nulls
                && this.suffixKeywords.equals(((NameContainsSuffixesPredicate) other).suffixKeywords)); // state check
    }

}
