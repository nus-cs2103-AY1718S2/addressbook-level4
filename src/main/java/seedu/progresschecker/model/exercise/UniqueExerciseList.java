package seedu.progresschecker.model.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.progresschecker.commons.util.CollectionUtil;
import seedu.progresschecker.model.exercise.exceptions.DuplicateExerciseException;

//@@author iNekox3
/**
 * A list of exercises that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Exercise#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueExerciseList implements Iterable<Exercise> {

    private final ObservableList<Exercise> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent exercise as the given argument.
     */
    public boolean contains(Exercise toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an exercise to the list.
     *
     * @throws DuplicateExerciseException if the exercise to add is a duplicate of an existing exercise in the list.
     */
    public void add(Exercise toAdd) throws DuplicateExerciseException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateExerciseException();
        }
        internalList.add(toAdd);
    }

    public void setExercises(UniqueExerciseList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setExercises(List<Exercise> exercises) throws DuplicateExerciseException {
        requireAllNonNull(exercises);
        final UniqueExerciseList replacement = new UniqueExerciseList();
        for (final Exercise exercise : exercises) {
            replacement.add(exercise);
        }
        setExercises(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Exercise> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Exercise> iterator() {
        return internalList.iterator();
    }
}
