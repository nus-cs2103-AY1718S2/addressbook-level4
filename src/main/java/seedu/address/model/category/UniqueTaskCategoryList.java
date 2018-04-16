package seedu.address.model.category;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

//@@author CYX28
/** A list of categories that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see TaskCategory#equals(Object)
 */
public class UniqueTaskCategoryList implements Iterable<TaskCategory> {

    private final ObservableList<TaskCategory> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskCategoryList.
     */
    public UniqueTaskCategoryList() {}

    /**
     * Creates a UniqueTaskCategoryList using given taskCategories.
     * Enforces no nulls.
     */
    public UniqueTaskCategoryList(Set<TaskCategory> categories) {
        requireAllNonNull(categories);
        internalList.addAll(categories);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all taskCategories in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<TaskCategory> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the TaskCategories in this list with those in the argument taskCategory list.
     */
    public void setTaskCategories(Set<TaskCategory> taskCategories) {
        requireAllNonNull(taskCategories);
        internalList.setAll(taskCategories);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every taskCategory in the argument list exists in this object.
     */
    public void mergeFrom(UniqueTaskCategoryList from) {
        final Set<TaskCategory> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(taskCategory -> !alreadyInside.contains(taskCategory))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent TaskCategory as the given argument.
     */
    public boolean contains(TaskCategory toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a TaskCategory to the list.
     *
     * @throws DuplicateTaskCategoryException if the TaskCategory to add
     * is a duplicate of an existing TaskCategory in the list.
     */
    public void add(TaskCategory toAdd) throws DuplicateTaskCategoryException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskCategoryException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<TaskCategory> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<TaskCategory> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this
                || (other instanceof UniqueTaskCategoryList
                && this.internalList.equals(((UniqueTaskCategoryList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueTaskCategoryList other) {
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
    public static class DuplicateTaskCategoryException extends DuplicateDataException {
        protected DuplicateTaskCategoryException() {
            super("Operation would result in duplicate taskCategories");
        }
    }

}
