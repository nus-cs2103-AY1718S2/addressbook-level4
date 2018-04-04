package seedu.address.model.Insurance;

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
 * A list of insurance that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the reInsurance's features.
 *
 * @see Insurance#equals(Object)
 */
public class UniqueInsuranceList implements Iterable<Insurance> {
    private final ObservableList<Insurance> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty InsuranceList.
     */
    public UniqueInsuranceList() {}

    /**
     * Creates a UniqueInsuranceList using given insurances.
     * Enforces no nulls.
     */
    public UniqueInsuranceList(Set<Insurance> insurance) {
        requireAllNonNull(insurance);
        internalList.addAll(insurance);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all insurance in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Insurance> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Insurance in this list with those in the argument insurance list.
     */
    public void setInsurances(Set<Insurance> insurance) {
        requireAllNonNull(insurance);
        internalList.setAll(insurance);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every insurance in the argument list exists in this object.
     */
    public void mergeFrom(UniqueInsuranceList from) {
        final Set<Insurance> alreadyInside = this.toSet();
        from.internalList.stream()
            .filter(insurance -> !alreadyInside.contains(insurance))
            .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Insurance as the given argument.
     */
    public boolean contains(Insurance toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Insurance to the list.
     *
     * @throws DuplicateInsuranceException if the Insuranceto add is a duplicate of an existing Tag in the list.
     */
    public void add(Insurance toAdd) throws DuplicateInsuranceException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateInsuranceException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Insurance> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Insurance> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
            || (other instanceof UniqueInsuranceList // instanceof handles nulls
            && this.internalList.equals(((UniqueInsuranceList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueInsuranceList other) {
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
    public static class DuplicateInsuranceException extends DuplicateDataException {
        protected DuplicateInsuranceException() {
            super("Operation would result in duplicate insurances");
        }
    }

}















