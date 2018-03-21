package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code deliver date} matches any of the keywords given.
 */
public class DatePredicate implements Predicate<Person> {
    private final List<String> keywords;

    public DatePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getDate().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DatePredicate // instanceof handles nulls
                && this.keywords.equals(((DatePredicate) other).keywords)); // state check
    }

}
