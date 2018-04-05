package seedu.address.model.person;

import java.util.Comparator;

/**
 * Comparator class for two Person objects
 */
//@@author limzk1994
public class ReadOnlyPersonComparator implements Comparator<Person> {

    @Override
    public int compare(Person personA, Person personB) {
        return personA.getName().compareTo(personB.getName());
    }
}
