package seedu.address.model.pair;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Pair}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicatePair implements Predicate<Pair> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicatePair(List<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public boolean test(Pair pair) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(pair.getPairName(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicatePair // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicatePair) other).keywords)); // state check
    }

}
