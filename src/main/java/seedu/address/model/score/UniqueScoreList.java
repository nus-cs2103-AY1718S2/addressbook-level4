package seedu.address.model.score;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;

//@@author TeyXinHui
/**
 * A list of scores that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Score#equals(Object)
 */
public class UniqueScoreList implements Iterable<Score> {

    private final ObservableList<Score> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ScoreList.
     */
    public UniqueScoreList() {}

    /**
     * Creates a UniqueScoreList using given scores.
     * Enforces no nulls.
     */
    public UniqueScoreList(Set<Score> scores) {
        internalList.addAll(scores);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all scores in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Score> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Scores in this list with those in the argument score list.
     */
    public void setScores(Set<Score> scores) {
        requireAllNonNull(scores);
        internalList.setAll(scores);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every score in the argument list exists in this object.
     */
    public void mergeFrom(UniqueScoreList from) {
        final Set<Score> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(score -> !alreadyInside.contains(score))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Score as the given argument.
     */
    public boolean contains(Score toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Score to the list.
     */
    public void add(Score toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Score> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Score> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueScoreList // instanceof handles nulls
                && this.internalList.equals(((UniqueScoreList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueScoreList other) {
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
//@@author
