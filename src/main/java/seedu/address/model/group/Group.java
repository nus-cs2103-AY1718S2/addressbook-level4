//@@author jas5469
package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents a Group in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Group {

    private final Information information;
    private UniquePersonList personList;

    /**
     * Every field must be present and not null.
     */
    public Group(Information information) {
        requireAllNonNull(information);
        this.information = information;
        this.personList = new UniquePersonList();
    }
    /**
     * Every field must be present and not null.
     */
    public Group(Information information, UniquePersonList personList) {
        requireAllNonNull(information);
        this.information = new Information(information.value);
        this.personList = new UniquePersonList();
        this.personList.setPersons(personList);
    }

    public Information getInformation() {
        return information;
    }

    public UniquePersonList getPersonList() {
        return personList;
    }

    //@@author Isaaaca
    /**
     * Adds a person to the group's personList
     * @param toAdd The Person to add.
     * @throws DuplicatePersonException
     */
    public void addPerson(Person toAdd) throws DuplicatePersonException {
        if (getPersonList().contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        this.personList.add(toAdd);
    }

    //@@author jas5469

    /**
     * Removes a person to the group's personList
     * @param toRemove The Person to remove.
     * @throws DuplicatePersonException
     */
    public void removePerson(Person toRemove) throws PersonNotFoundException {
        if (!getPersonList().contains(toRemove)) {
            throw new PersonNotFoundException();
        } else {
            this.personList.remove(toRemove);
        }

    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        if (otherGroup.getInformation().equals(this.getInformation())
            && otherGroup.getPersonList().asObservableList().size() == this.getPersonList().asObservableList().size()) {
            for (Person p : personList) {
                if (!otherGroup.getPersonList().contains(p)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(information);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getInformation());
        return builder.toString();
    }

}
