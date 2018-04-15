package seedu.address.model.pair;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.pair.exceptions.DuplicatePairException;
import seedu.address.model.pair.exceptions.PairNotFoundException;

//@@author alexawangzi
/**
 * A list of pairs that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Pair#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePairList implements Iterable<Pair> {

    private final ObservableList<Pair> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent pair as the given argument.
     */
    public boolean contains(Pair toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a pair to the list.
     *
     * @throws DuplicatePairException if the pair to add is a duplicate of an existing pair in the list.
     */
    public void add(Pair toAdd) throws DuplicatePairException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePairException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the pair {@code target} in the list with {@code editedPair}.
     *
     * @throws DuplicatePairException if the replacement is equivalent to another existing pair in the list.
     * @throws PairNotFoundException if {@code target} could not be found in the list.
     */
    public void setPair(Pair target, Pair editedPair)
            throws DuplicatePairException, PairNotFoundException {
        requireNonNull(editedPair);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PairNotFoundException();
        }

        if (!target.equals(editedPair) && internalList.contains(editedPair)) {
            throw new DuplicatePairException();
        }

        internalList.set(index, editedPair);
    }

    /**
     * Removes the equivalent pair from the list.
     *
     * @throws PairNotFoundException if no such pair could be found in the list.
     */

    public boolean remove(Pair toRemove) throws PairNotFoundException {
        requireNonNull(toRemove);
        final boolean pairFoundAndDeleted = internalList.remove(toRemove);
        if (!pairFoundAndDeleted) {
            throw new PairNotFoundException();
        }
        return pairFoundAndDeleted;
    }


    public void setPairs(UniquePairList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPairs(List<Pair> pairs) throws DuplicatePairException {
        requireAllNonNull(pairs);
        final UniquePairList replacement = new UniquePairList();
        for (final Pair pair : pairs) {
            replacement.add(pair);
        }
        setPairs(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Pair> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Pair> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePairList // instanceof handles nulls
                && this.internalList.equals(((UniquePairList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
