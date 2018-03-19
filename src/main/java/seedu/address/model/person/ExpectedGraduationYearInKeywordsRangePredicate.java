package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.logic.parser.FilterRange;

/**
 * A Predicate testing whether  a person has his/her expected graduation year in the keywords range
 */
public class ExpectedGraduationYearInKeywordsRangePredicate implements Predicate<Person> {
    private final String low;
    private final String high;
    public ExpectedGraduationYearInKeywordsRangePredicate(ExpectedGraduationYear low, ExpectedGraduationYear high) {
        this.low = low.value;
        this.high = high.value;
    }

    public ExpectedGraduationYearInKeywordsRangePredicate(FilterRange<ExpectedGraduationYear> filterRange) {
        if (filterRange.isRange()) {
            this.low = filterRange.getLowValue().value;
            this.high = filterRange.getHighValue().value;
        } else {
            this.low = filterRange.getExactValue().value;
            this.high = filterRange.getExactValue().value;
        }
    }

    @Override
    public boolean test(Person person) {
        return person.getExpectedGraduationYear().value.compareTo(high) <= 0
                && person.getExpectedGraduationYear().value.compareTo(low) >= 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpectedGraduationYearInKeywordsRangePredicate // instanceof handles nulls
                && this.low.equals(((ExpectedGraduationYearInKeywordsRangePredicate) other).low)
                && this.high.equals(((ExpectedGraduationYearInKeywordsRangePredicate) other).high)); // state check
    }
}
