package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.NoPlayerException;
import seedu.address.model.person.exceptions.PersonNotFoundException;


/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePersonList implements Iterable<Person> {

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

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<Person> persons) throws DuplicatePersonException {
        requireAllNonNull(persons);
        final UniquePersonList replacement = new UniquePersonList();
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

    //@@author lohtianwei
    /**
     * Sorts players by selected field in asc or desc order.
     * @return
     */
    public void sortBy(String field, String order) throws NoPlayerException {
        if (internalList.size() < 1) {
            throw new NoPlayerException();
        }

        Comparator<Person> comparator = null;

        Comparator<Person> nameComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getName().fullName.compareTo(p2.getName().fullName);
            }
        };

        Comparator<Person> jerseyComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getJerseyNumber().value.compareTo(p2.getJerseyNumber().value);
            }
        };

        Comparator<Person> ratingComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getRating().value.compareTo(p2.getRating().value);
            }
        };

        Comparator<Person> posComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getPosition().value.compareTo(p2.getPosition().value);
            }
        };

        Comparator<Person> emailComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getEmail().value.compareTo(p2.getEmail().value);
            }
        };

        Comparator<Person> addressComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getAddress().value.compareTo(p2.getAddress().value);
            }
        };

        switch (field) {
        case "name":
            comparator = nameComparator;
            break;

        case "jersey":
            comparator = jerseyComparator;
            break;

        case "pos":
            comparator = posComparator;
            break;

        case "rating":
            comparator = ratingComparator;
            break;

        case "email":
            comparator = emailComparator;
            break;

        case "address":
            comparator = addressComparator;
            break;

        default:
            throw new AssertionError("Invalid field parameter entered...\n");
        }

        switch (order) {
        case "asc":
            Collections.sort(internalList, comparator);
            break;

        case "desc":
            Collections.sort(internalList, Collections.reverseOrder(comparator));
            break;

        default:
            throw new AssertionError("Invalid field parameter entered...\n");
        }
    }
    //@@author

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && this.internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
