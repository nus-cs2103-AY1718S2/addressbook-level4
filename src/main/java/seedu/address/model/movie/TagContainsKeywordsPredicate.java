package seedu.address.model.movie;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;
//@@author slothhy
/**
 * Tests that a {@code Movie}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Movie> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
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
        return keywords.stream()
                .allMatch(keyword -> containsCaseInsensitive(keyword, tags));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
