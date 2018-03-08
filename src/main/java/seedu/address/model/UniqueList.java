package seedu.address.model;

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
 * A list of items that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 */
public class UniqueList<T> implements Iterable<T> {

    protected final ObservableList<T> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty UniqueList.
     */
    public UniqueList() {}

    /**
     * Creates a UniqueList using given set.
     * Enforces no nulls.
     */
    public UniqueList(Set<T> items) {
        requireAllNonNull(items);
        internalList.addAll(items);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all items in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<T> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the items in this list with those in the argument list.
     */
    public void setItems(Set<T> items) {
        requireAllNonNull(items);
        internalList.setAll(items);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every item in the argument list exists in this object.
     */
    public void mergeFrom(UniqueList<T> from) {
        final Set<T> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(tag -> !alreadyInside.contains(tag))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent item as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an item to the list.
     *
     * @throws DuplicateItemException if the item to add is a duplicate of an existing item in the list.
     */
    public void add(T toAdd) throws DuplicateDataException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateItemException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<T> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueList // instanceof handles nulls
                && this.internalList.equals(((UniqueList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueList<T> other) {
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
    public static class DuplicateItemException extends DuplicateDataException {
        protected DuplicateItemException() {
            super("Operation would result in duplicate items");
        }
    }

}
