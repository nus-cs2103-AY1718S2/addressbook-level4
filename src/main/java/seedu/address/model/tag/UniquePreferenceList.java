package seedu.address.model.tag;
//@@author SuxianAlicia-reused
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of preference tags that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Preference#equals(Object)
 */
public class UniquePreferenceList implements Iterable<Preference> {

    private final ObservableList<Preference> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PreferenceList.
     */
    public UniquePreferenceList() {}

    /**
     * Creates a UniquePreferenceList using given preference tags.
     * Enforces no nulls.
     */
    public UniquePreferenceList(Set<Preference> preferenceTags) {
        requireAllNonNull(preferenceTags);
        internalList.addAll(preferenceTags);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all preference tags in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Preference> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Preferences in this list with those in the argument preference tag list.
     */
    public void setTags(Set<Preference> preferenceTags) {
        requireAllNonNull(preferenceTags);
        internalList.setAll(preferenceTags);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every preference in the argument list exists in this object.
     */
    public void mergeFrom(UniquePreferenceList from) {
        final Set<Preference> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(preferenceTag -> !alreadyInside.contains(preferenceTag))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Preference as the given argument.
     */
    public boolean contains(Preference toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Preference to the list.
     *
     * @throws DuplicatePreferenceException if the Preference to add is a duplicate of an
     * existing Preference in the list.
     */
    public void add(Preference toAdd) throws DuplicatePreferenceException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePreferenceException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes preference from list if it exists
     */
    public void remove(Preference toRemove) {
        requireNonNull(toRemove);
        if (contains(toRemove)) {
            internalList.remove(toRemove);
        }
    }

    @Override
    public Iterator<Preference> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Preference> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniquePreferenceList // instanceof handles nulls
                && this.internalList.equals(((UniquePreferenceList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniquePreferenceList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicatePreferenceException extends DuplicateDataException {
        protected DuplicatePreferenceException() {
            super("Operation would result in duplicate preferences");
        }
    }
}
