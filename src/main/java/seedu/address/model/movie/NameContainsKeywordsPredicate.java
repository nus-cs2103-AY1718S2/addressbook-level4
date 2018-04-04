package seedu.address.model.movie;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Movie}'s {@code MovieName} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Movie> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Movie movie) {
        return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(movie.getName().movieName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
