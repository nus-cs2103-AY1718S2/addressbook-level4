package seedu.organizer.model.subtask;

//@@author aguss787
import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.exceptions.DuplicateDataException;
import seedu.organizer.commons.util.CollectionUtil;

/**
 * A list of subtasks that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Subtask#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueSubtaskList implements Iterable<Subtask> {

    private final ObservableList<Subtask> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty SubtaskList.
     */
    public UniqueSubtaskList() {}

    /**
     * Creates a UniqueSubtaskList using given subtasks.
     * Enforces no nulls.
     */
    public UniqueSubtaskList(List<Subtask> subtasks) {
        requireAllNonNull(subtasks);
        internalList.addAll(subtasks);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all subtasks in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public List<Subtask> toList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new ArrayList<>(internalList);
    }

    /**
     * Replaces the Subtasks in this list with those in the argument Subtask list.
     */
    public void setSubtasks(List<Subtask> subtasks) {
        requireAllNonNull(subtasks);
        internalList.setAll(subtasks);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Subtask as the given argument.
     */
    public boolean contains(Subtask toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Subtask to the list.
     *
     * @throws DuplicateSubtaskException if the Subtask to add is a duplicate of an existing Subtask in the list.
     */
    public void add(Subtask toAdd) throws DuplicateSubtaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateSubtaskException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Count the number of specified subtask
     * @param subtask to count
     * @return number of specified subtask
     */
    public int count(Subtask subtask) {
        int result = 0;

        for (Subtask i: internalList) {
            if (i.equals(subtask)) {
                result += 1;
            }
        }

        return result;
    }

    /**
     * Set subtask in a specified index
     * @throws DuplicateSubtaskException
     */
    public void set(Index index, Subtask toReplace) throws  DuplicateSubtaskException {
        requireNonNull(toReplace);

        int subtaskCount = this.count(toReplace);
        if (internalList.get(index.getZeroBased()).equals(toReplace)) {
            subtaskCount -= 1;
        }
        if (subtaskCount > 0) {
            throw new DuplicateSubtaskException();
        }

        internalList.set(index.getZeroBased(), toReplace);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Subtask> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Subtask> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueSubtaskList // instanceof handles nulls
                && this.internalList.equals(((UniqueSubtaskList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueSubtaskList other) {
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
    public static class DuplicateSubtaskException extends DuplicateDataException {
        protected DuplicateSubtaskException() {
            super("Operation would result in duplicate subtasks");
        }
    }

    //@@author natania-d
    /**
     * Makes the {@code Status} of all the subtasks in list not done.
     */
    public void makeAllSubtasksUndone(List<Subtask> subtasks) {
        for (Subtask subtask : subtasks) {
            ;
        }
    }

}
