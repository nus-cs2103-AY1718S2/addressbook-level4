package seedu.address.model.pair;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

//@@author alexawangzi
/**
 * A list of pairHashs that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see PairHash#equals(Object)
 */
public class UniquePairHashList implements Iterable<PairHash> {

    private final ObservableList<PairHash> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PairHashList.
     */
    public UniquePairHashList() {}

    /**
     * Creates a UniquePairHashList using given pairHashs.
     * Enforces no nulls.
     */
    public UniquePairHashList(Set<PairHash> pairHashs) {
        requireAllNonNull(pairHashs);
        internalList.addAll(pairHashs);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all pairHashs in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<PairHash> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Returns true if the list contains an equivalent PairHash as the given argument.
     */
    public boolean contains(PairHash toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a PairHash to the list.
     *
     * @throws DuplicatePairHashException if the PairHash to add is a duplicate of an existing PairHash in the list.
     */
    public void add(PairHash toAdd) throws DuplicatePairHashException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePairHashException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<PairHash> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<PairHash> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniquePairHashList // instanceof handles nulls
                && this.internalList.equals(((UniquePairHashList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicatePairHashException extends DuplicateDataException {
        protected DuplicatePairHashException() {
            super("Operation would result in duplicate pairHashs");
        }
    }

}
