package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code ExpectedGraduationYear} is before the keyword given, inclusive of keyword.
 */
public class ExpectedGraduationYearBeforeKeywordPredicate implements Predicate<Person> {
    private final String keyword;
    public ExpectedGraduationYearBeforeKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return person.getExpectedGraduationYear().toString().compareTo(keyword) <= 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpectedGraduationYearBeforeKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((ExpectedGraduationYearBeforeKeywordPredicate) other).keyword)); // state check
    }

}
