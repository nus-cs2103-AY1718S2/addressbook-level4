package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonEditedEvent;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
 */
public class UniqueEmployeeList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Person toAdd) throws DuplicatePersonException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void setPerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedPerson) && internalList.contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
        EventsCenter.getInstance().post(new PersonEditedEvent(index, editedPerson));
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(Person toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return personFoundAndDeleted;
    }

    public void setPersons(UniqueEmployeeList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<Person> persons) throws DuplicatePersonException {
        requireAllNonNull(persons);
        final UniqueEmployeeList replacement = new UniqueEmployeeList();
        for (final Person person : persons) {
            replacement.add(person);
        }
        setPersons(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEmployeeList // instanceof handles nulls
                        && this.internalList.equals(((UniqueEmployeeList) other).internalList));
    }

    //@@author Yoochard
    /**
     * Sorts existing persons, check sort field here
     */
    public void sort(String field) {
        switch (field) {
        case "name":
            sortByName();
            break;
        case "phone":
            sortByPhone();
            break;
        case "email":
            sortByEmail();
            break;
        case "address":
            sortByAddress();
            break;
        case "tag":
            sortByTag();
            break;
        case "rate":
            sortByRate();
            break;
        default:
            throw new AssertionError("Sort field should be name, phone, email, tag, address or rate.");
        }
    }
    /**
     * Specific sort method for every field, sort by name
     */
    public void sortByName() {
        Collections.sort(internalList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                int num = p1.getName().toString().compareToIgnoreCase(p2.getName().toString());
                return num;
            }
        });
    }

    /**
     * when the input field is phone, sort by phone
     */
    public void sortByPhone() {
        Collections.sort(internalList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                int num = p1.getPhone().toString().compareToIgnoreCase(p2.getPhone().toString());
                return num;
            }
        });
    }

    /**
     * when the input field is email, sort by email
     */
    public void sortByEmail() {
        Collections.sort(internalList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                int num = p1.getEmail().toString().compareToIgnoreCase(p2.getEmail().toString());
                return num;
            }
        });
    }

    /**
     * when the input field is address, sort by address
     */
    public void sortByAddress() {
        Collections.sort(internalList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                int num = p1.getAddress().toString().compareToIgnoreCase(p2.getAddress().toString());
                return num;
            }
        });
    }

    /**
     * when the input field is tag, sort by tag
     */
    public void sortByTag() {
        Collections.sort(internalList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                int num = p1.getTags().toString().compareToIgnoreCase(p2.getTags().toString());
                return num;
            }
        });
    }

    /**
     * when the input field is rate, sort by rate in descending order
     */
    public void sortByRate() {
        Collections.sort(internalList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                int num = p2.getRating().toString().compareToIgnoreCase(p1.getRating().toString());
                return num;
            }
        });
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
