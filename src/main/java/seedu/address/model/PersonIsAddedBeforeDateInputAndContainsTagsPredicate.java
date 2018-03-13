package seedu.address.model;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.DateAddedIsBeforeDateInputPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagListContainsTagsPredicate;

/**
 * Tests that a {@code Person}'s {@code UniqueTagList} matches all of the input tags and
 * {@code DateAdded} is before the date input.
 */
public class PersonIsAddedBeforeDateInputAndContainsTagsPredicate implements Predicate<Person> {

    private final Set<Tag> inputTags;
    private final String inputDate;

    public PersonIsAddedBeforeDateInputAndContainsTagsPredicate(Set<Tag> inputTags, String inputDate) {
        this.inputTags = inputTags;
        this.inputDate = inputDate;
    }

    @Override
    public boolean test(Person person) {
        UniqueTagListContainsTagsPredicate containsTagsPredicate =
                new UniqueTagListContainsTagsPredicate(inputTags);
        DateAddedIsBeforeDateInputPredicate isAddedBeforeDateInputPredicate =
                new DateAddedIsBeforeDateInputPredicate(inputDate);
        return containsTagsPredicate.test(person) && isAddedBeforeDateInputPredicate.test(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonIsAddedBeforeDateInputAndContainsTagsPredicate // instanceof handles nulls
                && this.inputTags.equals(((
                        PersonIsAddedBeforeDateInputAndContainsTagsPredicate) other).inputTags) // state check
                && this.inputDate.equals(((
                        PersonIsAddedBeforeDateInputAndContainsTagsPredicate) other).inputDate));
    }
}
