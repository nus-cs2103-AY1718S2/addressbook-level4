package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;

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

    public ObservableList<Person> getInternalList() {
        return internalList;
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


    /**
     * Removes specified group from all persons who have the group.
     */
    public void removeGroupFromAllPersons (Group toRemove) {
        for (Person p: internalList) {
            Set<Group> newGroups = new HashSet<>(p.getGroupTags());
            if (!newGroups.remove(toRemove)) {
                continue;
            }

            Person newPerson = new Person(p.getName(), p.getPhone(), p.getEmail(), p.getAddress(),
                    newGroups, p.getPreferenceTags());
            try {
                setPerson(p, newPerson);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("There should not be any duplicates as only groups are edited.");
            } catch (PersonNotFoundException e) {
                throw new AssertionError("Method is called only specified Group exists. internalList "
                        + "cannot be empty.");
            }
        }
    }

    /**
     * Removes specified preference from all persons who have the preference.
     */
    public void removePrefFromAllPersons(Preference toRemove) {
        for (Person p: internalList) {
            Set<Preference> newPreferences = new HashSet<>(p.getPreferenceTags());
            if (!newPreferences.remove(toRemove)) {
                continue;
            }

            Person newPerson = new Person(p.getName(), p.getPhone(), p.getEmail(), p.getAddress(),
                    p.getGroupTags(), newPreferences);
            try {
                setPerson(p, newPerson);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("There should not be any duplicates as only preferences"
                        + " are edited.");
            } catch (PersonNotFoundException e) {
                throw new AssertionError("Method is called only specified Preference exists. "
                        + "internalList cannot be empty.");
            }

        }
    }
}
