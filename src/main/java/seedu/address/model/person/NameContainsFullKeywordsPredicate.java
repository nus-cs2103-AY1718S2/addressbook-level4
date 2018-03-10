package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */

public class NameContainsFullKeywordsPredicate  implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsFullKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsFullKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsFullKeywordsPredicate) other).keywords)); // state check
    }

}
