package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Email} matches the substring given.
 */
public class EmailContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;

    public EmailContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> StringUtil.containsSubstringIgnoreCase(
                        person.getEmail().value, substring));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                        (EmailContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
