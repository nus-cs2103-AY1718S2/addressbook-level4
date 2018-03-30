package seedu.address.model.person;

import static java.util.Objects.isNull;

import java.time.LocalDateTime;
import java.util.function.Predicate;

import seedu.address.logic.parser.FilterRange;

/**
 * A Predicate testing whether a person has his/her interview date in the keywords range
 */
public class InterviewDateInKeywordsRangePredicate implements Predicate<Person> {
    private final LocalDateTime low;
    private final LocalDateTime high;
    public InterviewDateInKeywordsRangePredicate(InterviewDate low, InterviewDate high) {
        this.low = low.dateTime;
        this.high = high.dateTime;
    }

    public InterviewDateInKeywordsRangePredicate(FilterRange<InterviewDate> filterRange) {
        if (filterRange.isRange()) {
            this.low = filterRange.getLowValue().dateTime;
            this.high = filterRange.getHighValue().dateTime;
        } else {
            this.low = filterRange.getExactValue().dateTime;
            this.high = filterRange.getExactValue().dateTime;
        }
    }

    @Override
    public boolean test(Person person) {
        if (isNull(person.getInterviewDate().dateTime)) {
            return false;
        } else {
            return person.getInterviewDate().dateTime.compareTo(high) <= 0
                    && person.getInterviewDate().dateTime.compareTo(low) >= 0;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InterviewDateInKeywordsRangePredicate // instanceof handles nulls
                && this.low.equals(((InterviewDateInKeywordsRangePredicate) other).low)
                && this.high.equals(((InterviewDateInKeywordsRangePredicate) other).high)); // state check
    }
}
