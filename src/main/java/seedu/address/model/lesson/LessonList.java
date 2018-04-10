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
import seedu.address.model.lesson.exceptions.LessonNotFoundException;

/**
 * @@author demitycho
 * A list of lessons that enforces uniqueness between its elements and does not allow nulls.
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
     * Adds a student to the list.
     * TODO: throw exception
     */
    public void add(Lesson toAdd) {
        requireNonNull(toAdd);
        if (!contains(toAdd)) {
            internalList.add(toAdd);
        }
        Collections.sort(internalList);
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

    public void setLessons(List<Lesson> lessons) throws DuplicateLessonException {
        requireAllNonNull(lessons);
        final LessonList replacement = new LessonList();
        for (final Lesson lesson : lessons) {
            replacement.add(lesson);
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
