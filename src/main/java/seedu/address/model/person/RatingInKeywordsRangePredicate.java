package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.logic.parser.FilterRange;

/**
 * A Predicate testing whether a person has his/her rating in the keywords range
 */
public class RatingInKeywordsRangePredicate implements Predicate<Person> {
    private final double low;
    private final double high;
    //@@author mhq199657
    public RatingInKeywordsRangePredicate(Rating low, Rating high) {
        this.low = low.getOverallScore();
        this.high = high.getOverallScore();
    }

    public RatingInKeywordsRangePredicate(FilterRange<Rating> filterRange) {
        if (filterRange.isRange()) {
            this.low = filterRange.getLowValue().getOverallScore();
            this.high = filterRange.getHighValue().getOverallScore();
        } else {
            this.low = filterRange.getExactValue().getOverallScore();
            this.high = filterRange.getExactValue().getOverallScore();
        }
    }

    @Override
    public boolean test(Person person) {
        return person.getRating().getOverallScore() <= high
                && person.getRating().getOverallScore() >= low;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RatingInKeywordsRangePredicate // instanceof handles nulls
                && this.low == ((RatingInKeywordsRangePredicate) other).low
                && this.high == ((RatingInKeywordsRangePredicate) other).high); // state check
    }
}
