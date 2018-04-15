//@@author amad-person
package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.order.exceptions.OrderNotFoundException;

/**
 * A list of orders that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Order#equals(Object)
 */
public class UniqueOrderList implements Iterable<Order> {
    private final ObservableList<Order> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty OrderList.
     */
    public UniqueOrderList() {}

    /**
     * Creates a UniqueOrderList using given orders.
     * Enforces no nulls.
     */
    public UniqueOrderList(Set<Order> orders) {
        requireAllNonNull(orders);
        internalList.addAll(orders);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all orders in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Order> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the order {@code target} in the list with {@code editedOrder}.
     *
     * @throws DuplicateOrderException if the replacement is equivalent to another existing order in the list.
     * @throws OrderNotFoundException if {@code target} could not be found in the list.
     */
    public void setOrder(Order target, Order editedOrder)
            throws DuplicateOrderException, OrderNotFoundException {
        requireNonNull(editedOrder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new OrderNotFoundException();
        }

        if (!target.equals(editedOrder) && internalList.contains(editedOrder)) {
            throw new DuplicateOrderException();
        }

        internalList.set(index, editedOrder);
    }

    /**
     * Replaces the Orders in this list with those in the argument order list.
     */
    public void setOrders(Set<Order> orders) {
        requireAllNonNull(orders);
        internalList.setAll(orders);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every order in the argument list exists in this object.
     */
    public void mergeFrom(UniqueOrderList from) {
        final Set<Order> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(order -> !alreadyInside.contains(order))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Order as the given argument.
     */
    public boolean contains(Order toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an Order to the list.
     *
     * @throws DuplicateOrderException if the Order to add is a duplicate of an existing Order in the list.
     */
    public void add(Order toAdd) throws DuplicateOrderException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateOrderException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes Order from list if it exists.
     */
    public void remove(Order toRemove) {
        requireNonNull(toRemove);
        if (contains(toRemove)) {
            internalList.remove(toRemove);
        }
    }

    @Override
    public Iterator<Order> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Order> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueOrderList // instanceof handles nulls
                && this.internalList.equals(((UniqueOrderList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueOrderList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
}
