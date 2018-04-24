package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;
import seedu.address.model.activity.exceptions.DuplicateActivityException;
import seedu.address.model.util.DateTimeComparator;


/**
 * A list of activities, tasks and/or events that enforces uniqueness between its elements and
 * does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Activity#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueActivityList implements Iterable<Activity> {

    private static DateTimeComparator dateTimeComparator = new DateTimeComparator();

    private final ObservableList<Activity> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent activity as the given argument.
     */
    public boolean contains(Activity toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }


    /**
     * Adds an activity to the list.
     * If activity is a task or an event, is added to its respective list.
     *
     * @throws DuplicateActivityException if the activity to add is a duplicate of an existing activity in the list.
     */
    public void add(Activity toAdd) throws DuplicateActivityException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateActivityException();
        }
        internalList.add(toAdd);
        Collections.sort(internalList, dateTimeComparator);
    }

    /**
     * Replaces the activity {@code target} in the list with {@code editedActivity}.
     * If activity is a task or an event, edited in its respective list.
     *
     * @throws DuplicateActivityException if the replacement is equivalent to another existing activity in the list.
     * @throws ActivityNotFoundException if {@code target} could not be found in the list.
     */
    public void setActivity(Activity target, Activity editedActivity)
            throws DuplicateActivityException, ActivityNotFoundException {
        requireNonNull(editedActivity);

        int index = internalList.indexOf(target);
        int taskIndex;
        int eventIndex;

        if (index == -1) {
            throw new ActivityNotFoundException();
        }

        if (!target.equals(editedActivity) && internalList.contains(editedActivity)) {
            throw new DuplicateActivityException();
        }

        internalList.set(index, editedActivity);
        Collections.sort(internalList, dateTimeComparator);

    }

    public void setActivity(UniqueActivityList replacement) {
        this.internalList.setAll(replacement.internalList);
        Collections.sort(internalList, dateTimeComparator);
    }

    public void setActivity(List<Activity> activities) throws DuplicateActivityException {
        requireAllNonNull(activities);
        final UniqueActivityList replacement = new UniqueActivityList();
        for (final Activity activity : activities) {
            replacement.add(activity);
        }
        setActivity(replacement);
        Collections.sort(internalList, dateTimeComparator);
    }

    /**
     * Removes the equivalent activity from the list and its respective task or event list.
     *
     * @throws ActivityNotFoundException if no such activity could be found in the list.
     */
    public boolean remove(Activity toRemove) throws ActivityNotFoundException {
        requireNonNull(toRemove);
        final boolean activityFoundAndDeleted = internalList.remove(toRemove);
        if (!activityFoundAndDeleted) {
            throw new ActivityNotFoundException();
        } else  {
            internalList.remove(toRemove);
        }
        return activityFoundAndDeleted;
    }

    //@@author Kyomian
    /**
     * Clears either all tasks in deskboard or all events.
     * @param activityTypeToClear
     */
    public void clear(String activityTypeToClear) {
        internalList.removeIf(activity ->
            activity.getActivityType().equals(activityTypeToClear)
        );
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Activity> internalListAsObservable() {
        return FXCollections.unmodifiableObservableList(internalList);
    }



    @Override
    public Iterator<Activity> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueActivityList // instanceof handles nulls
                        && this.internalList.equals(((UniqueActivityList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
