package seedu.address.model.person;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.ComponentManager;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

//@@author cambioforma
/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class PersonContainsTagsPredicate extends ComponentManager implements Predicate<Person> {
    private final List<String> keywords;

    public PersonContainsTagsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        for (Iterator<String> i = keywords.iterator(); i.hasNext();) {
            String keyword = i.next();
            UniqueTagList personTagList = new UniqueTagList(person.getTags());
            if (Tag.isValidTagName(keyword)) {
                Tag keyTag = new Tag(keyword);
                if (personTagList.contains(keyTag)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsTagsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsTagsPredicate) other).keywords)); // state check
    }

}
