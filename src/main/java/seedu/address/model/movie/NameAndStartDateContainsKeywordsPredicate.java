package seedu.address.model.movie;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
//@@author slothhy
/**
 * Tests that a {@code Movie}'s {@code MovieName} matches any of the keywords given.
 */
public class NameAndStartDateContainsKeywordsPredicate implements Predicate<Movie> {
    private final List<String> nameKeywords;
    private final List<String> startDateKeywords;


    public NameAndStartDateContainsKeywordsPredicate(List<String> nameKeywords, List<String> startDateKeywords) {
        this.nameKeywords = nameKeywords;
        this.startDateKeywords = startDateKeywords;
    }

    @Override
    public boolean test(Movie movie) {
        return nameKeywords.stream().allMatch
                (keyword -> StringUtil.containsWordIgnoreCase(movie.getName().movieName, keyword))
                && startDateKeywords.stream().allMatch
                (keyword -> StringUtil.containsWordIgnoreCase(movie.getStartDate().startDate, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameAndStartDateContainsKeywordsPredicate // instanceof handles nulls
                && this.nameKeywords.equals(((NameAndStartDateContainsKeywordsPredicate) other).nameKeywords)
                && this.startDateKeywords.equals(((NameAndStartDateContainsKeywordsPredicate) other)
                .startDateKeywords)); // state check
    }

}
