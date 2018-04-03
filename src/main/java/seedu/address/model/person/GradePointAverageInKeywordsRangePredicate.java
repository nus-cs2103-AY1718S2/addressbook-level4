package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.logic.parser.FilterRange;

/**
 * A Predicate testing whether a person has his/her grade point average in the keywords range
 */
public class GradePointAverageInKeywordsRangePredicate implements Predicate<Person> {
    private final String low;
    private final String high;
    //@@author mhq199657
    public GradePointAverageInKeywordsRangePredicate(GradePointAverage low, GradePointAverage high) {
        this.low = low.value;
        this.high = high.value;
    }

    public GradePointAverageInKeywordsRangePredicate(FilterRange<GradePointAverage> filterRange) {
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
        return Double.valueOf(person.getGradePointAverage().value).compareTo(Double.valueOf(high)) <= 0
                && Double.valueOf(person.getGradePointAverage().value).compareTo(Double.valueOf(low)) >= 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GradePointAverageInKeywordsRangePredicate // instanceof handles nulls
                && this.low.equals(((GradePointAverageInKeywordsRangePredicate) other).low)
                && this.high.equals(((GradePointAverageInKeywordsRangePredicate) other).high)); // state check
    }
}
