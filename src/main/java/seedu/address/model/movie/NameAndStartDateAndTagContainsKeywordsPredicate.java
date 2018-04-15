package seedu.address.model.movie;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;
//@@author slothhy
/**
 * Tests that a {@code Movie}'s {@code MovieName} matches any of the keywords given.
 */
public class NameAndStartDateAndTagContainsKeywordsPredicate implements Predicate<Movie> {
    private final List<String> nameKeywords;
    private final List<String> startDateKeywords;
    private final List<String> tagKeywords;


    public NameAndStartDateAndTagContainsKeywordsPredicate(List<String> nameKeywords, List<String> startDateKeywords,
                                                    List<String> tagKeywords) {
        this.nameKeywords = nameKeywords;
        this.startDateKeywords = startDateKeywords;
        this.tagKeywords = tagKeywords;
    }

    public boolean containsCaseInsensitive(String s, List<String> l) {
        return l.stream().anyMatch(x -> x.equalsIgnoreCase(s));
    }

    @Override
    public boolean test(Movie movie) {
        ArrayList<String> tags = new ArrayList();
        for (Tag tag : movie.getTags()) {
            tags.add(tag.tagName);
        }
        return nameKeywords.stream().allMatch
                (keyword -> StringUtil.containsWordIgnoreCase(movie.getName().movieName, keyword))
                && startDateKeywords.stream().allMatch
                (keyword -> StringUtil.containsWordIgnoreCase(movie.getStartDate().startDate, keyword))
                && tagKeywords.stream().allMatch
                (keyword -> containsCaseInsensitive(keyword, tags));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameAndStartDateContainsKeywordsPredicate // instanceof handles nulls
                && this.nameKeywords
                .equals(((NameAndStartDateAndTagContainsKeywordsPredicate) other).nameKeywords)
                && this.startDateKeywords
                .equals(((NameAndStartDateAndTagContainsKeywordsPredicate) other).startDateKeywords)
                && this.tagKeywords
                .equals(((NameAndStartDateAndTagContainsKeywordsPredicate) other).tagKeywords)); // state check
    }

}
