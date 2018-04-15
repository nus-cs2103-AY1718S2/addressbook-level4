//@@author philos22
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s fields matches any of the keywords given.
 */
public class ContainsKeywordsPredicate implements Predicate<Person> {
    private final Predicate<Person> namePredicate;
    private final Predicate<Person> tagPredicate;

    public ContainsKeywordsPredicate(List<String> nameKeywords, List<String> tagKeywords) {
        this.namePredicate = new NameContainsKeywordsPredicate(nameKeywords);
        this.tagPredicate = new TagContainsKeywordsPredicate(tagKeywords);
    }


    @Override
    public boolean test(Person person) {
        return namePredicate.test(person) && tagPredicate.test(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ContainsKeywordsPredicate // Used to handle Nulls
                && this.namePredicate.equals(((ContainsKeywordsPredicate) other).namePredicate)
                && this.tagPredicate.equals(((ContainsKeywordsPredicate) other).tagPredicate)); // state check
    }

}
