package seedu.address.model.item;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A list of filepaths that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see String#equals(Object)
 */
//@@author Alaru
public class UniqueItemList {

    private final Set<String> internalList = new HashSet<>();

    /**
     * Returns true if the list contains an equivalent item/filepath as the given argument.
     */
    public boolean contains(String toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a filepath to the list.
     *
     */
    public void add(String toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent item/filepath from the list.
     *
     */
    public void remove(String toRemove) {
        requireNonNull(toRemove);
        internalList.remove(toRemove);
    }

    public void setItemList(List<String> replacement) {
        this.internalList.addAll(replacement);
    }

    public void clear() {
        this.internalList.clear();
    }

    public List<String> getItemList() {
        List<String> toReturn = new ArrayList<>(internalList);
        return Collections.unmodifiableList(toReturn);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueItemList // instanceof handles nulls
                && this.internalList.equals(((UniqueItemList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

