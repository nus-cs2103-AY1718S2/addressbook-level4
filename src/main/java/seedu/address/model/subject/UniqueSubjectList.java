package seedu.address.model.subject;

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
 * A list of subjects that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Subject#equals(Object)
 */
public class UniqueSubjectList implements Iterable<Subject> {

    private final ObservableList<Subject> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty Subject List.
     */
    public UniqueSubjectList() {}

    /**
     * Creates a UniqueSubjectList using given subjects.
     * Enforces no nulls.
     */
    public UniqueSubjectList(Set<Subject> subjects) {
        requireAllNonNull(subjects);
        internalList.addAll(subjects);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all Subjects in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Subject> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Subjects in this list with those in the argument subject list.
     */
    public void setSubjects(Set<Subject> subjects) {
        requireAllNonNull(subjects);
        internalList.setAll(subjects);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every subject in the argument list exists in this object.
     */
    public void mergeFrom(UniqueSubjectList from) {
        final Set<Subject> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(subject -> !alreadyInside.contains(subject))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Subject as the given argument.
     */
    public boolean contains(Subject toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Subject to the list.
     *
     * @throws DuplicateSubjectException if the Tag to add is a duplicate of an existing Subject in the list.
     */
    public void add(Subject toAdd) throws DuplicateSubjectException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateSubjectException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Remove a specific Subject from the list.
     * @param toRemove
     */
    public void remove(Subject toRemove) {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            //throw new exception?
        }
        internalList.remove(toRemove);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Subject> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Subject> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueSubjectList // instanceof handles nulls
                && this.internalList.equals(((UniqueSubjectList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueSubjectList other) {
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
    public static class DuplicateSubjectException extends DuplicateDataException {
        protected DuplicateSubjectException() {
            super("Operation would result in duplicate subject");
        }
    }

}
