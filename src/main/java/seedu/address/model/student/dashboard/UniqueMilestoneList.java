package seedu.address.model.student.dashboard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.student.dashboard.exceptions.DuplicateMilestoneException;
import seedu.address.model.student.dashboard.exceptions.MilestoneNotFoundException;

//@@author yapni
/**
 * A list of milestones that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Milestone#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueMilestoneList implements Iterable<Milestone> {

    private final ObservableList<Milestone> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent milestone as the given argument.
     */
    public boolean contains(Milestone toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a milestone to the list.
     *
     * @throws DuplicateMilestoneException if the milestone to add is a duplicate of an existing milestone in the list.
     */
    public void add(Milestone toAdd) throws DuplicateMilestoneException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMilestoneException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns the milestone at the specific {@code index} in the list
     *
     * @throws IndexOutOfBoundsException if the index provided is out of range
     */
    public Milestone get(Index index) throws IndexOutOfBoundsException {
        if (index.getZeroBased() < 0 || index.getZeroBased() >= internalList.size()) {
            throw new IndexOutOfBoundsException();
        }
        return internalList.get(index.getZeroBased());
    }

    /**
     * Returns the size of the internal list
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Replaces the milestone {@code target} in the list with {@code editedMilestone}.
     *
     * @throws DuplicateMilestoneException if the replacement is equivalent to another existing milestone in the list.
     * @throws MilestoneNotFoundException if {@code target} could not be found in the list.
     */
    public void setMilestone(Milestone target, Milestone editedMilestone)
            throws DuplicateMilestoneException, MilestoneNotFoundException {
        requireAllNonNull(target, editedMilestone);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MilestoneNotFoundException();
        }

        if (!target.equals(editedMilestone) && contains(editedMilestone)) {
            throw new DuplicateMilestoneException();
        }

        internalList.set(index, editedMilestone);
    }

    /**
     * Removes the equivalent milestone from the list.
     *
     * @throws MilestoneNotFoundException if no such milestone could be found in the list.
     */
    public boolean remove(Milestone toRemove) throws MilestoneNotFoundException {
        requireNonNull(toRemove);
        final boolean milestoneFoundAndDeleted = internalList.remove(toRemove);
        if (!milestoneFoundAndDeleted) {
            throw new MilestoneNotFoundException();
        }
        return milestoneFoundAndDeleted;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Milestone> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Milestone> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
            || (obj instanceof UniqueMilestoneList // instanceof handles null
                    && this.internalList.equals(((UniqueMilestoneList) obj).asObservableList()));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
