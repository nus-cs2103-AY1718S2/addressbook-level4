package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s fields matches any of the keywords given.
 */
public class FieldContainKeywordsPredicate implements Predicate<Person> {
    private final Predicate<Person> namePredicate;
    private final Predicate<Person> tagPredicate;

    public FieldContainKeywordsPredicate(List<String> nameKeywords, List<String> tagKeywords) {
        this.namePredicate = new NameContainsKeywordsPredicate(nameKeywords);
        this.tagPredicate = new TagContainsKeywordsPredicate(tagKeywords);
    }


    @Override
    public boolean test(Person person) {
        return namePredicate.test(person) && tagPredicate.test(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FieldContainKeywordsPredicate // instanceof handles nulls
                && this.namePredicate.equals(((FieldContainKeywordsPredicate) other).namePredicate)
                && this.tagPredicate.equals(((FieldContainKeywordsPredicate) other).tagPredicate)); // state check
    }

}
