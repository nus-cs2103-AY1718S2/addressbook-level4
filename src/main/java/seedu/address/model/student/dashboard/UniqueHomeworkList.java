package seedu.address.model.student.dashboard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.student.dashboard.exceptions.DuplicateHomeworkException;
import seedu.address.model.student.dashboard.exceptions.HomeworkNotFoundException;

/**
 * A list of homework that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Homework#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueHomeworkList implements Iterable<Homework> {

    private final ObservableList<Homework> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent homework as the given argument.
     */
    public boolean contains(Homework toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a homework to the list.
     *
     * @throws DuplicateHomeworkException if the homework to add is a duplicate of an existing homework in the list.
     * @throws NullPointerException if toAdd is null
     */
    public void add(Homework toAdd) throws DuplicateHomeworkException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateHomeworkException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the homework {@code target} in the list with {@code editedHomework}.
     *
     * @throws DuplicateHomeworkException if the replacement is equivalent to another existing homework in the list.
     * @throws HomeworkNotFoundException if {@code target} could not be found in the list.
     * @throws NullPointerException if target or editedHomework is null
     */
    public void setHomework(Homework target, Homework editedHomework)
            throws DuplicateHomeworkException, HomeworkNotFoundException {
        requireAllNonNull(target, editedHomework);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new HomeworkNotFoundException();
        }

        if (!target.equals(editedHomework) && contains(target)) {
            throw new DuplicateHomeworkException();
        }

        internalList.set(index, editedHomework);
    }

    /**
     * Removes the equivalent homework from the list.
     *
     * @throws HomeworkNotFoundException if no such homework could be found in the list.
     * @throws NullPointerException if toRemove is null
     */
    public boolean remove(Homework toRemove) throws HomeworkNotFoundException {
        requireNonNull(toRemove);
        final boolean homeworkFoundAndDeleted = internalList.remove(toRemove);
        if (!homeworkFoundAndDeleted) {
            throw new HomeworkNotFoundException();
        }
        return homeworkFoundAndDeleted;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Homework> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Homework> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof UniqueHomeworkList // instanceof handles null
                && this.internalList.equals(((UniqueHomeworkList) obj).asObservableList()));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
