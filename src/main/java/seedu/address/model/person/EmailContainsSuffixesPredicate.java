package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Email} matches the suffix string given.
 */
public class EmailContainsSuffixesPredicate implements Predicate<Person> {
    private final List<String> suffixKeywords;

    public EmailContainsSuffixesPredicate(List<String> suffixKeywords) {
        this.suffixKeywords = suffixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return suffixKeywords.stream()
                .anyMatch(suffix -> StringUtil.containsSuffixIgnoreCase(
                        person.getEmail().value, suffix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsSuffixesPredicate // instanceof handles nulls
                && this.suffixKeywords.equals(((EmailContainsSuffixesPredicate) other).suffixKeywords)); // state check
    }

}
