//@@author jas5469
package seedu.address.model.group;

import java.util.function.Predicate;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 *Tests that a {@code Person}'s is in the group specified.
 */
public class MembersInGroupPredicate implements Predicate<Person> {

    private final Group group;

    public MembersInGroupPredicate(Group group) {
        this.group = group;
    }

    @Override
    public boolean test(Person person) {
        UniquePersonList personList = group.getPersonList();
        if (personList.contains(person)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MembersInGroupPredicate // instanceof handles nulls
                && this.group.equals(((MembersInGroupPredicate) other).group)); // state check
    }
}
