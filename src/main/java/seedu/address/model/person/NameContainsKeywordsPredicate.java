package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;


/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override




    public boolean test(Person person) {
        final List<String> personDetails = person.toStringList();
        return keywords.stream()
                .anyMatch(keyword -> personDetails.stream()
                        .anyMatch(details -> details.toLowerCase().contains(keyword.toLowerCase())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
