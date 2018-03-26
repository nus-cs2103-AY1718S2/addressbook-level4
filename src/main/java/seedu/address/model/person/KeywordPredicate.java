package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Attribute} matches any of the keywords given.
 */
public class KeywordPredicate implements Predicate<Person> {
    private final String keyword;

    public KeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return (person.getSubject().toString().equals(keyword) || person.getLevel().toString().equals(keyword)
                || person.getStatus().toString().equals(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof KeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((KeywordPredicate) other).keyword)); // state check
    }

}
