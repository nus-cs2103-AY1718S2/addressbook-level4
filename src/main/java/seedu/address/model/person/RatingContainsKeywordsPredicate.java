package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class RatingContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public RatingContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.isEmpty()
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(person.getRating().value.toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RatingContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((RatingContainsKeywordsPredicate) other).keywords)); // state check
    }

}
