//@@author luca590

package seedu.address.model.person;

import java.util.Comparator;

/**
 * PersonCompare class to compare Persons by first name
 */
public class PersonCompare implements Comparator<Person> {

    /**
     * Default person comparison is by name,
     * may modify below function to compare by Tag, Address, etc
     * Necessary for sorting
     * @return int will return 1 of p1 > p2
     */
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getName().toString().compareTo(p2.getName().toString());
    }
}
//@@author
