//@@author chenchongsong
package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of items that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 */
public class UniqueItemList implements Iterable<Item> {

    private ArrayList<Item> internalList = new ArrayList<>();

    /**
     * Constructs empty ItemList.
     */
    public UniqueItemList() {}

    /**
     * Creates a UniqueItemList using given items.
     * Enforces no nulls.
     */
    public UniqueItemList(ArrayList<Item> items) {
        requireAllNonNull(items);
        internalList.addAll(items);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all items in this list as a ArrayList.
     * This ArrayList is mutable and change-insulated against the internal list.
     */
    public ArrayList<Item> toArrayList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new ArrayList<>(internalList);
    }

    /**
     * Returns all items in this list as a String.
     */
    public String toString() {
        assert CollectionUtil.elementsAreUnique(internalList);
        final StringBuilder builder = new StringBuilder();
        builder.append("Items: ");
        for (int i = 0; i < internalList.size(); i++) {
            builder.append("\nItem No." + Integer.toString(i + 1) + "    ||    ");
            builder.append(internalList.get(i).toString());
        }
        return builder.toString();
    }

    /**
     * Replaces the Items in this list with those in the argument Item list.
     */
    public void setItems(ArrayList<Item> newItemList) {
        requireAllNonNull(newItemList);
        internalList = new ArrayList<>(newItemList);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every item in the argument list exists in this object.
     */
    public void mergeFrom(UniqueItemList from) {
        final ArrayList<Item> alreadyInside = this.toArrayList();
        from.internalList.stream()
                .filter(item -> !alreadyInside.contains(item))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Item as the given argument.
     */
    public boolean contains(Item toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Item to the list.
     *
     * @throws DuplicateItemException if the Item to add is a duplicate of an existing Item in the list.
     */
    public void add(Item toAdd) throws DuplicateItemException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateItemException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Item> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueItemList // instanceof handles nulls
                && this.internalList.equals(((UniqueItemList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueItemList other) {
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
     * returns the sum of all items in the internalList
     * @return
     */
    public double getValueSum() {
        double sum = 0.0;
        for (Item item: internalList) {
            sum += Double.parseDouble(item.getItemValue());
        }
        return sum;
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
