package seedu.address.model.item;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A list of filepaths that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see String#equals(Object)
 */
public class UniqueItemList {

    private final ArrayList<String> internalList = new ArrayList<>();

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
        requireNonNull(replacement);
        this.internalList.clear();
        this.internalList.addAll(replacement);
    }

    public void clear() {
        this.internalList.clear();
    }

    public List<String> getItemList() {
        return Collections.unmodifiableList(internalList);
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

