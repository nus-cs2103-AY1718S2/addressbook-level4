package seedu.address.model.movie;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
//@@author slothhy
/**
 * Tests that a {@code Movie}'s {@code StartDate} matches any of the keywords given.
 */
public class StartDateContainsKeywordsPredicate implements Predicate<Movie> {
    private final List<String> keywords;

    public StartDateContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Movie movie) {
        return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(movie.getStartDate().startDate, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDateContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((StartDateContainsKeywordsPredicate) other).keywords)); // state check
    }

}
