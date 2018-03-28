package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Major} matches the substring given.
 */
public class MajorContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;

    public MajorContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> StringUtil.containsSubstringIgnoreCase(
                        person.getMajor().value, substring));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MajorContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                        (MajorContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
