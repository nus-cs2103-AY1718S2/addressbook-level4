package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;

/**
 * A list of students that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Student#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueStudentList implements Iterable<Student> {

    private final ObservableList<Student> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent student as the given argument.
     */
    public boolean contains(Student toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a student to the list.
     *
     * @throws DuplicateStudentException if the student to add is a duplicate of an existing student in the list.
     */
    public void add(Student toAdd) throws DuplicateStudentException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStudentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the student {@code target} in the list with {@code editedStudent}.
     *
     * @throws DuplicateStudentException if the replacement is equivalent to another existing student in the list.
     * @throws StudentNotFoundException if {@code target} could not be found in the list.
     */
    public void setStudent(Student target, Student editedStudent)
            throws DuplicateStudentException, StudentNotFoundException {
        requireNonNull(editedStudent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new StudentNotFoundException();
        }

        if (!target.equals(editedStudent) && internalList.contains(editedStudent)) {
            throw new DuplicateStudentException();
        }

        internalList.set(index, editedStudent);
    }

    /**
     * Removes the equivalent student from the list.
     *
     * @throws StudentNotFoundException if no such student could be found in the list.
     */
    public boolean remove(Student toRemove) throws StudentNotFoundException {
        requireNonNull(toRemove);
        final boolean studentFoundAndDeleted = internalList.remove(toRemove);
        if (!studentFoundAndDeleted) {
            throw new StudentNotFoundException();
        }
        return studentFoundAndDeleted;
    }

    public void setStudents(UniqueStudentList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setStudents(List<Student> students) throws DuplicateStudentException {
        requireAllNonNull(students);
        final UniqueStudentList replacement = new UniqueStudentList();
        for (final Student student : students) {
            replacement.add(student);
        }
        setStudents(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Student> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Student> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueStudentList // instanceof handles nulls
                        && this.internalList.equals(((UniqueStudentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
