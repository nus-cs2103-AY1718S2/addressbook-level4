package seedu.address.model.tag;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code UniqueTagList} matches all of the input tags.
 */

public class UniqueTagListContainsTagsPredicate  implements Predicate<Person> {
    private final Set<Tag> inputTags;

    public UniqueTagListContainsTagsPredicate(Set<Tag> inputTags) {
        this.inputTags = inputTags;
    }

    @Override
    public boolean test(Person person) {
        UniqueTagList personTags = new UniqueTagList(person.getTags());
        return inputTags.stream()
                .allMatch(personTags::contains);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTagListContainsTagsPredicate // instanceof handles nulls
                && this.inputTags.equals(((UniqueTagListContainsTagsPredicate) other).inputTags)); // state check
    }

}
