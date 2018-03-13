package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code ExpectedGraduationYear} matches any of the keywords given.
 */
public class ExpectedGraduationYearContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ExpectedGraduationYearContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                        (person.getExpectedGraduationYear().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpectedGraduationYearContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((
                        ExpectedGraduationYearContainsKeywordsPredicate) other).keywords)); // state check
    }

}
