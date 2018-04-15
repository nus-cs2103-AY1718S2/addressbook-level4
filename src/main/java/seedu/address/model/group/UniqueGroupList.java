//@@author jas5469
package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;

/**
 * A list of Groups that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Group#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent Group as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Group to the list.
     *
     * @throws DuplicateGroupException if the Group to add is a duplicate of an existing Group in the list.
     */
    public void add(Group groupToAdd) throws DuplicateGroupException {
        requireNonNull(groupToAdd);
        if (contains(groupToAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(groupToAdd);
    }

    public void setGroups(UniqueGroupList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setGroups(List<Group> groups) throws DuplicateGroupException {
        requireAllNonNull(groups);
        final UniqueGroupList replacement = new UniqueGroupList();
        for (final Group group : groups) {
            replacement.add(group);
        }
        setGroups(replacement);
    }

    /**
     * Removes the equivalent Group from the list.
     *
     * @throws GroupNotFoundException if no such Group could be found in the list.
     */
    public boolean remove(Group toRemove) throws GroupNotFoundException {
        requireNonNull(toRemove);
        final boolean groupFoundAndDeleted = internalList.remove(toRemove);
        if (!groupFoundAndDeleted) {
            throw new GroupNotFoundException();
        }
        return groupFoundAndDeleted;
    }

    /**
     * Replaces the group {@code target} in the list with {@code editedGroup}.
     *
     * @throws DuplicateGroupException if the replacement is equivalent to another existing Group in the list.
     * @throws GroupNotFoundException if {@code target} could not be found in the list.
     */
    public void setGroup(Group target, Group editedGroup)
            throws DuplicateGroupException, GroupNotFoundException {
        requireNonNull(editedGroup);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GroupNotFoundException();
        }

        if (!target.equals(editedGroup) && internalList.contains(editedGroup)) {
            throw new DuplicateGroupException();
        }

        internalList.set(index, editedGroup);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGroupList // instanceof handles nulls
                && this.internalList.equals(((UniqueGroupList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
