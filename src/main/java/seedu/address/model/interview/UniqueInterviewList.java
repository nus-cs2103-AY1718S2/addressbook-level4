//@@author deeheenguyen
package seedu.address.model.interview;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.interview.exceptions.DuplicateInterviewException;
import seedu.address.model.interview.exceptions.InterviewNotFoundException;

/**
 * A list of interviews that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Interview#equals(Object)
 */
public class UniqueInterviewList implements Iterable<Interview> {

    private final ObservableList<Interview> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Interview toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicateInterviewException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Interview toAdd) throws DuplicateInterviewException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateInterviewException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicateInterviewException if the replacement is equivalent to another existing person in the list.
     * @throws InterviewNotFoundException if {@code target} could not be found in the list.
     */
    public void setInterview(Interview target, Interview editedInterview)
            throws DuplicateInterviewException, InterviewNotFoundException {
        requireNonNull(editedInterview);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new InterviewNotFoundException();
        }

        if (!target.equals(editedInterview) && internalList.contains(editedInterview)) {
            throw new DuplicateInterviewException();
        }

        internalList.set(index, editedInterview);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws InterviewNotFoundException if no such person could be found in the list.
     */
    public boolean remove(Interview toRemove) throws InterviewNotFoundException {
        requireNonNull(toRemove);
        final boolean interviewFoundAndDeleted = internalList.remove(toRemove);
        if (!interviewFoundAndDeleted) {
            throw new InterviewNotFoundException();
        }
        return interviewFoundAndDeleted;
    }

    public void setInterviews(UniqueInterviewList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setInterviews(List<Interview> interviews) throws DuplicateInterviewException {
        requireAllNonNull(interviews);
        final UniqueInterviewList replacement = new UniqueInterviewList();
        for (final Interview interview : interviews) {
            replacement.add(interview);
        }
        setInterviews(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Interview> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Interview> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueInterviewList // instanceof handles nulls
                && this.internalList.equals(((UniqueInterviewList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
//author@@ deeheenguyen
