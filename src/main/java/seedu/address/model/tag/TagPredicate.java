package seedu.address.model.tag;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * @@author {clarissayong}
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagPredicate implements Predicate<Person> {
    private final String tag;

    public TagPredicate(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .anyMatch(keyword -> keyword.tagName.equalsIgnoreCase(tag));
    }

    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }
    */
}
