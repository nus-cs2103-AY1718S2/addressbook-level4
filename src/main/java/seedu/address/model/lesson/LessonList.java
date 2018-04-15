package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;

import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.InvalidLessonTimeSlotException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.student.Student;

//@@author demitycho
/**
 * A list of lessons that enforces non clashes int timings between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Lesson#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class LessonList implements Iterable<Lesson> {

    private final ObservableList<Lesson> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent student as the given argument.
     */
    public boolean contains(Lesson toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a lesson to the list.
     */
    public void add(Lesson toAdd)
            throws InvalidLessonTimeSlotException, DuplicateLessonException {
        requireNonNull(toAdd);
        if (!isValidSlot(toAdd)) {
            throw new InvalidLessonTimeSlotException();
        }
        if (contains(toAdd)) {
            throw new DuplicateLessonException();
        }
        internalList.add(toAdd);
        Collections.sort(internalList);
    }
    /**
     * Checks if lesson clashes with other lessons in the schedule
     * @return true/false
     */
    private boolean isValidSlot(Lesson l) {
        for (Lesson lesson : internalList) {
            if (l.clashesWith(lesson)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Lesson> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Removes the equivalent lesson from the list.
     *
     * @throws LessonNotFoundException if no such lesson could be found in the list.
     */
    public boolean remove(Lesson toRemove) throws LessonNotFoundException {
        requireNonNull(toRemove);
        final boolean lessonFoundAndDeleted = internalList.remove(toRemove);
        if (!lessonFoundAndDeleted) {
            throw new LessonNotFoundException();
        }
        return lessonFoundAndDeleted;
    }
    public void setLessons(LessonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setLessons(List<Lesson> lessons)
            throws InvalidLessonTimeSlotException, DuplicateLessonException {
        requireAllNonNull(lessons);
        final LessonList replacement = new LessonList();
        for (final Lesson lesson : lessons) {
            replacement.add(lesson);
        }
        setLessons(replacement);
    }

    /**
     * Reconstructs a new {@code LessonList} replacement based on Lessons not associated with {@code Student}
     * {@code internalList} will setLessons of replacement
     * @param target
     */
    public void removeStudentLessons(Student target)
            throws InvalidLessonTimeSlotException, DuplicateLessonException {
        final LessonList replacement = new LessonList();
        for (Lesson lesson : internalList) {
            if (!target.getUniqueKey().equals(lesson.getUniqueKey())) {
                replacement.add(lesson);
            }
        }
        setLessons(replacement);
    }
    @Override
    public Iterator<Lesson> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LessonList // instanceof handles nulls
                        && this.internalList.equals(((LessonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
