package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Phone} matches the suffix string given.
 */
public class PhoneContainsSuffixesPredicate implements Predicate<Person> {
    private final List<String> suffixKeywords;

    public PhoneContainsSuffixesPredicate(List<String> suffixKeywords) {
        this.suffixKeywords = suffixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return suffixKeywords.stream()
                .anyMatch(suffix -> StringUtil.containsSuffixIgnoreCase(
                        person.getPhone().value, suffix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsSuffixesPredicate // instanceof handles nulls
                && this.suffixKeywords.equals(((PhoneContainsSuffixesPredicate) other).suffixKeywords)); // state check
    }

}
